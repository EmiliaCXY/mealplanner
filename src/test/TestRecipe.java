package test;


import module.Recipes.RecipeComponents.Ingredient;
import module.Recipes.RecipeComponents.Procedure;
import module.Recipes.Recipe;
import module.Recipes.UserRecipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static module.Recipes.RecipeComponents.Measurement.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRecipe {

    private Recipe r;
    private ArrayList<Ingredient> i;
    private ArrayList<Procedure> p;
    private Ingredient i1;
    private Ingredient i2;
    private Ingredient i3;
    private Procedure p1;
    private Procedure p2;


    @BeforeEach
    public void setUp(){
        r = new UserRecipe();
        i = new ArrayList<>();
        p = new ArrayList<>();
        i1 = new Ingredient("eggs",3,NULL);
        i2 = new Ingredient("tomatoes",2,NULL);
        i3 = new Ingredient("beef",100,GRAM);
        p1 = new Procedure(1, "crack the eggs");
        p2 = new Procedure(2, "add oil to a flying pan");
        i.add(i1);
        i.add(i2);
        i.add(i3);
        r.setIngredients(i);
        p.add(p1);
        p.add(p2);
        r.setProcedures(p);
    }

    @Test
    public void testGetIndexIngredientTheFirstIngre(){
        Ingredient testI = r.getIngres().getIndexIngredient(1);
        assertEquals(testI.getMaterial(), i1.getMaterial());
        assertEquals(testI.getAmount(), i1.getAmount());

    }

    @Test
    public void testGetIndexIngredientTheMiddleIngre(){
        Ingredient testI = r.getIngres().getIndexIngredient(2);
        assertEquals(testI.getMaterial(), i2.getMaterial());
        assertEquals(testI.getAmount(), i2.getAmount());
    }

    @Test
    public void testGetIndexIngredientTheLastIngre(){
        Ingredient testI = r.getIngres().getIndexIngredient(3);
        assertEquals(testI.getMaterial(), i3.getMaterial());
        assertEquals(testI.getAmount(), i3.getAmount());
    }






}
