package com.company;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Author: William Whitecar
 * Assignment # 2: Style Checker
 */

public class StyleChecker {

    /**
     * Author of the program we are checking.
     */
    private static String programAuthor;

    /**
     * Errors that the current files contains specifically to be checked for.
     */
    private static String checkedErrors;

    /**
     * Main function will assure that a file has been passed in
     * and call each check function on that file.
     * @param args - file to ce stylecheck
     * @throws InvalidArgumentException - bad arguments
     */
    public static void main(String[] args) throws InvalidArgumentException {

        if( 0 == args.length){
            throw new InvalidArgumentException(args);
        }
        File file = createFile(args[0]);
        getAuthorsName(args[0]);
        CheckOptBraces.checkOptionalBraces(file);
        OperatorSpaces.checkOperatoreSpaces(file);
        SingleLineStatments.checkSingleLineStatements(file);
        ToManyCharsPerLine.checkMaxLineLength(file);
        createErrorReport();

    }

    /**
     * CreateErrorReport will write the output file
     * containing My name then the authors name the errros
     *  being check and then the list of errors that were found
     *  during the check.
     */
    private static void createErrorReport(){
        PrintWriter writer = null;
        File file = null;
       try {
           file = new File("Error-report-"+ programAuthor + ".txt");
           writer = new PrintWriter(file);

       } catch (IOException e) {
           e.printStackTrace();
       }
       writer.println("Style Report by William Trevor Whitecar");
       writer.println("Test program author: " + programAuthor);
       writer.println("Errors Checked For: " + checkedErrors);

       printErrorReports(writer);
       writer.close();
    }

    private static void printErrorReports(PrintWriter writer){
        if( CheckOptBraces.getBraceErrorLines().size() > 0){
            List<Integer> listy = CheckOptBraces.getBraceErrorLines();
            for(int i = 0; i < listy.size(); i++)
            writer.println( listy.get(i) + ": Optional Brace is Required");
        }
        if (OperatorSpaces.getOperatorErrorLine().size() > 0){
            for ( Integer i : OperatorSpaces.getOperatorErrorLine()){
                writer.println( i + ": Expected Spaces around operator");
            }
        }
        if (SingleLineStatments.getSingleLineStatementErrors().size() > 0) {
            for (Integer i : SingleLineStatments.getSingleLineStatementErrors()) {
                writer.println( i + ": Only one Statement per line allowed");
            }
        }
        if (ToManyCharsPerLine.getToManyCharErrors().size() > 0){
            List<Integer> listy = ToManyCharsPerLine.getToManyCharErrors();
            for (int i = 0; i < listy.size(); i++) {
                writer.println( listy.get(i) + ": To many Characters on this line");
            }
        }
    }

    /**
     * Creates a new file that will be check.
     * @param fileName - name of file to be created
     * @return File - a new file
     */
    private static File createFile(String fileName){
        File file = null;
        try {
            file = new File(fileName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    private static void getAuthorsName(String fileName){
        Scanner scanner = null;
        try {
            scanner = new Scanner( createFile(fileName));
        } catch (Exception e){
            e.printStackTrace();
        }

        String currentLine = scanner.nextLine();
        if(currentLine.startsWith("/*")){
            currentLine = scanner.nextLine();
            programAuthor =  currentLine.trim();
        }
        currentLine = scanner.nextLine();
        checkedErrors = currentLine.trim();
    }

    /**
     * Block indentation for all class body, method body,
     * loop bods, if/else bodys, and declerations (instance level)
     * should be indedented exactly 3 spaces from the lvel above them.
     * No tabs (they are evil). if incorrect indentation is found
     * an error is reported at the line it was found.
     */
    private static void checkBlockIndentation(){

    }

    /**
     * Check for proper brace alignment, meaning each brace is
     * located on a line of its own, and lined up with the first
     * char of that block. Should be checked for on all if/else
     * statments and all loops
     */
    private static void checkBraceAlignment(){

    }
}
