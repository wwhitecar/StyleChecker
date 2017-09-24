package com.company;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Multiple lines of code is not allowed to be on a single line.
 * This means that only one statement is allowed for a single
 * line of code. If multiple lines of code are detected an
 * error will be reported at that line of code.
 */
public class SingleLineStatments {

    /**
     * Keeps track of all lines that contain errors
     */
    private static Set<Integer> singleLineStatementErrors = new HashSet<>();

    /**
     * Makes sure there is only one statement per line for readability.
     * @param file
     */
    public static void checkSingleLineStatements(File file){
        Scanner scanner = getScanner(file);
        String currentLine = scanner.nextLine();
        int lineCounter = 1;

        if(currentLine.startsWith("/*")) {
            while (!currentLine.trim().startsWith("*/")) {
                currentLine = scanner.nextLine();
                lineCounter++;
            }
        }

        while (scanner.hasNext()) {
            currentLine = scanner.nextLine();
            lineCounter++;
            if(currentLine.contains(";")) {
                int i = currentLine.indexOf(";");
                if (currentLine.substring(i + 1).contains(";") && !currentLine.trim().startsWith("for")) {
                    singleLineStatementErrors.add(lineCounter);
                }
            }
        }
        scanner.close();
    }

    /**
     * Error checking for opening a scanner.
     * @param file - file to be used with scanner
     * @return Scanner to be used
     */
    private static Scanner getScanner(File file) {
        Scanner scanner = null;
        try{
            scanner = new Scanner(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scanner;
    }

    /**
     * Getter for the error list.
     * @return the set of errrors.
     */
    public static Set<Integer> getSingleLineStatementErrors() {
        return singleLineStatementErrors;
    }
}
