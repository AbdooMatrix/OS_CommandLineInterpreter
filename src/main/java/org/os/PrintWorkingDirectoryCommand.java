package org.os;


// pwd --> print working directory.

public class PrintWorkingDirectoryCommand
{
    // This method demonstrates how to print the current working directory in Java
    public String pwd(){

        // Retrieve the current working directory using the system property "user.dir"
        // System.getProperty("user.dir") returns the absolute path of the current directory as a String
        String currentDirectory = System.getProperty("user.dir");


        return currentDirectory ;

    }
}
