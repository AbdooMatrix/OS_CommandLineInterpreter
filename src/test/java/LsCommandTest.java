import org.junit.jupiter.api.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import org.os.LsCommand;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LsCommandTest {
    private static Path testDir;
    @BeforeAll
    static void setUp() throws Exception {
        testDir = Files.createTempDirectory("testDir"); // Create a temporary directory for testing
        // Create some test files and directories
        Files.createFile(testDir.resolve("file1.txt"));
        Files.createFile(testDir.resolve("file2.txt"));
        Path hiddenFile = testDir.resolve(".hiddenfile");// Create hidden file
        Files.createDirectory(testDir.resolve("subdir"));
        Files.createFile(hiddenFile);
        // Set hidden attribute on Windows
        ProcessBuilder pb = new ProcessBuilder("attrib", "+h", hiddenFile.toString());
        pb.start().waitFor(); //ensuring the hidden attribute is applied before any tests are run

    }

    @AfterAll
    static void tearDown() throws Exception {
        // Clean up test files and directories after tests
        Files.walk(testDir)
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    void testListDirectory() {
        String output = LsCommand.listDirectory(testDir.toString());
       // System.out.println("List Directory Output:\n" + output); // to test my output
        assertTrue(output.contains("file1.txt"));
        assertTrue(output.contains("file2.txt"));
        assertTrue(output.contains("subdir"));
        assertTrue(!output.contains(".hiddenfile"));  // Ensure hidden file is not listed
    }


    @Test
    void testListAllFiles() {
        String output = LsCommand.listAllFiles(testDir.toString());
        assertTrue(output.contains("file1.txt"));
        assertTrue(output.contains("file2.txt"));
        assertTrue(output.contains("subdir"));
        assertTrue(output.contains(".hiddenfile"));  // Hidden file should be listed
    }

    @Test
    void testListFilesReversed() {
        String output = LsCommand.listFilesReversed(testDir.toString());
        String[] lines = output.split("\n");
        assertEquals("subdir", lines[0]); // Confirm order is reversed
        assertEquals("file2.txt", lines[1]);
        assertEquals("file1.txt", lines[2]);
    }
}
