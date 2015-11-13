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
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gustavo Rabelo
 */
public class CadastroUsuario {
    
    //Panels
    private JPanel panelCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoes = new JPanel(new GridBagLayout()); 
    
    //Caixas de texto
    private PTextField txtNome = new PTextField();
    private PTextField txtSenha= new PTextField();
    private PTextField txtConfirmaSenha = new PTextField();
    private JCheckBox chkAdministrador = new JCheckBox();
    
    //Botões
    private JButton botCadastrar = new JButton();
    
    //Tabela
    DefaultTableModel tabela = new DefaultTableModel();
    private JTable tblCadastroUsuario = new JTable(tabela);
    
    public CadastroUsuario(){
        this.iniciaComponentes();   
    }
    
    public static void main(String[] args){
        CadastroUsuario CadasUsu = new CadastroUsuario();
    }
    
    public void iniciaComponentes(){
        
        //Instância da classe monta interfaces, passei o nome do cadastro usuario e o caminho onde a imagem dele está
        MontaInterfaces telaCadastroUsuario = new MontaInterfaces("Cadastro de Usuários", "/imagens/adiciona-usuario.png");
        
        //Deixei a janela visível
        telaCadastroUsuario.setVisible(true);  
        
        telaCadastroUsuario.addAbas(panelCadastro, "Cadastro");
        
        //Panel para os botões com o método addPanelBotoes
        telaCadastroUsuario.addPanelBotoes(panelCadastro, panelBotoes);
        
        //Adicionei os botões dentro do panelBotoes
        telaCadastroUsuario.addBotoes("Cadastrar", botCadastrar, panelBotoes);
        
        //Criei objetos do tipo icone com o caminho do icone para coloca-los nos botões 
        Icon iconeCadastrar = new ImageIcon(getClass().getResource("/imagens/salvar.png"));
        
        //Seta os icones dos bortões
        botCadastrar.setIcon(iconeCadastrar);
        
        //Adiciona os componentes na tela
        telaCadastroUsuario.addLabelTitulo("Cadastrar Usuario", panelCadastro);
        telaCadastroUsuario.addUmComponente("Usuário", txtNome, panelCadastro);
        telaCadastroUsuario.addUmComponente("Senha", txtSenha, panelCadastro);
        telaCadastroUsuario.addUmComponente("Confima senha", txtConfirmaSenha, panelCadastro);
        telaCadastroUsuario.addUmComponente("Administrador", chkAdministrador, panelCadastro);
        telaCadastroUsuario.addTabela(tblCadastroUsuario, panelCadastro);
        ConexaoBanco teste=new ConexaoBanco();
        teste.preencheTabela(tabela, "select *from usuario");
    
    }
    
}
