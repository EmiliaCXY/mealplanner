package ui.MainGUI;


import ui.MainGUI.MenuFrames.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    public static java.awt.Color colorB = new java.awt.Color(233,218,95);
    public static Color colorS = new java.awt.Color(233,221,136);

    public MainFrame(String title){
        super(title);
        getContentPane().setBackground(colorB);
        setLayout(null);

        insertPanel();
        insertStartBtn();

        }
    private void insertPanel(){
        JPanel page = new JPanel();
        page.setSize(500,290);

        ImageIcon heading = new ImageIcon("src\\ui\\MainGUI\\Images\\meal-planner-heading.jpg");
        ImageIcon headingScaled = new ImageIcon(heading.getImage().getScaledInstance(500,
                290, Image.SCALE_SMOOTH));
        JLabel headingLabel = new JLabel(headingScaled);
        headingLabel.setSize(headingScaled.getIconWidth(),headingScaled.getIconHeight());
        headingLabel.setLocation(0,0);

        page.add(headingLabel);
        page.setLocation(0,0);
        this.getContentPane().add(page);
    }

    private void insertStartBtn(){
        JLabel start = new JLabel("Start");
        start.setBounds(200,300,100,100);
        this.getContentPane().add(start);
        ImageIcon startBtn = new ImageIcon("src\\ui\\MainGUI\\Images\\enterBtn.png");
        ImageIcon startBtnScaled = new ImageIcon(startBtn.getImage().getScaledInstance(startBtn.getIconWidth()-50,
                startBtn.getIconHeight()-50, Image.SCALE_SMOOTH));
        JButton enterButton = new JButton(startBtnScaled);
        enterButton.setSize(startBtnScaled.getIconWidth(),startBtnScaled.getIconHeight());
        enterButton.setLocation(200,300);
        this.getContentPane().add(enterButton);

        enterButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.MainGUI.MenuFrames.Menu menu = new Menu("Meal Planner");
            }

        });
    }
}
