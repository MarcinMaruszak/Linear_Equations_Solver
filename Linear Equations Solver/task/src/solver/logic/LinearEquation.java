package solver.logic;

import solver.domain.ComplexNumber;
import solver.domain.Matrix;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;


public class LinearEquation {
    private final Matrix matrix;
    private boolean noSolutions;
    private boolean infiniteSolutions;

    public LinearEquation() {
        this.matrix= new Matrix();
        this.noSolutions=false;
        this.infiniteSolutions=false;
    }

    public void solve(){
        matrix.print();
        System.out.println("Start solving the equation.");
        matrixChecks();
        matrix.sort();
        matrix.print();
        System.out.println();
        matrixZeroBottom();
        matrixZeroTop();

    }

    private void matrixZeroBottom(){
        for (int i = 0; i<matrix.getMatrix().length;i++) {
            ComplexNumber value;
            value = matrix.getFirstNonZero(i);
            if(value.toString().contains("i")){
                System.out.println("R"+(i+1)+" / "+value+ " -> R"+(i+1));
                ComplexNumber [] row = matrix.divide(value, matrix.getRow(i));
                matrix.setRow(row,i);
                matrixChecks();
                matrix.sort();
                ///matrix.print();
                //System.out.println();
                if(noSolutions||infiniteSolutions){
                    break;
                }
            }else {
                double d = Double.parseDouble(value.toString());
                //System.out.println("value  "+value);
                if(!value.toString().equals("1")){
                    System.out.println((1/d)+" * R"+(i+1)+" -> R"+(i+1));
                    ComplexNumber [] row = matrix.multiply((1.0/d),i);
                    matrix.setRow(row,i);
                    matrix.sort();
                    matrixChecks();
                    //matrix.print();
                    //System.out.println();
                    if(noSolutions||infiniteSolutions){
                        break;
                    }
                }
            }
            for(int j =i+1;j< matrix.getMatrix().length;j++){
                if(!matrix.getMatrix()[j][i].toString().equals("0")){
                    value = matrix.getFirstNonZero(j).multiply(-1);
                    System.out.println(value +" * R"+(i+1) +" + R"+(j+1)+" -> R"+(j+1));
                    ComplexNumber [] row = matrix.multiply(value,i);
                    try {
                        row=matrix.addRows(row,matrix.getRow(j));
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                    matrix.setRow(row,j);
                    matrixChecks();
                    //matrix.print();
                    //System.out.println();
                    if(noSolutions||infiniteSolutions){
                        break;
                    }
                }
            }
        }
    }
    private void matrixZeroTop(){
        if (!noSolutions&&!infiniteSolutions) {
            for(int i = matrix.getMatrix().length-2;i>=0;i--){
                for(int j = matrix.getMatrix()[0].length-2;j>i;j--){
                    ComplexNumber value = matrix.getMatrix()[i][j];
                    value=value.multiply(-1);
                    if(!value.toString().equals("0")){
                        System.out.println((value)+" * R"+ (j+1) +" + R"+(i+1)+" -> R"+(i+1));
                        try {
                            ComplexNumber [] row = matrix.multiply(value,j);
                            row=matrix.addRows(row,matrix.getRow(i));
                            matrix.setRow(row,i);
                        } catch (Exception e) {
                            //e.printStackTrace();
                        }
                        matrix.print();
                    }
                }
            }

        }
        matrix.print();
        matrix.printSolution();
    }

    public void loadFromFile(String fileName){
        try(Scanner scanner = new Scanner(new File(fileName))) {
            String s = scanner.nextLine();
            int [] ints = Arrays.stream(s.split(" ")).mapToInt(Integer::parseInt).toArray();
            ComplexNumber [][] matrix = new ComplexNumber[ints[1]][];
            int i =0;
            while (scanner.hasNext()){
                String line = scanner.nextLine();
                String [] split= line.split(" ");
                ComplexNumber[] row = new ComplexNumber[split.length];
                for (int j = 0;j<split.length;j++){
                    row[j]=new ComplexNumber(split[j]);
                }
                matrix[i]=row;
                i++;
            }
            this.matrix.setMatrix(matrix);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile(String fileName){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName)))) {

            if (noSolutions) {
                System.out.println("No Solutions");
                writer.write("No solutions");
            }else if (infiniteSolutions){
                System.out.println("Infinitely many solutions");
                writer.write("Infinitely many solutions");
            }else {
                for(ComplexNumber[] row : matrix.getMatrix()){
                    writer.write(String.valueOf(row[row.length-1].toString()));
                    writer.write(System.lineSeparator());
                }
            }
            System.out.println("Saved to file "+fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void matrixChecks(){
        matrix.trim();
        infiniteSolutions=matrix.infiniteSolutions();
        noSolutions=matrix.checkNoSolution();
    }


}
