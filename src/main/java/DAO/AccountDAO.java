package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO {
    /**
     * Inserts a new account into the Accounts table.
     * @param account - an Account object
     * @return an Account object if no errors occur
     */
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString method here.
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves an account from the Accounts table, identified by its username and password.
     * @param account - an Account object
     * @return an account identified by username and password.
     */
    public Account retrieveAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
    
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int accountId = resultSet.getInt("account_id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                return new Account(accountId, username, password);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves an account from the Accounts table, identified by its username.
     * @return an account identified by username.
     * */
    public Account retrieveAccountByUsername(String username) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
            preparedStatement.setString(1, username);
    
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int accountId = resultSet.getInt("account_id");
                String password = resultSet.getString("password");
                return new Account(accountId, username, password);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves an account from the Accounts table, identified by its ID.
     * @return an account identified by ID.
     * */
    public Account retrieveAccountById(int accountId) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
            preparedStatement.setInt(1, accountId);
    
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                return new Account(accountId, username, password);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
