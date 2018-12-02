package ui.MainGUI.RecipeLibraryUI.RecipeGUI;

import javax.swing.*;
import java.awt.*;

public class BasicMng extends Mng {
    private JLabel name;
    private JLabel time;
    private JTextField nameField;
    private JTextField timeField;
    private JLabel calorie;
    private JTextField calorieField;

    public BasicMng(){
        super();
        GridBagConstraints gc = new GridBagConstraints();
        setUpBasicInfo(gc);
        setImage(gc);
        insertFooting(gc);
    }


    public String getName() {
        return nameField.getText();
    }

    public int getTime() {
        return Integer.parseInt(timeField.getText());
    }

    public Double getCalorie() {
        return Double.parseDouble(calorieField.getText());
    }

    public boolean checkValidity(){
        return (!(getName().equals("")||getTime()<=0||getCalorie()<=0));
    }

    private void setUpBasicInfo(GridBagConstraints gc){
        name = new JLabel("Name: ");
        nameField = new JTextField(10);
        time = new JLabel("Time: ");
        timeField = new JTextField(10);
        calorie = new JLabel("Calorie: ");
        calorieField = new JTextField(10);
        gc.anchor = GridBagConstraints.CENTER;
        gc.weightx = 2;
        gc.weighty = 2;
        gc.ipadx = 10;
        gc.ipady = 10;

        gc.gridx = 0;
        gc.gridy = 0;
        add(name,gc);

        gc.gridx = 1;
        gc.gridy = 0;
        add(nameField,gc);

        gc.gridx = 0;
        gc.gridy = 1;
        add(time,gc);

        gc.gridx = 1;
        gc.gridy = 1;
        add(timeField,gc);

        gc.gridx = 0;
        gc.gridy = 2;
        add(calorie,gc);

        gc.gridx = 1;
        gc.gridy = 2;
        add(calorieField,gc);
    }

    private void setImage(GridBagConstraints gc){
        ImageIcon pictBtn = new ImageIcon("src\\ui\\MainGUI\\Images\\ImageComingSoon.jpeg");
        ImageIcon pictBtnScaled = new ImageIcon(pictBtn.getImage().getScaledInstance(pictBtn.getIconWidth()/4,
                pictBtn.getIconHeight()/4, Image.SCALE_SMOOTH));
        JButton pictureBtn = new JButton(pictBtnScaled);
        pictureBtn.setSize(pictBtnScaled.getIconWidth(),pictBtnScaled.getIconHeight());

        gc.gridx = 5;
        gc.gridy = 0;
        gc.gridheight = 3;
        add(pictureBtn,gc);
    }

    private void insertFooting(GridBagConstraints gc){
        JLabel blank = new JLabel();
        gc.gridx = 0;
        gc.gridy = 2;
        add(blank,gc);

        JLabel blank2 = new JLabel();
        gc.gridx = 0;
        gc.gridy = 3;
        gc.weighty = 10;
        add(blank2,gc);
    }
}
