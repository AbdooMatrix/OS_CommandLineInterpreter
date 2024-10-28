package org.os;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class CatCommand {
    private Scanner scanner;

    public CatCommand(Scanner scanner) {
        this.scanner = scanner;
    }

    public void cat() {
        System.out.print("Enter file names to display, separated by spaces: ");

        // Check if there's input available
        if (scanner.hasNextLine()) {
            // Read the entire line of file names and split it into an array
            String input = scanner.nextLine();
            String[] fileNames = input.trim().split("\\s+");

            String currentDirectory = System.getProperty("user.dir"); // Get the current directory

            for (String fileName : fileNames) {
                if (fileName == null || fileName.trim().isEmpty()) {
                    System.out.println("Error: No file name provided for one of the files.");
                    continue;
                }

                String filePath = Paths.get(currentDirectory, fileName).toString();

                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading file " + filePath + ": " + e.getMessage());
                }
                System.out.println();
            }
        } else {
            System.out.println("No input provided.");
        }
    }
}
