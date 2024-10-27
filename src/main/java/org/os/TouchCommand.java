package org.os;

import java.io.File;
import java.io.IOException;

public class TouchCommand {

    public String createOrUpdateFile(String path) {
        File file = new File(path);
        StringBuilder output = new StringBuilder();

        try {
            if (file.exists()) {
                // Update last modified timestamp
                boolean success = file.setLastModified(System.currentTimeMillis());
                if (success) {
                    output.append("Updated timestamp of file: ").append(path).append("\n");
                } else {
                    output.append("Error: Could not update timestamp.\n");
                }
            } else {
                // Create new file
                boolean created = file.createNewFile();
                if (created) {
                    output.append("Created new file: ").append(path).append("\n");
                } else {
                    output.append("Error: Could not create file.\n");
                }
            }
        } catch (IOException e) {
            output.append("Error: An IOException occurred: ").append(e.getMessage()).append("\n");
        }

        return output.toString();
    }
}
