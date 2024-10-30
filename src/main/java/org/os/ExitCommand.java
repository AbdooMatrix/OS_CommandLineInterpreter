package org.os;

import java.util.Scanner;

public class ExitCommand {
    private final Scanner scanner;
    private final boolean shouldExit; // Control whether to exit

    public ExitCommand(Scanner scanner, boolean shouldExit) {
        this.scanner = scanner;
        this.shouldExit = shouldExit; // Store the exit flag
    }

    public void execute() {
        System.out.println("Exiting the CLI.");
        scanner.close(); // Close the scanner
        if (shouldExit) { // Only exit if shouldExit is true
            System.exit(0);  // Terminate the program
        }
    }
}
