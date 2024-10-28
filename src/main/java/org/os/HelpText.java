package org.os;

public class HelpText {

    // Method to return help text
    public static String getHelpText() {
        return """
               Available commands:
               - help: Displays this help message.
               - exit: Exits the CLI.
               - ls [path]: Lists files in the specified directory (default is current directory).
               - ls -a [path]: Lists all files, including hidden ones, in the specified directory.
               - ls -r [path]: Lists files in reverse order in the specified directory.
               - touch <file>: Creates a new file or updates the timestamp of an existing file.
               
               Usage:
               - To list all files in the current directory: ls
               - To create or update a file named 'example.txt': touch example.txt
               - To redirect output to a file: ls > output.txt
               
               Type 'command > file.txt' to redirect any command's output to file.txt.
               """;
    }
}
