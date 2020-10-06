package solver.domain;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComplexNumber {
    private final String complexNumber;

    public ComplexNumber(String value) {
        this.complexNumber = value;
    }

    public String getComplexNumber() {
        return complexNumber;
    }

    public ComplexNumber add(ComplexNumber add){
        double realSum = realValue()+realValue(add);
        double imaginarySum = imaginaryValue()+imaginaryValue(add);
        return new ComplexNumber(toString(realSum,imaginarySum));
    }

    public ComplexNumber multiply(double k){
        double real = realValue()*k;
        double imaginary = imaginaryValue()*k;
        return new ComplexNumber(toString(real,imaginary));
    }

    public ComplexNumber multiply(ComplexNumber cN){
        double real = realValue()*realValue(cN)+(imaginaryValue()*imaginaryValue(cN)*-(1));
        double imaginary = imaginaryValue()*realValue(cN)+realValue()*imaginaryValue(cN);
        return new ComplexNumber(toString(real,imaginary));
    }

    public ComplexNumber divide(ComplexNumber cN){
        double real = 0;
        double imaginary=0;
        if (realValue(cN)!=0) {
            real = realValue()/realValue(cN);
            imaginary = imaginaryValue()/realValue(cN);
        }
        return new ComplexNumber(toString(real,imaginary));
    }

    private String toString(double real, double imaginary){
        DecimalFormat decimalFormat =  new DecimalFormat("#.#####");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        String realS=real==0? "": decimalFormat.format(real);
        String imaginaryS = imaginary==0? "": imaginary +"i";
        if(realS.isEmpty()&&imaginaryS.isEmpty()){
            realS="0";
        }
        return String.format("%s"+(imaginary>0&&real!=0?"+":"")+(imaginary!=0?"%s":""),realS,imaginaryS);
    }

    public ComplexNumber conjugation(){
        ComplexNumber complexNumber = this;
       if(complexNumber.toString().contains("+")){
           complexNumber= new ComplexNumber(complexNumber.toString().replace("+", "-"));
       }else if(complexNumber.toString().contains("-")){
           complexNumber=new ComplexNumber(complexNumber.toString().replace("-","+"));
       }
       if(complexNumber.toString().startsWith("+")){
           complexNumber = new ComplexNumber(complexNumber.toString().replace("+","-"));
       }if(complexNumber.toString().equals("i")){
           complexNumber=new ComplexNumber("-i");
        }
       return complexNumber;
    }

    private double realValue(){
        return realValue(this);
    }

    private double realValue(ComplexNumber complexNumber){
        Pattern pattern =  Pattern.compile("(^[+-]*\\d+\\.*\\d*)([+-]|$)");
        Matcher matcher = pattern.matcher(complexNumber.toString());
        if(matcher.find()){
            return Double.parseDouble(matcher.group(1).replace("+",""));
        }
        return 0;
    }

    private double imaginaryValue(){
        return imaginaryValue(this);
    }

    private double imaginaryValue(ComplexNumber complexNumber){
        Pattern pattern =  Pattern.compile("([+-]*\\d*\\.*\\d*)([i])");
        Matcher matcher = pattern.matcher(complexNumber.toString());
        if(matcher.find()){
            ///System.out.println("matcher imaginary  " +matcher.group(1));
            if(matcher.group(1).equals("-")){
                return -1;
            }else if(matcher.group(1).equals("+")||matcher.group(1).equals("")){
                return 1;
            }
            return Double.parseDouble(matcher.group(1).replace("+",""));
        }
        return 0;
    }

    @Override
    public String toString() {
        return complexNumber;
    }
}
