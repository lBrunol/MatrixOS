/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import javax.swing.JFormattedTextField;

/**
 *
 * @author CASA
 */
public class PFormattedTextField extends JFormattedTextField {
    
    private boolean obrigatorio = true;
    
    public PFormattedTextField() {
        super();
    }
    
    public PFormattedTextField(AbstractFormatter formatter) {
        super(formatter);
    }
    
    public PFormattedTextField(AbstractFormatterFactory factory) {
        super(factory);
    }
    
    public PFormattedTextField(java.text.Format format) {
        super(format);
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
