package com.company;

import java.io.File;
import java.util.*;

public class OperatorSpaces {

    private static List<Integer> operatorErrorLine = new ArrayList<>();

    /**
     * Check for exactly one space around arithmetic operatoins
     * (+, - , /, *, mod), unless it is a legal operation such
     * as : ++, --, <=, >=, ==). Report an error at the line
     * that is missing the space around the operator
     */
    public static void checkOperatoreSpaces(File file){
        Scanner scanner = getScanner(file);
        String currentLine = scanner.nextLine();
        int lineNumber = 1;

        if(currentLine.startsWith("/*")) {
            while (!currentLine.trim().startsWith("*/")) {
                currentLine = scanner.nextLine();
                lineNumber++;
            }
        }

        while(scanner.hasNext()) {
            currentLine = scanner.nextLine();
            lineNumber++;
            checkPlusOperator(currentLine, lineNumber);
            checkMinusOperator(currentLine, lineNumber);
            checkDivisionOperator(currentLine, lineNumber);
            checkMultiplicationOperator(currentLine, lineNumber);
            checkModuloSpacing(currentLine, lineNumber);

        }
        scanner.close();
    }

    /**
     * Checks the plus operator has correct spacing around it but
     * at the same time making sure ++ is still valid.
     * @param currentLine - string that contains '+'
     * @param lineNumber - line number that we are currently on
     */
    private static void checkPlusOperator(String currentLine, int lineNumber){

        if(currentLine.contains("++") && currentLine.indexOf("++")
                == currentLine.indexOf('+') ) {
            currentLine = currentLine.substring(currentLine.indexOf('+') + 2);
        }

        if (currentLine.contains("+")) {
            int p = currentLine.indexOf("+");
            Character inFront = currentLine.charAt( p - 1);
            Character behind = currentLine.charAt( p + 1);
            if (!inFront.equals(' ') || !behind.equals(' ')
                    || behind.equals('+') ) {
                operatorErrorLine.add(lineNumber);
            }
            else {
                checkPlusOperator(currentLine.substring( p + 1), lineNumber);
            }
        }
    }
    /**
     * Checks the minus operator has correct spacing around it but
     * at the same time making sure -- is still valid.
     * @param currentLine - string that we are checking for correct spacing
     * @param lineNumber - the current line number
     */
    private static void checkMinusOperator(String currentLine,
                                           int lineNumber){

        if(currentLine.contains("--") && currentLine.indexOf("--") == currentLine.indexOf('-') ){
            currentLine = currentLine.substring(currentLine.indexOf('-') + 2);
        }

        if (currentLine.contains("-")) {
            int m = currentLine.indexOf("-");
            Character inFront = currentLine.charAt(m - 1);
            Character behind = currentLine.charAt(m + 1);
            if (!inFront.equals(' ') ||
                    !behind.equals(' ') || behind.equals('-')){
                operatorErrorLine.add(lineNumber);
            }
            else{
                checkMinusOperator(currentLine.substring(m + 1), lineNumber);
            }
        }
    }

    /**
     * Checks the Devision operator for correct spaceing
     * and correctly ignoring comments.
     * @param currentLine - the current line we are checking
     * @param lineNumber - line number of the line we are currently checking
     */
    private static void checkDivisionOperator(String currentLine,
                                              int lineNumber){

        if( needToCheckDivision(currentLine)) {
            int j = currentLine.indexOf("/");
            Character inFront = currentLine.charAt(j - 1);
            Character behind = currentLine.charAt(j + 1);

            if (!inFront.equals(' ') || !behind.equals(' ')) {
                operatorErrorLine.add(lineNumber);
            }
            else {
                checkDivisionOperator(currentLine.substring(j + 2), lineNumber);
            }
        }
    }

    /**
     * Helper function to verify a string does need to be check
     * for correct spacing around a division symbole
     * @param currentLine - string we are currently checking
     * @return true if need to check for corrent specing; false otherwise
     */
    private static boolean needToCheckDivision(String currentLine){
        boolean answer = false;
        if( !currentLine.contains("//") && !currentLine.trim().startsWith("/*")
             && currentLine.contains("/")&& !currentLine.trim().equals("*/")) {
            answer = true;
        }
        return answer;
    }

    /**
     * Checks the multiplication operator for correct spacing. Keeping in
     * mind that comments and important statement have * and are still valid.
     * @param currentLine - string we are checking for mulitplication errors
     * @param lineNumber - the current line we are working with.
     */
    private static void checkMultiplicationOperator(String currentLine,
                                                    int lineNumber){

        if ( needToCheckMultiplication( currentLine)) {
            int i = currentLine.indexOf("*");
            Character inFront = currentLine.charAt(i -1);
            Character behind = currentLine.charAt(i + 1);
            if (!inFront.equals(' ') || !behind.equals(' ')){
                operatorErrorLine.add(lineNumber);
            }
            else {
                checkMultiplicationOperator(currentLine.substring(i + 2),
                        lineNumber);
            }
        }
    }

    /**
     * Helper function to check if we need that checks all the cases that
     * would requireuse to check a line for correct spacing around multiple
     * symbol.
     * @param currentLine - current string that we are working with
     * @return true if we need to check the currentline; false otherwise
     */
    private static boolean needToCheckMultiplication(String currentLine) {
        boolean answer = false;
        if (!currentLine.trim().startsWith("*") &&
                !currentLine.trim().startsWith("/*")&&
                !currentLine.contains("import") &&
                currentLine.length()> 0 && currentLine.contains("*")){
            answer = true;
        }
        return answer;
    }

    /**
     * Checks the modulo operator for correct spaceing.
     * @param currentLine - current line we are checking for proper spacing
     * @param lineNumber - the line number we are to add if error is detected
     */
    private static void checkModuloSpacing(String currentLine, int lineNumber){

        if (currentLine.contains("%")) {
            int m = currentLine.indexOf("%");
            Character inFront = currentLine.charAt(m - 1);
            Character behind = currentLine.charAt(m + 1);
            if (!inFront.equals(' ') || !behind.equals(' ')) {
                operatorErrorLine.add(lineNumber);
            }
            else {
                checkModuloSpacing(currentLine.substring(m -1), lineNumber);
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
     * Getter for operatorErrorLine.
     * @return Set<Interger> - returns the set that has line errors on it.
     */
    public static List<Integer> getOperatorErrorLine(){

        removeDups();
        return operatorErrorLine;
    }

    /**
     * Removes duplicates from the arrayList
     */
    private static void removeDups(){
        for (int i = 0; i < operatorErrorLine.size(); i ++){
            for (int j = i + 1; j < operatorErrorLine.size(); j++){
                if (operatorErrorLine.get(i) == operatorErrorLine.get(j)){
                    operatorErrorLine.remove(j);
                    j--;
                }
            }
        }
    }
}
