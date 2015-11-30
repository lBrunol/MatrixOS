package Aplicacao;

import Core.ConexaoBanco;
import Core.MetodosAuxiliares;
import java.sql.SQLException;
import javax.swing.JOptionPane;


 /**
 *
 * @author Amanda
 */
public class CliPessoaFisica {

   
     //Instância da classe Métodos auxliares
     private MetodosAuxiliares auxiliar = new MetodosAuxiliares();
     ConexaoBanco conexao = new ConexaoBanco();
     
     private int cliCodigo;
     private String cliRg;
     private String cliCpf;
     
     public boolean cadastrar(){
        try {
            conexao.executaProcedure("INSERT_CLIPESSOAFISICA ('" + this.cliRg + "', '" + this.cliCpf + "' , " + this.cliCodigo + ")");
            return true;
             
        }catch (SQLException b) {
            JOptionPane.showMessageDialog(null, b.getMessage() + ". Ocorreu um erro de SQL. Por favor, entre em contato com administrador do sistema.");
        }
        catch (Exception b) {
            JOptionPane.showMessageDialog(null,"Erro desconhecido. Por favor entre em contato com administrador do sistema. \n" + b.getMessage());
        }
        
        return false;
     }
     
     public boolean deletar()
     {
          try {
            conexao.executaProcedure("DELETE_CLIPESSOAFISICA (" + this.cliCodigo + ")");
            return true;
             
        }catch (SQLException b) {
            JOptionPane.showMessageDialog(null, b.getMessage() + ". Ocorreu um erro de SQL. Por favor, entre em contato com administrador do sistema.");
        }
        catch (Exception b) {
            JOptionPane.showMessageDialog(null,"Erro desconhecido. Por favor entre em contato com administrador do sistema. \n" + b.getMessage());
        }
        
        return false;
     }

      public boolean alterar()
     {
          try {
            conexao.executaProcedure("UPDATE_CLIPESSOAFISICA ('" + this.cliRg + "', '" + this.cliCpf + "' , " + this.cliCodigo + ")");
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
     * @return the cliRg
     */
    public String getCliRg() {
        return cliRg;
    }

    /**
     * @param cliRg the cliRg to set
     */
    public void setCliRg(String cliRg) {
        this.cliRg = cliRg;
    }

    /**
     * @return the cliCpf
     */
    public String getCliCpf() {
        return cliCpf;
    }

    /**
     * @param cliCpf the cliCpf to set
     */
    public void setCliCpf(String cliCpf) {
        this.cliCpf = cliCpf;
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
}
   
