package module.Recipes;

import module.Recipes.RecipeComponents.Ingredient;
import ui.ConsoleInteraction.Instruction;

import java.io.IOException;
import java.util.Scanner;

public class UserRecipe extends Recipe{

    public UserRecipe() {
    }

    public UserRecipe(String name, BasicInfoManager basicInfo, IngredientListManager ingredients, ProcedureListManager procedures){
        super(name, basicInfo, ingredients, procedures);
    }

    // MODIFIES: this and Procedure and Ingredient
    // EFFECT: construct a new recipe and gives users instruction to add ingredients and procedures and store user input
    public void buildUserRecipe() {
        collectName();
        basicInfo.collectInfo(this.name);
        ingredients.addIngredient();
        procedures.addProcedure();
        match();
    }

    private void collectName(){
        Scanner str = new Scanner(System.in);
        System.out.println("Name of the dish:");
        String name1 = str.nextLine();
        setName(name1);
    }



    // MODIFIES: this
    // EFFECTS: gives instruction on adding an ingredient or a procedure and store the change
    private void adding(){
        System.out.println("Do you want to add an ingredient or a step?");
        Scanner input = new Scanner(System.in);
        String choice = input.nextLine();
        if(choice.equalsIgnoreCase("ingredient")){
            Ingredient i = ingredients.constructOneIngredient();
            ingredients.add(i);
        }else if(choice.equalsIgnoreCase("step")){
            procedures.insertingOneProcedure();
        }
    }

    // MODIFIES: this
    // EFFECT: give instruction about modifying a recipe
    public void changing(){
        Instruction instruction = new Instruction();
        instruction.changingRecipeInstru();
        Scanner input =  new Scanner(System.in);
        String choice = input.nextLine();
        if(choice.equalsIgnoreCase("a")){
            System.out.println("Please type in the new name.");
            String newName = input.nextLine();
            setName(newName);
        } else if(choice.equalsIgnoreCase("b")){
            System.out.println("Please type in the new time.");
            int newTime = input.nextInt();
            setTime(newTime);
        } else if(choice.equalsIgnoreCase("c")){
            System.out.println("Please type in the new calorie value.");
            double newCalorie = input.nextDouble();
            setCalorie(newCalorie);
        } else if(choice.equalsIgnoreCase("d")){
            ingredients.modifyingIngredient();
        } else if(choice.equalsIgnoreCase("e")){
            procedures.modifyingProcedure();
        } else if(choice.equalsIgnoreCase("f")){
            adding();
        }
        System.out.println("Your change has been saved.");


    }



    // EFFECTS: return the type of the recipe
    @Override
    protected String returnLabel(){
        return "User Recipe";
    }


}
