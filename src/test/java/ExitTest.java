package org.os;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExitTest {

    @Test
    void testExecute() {
        // Redirect output to capture it
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Create a real Scanner object
        Scanner scanner = new Scanner(System.in);
        // Create an instance of Exit with the real Scanner and set shouldExit to false
        Exit exitCommand = new Exit(scanner, false);
        // Call the execute method
        exitCommand.execute();
        // Capture the output
        String output = outputStream.toString().trim();
        // Check that the output matches the expected message
        assertEquals("Exiting the CLI.", output, "The output did not match the expected message.");
        scanner.close();
    }
}
