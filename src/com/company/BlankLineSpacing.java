package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Checks that there is proper spacing in 3 locations:
 * 1. Inbetween Class definition and instance varaiables
 * 2. Between instance variables and first method
 * 3. Between each method
 * If not an error will be reported at the line that was
 * expected to be blank
 */
public class BlankLineSpacing {

    /**
     * List that will keep track of the the lines that are
     * exected to be blank but are not.
     */
    private static List<Integer> missingBlankLines = new ArrayList<Integer>();

    /**
     * Will check for correct blank line usage in the file.
     * @param file
     */
    private static void checkForCorrectSpacing(File file){
        Scanner scanner = getScanner(file);
        String currentLine = scanner.nextLine();
        int lineCounter = 0;

        if(currentLine.startsWith("/*")) {
            while (!currentLine.trim().startsWith("*/")) {
                currentLine = scanner.nextLine();
                lineCounter++;
            }
        }
        scanner.close();

        while (scanner.hasNext()) {
            currentLine = scanner.nextLine();
            lineCounter++;

            if (currentLine.contains("class")) {
                currentLine = scanner.nextLine();
                lineCounter++;
                if (!currentLine.trim().isEmpty()){
                    missingBlankLines.add(lineCounter);
                }
            }

            //TODO:Blank line between methods and variables
        }
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
}
