package engineer.thomas_werner.mailbackup;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;

/**
 * Pipe is part of the "Pipes and Filters" pattern. This class implements the connection between two Filter classes.
 *
 * @author Thomas Werner
 */
public class Pipe {

    private static final Logger logger = Logger.getLogger(Pipe.class.getName());

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
                logger.log(
                        Level.SEVERE,
                        "Filter {0} threw Exception: {1}",
                        new Object[]{filter.getName(), ex.getMessage()}
                );
            }
        }
    }

}
