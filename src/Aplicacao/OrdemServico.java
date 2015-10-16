/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplicacao;

import Core.ComboItem;
import Core.ConexaoBanco;
import Core.MontaInterfaces;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.naming.spi.DirStateFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CASA
 */
public class OrdemServico implements ActionListener  {
    
    //Criação dos objetos, imprescindível criar ao menos um JPanel para servir de aba no formulário
    private JTextField txtCodigo = new JTextField();
    private JFormattedTextField txtDataAbertura = new JFormattedTextField();
    private JFormattedTextField txtDataFechamento = new JFormattedTextField();
    private JTextArea txtDescricaoOcorrencia = new JTextArea();
    private JTextField txtValorTotal = new JTextField();
    
    //Clientes
    private JComboBox cboCliente  = new JComboBox();
    private JTextField txtEndereco = new JTextField();
    private JTextField txtNumEndereco = new JTextField();
    private JTextField txtComplemento = new JTextField();
    private JFormattedTextField txtCEP = new JFormattedTextField();
    private JTextField txtBairro= new JTextField();
    private JTextField txtCidade = new JTextField();
    private JTextField txtUF  = new JTextField();
    private JFormattedTextField txtTelefone = new JFormattedTextField();    
    private JFormattedTextField txtCPF = new JFormattedTextField();
    private JFormattedTextField txtRG  = new JFormattedTextField();
    
    
    //Funcionários
    private JComboBox cboFuncionario  = new JComboBox();
    private JTextField txtCargo = new JTextField();
    
    //Serviços
    private JComboBox cboServico  = new JComboBox();
    private JTextField txtValor = new JTextField();
    private JTextField txtQtde = new JTextField();
    private JTextArea txtDescricaoServico = new JTextArea();    
    
    //Botões
    private JButton botCadastrar = new JButton();
    private JButton botLimpar = new JButton();
    private JButton botInserir = new JButton();
    private JButton botFaturarNotaFiscal = new JButton();
    private JButton botExcluir = new JButton();
    private JButton botAlterarRegistro = new JButton();
    
    DefaultTableModel tabela = new DefaultTableModel();
    
    //Tabela
    private JTable tblOrdemServico = new JTable(tabela);
    
    //Panels (Abas)
    private JPanel panelConsulta = new JPanel(new GridBagLayout());
    private JPanel panelCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoesCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoesAlteracao = new JPanel(new GridBagLayout());
    
    //Instância da classe de conexão com o banco
    ConexaoBanco conexao = new ConexaoBanco();
    
    public OrdemServico(){
        this.iniciaComponentes();
        
    }
    
    public static void main (String args[]){
        OrdemServico os = new OrdemServico();        
    }
    
