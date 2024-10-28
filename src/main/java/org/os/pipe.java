package org.os;

public class pipe {

    public static void runPipe(String commandLine) {
        String[] commands = commandLine.split("\\|");
        String output = null;

        for (int i = 0; i < commands.length; i++) {
            String commandLinePart = commands[i].trim();
            String[] parts = commandLinePart.split(" ", 2);
            String commandName = parts[0];
            String arg = parts.length > 1 ? parts[1] : null;

            command cmd = getCommand(commandName);
            if (cmd == null) {
                throw new IllegalArgumentException("Unknown command: " + commandName);
            }
            if (i > 0 && output != null) {
                arg = output;
            }
            output = cmd.execute(arg);

            if (output != null) {
                System.out.println("Output of " + commandName + ": " + output);
            }
        }
    }


    private static command getCommand(String command) {
        switch (command) {
            case "mkdir":
                return new Mkdir();
            case "rm":
                return new Rm();
            default:
                return null;
        }
    }
}




