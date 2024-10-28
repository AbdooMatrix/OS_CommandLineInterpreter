package org.os;

import java.io.IOException;
import java.util.Scanner;

public class CommandLineInterpreter {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the CLI. Type 'exit' to quit.");

        while (true) {
            PWDCommand currDirec = new PWDCommand(); // always printing current working directory.
            System.out.print(currDirec.pwd()+" --> ");
            String input = scanner.nextLine().trim();

            // Check for output redirection
            String[] commandParts = input.split(">"); // first filter split according to the middle operator

            String commandInput = commandParts[0].trim(); // first part of command ex : ls -r
            String filePath = commandParts.length > 1 ? commandParts[1].trim() : null; // second part will be the fill path

            // Split command input into tokens
            String[] tokens = commandInput.split("\\s+");
            if (tokens.length == 0) continue;

            String command = tokens[0];
            String output = "";

            if ("exit".equals(command))
            {
                System.out.println("Exiting CLI.");
                break;
            }
            else if ("help".equals(command))
            {
                output = getHelpText(); // Call method to get help text
            }
            else if ("ls".equals(command))
            {
                if (tokens.length == 1) {
//                    output = new LsCommand().listFiles(".");
                }
                else if ("-a".equals(tokens[1])) {
                    output = new LsACommand().listAllFiles(tokens.length > 2 ? tokens[2] : ".");
                }
                else if ("-r".equals(tokens[1])) {
                    output = new LsRCommand().listFilesReversed(tokens.length > 2 ? tokens[2] : ".");
                }
                else {
                    output = "Error: Unsupported 'ls' option. Supported options: '-a' (all files), '-r' (reverse order).\n";
                }
            }
            else if("mv".equals(command)){
                String srcPath = tokens[1] , destPath = tokens[2] ;
                moveCommand mv = new moveCommand();
                mv.move(srcPath, destPath);
            }
            else if ("touch".equals(command)) {
                output = new TouchCommand().createOrUpdateFile(tokens.length > 1 ? tokens[1] : null);
            }
            else {
                output = "Error: Command not recognized. Type 'help' for a list of commands.\n";
            }

            // Redirect output if ">" is specified; otherwise, print to console
            if (filePath != null)
            {
                OutputRedirector.handleOutput(output, filePath);
            }
            else {
                System.out.print(output);
            }
        }

        scanner.close();
    }

    // Method to return help text
    private static String getHelpText() {
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
