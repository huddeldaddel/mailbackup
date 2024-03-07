package engineer.thomas_werner.mailbackup.input;

import engineer.thomas_werner.mailbackup.Filter;
import engineer.thomas_werner.mailbackup.Configuration;
import engineer.thomas_werner.mailbackup.MessageContext;
import java.util.Optional;

import javax.mail.*;
import java.util.Properties;

/**
 * Connects to the specified IMAP server using the specified login credentials. Lists all e-mails that are stored in the
 * various folders of the server and passes them to the registered MessageHandlers.
 *
 * @author Thomas Werner
 */
public class Loader extends Filter {

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
                final Optional<Integer> port = conf.getPort();
                if(port.isPresent()) {
                    store.connect(conf.getHost(), port.get(), conf.getUser(), conf.getPassword());
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
        }
    }

    private void downloadMessages(final Folder folder) throws MessagingException {
        final int messageCount = folder.getMessageCount();
        for(int m=1; m<=messageCount; m++) {
            final Message message = folder.getMessage(m);
            if(null != pipe) {
                pipe.process(message, new MessageContext(folder.getFullName(), m -1, messageCount));
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


    @Override
    public String getName() {
        return "Loader";
    }

    @Override
    public void process(final Message message, final MessageContext context) throws MessagingException {
        throw new UnsupportedOperationException("Not supported.");
    }

}
