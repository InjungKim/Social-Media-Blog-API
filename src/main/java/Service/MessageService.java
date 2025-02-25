package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        this.messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message msg){
        return messageDAO.insertMessage(msg);
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int message_id){
        return messageDAO.getMessageById(message_id);
    }

    public Message deleteMessageById(int message_id){
        Message msg = messageDAO.getMessageById(message_id);
        if(msg != null) messageDAO.deleteMessageById(message_id);
        return msg;
    }

    public Message updateMessage(int message_id, Message msg){
        if(msg == null) return null;
        messageDAO.updateMessage(message_id, msg);
        return messageDAO.getMessageById(message_id);
    }

    public List<Message> getAllMessagesByUserHandler(int account_id){
        return messageDAO.getAllMessagesByUserHandler(account_id);
    }
}
