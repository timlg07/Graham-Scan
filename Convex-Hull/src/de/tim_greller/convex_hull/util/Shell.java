/**
 * 
 */
package de.tim_greller.convex_hull.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 */
public final class Shell {

    /** 
     * Holds the current information about whether the program should continue
     * execution or terminate after the current line of input is processed.
     */
    private static boolean continueExecution = true;
    
    /** Private constructor to prevent instantiation. */
    private Shell() { }
    
    /**
     * The main method processes the input received on System.in (standard 
     * input).
     * @param args The arguments are ignored. All input must be sent per stdin.
     * @throws IOException If an I/O error occurs.
     */
    public static void main(String[] args) throws IOException {
        BufferedReader stdin = new BufferedReader(
                new InputStreamReader(System.in));

        while (continueExecution) {
            processLine(stdin);
        }

        stdin.close();
    }

    /**
     * Reads the next input line and initiates the execution of it.
     * Sets the {@code continueExecution} to false if EOF is reached.
     * Performs no operation for blank lines.
     * 
     * @param stdin The BufferedReader for the standard input stream.
     * @throws IOException If an I/O error occurs.
     */
    private static void processLine(BufferedReader stdin) throws IOException {
        System.out.print("trie> ");
        String input = stdin.readLine();

        // exit program if EOF (end of file) is reached
        if (input == null) {
            continueExecution = false;
            return;
        }

        // show the prompt again if no input was given
        if (input.isBlank()) {
            return;
        }

        String[] tokenizedInput = input.trim().split("\\s+");
        executeCommand(tokenizedInput);
    }

    /**
     * If the first token of the input is a valid command, it gets executed.
     * 
     * @param tokenizedInput The input containing command and parameters.
     */
    private static void executeCommand(String[] tokenizedInput) {
        // The array is never empty, because spaces get removed by trim and
        // splitting an empty string returns an array containing an empty
        // string.
        String cmd = tokenizedInput[0].toLowerCase();
        
        switch(cmd) {
        default:
            break;
        }
    }
}
