import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.os.OutputRedirector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OutputRedirectorTest {

    // Sample file path for testing
    private final String testOutputFile = "testOutput.txt";

    // Sample output content for testing
    private final String sampleOutput = "This is a test output.";
    private final String appendedOutput = "This is appended output.";

    // Clean up any files created during tests
    @AfterEach
    public void tearDown() {
        File file = new File(testOutputFile);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testRedirectOutputToFile() throws IOException {
        // Redirect output to a file (overwrite)
        OutputRedirector.redirectOutput(sampleOutput, testOutputFile);

        // Check that the file was created and contains the correct content
        File file = new File(testOutputFile);
        assertTrue(file.exists(), "Output file should be created.");

        // Read the file content and check it matches the output
        String fileContent = new String(Files.readAllBytes(Paths.get(testOutputFile)));
        assertEquals(sampleOutput, fileContent, "File content should match the redirected output.");
    }

    @Test
    public void testAppendOutputToFile() throws IOException {
        // First, write initial content to the file
        OutputRedirector.redirectOutput(sampleOutput, testOutputFile);

        // Append output to the same file
        OutputRedirector.appendOutput(appendedOutput, testOutputFile);

        // Read the file content
        String fileContent = new String(Files.readAllBytes(Paths.get(testOutputFile)));

        // Check that the content is correctly appended
        assertEquals(sampleOutput + appendedOutput, fileContent,
                "File content should match the appended output.");
    }

    @Test
    public void testOutputRedirectionToNullFile() {
        // Test when the outputFile is null (nothing should happen)
        OutputRedirector.redirectOutput(sampleOutput, null);

        // Check that no file is created
        File file = new File(testOutputFile);
        assertFalse(file.exists(), "Output file should not be created when outputFile is null.");
    }
}
