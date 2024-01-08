package engineer.thomas_werner.mailbackup.input;

import engineer.thomas_werner.mailbackup.Filter;
import engineer.thomas_werner.mailbackup.MessageContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.Date;

public class MinAgeFilter extends Filter {

    private final Date dateThreshold;

    public MinAgeFilter(final Date date) {
        dateThreshold = date;
    }

    @Override
    public String getName() {
        return "MinAgeFilter";
    }

    @Override
    public void process(final Message message, final MessageContext context) throws MessagingException {
        if(null == pipe) {
            return;
        }

        if(null == dateThreshold) {
            pipe.process(message, context);
        }

        Date messageDate = message.getReceivedDate();
        if(null == messageDate) {
            messageDate = message.getSentDate();
        }

        if((null == messageDate) || messageDate.before(dateThreshold)) {
            pipe.process(message, context);
        }
    }

}
