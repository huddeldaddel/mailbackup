package engineer.thomas_werner.mailbackup.output;

import java.io.File;

public class OutputFormatter {

    /**
     * The folder name of IMAP is usually given as the full path, each folder separated by dots. This function replaces
     * the dots with the given separator String.
     *
     * @param folderName the IMAP foldername
     * @return foldername having dots replaced with separators
     */
    public String replaceFolderPathSeparator(final String folderName) {
        return replaceFolderPathSeparator(folderName, File.separator);
    }

    /**
     * The folder name of IMAP is usually given as the full path, each folder separated by dots. This function replaces
     * the dots with the given separator String.
     *
     * @param folderName the IMAP foldername
     * @param separator the new separator charater
     * @return foldername having dots replaced with separators
     */
    public String replaceFolderPathSeparator(final String folderName, final String separator) {
        final StringBuilder result = new StringBuilder();
        for(char c: folderName.toCharArray())
            result.append('.' == c ? separator : c);
        return result.toString();
    }

}
