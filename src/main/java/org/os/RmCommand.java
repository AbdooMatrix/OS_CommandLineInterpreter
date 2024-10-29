package org.os;

import java.io.File;

public class RmCommand {
    static String cw = System.getProperty("user.dir");

    public String execute(String path) {
        // Check if the path is absolute or relative
        File file = new File(path);
        if (!file.isAbsolute()) {
            file = new File(cw, path); // Convert relative path to absolute path based on `cw`
        }

        if (!file.exists()) {
            System.out.println("not exist: " + file.getAbsolutePath());
            return "not exist";
        }

        if (file.isDirectory()) {
            if (deleteDirectoryRecursively(file)) {
                System.out.println("Successfully deleted: " + file.getAbsolutePath());
                return "deleted successfully.";
            } else {
                System.out.println("Failed to delete: " + file.getAbsolutePath());
                return "Failed to delete";
            }
        } else {
            if (file.delete()) {
                System.out.println("Successfully deleted: " + file.getAbsolutePath());
                return "deleted successfully.";
            } else {
                System.out.println("Failed to delete: " + file.getAbsolutePath());
                return "Failed to delete";
            }
        }
    }

    private boolean deleteDirectoryRecursively(File dir) {
        File[] allContents = dir.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                if (file.isDirectory()) {
                    deleteDirectoryRecursively(file);
                } else {
                    file.delete();
                }
            }
        }
        return dir.delete();
    }
}
