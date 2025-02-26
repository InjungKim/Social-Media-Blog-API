package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    //reference to DAO

    private MessageDAO messageDAO;

    //constructors
    public MessageService(){
        this.messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    //method to insert a message
    public Message addMessage(Message msg){
        return messageDAO.insertMessage(msg);
    }

    //method to retrieve all messages
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    //method to get a message by its id
    public Message getMessageById(int message_id){
        return messageDAO.getMessageById(message_id);
    }

    //method to delete message by its id
    public Message deleteMessageById(int message_id){
        Message msg = messageDAO.getMessageById(message_id);
        if(msg != null) messageDAO.deleteMessageById(message_id);
        return msg;
    }

    //method to update message text by its id
    public Message updateMessage(int message_id, Message msg){
        if(msg == null) return null;
        messageDAO.updateMessage(message_id, msg);
        return messageDAO.getMessageById(message_id);
    }

    //method to retrieve all messages by a particular user
    public List<Message> getAllMessagesByUserHandler(int account_id){
        return messageDAO.getAllMessagesByUserHandler(account_id);
    }
}
