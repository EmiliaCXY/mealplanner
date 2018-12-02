package ui.MainGUI.MenuFrames;

import module.Plans.PlanComponents.PlanForAMeal;
import module.RecipeLibrary.RecipeLibrary;
import module.Recipes.Recipe;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

import static ui.MainGUI.MainFrame.colorB;

public abstract class Tab extends JFrame{
    protected ArrayList<Recipe> recipes;
    protected ArrayList<Recipe> defaultRecipes;
    protected ArrayList<Recipe> userRecipes;
    protected ArrayList<PlanForAMeal> plans;

    public Tab(String title)  {
        super(title);
        setSize(500, 500);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        RecipeLibrary recipeLibrary = new RecipeLibrary();
        getContentPane().setBackground(colorB);
        setVisible(true);
        try {
            recipeLibrary.load();
            ArrayList<Recipe> recipes = new ArrayList<>();
            defaultRecipes = recipeLibrary.getDefRecipes();
            recipes.addAll(defaultRecipes);
            userRecipes = recipeLibrary.getUserRecipes();
            recipes.addAll(userRecipes);

        } catch (IOException e) {
            System.out.println("error in loading library");
        }
    }
}