    public void iniciaComponentes(){
        MontaInterfaces telaOS = new MontaInterfaces("Gerenciamento de Ordem de Serviço", "/imagens/os.png");
        telaOS.setVisible(true);
        telaOS.setTamanho(1000, 1000);
        
        telaOS.addAbas(panelCadastro, "Cadastro");
        telaOS.addPanelBotoes(panelCadastro, panelBotoesCadastro);
        telaOS.addPanelBotoes(panelCadastro, panelBotoesAlteracao);
        
        //Botões de cadastro
        telaOS.addBotoes("Cadastrar", botCadastrar, panelBotoesCadastro);
        telaOS.addBotoes("Limpar", botLimpar, panelBotoesCadastro);
        
        //Botões de alteração
        panelBotoesAlteracao.setVisible(false);
        telaOS.addBotoes("Faturar Nota Fiscal", botFaturarNotaFiscal, panelBotoesAlteracao);
        telaOS.addBotoes("Modo Inserir", botInserir, panelBotoesAlteracao);
        telaOS.addBotoes("Excluir Registro", botExcluir, panelBotoesAlteracao);
        telaOS.addBotoes("Alterar Registro", botAlterarRegistro, panelBotoesAlteracao);
        
        //Cria objetos do tipo icone para coloca-los nos botões
        Icon iconeCadastrar = new ImageIcon(getClass().getResource("/imagens/salvar.png"));
        Icon iconeExcluir = new ImageIcon(getClass().getResource("/imagens/excluir.png"));
        Icon iconeAlterar = new ImageIcon(getClass().getResource("/imagens/alterar.png"));
        Icon iconeInserir = new ImageIcon(getClass().getResource("/imagens/adicionar.png"));
        Icon iconeFaturar = new ImageIcon(getClass().getResource("/imagens/faturar-nota-fiscal.png"));
        Icon iconeLimpar = new ImageIcon(getClass().getResource("/imagens/limpar.png"));
        
        //Seta os icones dos bortões
        botAlterarRegistro.setIcon(iconeAlterar);
        botCadastrar.setIcon(iconeCadastrar);
        botExcluir.setIcon(iconeExcluir);
        botFaturarNotaFiscal.setIcon(iconeFaturar);
        botInserir.setIcon(iconeInserir);
        botLimpar.setIcon(iconeLimpar);
        
        //Adiciona eventos aos botões 
        botCadastrar.addActionListener(this);
        botInserir.addActionListener(this);
        
        conexao.preencheCaixasTexto(txtBairro); 
        
        //Adiciona os componentes na tela
        telaOS.addLabelTitulo("Ordem de Serviço", panelCadastro);
        telaOS.addQuatroComponentes("Código", txtCodigo, "Data de Abertura", txtDataAbertura, "Data de Fechamento", txtDataFechamento, "Valor Total", txtValorTotal, panelCadastro);
        telaOS.addUmComponente("Descrição da Ocorrência", txtDescricaoOcorrencia, panelCadastro);
        telaOS.addLabelTitulo("Cliente", panelCadastro);
        telaOS.addUmComponente("Selecione o cliente*", cboCliente, panelCadastro);
        telaOS.addQuatroComponentes("Endereço", txtEndereco, "N°", txtNumEndereco, "Complemento", txtComplemento, "CEP", txtCEP, panelCadastro);
        telaOS.addQuatroComponentes("Bairro", txtBairro, "Cidade", txtCidade, "UF ",txtUF, "Telefone", txtTelefone, panelCadastro);
        telaOS.addDoisComponentes("CPF", txtCPF, "RG", txtRG, panelCadastro);
        telaOS.addLabelTitulo("Funcionário", panelCadastro);
        telaOS.addDoisComponentes("Selecione o Funcionário*", cboFuncionario, "Cargo", txtCargo, panelCadastro);  
        telaOS.addLabelTitulo("Serviço", panelCadastro);
        telaOS.addQuatroComponentes("Selecione o Serviço*", cboServico, "Valor", txtValor,"Quantidade", txtQtde,  "Descrição", txtDescricaoServico,  panelCadastro);        
        telaOS.addAbas(panelConsulta, "Consulta");
        telaOS.addTabela(tblOrdemServico, panelConsulta);
        
        conexao.preencheTabela(tabela, "SELECT ordCodigo Código, ordOcorrencia Ocorrência, ordDataAbertura, cliNome, funNome FROM cliente INNER JOIN (funcionario INNER JOIN ordemServico ON funcionario.funMatricula = ordemServico.funMatricula ) ON cliente.cliCodigo = ordemServico.cliCodigo");
        
        
        //Preenche as combobox
        cboCliente.addItem("");
        cboFuncionario.addItem("");
        cboServico.addItem("");
        
        conexao.preencheCombo(cboCliente, "SELECT * FROM cliente");
        conexao.preencheCombo(cboFuncionario, "SELECT * FROM funcionario");
        conexao.preencheCombo(cboServico, "SELECT * FROM servicos");
        cboCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            int idCombo;
                
                ComboItem comboItem = (ComboItem) cboCliente.getSelectedItem();
                    System.out.println(comboItem.getId());
                    idCombo = Integer.parseInt(comboItem.getId());
                
                try {

                    ResultSet rs = conexao.executar("SELECT cliNome, cliBairro, cliEndereco, cliNumEndereco, cliCEP FROM cliente WHERE cliCodigo = " + idCombo);
                    rs.next();
                    txtBairro.setText(rs.getString(2));
                    txtEndereco.setText(rs.getString(3));
                    txtNumEndereco.setText(rs.getString(4));         


                } catch (Exception b) {
                    System.out.println("erro" + b.getMessage());
                }
                }
        });
        
    }    
    
    public void mostraBotoesAlteracao(){
        if(panelBotoesAlteracao.isVisible()){
            panelBotoesAlteracao.setVisible(false);
            panelBotoesCadastro.setVisible(true);
        }else{
            panelBotoesAlteracao.setVisible(true);
            panelBotoesCadastro.setVisible(false);
        }
    }
    
    public void mostraBotoesCadastro(){
        if(panelBotoesCadastro.isVisible()){
            panelBotoesAlteracao.setVisible(true);
            panelBotoesCadastro.setVisible(false);
        }else{
            panelBotoesAlteracao.setVisible(false);
            panelBotoesCadastro.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent botao) {
        //Retorna qual o botão clicado e gera a ação
        if (botao.getSource() == botCadastrar ){
            this.mostraBotoesAlteracao();
        }
        if (botao.getSource() == botInserir ){
            this.mostraBotoesCadastro();
        }
    }
    
}
