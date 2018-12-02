package ui.MainGUI.RecipeLibraryUI;

import module.Recipes.Recipe;
import ui.MainGUI.RecipeLibraryUI.RecipeGUI.RecipeUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static ui.MainGUI.MainFrame.colorB;

public class LibraryUI extends JPanel{
    private ArrayList<Recipe> recipes;
    private ArrayList<JButton> bottons;
    private JPanel libraryView;
    private JPanel heading;
    private JPanel margin;
    private JScrollBar scrollBar;
    private JButton addRecipe;

    public LibraryUI(ArrayList<Recipe> recipes){
        this.recipes = recipes;
        bottons = new ArrayList<>();

        setLayout(new BorderLayout());
        setSize(500,500);
        setVisible(true);

        setUpViewPanel();
        setUpHeading();
        setUpMargin();
        setUpScrollbar();
        setUpBtns();
        setUpInteractions();
    }

    private void setUpViewPanel(){
        libraryView = new JPanel();
        libraryView.setSize(500,500);
        libraryView.setVisible(true);
        libraryView.setLayout(new GridLayout(recipes.size()+1,3,3,3));
        add(libraryView,BorderLayout.CENTER);
    }

    private void setUpHeading(){
        heading = new JPanel();
        heading.setSize(500,100);
        heading.setVisible(true);
        heading.setLayout(new BorderLayout());
        ImageIcon banner = new ImageIcon("src\\ui\\MainGUI\\Images\\RecipesBanner.jpg");
        ImageIcon bannerScaled = new ImageIcon(banner.getImage().getScaledInstance(banner.getIconWidth()/4,
                banner.getIconHeight()-450, Image.SCALE_SMOOTH));
        JLabel headingLabel = new JLabel(bannerScaled);
        heading.add(headingLabel,BorderLayout.CENTER);
        add(heading,BorderLayout.NORTH);
    }

    private void setUpScrollbar(){
        scrollBar = new JScrollBar();
        add(scrollBar,BorderLayout.EAST);
    }

    private void setUpMargin(){
        margin = new JPanel();
        margin.setBackground(colorB);
        add(margin,BorderLayout.WEST);
    }

    private void setUpBtns(){
        int i = 0;
        for(Recipe r: recipes){
            JButton recipeBtn = new JButton(r.getName());
            bottons.add(recipeBtn);
            libraryView.add(recipeBtn,i);
            i++;
        }

        addRecipe = new JButton("Add a new recipe");
        libraryView.add(addRecipe,recipes.size());
    }

    private void setUpInteractions(){
        for(JButton btn:bottons){
            int index = bottons.indexOf(btn);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    heading.setVisible(false);
                    libraryView.setVisible(false);
                    margin.setVisible(false);
                    scrollBar.setVisible(false);
                    RecipeUI recipeUI = new RecipeUI(recipes.get(index));
                    recipeUI.setVisible(true);
                    add(recipeUI,BorderLayout.CENTER);
                }
            });
        }

        addRecipe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                heading.setVisible(false);
                libraryView.setVisible(false);
                margin.setVisible(false);
                scrollBar.setVisible(false);
                AddRecipeUI addRecipeUI = new AddRecipeUI();
                add(addRecipeUI,BorderLayout.CENTER);
            }
        });
    }
}
