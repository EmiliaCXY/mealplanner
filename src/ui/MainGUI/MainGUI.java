package ui.MainGUI;


import javax.swing.*;

public class MainGUI {

    public static void main(String[] args) {
        JFrame frame = new MainFrame("Meal Planner");
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
