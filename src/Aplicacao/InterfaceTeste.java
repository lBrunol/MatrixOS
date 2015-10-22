package Aplicacao;

import Core.ConexaoBanco;
import Core.MontaInterfaces;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

public class InterfaceTeste implements ActionListener {
    
    //Criação dos objetos, imprescindível criar ao menos um JPanel para servir de aba no formulário
    private JTextField txtCodigo = new JTextField();
    private JTextField txtNome = new JTextField();
    private JTextField txtRG= new JTextField();
    private JTextField txtCPF = new JTextField();
    private JTextField txtCEP = new JTextField();
    private JTextField txtEstado  = new JTextField();
    private JTextField txtBairro= new JTextField();
    private JTextField txtComplemento = new JTextField();
    private JTextField txtCelular = new JTextField();
    private JTextField txtEmail  = new JTextField();
    private JButton botCadastrar = new JButton();
    private JButton botExcluir = new JButton();
    private JButton botAlterar = new JButton();
    DefaultTableModel tabela = new DefaultTableModel();
    private JTable tblClientes = new JTable(tabela);
    private JPanel panelConsulta = new JPanel(new GridBagLayout());
    private JPanel panelCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoes = new JPanel(new GridBagLayout());
    private int codigo;
    private String nome;
    ConexaoBanco conexao = new ConexaoBanco();
    
    public InterfaceTeste(){
		
    }
	
    public static void main(String[] args){
            InterfaceTeste teste = new InterfaceTeste();
            teste.iniciaComponentes();	

    }
	
    private void iniciaComponentes(){

        Icon icone = new ImageIcon(getClass().getResource("/imagens/salvar.png"));
        Icon iconeExcluir = new ImageIcon(getClass().getResource("/imagens/excluir.png"));
        Icon iconeAlterar = new ImageIcon(getClass().getResource("/imagens/alterar.png"));
        botCadastrar.setIcon(icone);
        botCadastrar.addActionListener(this);
        botExcluir.setIcon(iconeExcluir);
        botAlterar.setIcon(iconeAlterar);

        //Instância da classe que montará a interface da sua janela, aqui deve ser passado o título da janela
        MontaInterfaces testeInterface = new MontaInterfaces("Tela de exemplo", "/imagens/icone-servico.png");
        
        //Instância da classe de conexão com banco (teste da funcionalidade da tabela)
        ConexaoBanco cn = new ConexaoBanco("localhost", "system", "fanbno022");
        cn.preencheTabela(tabela, "SELECT * FROM CLIENTE");

        //Métodos que criarão a estrutura, imprescindível chamar o método addTabela que aceita um JPanel para adicionar ao menos uma aba        
        testeInterface.addAbas(panelCadastro, "Cadastro");
        testeInterface.addAbas(panelConsulta, "Consulta");
        //Em todos os métodos que adicionarão conteúdo, deve ser passado a aba que ele será adicionado, no caso o último argumento
        testeInterface.addPanelBotoes(panelCadastro, panelBotoes);
        testeInterface.addBotoes("Cadastrar", botCadastrar, panelBotoes);
        testeInterface.addBotoes("Excluir", botExcluir, panelBotoes);
        testeInterface.addBotoes("Alterar", botAlterar, panelBotoes);
        testeInterface.addDoisComponentes("Código", txtCodigo, "Nome", txtNome, panelCadastro);
        testeInterface.addDoisComponentes("RG", txtRG, "CPF", txtCPF, panelCadastro);	
        testeInterface.addUmComponente("Estado", txtEstado, panelCadastro);
        testeInterface.addUmComponente("CEP", txtCEP, panelCadastro);
        testeInterface.addQuatroComponentes("Bairro", txtBairro, "Celular", txtCelular, "Email", txtEmail, "Complemento", txtComplemento, panelCadastro);
        testeInterface.addTabela(tblClientes, panelConsulta);
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
                //conexao.executaProcedure("insert_cargos (" + codigo + ",'" + nome + "')");
                JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso");
            }
        }

    }   
}
