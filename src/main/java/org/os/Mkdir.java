package org.os;

import java.io.File;

public class Mkdir implements command {

    static String cw = System.getProperty("user.dir");

    public String execute(String name) {
        File dir = new File(cw, name);
        if (!dir.exists()) {
            try {
                boolean res = dir.mkdir();
                if (res) {
                    System.out.println("mkdir success");
                    return "Directory created at: " + dir.getAbsolutePath();
                } else {
                    System.out.println("mkdir fail");
                    return "Failed to create directory";
                }
            } catch (SecurityException e) {
                System.out.println("mkdir fail due to security restrictions");
                return "Security error: Cannot create directory";
            }
        } else {
            System.out.println("Directory already exists with this name.");
            return "Directory already exists: " + dir.getAbsolutePath();
        }
    }

        public static void main(String[] args) {
            Mkdir mkdirCommand = new Mkdir();

            System.out.println("Current working directory: " + Mkdir.cw);
            System.out.println(mkdirCommand.execute("testDir"));
            System.out.println(mkdirCommand.execute("testDir"));
            System.out.println(mkdirCommand.execute(""));
            System.out.println(mkdirCommand.execute("nestedDir/subDir"));
        }
    }

