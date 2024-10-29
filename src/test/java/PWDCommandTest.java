import org.junit.jupiter.api.Test;
import org.os.PWDCommand;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PWDCommandTest {


    @Test
    void testPWD(){
        PWDCommand command = PWDCommand.getInstance() ;

        String currentDir = command.getCurrentDirec() ;
        String expectedDir = System.getProperty("user.dir");

        assertEquals(expectedDir,currentDir , "the current working directory should be the same as the expected directory");

    }



}
