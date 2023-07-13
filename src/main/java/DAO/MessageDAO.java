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
                int generated_message_id = (int) pkeyResultSet.getLong(1);
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
    public Message deleteMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Create a prepared Statement
            String sql = "DELETE FROM message WHERE message_id = (?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);

            //Execute Statement, it will return an int number of rows effected, which will be 1 or 0 depending upon whether that message exists in the database. 
            int rowsDeleted = preparedStatement.executeUpdate();

            //If we successfully deleted a row, run a select query to return the deleted message
            if (rowsDeleted > 0) {
                //Create a prepared Statement
                String sql2 = "SELECT * FROM message WHERE message_id = ?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setInt(1, message_id);

                //Execute Statement
                ResultSet rs = preparedStatement2.executeQuery();

                //Store the deleted record in a message object and return it to the service. 
                while(rs.next()){
                    Message deletedMessage = new Message(rs.getInt("message_id"),
                            rs.getInt("posted_by"),
                            rs.getString("message_text"),
                            rs.getLong("time_posted_epoch"));
                    return deletedMessage;
                }
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        //Return null if no message was found to delete.
        return null;
    }


    //Update Message
    public Message updateMessage(int message_id, String message_text){
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            //Create a prepared Statement
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write PreparedStatement setString and setInt methods here.
            preparedStatement.setString(1, message_text);
            preparedStatement.setInt(2, message_id);

            //Execute Statement
            int rowsUpdated = preparedStatement.executeUpdate();

            //If we successfully updated a row, run a select query to return the updated message
            if (rowsUpdated > 0) {
                //Create a prepared Statement
                String sql2 = "SELECT * FROM message WHERE message_id = ?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setInt(1, message_id);

                //Execute Statement
                ResultSet rs = preparedStatement2.executeQuery();

                //Store the deleted record in a message object and return it to the service. 
                while(rs.next()){
                    Message updatedMessage = new Message(rs.getInt("message_id"),
                            rs.getInt("posted_by"),
                            rs.getString("message_text"),
                            rs.getLong("time_posted_epoch"));
                    return updatedMessage;
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        //Return null if no message was found for updating.
        return null;
    }

}


