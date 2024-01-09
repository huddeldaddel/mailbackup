package engineer.thomas_werner.mailbackup;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;

/**
 *
 * @author Thomas Werner
 */
public class MessageMock extends Message {

    @Override
    public Address[] getFrom() throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setFrom() throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setFrom(Address adrs) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addFrom(Address[] adrss) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Address[] getRecipients(RecipientType rt) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setRecipients(RecipientType rt, Address[] adrss) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addRecipients(RecipientType rt, Address[] adrss) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getSubject() throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setSubject(String string) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Date getSentDate() throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setSentDate(Date date) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Date getReceivedDate() throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Flags getFlags() throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setFlags(Flags flags, boolean bln) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Message reply(boolean bln) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveChanges() throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getSize() throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getLineCount() throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getContentType() throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isMimeType(String string) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getDisposition() throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setDisposition(String string) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getDescription() throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setDescription(String string) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getFileName() throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setFileName(String string) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public InputStream getInputStream() throws IOException, MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DataHandler getDataHandler() throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getContent() throws IOException, MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setDataHandler(DataHandler dh) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setContent(Object o, String string) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setText(String string) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setContent(Multipart mltprt) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void writeTo(OutputStream out) throws IOException, MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String[] getHeader(String string) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setHeader(String string, String string1) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addHeader(String string, String string1) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeHeader(String string) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Enumeration<Header> getAllHeaders() throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Enumeration<Header> getMatchingHeaders(String[] strings) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Enumeration<Header> getNonMatchingHeaders(String[] strings) throws MessagingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
