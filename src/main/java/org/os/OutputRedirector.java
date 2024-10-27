package org.os;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class OutputRedirector {

    public static void handleOutput(String output, String outputFile) {
        if (outputFile != null) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
                writer.print(output);
                System.out.println("Output redirected to " + outputFile);
            } catch (IOException e) {
                System.out.println("Error: Unable to write to file " + outputFile);
            }
        }
    }
}
