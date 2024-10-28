package org.os;
import java.io.BufferedWriter; // Importing BufferedWriter for efficient writing to files
import java.io.File; // Importing File class to handle file operations
import java.io.FileWriter; // Importing FileWriter for writing to files
import java.io.IOException; // Importing IOException to handle input/output exceptions


// >> --> command in Unix/Linux (and also in Windows Command Prompt) is used for output redirection.
//It allows you to append the output of a command to a file instead of displaying it on the terminal screen.
// If the file does not exist, it will be created.

public class AppendToFileOperator {
    // Method to append text to a specified file
    public void appendToFile(String filePath, String text) throws IOException {
        File file = new File(filePath); // Create a File object with the specified file path

        // Using try-with-resources to ensure proper resource management
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            // Creating a FileWriter with 'true' to enable append mode
            writer.write(text); // Writing the provided text to the file
            writer.newLine(); // Adding a new line after the written text
        }
        catch (IOException e) { // Catching potential IOExceptions
            e.printStackTrace(); // Printing the stack trace for debugging purposes
        }

        System.out.println("success"); // Indicating that the text was successfully appended
    }
}




