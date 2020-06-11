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
     * The field storing all points and calculating the convex hull. 
     */
    private static Field field = new Field();

    /** 
     * Private constructor to prevent instantiation. 
     */
    private Shell() { }

    /**
     * The main method processes the input received on System.in (standard 
     * input).
     * 
     * @param args The arguments are ignored. All input must be sent per stdin.
     * @throws IOException If an I/O error occurs.
     */
    public static void main(String[] args) throws IOException {
        BufferedReader stdin 
                = new BufferedReader(new InputStreamReader(System.in));
        boolean continueExecution = true;
        
        while (continueExecution) {
            System.out.print("gs> ");
            String input = stdin.readLine();
            continueExecution = processLine(input);
        }
    }

    /**
     * Reads the next input line and initiates the execution of it.
     * Sets the {@code continueExecution} to false if EOF is reached.
     * Performs no operation for blank lines.
     * 
     * @param stdin The BufferedReader for the standard input stream.
     * @return whether the program should continue execution or terminate after 
     *         the current line of input is processed.
     */
    private static boolean processLine(String line) {
        if (line == null) {
            // exit program if EOF (end of file) is reached
            return false;
        } else if (line.isBlank()) {
            // show the prompt again if no input was given
            return true;
        }

        String[] tokenizedInput = line.trim().split("\\s+");
        return executeCommand(tokenizedInput);
    }

    /**
     * If the first token of the input is a valid command, it gets executed.
     * 
     * @param tokenizedInput The input containing command and parameters.
     * @return whether the program should continue execution or terminate after 
     *         the current command was executed.
     */
    private static boolean executeCommand(String[] tokenizedInput) {
        String cmd = tokenizedInput[0].toLowerCase();

        switch (cmd) {
        case "new":
            field = new Field();
            break;

        case "help":
            printHelp();
            break;

        case "quit":
            return false;

        case "print":
            field.sortPoints();
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
        
        return true;
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
            Optional<Integer> x = parseInt(tokenizedInput[1], "x");
            Optional<Integer> y = parseInt(tokenizedInput[2], "y");

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
        } else {
            return true;
        }
    }

    /**
     * Tries to parse a String to an Integer.
     * 
     * @param value The String that should contain a numeric value.
     * @param paramName The name of the parameter which should be parsed.
     * @return An Optional containing the Integer if parsing was successful.
     */
    private static Optional<Integer> parseInt(String value, String paramName) {
        try {
            return Optional.of(Integer.valueOf(value));
        } catch (NumberFormatException e) {
            printError("The parameter " + paramName + " has to be an integer.");
            return Optional.empty();
        }
    }

    /**
     * Prints a help text about the usage of the Shell including all supported
     * commands with their syntax and a description.
     */
    private static void printHelp() {
        System.out.println(
              "The graham scan shell (gs) lets you dynamically specify a set of"
            + " points in a two-dimensional integer coordinate system to "
            + "calculate the convex hull of.\n\n"
            
            + "Available commands:\n"
            
            + "add    <x> <y>  Appends a new point with the integer coordinates"
            + " x and y to the current set of points. Fails if the coordinates "
            + "aren't valid integers or if such point already exists in the "
            + "set.\n"
            
            + "remove <x> <y>  Removes the point with the integer coordinates x"
            + " and y if it exists in the current set of points.\n"
            
            + "print   Prints the formatted string representation of the "
            + "current set of points. The points are sorted by their x value "
            + "(and if equal by y value).\n"
            
            + "convex  Calculates the convex hull of the current set of points "
            + "using the graham scan algorithm. Prints the list of the points "
            + "forming the convex hull starting with the leftmost point that "
            + " has the lowest y value and continuing counter clockwise. \n"
            
            + "new     Creates a new empty set of points, deleting all "
            + "previously added points.\n"
            
            + "help    Shows this help text.\n"
            
            + "quit    Exits the program.\n"
        );
    }

    /**
     * Prints an error text starting with {@code "Error!"} followed by the given
     * message.
     * 
     * @param msg The message describing the error.
     */
    private static void printError(String msg) {
        System.out.println("Error! " + msg);
    }
}
