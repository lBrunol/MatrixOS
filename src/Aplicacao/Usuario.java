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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gustavo Rabelo
 */
public class Usuario {
    
    //Panels
    private JPanel panelConsulta = new JPanel(new GridBagLayout());
    private JPanel panelAlteracao = new JPanel(new GridBagLayout());
    private JPanel panelCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoes = new JPanel(new GridBagLayout());  
    
    //Caixas de texto
    private PTextField txtNome = new PTextField();
    private PTextField txtSenha= new PTextField();
    private PTextField chkAdministrador = new PTextField();
    
    //Botões
    private JButton botExcluir = new JButton();
    private JButton botAlterar = new JButton();
    
    //Tabela
    DefaultTableModel tabela = new DefaultTableModel();
    private JTable tblUsuario = new JTable(tabela);
    
        public Usuario(){
        this.iniciaComponentes();   
    }
    
    public static void main(String[] args){
        Usuario Usro = new Usuario();
        
    }
    
    public void iniciaComponentes(){
        
        //Instância da classe monta interfaces, passei o nome do usuario e o caminho onde a imagem dele está
        MontaInterfaces telaUsuario = new MontaInterfaces("Usuários", "/imagens/adiciona.png");
        
        //Deixei a janela visível
        telaUsuario.setVisible(true); 
        
        //Adicionei as abas com o método addAbas e o panel para os botões com o método addPanelBotoes
        telaUsuario.addAbas(panelAlteracao, "Alteração");
        telaUsuario.addAbas(panelConsulta, "Consulta");
        
        //Adicionei os botões dentro do panelBotoes
        telaUsuario.addBotoes("Alterar Registro", botAlterar, panelBotoes);
        telaUsuario.addBotoes("Limpar Registro", botExcluir, panelBotoes);
        
        //Criei objetos do tipo icone com o caminho do icone para coloca-los nos botões 
        Icon iconeAlterar = new ImageIcon(getClass().getResource("/imagens/adicionar.png"));
        Icon iconeExcluir = new ImageIcon(getClass().getResource("/imagens/excluir.png"));
        
        //Seta os icones dos bortões
        botAlterar.setIcon(iconeAlterar);
        botExcluir.setIcon(iconeExcluir);
        
        //Adiciona os componentes na tela
        telaUsuario.addLabelTitulo("Usuários", panelCadastro);
       
        telaUsuario.addUmComponente("Nome", txtNome);
        telaUsuario.addDoisComponente("Senha",txtSenha, "Administrador", chkAdministrador, panelCadastro);
     
         
        telaUsuario.addTabela(tblUsuario, panelConsulta);
        ConexaoBanco teste=new ConexaoBanco();
        teste.preencheTabela(tabela, "select * from usuario");
        
    }
}
