package test;

import module.Exceptions.CannotFindRecipeException;
import module.RecipeLibrary.RecipeLibrary;
import module.Recipes.DefaultRecipe;
import module.Recipes.RecipeComponents.Ingredient;
import module.Recipes.RecipeComponents.Procedure;
import module.Recipes.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static module.Recipes.RecipeComponents.Measurement.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


public class TestRecipeLibrary {

    RecipeLibrary rl;
    DefaultRecipe r;
    DefaultRecipe rr;

    @BeforeEach
    public void setUp(){
        rl = new RecipeLibrary();
        r = new DefaultRecipe();
        r.setName("scrambled-eggs");
        r.setCalorie(100);
        r.setTime(5);
        r.setCategory("Default");
        Ingredient i = new Ingredient("eggs", 5,NULL);
        Procedure p = new Procedure(1, "crack eggs into a bow and beat the eggs");
        ArrayList<Ingredient> theIngre = new ArrayList<>();
        ArrayList<Procedure> thePro = new ArrayList<>();
        theIngre.add(i);
        thePro.add(p);
        r.setProcedures(thePro);
        r.setIngredients(theIngre);

        rr = new DefaultRecipe();
        rr.setName("Noodle Soup");
        rr.setCalorie(500);
        rr.setTime(30);
        rr.setCategory("Default");
        rr.setIngredients(theIngre);
        rr.setProcedures(thePro);

        rl.storeRecipe(r);
        rl.storeRecipe(rr);
    }

    @Test
    public void testGetSize(){
        int size = rl.size();
        assertTrue(size == 2);
        }



    @Test
    public void testSearchRecipeExpectNoExpection() throws IOException {
        try{
            Recipe testDefaultRecipe = rl.searching("scrambled-eggs");
            assertEquals(testDefaultRecipe,r);
        }catch (CannotFindRecipeException e){
            fail("It is the recipe in the library.");
        }
    }

    @Test
    public void testSearchRecipeExpectCannotFindException() throws IOException {
        try{
            rl.searching("Apple");
            fail("It's not a recipe in the library");
        } catch (CannotFindRecipeException e){
            // nothing needs to be done
        }

    }

    @Test
    public void testLoad() throws IOException {
        RecipeLibrary testLibrary = new RecipeLibrary();
        testLibrary.load();
        HashMap<String, ArrayList<Recipe>> readLibrary = testLibrary.getRecipeLibrary();
        List<Recipe> recipes = readLibrary.get("Default");
        Recipe r1 = recipes.get(0);
        Recipe r2 = recipes.get(1);
        Recipe r3 = recipes.get(2);
        assertEquals(r1.getName(),"Scrambled Eggs");
        assertEquals(r2.getName(),"Honey Yogurt Cheesecake");
        assertEquals(r3.getName(),"Banana Bread");

    }

    @Test
    public void testSave() throws IOException {
        RecipeLibrary oldLibrary = new RecipeLibrary();
        oldLibrary.load();
        oldLibrary.storeRecipe(r);
        oldLibrary.save();

        oldLibrary.load();
        HashMap<String, ArrayList<Recipe>> readLibrary = oldLibrary.getRecipeLibrary();
        List<Recipe> recipes = readLibrary.get("Default");
        Recipe r4 = recipes.get(recipes.size()-1);
        assertEquals(r4,r);


    }




}
