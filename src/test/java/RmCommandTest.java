package org.os;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RmCommandTest {
    private RmCommand rmCommand;
    private String testDirPath;
    private String testFilePath;

    @BeforeEach
    public void setUp() {
        rmCommand = new RmCommand();

        testDirPath = new File("testDir").getAbsolutePath();
        testFilePath = new File(testDirPath, "testFile.txt").getAbsolutePath();

        new File(testDirPath).mkdir(); // Create test directory
        try {
            new File(testFilePath).createNewFile(); // Create test file
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

    @AfterEach
    public void tearDown() {
        // Clean up the test directory and files after each test
        if (new File(testFilePath).exists()) {
            rmCommand.execute(testFilePath); // Delete test file
        }
        if (new File(testDirPath).exists()) {
            rmCommand.execute(testDirPath); // Delete test directory
        }
    }

    @Test
    public void testDeleteFile() {
        String result = rmCommand.execute(testFilePath);
        assertEquals("deleted successfully.", result);
    }

    @Test
    public void testDeleteDirectory() {
        String result = rmCommand.execute(testDirPath);
        assertEquals("deleted successfully.", result);
    }

    @Test
    public void testDeleteNonExistingFile() {
        String result = rmCommand.execute("nonExistingFile.txt");
        assertEquals("not exist", result);
    }

    @Test
    public void testDeleteNonExistingDirectory() {
        String result = rmCommand.execute("nonExistingDir");
        assertEquals("not exist", result);
    }
}



