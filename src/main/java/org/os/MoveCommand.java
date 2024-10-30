package org.os;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

// mv --> You can use the mv command to move files or directories
//        from one location to another within the filesystem.

public class MoveCommand {

    // Applying singleton design pattern.
    private static MoveCommand instance;

    // Make the constructor private
    private MoveCommand() {}

    // Provide a public static method for getting the instance
    public static MoveCommand getInstance() {
        if (instance == null) {
            instance = new MoveCommand();
        }
        return instance;
    }

    // Method to move a file or directory
    public void move(String sourcePath, String destinationPath) throws IOException {
        File source = new File(sourcePath);
        File destination = new File(destinationPath);

        // Check if the source exists before moving
        if (!source.exists()) {
            throw new IOException("Source file or directory does not exist: " + sourcePath);
        }

        // Check for moving a directory into itself
        if (source.isDirectory() && destination.getPath().startsWith(source.getPath())) {
            throw new IOException("Cannot move a directory inside itself: " + sourcePath);
        }

        // Create parent directory for the destination if it doesn't exist
        if (destination.getParentFile() != null && !destination.getParentFile().exists()) {
            throw new IOException("Destination directory does not exist: " + destination.getParent());
        }

        // Move the source file/directory to the destination
        try {
            Files.move(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Error moving file/directory from " + sourcePath + " to " + destinationPath + ": " + e.getMessage());
        }
    }
}
