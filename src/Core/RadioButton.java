/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

/**
 *
 * @author Amanda
 */

import javax.swing.JFrame;
import javax.swing.JRadioButton;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JOptionPane;


public class RadioButton extends JFrame {
	private JRadioButton rdbpf;
        private JRadioButton rdbpj;
	private RadioButtonHandler handler;
        private boolean obrigatorio = true;
	public RadioButton(){
              
		super();
		setLayout( new FlowLayout() );
		
		
		handler = new RadioButtonHandler();
		rdbpf = new JRadioButton("Pessoa Física", false);
	        rdbpj = new JRadioButton("Pessoa Jurídica", false);
		
		add(rdbpf);
		add(rdbpj);

		rdbpf.addItemListener(handler);
		rdbpj.addItemListener(handler); 
        
        
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
	

   
private class RadioButtonHandler implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent event) {
		//Aqui é para destinguir qual o tipo de cliente e o que fazer quando for selecionado.
                //Seria para ocultar e aparecer os itens distintos.
                if(  rdbpf.isSelected()){
                    
                      
        
                    rdbpj.setSelected(false);
          
                }
                else if(  rdbpj.isSelected()){
                  
                    rdbpf.setSelected(false);
                 }
           }
  
    }
}