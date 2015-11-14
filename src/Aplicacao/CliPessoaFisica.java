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
     private int cliRg;
     private int cliCpf;
    
    

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
     * @return the cliRg
     */
    public int getCliRg() {
        return cliRg;
    }

    /**
     * @param cliRg the cliRg to set
     */
    public void setCliRg(int cliRg) {
        this.cliRg = cliRg;
    }

    /**
     * @return the cliCpf
     */
    public int getCliCpf() {
        return cliCpf;
    }

    /**
     * @param cliCpf the cliCpf to set
     */
    public void setCliCpf(int cliCpf) {
        this.cliCpf = cliCpf;
    }
}
   
