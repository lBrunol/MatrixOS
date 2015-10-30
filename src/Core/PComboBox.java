/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import javax.swing.JComboBox;

/**
 *
 * @author CASA
 */
public class PComboBox extends JComboBox{
    
    private boolean obrigatorio = true;
    
    public PComboBox(){
        super();
    }
    
    /**
     * @return the OBRIGATORIO
     */
    public boolean isObrigatorio() {
        return obrigatorio;
    }

    /**
     * @param obrigatorio the OBRIGATORIO to set
     */
    public void setObrigatorio(boolean obrigatorio) {
        this.obrigatorio = obrigatorio;
    }
    
}
