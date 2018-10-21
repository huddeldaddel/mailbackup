package engineer.thomas_werner.mailbackup.output;

import engineer.thomas_werner.mailbackup.input.MessageLoadedListener;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class EmlFileWriter implements MessageLoadedListener {

    private static final Logger LOGGER = Logger.getLogger(EmlFileWriter.class.getName());

    private final EmlFileNameBuilder fileNameBuilder;
    private Path outputFolder;

    public EmlFileWriter(final EmlFileNameBuilder fileNameBuilder) {
        this.fileNameBuilder = fileNameBuilder;
    }

    public Path getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(Path outputFolder) {
        this.outputFolder = outputFolder;
    }

    @Override
    public void messageLoaded(final Message message, final String folderName) throws MessagingException {
        try {
            if (!Files.exists(getOutputFolder())) {
                if (LOGGER.isLoggable(Level.INFO))
                    LOGGER.info("Creating output directory: " + getOutputFolder().toString());
                Files.createDirectories(getOutputFolder());
            }
            final File path = Paths.get(getOutputFolder().toString(), fileNameBuilder.buildFileName(message)).toFile();
            if (LOGGER.isLoggable(Level.INFO))
                LOGGER.info("Writing file to output directory: " + path.getAbsolutePath());
            message.writeTo(new FileOutputStream(path));
        } catch (IOException iox) {
            throw new MessagingException("Unable to write message to output directory", iox);
        }
    }

}