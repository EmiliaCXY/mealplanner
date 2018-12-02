package ui.MainGUI.RecipeLibraryUI.RecipeGUI;

import module.Recipes.RecipeComponents.Ingredient;
import module.Recipes.RecipeComponents.Measurement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

public class IngreMng extends Mng implements Iterable<JTextField>{
    private JLabel ingre;
    private ArrayList<JTextField> ingreField;
    private ArrayList<JComboBox> measurements;


    public IngreMng(){
        super();
        GridBagConstraints gc = new GridBagConstraints();
        setUpIngre(gc);
    }

    private void setUpIngre(GridBagConstraints gc){
        ingre = new JLabel("Ingredients: ");
        ingreField = new ArrayList<>();
        measurements = new ArrayList<>();
        gc.weightx = 0.5;
        gc.weighty = 0.5;
        gc.gridx = 0;
        gc.gridy = 1;
        add(ingre,gc);

        JLabel mat = new JLabel("Material");
        JLabel amt = new JLabel("Amount");
        JLabel mea = new JLabel("Measurement");
        gc.gridx = 1;
        gc.gridy = 1;
        add(mat,gc);
        gc.gridx = 2;
        add(amt,gc);
        gc.gridx = 3;
        add(mea,gc);
        for(int i=0;i<5;i++){
            gc.gridx = 1;
            gc.gridy = 2+i;
            JLabel index = new JLabel(String.valueOf(i+1));
            JTextField material = new JTextField(10);
            JTextField amount = new JTextField(5);
            String[] measurementChoice = Measurement.stringsMeasurement();
            JComboBox measurement = new JComboBox(measurementChoice);
            measurements.add(measurement);
            ingreField.add(material);
            add(material,gc);
            gc.gridx = 2;
            add(amount,gc);
            gc.gridx = 3;
            add(measurement,gc);
            gc.gridx = 0;
            gc.gridy = 2+i;
            add(index,gc);
        }
        JButton addBtn = new JButton("+");
        gc.gridx = 4;
        gc.gridy = 9;
        add(addBtn,gc);


        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField newIngre = new JTextField(10);
                JTextField newAmount = new JTextField(5);
                String[] newMeasurementChoice = Measurement.stringsMeasurement();
                JComboBox measurement = new JComboBox(newMeasurementChoice);
                measurements.add(measurement);
                int y = ingreField.size();
                JLabel index = new JLabel(String.valueOf(y+1));
                gc.gridx = 0;
                gc.gridy = y+5;
                add(index,gc);
                ingreField.add(newIngre);
                gc.gridx = 1;
                gc.gridy = y+5;
                add(newIngre,gc);
                gc.gridx = 2;
                add(newAmount,gc);
                gc.gridx = 3;
                add(measurement,gc);
                remove(addBtn);
                gc.gridx = 4;
                gc.gridy++;
                add(addBtn,gc);
                repaint();
            }
        });
    }

    public ArrayList<Ingredient> getIngreInfo(){
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for(int num=0;num<ingreField.size();num++){
            Ingredient i = new Ingredient();
            i.setMaterial(ingreField.get(num).getText());
            i.setAmount(1);
            ingredients.add(i);
        }
        for(Ingredient i: ingredients){
            int index = ingredients.indexOf(i);
            String mea = measurements.get(index).getSelectedItem().toString().toUpperCase();
            i.setMeasurement(Measurement.valueOf(mea));
        }
        return ingredients;
    }

    @Override
    public Iterator<JTextField> iterator() {
        return ingreField.iterator();
    }
}
