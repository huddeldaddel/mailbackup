package engineer.thomas_werner.mailbackup.output;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleWriterTest {

    private TestConsoleWriter consoleWriter;

    @BeforeEach
    public void setUp() {
        consoleWriter = new TestConsoleWriter();
    }

    @Test
    public void fullLifecycleTest() throws MessagingException {
        consoleWriter.messageLoaded(null, "INPUT");
        consoleWriter.messageLoaded(null, "INPUT");
        consoleWriter.messageLoaded(null, "INPUT.Test");
        consoleWriter.messageLoaded(null, "INPUT.Test");
        consoleWriter.messageLoaded(null, "INPUT.Test");
        consoleWriter.messageLoaded(null, "INPUT.Test.Test");
        consoleWriter.messageLoaded(null, "INPUT.Test.Test");
        consoleWriter.messageLoaded(null, "INPUT.Test.Test");
        consoleWriter.messageLoaded(null, "INPUT.Test.Test");
        consoleWriter.done();

        assertEquals("INPUT: 1", consoleWriter.getOutputList().get(0));
        assertEquals("\rINPUT: 2", consoleWriter.getOutputList().get(1));
        assertEquals("\nINPUT/Test: 1", consoleWriter.getOutputList().get(2));
        assertEquals("\rINPUT/Test: 2", consoleWriter.getOutputList().get(3));
        assertEquals("\rINPUT/Test: 3", consoleWriter.getOutputList().get(4));
        assertEquals("\nINPUT/Test/Test: 1", consoleWriter.getOutputList().get(5));
        assertEquals("\rINPUT/Test/Test: 2", consoleWriter.getOutputList().get(6));
        assertEquals("\rINPUT/Test/Test: 3", consoleWriter.getOutputList().get(7));
        assertEquals("\rINPUT/Test/Test: 4", consoleWriter.getOutputList().get(8));
        assertEquals("\n", consoleWriter.getOutputList().get(9));
    }

    private class TestConsoleWriter extends ConsoleWriter {

        private List<String> outputList;

        public TestConsoleWriter() {
            super(new OutputFormatter() {
                @Override
                public String replaceFolderPathSeparator(final String folderName) {
                    return replaceFolderPathSeparator(folderName, "/");
                }
            });
            outputList = new LinkedList<>();
        }

        public List<String> getOutputList() {
            return Collections.unmodifiableList(outputList);
        }

        @Override
        void printOutput(final String output) {
            outputList.add(output);
        }

    }

}
