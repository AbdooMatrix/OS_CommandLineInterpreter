package org.os;
import java.io.IOException;

public class PipeCommand {

    public static void runPipe(String commandLine) {
        String[] commands = commandLine.split("\\|");
        String output = null;

        for (int i = 0; i < commands.length; i++) {
            String commandLinePart = commands[i].trim();
            String[] parts = commandLinePart.split(" ", 2);
            String commandName = parts[0];
            String arg = parts.length > 1 ? parts[1] : null;

            Object cmd = getCommand(commandName);
            if (cmd == null) {
                throw new IllegalArgumentException("Unknown command: " + commandName);
            }

            if (i > 0 && output != null) {
                arg = output;
            }

            String result = null;

            try {
                if (cmd instanceof RmCommand) {
                    result = ((RmCommand) cmd).execute(arg);
                } else if (cmd instanceof Mkdir) {
                    result = ((Mkdir) cmd).execute(arg);
                } else if (cmd instanceof cdCommand) {
                    result = cdCommand.cd(arg);
                } else if (cmd instanceof CatCommand) {
                    result = ((CatCommand) cmd).cat(arg != null ? arg.split(" ") : new String[]{});
                } else if (cmd instanceof MoveCommand) {
                    String[] paths = arg != null ? arg.split(" ") : new String[]{};
                    if (paths.length == 2) {
                        ((MoveCommand) cmd).move(paths[0], paths[1]);
                        result = "Move successful";
                    }
                } else if (cmd instanceof PWDCommand) {
                    result = ((PWDCommand) cmd).getCurrentDirec();
                } else if (cmd instanceof TouchCommand) {
                    result = ((TouchCommand) cmd).createOrUpdateFile(arg);
                } else if (cmd instanceof RmdirCommand) {
                    result = ((RmdirCommand) cmd).rmdir(arg);
                } else if (cmd instanceof LsCommand) { 
                    if (arg == null) {
                        result = LsCommand.listDirectory(".");
                    } else if (arg.equals("-a")) {
                        result = LsCommand.listAllFiles(".");
                    } else if (arg.equals("-r")) {
                        result = LsCommand.listFilesReversed(".");
                    } else {
                        result = LsCommand.listDirectory(arg);
                    }
                }
            } catch (IOException e) {
                result = "Error executing " + commandName + ": " + e.getMessage();
            }

            if (result != null) {
                System.out.println("Output of " + commandName + ": " + result);
            }
            output = result;
        }
    }

    private static Object getCommand(String command) {
        switch (command) {
            case "mkdir":
                return new Mkdir();
            case "rm":
                return new RmCommand();
            case "cd":
                return new cdCommand();
            case "cat":
                return new CatCommand();
            case "mv":
                return MoveCommand.getInstance();
            case "pwd":
                return PWDCommand.getInstance();
            case "touch":
                return new TouchCommand();
            case "rmdir":
                return new RmdirCommand();
            case "ls":
                return new LsCommand();
            default:
                return null;
        }
    }
}






