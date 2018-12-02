package ui.MainGUI.RecipeLibraryUI;

import module.RecipeLibrary.RecipeLibrary;
import module.Recipes.Recipe;
import module.Recipes.UserRecipe;
import ui.MainGUI.RecipeLibraryUI.RecipeGUI.BasicMng;
import ui.MainGUI.RecipeLibraryUI.RecipeGUI.IngreMng;
import ui.MainGUI.RecipeLibraryUI.RecipeGUI.ProMng;
import ui.MainGUI.RecipeLibraryUI.RecipeGUI.RecipeUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AddRecipeUI extends JPanel {
    private JTabbedPane sidebar;
    private JPanel heading;
    private JPanel main;
    private JButton previewButton;
    private JButton save;
    private BasicMng basicMng;
    private IngreMng ingreMng;
    private ProMng proMng;
    private RecipeLibrary rl;


    public AddRecipeUI(){
        setLayout(new BorderLayout());
        insertHeadingPanel();
        insertMainPanel();

        rl = new RecipeLibrary();
        try {
            rl.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setUpInteraction();
    }

    private void insertHeadingPanel(){
        heading = new JPanel();
        heading.setLayout(new BorderLayout());
        heading.setVisible(true);
        heading.setSize(500,100);
        heading.add(createBanner());
        add(heading,BorderLayout.NORTH);

        previewButton = new JButton("preview");
        heading.add(previewButton,BorderLayout.EAST);
        save = new JButton("save");

    }

    private JLabel createBanner(){
        ImageIcon banner = new ImageIcon("src\\ui\\MainGUI\\Images\\RecipesBanner.jpg");
        ImageIcon bannerScaled = new ImageIcon(banner.getImage().getScaledInstance(banner.getIconWidth()/4,
                banner.getIconHeight()-450, Image.SCALE_SMOOTH));
        JLabel profileL = new JLabel(bannerScaled);
        profileL.setLocation(250,50);
        return profileL;
    }

    private void insertMainPanel(){
        main = new JPanel();
        main.setLayout(new BorderLayout());
        main.setSize(500,400);
        main.setVisible(true);
        main.setLocation(0,70);
        add(main,BorderLayout.CENTER);
        setUpSidebar();

    }

    private void setUpInteraction(){
        previewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkFilled()){
                    main.setVisible(false);
                    Recipe r = setUpRecipe();
                    JPanel viewRecipe = new RecipeUI(r);
                    heading.remove(previewButton);
                    heading.add(save,BorderLayout.EAST);
                    add(viewRecipe,BorderLayout.CENTER);
                }
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Recipe r = setUpRecipe();
                rl.storeRecipe(r);
                try {
                    rl.save();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void setUpSidebar(){
        sidebar = new JTabbedPane();
        sidebar.setSize(500,300);
        sidebar.setTabPlacement(JTabbedPane.TOP);
        loadTabs();
        main.add(sidebar,BorderLayout.CENTER);
    }

    private void loadTabs(){
        basicMng = new BasicMng();
        ingreMng = new IngreMng();
        proMng = new ProMng();
        sidebar.add(basicMng,0);
        sidebar.add(ingreMng,1);
        sidebar.add(proMng,2);
        sidebar.setTitleAt(0,"Basic Info");
        sidebar.setTitleAt(1,"Ingredients");
        sidebar.setTitleAt(2,"Procedures");
    }




    private boolean checkFilled(){
        boolean isBasicInfoValid = basicMng.checkValidity();
        boolean isIngre = false;
        boolean isPro = false;
        for(JTextField textField: ingreMng){
            if(!textField.getText().isEmpty()){
               isIngre = true;
            }
        }
        for(JTextField textField: proMng){
            if(!textField.getText().isEmpty()){
                isPro = true;
            }
        }
        return isBasicInfoValid&&isIngre&&isPro;
    }

    private Recipe setUpRecipe(){
        Recipe r = new UserRecipe();
        r.setName(basicMng.getName());
        r.setTime(basicMng.getTime());
        r.setCategory("User");
        r.setCalorie(basicMng.getCalorie());
        r.setIngredients(ingreMng.getIngreInfo());
        r.setProcedures(proMng.getProInfo());
        return r;
    }
}
