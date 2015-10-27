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
public class CadastroDoEmitente {
    
    //Panels
    private JPanel panelCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoes = new JPanel(new GridBagLayout());
    
    //Caixas de texto
    private JTextField txtNomeFantasia = new JTextField();
    private JTextField txtRazaoSocial = new JTextField();
    private JTextField txtInscricaoMunicipal= new JTextField();
    private JTextField txtInscricaoEstadual = new JTextField();
    private JTextField txtCEP = new JTextField();
    private JTextField txtEndereco= new JTextField();
    private JTextField txtTelefone = new JTextField();
    private JTextField txtEmail = new JTextField();
    
    //Botões
    private JButton botCadastrar = new JButton();
    private JButton botExcluir = new JButton();
    private JButton botLimpar = new JButton();
    
    //Tabela
    DefaultTableModel tabela = new DefaultTableModel();
    private JTable tblCadastroDoEmitente = new JTable(tabela);
    
    
    public CadastroDoEmitente(){
        this.iniciaComponentes();
    }
    
    public static void main(String[] args){
        CadastroDoEmitente emitente = new CadastroDoEmitente();
    }
    
    public void iniciaComponentes(){
        
        //Instância da classe monta interfaces, passei o nome do formulário e o caminho onde a imagem dele está
        MontaInterfaces telaOS = new MontaInterfaces("Cadastro do Emitente", "/imagens/dados-emitente.png");
        //Deixei a janela visível
        telaOS.setVisible(true);        
        //Adicionei as abas com o método addAbas e o panel para os botões com o método addPanelBotoes
        telaOS.addAbas(panelCadastro, "Cadastro");
        telaOS.addPanelBotoes(panelCadastro, panelBotoes);
        
        //Adicionei os botões dentro do panelBotoes
        telaOS.addBotoes("Cadastrar", botCadastrar, panelBotoes);
        telaOS.addBotoes("Excluir", botExcluir, panelBotoes);
        telaOS.addBotoes("Limpar", botLimpar, panelBotoes);
        
        //Criei objetos do tipo icone com o caminho do icone para coloca-los nos botões 
        Icon iconeCadastrar = new ImageIcon(getClass().getResource("/imagens/salvar.png"));
        Icon iconeExcluir = new ImageIcon(getClass().getResource("/imagens/excluir.png"));
        Icon iconeLimpar = new ImageIcon(getClass().getResource("/imagens/limpar.png"));
        
        //Seta os icones dos bortões
        botCadastrar.setIcon(iconeCadastrar);
        botExcluir.setIcon(iconeExcluir);
        botLimpar.setIcon(iconeLimpar);
        
        //Adiciona os componentes na tela
        telaOS.addLabelTitulo("Cliente", panelCadastro);
        telaOS.addDoisComponentes("Nome Fantasia", txtNomeFantasia, "Razão Social", txtRazaoSocial, panelCadastro);
        telaOS.addQuatroComponentes("Inscrição Municipal", txtInscricaoMunicipal, "Inscrição Estadual", txtInscricaoEstadual, "CEP", txtCEP, "Endereço", txtEndereco, panelCadastro);
        telaOS.addDoisComponentes("Telefone", txtTelefone, "E-mail", txtEmail, panelCadastro);

        
    }
    
}
