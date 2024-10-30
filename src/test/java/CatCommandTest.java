import org.os.CatCommand;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

//I am testing four cases:
//(1) gives me a pathes and should I go to open the files in this pathes.
//(2) gives me the names of the files only,So I display the content of the files in current path .
//(3) gives me the name of a specific file without the extension .
//(4) gives me an empty file and a full one.

class CatCommandTest {
    @Test
    public void testCatWithValidFiles() {
        CatCommand catCommand = new CatCommand();

        String[] filePaths = {
                "E:\\text1.txt",
                "E:\\text2.txt"
        };
        String result = catCommand.cat(filePaths);
        assertTrue(result.contains("I am"), "Should contain 'I am' from text1.txt");
        assertTrue(result.contains("a Student"), "Should contain 'a Student' from text1.txt");
        assertTrue(result.contains("studying"), "Should contain 'studying' from text2.txt");
        assertTrue(result.contains("computer science"), "Should contain 'computer science' from text2.txt");

    }
    @Test
    public void testCatWithFileNamesOnly() {
        CatCommand catCommand = new CatCommand();
        // Use relative paths to the files for better portability
        String[] filePaths = {
                "example.txt",
                "example2.txt"
        };
        String result = catCommand.cat(filePaths);
        assertTrue(result.contains("Hello"), "Should contain 'Hello' from example.txt");
        assertTrue(result.contains("donia"), "Should contain 'donia' from example.txt");
        assertTrue(result.contains("kareem"), "Should contain 'kareem' from example2.txt");
        assertTrue(result.contains("mohammed"), "Should contain 'mohammed' from example2.txt");
    }

    @Test
    public void testCatWithInvalidFilePath() {
        CatCommand catCommand = new CatCommand();
        String[] filePaths = { "example" };
        String result = catCommand.cat(filePaths);
        // Should report an error for non-existent file
        assertTrue(result.contains("Error reading file"), "Should report error for missing file");
    }

    @Test
    public void testCatWithEmptyFilePath() {
        CatCommand catCommand = new CatCommand();
        String[] filePaths = { "", "example2.txt" };
        String result = catCommand.cat(filePaths);
        // Should show an error for empty path and read from `sample2.txt`
        assertTrue(result.contains("Error: No file path provided for one of the files."));
        assertTrue(result.contains("kareem"), "Should contain 'kareem' from example2.txt");
        assertTrue(result.contains("mohammed"), "Should contain 'mohammed' from example2.txt");
    }


}
