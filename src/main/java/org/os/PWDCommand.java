package org.os;

// pwd --> print working directory.
public class PWDCommand {

    private static PWDCommand instance;

    private PWDCommand() {}

    public static PWDCommand getInstance() {
        if (instance == null) {
            instance = new PWDCommand();
        }
        return instance;
    }

    // This method retrieves the current working directory in Java
    public String getCurrentDirec() {
        try {
            // Retrieve the current working directory using the system property "user.dir"
            // System.getProperty("user.dir") returns the absolute path of the current directory as a String
            String currentDirectory = System.getProperty("user.dir");
            return currentDirectory;
        } catch (Exception e) {
            // Handle any unexpected exceptions
            return "Error: An unexpected error occurred while retrieving the current working directory: " + e.getMessage() + "\n";
        }
    }
}
