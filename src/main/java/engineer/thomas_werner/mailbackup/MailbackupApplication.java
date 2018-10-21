package engineer.thomas_werner.mailbackup;

import engineer.thomas_werner.mailbackup.domain.Configuration;
import engineer.thomas_werner.mailbackup.input.Loader;
import engineer.thomas_werner.mailbackup.input.MessageRemover;
import engineer.thomas_werner.mailbackup.input.MinAgeFilter;
import engineer.thomas_werner.mailbackup.output.ConsoleWriter;
import engineer.thomas_werner.mailbackup.output.EmlFileNameBuilder;
import engineer.thomas_werner.mailbackup.output.EmlFileWriter;
import org.apache.commons.cli.*;

import javax.mail.MessagingException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static engineer.thomas_werner.mailbackup.output.EmlFileNameBuilder.DEFAULT_PATTERN;

public class MailbackupApplication {

    private static final String OPT_DELETE = "delete";
    private static final String OPT_OLDER_THAN = "olderThan";
    private static final String OPT_OUTPUT_DIR = "dir";
    private static final String OPT_FLATTEN = "flatten";
    private static final String OPT_OUTPUT_PATTERN = "format";
    private static final String OPT_PASSWORD = "password";
    private static final String OPT_PORT = "port";
    private static final String OPT_SERVER = "hostname";
    private static final String OPT_SSL = "ssl";
    private static final String OPT_USERNAME = "username";

    private final ConsoleWriter consoleWriter;
    private final Loader loader;
    private final EmlFileWriter emlFileWriter;
    private final EmlFileNameBuilder emlFileNameBuilder;

    public MailbackupApplication() {
        consoleWriter = new ConsoleWriter();
        loader =  new Loader();
        emlFileNameBuilder = new EmlFileNameBuilder();
        emlFileWriter = new EmlFileWriter(emlFileNameBuilder);
    }

    public void run(String... args) {
        if(args.length == 0) {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.setOptionComparator(null);
            formatter.printHelp("imap2eml", buildCmdOptions(), true);
            return;
        }

        final CommandLineParser parser = new DefaultParser();
        try {
            final CommandLine line = parser.parse(buildCmdOptions(), args);
            if(!isOptionSetComplete(line))
                return;

            loader.addMessageHandler(consoleWriter);

            emlFileNameBuilder.setFileNamePattern(line.hasOption(OPT_OUTPUT_PATTERN) ? line.getOptionValue(OPT_OUTPUT_PATTERN) : DEFAULT_PATTERN);
            emlFileWriter.setFlattenStructure(line.hasOption(OPT_FLATTEN));
            emlFileWriter.setOutputFolder(Paths.get(line.hasOption(OPT_OUTPUT_DIR) ? line.getOptionValue(OPT_OUTPUT_DIR) : System.getProperty("user.dir")));
            loader.addMessageHandler(emlFileWriter);

            if(line.hasOption(OPT_DELETE))
                loader.addMessageHandler(new MessageRemover());

            if(line.hasOption(OPT_OLDER_THAN)) {
                final String olderThan = line.getOptionValue(OPT_OLDER_THAN);
                final Date lowerDateBound = new SimpleDateFormat("yyyy-MM-dd").parse(olderThan);
                loader.addMessageFilter(new MinAgeFilter(lowerDateBound));
            }
            try {
                loader.start(buildConfiguration(line));
            } catch(final MessagingException me) {
                System.err.println("An error occurred: " + me.getMessage() + "\n" + me.toString());
            }

        } catch(final ParseException | java.text.ParseException exp) {
            System.err.println("Parsing cmd options failed. Reason: " + exp.getMessage());
        }
    }

    /**
     * Builds the CommandLine options.
     *
     * @return CommandLine options
     */
    private static Options buildCmdOptions() {
        return new Options()
                .addOption(Option.builder(OPT_SERVER)
                        .hasArg(true)
                        .desc("hostname of the IMAP server")
                        .required(true)
                        .build())
                .addOption(Option.builder(OPT_USERNAME)
                        .hasArg(true)
                        .desc("user name")
                        .required(true)
                        .build())
                .addOption(Option.builder(OPT_PASSWORD)
                        .hasArg(true)
                        .desc("password")
                        .required(true)
                        .build())
                .addOption(OPT_DELETE, false, "specify to delete mails on the server (after download)")
                .addOption(OPT_FLATTEN, false, "will not use subfolders when storing eml files")
                .addOption(OPT_OLDER_THAN, true, "only process messages up to given date, yyyy-MM-dd")
                .addOption(OPT_OUTPUT_DIR, true, "directory to write the messages to")
                .addOption(OPT_OUTPUT_PATTERN, true, "name pattern for the message files")
                .addOption(OPT_PORT, true, "port number of the IMAP server")
                .addOption(OPT_SSL, false, "use SSL / TLS encryption");
    }

    /**
     * Checks the options passed to the CommandLine. Returns true if all required options are present.
     *
     * @param line the CommandLine that returns the arguments to be checked
     * @return true if all required options are present
     */
    private static boolean isOptionSetComplete(final CommandLine line) {
        if(!line.hasOption(OPT_USERNAME)) {
            System.err.println("Please specify a username for login");
            return false;
        }

        if(!line.hasOption(OPT_PASSWORD)) {
            System.err.println("Please specify a password for login");
            return false;
        }

        if(!line.hasOption(OPT_SERVER)) {
            System.err.println("Please specify the host to be contacted");
            return false;
        }

        return true;
    }

    private static Configuration buildConfiguration(final CommandLine line) throws NumberFormatException {
        final Configuration configuration = new Configuration();
        if(line.hasOption(OPT_PORT)) {
            try {
                configuration.setPort(Integer.parseInt(line.getOptionValue(OPT_PORT)));
            } catch(final NumberFormatException ex) {
                System.err.println("The port number has to be a numeric value. Typically 143 or 993.");
                System.exit(-1);
            }
        }
        configuration.setHost(line.getOptionValue(OPT_SERVER));
        configuration.setUser(line.getOptionValue(OPT_USERNAME));
        configuration.setPassword(line.getOptionValue(OPT_PASSWORD));
        configuration.setSsl(line.hasOption(OPT_SSL));
        return configuration;
    }

    public static void main(String[] args) {
        new MailbackupApplication().run(args);
    }

}
