package org.os;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLineInterpreter {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the CLI. Type 'exit' to quit.");

        String currDir = PWDCommand.getInstance().getCurrentDirec();

        loop:
        while (true) {
            System.out.print(currDir + " --> ");
            String input = scanner.nextLine().trim();

            // Split command input based on ">>" first, then ">" to handle both cases
            String[] commandParts = input.split(">>");
            String commandInput = commandParts[0].trim(); // Command part
            String filePath = null;

            // Check for output redirection using ">"
            if (commandParts.length > 1) {
                filePath = commandParts[1].trim(); // Output file if redirected with ">>"
            } else {
                // Check for single ">"
                commandParts = input.split(">");
                if (commandParts.length > 1) {
                    filePath = commandParts[1].trim(); // Output file if redirected with ">"
                }
            }

            // Split command input into tokens while handling paths with spaces
            List<String> tokens = parseCommandInput(commandInput);

            if (tokens.isEmpty()) continue;

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
                        try {
                            MoveCommand.getInstance().move(srcPath, destPath);
                            output = "Moved successfully from (" + srcPath + ") to (" + destPath + ")\n";
                        } catch (IOException e) {
                            output = "Error: " + e.getMessage() + "\n";
                        }
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

            // Redirect output if ">" or ">>" is specified; otherwise, print to console
            if (filePath != null) {
                if (input.contains(">>")) {
                    // Handle appending to the file
                    try {
                        new AppendToFileOperator().appendToFile(filePath, output);
                    } catch (IOException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                } else if (input.contains(">")) {
                    // Handle overriding the file
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                        writer.write(output);
                    } catch (IOException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
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
                // Toggle the insideQuotes flag if we encounter a quote
                insideQuotes = !insideQuotes;
            } else if (c == ' ' && !insideQuotes) {
                // If we encounter a space and we're not inside quotes, finalize the current token
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0); // Reset the token
                }
            } else {
                // Add characters to the current token
                currentToken.append(c);
            }
        }

        // Add the last token if it's non-empty
        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }

        return tokens;
    }

}