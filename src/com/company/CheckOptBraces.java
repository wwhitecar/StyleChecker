package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CheckOptBraces {

    private static List<Integer> braceErrorLines = new ArrayList<>();
    private static int lineCounter = 0;

    /**
     * Check that the char following the closing
     * parenthese is an opening curly brace for all if/else
     * statements and loops.
     * If it is missing report an error at the line
     * after the statement.
     */
    public static void checkOptionalBraces(File file){
        Scanner scanner = getScanner(file);
        String currentLine = scanner.nextLine();

        if(currentLine.startsWith("/*")) {
            while (!currentLine.trim().startsWith("*/")) {
                currentLine = scanner.nextLine();
                lineCounter++;
            }
        }

        while(scanner.hasNext()) {
            while (checkForIfElseForWhile(currentLine)) {
                lineCounter = checkForBraces(currentLine, scanner);
                break;
            }
            currentLine = scanner.nextLine();
            lineCounter++;
        }
        scanner.close();
    }

    private static int checkForBraces(String currentLine, Scanner scanner){
        boolean checkNextLine = false;
        if (scanner.hasNext() && !currentLine.contains("{")) {
            currentLine = scanner.nextLine();
            lineCounter++;
            checkNextLine = true;
        }
        if (!currentLine.contains ("{")) {
            braceErrorLines.add(lineCounter);
            if(checkNextLine && checkForIfElseForWhile(currentLine)) {
                checkForBraces(currentLine, scanner);
            }
            return lineCounter;
        }
        return lineCounter;
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
     * Helper method that will check a string for conditional statements.
     * @param currentLine - string to check for statments
     * @return boolean - if the statements were found or not
     */
    private static boolean checkForIfElseForWhile(String currentLine){
        boolean answer = false;
        if (currentLine.contains("if (") || currentLine.contains("if(") ||
                currentLine.contains("else") ||
                currentLine.contains("for (") || currentLine.contains("for(") ||
                currentLine.contains("while (") ||
                currentLine.contains("while(")){
            answer = true;
        }
        return answer;
    }

    /**
     * Getter for Arraylist of errors
     * @return ArrayList that consists of the lines that hard errors on them
     */
    public static List<Integer> getBraceErrorLines(){
        return braceErrorLines;
    }
}
