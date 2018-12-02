package ui.MainGUI.RecipeLibraryUI.RecipeGUI;

import javax.swing.*;
import java.awt.*;

import static ui.MainGUI.MainFrame.colorB;

public abstract class Mng extends JPanel {

    public Mng(){
        setBackground(colorB);
        setLayout(new GridBagLayout());
    }
}
