package DAO;
import Util.ConnectionUtil;
import Model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



// User Registration
// As a user, I should be able to create a new Account on the endpoint POST localhost:8080/register. 
// The body will contain a representation of a JSON Account, but will not contain an account_id.
// The registration will be successful if and only if the username is not blank, the password is at 
// least 4 characters long, and an Account with that username does not already exist. 
// If all these conditions are met, the response body should contain a JSON of the Account, including its account_id. 
// The response status should be 200 OK, which is the default. The new account should be persisted to the database.
// If the registration is not successful, the response status should be 400. (Client error)

// Login
// As a user, I should be able to verify my login on the endpoint POST localhost:8080/login. 
// The request body will contain a JSON representation of an Account, not containing an account_id. 
// In the future, this action may generate a Session token to allow the user to securely use the site. 
// We will not worry about this for now.
// The login will be successful if and only if the username and password provided in the request body JSON 
// match a real account existing on the database. 
// If successful, the response body should contain a JSON of the account in the response body, including its account_id. 
// The response status should be 200 OK, which is the default.
// If the login is not successful, the response status should be 401. (Unauthorized)




public class AccountDAO {
    
    //Create New Account
    public Account createAccount(Account newAccount){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Create prepared statement. 
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newAccount.getUsername());
            preparedStatement.setString(2, newAccount.getPassword());

            //Execute update
            preparedStatement.executeUpdate();
            
            //Retrieve and unpack the response. If there is a response, use it to create a new account object with the generated ID  
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_message_id, newAccount.getUsername(), newAccount.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Login
    public Account loginUser(Account account){
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            //Create a prepared Statement
            String sql = "SELECT * FROM account WHERE username = (?) AND password = (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            //Execute Statement
            ResultSet rs = preparedStatement.executeQuery();

            //Store the record in an account object and return it to the service. 
            while(rs.next()){
                Account newAccount = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password")
                        );
                return newAccount;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


}
