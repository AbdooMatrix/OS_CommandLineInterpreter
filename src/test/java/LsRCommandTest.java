import org.os.LsRCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LsRCommandTest {
    private LsRCommand command;
    private Path testDirectory;

    @BeforeEach
    void setUp() throws IOException {
        command = new LsRCommand();

        // Specify the parent directory for the temporary directory
        Path parentDirectory = Path.of("E:\\testDirectory");

        // Ensure the parent directory exists
        if (!Files.exists(parentDirectory)) {
            Files.createDirectories(parentDirectory);
        }

        // Create a temporary directory in the specified location
        testDirectory = Files.createTempDirectory(parentDirectory, "testDirectory_");

        // Create test files in the directory
        Files.createFile(testDirectory.resolve("fileB.txt"));
        Files.createFile(testDirectory.resolve("fileA.txt"));
        Files.createFile(testDirectory.resolve("fileC.txt"));
    }

    @AfterEach
    void tearDown() throws IOException {
        // Clean up the test directory and its contents after each test
        List<Path> files = Files.list(testDirectory).collect(Collectors.toList());
        for (Path path : files) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Files.deleteIfExists(testDirectory);
    }

    @Test
    void testListFilesReversed() {
        // Pass the path of the created temporary directory
        String result = command.listFilesReversed(testDirectory.toString());

        String expected = "\nFiles in reverse order:\nfileC.txt\nfileB.txt\nfileA.txt\n\n";

        assertEquals(expected, result, "The two output files must be equal");
    }

}
