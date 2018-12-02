package module.Recipes;


import java.io.IOException;
import java.util.ArrayList;

public class DefaultRecipe extends Recipe{

    public DefaultRecipe() {
    }

    public DefaultRecipe(String name, BasicInfoManager basicInfo, IngredientListManager ingredients, ProcedureListManager procedures)  {
        super(name, basicInfo, ingredients, procedures);
    }

    // EFFECTS: print out a sentence that default recipes cannot be changed
    @Override
    public void changing(){
        System.out.println("Sorry, you cannot change a default recipe.");


    }

    // EFFECTS: return a string on the category of the recipe
    @Override
    protected String returnLabel(){
        return "Default Recipe";
    }



}


