package engineer.thomas_werner.mailbackup.input;

import javax.mail.Message;
import javax.mail.MessagingException;

/**
 * MessageHandlers are listeners that can be registered with the Loader class. They will be invoked each time the Loader
 * retrieves a message from the IMAP server.
 *
 * @author Thomas Werner
 */
public interface MessageLoadedListener {

    /**
     * Invoked when the Loader received a message.
     *
     * @param message the message that the loader retrieved from the IMAP server
     * @param folderName the name / path of the folder that contained the message
     */
    void messageLoaded(Message message, String folderName) throws MessagingException;

    /**
     * Invoked when the Loader finished.
     */
    void done();

}
