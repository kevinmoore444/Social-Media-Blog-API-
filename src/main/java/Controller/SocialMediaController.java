package Controller;
import Service.AccountService;
import Service.MessageService;
import Model.Account;
import Model.Message;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    
    //Instantiate both service classes
    AccountService accountService;
    MessageService messageService;

    //No args constructor
    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registrationHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::newMessageHandler);
        app.get("/messages", this::allMessagesHandler);
        app.get("/messages/{message_id}", this::oneMessageHandler);
        app.delete("/messages/{message_id}", this::deleteHandler);
        app.patch("/messages/{message_id}", this::updateHandler);
        app.get("/accounts/{account_id}/messages", this::accountMessagesHandler);
        return app;
    }

    //Register User
    private void registrationHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account newAccount = accountService.createAccount(account);
        if(newAccount!=null){
            ctx.json(mapper.writeValueAsString(newAccount));
        }else{
            ctx.status(400);
        }
    }

    //Login User
    private void loginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account newAccount = accountService.loginUser(account);
        if(newAccount!=null){
            ctx.json(mapper.writeValueAsString(newAccount));
        }else{
            ctx.status(401);
        }
    }

    //Create new Message
    private void newMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.createMessage(message);
        if(addedMessage!=null){
            ctx.json(mapper.writeValueAsString(addedMessage));
        }else{
            ctx.status(400);
        }
    }


    //Retrieve All Messages
    private void allMessagesHandler(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }


    //Get One Message By ID
    private void oneMessageHandler(Context ctx){
        Message messageRetrieved = messageService.getOneMessage(Integer.parseInt(ctx.pathParam("message_id")));
        if(messageRetrieved != null){
            ctx.json(messageRetrieved);
        }
    }

    //Delete Message By ID
    private void deleteHandler(Context ctx){
        Message messageDeleted = messageService.deleteMessageById(Integer.parseInt(ctx.pathParam("message_id")));
        if(messageDeleted != null){
            ctx.json(messageDeleted);
        }
        
    }

    //Update Message By ID
    private void updateHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessage(message_id, message);
        if(updatedMessage == null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(updatedMessage));
        }

    }

    //Retreive all Messages from One User
    private void accountMessagesHandler(Context ctx){
        ctx.json(messageService.getAllUserMessages(Integer.parseInt(ctx.pathParam("account_id"))));
    }

}