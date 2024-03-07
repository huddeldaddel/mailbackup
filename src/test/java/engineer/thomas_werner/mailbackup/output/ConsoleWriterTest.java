package engineer.thomas_werner.mailbackup.output;

import engineer.thomas_werner.mailbackup.FilterMock;
import engineer.thomas_werner.mailbackup.MessageContext;
import engineer.thomas_werner.mailbackup.Pipe;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for ConsoleWriter;
 *
 * @author Thomas Werner
 */
class ConsoleWriterTest {

    @Test
    void testOutput() throws Exception {
        final TestConsoleWriter writer = new TestConsoleWriter();
        writer.process(null, new MessageContext("INPUT", 0, 2));
        writer.process(null, new MessageContext("INPUT", 1, 2));
        writer.process(null, new MessageContext("INPUT.Test", 0, 3));
        writer.process(null, new MessageContext("INPUT.Test", 1, 3));
        writer.process(null, new MessageContext("INPUT.Test", 2, 3));
        writer.process(null, new MessageContext("INPUT.Test.Test", 0, 4));
        writer.process(null, new MessageContext("INPUT.Test.Test", 1, 4));
        writer.process(null, new MessageContext("INPUT.Test.Test", 2, 4));
        writer.process(null, new MessageContext("INPUT.Test.Test", 3, 4));


        assertEquals("\rINPUT: 1/2", writer.output.get(0));
        assertEquals("\rINPUT: 2/2", writer.output.get(1));
        assertEquals("\n", writer.output.get(2));
        assertEquals("\rINPUT/Test: 1/3", writer.output.get(3));
        assertEquals("\rINPUT/Test: 2/3", writer.output.get(4));
        assertEquals("\rINPUT/Test: 3/3", writer.output.get(5));
        assertEquals("\n", writer.output.get(6));
        assertEquals("\rINPUT/Test/Test: 1/4", writer.output.get(7));
        assertEquals("\rINPUT/Test/Test: 2/4", writer.output.get(8));
        assertEquals("\rINPUT/Test/Test: 3/4", writer.output.get(9));
        assertEquals("\rINPUT/Test/Test: 4/4", writer.output.get(10));
    }

    @Test
    void testPipeline() throws Exception {
        final TestConsoleWriter writer = new TestConsoleWriter();
        final FilterMock filterMock = new FilterMock();
        writer.connect(new Pipe(filterMock));

        assertEquals(0, filterMock.getInvokationCount());
        writer.process(null, new MessageContext("INPUT", 0, 2));
        assertEquals(1, filterMock.getInvokationCount());
        writer.process(null, new MessageContext("INPUT", 1, 2));
        assertEquals(2, filterMock.getInvokationCount());
    }

    private class TestConsoleWriter extends ConsoleWriter {

        final List<String> output = new LinkedList<>();

        @Override
        void printOutput(final String output) {
            this.output.add(output);
        }

    }

}