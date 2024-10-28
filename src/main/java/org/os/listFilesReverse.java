package org.os;
import java.nio.file.Paths ;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;



// ls-r --> reverses the listing order of files and directories, which can be useful
//          when you want to see items in the opposite order of default sorting (e.g., reverse alphabetical).

public class listFilesReverse
{
    public void lsR() {
        try {
            // Specify the directory path (current directory)
            String directoryPath = ".";

            // List all files and directories in the specified path
            List<Path> files = Files.list(Paths.get(directoryPath)) // Create a stream of paths in the directory
                    // Sort the paths in reverse order by their file names
                    .sorted((path1, path2) -> path2.getFileName().compareTo(path1.getFileName()))
                    // Collect the sorted paths into a list
                    .toList();

            // Print the sorted file names to the console
            for (Path file : files) {
                System.out.println(file.getFileName()); // Print each file's name
            }
        } catch (IOException e) {
            // Handle any I/O exceptions that may occur during the directory reading process
            System.err.println("Error reading directory: " + e.getMessage()); // Print the error message to the standard error stream
        }
    }
}
