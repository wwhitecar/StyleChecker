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

    private static int lineCounter = 0;
    /**
     * List that will keep track of the the lines that are
     * exected to be blank but are not.
     */
    private static List<Integer> missingBlankLines = new ArrayList<>();

    /**
     * List that will keep track if there are to many blank
     * lines between vars class dec and methods
     */
    private static List<Integer> toManyBlankLines = new ArrayList<>();

    /**
     * Will check for correct blank line usage in the file.
     * @param file - file to be checked for spacing errors
     */
    public static void checkForCorrectSpacing(File file){
        Scanner scanner = getScanner(file);
        String currentLine = scanner.nextLine();
        lineCounter = 1;

        if(currentLine.startsWith("/*")) {
            while (!currentLine.trim().startsWith("*/")) {
                currentLine = scanner.nextLine();
                lineCounter++;
            }
        }

        while (scanner.hasNext()) {
            currentLine = scanner.nextLine();
            lineCounter++;

            checkClassVarBlankLine(currentLine, lineCounter, scanner);


            if (currentLine.contains("public") || currentLine.contains("private")
                    || currentLine.contains("void")
                    || currentLine.contains("static") &&
                    (currentLine.contains("(") && currentLine.contains(")"))) {
                System.out.println(lineCounter);
                int braceCounter = 1;
                if (!currentLine.contains("{")) {
                    while (!currentLine.contains("{") && scanner.hasNext()) {
                        currentLine = scanner.nextLine();
                        lineCounter++;
                    }
                    while (braceCounter != 0) {
                        currentLine = scanner.nextLine();
                        lineCounter++;
                        if (currentLine.contains("{")) {
                            braceCounter++;
                        }
                        if (currentLine.contains("}")) {
                            braceCounter--;
                        }
                    }
                    currentLine = scanner.nextLine();
                    lineCounter++;
                    if(!currentLine.trim().isEmpty()){
                        System.out.println("added here" + lineCounter+ currentLine);
                        missingBlankLines.add(lineCounter);
                    }
                    else{
                        checkForToManySpaces(currentLine, lineCounter, scanner);
                    }
                }
            }
            //TODO:Blank line between methods and variables
        }

        scanner.close();
    }

    /**
     * Checks that after a class is declared the line following it
     * will be a blank line before variables or methods.
     */
    private static void checkClassVarBlankLine(String currentLine,
                                               int lineCounter, Scanner scanner){
        if (currentLine.contains("class")) {
            if(currentLine.contains("{")) {
                currentLine = scanner.nextLine();
                lineCounter++;
                if (!currentLine.trim().isEmpty()) {
                    missingBlankLines.add(lineCounter);
                }
            }
            else {
                checkForToManySpaces(currentLine, lineCounter, scanner);
            }
        }
    }

    /**
     * Check if we have to many spaces in a location
     */
    private static void checkForToManySpaces(String currentLine, int lineCounter, Scanner scanner){
        while (!currentLine.contains("{") && scanner.hasNext()) {
            currentLine = scanner.nextLine();
            lineCounter++;
        }
        currentLine = scanner.nextLine();
        lineCounter++;
        if (!currentLine.trim().isEmpty()){
            missingBlankLines.add(lineCounter);
        }
        else {
            currentLine = scanner.nextLine();
            lineCounter++;
            if (currentLine.trim().isEmpty()){
                toManyBlankLines.add(lineCounter);
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

    /**
     * Getter for BlankLines error list.
     * @return list of lines that contain an error
     */
    public static List<Integer> getMissingBlankLines() {
        return missingBlankLines;
    }

    /**
     * Getter for toManyBlankLines error list.
     * @return list that has a line that is expected to not be empty
     */
    public static List<Integer> getToManyBlankLines(){
        return toManyBlankLines;
    }
}
