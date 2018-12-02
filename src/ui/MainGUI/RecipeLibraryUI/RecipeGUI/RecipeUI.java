package ui.MainGUI.RecipeLibraryUI.RecipeGUI;

import module.Recipes.Recipe;
import module.Recipes.RecipeComponents.Ingredient;
import module.Recipes.RecipeComponents.Procedure;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static ui.MainGUI.MainFrame.colorB;
import static ui.MainGUI.MainFrame.colorS;

public class RecipeUI extends JPanel {
    private Recipe r;
    private JPanel basicInfo;
    private JSplitPane ingreAndPro;
    private JPanel ingre;
    private JPanel pro;
    private JPanel marginL;
    public RecipeUI(Recipe r){
        this.r = r;
        setVisible(true);
        setLayout(new BorderLayout());
        ingre = new JPanel();
        pro = new JPanel();
        ingreAndPro = new JSplitPane(JSplitPane.VERTICAL_SPLIT,ingre,pro);
        viewRecipe();
    }

    private void viewRecipe(){
        setUpBasicInfo();
        setUpSplitPane();
        setUpIngreInfo();
        setUpProInfo();
        setUpMargin();
    }

    private void setUpBasicInfo(){
        basicInfo = new JPanel();
        basicInfo.setSize(400,100);
        basicInfo.setBackground(colorB);
        basicInfo.setVisible(true);
        basicInfo.setLayout(new GridLayout(1,1,3,3));
        JLabel name = new JLabel(r.getName());
        name.setFont(new Font("Tekton pro", Font.BOLD,24));
        JLabel info = new JLabel("Time: "+r.getTime() +" min " +"  Calorie: "+r.getCalorie() + "kCal");
        info.setFont(new Font("Tekton pro", Font.PLAIN,16));

        basicInfo.add(name,0);
        basicInfo.add(info,1);

        add(basicInfo,BorderLayout.NORTH);
    }

    private void setUpSplitPane(){
        ingreAndPro.setVisible(true);
        ingreAndPro.setSize(400,400);
        ingreAndPro.setDividerLocation(180);
        add(ingreAndPro,BorderLayout.CENTER);
    }

    private void setUpIngreInfo(){
        ArrayList<Ingredient> ingres = r.getIngres().getIngredients();
        ingre.setLayout(new GridLayout(6,1,1,1));
        ingre.setVisible(true);
        ingre.setSize(400,220);
        ingre.setBackground(colorS);
        JLabel ingreLabel = new JLabel("Ingredients:");
        ingreLabel.setFont(new Font("Tekton pro cond", Font.BOLD,20));
        ingreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ingre.add(ingreLabel,0);
        int index = 1;
        for(Ingredient i: ingres){
            JLabel iLabel = new JLabel(i.toStringD());
            iLabel.setFont(new Font("Tekton pro cond", Font.PLAIN,16));
            ingre.add(iLabel,index);
            index++;
        }
    }

    private void setUpProInfo(){
        ArrayList<Procedure> pros = r.getPros().getProcedures();
        pro.setLayout(new GridLayout(pros.size()+1,1,3,3));
        pro.setVisible(true);
        pro.setSize(400,220);
        pro.setBackground(colorS);
        JLabel proLabel = new JLabel("Direction:");
        proLabel.setFont(new Font("Tekton pro cond", Font.BOLD,20));
        proLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pro.add(proLabel,0);
        for(Procedure p: pros){
            JLabel pLabel = new JLabel(p.toStringD());
            pLabel.setFont(new Font("Tekton pro cond", Font.PLAIN,16));
            pro.add(pLabel,p.getStepNumber());
        }
    }

    private void setUpMargin(){
        marginL = margin();
        add(marginL,BorderLayout.WEST);
    }

    private JPanel margin(){
        JPanel margin = new JPanel();
        margin.setVisible(true);
        margin.setBackground(colorB);
        return margin;
    }
}
