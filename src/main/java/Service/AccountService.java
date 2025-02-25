package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account){
        return accountDAO.insertAccount(account);
    }

    public Account checkAccountById(int account_id){
        return accountDAO.checkAccountById(account_id);
    }

    public Account checkAccountByUsernameAndPassword(Account account){
        return accountDAO.checkAccountByUsernameAndPassword(account);
    }
}
