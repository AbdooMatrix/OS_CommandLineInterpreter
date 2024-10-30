package org.os;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;

public class OutputRedirector {

    // Method for handling '>' redirection (overwrite)
    public static void redirectOutput(String output, String outputFile) {
        if (outputFile != null && !outputFile.isEmpty()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
                writer.print(output); // Overwrite the file with the new output
                System.out.println("Output redirected to " + outputFile);
            } catch (IOException e) {
                System.out.println("Error: Unable to write to file " + outputFile + ". " + e.getMessage());
            }
        } else {
            System.out.println("Error: Invalid output file specified for redirection.");
        }
    }

    // Method for handling '>>' redirection (append)
    public static void appendOutput(String output, String outputFile) {
        if (outputFile != null && !outputFile.isEmpty()) {
            File file = new File(outputFile);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(output); // Append the output to the existing file
                writer.newLine(); // Optionally add a newline after the output
                System.out.println("Output appended to " + outputFile);
            } catch (IOException e) {
                System.out.println("Error: Unable to append to file " + outputFile + ". " + e.getMessage());
            }
        } else {
            System.out.println("Error: Invalid output file specified for appending.");
        }
    }
}
