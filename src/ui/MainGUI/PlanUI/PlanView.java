package ui.MainGUI.PlanUI;

import module.Plans.PlanComponents.Meal;
import module.Plans.PlanComponents.PlanForAMeal;
import module.Plans.PlanComponents.PlanPresenter;
import module.Plans.Planner;
import module.Recipes.Recipe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.util.ArrayList;

import static ui.MainGUI.MainFrame.colorB;

public class PlanView extends JPanel{
    private java.util.List<PlanForAMeal> plans;
    private ArrayList<Recipe> listOfRecipes;
    private JPanel mainView;
    private JButton groViewBtn;

    public PlanView(java.util.List<PlanForAMeal> plans) {
        this.plans = plans;
        extractRecipes();
        setVisible(true);
        setSize(500, 500);
        setLayout(new BorderLayout());
        setUpMainView();
    }

    private void setUpMainView(){
        mainView = new JPanel();
        mainView.setVisible(true);
        mainView.setBackground(colorB);
        mainView.setLayout(new GridBagLayout());
        mainView.setSize(500,500);
        add(mainView,BorderLayout.CENTER);
        GridBagConstraints gc = new GridBagConstraints();
        setUpDates(gc);
        setUpMeals(gc);
        fillinRecipeNames(gc);
        insertGroBtn(gc);
        setUpCalorie(gc);

    }

    private void setUpDates(GridBagConstraints gc){
        gc.weightx = 5;
        gc.weighty = 5;
        DayOfWeek date = DayOfWeek.MONDAY;
        for(int i =0; i<7;i++){
            JLabel dateLabel = new JLabel(date.toString());
            gc.gridx = 0;
            gc.gridy = i+1;
            mainView.add(dateLabel,gc);
            date = date.plus(1);
        }
    }

    private void setUpMeals(GridBagConstraints gc){
        Meal meal = Meal.BREAKFAST;
        for(int i=0; i<3; i++){
            JLabel mealLabel = new JLabel(meal.translateMeal());
            gc.gridx = i+1;
            gc.gridy = 0;
            mainView.add(mealLabel,gc);
            meal = meal.plus(meal);
        }
    }


    private void fillinRecipeNames(GridBagConstraints gc){
        int x = 1;
        int y = 1;
        for(Recipe r: listOfRecipes){
            JLabel recipeLabel = new JLabel(r.getName());
            gc.gridx = x;
            gc.gridy = y;
            if(x<3){
                x++;
            }
            else if(x==3){
                x=1;
                y++;
            }
            mainView.add(recipeLabel,gc);
        }
    }

    private void setUpCalorie(GridBagConstraints gc){
        gc.gridx = 4;
        gc.gridy = 0;
        JLabel calorie = new JLabel("Calorie");
        mainView.add(calorie,gc);
        Planner planner = new Planner(plans);
        PlanPresenter presenter = new PlanPresenter(planner);
        DayOfWeek date = DayOfWeek.MONDAY;
        for(int i=0;i<7;i++){
            JLabel c = new JLabel(presenter.sumCalorie(date).toString());
            gc.gridy = i+1;
            mainView.add(c,gc);
            date = date.plus(1);
        }
    }

    private void insertGroBtn(GridBagConstraints gc){
        groViewBtn = new JButton("Grocery");
        gc.gridx = 4;
        gc.gridy = 8;
        mainView.add(groViewBtn,gc);

        groViewBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.setVisible(false);
                GroceryView groceryView = new GroceryView(listOfRecipes);
                add(groceryView,BorderLayout.CENTER);
            }
        });
    }

    private void extractRecipes(){
        listOfRecipes = new ArrayList<>();
        for(PlanForAMeal pfm:plans){
            listOfRecipes.add(pfm.getRecipesForAMeal());
        }
    }


}
