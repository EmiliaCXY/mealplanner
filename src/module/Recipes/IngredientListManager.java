package module.Recipes;

import module.Exceptions.IngredientInvalidException;
import module.Exceptions.ProcedureInvalidException;
import module.Exceptions.RecipeBasicInfoInvalidException;
import module.Interfaces.Components;
import module.Recipes.RecipeComponents.Ingredient;
import module.Recipes.RecipeComponents.Measurement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class IngredientListManager {

    private ArrayList<Ingredient> ingredients;

    public IngredientListManager(){
        ingredients = new ArrayList<>();
    }

    public IngredientListManager(ArrayList<Ingredient> ingredients){ this.ingredients = ingredients;}

    // MODIFIES: this and Ingredients
    // EFFECT: change the ingredients the recipe takes
    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    // EFFECT: return a list of intredients in the recipe
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void add(Ingredient i){
        ingredients.add(i);
    }

    public void remove(Ingredient i){
        ingredients.remove(i);
    }

    public boolean isContain(Ingredient i){
        return ingredients.contains(i);
    }

    public boolean isEmpty(){
        return ingredients.isEmpty();
    }

    public void checkValidity() throws RecipeBasicInfoInvalidException, IngredientInvalidException, ProcedureInvalidException {
        if(ingredients.isEmpty()){
            throw new IngredientInvalidException();
        }
        for (Components i : ingredients) {
            i.checkValidity();
        }
    }

    // REQUIRES: 0 < index < Ingredients.size()
    //   the ingredients should have been set up
    // EFFECT: return the ingredient at a specific position
    public Ingredient getIndexIngredient(int index) {
        return ingredients.get(index - 1);
    }

    // MODIFIES: this and Ingredients
    // EFFECT: give users instruction to set up the ingredients of a recipe and store it in the RecipeLibrary
    public void addIngredient() {
        for (int i = 0; i <= 0; i++) {
            ingredients.add(constructOneIngredient());
        }
    }
    // MODIFIES: this and Ingredient
    // EFFECTS: add a new ingredient
    public Ingredient constructOneIngredient(){
        Scanner input =  new Scanner(System.in);
        System.out.println("Please type in the material");
        String material = input.nextLine();
        System.out.println("Please type in the amount of it");
        double amount = input.nextDouble();
        input.nextLine();
        System.out.println("Please type in the measurement of the amount");
        String meas = input.nextLine();
        Measurement theMeas = Measurement.valueOf(meas);
        return new Ingredient(material,amount,theMeas);
    }

    public void printIngre()  {
        System.out.println("Ingredients: ");
        for (Ingredient i : ingredients) {
            i.printing();
        }
    }

    public void modifyingIngredient(){
        printIngre();
        Scanner input = new Scanner(System.in);
        System.out.println("Please type in the index of the ingredient that you want to change");
        int index = input.nextInt();
        Ingredient needChange = getIndexIngredient(index);
        Ingredient changeInto = constructOneIngredient();
        changeIngredient(needChange,changeInto);
        System.out.println("This is the new ingredient list!");
        printIngre();
    }
    // REQUIRES: the needChange ingredient has to be an element in the ingredient list
    // MODIFIES: this and Ingredients
    // EFFECT: remove the needChange and add the changeInto the ingredients
    public void changeIngredient(Ingredient needChange, Ingredient changeInto) {
        for (Ingredient i : ingredients) {
            if (needChange.getMaterial().equals(i.getMaterial())) {
                ingredients.remove(i);
            }
        }
        ingredients.add(changeInto);

    }

}
