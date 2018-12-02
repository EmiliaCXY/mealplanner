package ui.MainGUI.PlanUI;

import module.Exceptions.CannotFindRecipeException;
import module.Plans.PlanComponents.Meal;
import module.Plans.PlanComponents.PlanForAMeal;
import module.RecipeLibrary.RecipeLibrary;
import module.Recipes.Recipe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;

import static java.awt.GridBagConstraints.LINE_START;
import static ui.MainGUI.MainFrame.colorB;

public class PlanAdd extends JPanel {
    private ArrayList<JTextField> listOfMealFields;
    private RecipeLibrary recipeLibrary;
    private JSplitPane splitPane;
    private JPanel heading;
    private JPanel recipeDisplayer;
    private JPanel plan;
    private JPanel mealInfo;
    private JButton previewBtn;

    public PlanAdd(){
        setLayout(new BorderLayout());
        setVisible(true);
        setSize(500,500);
        listOfMealFields = new ArrayList<>();
        plan = new JPanel();
        recipeDisplayer = new JPanel();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,plan,recipeDisplayer);

        recipeLibrary = new RecipeLibrary();
        try {
            recipeLibrary.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        insertHeading();
        setUpSplitpane();
        setUpPlan();
        setUpDisplayer();
        setUpInteraction();

    }

    private void setUpSplitpane(){
        splitPane.setVisible(true);
        splitPane.setBackground(colorB);
        splitPane.setSize(500,500);
        splitPane.setDividerLocation(300);
        add(splitPane,BorderLayout.CENTER);
    }

    private void insertHeading(){
        heading = new JPanel();
        heading.setVisible(true);
        heading.setSize(500,50);
        heading.setLayout(new BorderLayout());

        JLabel headingLabel = new JLabel("New Plan");
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headingLabel.setFont(new Font("Tekton pro",Font.BOLD,20));
        heading.add(headingLabel,BorderLayout.CENTER);

        previewBtn = new JButton("Preview");
        previewBtn.setFont(new Font("Tekton pro",Font.PLAIN,18));
        headingLabel.setSize(300,50);
        heading.add(previewBtn,BorderLayout.EAST);

        add(heading,BorderLayout.NORTH);


    }

    private void setUpInteraction(){
        previewBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkFilled()){
                    heading.setVisible(false);
                    splitPane.setVisible(false);
                    plan.setVisible(false);
                    recipeDisplayer.setVisible(false);
                    ArrayList<PlanForAMeal> plans = assemblePlan();
                    PlanView planView = new PlanView(plans);
                    add(planView,BorderLayout.CENTER);
                }
            }
        });
    }
    private void setUpPlan(){
        plan.setSize(350,500);
        plan.setVisible(true);
        plan.setBackground(colorB);
        plan.setLayout(new BorderLayout());
        JTabbedPane dateChooser = new JTabbedPane();
        dateChooser.setSize(50,500);
        dateChooser.setTabPlacement(JTabbedPane.LEFT);
        plan.add(dateChooser,BorderLayout.CENTER);
        loadTabs(dateChooser);
    }

    private JPanel constructMealChoosePanel(){
        mealInfo = new JPanel();
        mealInfo.setVisible(true);
        mealInfo.setBackground(colorB);
        mealInfo.setSize(300,500);
        mealInfo.setLayout(new GridBagLayout());
        JLabel breakfast = new JLabel("Breakfast");
        JLabel lunch = new JLabel("Lunch");
        JLabel dinner = new JLabel("Dinner");
        JTextField bField = new JTextField(10);
        JTextField lField = new JTextField(10);
        JTextField dField = new JTextField(10);
        listOfMealFields.add(bField);
        listOfMealFields.add(lField);
        listOfMealFields.add(dField);


        GridBagConstraints gc = new GridBagConstraints();

        gc.weightx = 2;
        gc.weighty = 1;

        gc.gridx = 1;
        gc.gridy = 0;
        mealInfo.add(breakfast,gc);

        gc.gridx = 2;
        gc.gridy = 0;
        mealInfo.add(bField,gc);

        gc.gridx = 1;
        gc.gridy = 1;
        mealInfo.add(lunch,gc);

        gc.gridx = 2;
        gc.gridy = 1;
        mealInfo.add(lField,gc);

        gc.gridx = 1;
        gc.gridy = 3;
        mealInfo.add(dinner,gc);

        gc.gridx = 2;
        gc.gridy = 3;
        mealInfo.add(dField,gc);

        insertBlank(0,4,mealInfo,gc);


        return mealInfo;

    }

    private void loadTabs(JTabbedPane mealChooser){
        DayOfWeek date = DayOfWeek.MONDAY;
        for(int i=1;i<=7;i++){
            JPanel day = constructMealChoosePanel();
            mealChooser.add(day,i-1);
            mealChooser.setTitleAt(i-1,date.toString().substring(0,3));
            date = date.plus(1);
        }
    }

    private void setUpDisplayer(){
        recipeDisplayer.setSize(200,500);
        recipeDisplayer.setVisible(true);
        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.addAll(recipeLibrary.getUserRecipes());
        recipes.addAll(recipeLibrary.getDefRecipes());

        recipeDisplayer.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.gridy = 0;
        JLabel libraryLabel = new JLabel("Recipe Library");
        recipeDisplayer.add(libraryLabel,gc);

        int i =1;
        for(Recipe r: recipes){
            gc.anchor = LINE_START;
            gc.gridx = 0;
            gc.gridy = i;
            JLabel recipeLabel = new JLabel(r.getName());
            recipeDisplayer.add(recipeLabel,gc);
            i++;
        }

        insertBlank(0,recipes.size(),recipeDisplayer,gc);

    }

    private void insertBlank(int x, int y, JPanel panel, GridBagConstraints gc){
        JLabel blank = new JLabel();
        blank.setBackground(colorB);
        gc.gridx = x;
        gc.gridy = y;
        panel.add(blank,gc);

        JLabel blank2 = new JLabel();
        blank2.setBackground(colorB);
        gc.gridx = x;
        gc.gridy = y+1;
        gc.weighty = 10;
        panel.add(blank2,gc);
    }

    private boolean checkFilled(){
        boolean filled = true;
        for(JTextField field: listOfMealFields){
            if(field.getText().isEmpty()){
                filled = false;
            }
        }
        return filled;
    }

    private ArrayList<Recipe> collectRecipeNames(){
        ArrayList<Recipe> recipes = new ArrayList<>();
        for(JTextField field: listOfMealFields){
            String name = field.getText();
            try {
                Recipe r = recipeLibrary.searching(name);
                recipes.add(r);
            } catch (CannotFindRecipeException e) {
                System.out.println("Cannot find recipe");
            }
        }
        return recipes;
    }

    private ArrayList<PlanForAMeal> assemblePlan(){
        ArrayList<Recipe> recipes = collectRecipeNames();
        ArrayList<PlanForAMeal> plans = new ArrayList<>();

        DayOfWeek date = DayOfWeek.MONDAY;
        Meal meal = Meal.BREAKFAST;
        for(Recipe r: recipes){
            PlanForAMeal p = new PlanForAMeal(date,meal,r);
            if(meal.equals(Meal.DINNER)){
                date = date.plus(1);
            }
            meal = meal.plus(meal);
            plans.add(p);
        }
        return plans;
    }
}
