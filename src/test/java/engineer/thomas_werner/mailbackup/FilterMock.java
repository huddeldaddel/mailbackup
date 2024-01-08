package engineer.thomas_werner.mailbackup;

import javax.mail.Message;
import javax.mail.MessagingException;

/**
 *
 * @author Thomas Werner
 */
public class FilterMock extends Filter {

    private int invokationCount;

    @Override
    public String getName() {
        return "FilterMock";
    }

    public int getInvokationCount() {
        return invokationCount;
    }

    @Override
    public void process(Message message, MessageContext context) throws MessagingException {
        invokationCount++;
    }



}
