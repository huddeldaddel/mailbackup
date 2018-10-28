package engineer.thomas_werner.mailbackup.output;

import engineer.thomas_werner.mailbackup.input.MessageLoadedListener;

import javax.mail.Message;
import javax.mail.MessagingException;

/**
 * Listener that prints a status message for each mail that has been loaded from the server.
 *
 * @author Thomas Werner
 */
public class ConsoleWriter implements MessageLoadedListener {

    private String folderName;
    private String formattedFolderName;
    private int mailCount;
    private final OutputFormatter outputFormatter;

    public ConsoleWriter(final OutputFormatter outputFormatter) {
        this.outputFormatter = outputFormatter;
    }

    @Override
    public void messageLoaded(final Message message, final String folderName) throws MessagingException {
        if(folderName.equals(this.folderName)) {
            mailCount++;
            printOutput("\r" + formattedFolderName + ": " + mailCount);
        } else {
            final String wrap = null == this.folderName ? "" : "\n";
            this.folderName = folderName;
            formattedFolderName = outputFormatter.replaceFolderPathSeparator(folderName);
            mailCount = 1;
            printOutput(wrap + formattedFolderName + ": " + mailCount);
        }
    }

    @Override
    public void done() {
        printOutput("\n");
        folderName = null;
        mailCount = 0;
    }

    void printOutput(final String output) {
        System.out.print(output);
    }

}
