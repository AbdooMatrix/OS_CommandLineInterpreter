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

        basePath = Files.createTempDirectory("testDir");
        CdCommand.cw = basePath.toString();
    }

    @Test
    void testChangeToValidDirectory() {

        Path testDirectoryPath = basePath.resolve("testDirectory");
        new File(testDirectoryPath.toString()).mkdir();

        String result = CdCommand.cd("testDirectory");
        assertEquals(testDirectoryPath.toString(), result);
    }

    @Test
    void testStayInCurrentDirectory() {
        String result = CdCommand.cd(".");
        assertEquals(basePath.toString(), result);
    }

    @Test
    void testGoAboveRoot() {
        CdCommand.cw = "C:\\";
        String result = CdCommand.cd("..");
        assertEquals("Cannot go above root directory.", result);
    }

    @Test
    void testGoUpDirectory() {

        Path parentPath = basePath.getParent();
        String result = CdCommand.cd("..");
        assertEquals(parentPath.toString(), result);
    }

    @Test
    void testChangeToInvalidDirectory() {
        String result = CdCommand.cd("invalidDir");
        assertEquals("Incorrect path: invalidDir", result);
    }
}


