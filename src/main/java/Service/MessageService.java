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
}