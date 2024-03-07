package engineer.thomas_werner.mailbackup.input;

import engineer.thomas_werner.mailbackup.FilterMock;
import engineer.thomas_werner.mailbackup.MessageMock;
import engineer.thomas_werner.mailbackup.Pipe;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.mail.Message;
import javax.mail.MessagingException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for MinAgeFilter
 *
 * @author Thomas Werner
 */
class MinAgeFilterTest {

    public MinAgeFilterTest() {
    }

    @Test
    void testProcessInMailOlder() throws Exception {
        final Message inOnMonday = new MessageWithDateMock(new GregorianCalendar(2024, 1, 8).getTime(), null);

        final MinAgeFilter minAgeFilter = new MinAgeFilter(new GregorianCalendar(2024, 1, 9).getTime());
        final FilterMock filterMock = new FilterMock();
        minAgeFilter.connect(new Pipe(filterMock));

        minAgeFilter.process(inOnMonday, null);
        assertEquals(1, filterMock.getInvokationCount());
    }

    @Test
    void testProcessInMailSameDay() throws Exception {
        final Message inOnTuesday = new MessageWithDateMock(new GregorianCalendar(2024, 1, 9).getTime(), null);

        final MinAgeFilter minAgeFilter = new MinAgeFilter(new GregorianCalendar(2024, 1, 9).getTime());
        final FilterMock filterMock = new FilterMock();
        minAgeFilter.connect(new Pipe(filterMock));

        minAgeFilter.process(inOnTuesday, null);
        assertEquals(0, filterMock.getInvokationCount());
    }

    @Test
    void testProcessOutMailOlder() throws Exception {
        final Message outOnMonday = new MessageWithDateMock(null, new GregorianCalendar(2024, 1, 8).getTime());

        final MinAgeFilter minAgeFilter = new MinAgeFilter(new GregorianCalendar(2024, 1, 9).getTime());
        final FilterMock filterMock = new FilterMock();
        minAgeFilter.connect(new Pipe(filterMock));

        minAgeFilter.process(outOnMonday, null);
        assertEquals(1, filterMock.getInvokationCount());
    }

    @Test
    void testProcessOutMailSameDay() throws Exception {
        final Message outOnTuesday = new MessageWithDateMock(null, new GregorianCalendar(2024, 1, 9).getTime());

        final MinAgeFilter minAgeFilter = new MinAgeFilter(new GregorianCalendar(2024, 1, 9).getTime());
        final FilterMock filterMock = new FilterMock();
        minAgeFilter.connect(new Pipe(filterMock));

        minAgeFilter.process(outOnTuesday, null);
        assertEquals(0, filterMock.getInvokationCount());
    }

    private class MessageWithDateMock extends MessageMock {

        private final Date sent;
        private final Date received;

        MessageWithDateMock(final Date received, final Date sent) {
            this.received = received;
            this.sent = sent;
        }

        @Override
        public Date getSentDate() throws MessagingException {
            return sent;
        }

        @Override
        public Date getReceivedDate() throws MessagingException {
            return received;
        }
    }

}
