/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplicacao;
import Core.ConexaoBanco;
import Core.MontaInterfaces;
import Core.PTextField;
import java.awt.GridBagLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Drikka´s
 */
public class Funcionario {
     //Panels
    private JPanel panelConsulta = new JPanel(new GridBagLayout());
    private JPanel panelCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoes = new JPanel(new GridBagLayout());
    
    //Caixas de texto
    private PTextField txtMatricula = new PTextField();
    private PTextField txtNome = new PTextField();
    private PTextField txtTelefone= new PTextField();
    private PTextField txtCargo = new PTextField();
    
    //Botões
    private JButton botCadastrar = new JButton();
    private JButton botExcluir = new JButton();
    private JButton botAlterar = new JButton();
    
    //Tabela
    DefaultTableModel tabela = new DefaultTableModel();
    private JTable tblFuncionario = new JTable(tabela);
    
    
    public Funcionario(){
        this.iniciaComponentes();
    }
    
    public static void main(String[] args){
        Funcionario fun = new Funcionario();
    }
    
    public void iniciaComponentes(){
        
        //Instância da classe monta interfaces, passei o nome do formulário e o caminho onde a imagem dele está
        MontaInterfaces telaOS = new MontaInterfaces("Gerenciamento de Funcionario", "/imagens/funcionario.png");
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
        telaOS.addLabelTitulo("Funcionario", panelCadastro);
        telaOS.addQuatroComponentes("Matricula", txtMatricula, "Nome", txtNome, "Telefone", txtTelefone, "Cargo", txtCargo, panelCadastro);
        telaOS.addTabela(tblFuncionario, panelConsulta);
        ConexaoBanco teste=new ConexaoBanco();
        teste.preencheTabela(tabela, "select *from Funcionario");

        
    }
    
}
