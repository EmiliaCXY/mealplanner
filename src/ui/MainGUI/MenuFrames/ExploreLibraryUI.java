package ui.MainGUI.MenuFrames;


import ui.MainGUI.RecipeLibraryUI.LibraryUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExploreLibraryUI extends Tab {
    private JPanel buttons;
    private JPanel marginUp;
    private JPanel marginDown;
    private JButton defaultLibrary;
    private JButton userLibrary;


    public ExploreLibraryUI(String title){
        super(title);
        setLayout(new BorderLayout());
        setUpMargin();
        setUpBtns();
        setUpInteraction();
    }

    private void setUpMargin(){
        marginUp = margin();
        marginDown = margin();
        add(marginUp,BorderLayout.NORTH);
        add(marginDown,BorderLayout.SOUTH);
    }


    private JPanel margin(){
        JPanel margin = new JPanel();
        margin.setLayout(new BorderLayout());
        margin.setVisible(true);
        ImageIcon banner = new ImageIcon("src\\ui\\MainGUI\\Images\\RecipesBanner.jpg");
        ImageIcon bannerScaled = new ImageIcon(banner.getImage().getScaledInstance(banner.getIconWidth()/4,
                banner.getIconHeight()-450, Image.SCALE_SMOOTH));
        JLabel label2 = new JLabel(bannerScaled);
        margin.add(label2, BorderLayout.CENTER);

        return margin;
    }

    private void setUpBtns(){
        buttons = new JPanel();
        buttons.setVisible(true);
        buttons.setBackground(getContentPane().getBackground());
        buttons.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        defaultLibrary = new JButton("Default Recipes");
        gc.ipadx = 40;
        gc.ipady = 40;
        gc.gridwidth = 3;
        gc.gridx = 1;
        gc.gridy = 0;
        buttons.add(defaultLibrary,gc);

        userLibrary = new JButton("Your Recipes");
        gc.gridx = 6;
        gc.gridy = 0;
        buttons.add(userLibrary,gc);

        add(buttons,BorderLayout.CENTER);
    }

    private void setUpInteraction(){
        defaultLibrary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttons.setVisible(false);
                marginUp.setVisible(false);
                marginDown.setVisible(false);
                LibraryUI libraryUI = new LibraryUI(defaultRecipes);
                add(libraryUI);
            }
        });

        userLibrary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                marginUp.setVisible(false);
                marginDown.setVisible(false);
                LibraryUI libraryUI = new LibraryUI(userRecipes);
                add(libraryUI);
                buttons.setVisible(false);
            }
        });
    }


}
