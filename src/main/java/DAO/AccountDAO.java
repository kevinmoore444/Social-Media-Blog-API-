package DAO;
import Util.ConnectionUtil;
import Model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;


public class AccountDAO {
    
    //Create New Account
    public Account createAccount(Account newAccount){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Create prepared statement. 
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, newAccount.getUsername());
            preparedStatement.setString(2, newAccount.getPassword());

            //Execute update
            preparedStatement.executeUpdate();
            
            //If successful, return new Account object. 
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

    //Check for existing username - to prevent duplicate accounts.
    public String checkUsername(String username){
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            //Create a prepared Statement
            String sql = "SELECT * FROM account WHERE username = (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            //Execute Query
            ResultSet rs = preparedStatement.executeQuery();

            //If username already taken, return the username
            while(rs.next()){
                return rs.getString("username");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        //If usename not yet taken, return null
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

            //Execute Query
            ResultSet rs = preparedStatement.executeQuery();

            //Store and return the retrieved record in a new account object.
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
        //Return null if no records found with given username and pw.
        return null;
    }
}
