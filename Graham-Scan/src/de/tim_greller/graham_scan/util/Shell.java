/**
 * The utility package handling I/O.
 */
package de.tim_greller.graham_scan.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

import de.tim_greller.graham_scan.model.Field;
import de.tim_greller.graham_scan.model.Point;

/**
 * The graham scan shell enables direct user communication to perform operations 
 * on a {@link Field} used to execute the graham scan with a set of points.
 */
public final class Shell {

    /**
     * Holds the current information about whether the program should continue
     * execution or terminate after the current line of input is processed.
     */
    private static boolean continueExecution = true;

    /** The field storing all points and calculating the convex hull. */
    private static Field field = new Field();

    /** Private constructor to prevent instantiation. */
    private Shell() { }
    

    /**
     * The main method processes the input received on System.in (standard 
     * input).
     * 
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
        System.out.print("gs> ");
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

        switch (cmd) {
        case "new":
            field = new Field();
            break;

        case "help":
            printHelp();
            break;

        case "quit":
            continueExecution = false;
            break;

        case "print":
            System.out.println(field);
            break;

        case "add":
            addPoint(tokenizedInput);
            break;

        case "remove":
            removePoint(tokenizedInput);
            break;

        case "convex":
            System.out.println(field.convexHull());
            break;

        default:
            printError("Unknown command \"" + cmd + "\"");
            break;
        }
    }

    /**
     * Tries to parse the input and if successful adds a new {@link Point} to 
     * the field. Prints an error when parsing fails or adding the point would
     * produce a duplicate in the field.
     * 
     * @param tokenizedInput The full input split in its tokens.
     */
    private static void addPoint(String[] tokenizedInput) {
        Optional<Point> parsedPoint = parseParamsToPoint(tokenizedInput);
        if (parsedPoint.isPresent()) {
            Point pointToAdd = parsedPoint.get();
            boolean success = field.add(pointToAdd);
            if (!success) {
                printError("The Point " + pointToAdd + " was already added.");
            }
        }
    }

    /**
     * Tries to parse the input and if successful tries to remove the 
     * {@link Point} from the field. Prints an error when parsing or removing
     * fails.
     * 
     * @param tokenizedInput The full input split in its tokens.
     */
    private static void removePoint(String[] tokenizedInput) {
        Optional<Point> parsedPoint = parseParamsToPoint(tokenizedInput);
        if (parsedPoint.isPresent()) {
            Point pointToRemove = parsedPoint.get();
            boolean success = field.remove(pointToRemove);
            if (!success) {
                printError("The Point " + pointToRemove + " was not found.");
            }
        }
    }

    /**
     * Parses the parameters to an Point if possible.
     * 
     * @param tokenizedInput The full tokenized input, including command and
     *                       parameters.
     * @return An {@link Optional} containing the {@link Point} if parsing the
     *         parameters was successful.
     */
    private static Optional<Point> parseParamsToPoint(String[] tokenizedInput) {
        if (hasEnoughParameters(tokenizedInput, 2)) {
            Optional<Integer> x = parseInt(tokenizedInput[1]);
            Optional<Integer> y = parseInt(tokenizedInput[2]);

            if (x.isPresent() && y.isPresent()) {
                return Optional.of(new Point(x.get(), y.get()));
            }
        }
        return Optional.empty();
    }

    /**
     * Checks if the input contains enough parameters. Prints an error message
     * if given less parameters than required.
     * 
     * @param tokenizedInput     The full input split in its tokens.
     * @param requiredParameters The amount of required parameters.
     * @return {@code true} if the input contains enough parameters.
     */
    private static boolean hasEnoughParameters(String[] tokenizedInput, 
            int requiredParameters) {
        int givenParameters = tokenizedInput.length - 1 /* sub command token */;
        if (givenParameters < requiredParameters) {
            printError("Missing parameters. " + givenParameters 
                       + " recieved, but " + requiredParameters + " required.");
            return false;
        }
        return true;
    }

    /**
     * Tries to parse a String to an Integer.
     * 
     * @param value The String that should contain a numeric value.
     * @return An Optional containing the Integer if parsing was successful.
     */
    private static Optional<Integer> parseInt(String value) {
        try {
            return Optional.of(Integer.valueOf(value));
        } catch (NumberFormatException e) {
            printError("The parameters have to be integers.");
            return Optional.empty();
        }
    }

    /**
     * Prints a help text about the usage of the Shell including all supported
     * commands with their syntax and a description.
     */
    private static void printHelp() {
        System.out.println("");
    }

    /**
     * Prints an error text starting with {@code "Error!"} followed by the given
     * message.
     * 
     * @param msg The message describing the error.
     */
    private static void printError(String msg) {
        System.out.println("Error! " + msg);
        System.out.println("Enter 'help' to display the syntax.");
    }
}
