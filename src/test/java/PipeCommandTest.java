package org.os;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
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


