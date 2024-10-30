package org.os;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLineInterpreter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the CLI. Type 'exit' to quit.");

        String currDir = PWDCommand.getInstance().getCurrentDirec();
        HelpCommand helpCommand = new HelpCommand(); // Create instance of HelpCommand

        loop:
        while (true) {
            System.out.print(currDir + " --> ");
            String input = scanner.nextLine().trim();

            // Check if input contains a pipe and handle accordingly
            if (input.contains("|")) {
                PipeCommand.runPipe(input); // Run pipe command processing
                continue;
            }

            // Split command input based on ">>" first, then ">" to handle both cases
            String[] commandParts = input.split(">>");
            String commandInput = commandParts[0].trim(); // Command part
            String filePath = null;

            if (commandParts.length > 1) {
                filePath = commandParts[1].trim(); // Output file if redirected with ">>"
            } else {
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
                    Exit exitCommand = new Exit(scanner, true); // Set shouldExit to true to allow exiting
                    exitCommand.execute();
                    break loop;

                case "help":
                    output = helpCommand.getHelpText(); // Call HelpCommand instance
                    break;

                case "ls":
                    output = handleLsCommand(tokens, currDir);
                    break;

                case "rm":
                    output = handleRmCommand(tokens);
                    break;

                case "rmdir": // Add this case for rmdir
                    output = handleRmdirCommand(tokens);
                    break;

                case "mv":
                    output = handleMvCommand(tokens);
                    break;

                case "touch":
                    output = new TouchCommand().createOrUpdateFile(tokens.size() > 1 ? tokens.get(1) : null);
                    break;

                case "cat":
                    output = handleCatCommand(tokens);
                    break;

                case "cd":
                    currDir = CdCommand.cd(tokens.size() > 1 ? tokens.get(1) : currDir);
                    break;

                case "mkdir":
                    output = new MkdirCommand().execute(tokens.size() > 1 ? tokens.get(1) : currDir);
                    break;

                default:
                    output = "Error: Command not recognized. Type 'help' for a list of commands.\n";
                    break;
            }

            // Redirect output if ">" or ">>" is specified; otherwise, print to console
            if (filePath != null) {
                if (input.contains(">>")) {
                    OutputRedirector.appendOutput(output, filePath);
                } else if (input.contains(">")) {
                    OutputRedirector.redirectOutput(output, filePath);
                }
            } else {
                System.out.print(output);
            }
        }

        scanner.close();
    }

    private static String handleLsCommand(List<String> tokens, String currDir) {
        if (tokens.size() == 1) {
            return LsCommand.listDirectory(currDir);
        } else if ("-a".equals(tokens.get(1))) {
            return LsCommand.listAllFiles(tokens.size() > 2 ? tokens.get(2) : currDir);
        } else if ("-r".equals(tokens.get(1))) {
            return LsCommand.listFilesReversed(tokens.size() > 2 ? tokens.get(2) : currDir);
        } else {
            return LsCommand.listDirectory(tokens.size() == 2 ? tokens.get(1) : currDir);
        }
    }

    private static String handleRmCommand(List<String> tokens) {
        if (tokens.size() < 2) {
            return "Error: 'rm' requires a path.\n";
        }
        String pathToDelete = tokens.get(1);
        return new RmCommand().execute(pathToDelete);
    }

    private static String handleRmdirCommand(List<String> tokens) {
        if (tokens.size() < 2) {
            return "Error: 'rmdir' requires a directory path.\n";
        }
        String dirPathToDelete = tokens.get(1);
        return new RmdirCommand().rmdir(dirPathToDelete);
    }

    private static String handleMvCommand(List<String> tokens) {
        if (tokens.size() < 3) {
            return "Error: 'mv' requires source and destination paths.\n";
        }
        String srcPath = tokens.get(1);
        String destPath = tokens.get(2);
        try {
            MoveCommand.getInstance().move(srcPath, destPath);
            return "Moved successfully from (" + srcPath + ") to (" + destPath + ")\n";
        } catch (IOException e) {
            return "Error: " + e.getMessage() + "\n";
        }
    }

    private static String handleCatCommand(List<String> tokens) {
        String[] filePaths = new String[tokens.size() - 1];
        for (int i = 1; i < tokens.size(); i++) {
            filePaths[i - 1] = tokens.get(i).trim();
        }
        return new CatCommand().cat(filePaths);
    }

    private static List<String> parseCommandInput(String commandInput) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < commandInput.length(); i++) {
            char c = commandInput.charAt(i);

            if (c == '"') {
                insideQuotes = !insideQuotes;
            } else if (c == ' ' && !insideQuotes) {
                if (!currentToken.isEmpty()) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }
            } else {
                currentToken.append(c);
            }
        }

        if (!currentToken.isEmpty()) {
            tokens.add(currentToken.toString());
        }

        return tokens;
    }
}
