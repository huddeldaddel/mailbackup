package engineer.thomas_werner.mailbackup.output;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmlFileNameBuilder {

    public static final String DEFAULT_PATTERN = "<date> - <sender> - <recipient> - <subject>";

    public static final String PLACEHOLDER_DATE = "<date>";
    public static final String PLACEHOLDER_SENDER = "<sender>";
    public static final String PLACEHOLDER_RECIPIENT = "<recipient>";
    public static final String PLACEHOLDER_SUBJECT = "<subject>";

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private String fileNamePattern;

    public String getFileNamePattern() {
        return fileNamePattern;
    }

    public void setFileNamePattern(String fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
    }

    /**
     * Builds constructs a valid file name based on the user defined pattern and the properties of the message. The
     * file name will have only valid characters and a max. length of 255 chars.
     *
     * @param message the message that contains the various properties to be used in the file name pattern
     * @return a valid file name
     * @throws MessagingException in case that the message properties cannot be accessed
     */
    public String buildFileName(final Message message) throws MessagingException {
        // Replace placeholders of the fileNamePattern
        String temp = getFileNamePattern().replace(PLACEHOLDER_DATE, getMessageDate(message));
        temp = temp.replace(PLACEHOLDER_SENDER, getSender(message));
        temp = temp.replace(PLACEHOLDER_RECIPIENT, getRecipient(message));
        temp = temp.replace(PLACEHOLDER_SUBJECT, message.getSubject());

        // Replace all (possible) invalid characters
        temp = temp.replaceAll("[^a-zA-Z0-9\\.\\-\\ @]", "_");

        // restrict to max. 255 characters length
        if (temp.length() > 250)
            temp.subSequence(0, 250);

        // append .eml suffix
        return temp += ".eml";
    }

    /**
     * Formats the date of the message (received date or sent date).
     *
     * @param message the message that contains the date property
     * @return the message's date as string
     * @throws MessagingException in case that the properties cannot be accessed
     */
    private String getMessageDate(Message message) throws MessagingException {
        Date date = message.getReceivedDate();
        if (null == date)
            date = message.getSentDate();
        return null == date ? "" : dateFormat.format(date);
    }

    /**
     * Returns the first sender of the message - or an empty string of there is none.
     *
     * @param message the message that contains the sender
     * @return the first sender of the message
     * @throws MessagingException when the sender cannot be extracted
     */
    private static String getSender(Message message) throws MessagingException {
        return getFirstAddress(message.getFrom());
    }

    /**
     * Returns the first recipient of the message - or an empty string of there is none.
     *
     * @param message the message that contains the recipient
     * @return the first recipient of the message
     * @throws MessagingException when the recipient cannot be extracted
     */
    private static String getRecipient(Message message) throws MessagingException {
        return getFirstAddress(message.getAllRecipients());
    }

    /**
     * Returns the the first Address from an array of Addresses. Returns an empty string of the array is empty or null.
     *
     * @param addresses an array of Address objects
     * @return the first element of the array as String
     */
    private static String getFirstAddress(Address[] addresses) {
        if (null != addresses && addresses.length > 0) {
            final String temp = addresses[0].toString();
            final Matcher m = Pattern.compile("\\<(.+?)\\>").matcher(temp);
            if (m.find())
                return m.group(1);
            return temp;
        }
        return "";
    }

}
