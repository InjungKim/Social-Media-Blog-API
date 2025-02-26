package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    //method to insert an account into the table
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            //set SQL statement
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //set parameters
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            //execute
            ps.executeUpdate();

            //run through the result set
            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            if(pkeyResultSet.next()){
                //return the newly inserted account
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }catch(SQLException e){
            //catch exceptions
            System.out.println(e.getMessage());
        }
        //return null if insertion failed
        return null;
    }

    //method to retrieve an account by its id
    public Account checkAccountById(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            //set SQl statement
            String sql = "SELECT * FROM account WHERE account.account_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            //set parameters
            ps.setInt(1, account_id);

            //execute
            ResultSet rs = ps.executeQuery();

            //run through the result set
            while(rs.next()){
                //return the account in the result set
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return account;
            }
        }catch(SQLException e){
            //catch exceptions
            System.out.println(e.getMessage());
        }
        //return null if there was no account by the id or a problem occured
        return null;
    }

    //method to retrieve an account by its username and password
    public Account checkAccountByUsernameAndPassword(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            //set SQL statement
            String sql = "SELECT * FROM account WHERE account.username = ? AND account.password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            //set parameters
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            //execute
            ResultSet rs = ps.executeQuery();

            //run through the result set
            while(rs.next()){
                //return the account in the result set
                Account acc = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return acc;
            }
        }catch(SQLException e){
            //catch exceptions
            System.out.println(e.getMessage());
        }
        //return null if there was no account by the username and password or a problem occured
        return null;
    }
}
