package Service;
import DAO.AccountDAO;
import Model.Account;


public class AccountService {
    
    //Instantiate AccountDAO
    public AccountDAO accountDAO;
    
    //No Arg Constructor
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    //Create Account
    public Account createAccount(Account newAccount){
        if(newAccount.getUsername().length() > 0 && newAccount.getPassword().length() >=4 && accountDAO.checkUsername(newAccount.username) == null){
            return accountDAO.createAccount(newAccount);
        }
        return null;
    }

    //Login User
    public Account loginUser(Account account){
        return accountDAO.loginUser(account);
    }

}