package org.os;

import java.io.File;

public class RmCommand {

    public String execute(String path) {
        if (path == null || path.trim().isEmpty()) {
            return "Error: 'rm' command requires a file or directory path or name.\n";
        }

        File file = new File(path);

        if (!file.exists()) {
            return "Error: File or directory does not exist: " + file.getAbsolutePath() + "\n";
        }

        if (file.isDirectory()) {
            if (deleteDirectoryRecursively(file)) {
                return "Successfully deleted directory: " + file.getAbsolutePath() + "\n";
            } else {
                return "Error: Failed to delete directory: " + file.getAbsolutePath() + "\n";
            }
        } else {
            if (file.delete()) {
                return "Successfully deleted file: " + file.getAbsolutePath() + "\n";
            } else {
                return "Error: Failed to delete file: " + file.getAbsolutePath() + "\n";
            }
        }
    }

    private boolean deleteDirectoryRecursively(File dir) {
        File[] allContents = dir.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                if (file.isDirectory()) {
                    deleteDirectoryRecursively(file);
                }
                if (!file.delete()) {
                    System.err.println("Error: Could not delete file: " + file.getAbsolutePath());
                }
            }
        }
        return dir.delete(); // Try to delete the directory after clearing its contents
    }
}
