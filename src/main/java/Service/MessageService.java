package Service;

import Model.Message;
import Model.Account;
import DAO.MessageDAO;
import DAO.AccountDAO;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    /**
     * no-args constructor for creating a new MessageService with a new MessageDAO.
     * There is no need to change this constructor.
     */
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    /**
     * Constructor for a MessageService when an MessageDAO is provided.
     * This is used for when a mock MessageDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of MessageService independently of MessageDAO.
     */
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    /**
     * Use the MessageDAO to persist a message. The given Message will not have an id provided.
     * Method should check:
     * if the message text is not blank, <= 255 characters and is posted by a real user then it's a valid message, 
     * @param message - a Message object.
     * @return Message if the persisted message was successful
     */
    public Message addMessage(Message message) {
        AccountDAO accountDAO = new AccountDAO();
        // Check if the message is blank or is the right length
        if (message.getMessage_text().trim().isEmpty() || message.getMessage_text().length() > 255) 
        {
            return null;
        }

        // Check if an Account with that posted_by id exists
        Account existingAccount = accountDAO.retrieveAccountById(message.getPosted_by());
        if (existingAccount == null) 
        {
            return null;
        }
        // If all checks pass, insert the message
        return messageDAO.insertMessage(message);
    }

    /**
     * Uses the messageDAO to retrieve all messages.
     * @return all messages.
     */
    public List<Message> getAllMessages() {
        return messageDAO.retrieveAllMessages();
    }

    /**
     * Uses the messageDAO to retrieve a nessage identified by its Id.
     * @return message - a message object.
     */
    public Message getMessageById(int messageId) {
        return messageDAO.retrieveMessageById(messageId);
    }

    /**
     * Uses the messageDAO to remove a nessage identified by its Id.
     * @return nothing
     */
    public void deleteMessage(Message message) {
        messageDAO.removeMessage(message);
    }

    /**
     * Uses the messageDAO to update a nessage identified by its Id.
     * @return message - A message object
     */
    public Message updateMessage(Message message)
    {
        // get the message that matches the id of the message param
        Message databaseMessage = this.getMessageById(message.getMessage_id());

        // if the message doesn't exist return null
        if(databaseMessage == null)
        {
            return null;
        }

        // Check if the message is blank or is the right length
        if (message.getMessage_text().trim().isEmpty() || message.getMessage_text().length() > 255) 
        {
            return null;
        }

        // if the message is valid then update the text of the database message
        databaseMessage.setMessage_text(message.getMessage_text());
        // input the updated message into the database
        messageDAO.updateMessage(databaseMessage);

        // return the updated message
        return databaseMessage;
    }

    /**
     * Uses the messageDAO to retrieve all nessages identified by a user Id.
     * @return a list of message objects.
     */
    public List<Message> getMessagesByAccountId(int accountId) {
        return messageDAO.retrieveMessagesByAccountId(accountId);
    }
}