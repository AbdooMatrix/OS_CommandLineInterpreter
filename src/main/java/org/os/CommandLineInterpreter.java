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
        HelpCommand helpCommand = new HelpCommand();

        loop:
        while (true) {
            System.out.print(currDir + " --> ");
            String input = scanner.nextLine().trim();

            // Handle pipe commands
            if (input.contains("|")) {
                try {
                    PipeCommand.runPipe(input);
                } catch (Exception e) {
                    System.out.println("Error with pipe command: " + e.getMessage());
                }
                continue;
            }

            // Split the input by redirection operators ">>" or ">"
            String[] commandParts = input.split(">>");
            String commandInput = commandParts[0].trim();
            List<String> filePathTokens = null;

            if (commandParts.length > 1) {
                filePathTokens = parseCommandInput(commandParts[1].trim());
            } else {
                commandParts = input.split(">");
                if (commandParts.length > 1) {
                    filePathTokens = parseCommandInput(commandParts[1].trim());
                }
            }

            // Parse command input and get command tokens
            List<String> tokens = parseCommandInput(commandInput);
            if (tokens.isEmpty()) continue;

            // Get the command and prepare for execution
            String command = tokens.get(0);
            String output = "";

            try {
                switch (command) {
                    case "exit":
                        ExitCommand exitCommand = new ExitCommand(scanner, true);
                        exitCommand.execute();
                        break loop;

                    case "help":
                        output = helpCommand.getHelpText();
                        break;

                    case "ls":
                        output = handleLsCommand(tokens, currDir);
                        break;

                    case "rm":
                        output = handleRmCommand(tokens);
                        break;

                    case "rmdir":
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
                        continue loop;

                    case "mkdir":
                        output = new MkdirCommand().execute(tokens.size() > 1 ? tokens.get(1) : currDir);
                        break;

                    default:
                        output = "Error: Command not recognized. Type 'help' for a list of commands.\n";
                        break;
                }
            } catch (Exception e) {
                output = "An error occurred while executing the command: " + e.getMessage() + "\n";
            }

            // Handle output redirection if filePathTokens is present
            if (filePathTokens != null && !filePathTokens.isEmpty()) {
                String parsedFilePath = filePathTokens.get(0); // First token is the file path
                if (input.contains(">>")) {
                    OutputRedirector.appendOutput(output, parsedFilePath);
                } else if (input.contains(">")) {
                    OutputRedirector.redirectOutput(output, parsedFilePath);
                }
            } else {
                System.out.print(output);
            }
        }

        scanner.close();
    }

    private static String handleLsCommand(List<String> tokens, String currDir) {
        try {
            if (tokens.size() == 1) {
                return LsCommand.listDirectory(currDir);
            } else if ("-a".equals(tokens.get(1))) {
                return LsCommand.listAllFiles(tokens.size() > 2 ? tokens.get(2) : currDir);
            } else if ("-r".equals(tokens.get(1))) {
                return LsCommand.listFilesReversed(tokens.size() > 2 ? tokens.get(2) : currDir);
            } else {
                return LsCommand.listDirectory(tokens.size() == 2 ? tokens.get(1) : currDir);
            }
        } catch (Exception e) {
            return "Error executing ls command: " + e.getMessage() + "\n";
        }
    }

    private static String handleRmCommand(List<String> tokens) {
        if (tokens.size() < 2) {
            return "Error: 'rm' requires a path.\n";
        }
        try {
            return new RmCommand().execute(tokens.get(1));
        } catch (Exception e) {
            return "Error executing rm command: " + e.getMessage() + "\n";
        }
    }

    private static String handleRmdirCommand(List<String> tokens) {
        if (tokens.size() < 2) {
            return "Error: 'rmdir' requires a directory path.\n";
        }
        try {
            return new RmdirCommand().rmdir(tokens.get(1));
        } catch (Exception e) {
            return "Error executing rmdir command: " + e.getMessage() + "\n";
        }
    }

    private static String handleMvCommand(List<String> tokens) {
        if (tokens.size() < 3) {
            return "Error: 'mv' requires source and destination paths.\n";
        }
        try {
            MoveCommand mv = MoveCommand.getInstance();
            mv.move(tokens.get(1), tokens.get(2)); // 1--> source path 2 --> destination path.
            return "Moved successfully from (" + tokens.get(1) + ") to (" + tokens.get(2) + ")\n";
        } catch (IOException e) {
            return "Error executing mv command: " + e.getMessage() + "\n";
        }
    }

    private static String handleCatCommand(List<String> tokens) {
        String[] filePaths = new String[tokens.size() - 1];
        for (int i = 1; i < tokens.size(); i++) {
            filePaths[i - 1] = tokens.get(i).trim();
        }
        try {
            return new CatCommand().cat(filePaths);
        } catch (Exception e) {
            return "Error executing cat command: " + e.getMessage() + "\n";
        }
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
