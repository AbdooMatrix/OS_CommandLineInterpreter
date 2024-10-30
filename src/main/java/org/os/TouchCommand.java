package org.os;

import java.io.File;
import java.io.IOException;

public class TouchCommand {

    // Method to create a new file or update an existing file's timestamp
    public String createOrUpdateFile(String path) {
        if (path == null || path.trim().isEmpty()) {
            return "Error: 'touch' command requires a file name or path.\n";
        }

        // Create a File object for the given path
        File file = new File(path);

        // StringBuilder to store the result message
        StringBuilder output = new StringBuilder();

        try {
            // Check if the file already exists
            if (file.exists()) {
                // If file exists, update its last modified timestamp
                boolean success = file.setLastModified(System.currentTimeMillis());

                // Append appropriate success or error message
                if (success) {
                    output.append("Updated timestamp of file: ").append(path).append("\n");
                } else {
                    output.append("Error: Could not update the timestamp for the file: ").append(path).append("\n");
                }
            } else {
                // If file doesn't exist, create a new file
                boolean created = file.createNewFile();

                // Append appropriate success or error message
                if (created) {
                    output.append("Created new file: ").append(path).append("\n");
                } else {
                    output.append("Error: Could not create the file: ").append(path).append("\n");
                }
            }
        } catch (IOException e) {
            // Handle any IO exceptions that occur
            output.append("Error: An IOException occurred while accessing ").append(path).append(": ").append(e.getMessage()).append("\n");
        }

        // Return the output message
        return output.toString();
    }
}
