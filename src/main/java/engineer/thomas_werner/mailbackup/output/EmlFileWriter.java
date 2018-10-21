package engineer.thomas_werner.mailbackup.output;

import engineer.thomas_werner.mailbackup.input.MessageLoadedListener;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EmlFileWriter implements MessageLoadedListener {

    private final EmlFileNameBuilder fileNameBuilder;
    private boolean flattenStructure = false;
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

    public boolean isFlattenStructure() {
        return flattenStructure;
    }

    public void setFlattenStructure(boolean value) {
        flattenStructure = value;
    }

    @Override
    public void messageLoaded(final Message message, final String folderName) throws MessagingException {
        try {
            if (!Files.exists(getOutputFolder())) {
                Files.createDirectories(getOutputFolder());
            }

            final File path;
            if(flattenStructure) {
                path = Paths.get(
                        getOutputFolder().toString(),
                        fileNameBuilder.buildFileName(message)
                ).toFile();
            } else {
                path = Paths.get(
                        getOutputFolder().toString(),
                        folderName.replaceAll("\\.", File.separator),
                        fileNameBuilder.buildFileName(message)
                ).toFile();
            }

            if(!path.getParentFile().exists())
                path.getParentFile().mkdirs();

            message.writeTo(new FileOutputStream(path));
        } catch (IOException iox) {
            throw new MessagingException("Unable to write message to output directory: " + iox.getMessage(), iox);
        }
    }

}