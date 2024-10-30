import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.os.ExitCommand;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExitCommandTest {

    @Test
    void testExecute() {
        // Redirect output to capture it
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Create a real Scanner object
        Scanner scanner = new Scanner(System.in);
        // Create an instance of Exit with the real Scanner and set shouldExit to false
        ExitCommand exitCommand = new ExitCommand(scanner, false);
        // Call the execute method
        exitCommand.execute();
        // Capture the output
        String output = outputStream.toString().trim();
        // Check that the output matches the expected message
        assertEquals("Exiting the CLI.", output, "The output did not match the expected message.");
        scanner.close();
    }
}
