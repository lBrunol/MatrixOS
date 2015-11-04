package Aplicacao;

import Core.MetodosAuxiliares;
import Core.MontaInterfaces;
import java.awt.GridBagLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;


 /**
 *
 * @author Amanda
 */
public class CliPessoaFisica {

   
     //Instância da classe Métodos auxliares
     private MetodosAuxiliares auxiliar = new MetodosAuxiliares();
    
     //Caixas de texto
     private JTextField txtCPF = new  JFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_CPF));
     private JFormattedTextField txtRG = new JFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_RG));

    public void cadastrarPF(){}
    public void alterarPF(){}
    public void excluirPF(){}
    /**
     * @return the auxiliar
     */
    public MetodosAuxiliares getAuxiliar() {
        return auxiliar;
    }

    /**
     * @param auxiliar the auxiliar to set
     */
    public void setAuxiliar(MetodosAuxiliares auxiliar) {
        this.auxiliar = auxiliar;
    }

    /**
     * @return the txtCPF
     * 
     * 
     */
    public JTextField getTxtCPF() {
        return txtCPF;
    }

    /**
     * @param txtCPF the txtCPF to set
     */
    public void setTxtCPF(JTextField txtCPF) {
        this.txtCPF = txtCPF;
    }

    /**
     * @return the txtRG
     */
    public JFormattedTextField getTxtRG() {
        return txtRG;
    }

    /**
     * @param txtRG the txtRG to set
     */
    public void setTxtRG(JFormattedTextField txtRG) {
        this.txtRG = txtRG;
    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
     
    
        
    
         

}
