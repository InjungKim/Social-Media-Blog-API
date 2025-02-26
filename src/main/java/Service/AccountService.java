package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    //reference to DAO
    private AccountDAO accountDAO;

    //constructors
    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    //method to insert an account 
    public Account addAccount(Account account){
        return accountDAO.insertAccount(account);
    }

    //method to retrieve an account by its id
    public Account checkAccountById(int account_id){
        return accountDAO.checkAccountById(account_id);
    }

    //method to retrieve an account by its username and password
    public Account checkAccountByUsernameAndPassword(Account account){
        return accountDAO.checkAccountByUsernameAndPassword(account);
    }
}
