package test;

import module.Exceptions.IngredientInvalidException;
import module.Exceptions.ProcedureInvalidException;
import module.Exceptions.RecipeBasicInfoInvalidException;
import module.Interfaces.Components;
import module.Plans.PlanComponents.PlanForAMeal;
import module.Plans.PlanComponents.Meal;
import module.Recipes.BasicInfoManager;
import module.Recipes.DefaultRecipe;
import module.Recipes.RecipeComponents.Ingredient;
import module.Recipes.RecipeComponents.Procedure;
import module.Recipes.IngredientListManager;
import module.Recipes.ProcedureListManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static java.time.DayOfWeek.*;
import static module.Recipes.RecipeComponents.Measurement.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TestComponents {
    Components ingre;
    Components ingre1;
    Components pro;
    Components pro1;
    Components recipe;
    Components recipe1;
    Components pfm;
    Components pfm1;
    BasicInfoManager basicMng;

    @BeforeEach
    public void setUp(){
        ingre = new Ingredient();
        pro = new Procedure();
        recipe = new DefaultRecipe();
        pfm = new PlanForAMeal();

        ingre1 = new Ingredient("eggs",5,NULL);
        pro1 = new Procedure(1,"idk what I am doing");
        Ingredient ingre2 = new Ingredient("eggs",5,NULL);
        ArrayList<Ingredient> li = new ArrayList<>();
        li.add(ingre2);
        IngredientListManager ingreMng = new IngredientListManager(li);
        Procedure pro2 = new Procedure(1,"idk what I am doing");
        ArrayList<Procedure> lp = new ArrayList<>();
        lp.add(pro2);
        ProcedureListManager proMng = new ProcedureListManager(lp);
        basicMng = new BasicInfoManager(10,100,"Deault");
        recipe1 = new DefaultRecipe("recipe1",basicMng,ingreMng,proMng);
        DefaultRecipe defaultRecipe2 = new DefaultRecipe("defaultRecipe2",basicMng,ingreMng,proMng);

        pfm1 = new PlanForAMeal(MONDAY, Meal.BREAKFAST, defaultRecipe2);

    }

    @Test
    public void testValidityExpectBasicInfoInvalid(){
        Ingredient i = new Ingredient("Tomato",10,NULL);
        Procedure p = new Procedure(1,"wash tomatoes and dice them");
        ArrayList<Ingredient> ingres = new ArrayList<>();
        ArrayList<Procedure> pros = new ArrayList<>();
        ingres.add(i);
        IngredientListManager imng = new IngredientListManager(ingres);
        pros.add(p);
        ProcedureListManager pmng = new ProcedureListManager(pros);
        Components invalidRecipe1 = new DefaultRecipe("",basicMng,imng,pmng);
        BasicInfoManager invalidBasicInfo = new BasicInfoManager(-10,-100,"Default");
        Components invalidRecipe2 = new DefaultRecipe("Tomato soup", invalidBasicInfo,imng,pmng);
        try{
            invalidRecipe1.checkValidity();
            fail("You should catch a exception");
        } catch (RecipeBasicInfoInvalidException e){
            System.out.println("Yeeeey");
        } catch (IngredientInvalidException e){
            fail("Ingredient is valid");
        } catch (ProcedureInvalidException e){
            fail("Procedure is valid");
        }
        try{
            invalidRecipe2.checkValidity();
            fail("there should be a info invalid exception");
        } catch (IngredientInvalidException e) {
            fail("Not ingredient invalid");
        } catch (ProcedureInvalidException e) {
            fail("NOOO");
        } catch (RecipeBasicInfoInvalidException e) {
            System.out.println("Yeeeey");
        }

    }

    @Test
    public void testValidityExpectIngredientInvalid(){
        Procedure p = new Procedure(1,"wash tomatoes and dice them");
        IngredientListManager imng = new IngredientListManager();
        ProcedureListManager pmng = new ProcedureListManager();
        pmng.add(p);
        recipe = new DefaultRecipe("Tomato soup",basicMng,imng,pmng);
        try{
            recipe.checkValidity();
            fail("You should catch a exception");
        } catch (RecipeBasicInfoInvalidException e){
            fail("Basic info is valid");
        } catch (IngredientInvalidException e){
            System.out.println("Yeeeey");
        } catch (ProcedureInvalidException e){
            fail("Procedure is valid");
        }

    }


}
