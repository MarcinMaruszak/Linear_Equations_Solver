package solver.domain;

import java.util.ArrayList;
import java.util.Arrays;

public class Matrix {
    private ComplexNumber [][] matrix;

    public Matrix() {
        this.matrix = null;
    }

    public void setMatrix(ComplexNumber[][] matrix) {
        this.matrix = matrix;
    }

    public void setRow(ComplexNumber[] row, int i) {
        this.matrix[i] = row;
    }

    public ComplexNumber[] getRow(int i){
        return matrix[i].clone();
    }

    public ComplexNumber[][] getMatrix() {
        return matrix;
    }

    public void print() {
        for (ComplexNumber[] row : matrix) {
            for (ComplexNumber d : row) {
                System.out.print(d + " ");
            }
            System.out.println();
        }
    }

    public ComplexNumber getFirstNonZero(int i){
        ComplexNumber value=new ComplexNumber("0");
        for(ComplexNumber d: matrix[i]){
            if(!d.toString().equals("0")){
                value=d;
                break;
            }
        }
        return value;
    }

    public ComplexNumber[] multiply(double d, int r){
        ComplexNumber[] row = matrix[r].clone();
        for(int i = 0 ; i <matrix[r].length;i++){
            row[i]=row[i].multiply(d);
        }
        return row;
    }

    public ComplexNumber[] multiply(ComplexNumber complexNumber, int r){
        ComplexNumber[] row = matrix[r].clone();
        for(int i = 0 ; i <matrix[r].length;i++){
            row[i]=row[i].multiply(complexNumber);
        }
        return row;
    }

    public ComplexNumber [] addRows(ComplexNumber[] r1, ComplexNumber[] r2){

        ComplexNumber [] row = new ComplexNumber[r1.length];
        for(int i=0;i<r1.length;i++){
            row[i]=r1[i].add(r2[i]);
        }
        return row;
    }

    public ComplexNumber[] divide(ComplexNumber complexNumber, ComplexNumber[] r1){
        ComplexNumber complexConjugation= complexNumber.conjugation();
        for(int i =0;i<r1.length;i++){
            ComplexNumber top = r1[i].multiply(complexConjugation);
            ComplexNumber bottom = complexNumber.multiply(complexConjugation);
            r1[i]=top.divide(bottom);
        }
        return r1;
    }

    public void printSolution() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("The Solution is: (");
        for (ComplexNumber[] row : matrix) {
            stringBuilder.append(row[row.length - 1]).append(", ");
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append(")");
        System.out.println(stringBuilder.toString());
    }

    public void sort() {
        Arrays.sort(matrix, (o1, o2) -> {
            int i1 = zeroes(o1);
            int i2 = zeroes(o2);
            return i1 - i2;
        });
    }

    private int zeroes(ComplexNumber [] row){
        int i = 0;
        for (ComplexNumber d : row) {
            if ("0".equals(d.getComplexNumber())) {
                i++;
            }else {
                break;
            }
        }
        return i;
    }

    public boolean checkNoSolution() {
        boolean noSolutions = false;
        for (ComplexNumber[] row : matrix) {
            int i = zeroes(row);
            if (i == row.length - 1 && !(row[row.length - 1]).toString().equals("0")) {
                noSolutions = true;
                break;
            }
        }
        return noSolutions;
    }

    public void trim() {
        ArrayList<ComplexNumber[]> matrixTemp = new ArrayList<>();
        for (ComplexNumber[] row : matrix) {
            int i = zeroes(row);
            if (i != row.length) {
                matrixTemp.add(row);
            }
        }
        matrix= new ComplexNumber[matrixTemp.size()][];
        for (int i =0;i<matrixTemp.size();i++) {
            matrix[i]=matrixTemp.get(i);
        }
    }

    public boolean infiniteSolutions() {
        return matrix.length<matrix[0].length-1;
    }
}