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

	    checkClassCasing(currentLine, lineCounter);
	    checkConstentCasing(currentLine, lineCounter);

            if (currentLine.contains("(") && currentLine.contains(")") && 
			    (currentLine.contains("public")
			     || currentLine.contains("private"))
                             || currentLine.contains("void")
                             || currentLine.contains("static")){

                if (currentLine.contains("=")){
                    int location = currentLine.indexOf("=");
                    currentLine = currentLine.substring( 0, location - 1);
                }
	            String[] words  = currentLine.split(" ");
	            String methodName = null;
	            for (int i = 0; i < words.length; i++){
	                if (words[i].contains("(")){
		                if(words[i].equals("(")){
                            methodName = words[i - 1];
                            i = words.length;
		                }
		            else {
		                methodName = words[i];
                        i = words.length;
		            }
                    String firstChar = methodName.charAt(0) + ("");
                    if (!firstChar.equals(firstChar.toLowerCase())){
                        System.out.println(currentLine);
                        casingErrorList.add(lineCounter);
                    }
		        }
            }
	      }
        }
    }

    /**
     * Helper function that will check the currentLine string
     * if it contains a variable that is intended to be a constent.
     * If found it will assure that the variable name is all caps.
     * @param currentLine - the line we are checking
     * @param lineCounter - number of ht line we are currently checking
     */
    private static void checkConstentCasing(String currentLine, int lineCounter){
        if (currentLine.contains("final") && !currentLine.contains("System.out.println")) {

	    if (currentLine.contains("=")){
	        int location = currentLine.indexOf("=");
            currentLine = currentLine.substring( 0, location - 1);
        }
            String[] wordArray =  currentLine.split(" ");
            String lastWord = wordArray[wordArray.length - 1];
            if (!lastWord.equals(lastWord.toUpperCase())){
                casingErrorList.add(lineCounter);
            }
         }
    }

    /**
     * Helper function that will check the current string for a 
     * class name and assure that the first letter of it is capital,
     * if not it will add the line to the error list.
     * @param currentLine - the line we are checking
     * @param lineCounter - number of the line we are checking
     */
    private static void checkClassCasing(String currentLine, int lineCounter){
       if (currentLine.contains("class") && 
			    (currentLine.contains("public")
			     || currentLine.contains("private")
			     || currentLine.contains("{")
			     || currentLine.contains("static"))){

	    String[] wordArray = currentLine.trim().split(" ");
	    String lastWord = wordArray[wordArray.length -1];

	    if (lastWord.equals("{")){
	        lastWord = wordArray[wordArray.length - 2];
        }

	    String firstChar = lastWord.charAt(0) + "";
	    if (!firstChar.equals(firstChar.toUpperCase())){
	       casingErrorList.add(lineCounter);
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
     * Getter for getCassingErrorList
     * @return list containing the line number for errors
     */
    public static List<Integer> getCasingErrorList() {
        return casingErrorList;
    }


}
