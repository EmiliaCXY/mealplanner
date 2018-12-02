package module.Recipes;

import module.Exceptions.IngredientInvalidException;
import module.Exceptions.ProcedureInvalidException;
import module.Exceptions.RecipeBasicInfoInvalidException;
import module.Interfaces.Components;
import module.Recipes.RecipeComponents.Ingredient;
import module.Recipes.RecipeComponents.MatchHelper;
import module.Recipes.RecipeComponents.Procedure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public abstract class Recipe extends MatchHelper implements Components {
    protected String name;
    protected BasicInfoManager basicInfo;
    protected IngredientListManager ingredients;
    protected ProcedureListManager procedures;
    protected ArrayList<Recipe> relatedRecipes;

    public Recipe(){
        name = "";
        basicInfo = new BasicInfoManager();
        ingredients = new IngredientListManager();
        procedures = new ProcedureListManager();
        relatedRecipes = new ArrayList<>();
    }

    public Recipe(String name, BasicInfoManager basicInfo, IngredientListManager ingredients, ProcedureListManager procedures) {
        this.name = name;
        this.basicInfo = basicInfo;
        this.ingredients = ingredients;
        this.procedures = procedures;
        relatedRecipes = match();
    }

    // Setters:
    public void setName(String name) {
        this.name = name;
    }
    public void setTime(int t){this.basicInfo.setTime(t);}
    public void setCalorie(double c){this.basicInfo.setCalorie(c);}
    public void setCategory(String s){this.basicInfo.setCategory(s);}
    public void setBasicInfo(BasicInfoManager basicInfo) {this.basicInfo = basicInfo; }
    public void setIngredients(ArrayList<Ingredient> ingredients) { this.ingredients.setIngredients(ingredients); }
    public void setProcedures(ArrayList<Procedure> procedures) { this.procedures.setProcedures(procedures); }
    public void setRelatedRecipes(ArrayList<Recipe> relatedRecipes){this.relatedRecipes = relatedRecipes;}

    // Getters:
    public String getName() {
        return name;
    }
    public int getTime(){return this.basicInfo.getTime();}
    public double getCalorie(){return this.basicInfo.getCalorie();}
    public String getCategory(){ return this.basicInfo.getCategory();}
    public BasicInfoManager getBasicInfo() {return basicInfo; }
    public IngredientListManager getIngres() {return ingredients; }
    public ProcedureListManager getPros() { return procedures; }
    public ArrayList<Recipe> getRelatedRecipes(){return relatedRecipes;}


    public void addOneIngredient(Ingredient i){
        if(!ingredients.isContain(i)){
            ingredients.add(i);
            i.addOneRecipe(this);
        }
    }

    public void removeOneIngredient(Ingredient i){
        if(ingredients.isContain(i)){
            ingredients.remove(i);
            i.removeOneRecipe(this);
        }
    }
    // EFFECTS: print out the content of the recipe
    public void printing() {
        System.out.println(name);
        System.out.println("-----------------------------");
        basicInfo.printInfo();
        System.out.println("-----------------------------");
        ingredients.printIngre();
        System.out.println("-----------------------------");
        procedures.printPro();
        }

    public void viewingRecipe(){
        printing();
        Scanner input = new Scanner(System.in);
        System.out.println("Do you want to modify this recipe?");
        String anotherChoice = input.nextLine();
        if(anotherChoice.equalsIgnoreCase("yes")){
            changing();
        }
    }
    public void checkValidity() throws RecipeBasicInfoInvalidException, IngredientInvalidException, ProcedureInvalidException {
        if (!((name.equals(""))|| getCalorie() <= 0 || getTime() <=0) ) {
            ingredients.checkValidity();
            procedures.checkValidity();
        } else{
            throw new RecipeBasicInfoInvalidException();
        }
    }

    // REQUIRES: the recipe has to have valid info
    // EFFECTS: return a string recording the info in the recipe
    @Override
    public String toString(){
        return name+":"+getTime()+":"+getCalorie()+":"+getCategory()+":";
    }


    // MODIFIES: this and Ingredient and Procedure
    // EFFECTS: modifies a recipe
    public abstract void changing();

    // EFFECTS: return a string on the category of the recipe
    protected abstract String returnLabel();

    public ArrayList<Recipe> match() {
        ArrayList<Ingredient> ingredientList = getIngres().getIngredients();
        ArrayList<Recipe> recipes = new ArrayList<>();
        for(Ingredient i: ingredientList){
            recipes.addAll(i.match());
        }
        setRelatedRecipes(recipes);
        return recipes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(name, recipe.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
