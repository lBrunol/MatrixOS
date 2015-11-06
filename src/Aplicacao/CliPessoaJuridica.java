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
     
    private JTextField txtRazaoSocial = new JTextField();
    private JTextField txtNomeFantasia = new JTextField();
    private JTextField txtIM = new JTextField();
    private JTextField txtIE = new JTextField();
    private JFormattedTextField txtCNPJ = new JFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_CNPJ));

    public void cadastrarPJ(){}
    public void alterarPJ(){}
    public void excluirPJ(){}
    
    
    
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
     * @return the txtRazaoSocial
     */
    public JTextField getTxtRazaoSocial() {
        return txtRazaoSocial;
    }

    /**
     * @param txtRazaoSocial the txtRazaoSocial to set
     */
    public void setTxtRazaoSocial(JTextField txtRazaoSocial) {
        this.txtRazaoSocial = txtRazaoSocial;
    }

    /**
     * @return the txtNomeFantasia
     */
    public JTextField getTxtNomeFantasia() {
        return txtNomeFantasia;
    }

    /**
     * @param txtNomeFantasia the txtNomeFantasia to set
     */
    public void setTxtNomeFantasia(JTextField txtNomeFantasia) {
        this.txtNomeFantasia = txtNomeFantasia;
    }

    /**
     * @return the txtIM
     */
    public JTextField getTxtIM() {
        return txtIM;
    }

    /**
     * @param txtIM the txtIM to set
     */
    public void setTxtIM(JTextField txtIM) {
        this.txtIM = txtIM;
    }

    /**
     * @return the txtIE
     */
    public JTextField getTxtIE() {
        return txtIE;
    }

    /**
     * @param txtIE the txtIE to set
     */
    public void setTxtIE(JTextField txtIE) {
        this.txtIE = txtIE;
    }

    /**
     * @return the txtCNPJ
     */
    public JFormattedTextField getTxtCNPJ() {
        return txtCNPJ;
    }

    /**
     * @param txtCNPJ the txtCNPJ to set
     */
    public void setTxtCNPJ(JFormattedTextField txtCNPJ) {
        this.txtCNPJ = txtCNPJ;
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
