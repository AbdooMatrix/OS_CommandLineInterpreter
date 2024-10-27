// Import necessary packages for testing
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.os.LsACommand;

import java.io.File;

public class LsACommandTest {

    // Instance of LsACommand to run tests on
    private final LsACommand lsACommand = new LsACommand();

    @Test
    public void testListAllFiles_ValidDirectory() {
        // Using the current directory (".") as a sample valid path
        String result = lsACommand.listAllFiles(".");

        // Check that the output is not empty (assuming there are files in the current directory)
        assertNotNull(result);
        assertFalse(result.isEmpty(), "Output should not be empty for a valid directory.");

        // Example: Checking if a known file or directory exists in the output
        assertTrue(result.contains("src"), "Output should contain known file/directory names if they exist.");
    }

    @Test
    public void testListAllFiles_NullPath() {
        // Test when the path is null (it should default to the current directory)
        String result = lsACommand.listAllFiles(null);

        assertNotNull(result);
        assertFalse(result.isEmpty(), "Output should not be empty when path is null.");
    }

    @Test
    public void testListAllFiles_InvalidDirectory() {
        // Using an invalid directory path
        String result = lsACommand.listAllFiles("invalid/path");

        // Check that the output contains an error message
        assertEquals("Error: Directory not found or is not a directory.\n", result);
    }
}
