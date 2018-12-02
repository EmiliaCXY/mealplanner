package test;

import module.Recipes.RecipeComponents.Ingredient;
import module.Recipes.RecipeComponents.Procedure;
import module.Recipes.UserRecipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static module.Recipes.RecipeComponents.Measurement.GRAM;
import static module.Recipes.RecipeComponents.Measurement.NULL;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUserRecipe {

    public UserRecipe r;
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
    public void testChangeIngredient(){
        Ingredient i4 = new Ingredient("rice",100,GRAM);
        Ingredient needChange = r.getIngres().getIndexIngredient(2);
        r.getIngres().changeIngredient(needChange,i4);
        ArrayList<Ingredient> ingreList = r.getIngres().getIngredients();
        int size = ingreList.size();
        Ingredient changed = r.getIngres().getIndexIngredient(size);
        assertEquals(changed.getMaterial(),"rice");
        assertEquals(changed.getAmount(),100);

    }

    @Test
    public void testChangeProcedure(){
        Procedure p3 = new Procedure(2,"stir flying for 3 minutes");
        Procedure needChange = r.getPros().getStepProcedure(2);
        r.getPros().changeProcedure(needChange, p3);
        Procedure changed = r.getPros().getStepProcedure(2);
        assertEquals(changed.getStepNumber(),2);
        assertEquals(changed.getDirection(),"stir flying for 3 minutes");

    }
}
