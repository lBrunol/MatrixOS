package Aplicacao;

import Core.ConexaoBanco;
import Core.MetodosAuxiliares;
import java.sql.SQLException;
import javax.swing.JOptionPane;




/**
 *
 * @author Amanda
 */
public class CliPessoaJuridica  {

    
    
     //Instância da classe Métodos auxliares
     private MetodosAuxiliares auxiliar = new MetodosAuxiliares();
     ConexaoBanco conexao = new ConexaoBanco();
    
     //Caixas de texto
    private String longCnpj;
    private String strRazaoSocial;
    private String intIM;
    private String intIE;
    private String strNomeFantasia;
    private int cliCodigo;
     
     
       public boolean cadastrar(){
        try {
            conexao.executaProcedure("INSERT_CLIPESSOAJURIDICA ('" + this.strRazaoSocial + "', '" + this.strNomeFantasia + "', '" + this.longCnpj + "', '"+ this.intIM + "','" + this.intIE + "', "+ this.cliCodigo + ")");
            return true;
             
        }catch (SQLException b) {
            JOptionPane.showMessageDialog(null, b.getMessage() + ". Ocorreu um erro de SQL. Por favor, entre em contato com administrador do sistema.");
        }
        catch (Exception b) {
            JOptionPane.showMessageDialog(null,"Erro desconhecido. Por favor entre em contato com administrador do sistema. \n" + b.getMessage());
        }
        
        return false;
        
       }
       
        public boolean deletar(){
        try {
            conexao.executaProcedure("DELETE_CLIPESSOAJURIDICA (" + this.getCliCodigo() + ")");
            return true;
             
        }catch (SQLException b) {
            JOptionPane.showMessageDialog(null, b.getMessage() + ". Ocorreu um erro de SQL. Por favor, entre em contato com administrador do sistema.");
        }
        catch (Exception b) {
            JOptionPane.showMessageDialog(null,"Erro desconhecido. Por favor entre em contato com administrador do sistema. \n" + b.getMessage());
        }
        
        return false;
        
       }
     
       public boolean alterar(){
        try {
            conexao.executaProcedure("UPDATE_CLIPESSOAJURIDICA ('" + this.strRazaoSocial + "', '" + this.strNomeFantasia + "', '" + this.longCnpj + "', '"+ this.intIM + "','" + this.intIE + "', " + this.cliCodigo + ")");
            return true;
             
        }catch (SQLException b) {
            JOptionPane.showMessageDialog(null, b.getMessage() + ". Ocorreu um erro de SQL. Por favor, entre em contato com administrador do sistema.");
        }
        catch (Exception b) {
            JOptionPane.showMessageDialog(null,"Erro desconhecido. Por favor entre em contato com administrador do sistema. \n" + b.getMessage());
        }
        
        return false;
        
       }

 
 
 
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
    public String getIntIM() {
        return intIM;
    }

    /**
     * @param intIM the intIM to set
     */
    public void setIntIM(String intIM) {
        this.intIM = intIM;
    }

    /**
     * @return the intIE
     */
    public String getIntIE() {
        return intIE;
    }

    /**
     * @param intIE the intIE to set
     */
    public void setIntIE(String intIE) {
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

    /**
     * @return the longCnpj
     */
    public String getLongCnpj() {
        return longCnpj;
    }

    /**
     * @param longCnpj the longCnpj to set
     */
    public void setLongCnpj(String longCnpj) {
        this.longCnpj = longCnpj;
    }

    /**
     * @return the cliCodigo
     */
    public int getCliCodigo() {
        return cliCodigo;
    }

    /**
     * @param cliCodigo the cliCodigo to set
     */
    public void setCliCodigo(int cliCodigo) {
        this.cliCodigo = cliCodigo;
    }

    /**
     * @return the intCnpj
     */
  

    
}
