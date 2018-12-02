package module.Interfaces;

import module.Exceptions.CannotFindRecipeException;

import java.io.IOException;

public interface Storage {

    // REQUIRES: a valid component
    // MODIFIES: this
    // EFFECTS: add the component into storage
    void adding() throws IOException, CannotFindRecipeException;

    // REQUIRES: the storage has at least one element
    // MODIFIES: this
    // EFFECTS: calls on setters, getters, and other helper functions to modify a component
    void changing() throws IOException, CannotFindRecipeException;


    // EFFECTS: return the size of the storage
    int size();

    // EFFECTS: return the component with the name as the given name
    Components searching(String name) throws IOException, CannotFindRecipeException;

    void load() throws IOException, CannotFindRecipeException;

    void save() throws IOException;


}
