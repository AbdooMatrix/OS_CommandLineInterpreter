package org.os;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CdCommandTest {

    private Path basePath;

    @BeforeEach
    void setUp() throws IOException {
        // Create a temporary directory for testing
        basePath = Files.createTempDirectory("testDir");
        CdCommand.cw = basePath.toString(); // Set the current working directory to the temporary base path
    }

    @Test
    void testChangeToValidDirectory() {
        // Create a test directory for the test
        Path testDirectoryPath = basePath.resolve("testDirectory");
        new File(testDirectoryPath.toString()).mkdir(); // Create directory

        String result = CdCommand.cd("testDirectory");
        assertEquals(testDirectoryPath.toString(), result);
    }

    @Test
    void testStayInCurrentDirectory() {
        String result = CdCommand.cd(".");
        assertEquals(basePath.toString(), result); // Use basePath for assertion
    }

    @Test
    void testGoAboveRoot() {
        CdCommand.cw = "C:\\"; // Set to root
        String result = CdCommand.cd("..");
        assertEquals("cannot go above root directory.", result);
    }

    @Test
    void testGoUpDirectory() {
        // Calculate the parent directory based on the base path
        Path parentPath = basePath.getParent(); // Go up one directory
        String result = CdCommand.cd("..");
        assertEquals(parentPath.toString(), result); // Use parentPath for assertion
    }

    @Test
    void testChangeToInvalidDirectory() {
        String result = CdCommand.cd("invalidDir");
        assertEquals("Incorrect path: invalidDir", result);
    }
}


