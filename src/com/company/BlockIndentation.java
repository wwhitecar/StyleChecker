package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlockIndentation {

    private static List<Integer> blockIndentationError = new ArrayList<>();
    /**
     * Block indentation for all class body, method body,
     * loop bods, if/else bodys, and declerations (instance level)
     * should be indedented exactly 3 spaces from the lvel above them.
     * No tabs (they are evil). if incorrect indentation is found
     * an error is reported at the line it was found.
     */
    public static void checkBlockIndentation(File file){

        checkClassIndentation(file);
        checkMethodIndentation(file);
    }

    /**
     * Check that the class level indentation is correct.
     */
    private static void checkMethodIndentation(File file) {
        Scanner scanner = getScanner(file);
        String currentLine = scanner.nextLine();
        int lineCounter = 1;

        if (currentLine.startsWith("/*")) {
            while (!currentLine.trim().startsWith("*/")) {
                currentLine = scanner.nextLine();
                lineCounter++;
            }
        }

        while (scanner.hasNext()) {
            currentLine = scanner.nextLine();
            lineCounter++;

            int methodInden = 0;
            if (checkerForMethod(currentLine)) {
                for (int i = 0; i < currentLine.length(); i++){
                    if (currentLine.charAt(i) != ' '){
                        methodInden = i;
                        i = currentLine.length();
                    }
                }
                while (!currentLine.contains("{")) {
                    currentLine = scanner.nextLine();
                    lineCounter++;
                }

                int braceCounter = 1;
                while (braceCounter != 0) {
                    currentLine = scanner.nextLine();
                    lineCounter++;
                    if(currentLine.contains("{")){
                        braceCounter++;
                        correctIndent(methodInden, currentLine, lineCounter);
                    }
                    if(currentLine.contains("}")){
                        braceCounter--;
                        correctIndent(methodInden - 3, currentLine, lineCounter);
                    }
                    else {
                        correctIndent(methodInden, currentLine, lineCounter);
                    }
                }
            }
        }
    }
    /**
     * Checks if the currentLine is a method deceleration.
     * @param currentLine current string we are working with
     * @return true if method deceleration; flase otherwise
     */
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
     * Check that the class level indentation is correct.
     */
    private static void checkClassIndentation(File file){
        Scanner scanner = getScanner(file);
        String currentLine = scanner.nextLine();
        int lineCounter = 1;

        if(currentLine.startsWith("/*")) {
            while (!currentLine.trim().startsWith("*/")) {
                currentLine = scanner.nextLine();
                lineCounter++;
            }
        }

        while (!currentLine.contains("class")){
            currentLine = scanner.nextLine();
            lineCounter++;
        }
        while (!currentLine.contains("{")) {
            currentLine = scanner.nextLine();
            lineCounter++;
        }

        int classInden = 0;
        for (int i = 0; i < currentLine.length(); i++){
            if (currentLine.charAt(i) != ' '){
                classInden = i;
            }
        }

        while(scanner.hasNext()){
            currentLine = scanner.nextLine();
            lineCounter++;
            if (!currentLine.contains("{") && scanner.hasNext()) {
                correctIndent(classInden, currentLine, lineCounter);
            }
        }
    }

    /**
     * Helper function to assure that correct spacing is
     * done for that current Line
     */
    private static void correctIndent(int startingIndent,
                                      String currentLine, int lineCounter){
        for (int i = 0; i < 3; i++) {
            if (!currentLine.trim().isEmpty()) {
                if (currentLine.contains("\t.")) {
                    blockIndentationError.add(lineCounter);
                    i = 3;
                }
                if (currentLine.length() <= startingIndent + 3) {
                    blockIndentationError.add(lineCounter);
                    i = 3;
                } else {
                    Character characterHolder = currentLine.charAt(startingIndent + i);
                    String charCoverter = characterHolder + "";
                    if (!charCoverter.equals(" ")) {
                        blockIndentationError.add(lineCounter);
                        i = 3;
                    }
                }
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
     * Getter for the list of errors containing block indentation errors.
     * @return list of errors
     */
    public static List<Integer> getBlockIndentationError() {
        return blockIndentationError;
    }
}
