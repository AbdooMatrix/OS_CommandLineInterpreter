package org.os;

import java.io.IOException;
import java.util.Scanner;

public class CommandLineInterpreter {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the CLI. Type 'exit' to quit.");

        String currDirec = new PWDCommand().pwd();

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

            if ("exit".equals(command)) {
                System.out.println("Exiting CLI.");
                break;
            } else if ("help".equals(command)) {
                output = HelpText.getHelpText();
            } else if ("ls".equals(command)) {
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
            } else if ("mv".equals(command)) {
                String srcPath = tokens[1], destPath = tokens[2];
                MoveCommand mv = new MoveCommand();
                mv.move(srcPath, destPath);
            } else if ("touch".equals(command)) {
                output = new TouchCommand().createOrUpdateFile(tokens.length > 1 ? tokens[1] : null);
            } else if ("cat".equals(command)) {
                String[] filePaths = new String[tokens.length - 1];
                for (int i = 1; i < tokens.length; i++) {
                    filePaths[i - 1] = tokens[i].trim();
                }
                output = new CatCommand().cat(filePaths);
            } else if ("cd".equals(command)) {
                currDirec = new cdCommand().cd(tokens.length > 1 ? tokens[1] : null);
            } else {
                output = "Error: Command not recognized. Type 'help' for a list of commands.\n";
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
}
