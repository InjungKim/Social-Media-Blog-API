package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    //method to insert a message into the table
    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            //set SQL statement
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //set parameters for the SQL statement
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            //execute
            ps.executeUpdate();

            //run through the resultset
            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                //return the inserted message
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            //catch exceptions
            System.out.println(e.getMessage());
        }
        //if the insertion fails, return null
        return null;
    }

    //method to get all messages from table
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //set SQL statement
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //execute
            ResultSet rs = preparedStatement.executeQuery();

            //run through result set
            while(rs.next()){
                //create and add every message to list
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            //catch exceptions
            System.out.println(e.getMessage());
        }
        //return the message list
        return messages;
    }

    //method to retrieve a message by its id column
    public Message getMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //set SQL statement
            String sql = "SELECT * FROM message WHERE message.message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //set parameters
            preparedStatement.setInt(1, message_id);

            //execute
            ResultSet rs = preparedStatement.executeQuery();

            //run through the result set
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                //return the message with the matching id
                return message;
            }
        }catch(SQLException e){
            //catch exceptions
            System.out.println(e.getMessage());
        }
        //return null if there was no message with the matching id or if it ran into any problems
        return null;
    }

    //method to delete a message from table by its id column
    public void deleteMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //set SQL statement
            String sql = "DELETE FROM message WHERE message.message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //set parameters
            preparedStatement.setInt(1, message_id);

            //execute
            preparedStatement.executeQuery();
        }catch(SQLException e){
            //catch exceptions
            System.out.println(e.getMessage());
        }
    }

    //method to update a message text from table by its id column
    public void updateMessage(int message_id, Message msg){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //set SQL statement
            String sql = "UPDATE message SET message.message_text = ? WHERE message.message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //set parameters
            preparedStatement.setString(1, msg.getMessage_text());
            preparedStatement.setInt(2, message_id);

            //execute
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            //catch exceptions
            System.out.println(e.getMessage());
        }
    }

    //method to get all messages by the account id column (foreign key)
    public List<Message> getAllMessagesByUserHandler(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //set SQL statement
            String sql = "SELECT * FROM message WHERE message.posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //set parameters
            preparedStatement.setInt(1, account_id);

            //execute
            ResultSet rs = preparedStatement.executeQuery();

            //run throught the result set
            while(rs.next()){
                //add all messages in the result set into a list
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            //catch exceptions
            System.out.println(e.getMessage());
        }
        //return the list of messages
        return messages;
    }
}
