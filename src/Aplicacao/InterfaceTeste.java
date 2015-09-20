package Aplicacao;

import Core.MontaInterfaces;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InterfaceTeste {
    
    public InterfaceTeste(){
		
    }
    
    public static void main(String[] args){
        InterfaceTeste teste = new InterfaceTeste();
        teste.iniciaComponentes();
    }
    private void iniciaComponentes(){
		
        //Criação dos objetos, imprescindível criar ao menos um JPanel para servir de aba no formulário
        JTextField txtCodigo = new JTextField();
        JTextField txtNome = new JTextField();
        JTextField txtRG= new JTextField();
        JTextField txtCPF = new JTextField();
        JTextField txtCEP = new JTextField();
        JTextField txtEstado  = new JTextField();
        JPanel panelConsulta = new JPanel(new GridBagLayout());
        JPanel panelCadastro = new JPanel(new GridBagLayout());
        //Instância da classe que montará a interface da sua janela, aqui deve ser passado o título da janela
        MontaInterfaces testeInterface = new MontaInterfaces("Tela de exemplo");

        //Métodos que criarão a estrutura, imprescindível chamar o método add que aceita um JPanel para adicionar ao menos uma aba
        testeInterface.add(panelCadastro, "Cadastro");
        testeInterface.add(panelConsulta, "Consulta");
        //Em todos os métodos que adicionarão conteúdo, deve ser passado a aba que ele será adicionado, no caso o último argumento
        testeInterface.add("Código", txtCodigo, "Nome", txtNome, panelCadastro);
        testeInterface.add("RG", txtRG, "CPF", txtCPF, panelCadastro);	
        testeInterface.add("Estado", txtEstado, panelConsulta);
        testeInterface.add("CEP", txtCEP, panelConsulta);
        testeInterface.setTamanho(600, 600);
        testeInterface.setVisible(true);		
    }
    
}
