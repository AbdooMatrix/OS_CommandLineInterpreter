package org.os;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MkdirCommandTest {
    private MkdirCommand mkdirCommand;
    private String testDirName;

    @BeforeEach
    void setUp() {
        mkdirCommand = new MkdirCommand();
        testDirName = "testDir"; // Use a consistent test directory name
    }

    @AfterEach
    void tearDown() {
        // Clean up the test directory after each test
        File dir = new File(testDirName);
        if (dir.exists()) {
            dir.delete(); // Delete the test directory
        }
    }

    @Test
    void testCreateNewDirectory() {
        String result = mkdirCommand.execute(testDirName);
        assertTrue(result.startsWith("Directory created at:"),
                "Expected a successful directory creation message.");
    }

    @Test
    void testDirectoryAlreadyExists() {
        mkdirCommand.execute(testDirName); // Create it first
        String result = mkdirCommand.execute(testDirName); // Attempt to create it again
        assertTrue(result.startsWith("Directory already exists:"),
                "Expected a message indicating the directory already exists.");
    }

    @Test
    void testCreateDirectoryWithInvalidPath() {
        String result = mkdirCommand.execute("invalid<>dir");
        assertEquals("Failed to create directory", result,
                "Expected failure for invalid directory name.");
    }
}

