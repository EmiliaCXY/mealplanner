package ui.MainGUI.RecipeLibraryUI.RecipeGUI;

import module.Recipes.RecipeComponents.Procedure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

public class ProMng extends Mng implements Iterable<JTextField>{
    private JLabel pro;
    private ArrayList<JTextField> proField;

    public ProMng(){
        super();
        GridBagConstraints gc = new GridBagConstraints();
        setUpPro(gc);
    }

    private void setUpPro(GridBagConstraints gc) {
        pro = new JLabel("Procedures: ");
        proField = new ArrayList<>();

        gc.weightx = 2;
        gc.weighty = 1;
        gc.gridx = 0;
        gc.gridy = 1;
        add(pro, gc);
        JLabel stepNum = new JLabel("Step");
        JLabel direction = new JLabel("Direction");
        gc.gridx = 1;
        gc.gridy = 1;
        add(stepNum,gc);
        gc.gridx =2;
        add(direction,gc);

        for (int i = 0; i < 5; i++) {
            JLabel step = new JLabel(String.valueOf(i+1));
            gc.gridx = 1;
            gc.gridy = 2 + i;
            add(step,gc);
            JTextField procedure = new JTextField(10);
            proField.add(procedure);
            gc.gridx = 2;
            add(procedure, gc);
        }

        JButton addBtn = new JButton("+");
        gc.gridx = 3;
        gc.gridy = 14;
        add(addBtn,gc);

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField newPro = new JTextField(10);
                gc.gridx = 1;
                gc.gridy = 8;
                proField.add(newPro);
                add(newPro,gc);
                remove(addBtn);
                gc.gridx = 3;
                gc.gridy++;
                add(addBtn,gc);
                repaint();
            }
        });

    }

    public ArrayList<Procedure> getProInfo(){
        ArrayList<Procedure> procedures = new ArrayList<>();
        int i = 1;
        for(JTextField line: proField){
            Procedure p = new Procedure();
            p.setStepNumber(i);
            p.setDirection(line.getText());
            procedures.add(p);
        }
        return procedures;
    }

    @Override
    public Iterator<JTextField> iterator() {
        return proField.iterator();
    }
}
