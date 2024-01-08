package engineer.thomas_werner.mailbackup;

/**
 * Provides context information about a Message.
 *
 * @author Thomas Werner
 */
public class MessageContext {

    private final String folderName;
    private final int messageIndex;
    private final int folderMessageCount;

    public MessageContext(java.lang.String folderName, int messageIndex, int folderMessageCount) {
        this.folderName = folderName;
        this.messageIndex = messageIndex;
        this.folderMessageCount = folderMessageCount;
    }

    /**
     * @return the folderName
     */
    public String getFolderName() {
        return folderName;
    }

    /**
     * @return the index of the message inside the given folder
     */
    public int getMessageIndex() {
        return messageIndex;
    }

    /**
     * @return total number of messages in the given folder
     */
    public int getFolderMessageCount() {
        return folderMessageCount;
    }

}
