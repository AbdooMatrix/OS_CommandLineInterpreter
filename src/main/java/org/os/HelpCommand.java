package org.os;

public class HelpCommand {

    public String getHelpText() {
        return """
        Available commands:
        
        - help: Displays this help message.
          Example: help
          
        - exit: Exits the CLI.
          Example: exit
          
        - ls [path]: Lists files in the specified directory (default is current directory).
          Example: ls
                   ls "D:\\Folder"
                   
        - ls -a [path]: Lists all files, including hidden ones, in the specified directory.
          Example: ls -a
                   ls -a "D:\\Folder"
                   
        - ls -r [path]: Lists files in reverse order in the specified directory.
          Example: ls -r
                   ls -r "D:\\Folder"

        - touch <file>: Creates a new file or updates the timestamp of an existing file.
          Example: touch newFile.txt

        - mv [sourcePath] [destinationPath]: Moves a file from one directory to another.
          Example: mv "D:\\Folder\\file.txt" "D:\\NewFolder"

        - rm <file_path>: Removes a file or directory.
          Example: rm "D:\\Folder\\file.txt"

        - cat <file_path(s)>: Concatenates and displays the content of files.
          Example: cat file1.txt file2.txt

        - cd <directory>: Changes the current directory.
          Example: cd "D:\\Folder"

        - mkdir <directory>: Creates a new directory.
          Example: mkdir newFolder

        - rmdir <directory>: Removes an empty directory.
          Example: rmdir emptyFolder

        Redirection Operators:
        
        - [command] > [file]: Redirects command output to a file, overwriting existing content.
          Example: ls > output.txt

        - [command] >> [file]: Appends command output to a file without overwriting.
          Example: ls >> output.txt

        - [command] | [command]: Pipes the output of one command to another command.
          Example: ls | grep 'example'
        """;
    }
}
