package org.os;

public class PipeCommand {

    public static void runPipe(String commandLine) {
        String[] commands = commandLine.split("\\|");
        String output = null;

        for (int i = 0; i < commands.length; i++) {
            String commandLinePart = commands[i].trim();
            String[] parts = commandLinePart.split(" ", 2);
            String commandName = parts[0];
            String arg = parts.length > 1 ? parts[1] : null;

            Object cmd = getCommand(commandName); // Change to Object
            if (cmd == null) {
                throw new IllegalArgumentException("Unknown command: " + commandName);
            }

            // If not the first command, pass output from the previous command
            if (i > 0 && output != null) {
                arg = output;  // Use previous output as argument
            }

            // Execute the command and capture its output
            String result = null;

            if (cmd instanceof RmCommand) {
                result = ((RmCommand) cmd).execute(arg);
            } else if (cmd instanceof Mkdir) {
                result = ((Mkdir) cmd).execute(arg);
            } else if (cmd instanceof cdCommand) {
                result = cdCommand.cd(arg);
            }

            // Print command output if desired
            if (result != null) {
                System.out.println("Output of " + commandName + ": " + result);
            }
            output = result; // Store the output for the next command
        }
    }

    private static Object getCommand(String command) {
        switch (command) {
            case "mkdir":
                return new Mkdir();
            case "rm":
                return new RmCommand();
            case "cd":
                return new cdCommand(); // Assuming this will also be used as a command
            default:
                return null;
        }
    }
}