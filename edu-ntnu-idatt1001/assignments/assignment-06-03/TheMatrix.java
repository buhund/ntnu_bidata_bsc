import java.util.*;

public final class TheMatrix {

    // Class variable
    // column * row
    final double[][] firstMatrix;

    // Constructor method
    public TheMatrix(double[][] firstMatrix) {
        this.firstMatrix = firstMatrix;
    }

    // Getter method
    public double[][] getFirstMatrix() {
        return firstMatrix;
    }

    // Method for adding two matrices.
    public TheMatrix addition(TheMatrix addMatrix){
    // Must check if firstMatrix and addMatrix are compatible for addition.
    // I.e. they must have the same number of rows and columns
        double[][] otherMatrix = addMatrix.getFirstMatrix();
//        otherMatrix.length; // Check number of columns
//        otherMatrix[0].length; // Check number of rows
//        firstMatrix.length; // Check number of columns
//        firstMatrix[0].length; // Check number of rows
        if(otherMatrix.length != firstMatrix.length || otherMatrix[0].length != firstMatrix[0].length){
            return null;
        }

        double[][] newMatrix = new double[otherMatrix.length][otherMatrix[0].length];
        for(int col = 0; col < otherMatrix.length; col++)
            for(int row = 0; row < otherMatrix[0].length; row++){
                newMatrix[col][row] = this.firstMatrix[col][row] + otherMatrix[col][row];
            }
        return new TheMatrix(newMatrix);
    }

    // Method for multiplying two matrices.
    public TheMatrix multiplication(TheMatrix multiMatrix){
        double[][] otherMatrix = multiMatrix.getFirstMatrix();
        if(this.firstMatrix.length != otherMatrix[0].length || firstMatrix[0].length != otherMatrix.length){
            return null;
        }
        double[][] newMatrix = new double[otherMatrix.length][otherMatrix[0].length];

        for(int col=0; col < otherMatrix.length; col++)
            for(int row=0; row < otherMatrix[0].length; row ++)
                for(int i=0; i < otherMatrix.length; i++){
                    newMatrix[col][row] += this.firstMatrix[i][row] * otherMatrix[col][i];
                }
        return new TheMatrix(newMatrix);
    }

    // Method for transposing.
    public TheMatrix transpose(){
        double[][] newMatrix = new double[firstMatrix[0].length][firstMatrix.length];

        for(int col = 0; col < firstMatrix[0].length; col++)
            for(int row = 0; row < firstMatrix.length; row ++){
                newMatrix[col][row] = firstMatrix[row][col];
            }
        return new TheMatrix(newMatrix);
    }
}




/*
Lag en klasse Matrise. Den skal inneholde en todimensjonal tabell som svarer til en matrise i
matematikken. Lag metoder for å
[X] Addere en matrise til denne matrisen
[] Multiplisere denne matrisen med en annen matrise
[] Transponere matrisen.
Klassen skal være immutabel. Det vil si at alle metodene må lage nye metoder som returneres.
Dersom operasjonene er umulige, skal metodene returnere null.
Lag et enkelt klientprogram som kan brukes til å teste klassen.
 */