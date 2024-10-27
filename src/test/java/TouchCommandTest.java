import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.os.TouchCommand;

import java.io.File;

public class TouchCommandTest {

    // Instance of TouchCommand to run tests on
    private final TouchCommand touchCommand = new TouchCommand();

    // Sample file path for testing
    private final String testFilePath = "testFile.txt";

    // Clean up any files created during tests
    @AfterEach
    public void tearDown() {
        File file = new File(testFilePath);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testCreateNewFile() {
        // Test creating a new file
        String result = touchCommand.createOrUpdateFile(testFilePath);

        // Check that the file was created
        File file = new File(testFilePath);
        assertTrue(file.exists(), "File should be created.");
        assertEquals("Created new file: " + testFilePath + "\n", result, "Should return success message for file creation.");
    }

    @Test
    public void testUpdateExistingFileTimestamp() throws InterruptedException {
        // Ensure file exists before updating
        File file = new File(testFilePath);
        touchCommand.createOrUpdateFile(testFilePath);

        // Record the last modified time
        long initialModifiedTime = file.lastModified();

        // Wait a bit to ensure the timestamp update is noticeable
        Thread.sleep(1000);

        // Test updating the timestamp of an existing file
        String result = touchCommand.createOrUpdateFile(testFilePath);
        long updatedModifiedTime = file.lastModified();

        // Check that the timestamp has been updated
        assertTrue(updatedModifiedTime > initialModifiedTime, "Timestamp should be updated.");
        assertEquals("Updated timestamp of file: " + testFilePath + "\n", result, "Should return success message for timestamp update.");
    }

    @Test
    public void testIOExceptionForInvalidPath() {
        // Provide an invalid path (e.g., a directory that doesn't exist)
        String invalidPath = "/invalid/testFile.txt";
        String result = touchCommand.createOrUpdateFile(invalidPath);

        // Check that the output contains an IO error message
        assertTrue(result.startsWith("Error: An IOException occurred:"), "Should return error message for invalid path.");
    }
}
