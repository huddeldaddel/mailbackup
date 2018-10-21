package engineer.thomas_werner.mailbackup.output;

import engineer.thomas_werner.mailbackup.input.MessageLoadedListener;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Listener that prints a status message for each mail that has been loaded from the server.
 *
 * @author Thomas Werner
 */
public class ConsoleWriter implements MessageLoadedListener {

    private boolean verbose;
    private boolean silent;
    private final DateFormat dateFormat = new SimpleDateFormat();

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isSilent() {
        return silent;
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }

    @Override
    public void messageLoaded(final Message message, final String folderName) throws MessagingException {
        if(isVerbose()) {
            System.out.println(new StringBuilder().append(folderName)
                    .append(": ")
                    .append(getMessageDate(message))
                    .append(" ")
                    .append(message.getSubject())
                    .toString());
        } else if(!isSilent()) {
            System.out.print(".");
        }
    }

    private String getMessageDate(Message message) throws MessagingException {
        Date date = message.getReceivedDate();
        if(null == date)
            date = message.getSentDate();
        return null == date ? "" : dateFormat.format(date);
    }

}
