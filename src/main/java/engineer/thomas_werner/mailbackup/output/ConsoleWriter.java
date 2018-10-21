package engineer.thomas_werner.mailbackup.output;

import engineer.thomas_werner.mailbackup.input.MessageLoadedListener;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Listener that prints a status message for each mail that has been loaded from the server.
 *
 * @author Thomas Werner
 */
public class ConsoleWriter implements MessageLoadedListener {

    private final DateFormat dateFormat = new SimpleDateFormat();

    @Override
    public void messageLoaded(final Message message, final String folderName) throws MessagingException {
        String subject = message.getSubject().substring(0, Math.min(50, message.getSubject().length()));
        if(50 > subject.length())
            subject =  subject + " ".repeat(50 - subject.length());

        final String txt = new StringBuilder()
                .append("\r")
                .append(folderName.replaceAll("\\.", File.separator))
                .append(": ")
                .append(getMessageDate(message))
                .append(" ")
                .append(subject)
                .toString();
        System.out.print(txt);
    }

    private String getMessageDate(final Message message) throws MessagingException {
        Date date = message.getReceivedDate();
        if(null == date)
            date = message.getSentDate();
        return null == date ? "" : dateFormat.format(date);
    }

}
