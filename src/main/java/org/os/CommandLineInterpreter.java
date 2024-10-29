package org.os;

import java.io.IOException;
import java.util.Scanner;

public class CommandLineInterpreter {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the CLI. Type 'exit' to quit.");

        String currDirec = new PWDCommand().pwd();

        label:
        while (true) {
            // Always printing the current working directory.
            System.out.print(currDirec + " --> ");
            String input = scanner.nextLine().trim();

            // Check for output redirection
            String[] commandParts = input.split(">");
            String commandInput = commandParts[0].trim(); // Command part, e.g., "ls -r"
            String filePath = commandParts.length > 1 ? commandParts[1].trim() : null; // Output file if redirected

            // Split command input into tokens
            String[] tokens = commandInput.split("\\s+");
            if (tokens.length == 0) continue;

            String command = tokens[0];
            String output = "";

            switch (command) {
                case "exit":
                    System.out.println("Exiting CLI.");
                    break label;
                case "help":
                    output = getHelpText();
                    break;
                case "ls":
                    // Handle "ls" commands with options
                    if (tokens.length == 1) {
                        output = LsCommand.listDirectory(currDirec);
                    } else if ("-a".equals(tokens[1])) {
                        output = LsCommand.listAllFiles(tokens.length > 2 ? tokens[2] : currDirec);
                    } else if ("-r".equals(tokens[1])) {
                        output = LsCommand.listFilesReversed(tokens.length > 2 ? tokens[2] : currDirec);
                    } else if (tokens.length == 2) {
                        output = LsCommand.listDirectory(tokens[1]);
                    } else {
                        output = "Error: Unsupported 'ls' option. Supported options: '-a' (all files), '-r' (reverse order).\n";
                    }
                    break;
                case "mv":
                    String srcPath = tokens[1], destPath = tokens[2];
                    MoveCommand mv = new MoveCommand();
                    mv.move(srcPath, destPath);
                    break;
                case "touch":
                    output = new TouchCommand().createOrUpdateFile(tokens.length > 1 ? tokens[1] : null);
                    break;
                case "cat":
                    String[] filePaths = new String[tokens.length - 1];
                    for (int i = 1; i < tokens.length; i++) {
                        filePaths[i - 1] = tokens[i].trim();
                    }
                    output = new CatCommand().cat(filePaths);
                    break;
                case "cd":
                    currDirec = new cdCommand().cd(tokens.length > 1 ? tokens[1] : null);
                    break;
                case "mkdir":
                    output = new Mkdir().execute(tokens.length > 1 ? tokens[1] : currDirec);
                    break;
                case "pipe":
                    StringBuilder commandline = null;
                    for (int i = 1; i < tokens.length; i++) {
                        commandline.append(tokens[i]).append(" | ");
                    }
                    PipeCommand pipe = new PipeCommand();
                    break;
                case null:
                default:
                    output = "Error: Command not recognized. Type 'help' for a list of commands.\n";
                    break;
            }

            // Redirect output if ">" is specified; otherwise, print to console
            if (filePath != null) {
                OutputRedirector.handleOutput(output, filePath);
            } else {
                System.out.print(output);
            }
        }

        scanner.close();
    }

    // Function to return help text
    public static String getHelpText() {
        return """
               Available commands:
               - help: Displays this help message.
               - exit: Exits the CLI.
               - ls [path]: Lists files in the specified directory (default is current directory).
               - ls -a [path]: Lists all files, including hidden ones, in the specified directory.
               - ls -r [path]: Lists files in reverse order in the specified directory.
               - touch <file>: Creates a new file or updates the timestamp of an existing file.
               - mv [sourcePath] [destinationPath]: move a file from a specific directory to another.
               
               Usage:
               - To list all files in the current directory: ls
               - To create or update a file named 'example.txt': touch example.txt
               - To redirect output to a file: ls > output.txt
               - To move a file from directory to another : mv
               
               Type 'command > file.txt' to redirect any command's output to file.txt.
               """;
    }
}
