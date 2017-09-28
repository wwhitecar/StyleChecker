package com.company;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Check that proper style casing is followed for the
 * class name, method name, and any constent variables,
 * camel casing is ignored.
 * If any casing errors are found the error should be
 * reported on the line the error is found on.
 */
public class CheckCorrectCasing {

    /**
     * List that will keep track of lines that dont have proper
     * casing according to java standards.
     */
    private static List<Integer> casingErrorList = new ArrayList<>();

    /**
     * Check for correct casing throught the file provided.
     * @param file - the file to be checked for errors
     */
    public static void checkForCasingErrors(File file){
        Scanner scanner = getScanner(file);
        String currentLine = scanner.nextLine();
        int lineCounter = 0;

        if(currentLine.startsWith("/*")) {
            while (!currentLine.trim().startsWith("*/")) {
                currentLine = scanner.nextLine();
                lineCounter++;
            }
        }

        while (scanner.hasNext()) {
            currentLine = scanner.nextLine();
            lineCounter++;

            if (currentLine.contains("final")) {

            }
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
