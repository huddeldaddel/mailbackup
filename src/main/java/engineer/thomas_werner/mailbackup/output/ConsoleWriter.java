package engineer.thomas_werner.mailbackup.output;

import engineer.thomas_werner.mailbackup.input.MessageLoadedListener;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.File;

/**
 * Listener that prints a status message for each mail that has been loaded from the server.
 *
 * @author Thomas Werner
 */
public class ConsoleWriter implements MessageLoadedListener {

    private String folderName;
    private int mailCount;

    @Override
    public void messageLoaded(final Message message, final String folderName) throws MessagingException {
        if(folderName.equals(this.folderName)) {
            mailCount++;
            System.out.print("\r" + folderName.replaceAll("\\.", File.separator) + ": " + mailCount);
        } else {
            final String wrap = null == this.folderName ? "" : "\n";
            this.folderName = folderName;
            mailCount = 1;
            System.out.print(wrap + folderName.replaceAll("\\.", File.separator) + ": " + mailCount);
        }
    }

    @Override
    public void done() {
        System.out.print("\n");
        folderName = null;
        mailCount = 0;
    }

}
