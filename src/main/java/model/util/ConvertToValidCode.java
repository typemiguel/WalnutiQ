package model.util;

/**
 * Created by qliu on 6/29/14.
 */
public class ConvertToValidCode {
    /**
     * @param int2DArray must contain \n to represent the next line
     */
    public void printsToConsoleConvertedJavaForInt2DArray(String int2DArray, int numberOfRows, int numberOfColumns) {
        System.out.println("\n= new int[][] {");
        System.out.print("{ ");
        int int2DArrayLength = int2DArray.length();
        for (int i = 0; i < int2DArrayLength; i++) {
            char currentChar = int2DArray.charAt(i);
            char nextChar = int2DArray.charAt(i + 1);

            if (nextChar == 'E') {
                System.out.println("0 }\n};");
                return;
            } else if (nextChar == '\n') {
                System.out.print("0 },\n{ ");
                i++;
            } else {
                System.out.print(currentChar + ",");
            }
        }
    }
}
