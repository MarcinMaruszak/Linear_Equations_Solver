package solver.domain;

import java.util.ArrayList;
import java.util.Arrays;

public class Matrix {
    private double[][] matrix;

    public Matrix() {
        this.matrix = null;
    }

    public void setMatrix(int x) {
        this.matrix = new double[x][];
    }

    public void setRow(double[] row, int i) {
        this.matrix[i] = row;
    }


    public double[][] getMatrix() {
        return matrix;
    }

    public void print() {
        for (double[] row : matrix) {
            for (double d : row) {
                System.out.print(d + " ");
            }
            System.out.println();
        }
    }

    public void printSolution() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("The Solution is: (");
        for (double[] row : matrix) {
            stringBuilder.append(row[row.length - 1]).append(", ");
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append(")");
        System.out.println(stringBuilder.toString());
    }

    public void sort() {
        Arrays.sort(matrix, (o1, o2) -> {
            int i1 = 0;
            for (double d : o1) {
                if (d == 0) {
                    i1++;
                } else {
                    break;
                }
            }
            int i2 = 0;
            for (double d : o2) {
                if (d == 0) {
                    i2++;
                } else {
                    break;
                }
            }
            return i1 - i2;
        });
    }

    public boolean checkNoSolution() {
        boolean noSolutions = false;
        for (double[] row : matrix) {
            int i = 0;
            for (double d : row) {
                if (d == 0) {
                    i++;
                }
                if (i == row.length - 1 && row[row.length - 1] != 0) {
                    noSolutions = true;
                }
            }
        }
        return noSolutions;
    }

    public void trim() {
        ArrayList<double[]> matrixTemp = new ArrayList<>();
        for (double[] row : matrix) {
            int i = 0;
            for (double d : row) {
                if (d == 0) {
                    i++;
                }
            }
            if (i != row.length) {
                matrixTemp.add(row);
            }
        }
        matrix=new double[matrixTemp.size()][];
        for (int i =0;i<matrixTemp.size();i++) {
            matrix[i]=matrixTemp.get(i);
        }
    }

    public boolean infiniteSolutions() {
        return matrix.length<matrix[0].length-1;
    }
}