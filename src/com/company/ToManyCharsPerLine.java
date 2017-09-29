package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Per "The Elements of Java Stlye" a lline should have no
 * more then 80 or 132 char's per line. For this assignment
 * will restrict the max line length to 80 chars. If a line
 * exceds this number of chars a error will be reported
 * at that line.
 */
public class ToManyCharsPerLine {

    /**
     * Keeps track of the lines that exceeds the maximum line length.
     */
    private static List<Integer> toManyCharErorrs = new ArrayList<>();

    private static final int MAXLENGTH = 80;

    /**
     * Will check each line for a maximum line length
     * and if it exceeds that amount then we will report
     * an error at that line.
     */
    public static void checkMaxLineLength(File file){
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

            if (currentLine.length() > MAXLENGTH) {
                toManyCharErorrs.add(lineCounter);
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
     * Getter for toManyCharErrors list
     * @return the list of errors
     */
    public static List<Integer> getToManyCharErrors(){
        removeDups();
        return toManyCharErorrs;
    }

    /**
     * Removes duplicates from the arrayList
     */
    private static void removeDups(){
        for (int i = 0; i < toManyCharErorrs.size(); i ++){
            for (int j = i + 1; j < toManyCharErorrs.size(); j++){
                if (toManyCharErorrs.get(i) == toManyCharErorrs.get(j)){
                    toManyCharErorrs.remove(j);
                    j--;
                }
            }
        }
    }
}
