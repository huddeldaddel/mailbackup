package engineer.thomas_werner.mailbackup;

import engineer.thomas_werner.mailbackup.input.Loader;
import engineer.thomas_werner.mailbackup.output.*;
import engineer.thomas_werner.mailbackup.input.MinAgeFilter;
import org.apache.commons.cli.*;

import javax.mail.MessagingException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.Console;

import static engineer.thomas_werner.mailbackup.output.EmlFileNameBuilder.DEFAULT_PATTERN;

public class MailbackupApplication {

    private static final String OPT_DELETE = "delete";
    private static final String OPT_FLATTEN = "flatten";
    private static final String OPT_HOSTNAME = "hostname";
    private static final String OPT_NOSSL = "unencrypted";
    private static final String OPT_OLDER_THAN = "olderThan";
    private static final String OPT_OUTPUT_DIR = "out";
    private static final String OPT_OUTPUT_PATTERN = "outpattern";
    private static final String OPT_PASSWORD = "password";
    private static final String OPT_PORT = "port";
    private static final String OPT_USERNAME = "username";

    public void run(String... args) {
        if(args.length == 0) {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.setOptionComparator(null);
            formatter.printHelp("mailbackup", buildCmdOptions(), true);
            return;
        }

        final CommandLineParser parser = new DefaultParser();
        try {
            final CommandLine line = parser.parse(buildCmdOptions(), args);
            if(!isOptionSetComplete(line))
                return;

            try {
                Loader loader = buildPipeline(line);
                loader.start(buildConfiguration(line));
            } catch(final MessagingException me) {
                System.err.println("An error occurred: " + me.getMessage() + "\n" + me.toString());
            }

        } catch(final ParseException | java.text.ParseException exp) {
            System.err.println("Parsing cmd options failed. Reason: " + exp.getMessage());
        }
        System.out.println();
    }

    Loader buildPipeline(final CommandLine line) throws java.text.ParseException {
        final Loader loader = new Loader();

        Filter latestFilter = loader.connect(new Pipe(new ConsoleWriter()));

        if(line.hasOption(OPT_OLDER_THAN)) {
            final String olderThan = line.getOptionValue(OPT_OLDER_THAN);
            final Date lowerDateBound = new SimpleDateFormat("yyyy-MM-dd").parse(olderThan);
            latestFilter = latestFilter.connect(new Pipe(new MinAgeFilter(lowerDateBound)));
        }

        final EmlFileNameBuilder emlFileNameBuilder = new EmlFileNameBuilder();
        emlFileNameBuilder.setFileNamePattern(
                line.hasOption(OPT_OUTPUT_PATTERN)
                        ? line.getOptionValue(OPT_OUTPUT_PATTERN)
                        : DEFAULT_PATTERN
        );
        final EmlFileWriter emlFileWriter = new EmlFileWriter(
                emlFileNameBuilder,
                Paths.get(line.hasOption(OPT_OUTPUT_DIR)
                        ? line.getOptionValue(OPT_OUTPUT_DIR)
                        : System.getProperty("user.dir")
                ),
                line.hasOption(OPT_FLATTEN)
        );
        latestFilter = latestFilter.connect(new Pipe(emlFileWriter));

        if(line.hasOption(OPT_DELETE)) {
            // Here the pipeline ends. To extends more filters latestFilter would have to be updated.
            latestFilter.connect(new Pipe(new MessageRemover()));
        }

        return loader;
    }

    /**
     * Builds the CommandLine options.
     *
     * @return CommandLine options
     */
    private static Options buildCmdOptions() {
        return new Options()
                .addOption(Option.builder(OPT_USERNAME)
                        .hasArg(true)
                        .desc("username")
                        .required(true)
                        .build())
                .addOption(Option.builder(OPT_PASSWORD)
                        .hasArg(true)
                        .desc("password")
                        .required(false)
                        .build())
                .addOption(Option.builder(OPT_HOSTNAME)
                        .hasArg(true)
                        .desc("hostname of the IMAP server")
                        .required(false)
                        .build())
                .addOption(OPT_DELETE, false, "Delete mails from server after download")
                .addOption(OPT_FLATTEN, false, "Will not use subfolders when storing eml files")
                .addOption(OPT_OLDER_THAN, true, "Only process messages up to given date, YYYY-MM-DD")
                .addOption(OPT_OUTPUT_DIR, true, "Directory to write the messages to")
                .addOption(OPT_OUTPUT_PATTERN, true, "Name pattern for the message files. This " +
                        "pattern can include the placeholders " + EmlFileNameBuilder.PLACEHOLDER_DATE + ", " +
                        EmlFileNameBuilder.PLACEHOLDER_RECIPIENT + ", " + EmlFileNameBuilder.PLACEHOLDER_SENDER +
                        " and " + EmlFileNameBuilder.PLACEHOLDER_SUBJECT)
                .addOption(OPT_PORT, true, "Port number of the IMAP server")
                .addOption(OPT_NOSSL, false, "Do not use SSL / TLS encryption");
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
        return true;
    }

    private static Configuration buildConfiguration(final CommandLine line) throws NumberFormatException {
        final Configuration configuration = new Configuration();
        if(line.hasOption(OPT_PORT)) {
            try {
                configuration.setPort(Integer.valueOf(line.getOptionValue(OPT_PORT)));
            } catch(final NumberFormatException ex) {
                System.err.println("The port number has to be a numeric value. Typically 143 or 993.");
                System.exit(-1);
            }
        }

        if(line.hasOption(OPT_HOSTNAME)) {
            configuration.setHost(line.getOptionValue(OPT_HOSTNAME));
        } else {
            final String username = line.getOptionValue(OPT_USERNAME);
            if(0 < username.indexOf("@")) {
                configuration.setHost(username.substring(username.indexOf("@") +1));
            } else {
                System.err.println("Unable to extract the hostname based on username.");
                System.err.println("Please specify hostname parameter explitly");
                System.exit(-1);
            }
        }

        configuration.setUser(line.getOptionValue(OPT_USERNAME));
        if(line.hasOption(OPT_PASSWORD)) {
            configuration.setPassword(line.getOptionValue(OPT_PASSWORD));
        } else {
            final Console console = System.console();
            if (console == null) {
                System.err.println("Please specify a password for login");
                System.exit(-1);
            }

            final char[] password = console.readPassword("Enter password: ");
            configuration.setPassword(String.valueOf(password));

        }
        configuration.setSsl(!line.hasOption(OPT_NOSSL));
        return configuration;
    }

    public static void main(String[] args) {
        new MailbackupApplication().run(args);
    }

}
