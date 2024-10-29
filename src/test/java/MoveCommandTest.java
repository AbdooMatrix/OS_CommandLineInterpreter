import org.junit.jupiter.api.AfterEach;
import org.os.MoveCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static org.junit.jupiter.api.Assertions.*;



// The @BeforeEach annotation in JUnit is used to define a method that will be executed before each test
// in the test class. This setup method is commonly used to initialize or prepare objects,
// files, databases, or other resources needed by the tests, ensuring a
// consistent starting state for each test case.


// The @AfterEach annotation in JUnit is used to define a method that runs after each test in the test class.
// It is typically used for cleanup tasks, like deleting temporary files, closing database connections,
// or freeing up resources initialized in the @BeforeEach method.
// This helps ensure that each test case leaves the environment in a clean state, ready for the next test.


//@Test
//Purpose: Marks a method as a test case.
//Usage: Methods with the @Test annotation are
// recognized as test cases and are automatically run by the testing framework.

//assertTrue
//Usage: Verifies that a condition is true. If the condition is false, the test fails.

//assertFalse
//Usage: Verifies that a condition is false. If the condition is true, the test fails


// assertEquals is an assertion method used to check if two values are equal.
// If the values are not equal, the assertion fails, and JUnit will report a test failure.
// This method is essential for verifying that your code behaves as expected.



class MoveCommandTest {
    private MoveCommand mv;
    private File sourceFile;
    private File destinationFile;

    @BeforeEach
    void setUp() throws IOException {
        mv = MoveCommand.getInstance() ;

        // Initialize the files and directories with absolute paths
        sourceFile = new File("test1.txt");
        destinationFile = new File("test2.txt");

        // Create files and handling potential IOException
        if (!sourceFile.exists()) {
            sourceFile.createNewFile();
        }
    }

    @AfterEach
    void tearDown() {
        try {
            if (sourceFile != null && sourceFile.exists()) {
                Files.deleteIfExists(sourceFile.toPath());
            }
            if (destinationFile != null && destinationFile.exists()) {
                Files.deleteIfExists(destinationFile.toPath());
            }
        } catch (IOException e) {
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }

    @Test
    void testMoveFileSuccessfully() throws IOException {
        // Move the file
        mv.move(sourceFile.toPath().toString(), destinationFile.toPath().toString());

        // Verify that the source file no longer exists and the destination file exists
        assertFalse(sourceFile.exists(), "Source file should not exist after move");
        assertTrue(destinationFile.exists(), "Destination file should exist after move");
    }


    @Test
    void testMoveNonExistentFile() {
        // Attempt to move a non-existent file and expect an IOException
        File nonExistentFile = new File("nonExistentFile.txt");
        IOException exception = assertThrows(IOException.class, () ->
                mv.move(nonExistentFile.getPath(), "someDestination.txt"));

        assertEquals("Source file or directory doesn't exist", exception.getMessage());
    }
}
