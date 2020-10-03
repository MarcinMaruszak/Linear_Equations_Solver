package solver.logic;

import solver.domain.Matrix;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;


public class LinearEquation {
    private Matrix matrix;
    private boolean noSolutions;
    private boolean infiniteSolutions;

    public LinearEquation() {
        this.matrix= new Matrix();
        this.noSolutions=false;
        this.infiniteSolutions=false;
    }

    public void solve(){
        //matrix.print();
        System.out.println("Start solving the equation.");
        matrixChecks();
        //matrix.print();
        matrixZeroBottom();
        matrixZeroTop();

    }

    private void matrixZeroBottom(){
        for (int i = 0; i<matrix.getMatrix().length;i++) {
            double value;
            value = firstNoNZero(matrix.getMatrix()[i]);
            if(value!=-1){
                System.out.println((-1/value)+" * R"+(i+1)+" -> R"+(i+1));
                makeOne(matrix.getMatrix()[i]);
                matrixChecks();
                if(noSolutions||infiniteSolutions){
                    break;
                }
            }
            for(int j =i+1;j< matrix.getMatrix().length;j++){
                if(matrix.getMatrix()[j][i]!=0){
                    value = firstNoNZero(matrix.getMatrix()[j]);
                    System.out.println((value) +" * R"+j +" + R"+(j+1)+" -> R"+(j+1));
                    double [] row = matrix.getMatrix()[j];
                    addRows(row, multiplyRow(value,matrix.getMatrix()[i].clone()));
                    matrix.setRow(row,j);
                    //matrix.print();
                    matrixChecks();
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
                    double value = matrix.getMatrix()[i][j];
                    if(value!=0){
                        System.out.println((value*-1)+" * R"+ (j+1) +" + R"+(i+1)+" -> R"+(i+1));
                        try {
                            addRows(matrix.getMatrix()[i],multiplyRow((value*-1),matrix.getMatrix()[j].clone()));
                        } catch (Exception e) {
                            //e.printStackTrace();
                        }
                    }
                }
            }
            matrix.print();
            matrix.printSolution();
        }
    }

    private double firstNoNZero(double [] row){
        double value=0;
        for(double d: row){
            if(d!=0){
                value=d*-1;
                break;
            }
        }
        return value;
    }

    private void makeOne(double [] row){
        double first = 1.0/(firstNoNZero(row)*-1);
        if(first!=-1){
            for(int i=0; i< row.length;i++){
                if(row[i]!=0){
                    double d = row[i]*first;
                    d = Math.round(d*100000d)/100000d;
                    row[i] =d;
                }
            }
        }
    }

    private double[] multiplyRow(double value, double[] row){
        for(int i=0;i<row.length;i++){
            row[i]*=value;
        }
        return row;
    }

    private void addRows(double [] row, double [] row2){
        for(int i = 0; i< row.length;i++){
            row[i]+=row2[i];
        }
    }

    public void loadFromFile(String fileName){
        try(Scanner scanner = new Scanner(new File(fileName))) {
            String s = scanner.nextLine();
            int [] ints = Arrays.stream(s.split(" ")).mapToInt(Integer::parseInt).toArray();
            matrix.setMatrix(ints[1]);
            int i =0;
            while (scanner.hasNext()){
                String line = scanner.nextLine();
                double [] row = Arrays.stream(line.split(" "))
                        .mapToDouble(Double::parseDouble)
                        .toArray();
                matrix.setRow(row,i);
                i++;
            }

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
                for(double [] row : matrix.getMatrix()){
                    writer.write(String.valueOf(row[row.length-1]));
                    writer.write(System.lineSeparator());
                }
            }
            System.out.println("Saved to file "+fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void matrixChecks(){
        matrix.sort();
        matrix.trim();
        infiniteSolutions=matrix.infiniteSolutions();
        noSolutions=matrix.checkNoSolution();
    }


}
