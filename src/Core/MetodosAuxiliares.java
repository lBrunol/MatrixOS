/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author CASA
 */
public class MetodosAuxiliares {
    
    public static final String MASCARA_CPF = "###.###.###-##";
    public static final String MASCARA_RG = "##.###.###-#";
    public static final String MASCARA_TELEFONE = "(##) ####-####";
    public static final String MASCARA_DATA = "##/##/####";
    public static final String MASCARA_CNPJ = "##.###.###/####-##";
    public static final String MASCARA_CEP = "#####-###";
    
    public MetodosAuxiliares(){
    
    }
    
    /** Função que calcula um valor total a partir do valor unitário * a quantidade
     * 
     * @param valor variavel do tipo float que serve como valor unitário
     * @param quantidade variável do tipo inteiro que serve para calcular o valor total
     * @return resultado valor total
     */
    public float calculaValorTotal(float valor, int quantidade){
        float resultado;
        
        resultado = valor * quantidade;
        
        return resultado;
    }
    
    public MaskFormatter inseriMascara (String mascara){
        MaskFormatter ms = new MaskFormatter();     
        try {                   
            ms.setMask(mascara);
            ms.setPlaceholderCharacter(' ');
        } catch (Exception e) {            
            try {
                ms.setMask("");
            } catch (ParseException err) {
                JOptionPane.showMessageDialog(null, "Erro grave, favor reportar ao administrador do sistema");
            }
            return ms;
        }       
        return ms;
    }   
}
