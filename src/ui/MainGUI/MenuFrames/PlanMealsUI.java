package ui.MainGUI.MenuFrames;

import module.Exceptions.CannotFindRecipeException;
import module.Plans.PlanComponents.PlanForAMeal;
import module.Plans.Planner;
import ui.MainGUI.PlanUI.PlanAdd;
import ui.MainGUI.PlanUI.PlanView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlanMealsUI extends Tab {

    private JPanel plansOverview;
    private JPanel heading;
    private JPanel marginR;
    private JPanel marginL;
    private Planner planner;
    private List<PlanForAMeal> plans;
    private int numWeek;
    private ArrayList<JButton> planBtns;
    private JButton addBtn;



    public PlanMealsUI(String title){
        super(title);
        planner = new Planner();
        planBtns = new ArrayList<>();
        plans = new ArrayList<>();
        setLayout(new BorderLayout());
        try {
            planner.load();
            plans = planner.getPlanner();
            numWeek = plans.size()/21;
            setUpOverview();
        } catch (IOException e) {
            System.out.println("Error reading plans");
        } catch (CannotFindRecipeException e) {
            System.out.println("Error in plans");
        }
        setUpHeading();
        setUpMargin();

        }



    private void setUpOverview(){
        setUpOverviewPanel();
        setUpBtns();
        setUpInteraction();
    }

    private void setUpOverviewPanel(){
         plansOverview = new JPanel();
         plansOverview.setVisible(true);
         java.awt.Color color = new java.awt.Color(233,221,136);
         plansOverview.setBackground(color);
         plansOverview.setSize(500,500);
         plansOverview.setLayout(new GridLayout(2,2,3,3));
         add(plansOverview,BorderLayout.CENTER);

     }

    private void setUpBtns(){
        for(int i=1;i<=numWeek;i++){
            JButton weekPlan = new JButton("Meal Plan" + String.valueOf(i));
            planBtns.add(weekPlan);
            plansOverview.add(weekPlan,i-1);
        }
        addBtn = new JButton("+");
        plansOverview.add(addBtn,numWeek);
    }

    private void setUpInteraction() {
        for (JButton planbtn : planBtns) {
            int index = planBtns.indexOf(planbtn);
            List<PlanForAMeal> aPlan = plans.subList(index * 21, (index + 1) * 21);
            planbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    plansOverview.setVisible(false);
                    heading.setVisible(false);
                    marginL.setVisible(false);
                    marginR.setVisible(false);
                    PlanView planView = new PlanView(aPlan);
                    add(planView, BorderLayout.CENTER);
                }
            });
        }

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plansOverview.setVisible(false);
                heading.setVisible(false);
                marginL.setVisible(false);
                marginR.setVisible(false);
                PlanAdd planAdd = new PlanAdd();
                planAdd.setVisible(true);
                add(planAdd,BorderLayout.CENTER);
            }
        });
    }

    private void setUpHeading(){
        heading = new JPanel();
        heading.setVisible(true);
        add(heading,BorderLayout.NORTH);
        ImageIcon headingImage = new ImageIcon("src\\ui\\MainGUI\\Images\\meal-planner-banner.jpg");
        ImageIcon headingScaled = new ImageIcon(headingImage.getImage().getScaledInstance(500,
                80, Image.SCALE_SMOOTH));
        JLabel headingLabel = new JLabel(headingScaled);
        heading.add(headingLabel);
    }

    private void setUpMargin(){
        marginL = margin();
        marginR = margin();
        add(marginL,BorderLayout.WEST);
        add(marginR,BorderLayout.EAST);
    }

    private JPanel margin(){
        JPanel margin = new JPanel();
        java.awt.Color color = new java.awt.Color(233,218,95);
        margin.setBackground(color);
        return margin;
    }
}
