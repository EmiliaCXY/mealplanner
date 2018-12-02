package module.Recipes.RecipeComponents;


import module.Exceptions.ProcedureInvalidException;
import module.Interfaces.Components;

import java.util.ArrayList;
import java.util.Arrays;

public class Procedure implements Components {
    private int stepNumber;
    private String direction;

    // step:direction

    public Procedure(){}

    // REQUIRES: step > 0
    // MODIFIES: this
    // EFFECT: construct a procedure
    public Procedure(int step, String direction){
        stepNumber = step;
        this.direction = direction;
    }

    // MODIFIES: this
    // EFFECT: assign direction to the parameter passed into the method
    public void setDirection(String direction) {
        this.direction = direction;
    }

    // REQUIRES: stepNumber>=1
    // EFFECT: set the step number and it is 1-based indexing
    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    // EFFECT: return the direction instruction
    public String getDirection() {
        return direction;
    }

    // EFFECT: return the step number
    public int getStepNumber() {
        return stepNumber;
    }

    // EFFECTS: print out the connect of a procedure
    @Override
    public void printing(){
        System.out.println(stepNumber+":"+direction);
    }

    // EFFECTS: return true if all the fields in a procedure contain some information
    public void checkValidity() throws ProcedureInvalidException {
        if(stepNumber>0 && !(direction ==null) && !(direction.equals(""))){
        }else{
            throw new ProcedureInvalidException();
        }
    }

    public String toString(){
        return stepNumber + ":" + direction+":";
    }

    public String toStringD(){
        return stepNumber+" "+direction;
    }

    public Procedure fromString(String input){
        ArrayList<String> proInstru = split(input);
        int step = Integer.getInteger(proInstru.get(0));
        String direction = proInstru.get(1);
        return new Procedure(step,direction);
    }

    private ArrayList<String> split(String line){
        String[] splits = line.split(":");
        return new ArrayList<>(Arrays.asList(splits));
    }
}
