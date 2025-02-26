package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    //constructor
    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("messages", this::getAllMessagesHandler);
        app.get("messages/{message_id}", this::getMessageByIdHandler);
        app.delete("messages/{message_id}", this::deleteMessageByIdYHandler);
        app.patch("messages/{message_id}", this::updateMessageByIdHandler);
        app.get("accounts/{account_id}/messages", this::getAllMessagesByUserHandler);
        return app;
    }

    /**
     * This is a Handler for processing new user registrations
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postAccountHandler(Context ctx) throws JsonProcessingException{
        //requirement 1
        //read the ctx and get Account
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(ctx.body(), Account.class);
        
        //check to see if account is valid (username is not blank and the password is atleast 4 chars and account with username does not already exist)
        if(acc.getUsername() != null && acc.getUsername().length() > 0 && acc.getPassword().length() >= 4){
            Account accountExists = accountService.checkAccountById(acc.getAccount_id());
            //if the checkAccountById returns a non-null value, an account by that id exists, so return with status 400
            if(accountExists != null){
                ctx.status(400);
            }else{
                Account addedAcc = accountService.addAccount(acc);
                if(addedAcc!=null){
                    ctx.json(mapper.writeValueAsString(addedAcc));
                }else{
                    //adding account failed
                    ctx.status(400);
                }
            }
        }else{
            //did not meet requirements
            ctx.status(400);
        }
    }

    /**
     * This is a Handler for processing user Logins
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postLoginHandler(Context ctx) throws JsonProcessingException{
        //requirement 2
        //read the ctx and get Account
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(ctx.body(), Account.class);
        
        //check to see if account is valid (an account by this username and password exists in the database)
        Account accountExists = accountService.checkAccountByUsernameAndPassword(acc);
        if(accountExists == null){
            ctx.status(401);
        }else{
            ctx.json(mapper.writeValueAsString(accountExists));
        }
    }

    /**
     * This is a Handler for the creation of new messages
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postMessageHandler(Context ctx) throws JsonProcessingException{
        //requirement 3
        //read the ctx and get Message
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(ctx.body(), Message.class);
        
        //check to see if message is valid (message text is not blank and less than 255 chars and is posted by an actual user in the database)
        if(msg.getMessage_text() != null && msg.getMessage_text().length() > 0 && msg.getMessage_text().length() <= 255){
            Account postedByUser = accountService.checkAccountById(msg.getPosted_by());
            //if the checkAccountById returns a null value, an account by that id does not exist, so return with status 400
            if(postedByUser == null){
                ctx.status(400);
            }else{
                Message addedMsg = messageService.addMessage(msg);
                if(addedMsg != null){
                    ctx.json(mapper.writeValueAsString(addedMsg));
                }else{
                    //adding account failed
                    ctx.status(400);
                }
            }
        }else{
            //did not meet requirements
            ctx.status(400);
        }
    }

    /**
     * This is a Handler for retrieving all messages
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessagesHandler(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }
    
    /**
     * This is a Handler for retrieving a message by its ID
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessageByIdHandler(Context ctx){
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(message_id);
        if(message != null){
            ctx.json(message);
        }
    }

    /**
     * This is a Handler for deleting a message by its ID
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void deleteMessageByIdYHandler(Context ctx){
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessageById(message_id);
        if(message != null){
            ctx.json(message);
        }
    }

    /**
     * This is a Handler for updating a message text identified by its ID
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void updateMessageByIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        if(msg.getMessage_text() != null && msg.getMessage_text().length() > 0 && msg.getMessage_text().length() <= 255){
            Message updatedMessage = messageService.updateMessage(message_id, msg);
            if(updatedMessage == null){
                ctx.status(400);
            }else{
                ctx.json(mapper.writeValueAsString(updatedMessage));
            }
        }else{
            ctx.status(400);
        }
    }

    /**
     * This is a Handler for retrieving all messages written by a particular user
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessagesByUserHandler(Context ctx){
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesByUserHandler(account_id);
        ctx.json(messages);
    }
}