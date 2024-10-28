package org.os;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        System.out.println("Type 'help' for available commands, or 'exit' to quit.");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                new Exit(scanner).execute();
                break;
            }


            switch (input) {
                case "cat":
                    CatCommand catCommand = new CatCommand(scanner); // Pass the scanner instance
                    catCommand.cat();
                    break;
                case "ls":
                    LsCommand lsCommand = new LsCommand();
                    lsCommand.ls();
                    break;
                case "rmdir":
                    RmdirCommand rmdirCommand = new RmdirCommand();
                    rmdirCommand.rmdir("donia");
                    break;
                default:
                    System.out.println("Unknown command: " + input);
            }
        }
    }
}


