package engineer.thomas_werner.mailbackup.input;

import javax.mail.Message;
import javax.mail.MessagingException;

public interface MessageFilter {

    boolean passes(Message message) throws MessagingException;

}
