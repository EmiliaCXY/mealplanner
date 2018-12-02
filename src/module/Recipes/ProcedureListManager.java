package module.Recipes;

import module.Exceptions.IngredientInvalidException;
import module.Exceptions.ProcedureInvalidException;
import module.Exceptions.RecipeBasicInfoInvalidException;
import module.Interfaces.Components;
import module.Recipes.RecipeComponents.Procedure;

import java.util.ArrayList;
import java.util.Scanner;

public class ProcedureListManager {

    private ArrayList<Procedure> procedures;

    public ProcedureListManager(){
        procedures = new ArrayList<>();
    }

    public ProcedureListManager(ArrayList<Procedure> procedures){ this.procedures = procedures;}
    // MODIFIES: this and Procedures
    // EFFECT: change the procedures of the recipe
    public void setProcedures(ArrayList<Procedure> procedures) {
        this.procedures = procedures;
    }

    public ArrayList<Procedure> getProcedures() {
        return procedures;
    }

    // REQUIRES: 0 < step < Procedures.size()
    //   the procedures should have been set up
    // EFFECT: return the procedure of a certain step
    public Procedure getStepProcedure(int step) {
        Procedure p = procedures.get(step - 1);
        return p;
    }

    public void add(Procedure p){
        procedures.add(p);
    }

    public void remove(Procedure p){
        procedures.remove(p);
    }

    public boolean isEmpty(){
        return procedures.isEmpty();
    }

    public void checkValidity() throws RecipeBasicInfoInvalidException, IngredientInvalidException, ProcedureInvalidException {
        if(procedures.isEmpty()){
            throw new ProcedureInvalidException();
        }
        for (Components p : procedures) {
            p.checkValidity();
        }
    }

    // MODIFIES: this and Procedures
    // EFFECT: give users instructions to set up the procedures of their recipes and store them in RecipeLibrary
    public void addProcedure() {
        System.out.println("Now, please input the procedure of the dish!");
        for (int i = 0; i <= 0; i++) {
            System.out.println("Step" + (i + 1) + ":");
            Scanner proce = new Scanner(System.in);
            String procedure = proce.nextLine();
            Procedure newStep = new Procedure(i + 1, procedure);
            procedures.add(newStep);
        }


    }

    public void insertingOneProcedure(){
        printPro();
        int stepNum = constructOneProcedure().getStepNumber();
        procedures.add((stepNum-1), constructOneProcedure());
        modifyStepNum(stepNum);
        System.out.println("This is the new procedure!");
        printPro();
    }


    // MODIFIES: this and Procedure
    // EFFECTS: add one procedure to the recipe
    private Procedure constructOneProcedure(){
        System.out.println("Where do you want to add the new step to?");
        Scanner proce = new Scanner(System.in);
        int stepNum = proce.nextInt();
        System.out.println("Please type in the new step");
        String newCooking = proce.nextLine();
        return new Procedure(stepNum,newCooking);
    }

    // REQUIRES: stepNum>0
    // MODIFIES: Procedures and this
    // EFFECTS: add one to the stepNum of steps after stepNum
    private void modifyStepNum(int stepNum){
        for(Procedure p: procedures){
            int pNum = p.getStepNumber();
            if(pNum>=  stepNum){
                p.setStepNumber(pNum+1);
            }
        }
    }

    public void modifyingProcedure(){
        printPro();
        Scanner input = new Scanner(System.in);
        System.out.println("Which step do you want to change?");
        int step = input.nextInt();
        Procedure needChange = getStepProcedure(step);
        System.out.println("What is the new step?");
        String newCooking = input.nextLine();
        Procedure changeInto = new Procedure(step,newCooking);
        changeProcedure(needChange,changeInto);
        System.out.println("This is the new procedure!");
        printPro();
    }

    // REQUIRES: the needChange procedure has to be an item in the procedure list
    // MODIFIES: this and Procedures
    // EFFECT: remove the needChange procedure and add the changeInto
    public void changeProcedure(Procedure needChange, Procedure changeInto) {
        int index = needChange.getStepNumber() - 1;
        procedures.remove(index);
        procedures.add(index, changeInto);

    }

    protected void printPro(){
        System.out.println("Procedures: ");
        for (Components p : procedures) {
            p.printing();
        }
    }

}
