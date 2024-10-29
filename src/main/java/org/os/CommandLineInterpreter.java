package org.os;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLineInterpreter {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the CLI. Type 'exit' to quit.");

        String currDir = new PWDCommand().pwd();

        loop:
        while (true) {
            System.out.print(currDir + " --> ");
            String input = scanner.nextLine().trim();

            // Check for output redirection
            String[] commandParts = input.split(">");
            String commandInput = commandParts[0].trim(); // Command part
            String filePath = commandParts.length > 1 ? commandParts[1].trim() : null; // Output file if redirected

            // Split command input into tokens while handling paths with spaces
            List<String> tokens = parseCommandInput(commandInput);

            if (tokens.isEmpty()) continue;

            for (String token : tokens) {
                System.out.println(token + "\n");
            }

            String command = tokens.get(0);
            String output = "";

            switch (command) {
                case "exit":
                    System.out.println("Exiting CLI.");
                    break loop;

                case "help":
                    output = getHelpText();
                    break;

                case "ls":
                    if (tokens.size() == 1) {
                        output = LsCommand.listDirectory(currDir);
                    } else if ("-a".equals(tokens.get(1))) {
                        output = LsCommand.listAllFiles(tokens.size() > 2 ? tokens.get(2) : currDir);
                    } else if ("-r".equals(tokens.get(1))) {
                        output = LsCommand.listFilesReversed(tokens.size() > 2 ? tokens.get(2) : currDir);
                    } else {
                        output = LsCommand.listDirectory(tokens.size() == 2 ? tokens.get(1) : currDir);
                    }
                    break;

                case "rm":
                    if (tokens.size() < 2) {
                        output = "Error: 'rm' requires a path.\n";
                    } else {
                        String pathToDelete = tokens.get(1);
                        output = new RmCommand().execute(pathToDelete);
                    }
                    break;

                case "mv":
                    if (tokens.size() < 3) {
                        output = "Error: 'mv' requires source and destination paths.\n";
                    } else {
                        String srcPath = tokens.get(1);
                        String destPath = tokens.get(2);
                        MoveCommand mv = new MoveCommand();
                        mv.move(srcPath, destPath);
                    }
                    break;

                case "touch":
                    output = new TouchCommand().createOrUpdateFile(tokens.size() > 1 ? tokens.get(1) : null);
                    break;

                case "cat":
                    String[] filePaths = new String[tokens.size() - 1];
                    for (int i = 1; i < tokens.size(); i++) {
                        filePaths[i - 1] = tokens.get(i).trim();
                    }
                    output = new CatCommand().cat(filePaths);
                    break;

                case "cd":
                    currDir = cdCommand.cd(tokens.size() > 1 ? tokens.get(1) : currDir);
                    break;

                case "mkdir":
                    output = new Mkdir().execute(tokens.size() > 1 ? tokens.get(1) : currDir);
                    break;

                case "pipe":
                    // Pipe command logic goes here if needed
                    break;

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
               - rm <file_path>: Removes a file or directory.
               - cat <file_path(s)>: Concatenates and displays content of files.
               - cd <directory>: Changes the current directory.
               - mkdir <directory>: Creates a new directory.
               - pipe <command>: Pipes the output of one command to another.
               - append <file> <text>: Appends text to a specified file.
               - rmdir <directory>: Removes an empty directory.
               
               Usage:
               - To list all files in the current directory: ls
               - To create or update a file named 'example.txt': touch example.txt
               - To redirect output to a file: ls > output.txt
               - To move a file from directory to another : mv
               - To remove a file or directory: rm D:\\New folder\\myfile.txt
               
               Type 'command > file.txt' to redirect any command's output to file.txt.
               """;
    }

    private static List<String> parseCommandInput(String commandInput) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < commandInput.length(); i++) {
            char c = commandInput.charAt(i);

            if (c == '"') {
                // Toggle the insideQuotes flag when encountering double quotes
                insideQuotes = !insideQuotes;

                // If exiting a quoted section, add the token
                if (!insideQuotes && currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }
            } else if (c == ' ' && !insideQuotes) {
                // Add token if it's not inside quotes and there's a space
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }
            } else {
                // Accumulate characters for tokens inside or outside quotes
                currentToken.append(c);
            }
        }

        // Add any remaining token after the loop
        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }

        return tokens;
    }

}
