import org.os.CatCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
class CatCommandTest {
    private Path tempDir;
    private Path tempFile1;
    private Path tempFile2;
    private Path exampleFile1;
    private Path exampleFile2;

    @BeforeEach
    public void setUp() throws IOException {
        // Create a temporary directory
        tempDir = Files.createTempDirectory("testDir");
        // Create temporary test files with content
        tempFile1 = createTempFileWithContent("text1.txt", "I am a Student");
        tempFile2 = createTempFileWithContent("text2.txt", "studying computer science");
        exampleFile1 = createTempFileWithContent("example.txt", "Hello donia");
        exampleFile2 = createTempFileWithContent("example2.txt", "kareem mohammed");

    }

    @AfterEach
    public void tearDown() throws IOException {
        // Delete the temporary directory and files
        Files.deleteIfExists(tempFile1);
        Files.deleteIfExists(tempFile2);
        Files.deleteIfExists(exampleFile1);
        Files.deleteIfExists(exampleFile2);
        Files.deleteIfExists(tempDir);
    }

    private Path createTempFileWithContent(String fileName, String content) throws IOException {
        Path filePath = tempDir.resolve(fileName);
        Files.writeString(filePath, content);
        return filePath;
    }

    @Test
    public void testCatWithValidFiles() {
        CatCommand catCommand = new CatCommand();
        String[] filePaths = { tempFile1.toString(), tempFile2.toString() };
        String result = catCommand.cat(filePaths);
        System.out.println("Result from cat: " + result); // expected output
        assertTrue(result.contains("I am"), "Should contain 'I am' from text1.txt");
        assertTrue(result.contains("a Student"), "Should contain 'a Student' from text1.txt");
        assertTrue(result.contains("studying"), "Should contain 'studying' from text2.txt");
        assertTrue(result.contains("computer science"), "Should contain 'computer science' from text2.txt");
    }

    @Test
    public void testCatWithFileNamesOnly() {
        CatCommand catCommand = new CatCommand();
        String[] filePaths = { exampleFile1.toString(), exampleFile2.toString() };
        String result = catCommand.cat(filePaths);
        System.out.println("Result from cat: " + result);// expected output
        assertTrue(result.contains("Hello"), "Should contain 'Hello' from example.txt");
        assertTrue(result.contains("donia"), "Should contain 'donia' from example.txt");
        assertTrue(result.contains("kareem"), "Should contain 'kareem' from example2.txt");
        assertTrue(result.contains("mohammed"), "Should contain 'mohammed' from example2.txt");
    }

    @Test
    public void testCatWithInvalidFilePath() {
        CatCommand catCommand = new CatCommand();
        String[] filePaths = { "nonexistent_file" };
        String result = catCommand.cat(filePaths);
        System.out.println("Result from cat: " + result); // expected output
        assertTrue(result.contains("Error reading file"), "Should report error for missing file");
    }

    @Test
    public void testCatWithEmptyFilePath() {
        CatCommand catCommand = new CatCommand();
        String[] filePaths = { "", exampleFile2.toString() };
        String result = catCommand.cat(filePaths);
        System.out.println("Result from cat: " + result); // expected output
        assertTrue(result.contains("Error: No file path provided for one of the files."));
        assertTrue(result.contains("kareem"), "Should contain 'kareem' from example2.txt");
        assertTrue(result.contains("mohammed"), "Should contain 'mohammed' from example2.txt");
    }
}
