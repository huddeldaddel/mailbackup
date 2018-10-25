package engineer.thomas_werner.mailbackup.input;

import engineer.thomas_werner.mailbackup.domain.Configuration;

import javax.mail.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Connects to the specified IMAP server using the specified login credentials. Lists all e-mails that are stored in the
 * various folders of the server and passes them to the registered MessageHandlers.
 *
 * @author Thomas Werner
 */
public class Loader {

    private final List<MessageLoadedListener> messageHandlers = new LinkedList<>();
    private final List<MessageFilter> filters = new LinkedList<>();

    public void addMessageHandler(final MessageLoadedListener handler) {
        messageHandlers.add(handler);
    }

    public void removeMessageHandler(final MessageLoadedListener handler) {
        messageHandlers.remove(handler);
    }

    public void addMessageFilter(final MessageFilter filter) {
        filters.add(filter);
    }

    public void removeMessageFilter(final MessageFilter filter) {
        filters.remove(filter);
    }

    public void start(final Configuration conf) throws MessagingException {
        final String protocol = conf.isSsl() ? "imaps" : "imap";
        final Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", protocol);
        final Session session = Session.getDefaultInstance(props, null);
        Store store = null;
        try {
            System.out.println("Connecting " + conf.getHost() + " as " + conf.getUser());

            store = session.getStore(protocol);
            try {
                if(conf.getPort().isPresent()) {
                    store.connect(conf.getHost(), conf.getPort().get(), conf.getUser(), conf.getPassword());
                } else {
                    store.connect(conf.getHost(), conf.getUser(), conf.getPassword());
                }
            } catch(final MessagingException me) {
                System.err.println("Unable to connect to " + conf.getHost() + " as " + conf.getUser() + ": " + me.getMessage());
                return;
            }

            processSubFolders(store.getDefaultFolder(), true);
        } finally {
            if(null != store)
                store.close();

            for(final MessageLoadedListener handler: messageHandlers) {
                handler.done();
            }
        }
    }

    private boolean passesFilters(final Message message) throws MessagingException {
        boolean result = true;
        for(MessageFilter filter: filters)
            result = result && filter.passes(message);
        return result;
    }

    private void downloadMessages(final Folder folder) throws MessagingException {
        for(int m=1; m<=folder.getMessageCount(); m++) {
            final Message message = folder.getMessage(m);
            if(passesFilters(message))
                processMessage(folder, message);
        }
    }

    private void processMessage(final Folder folder, final Message message) throws MessagingException {
        for(final MessageLoadedListener handler: messageHandlers) {
            try {
                handler.messageLoaded(message, folder.getFullName());
            } catch(final Exception ex) {
                System.err.println("The message " + message.getSubject() + " cannot be processed: " + ex.getMessage());
            }
        }
    }

    private void processSubFolders(final Folder folder, final boolean isRoot) throws MessagingException {
        if (!isRoot) {
            try {
                folder.open(Folder.READ_WRITE);
                downloadMessages(folder);
            } finally {
                if(folder.isOpen())
                    folder.close(true);
            }
        }

        for(final Folder subFolder: folder.list())
            processSubFolders(subFolder, false);
    }

}
