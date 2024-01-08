package engineer.thomas_werner.mailbackup;

import javax.mail.Message;
import javax.mail.MessagingException;

/**
 * Filter classes are part of the "Pipes and Filters" pattern. This class is the abstract base class for Filter
 * implementations.
 *
 * @author Thomas Werner
 */
public abstract class Filter {

    protected Pipe pipe;

    /**
     * Appends the given pipe to this Filter.
     * @param pipe the Pipe to be appended
     * @return the Filter at the other end of pipe
     */
    public Filter connect(final Pipe pipe) {
        this.pipe = pipe;
        return pipe.getFilter();
    }

    public Pipe getPipe() {
        return pipe;
    }

    public abstract String getName();

    public abstract void process(final Message message, final MessageContext context) throws MessagingException;

}
