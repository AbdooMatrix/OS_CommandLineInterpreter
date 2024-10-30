package org.os;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PipeCommandTest {
    private final String testDirPath = "testDir";

    @BeforeEach
    public void setUp() {
        // Create a temporary test directory before each test
        File testDir = new File(testDirPath);
        if (!testDir.exists()) {
            testDir.mkdir();
        }
    }

    @AfterEach
    public void tearDown() {
        // Delete the test directory after each test
        deleteDirectoryRecursively(new File(testDirPath));
    }

    private void deleteDirectoryRecursively(File dir) {
        File[] allContents = dir.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                if (file.isDirectory()) {
                    deleteDirectoryRecursively(file);
                } else {
                    file.delete();
                }
            }
        }
        dir.delete();
    }

    @Test
    void testRunPipeWithTouchAndCat() throws IOException {
        
        String testFilePath = "testDir/testFilePipe.txt";

        // Use the touch command to create or update the file
        String touchCommand = "touch " + testFilePath;
        String touchOutput = PipeCommand.runPipe(touchCommand);
        assertTrue(touchOutput.contains("Created new file") || touchOutput.contains("Updated timestamp"),
                "Touch command failed to create or update file.");

        // Write some content to the file directly for the test
        try (FileWriter writer = new FileWriter(testFilePath)) {
            writer.write("Hello, this is a test file for cat command.\n");
        }

        // Use the cat command to read the file
        String catCommand = "cat " + testFilePath;
        String catOutput = PipeCommand.runPipe(catCommand);
        assertTrue(catOutput.contains("Hello, this is a test file for cat command."),
                "Cat command did not return the correct file content.");

        // Cleanup: delete the test file
        new File(testFilePath).delete();
    }
    @Test
    public void testRunPipeWithNonExistingCommand() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            PipeCommand.runPipe("unknownCommand");
        });
        String expectedMessage = "Unknown command: unknownCommand";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testRunPipeWithMkdirAndRmdir() {
        PipeCommand.runPipe("mkdir testDir/testSubDir | rmdir testDir/testSubDir");
        // Check if the directory was deleted
        File subDir = new File(testDirPath + "/testSubDir");
        assertFalse(subDir.exists(), "The directory was not deleted as expected."); // Use assertFalse
    }
}


