package com.company;

import java.io.File;
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
