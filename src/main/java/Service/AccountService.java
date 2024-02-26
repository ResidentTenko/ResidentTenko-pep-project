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
     * @param account an account object.
     * @return Account if the persisted account was successful
     * Else null if it was not successfully persisted (eg if the account does not satisfy the above criteria)
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
        Account existingAccount = accountDAO.getAccountByUsername(account.getUsername());
        if (existingAccount != null) 
        {
            return null;
        }

        // If all checks pass, insert the account
        return accountDAO.insertAccount(account);
    }

    /**
     * TODO: Use the bookDAO to persist a book to the database.
     * An ISBN will be provided in Book. Method should check if the book ISBN already exists before it attempts to
     * persist it.
     * @param book a book object.
     * @return book if it was successfully persisted, null if it was not successfully persisted (eg if the book primary
     * key was already in use.)
     
    public Book addBook(Book book) {
        // Check if the book with the given ISBN already exists
        Book existingBook = bookDAO.getBookByIsbn(book.getIsbn());
        if (existingBook == null) 
        {
            // Book with the given ISBN does not exist, proceed with insertion
            return bookDAO.insertBook(book);
        } 
        else 
        {
            // Book with the given ISBN already exists
            return null;
        }
    }
    */
}
