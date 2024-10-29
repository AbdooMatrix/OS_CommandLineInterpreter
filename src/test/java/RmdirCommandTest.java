package org.os;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RmdirCommandTest {
    private RmdirCommand rmdirCommand;
    private Path testDir;

    @BeforeEach
    void setUp() throws IOException {
        rmdirCommand = new RmdirCommand(); // Initialize the RmdirCommand object.
        testDir = Paths.get("testDir"); // Create a Path object for the directory we want to test.

        // Create the test directory if it doesn't exist.
        if (!Files.exists(testDir)) {
            Files.createDirectory(testDir); // Create the directory.
        }
    }
    @AfterEach
    void tearDown() throws IOException {
        // Clean up the test directory after each test
        if (Files.exists(testDir)) {
            Files.delete(testDir); // Delete the directory to clean up.
        }
    }
// I test here if the rmdir method correctly removes an empty directory
    @Test
    void testRemoveEmptyDirectory() throws IOException {
        // Call the rmdir method on the empty directory
        String result = rmdirCommand.rmdir(testDir.toString()); // Call the method to remove the directory.
        // Check if the result matches the expected output
        assertEquals("Removed directory: " + testDir.toString(), result); // Assert that the output is correct.
    }
    // I test here if the rmdir method correctly doesn't remove a non-empty directory
    @Test
    void testRemoveNonEmptyDirectory() throws IOException {
        // Create a file inside the test directory to make it non-empty
        Path filePath = testDir.resolve("file.txt");
        Files.createFile(filePath);
        // Call the rmdir method on the non-empty directory
        String result = rmdirCommand.rmdir(testDir.toString());
        assertEquals(testDir.toString() + " is not empty.", result);
        // Clean up the created file
        Files.delete(filePath);
    }
    // I test here if the rmdir method Tests that trying to remove a directory that does not exist
    // returns the correct message.
    @Test
    void testRemoveNonExistentDirectory() {
        // Call the rmdir method on a non-existent directory
        String result = rmdirCommand.rmdir("nonExistentDir");
        assertEquals("Directory does not exist: nonExistentDir", result);
    }
   //I Test here trying to remove a file that has the same name as a directory correctly reports
   // that the path is not a directory.
    @Test
    void testRemoveFileInsteadOfDirectory() throws IOException {
        // Create a file with a different name than the directory
        Path filePath = Paths.get("testDirFile"); // Change the name to avoid conflict
        Files.createFile(filePath); // Create the file.
        // Call the rmdir method on the file
        String result = rmdirCommand.rmdir(filePath.toString());
        assertEquals(filePath.toString() + " is not a directory.", result);
        // Clean up the created file
        Files.delete(filePath);
    }

}
