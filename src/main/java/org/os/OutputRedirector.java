package org.os;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class OutputRedirector {

    // Method to handle output redirection to a specified file
    public static void handleOutput(String output, String outputFile) {
        // Check if an output file is specified
        if (outputFile != null) {
            // Try-with-resources to handle file writing and automatically close resources
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
                // Write the output to the specified file
                writer.print(output);

                // Print a confirmation message to the console
                System.out.println("Output redirected to " + outputFile);
            } catch (IOException e) {
                // Handle any IOExceptions that occur during file writing
                System.out.println("Error: Unable to write to file " + outputFile);
            }
        }
    }
}
