/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

/**
 *
 * @author CASA
 */
public class ComboItem {
    private String id;
    private String nome;
    private int index;
    
    
    public ComboItem(String id, String nome, int index) {
        this.id = id;
        this.nome = nome;
        this.index = index;
    }
    

    // This will be used internally by JComboBox as the label to be displayed.
    @Override
    public String toString() {
        return getNome();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }
}
