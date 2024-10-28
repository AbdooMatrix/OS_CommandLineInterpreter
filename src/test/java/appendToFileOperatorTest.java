import org.os.appendToFileOperator ;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class appendToFileOperatorTest {

    private appendToFileOperator fileOperator;
    private String testFilePath;

    @BeforeEach
    public void setUp() {
        fileOperator = new appendToFileOperator();
        testFilePath = "testFile.txt"; // Temporary file for testing
    }

    @AfterEach
    public void tearDown() {
        // Delete the test file after each test to clean up
        File testFile = new File(testFilePath);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testAppendToFile() throws IOException {
        String textToAppend = "Hello, World!";
        fileOperator.appendToFile(testFilePath, textToAppend); // Call the method to test

        // Verify that the text was appended to the file
        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {
            String line = reader.readLine(); // Read the first line
            assertEquals(textToAppend, line); // Check if it matches the appended text
        }
    }

    @Test
    public void testAppendMultipleLines() throws IOException {
        String firstLine = "First line";
        String secondLine = "Second line";

        fileOperator.appendToFile(testFilePath, firstLine);
        fileOperator.appendToFile(testFilePath, secondLine);

        // Verify that both lines are in the file
        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {
            assertEquals(firstLine, reader.readLine()); // Check the first line
            assertEquals(secondLine, reader.readLine()); // Check the second line
        }
    }

    @Test
    public void testFileCreation() throws IOException {
        String textToAppend = "Checking file creation";

        fileOperator.appendToFile(testFilePath, textToAppend); // Append to file

        // Verify that the file was created
        File testFile = new File(testFilePath);
        assertTrue(testFile.exists(), "The test file should exist after appending text.");
    }
}
