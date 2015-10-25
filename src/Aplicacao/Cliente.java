/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplicacao;

import Core.ConexaoBanco;
import Core.MontaInterfaces;
import java.awt.GridBagLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CASA
 */
public class Cliente {
    
    //Panels
    private JPanel panelConsulta = new JPanel(new GridBagLayout());
    private JPanel panelCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoes = new JPanel(new GridBagLayout());
    
    //Caixas de texto
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
    
    //Botões
    private JButton botCadastrar = new JButton();
    private JButton botExcluir = new JButton();
    private JButton botAlterar = new JButton();
    
    //Tabela
    DefaultTableModel tabela = new DefaultTableModel();
    private JTable tblClientes = new JTable(tabela);
    
    
    public Cliente(){
        this.iniciaComponentes();
    }
    
    public static void main(String[] args){
        Cliente cli = new Cliente();
    }
    
    public void iniciaComponentes(){
        
        //Instância da classe monta interfaces, passei o nome do formulário e o caminho onde a imagem dele está
        MontaInterfaces telaOS = new MontaInterfaces("Gerenciamento de Clientes", "/imagens/clientes.png");
        //Deixei a janela visível
        telaOS.setVisible(true);        
        //Adicionei as abas com o método addAbas e o panel para os botões com o método addPanelBotoes
        telaOS.addAbas(panelCadastro, "Cadastro");
        telaOS.addAbas(panelConsulta, "Consulta");
        telaOS.addPanelBotoes(panelCadastro, panelBotoes);
        
        //Adicionei os botões dentro do panelBotoes
        telaOS.addBotoes("Cadastrar", botCadastrar, panelBotoes);
        telaOS.addBotoes("Limpar", botExcluir, panelBotoes);
        
        //Criei objetos do tipo icone com o caminho do icone para coloca-los nos botões 
        Icon iconeCadastrar = new ImageIcon(getClass().getResource("/imagens/salvar.png"));
        Icon iconeExcluir = new ImageIcon(getClass().getResource("/imagens/excluir.png"));
        
        //Seta os icones dos bortões
        botCadastrar.setIcon(iconeCadastrar);
        botExcluir.setIcon(iconeExcluir);
        
        //Adiciona os componentes na tela
        telaOS.addLabelTitulo("Cliente", panelCadastro);
        telaOS.addQuatroComponentes("Código", txtCodigo, "Nome", txtNome, "Bairro", txtBairro, "Complemento", txtComplemento, panelCadastro);
        telaOS.addQuatroComponentes("RG", txtRG, "CPF", txtCPF, "CEP", txtCEP, "Estado", txtEstado, panelCadastro);
        telaOS.addDoisComponentes("Celular", txtCelular, "Email", txtEmail, panelCadastro);
        telaOS.addTabela(tblClientes, panelConsulta);
        ConexaoBanco teste=new ConexaoBanco();
        teste.preencheTabela(tabela, "select *from cliente");

        
    }
    
}
