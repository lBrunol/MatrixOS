/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import javax.swing.JTextField;

/**
 *
 * @author CASA
 */
public class PTextField extends JTextField {
    
    private boolean obrigatorio;
    
    public PTextField() {
        super();
        this.setObrigatorio(true);

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
