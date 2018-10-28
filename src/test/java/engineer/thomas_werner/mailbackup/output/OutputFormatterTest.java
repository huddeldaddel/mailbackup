package engineer.thomas_werner.mailbackup.output;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutputFormatterTest {

    private OutputFormatter outputFormatter;

    @BeforeEach
    public void setUp() {
        outputFormatter = new OutputFormatter();
    }

    @Test
    public void replaceFolderPathSeparatorLinuxStyle() {
        assertEquals("INBOX", outputFormatter.replaceFolderPathSeparator("INBOX", "/"));
        assertEquals("INBOX/Test1/Test2", outputFormatter.replaceFolderPathSeparator("INBOX.Test1.Test2", "/"));
    }

    @Test
    public void replaceFolderPathSeparatorWindowsStyle() {
        assertEquals("INBOX", outputFormatter.replaceFolderPathSeparator("INBOX", "\\"));
        assertEquals("INBOX\\Test1\\Test2", outputFormatter.replaceFolderPathSeparator("INBOX.Test1.Test2", "\\"));
    }

}
