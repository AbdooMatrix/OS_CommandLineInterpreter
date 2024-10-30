package org.os;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CdCommandTest {

    @BeforeEach
    void setUp() {
        // Set the initial working directory to a known test directory
        CdCommand.cw = "C:\\Users\\Ts\\IdeaProjects\\os1"; // Change this to your desired starting path
    }

    @Test
    void testChangeToValidDirectory() {
        new File(CdCommand.cw, "testDirectory").mkdir(); // Create a test directory for the test
        String result = CdCommand.cd("testDirectory");
        assertEquals("C:\\Users\\Ts\\IdeaProjects\\os1\\testDirectory", result);
    }

    @Test
    void testStayInCurrentDirectory() {
        String result = CdCommand.cd(".");
        assertEquals("C:\\Users\\Ts\\IdeaProjects\\os1", result);
    }

    @Test
    void testGoAboveRoot() {
        CdCommand.cw= "C:\\"; // Set to root
        String result = CdCommand.cd("..");
        assertEquals("cannot go above root directory.", result);
    }

    @Test
    void testGoUpDirectory() {
        String result = CdCommand.cd("..");
        assertEquals("C:\\Users\\Ts\\IdeaProjects", result); // Ensure this is correct based on your path structure
    }

    @Test
    void testChangeToInvalidDirectory() {
        String result = CdCommand.cd("invalidDir");
        assertEquals("Incorrect path: invalidDir", result);
    }
}

