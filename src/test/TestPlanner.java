package test;

import module.Exceptions.CannotFindRecipeException;
import module.Exceptions.NotEnoughPlansException;
import module.Exceptions.TooHighCalorieException;
import module.Exceptions.TooLowCalorieException;
import module.Plans.PlanComponents.PlanForAMeal;
import module.Plans.PlanComponents.PlanPresenter;
import module.Plans.Planner;
import module.Plans.PlanComponents.Meal;
import module.RecipeLibrary.RecipeLibrary;
import module.Recipes.DefaultRecipe;
import module.Recipes.Recipe;
import module.Recipes.UserRecipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import static java.time.DayOfWeek.*;
import static module.Plans.PlanComponents.Meal.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestPlanner {

    private Planner p;
    private PlanPresenter pp;
    private Recipe r1;
    private Recipe r2;
    private Recipe r3;
    private PlanForAMeal pB;
    private PlanForAMeal pL;
    private PlanForAMeal pD;


    @BeforeEach
    public void setUp(){
        p = new Planner();
        pp = new PlanPresenter(p);
        p.addPlanObserver(pp);
        r1 = new UserRecipe();
        r1.setName("Scrambled Eggs");
        r1.setCalorie(400);
        r2 = new DefaultRecipe();
        r2.setCalorie(800);
        r2.setName("Banana Bread");
        r3 = new DefaultRecipe();
        r3.setCalorie(1500);
        r3.setName("Honey Yogurt Cheesecake");
        pB = new PlanForAMeal(MONDAY,BREAKFAST, r1);
        pL = new PlanForAMeal(MONDAY,LUNCH,r2);
        pD = new PlanForAMeal(MONDAY,DINNER,r3);
        p.storing(pB);
        p.storing(pL);
        p.storing(pD);


    }

    @Test
    public void testSumCalorie(){

        double calorieSum = pp.sumCalorie(MONDAY);
        assertEquals(calorieSum,2700);

    }

    @Test
    public void testSearchPlanInAPlanner(){
        try{
            PlanForAMeal pTest = p.searching("Honey Yogurt Cheesecake");
            assertEquals(pTest,pD);
        } catch (CannotFindRecipeException e){
            fail("Noooo");
        }

    }

    @Test
    public void testSearchPlanNotFound(){
        try{
            p.searching("null");
            fail("There should be an exception");
        }catch (CannotFindRecipeException e){
            // nothing needs to be done
        }
    }

    @Test
    public void testSize(){
        assertEquals(p.size(),3);
        p.storing(pB);
        assertEquals(p.size(),4);
    }

    @Test
    public void testLoad() throws IOException, CannotFindRecipeException {
        Planner pLoad = new Planner();
        pLoad.load();
        List<PlanForAMeal> plans = pLoad.getPlanner();
        PlanForAMeal plan1 = plans.get(0);
        DayOfWeek day1 = plan1.getTheDate();
        Meal meal = plan1.getTheMeal();
        Recipe recipe1 = plan1.getRecipesForAMeal();
        assertEquals(day1,MONDAY);
        assertEquals(meal,BREAKFAST);
        assertEquals(recipe1.getName(),"Scrambled Eggs");

    }

    @Test
    public void testSave() throws IOException, CannotFindRecipeException {
        p.save();
        Planner pTest = new Planner();
        pTest.load();
        List<PlanForAMeal> plans = pTest.getPlanner();
        PlanForAMeal plan1 = plans.get(0);
        DayOfWeek day1 = plan1.getTheDate();
        Meal meal = plan1.getTheMeal();
        Recipe recipe1 = plan1.getRecipesForAMeal();
        assertEquals(day1,MONDAY);
        assertEquals(meal,BREAKFAST);
        assertEquals(recipe1.getName(),"Scrambled Eggs");

    }

    @Test
    public void testEvaluateExpectNoException(){
        Planner goodPlan = new Planner();
        PlanPresenter goodPlanPresenter = new PlanPresenter(goodPlan);
        goodPlan.addPlanObserver(goodPlanPresenter);
        DayOfWeek theDate = MONDAY;
        Meal theMeal = BREAKFAST;
        for(int date=1; date<=7; date++) {
            for (int meal = 1; meal <= 3; meal++) {
                PlanForAMeal plan1 = new PlanForAMeal(theDate,theMeal,r2);
                goodPlan.storing(plan1);
                theMeal = theMeal.plus(theMeal);
            }
            theDate = theDate.plus(1);
        }
        try{
            goodPlanPresenter.evaluate();
        }catch(NotEnoughPlansException e){
            fail("There are 21 plans");
        }catch(TooLowCalorieException e){
            fail("Calorie is above the lower bound");
        }catch (TooHighCalorieException e){
            fail("Calorie is below the higher bound");
        }
    }

    @Test
    public void testEvaluateExpectTooHighCalorieException(){
        Planner calorieTooHighPlan = new Planner();
        PlanPresenter tooHighCaPresenter= new PlanPresenter(calorieTooHighPlan);
        calorieTooHighPlan.addPlanObserver(tooHighCaPresenter);
        DayOfWeek theDate = MONDAY;
        Meal theMeal = BREAKFAST;
        for(int date=1; date<=7; date++) {
            for (int meal = 1; meal <= 3; meal++) {
                PlanForAMeal plan1 = new PlanForAMeal(theDate,theMeal,r3);
                calorieTooHighPlan.storing(plan1);
                theMeal = theMeal.plus(theMeal);
            }
            theDate = theDate.plus(1);
        }
        try{
            tooHighCaPresenter.evaluate();
            fail("Calorie is too high");
        }catch(NotEnoughPlansException e){
            fail("There are 21 plans");
        }catch(TooLowCalorieException e){
            fail("Calorie is way above the lower bound");
        }catch (TooHighCalorieException e){
            //nothing needs to be done
        }
    }

    @Test
    public void testEvaluateExpectTooLowCalorieException(){
        Planner calorieTooLowPlan = new Planner();
        PlanPresenter tooLowCaPresenter = new PlanPresenter(calorieTooLowPlan);
        calorieTooLowPlan.addPlanObserver(tooLowCaPresenter);
        DayOfWeek theDate = MONDAY;
        Meal theMeal = BREAKFAST;
        for(int date=1; date<=7; date++) {
            for (int meal = 1; meal <= 3; meal++) {
                PlanForAMeal plan1 = new PlanForAMeal(theDate,theMeal,r1);
                calorieTooLowPlan.storing(plan1);
                theMeal = theMeal.plus(theMeal);
            }
            theDate = theDate.plus(1);
        }
        try{
            tooLowCaPresenter.evaluate();
            fail("Calorie is too low");
        }catch(NotEnoughPlansException e){
            fail("There are 21 plans");
        }catch(TooLowCalorieException e){
            //nothing needs to be done
        }catch (TooHighCalorieException e){
            fail("Calorie is way too low");
        }
    }

    @Test
    public void testEvaluateExpectNotEnoughPlanException(){
        Planner notFinishedPlan = new Planner();
        PlanPresenter planNotFinishedPresenter = new PlanPresenter(notFinishedPlan);
        notFinishedPlan.addPlanObserver(planNotFinishedPresenter);
        DayOfWeek theDate = MONDAY;
        Meal theMeal = BREAKFAST;
        for(int date=1; date<=6; date++) {
            for (int meal = 1; meal <= 3; meal++) {
                PlanForAMeal plan1 = new PlanForAMeal(theDate,theMeal,r2);
                notFinishedPlan.storing(plan1);
                theMeal = theMeal.plus(theMeal);
            }
            theDate = theDate.plus(1);
        }
        try{
            planNotFinishedPresenter.evaluate();
            fail("The number of plans is not enough");
        }catch(NotEnoughPlansException e){
            //nothing needs to be done
        }catch(TooLowCalorieException e){
            fail("There isn't enough plans");
        }catch (TooHighCalorieException e){
            fail("PlanComponents not enough");
        }
    }

}
