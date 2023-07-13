package Service;
import DAO.MessageDAO;
import Model.Message;
import java.util.List;

public class MessageService {

    //Instantiate MessageDao
    public MessageDAO messageDAO;
    
    //No Arg Constructor
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    //Create Message
    public Message createMessage(Message message) {
        if(message.getMessage_text().length() > 0 && message.getMessage_text().length() < 255 ){
            return messageDAO.createMessage(message);
        }
        return null;
    }

    //Get All Messages
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    //Get One Message
    public Message getOneMessage(int message_id){
        return messageDAO.getMessageById(message_id);
    }

    // Get All Messages From User, Given Account Id
    public List<Message> getAllUserMessages(int posted_by){
        return messageDAO.getAllUserMessages(posted_by);
    }

    // Delete a Message, Given Message Id
    public Message deleteMessageById(int message_id){
        Message messageToDelete = messageDAO.getMessageById(message_id);
        messageDAO.deleteMessageById(message_id);
        return messageToDelete;
    }

    // Update Message, Given Message Id
    public Message updateMessage(int message_id, Message message){
        if(message.getMessage_text().length() > 0 && message.getMessage_text().length() < 255 && messageDAO.getMessageById(message_id) != null){
            messageDAO.updateMessage(message_id, message);
            return messageDAO.getMessageById(message_id);
        }
        return null;
    }

}

