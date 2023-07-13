package DAO;
import Util.ConnectionUtil;
import Model.Message;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class MessageDAO {
    
    //Create New Message
    public Message createMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Create prepared statement. 
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            //Execute update
            preparedStatement.executeUpdate();
            
            //Retrieve and unpack the response. If there is a response, use it to create a new message object with the generated ID and 
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getInt(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //Get All messages
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {

            //Create a prepared Statement
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //Execute Statement
            ResultSet rs = preparedStatement.executeQuery();

            //With every row in the response, create a new message object and append it to our list of messages. 
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    // Get All Messages From User Given Account Id
    public List<Message> getAllUserMessages(int posted_by){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {

            //Create a prepared Statement
            String sql = "SELECT * FROM message WHERE posted_by = (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, posted_by);

            //Execute Statement
            ResultSet rs = preparedStatement.executeQuery();

            //With every row in the response, create a new message object and append it to our list of messages. 
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    //Get One Message
    public Message getMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            //Create a prepared Statement
            String sql = "SELECT * FROM message WHERE message_id = (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);

            //Execute Statement
            ResultSet rs = preparedStatement.executeQuery();

            //Store the record in a message object and return it to the service. 
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //Delete One Message
    public void deleteMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Create a prepared Statement
            String sql = "DELETE FROM message WHERE message_id = (?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);

            //Execute Statement 
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }


    //Update Message
    public void updateMessage(int message_id, Message message){
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            //Create a prepared Statement
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, message.getMessage_text());
            preparedStatement.setInt(2, message_id);

            //Execute Statement
            preparedStatement.executeUpdate();            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

}


