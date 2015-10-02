package Aplicacao;

import Core.ConexaoBanco;
import Core.MontaInterfaces;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class InterfaceTeste implements ActionListener {
    
    //Criação dos objetos, imprescindível criar ao menos um JPanel para servir de aba no formulário
    private JTextField txtCodigo = new JTextField();
    private JTextField txtNome = new JTextField();
    private JTextField txtRG= new JTextField();
    private JTextField txtCPF = new JTextField();
    private JTextField txtCEP = new JTextField();
    private JTextField txtEstado  = new JTextField();
    private JButton botCadastrar = new JButton();
    private JButton botExcluir = new JButton();
    DefaultTableModel tabela = new DefaultTableModel();
    private JTable tblClientes = new JTable(tabela);
    private JPanel panelConsulta = new JPanel(new GridBagLayout());
    private JPanel panelCadastro = new JPanel(new GridBagLayout());
    private int codigo;
    private String nome;
    ConexaoBanco conexao = new ConexaoBanco("localhost","system","2020");
    
    public InterfaceTeste(){
		
    }
	
    public static void main(String[] args){
            InterfaceTeste teste = new InterfaceTeste();
            teste.iniciaComponentes();	

    }
	
    private void iniciaComponentes(){

        Icon icone = new ImageIcon(getClass().getResource("/imagens/salvar.png"));
        Icon iconeExcluir = new ImageIcon(getClass().getResource("/imagens/excluir.png"));
        botCadastrar.setIcon(icone);
        botCadastrar.addActionListener(this);
        botExcluir.setIcon(iconeExcluir);

        //Instância da classe que montará a interface da sua janela, aqui deve ser passado o título da janela
        MontaInterfaces testeInterface = new MontaInterfaces("Tela de exemplo");
        
        //Instância da classe de conexão com banco (teste da funcionalidade da tabela)
        ConexaoBanco cn = new ConexaoBanco("localhost", "system", "fanbno022");
        cn.preencheTabela(tabela, "SELECT * FROM CLIENTE");

        //Métodos que criarão a estrutura, imprescindível chamar o método add que aceita um JPanel para adicionar ao menos uma aba
        testeInterface.add(panelCadastro, "Cadastro");
        testeInterface.add(panelConsulta, "Consulta");
        //Em todos os métodos que adicionarão conteúdo, deve ser passado a aba que ele será adicionado, no caso o último argumento
        testeInterface.add("Cadastrar", botCadastrar, panelCadastro);
        //testeInterface.add("Excluir", botExcluir, panelCadastro);
        testeInterface.add("Código", txtCodigo, "Nome", txtNome, panelCadastro);
        testeInterface.add("RG", txtRG, "CPF", txtCPF, panelCadastro);	
        testeInterface.add("Estado", txtEstado, panelCadastro);
        testeInterface.add("CEP", txtCEP, panelCadastro);
        testeInterface.add(tblClientes, panelConsulta);
        testeInterface.setTamanho(800, 800);
        testeInterface.setVisible(true);
    }
	
    boolean cadastrar(){
        if(txtCodigo.getText().isEmpty() || txtNome.getText().isEmpty()){			
            JOptionPane.showMessageDialog(null, "Preencha os campos corretamente");
            return false;
        }else{            
            this.codigo = Integer.parseInt(txtCodigo.getText());
            this.nome = txtNome.getText();
            return true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent botao) {
        //Retorna qual o botão clicado e gera a ação
        if (botao.getSource() == botCadastrar ){
            boolean ok ;
            ok = cadastrar();
            if(ok){	
                conexao.executaProcedure("insert_cargos (" + codigo + ",'" + nome + "')");
                JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso");
            }
        }

    }
    
}
