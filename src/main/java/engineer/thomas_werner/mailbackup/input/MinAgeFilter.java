package engineer.thomas_werner.mailbackup.input;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.Date;

public class MinAgeFilter implements MessageFilter {

    private final Date dateThreshold;

    public MinAgeFilter(final Date date) {
        dateThreshold = date;
    }

    @Override
    public boolean passes(final Message message) throws MessagingException {
        if(null == dateThreshold)
            return true;

        Date messageDate = message.getReceivedDate();
        if(null == messageDate)
            messageDate = message.getSentDate();

        if(null == messageDate)
            return true;

        return messageDate.before(dateThreshold);
    }

}
