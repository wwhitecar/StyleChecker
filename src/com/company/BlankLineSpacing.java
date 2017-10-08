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
    private static String currentLine = "";
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
        currentLine = scanner.nextLine();
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

            checkClassVarBlankLine(scanner);
            checkBetweenMethodBlankLines(scanner);

            if (!(currentLine.contains("(") && currentLine.contains(")")
                    && currentLine.contains(";") && !currentLine.contains("class")) &&
                    (currentLine.contains("public") || currentLine.contains("private")
                            || currentLine.contains("void") || currentLine.contains("String")
                            || currentLine.contains("static") || currentLine.contains("int")
                            || currentLine.contains("double") || currentLine.contains("float")
                            || currentLine.contains("boolean") || currentLine.contains("byte")
                            || currentLine.contains("char") || currentLine.contains("long")
                    )){
                currentLine = scanner.nextLine();
                lineCounter++;

                if(checkerForMethod(currentLine)){
                    missingBlankLines.add(lineCounter);
                }
            }
        }

        scanner.close();
    }

    /**
     * Checks for blank lines between methods
     */
    private static void checkBetweenMethodBlankLines(Scanner scanner){
        if (checkerForMethod(currentLine)){
            while (!currentLine.contains("{")){
                currentLine = scanner.nextLine();
                lineCounter++;
            }

            int braceCounter = 1;
            while (braceCounter != 0){
                currentLine = scanner.nextLine();
                lineCounter++;
                if (currentLine.contains("{")){
                    braceCounter++;
                }
                if (currentLine.contains("}")){
                    braceCounter--;
                }
            }
            currentLine = scanner.nextLine();
            lineCounter++;
            if(!currentLine.trim().isEmpty() && !currentLine.trim().equals("}")){
                missingBlankLines.add(lineCounter);
                checkBetweenMethodBlankLines(scanner);
            }
            if(!scanner.hasNext()){
                return;
            }
            else{
                checkForToManySpaces(scanner);
            }
        }
    }

    private static boolean checkerForMethod(String currentLine){
        boolean answer = false;
        if ((currentLine.contains("(") && currentLine.contains(")")
                && !currentLine.contains(";") && !currentLine.contains("class")) &&
                (currentLine.contains("public") || currentLine.contains("private")
                        || currentLine.contains("void") || currentLine.contains("String")
                        || currentLine.contains("static") || currentLine.contains("int")
                        || currentLine.contains("double") || currentLine.contains("float")
                        || currentLine.contains("boolean") || currentLine.contains("byte")
                        || currentLine.contains("char") || currentLine.contains("long")
                )){
            answer  = true;
        }
        return answer;
    }

    /**
     * Checks that after a class is declared the line following it
     * will be a blank line before variables or methods.
     */
    private static void checkClassVarBlankLine(Scanner scanner){
        if (currentLine.contains("class")) {
            while (!currentLine.contains("{")) {
                currentLine = scanner.nextLine();
                lineCounter++;
            }
            currentLine = scanner.nextLine();
            lineCounter++;
            if (!currentLine.trim().isEmpty()) {
                missingBlankLines.add(lineCounter);
            }
            else {
                checkForToManySpaces(scanner);
            }
        }
    }

    /**
     * Check if we have to many spaces in a location
     */
    private static void checkForToManySpaces(Scanner scanner){

        currentLine = scanner.nextLine();
        lineCounter++;
        if (currentLine.trim().isEmpty()){
            toManyBlankLines.add(lineCounter);
        }
        else {
            checkBetweenMethodBlankLines(scanner);
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
