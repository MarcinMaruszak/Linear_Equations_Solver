package solver.userInterface;

import solver.logic.LinearEquation;


public class UI {
    private String [] input;
    LinearEquation linearEquation;

    public UI(String [] input) {
        this.input = input;
        linearEquation=new LinearEquation();
    }

    public void start(){
        linearEquation.loadFromFile(input[1]);
        linearEquation.solve();
        linearEquation.saveToFile(input[3]);
    }
}
