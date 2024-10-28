package org.os;

import java.util.Scanner;

public class Exit {
    private final Scanner scanner;

    public Exit(Scanner scanner) {
        this.scanner = scanner;
    }

    public void execute() {
        System.out.println("Exiting the CLI.");
        scanner.close(); // Close the scanner
        System.exit(0);  // Terminate the program
    }
}
