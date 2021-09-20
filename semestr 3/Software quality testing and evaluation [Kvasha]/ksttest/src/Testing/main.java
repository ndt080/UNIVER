package Testing;

import java.util.Arrays;

public class main {
    public static void main(String[] args) {
        int[][] matrix1 = new int[4][4];
        for(int i=0; i < matrix1.length; i++){
            for(int j=0; j < matrix1.length; j++){
                matrix1[i][j]= (int) (Math.pow(2,i)-j);
            }
        }
        System.out.println("First initial  matrix: ");
        printMatrix(matrix1);

        int[][] matrix2 = {{1, 2, 3, 4}, {5, 7, 12, 15}, {36, 40, 42, 44}, {0, 1, 2, 3}};

        System.out.println("**************************************************************************");
        System.out.println("Second matrix, filled in using the keyboard: ");
        printMatrix(matrix2);

        int[][] matrix3 = addMatrix(matrix1, matrix2);
        System.out.println("**************************************************************************");
        System.out.println("Third matrix, the first matrix - the second matrix: ");
        printMatrix(matrix3);

        System.out.println("**************************************************************************");

        System.out.println("The arithmetic mean elements of the third matrix: " + arithmeticElements(matrix3));
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] ints : matrix) {
            System.out.println(Arrays.toString(ints));
        }
    }

    public static int[][] addMatrix(int[][] matrix1, int[][] matrix2) {
        int[][] matrix3 = new int[4][4];
        for (int i = 0; i < matrix3.length; i++) {
            for (int j = 0; j < matrix3[i].length; j++) {
                matrix3[i][j] = matrix1[i][j] - matrix2[i][j];
            }
        }
        return matrix3;
    }

    public static int arithmeticElements(int[][] matrix) {
        int sum = 0;
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[i].length; ++j) {
                sum = sum + matrix[i][j];
            }
        }
        sum = sum/16;
        return sum;
    }
}
