import java.util.*;

public class Main{

    // Helper function to print matrix to console.
public static void printMatrix(TheMatrix matrix){
    if ( matrix == null){
        System.out.println("The matrix contains no data.");
        return;
    }
    double[][] data = matrix.getFirstMatrix();
    for(int row = 0; row < data[0].length; row++){
                for(int col = 0; col < data.length; col++){
            System.out.print(data[col][row] + " ");
        }
        System.out.println();
    }
}

    public static void main(String[] args) {

        // Data sets for testing            col1    col2        col3
        double [][] data1 = new double[][]{{1,2,4}, {4,5,6}, {7,8,9}};
        TheMatrix matrix1 = new TheMatrix(data1);
        // Data sets for testing            col1    col2        col3
        double [][] data2 = new double[][]{{9,8,7}, {6,5,4}, {3,2,1}};
        TheMatrix matrix2 = new TheMatrix(data2);
        // Data sets for testing            col1    col2        col3
        double [][] data3 = new double[][]{{1,2,2}, {3,4,4}, {5,6,6}};
        TheMatrix matrix3 = new TheMatrix(data3);

        double [][] data4 = new double[][]{{9,2}, {1,4}};
        TheMatrix matrix4 = new TheMatrix(data4);


        // Performing and printing matrix operations
        System.out.println("Add Matrix 1 and Matrix 2: ");
        printMatrix(matrix1.addition(matrix2));
        System.out.println("\n");

        System.out.println("Multiply Matrix 1 and Matrix 3: ");
        printMatrix((matrix1.multiplication(matrix3)));
        System.out.println("\n");

        System.out.println("Transposing: ");
        System.out.println("Matrix 1, non-transposed: ");
        printMatrix(matrix1);
        System.out.println("\n");

        System.out.println("Matrix 1, transposed: ");
        printMatrix(matrix1.transpose());
        System.out.println("\n");

        // Testing error statement by using operationally incompatible matrices.
        System.out.println("Testing for error. Multiply 3x3 matrix with 2x2 matrix: ");
        printMatrix((matrix1.multiplication(matrix4)));
    }
}