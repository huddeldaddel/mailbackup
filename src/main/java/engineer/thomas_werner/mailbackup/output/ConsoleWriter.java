package engineer.thomas_werner.mailbackup.output;

import engineer.thomas_werner.mailbackup.Filter;
import engineer.thomas_werner.mailbackup.MessageContext;

import javax.mail.Message;
import javax.mail.MessagingException;

/**
 * Listener that prints a status message for each mail that has been loaded from the server.
 *
 * @author Thomas Werner
 */
public class ConsoleWriter extends Filter {

    String folderName = "";

    void printOutput(final String output) {
        System.out.print(output);
    }

    @Override
    public String getName() {
        return "ConsoleWriter";
    }

    @Override
    public void process(final Message message, final MessageContext context) throws MessagingException {
        if(!("".equals(this.folderName) || (this.folderName.equals(context.getFolderName())))) {
            printOutput("\n");
        }
        if(!this.folderName.equals(context.getFolderName())) {
            this.folderName = context.getFolderName();
        }

        final String formattedFolderName = new OutputFormatter().replaceFolderPathSeparator(folderName);
        final int mailCnt = context.getFolderMessageCount();
        final int mailIdx = context.getMessageIndex() + 1;
        printOutput("\r" + formattedFolderName + ": " + mailIdx + "/" + mailCnt);

        if(null != this.pipe) {
            pipe.process(message, context);
        }
    }

}
