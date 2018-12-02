package module.Recipes.RecipeComponents;


import module.Exceptions.IngredientInvalidException;
import module.Interfaces.Components;
import module.RecipeLibrary.RecipeLibrary;
import module.Recipes.Recipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Ingredient extends MatchHelper implements Components {
    private String material;
    private double amount;
    private Measurement measurement;
    private List<Recipe> recipes;



    public Ingredient(){
        recipes = new ArrayList<>();
    }

    // REQUIRES: amount>0
    // MODIFIES: this
    // EFFECT: record an ingredient and its amount
    public Ingredient(String material, double amount, Measurement measurement){
        this.material = material;
        this.amount = amount;
        this.measurement = measurement;
        recipes = new ArrayList<>();
    }

    // EFFECT: return the material
    public String getMaterial(){
        return material;
    }

    public double getAmount() {
        return amount;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public List<Recipe> getRecipes() {return recipes;}

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    // REQUIRES: mat is a string with at least one letter
    // MODIFIES: this
    // EFFECT: set the material
    public void setMaterial(String mat){
        material = mat;
    }

    public void setRecipes(List<Recipe> recipes){this.recipes = recipes;}

    public void addOneRecipe(Recipe r){
        if(recipes.isEmpty()||!recipes.contains(r)){
            recipes.add(r);
            r.addOneIngredient(this);
        }
    }

    public void removeOneRecipe(Recipe r){
        if(recipes.contains(r)){
            recipes.remove(r);
            r.removeOneIngredient(this);
        }
    }

    public void printRecipeName(){
        for(Recipe r: recipes){
            System.out.println(r.getName());
        }
    }

    // REQUIRES: the ingredient is not null
    // EFFECT: print out the ingredient and its amount
    @Override
    public void printing(){
        System.out.println(material+":"+amount+" "+measurement);
    }

    @Override
    public String toString() {
        return material+":"+amount+":"+measurement+":";
    }

    public String toStringD(){
        if(amount==0){
            return measurement + " " + material;
        }else if(measurement.equals(Measurement.NULL)){
            return amount +" " +material;
        }else {
            return amount +" " + measurement + " " + material;
        }
    }

    public Ingredient fromString(String input) {
        ArrayList<String> ingreInfo = split(input);
        String material = ingreInfo.get(0);
        double amount = Double.parseDouble(ingreInfo.get(1));
        Measurement measurement = Measurement.valueOf(ingreInfo.get(2));
        return new Ingredient(material,amount,measurement);
        }

    private ArrayList<String> split(String line){
        String[] splits = line.split(":");
        return new ArrayList<>(Arrays.asList(splits));
    }

    // EFFECT: return true if the amount is greater than zero and the material has at least one letter
    public void checkValidity() throws IngredientInvalidException {
        if(amount>0 && !(material==null)&&!(material.equals(""))){
        }else{
            throw new IngredientInvalidException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(material, that.material);
    }

    @Override
    public int hashCode() {

        return Objects.hash(material);
    }

    @Override
    public ArrayList<Recipe> match() {
        ArrayList<Recipe> matchedRecipes = new ArrayList<>();
        ArrayList<Recipe> recipes = new ArrayList<>();
        RecipeLibrary rl = new RecipeLibrary();
        try {
            rl.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        recipes.addAll(rl.getRecipeLibrary().get("Default"));
        recipes.addAll(rl.getRecipeLibrary().get("User"));
        for(Recipe r: recipes){
            if(r.getName().equals(material)){
                matchedRecipes.add(r);
            }
        }
        return matchedRecipes;
    }
}
