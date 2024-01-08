package engineer.thomas_werner.mailbackup;

import javax.mail.Message;

/**
 * Pipe is part of the "Pipes and Filters" pattern. This class implements the connection between two Filter classes.
 *
 * @author Thomas Werner
 */
public class Pipe {

    private final Filter filter;

    public Pipe(final Filter filter) {
        this.filter = filter;
    }

    public Filter getFilter() {
        return filter;
    }

    public void process(final Message message, final MessageContext context) {
        if(null != filter) {
            try {
                filter.process(message, context);
            } catch(final Exception ex) {
                System.err.println("Filter " + filter.getName() + " threw Exception: " + ex.getMessage());
            }
        }
    }

}
