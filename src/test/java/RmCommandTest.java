package org.os;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RmCommandTest {
    private RmCommand rmCommand;

    @BeforeEach
    public void setUp() {
        rmCommand = new RmCommand();
        // Create a test directory and file for testing
        new File("testDir").mkdir();
        try {
            new File("testDir/testFile.txt").createNewFile();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

    @AfterEach
    public void tearDown() {
        // Clean up the test directory and files after each test
        rmCommand.execute("testDir/testFile.txt");
        rmCommand.execute("testDir");
    }

    @Test
    public void testDeleteFile() {
        String result = rmCommand.execute("testDir/testFile.txt");
        assertEquals("deleted successfully.", result);
    }

    @Test
    public void testDeleteDirectory() {
        String result = rmCommand.execute("testDir");
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



