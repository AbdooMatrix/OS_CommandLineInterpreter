package org.os;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


// mv --> You can use the mv command to move files or directories
//        from one location to another within the filesystem.

// Path Example
// C:\Users\\username\\Desktop\\example.txt (on Windows).
// but in java \ is an escape character so we need  \\ instead of \

public class moveCommand {
    public void move(String sourcePath, String destinationPath) throws IOException {

        // Create File objects for source and destination paths
        File source = new File(sourcePath);
        File destination = new File(destinationPath);

        // Check if the source file or directory exists
        if (!source.exists()) {
            // If the source does not exist, throw an IOException with a custom error message
            throw new IOException("Source file or directory doesn't exist");
        }

        // Check if the source is a directory and if the destination is inside the source
        // This check prevents moving a directory inside itself, which would cause an error
        if (source.isDirectory() && destination.getAbsolutePath().startsWith(source.getAbsolutePath())) {
            throw new IOException("Cannot move a directory inside itself.");
        }

        // Move the source file/directory to the destination
        // StandardCopyOption.REPLACE_EXISTING option will replace the file at the destination if it exists
        Files.move(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

        // Print a success message if the move operation completes without exceptions
        System.out.println("\nMoved successfully from (" + sourcePath + ") to (" + destinationPath + ")\n");
    }
}



