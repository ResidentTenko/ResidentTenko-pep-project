package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Service.AccountService;

import Model.Message;
import Service.MessageService;

import io.javalin.Javalin;
import io.javalin.http.Context;


public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

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
        
        app.post("/register", this::registerAccountHandler);
        app.post("/login", this::loginAccountHandler);
        app.post("/messages", this::submitMessageHandler);
        return app;
    }

    /**
     * Handler to register a new account.
     * The Jackson ObjectMapper will automatically convert the JSON of the POST request into an Account object.
     * If AccountService returns a null account (meaning posting an Account was unsuccessful), the API will return a 400
     * message (client error).
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.post method.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void registerAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account newAccount = accountService.addAccount(account);
        // Send the registered account as a JSON response
        if(newAccount!=null){
            ctx.json(mapper.writeValueAsString(newAccount));
        }else{
            ctx.status(400);
        }
    }

     /**
     * Handler to retrieve an account if it exists.
     * The Jackson ObjectMapper will automatically convert the JSON of the POST request into an Account object.
     * If accountService returns a null account (meaning posting an account was unsuccessful), the API will return a 400
     * message (client error).
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.post method.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void loginAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.getAccount(account);
        // Send the registered account as a JSON response
        if(loginAccount!=null){
            ctx.json(mapper.writeValueAsString(loginAccount));
        }else{
            ctx.status(401);
        }
    }

    /**
     * Handler to submit a new message.
     * The Jackson ObjectMapper will automatically convert the JSON of the POST request into an Account object.
     * If messageService returns a null message (meaning posting a message was unsuccessful), the API will return a 400
     * message (client error).
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.post method.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void submitMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = messageService.addMessage(message);
        // Send the registered account as a JSON response
        if(newMessage!=null){
            ctx.json(mapper.writeValueAsString(newMessage));
        }else{
            ctx.status(400);
        }
    }
}