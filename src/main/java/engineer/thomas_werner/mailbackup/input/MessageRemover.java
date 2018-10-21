package engineer.thomas_werner.mailbackup.input;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;

public class MessageRemover implements MessageLoadedListener {

    @Override
    public void messageLoaded(final Message message, final String folderName) throws MessagingException {
        message.setFlag(Flags.Flag.DELETED, true);
    }

}
