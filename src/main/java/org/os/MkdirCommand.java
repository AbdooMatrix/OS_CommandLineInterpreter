package org.os;

import java.io.File;

public class MkdirCommand {
    static String cw = System.getProperty("user.dir");

    public String execute(String path) {
        File dir = new File(path);

        // Check if the directory already exists
        if (!dir.exists()) {
            try {
                // Attempt to create the directory
                boolean res = dir.mkdir();
                if (res) {
                    System.out.println("mkdir success");
                    return "Directory created at: " + dir.getAbsolutePath();
                } else {
                    System.out.println("mkdir fail");
                    return "Error: Failed to create directory. Possible reasons include insufficient permissions or an invalid path.";
                }
            } catch (Exception e) {
                System.out.println("Unexpected error during directory creation: " + e.getMessage());
                return "Error: An unexpected error occurred: " + e.getMessage();
            }
        } else {
            System.out.println("Directory already exists with this name.");
            return "Directory already exists: " + dir.getAbsolutePath();
        }
    }
}
