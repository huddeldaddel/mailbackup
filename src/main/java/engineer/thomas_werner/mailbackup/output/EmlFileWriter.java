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
            if (!Files.exists(getOutputFolder()))
                Files.createDirectories(getOutputFolder());

            final File path = buildPath(message, folderName);

            if(!path.getParentFile().exists())
                path.getParentFile().mkdirs();

            message.writeTo(new FileOutputStream(path));
        } catch (IOException iox) {
            throw new MessagingException("Unable to write message to output directory: " + iox.getMessage(), iox);
        }
    }

    @Override
    public void done() { }

    private File buildPath(final Message message, final String folderName) throws MessagingException {
        final File result;
        if (flattenStructure) {
            result = Paths.get(
                    getOutputFolder().toString(),
                    fileNameBuilder.buildFileName(message)
            ).toFile();
        } else {
            result = Paths.get(
                    getOutputFolder().toString(),
                    folderName.replaceAll("\\.", File.separator),
                    fileNameBuilder.buildFileName(message)
            ).toFile();
        }
        return result.exists() ? buildPath(message, folderName, 1) : result;
    }

    private File buildPath(final Message message, final String folderName, final int idx) throws MessagingException {
        final File result;
        if(flattenStructure) {
            result = Paths.get(
                    getOutputFolder().toString(),
                    fileNameBuilder.buildFileName(message, Integer.toString(idx))
            ).toFile();
        } else {
            result = Paths.get(
                    getOutputFolder().toString(),
                    folderName.replaceAll("\\.", File.separator),
                    fileNameBuilder.buildFileName(message, Integer.toString(idx))
            ).toFile();
        }

        return result.exists() ? buildPath(message, folderName, idx +1) : result;
    }

}