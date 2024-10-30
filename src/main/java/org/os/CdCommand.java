package org.os;

import java.io.File;

public class CdCommand {

    public static String cw;


    public static String cd(String path) {
        if (path.matches("^[a-zA-Z]:\\\\.*")) {
            File dir = new File(path);
            if (dir.exists() && dir.isDirectory()) {
                cw = dir.getAbsolutePath();
            } else {
                return "Incorrect path: " + path;
            }
        } else {
            File current = new File(cw);
            if (path.equals("..")) {
                if (current.getParent() != null) {
                    cw = current.getParent();
                } else {
                    return "Cannot go above root directory.";
                }
            } else if (path.equals(".")) {
                return cw;
            } else {
                File dir = new File(current, path);
                if (dir.exists() && dir.isDirectory()) {
                    cw = dir.getAbsolutePath();
                } else if (!dir.exists()) {
                    return "Incorrect path: " + path;
                } else {
                    return "Not a directory: " + path;
                }
            }
        }
        return cw;
    }

}

