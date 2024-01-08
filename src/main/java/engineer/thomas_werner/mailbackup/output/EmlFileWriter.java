package engineer.thomas_werner.mailbackup.output;

import engineer.thomas_werner.mailbackup.Filter;
import engineer.thomas_werner.mailbackup.MessageContext;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EmlFileWriter extends Filter {

    private final EmlFileNameBuilder fileNameBuilder;
    private final boolean flattenStructure;
    private final Path outputFolder;

    public EmlFileWriter(EmlFileNameBuilder fileNameBuilder, Path outputFolder, boolean flattenStructure) {
        this.fileNameBuilder = fileNameBuilder;
        this.outputFolder = outputFolder;
        this.flattenStructure = flattenStructure;
    }

    @Override
    public String getName() {
        return "EmlFileWriter";
    }

    @Override
    public void process(final Message message, final MessageContext context) throws MessagingException {
        try {
            if (!Files.exists(outputFolder))
                Files.createDirectories(outputFolder);

            final File path = buildPath(message, context.getFolderName());

            if(!path.getParentFile().exists())
                path.getParentFile().mkdirs();

            message.writeTo(new FileOutputStream(path));

            if(null != pipe) {
                pipe.process(message, context);
            }
        } catch (IOException iox) {
            throw new MessagingException("Unable to write message to output directory: " + iox.getMessage(), iox);
        }
    }

    private File buildPath(final Message message, final String folderName) throws MessagingException {
        final File result;
        if (flattenStructure) {
            result = Paths.get(
                    outputFolder.toString(),
                    fileNameBuilder.buildFileName(message)
            ).toFile();
        } else {

            result = Paths.get(
                    outputFolder.toString(),
                    new OutputFormatter().replaceFolderPathSeparator(folderName),
                    fileNameBuilder.buildFileName(message)
            ).toFile();
        }
        return result.exists() ? buildPath(message, folderName, 1) : result;
    }

    private File buildPath(final Message message, final String folderName, final int idx) throws MessagingException {
        final File result;
        if(flattenStructure) {
            result = Paths.get(
                    outputFolder.toString(),
                    fileNameBuilder.buildFileName(message, Integer.toString(idx))
            ).toFile();
        } else {
            result = Paths.get(
                    outputFolder.toString(),
                    new OutputFormatter().replaceFolderPathSeparator(folderName),
                    fileNameBuilder.buildFileName(message, Integer.toString(idx))
            ).toFile();
        }

        return result.exists() ? buildPath(message, folderName, idx +1) : result;
    }

}