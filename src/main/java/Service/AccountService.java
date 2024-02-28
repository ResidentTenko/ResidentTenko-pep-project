package Service;

import Model.Account;
import DAO.AccountDAO;
import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;

    /**
     * no-args constructor for creating a new AccountService with a new AccountDAO.
     * There is no need to change this constructor.
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    /**
     * Constructor for a AccountService when an AccountDAO is provided.
     * This is used for when a mock AccountDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of AccountService independently of AccountDAO.
     */
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    /**
     * Use the AccountDAO to persist an Account. The given Account will not have an id provided.
     * Method should check:
     * if the username is not blank, 
     * the password is at least 4 characters long, 
     * and an Account with that username does not already exist
     * @param account - an Account object.
     * @return Account if the persisted account was successful
     */
    public Account addAccount(Account account) {
        // Check if the username is not blank
        if (account.getUsername() == null || account.getUsername().trim().isEmpty()) 
        {
            return null;
        }

        // Check if the password is at least 4 characters long
        if (account.getPassword() == null || account.getPassword().length() < 4) 
        {
            return null;
        }
        // Check if an Account with that username does not already exist
        Account existingAccount = accountDAO.retrieveAccountByUsername(account.getUsername());
        if (existingAccount != null) 
        {
            return null;
        }

        // If all checks pass, insert the account
        return accountDAO.insertAccount(account);
    }

    /**
     * Use the AccountDAO to return an Account. The given Account will not have an id provided.
     * @param account an Account object.
     * @return Account if the account exists or null if it doesn't
     */
    public Account getAccount(Account account) {
        Account existingAccount = accountDAO.retrieveAccount(account);
        
        if (existingAccount != null) 
        {
            return existingAccount;
        } 
        else 
        {
            return null;
        }
    }
}
