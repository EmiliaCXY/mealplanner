package module.Recipes.RecipeComponents;

import module.RecipeLibrary.RecipeLibrary;
import module.Recipes.Recipe;

import java.io.IOException;
import java.util.ArrayList;

public abstract class MatchHelper {

    public abstract ArrayList<Recipe> match() throws IOException;
}
