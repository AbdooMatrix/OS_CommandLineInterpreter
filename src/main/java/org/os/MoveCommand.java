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





public class MoveCommand {

    // applying singleton design pattern.

    // Step 2: Create a private static instance of the class
    private static MoveCommand instance ;


    // Step 1: Make the constructor private
    private MoveCommand(){}


    // Step 3: Provide a public static method for getting the instance
    public static MoveCommand getInstance() {

        // Check if the instance is null (not yet created)
        if (instance == null) {

            // If null, create a new instance of MoveCommand
            instance = new MoveCommand();
        }

        // Return the existing or newly created instance
        return instance;
    }



    // the move method is the same
    public void move(String sourcePath, String destinationPath) throws IOException {
        File source = new File(sourcePath);
        File destination = new File(destinationPath);

        // Check if the source exists before moving
        if (!source.exists()) {
            throw new IOException("Source file or directory doesn't exist");
        }

        // Check for moving a directory into itself
        if (source.isDirectory() && destination.getPath().startsWith(source.getPath())) {
            throw new IOException("Cannot move a directory inside itself.");
        }

        // Move the source file/directory to the destination
        Files.move(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

        System.out.println("\nMoved successfully from (" + sourcePath + ") to (" + destinationPath + ")\n");
    }
}




