package ui.MainGUI.PlanUI;

import module.Recipes.Recipe;
import module.Recipes.RecipeComponents.Ingredient;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static ui.MainGUI.MainFrame.colorB;
import static ui.MainGUI.MainFrame.colorS;

public class GroceryView extends JPanel {
    private JPanel heading;
    private JPanel mainView;
    private ArrayList<Recipe> recipes;

    public GroceryView(ArrayList<Recipe> recipes){
        this.recipes = recipes;
        setVisible(true);
        setLayout(new BorderLayout());
        setUpMainView();
        setUpHeading();

    }

    private void setUpMainView(){
        mainView = new JPanel();
        mainView.setVisible(true);
        mainView.setBackground(colorB);
        mainView.setLayout(new GridLayout(10,10,2,2));
        mainView.setSize(500,400);
        ArrayList<Ingredient> needBuy = needBuy();
        add(mainView,BorderLayout.CENTER);
        int index = 0;
        for(Ingredient i:needBuy){
            JLabel ingre = new JLabel(i.toStringD());
            mainView.add(ingre,index);
            index++;
        }
    }

    private ArrayList<Ingredient> needBuy() {
        ArrayList<Ingredient> ingreWithNoDuplication = new ArrayList<>();
        for (Recipe r : recipes) {
            ArrayList<Ingredient> ingredientsNeeded = r.getIngres().getIngredients();
            for (Ingredient i : ingredientsNeeded) {
                if (ingreWithNoDuplication.contains(i)) {
                    int indexOfOriginalIngre = ingreWithNoDuplication.indexOf(i);
                    Ingredient iOriginal = ingreWithNoDuplication.get(indexOfOriginalIngre);
                    Double newAmt = iOriginal.getAmount() + i.getAmount();
                    iOriginal.setAmount(newAmt);
                } else {
                    ingreWithNoDuplication.add(i);
                }
            }
        }
        return ingreWithNoDuplication;
    }

    private void setUpHeading(){
        heading = new JPanel();
        heading.setVisible(true);
        heading.setBackground(colorS);
        ImageIcon image = new ImageIcon("src\\ui\\MainGUI\\Images\\shopping cart.jpg");
        ImageIcon imageScaled = new ImageIcon(image.getImage().getScaledInstance(50,
                50, Image.SCALE_SMOOTH));
        JLabel headingL = new JLabel(imageScaled);
        heading.add(headingL);
        add(headingL,BorderLayout.WEST);
    }
}
