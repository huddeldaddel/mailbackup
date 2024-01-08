package engineer.thomas_werner.mailbackup.output;

import engineer.thomas_werner.mailbackup.Filter;
import engineer.thomas_werner.mailbackup.MessageContext;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;

public class MessageRemover extends Filter {

    @Override
    public String getName() {
        return "MessageRemover";
    }

    @Override
    public void process(Message message, final MessageContext context) throws MessagingException {
        message.setFlag(Flags.Flag.DELETED, true);

        if(null != pipe) {
            pipe.process(message, context);
        }
    }

}
