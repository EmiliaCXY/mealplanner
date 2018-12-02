package ui.MainGUI.MenuFrames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {

    public Menu(String title){
        super(title);
        java.awt.Color color = new java.awt.Color(233,218,95);
        getContentPane().setBackground(color);
        setLayout(new BorderLayout());
        setSize(500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setHeadingAndFooting();
        insertPanel();
        setMargin();
        setVisible(true);

    }

    private void setHeadingAndFooting(){
        ImageIcon menuHeading = new ImageIcon("src\\ui\\MainGUI\\Images\\heading-menu.png");
        ImageIcon menuHeadingScaled = new ImageIcon(menuHeading.getImage().getScaledInstance(menuHeading.getIconWidth()-150,
                menuHeading.getIconHeight()-50, Image.SCALE_SMOOTH));

        ImageIcon footing = new ImageIcon("src\\ui\\MainGUI\\Images\\meatlovers.jpg");
        ImageIcon footingScaled = new ImageIcon(footing.getImage().getScaledInstance(footing.getIconWidth(),
                menuHeading.getIconHeight()-30, Image.SCALE_SMOOTH));

        JLabel menuLabel = new JLabel(menuHeadingScaled);
        JLabel menuLabel2 = new JLabel(footingScaled);
        add(menuLabel,BorderLayout.NORTH);
        add(menuLabel2, BorderLayout.SOUTH);
    }

    private void insertPanel(){
        JPanel buttons = new JPanel();
        buttons.setPreferredSize(new Dimension(300,200));
        buttons.setLayout(new GridLayout(2,1,5,5));
        JButton exploreBtn = new JButton("Explore");
        JButton planBtn = new JButton("Plan");

        buttons.add(exploreBtn,0);
        buttons.add(planBtn,1);

        this.getContentPane().add(buttons, BorderLayout.CENTER);

        exploreBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame exploreLibraryUI = new ExploreLibraryUI("Meal Planner");

            }
        });

        planBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame planMealsUI = new PlanMealsUI("Meal Planner");
            }
        });
    }

    private void setMargin(){
        JPanel marginLeft = margin();
        JPanel marginRight = margin();
        add(marginLeft,BorderLayout.LINE_START);
        add(marginRight,BorderLayout.LINE_END);
    }

    private JPanel margin(){
        JPanel margin = new JPanel();
        java.awt.Color color = new java.awt.Color(233,218,95);
        margin.setVisible(true);
        margin.setBackground(color);
        margin.setSize(100,100);
        return margin;
    }
}
