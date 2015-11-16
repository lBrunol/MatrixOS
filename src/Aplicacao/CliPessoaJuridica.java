package Aplicacao;

import Core.MetodosAuxiliares;
import Core.MontaInterfaces;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;



/**
 *
 * @author Amanda
 */
public class CliPessoaJuridica  {

    
    
     //Instância da classe Métodos auxliares
     private MetodosAuxiliares auxiliar = new MetodosAuxiliares();
    
     //Caixas de texto
     private String strRazaoSocial;
     private int intIM;
     private int intIE;
     private String strNomeFantasia;
     
     

 
 
 
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
     * @return the strRazaoSocial
     */
    public String getStrRazaoSocial() {
        return strRazaoSocial;
    }

    /**
     * @param strRazaoSocial the strRazaoSocial to set
     */
    public void setStrRazaoSocial(String strRazaoSocial) {
        this.strRazaoSocial = strRazaoSocial;
    }

    /**
     * @return the intIM
     */
    public int getIntIM() {
        return intIM;
    }

    /**
     * @param intIM the intIM to set
     */
    public void setIntIM(int intIM) {
        this.intIM = intIM;
    }

    /**
     * @return the intIE
     */
    public int getIntIE() {
        return intIE;
    }

    /**
     * @param intIE the intIE to set
     */
    public void setIntIE(int intIE) {
        this.intIE = intIE;
    }

    /**
     * @return the strNomeFantasia
     */
    public String getStrNomeFantasia() {
        return strNomeFantasia;
    }

    /**
     * @param strNomeFantasia the strNomeFantasia to set
     */
    public void setStrNomeFantasia(String strNomeFantasia) {
        this.strNomeFantasia = strNomeFantasia;
    }
    
    

    
}
