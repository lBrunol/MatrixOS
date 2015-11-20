/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplicacao;

import Core.ComboItem;
import Core.ConexaoBanco;
import Core.MetodosAuxiliares;
import Core.MontaInterfaces;
import Core.PTextField;
import Core.PadraoFormulario;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gustavo Rabelo
 */
public class Usuario implements PadraoFormulario, ActionListener {
    
    MetodosAuxiliares auxiliar = new MetodosAuxiliares();
    MontaInterfaces telaUsuario = new MontaInterfaces("Gerenciamento de Usuários", "/imagens/adiciona-usuario.png");
    ConexaoBanco conexao = new ConexaoBanco();
    
    //Panels
    private JPanel panelConsulta = new JPanel(new GridBagLayout());
    private JPanel panelCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoesCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoesAlteracao = new JPanel(new GridBagLayout());  
    
    //Caixas de texto
    private PTextField txtNome = new PTextField();
    private PTextField txtSenha= new PTextField();
    private JCheckBox chkAdministrador = new JCheckBox();
    
    //Botões
    private JButton botCadastrar = new JButton();
    private JButton botLimpar = new JButton();
    private JButton botInserir = new JButton();
    private JButton botExcluir = new JButton();
    private JButton botAlterarRegistro = new JButton();
    
    //Tabela
    DefaultTableModel tabela = new DefaultTableModel();
    private JTable tblUsuario = new JTable(tabela){public boolean isCellEditable(int row,int column){return false;}};
    
    //Atributos da classe relacionados ao banco
    private int intCodigo;
    private String strSenha;
    private String strUsuario;
    private String strAdm;
    
    public Usuario(){
        this.iniciaComponentes();
        this.atribuiIcones();
        
        telaUsuario.setTamanho(600, 670);
        telaUsuario.setVisible(true);
        
        this.preencheCombos();
        this.adicionaEventos();
        
        this.preencheTabela();
        this.setaNomes();   
    }
    
    public static void main(String[] args){
        Usuario Usro = new Usuario();
        
    }
    
    public void iniciaComponentes(){

        telaUsuario.addAbas(panelCadastro, "Cadastro");
        telaUsuario.addAbas(panelConsulta, "Consulta");
        telaUsuario.addPanelBotoes(panelCadastro, panelBotoesCadastro);
        telaUsuario.addPanelBotoes(panelCadastro, panelBotoesAlteracao);
       
        telaUsuario.addBotoes("Cadastrar", botCadastrar, panelBotoesCadastro);
        telaUsuario.addBotoes("Limpar", botLimpar, panelBotoesCadastro);
        telaUsuario.addBotoes("Modo Inserir", botInserir, panelBotoesAlteracao);
        telaUsuario.addBotoes("Excluir Registro", botExcluir, panelBotoesAlteracao);
        telaUsuario.addBotoes("Alterar Registro", botAlterarRegistro, panelBotoesAlteracao); 
        
        telaUsuario.addLabelTitulo("Usuários", panelCadastro);
       
        telaUsuario.addUmComponente("Nome", txtNome, panelCadastro);
        telaUsuario.addDoisComponentes("Senha",txtSenha, "Administrador", chkAdministrador, panelCadastro);         
        telaUsuario.addTabela(tblUsuario, panelConsulta);
        
        panelBotoesAlteracao.setVisible(false);
        panelBotoesCadastro.setVisible(true);
       
    }

    @Override
    public void atribuiIcones() {        
        Icon iconeCadastrar = new ImageIcon(getClass().getResource("/imagens/salvar.png"));
        Icon iconeExcluir = new ImageIcon(getClass().getResource("/imagens/excluir.png"));
        Icon iconeAlterar = new ImageIcon(getClass().getResource("/imagens/alterar.png"));
        Icon iconeInserir = new ImageIcon(getClass().getResource("/imagens/adicionar.png"));
        Icon iconeLimpar = new ImageIcon(getClass().getResource("/imagens/limpar.png"));

        botAlterarRegistro.setIcon(iconeAlterar);
        botCadastrar.setIcon(iconeCadastrar);
        botExcluir.setIcon(iconeExcluir);
        botInserir.setIcon(iconeInserir);
        botLimpar.setIcon(iconeLimpar);
    }

    @Override
    public void preencheCombos() {
        
    }

    @Override
    public void adicionaEventos() {
        botCadastrar.addActionListener(this);
        botInserir.addActionListener(this);
        botExcluir.addActionListener(this);
        botLimpar.addActionListener(this);
        botAlterarRegistro.addActionListener(this);
        
        tblUsuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                              
                if (me.getClickCount() == 2) {                    
                    try {
                        
                        JTable table =(JTable) me.getSource();
                        Point p = me.getPoint();  
                        int row = table.rowAtPoint(p);
                        intCodigo = Integer.parseInt(tblUsuario.getValueAt(row, 0).toString());
                        
                        ResultSet rs;
                        rs = conexao.executar("SELECT * FROM usuarios WHERE usrCodigo =" + intCodigo);
                        rs.next();                    
                        txtNome.setText(rs.getString(2));
                        txtSenha.setText(rs.getString(3));
                        if("S".equals(rs.getString(4))){
                            chkAdministrador.setSelected(true);
                        }else{
                            chkAdministrador.setSelected(false);
                        }                        
                        mostraBotoesAlteracao();
                        telaUsuario.getTabbedPane().setSelectedIndex(0);
                        rs.close();
                    }             
                    catch (SQLException b) {
                        JOptionPane.showMessageDialog(null, b.getMessage() + ". Ocorreu um erro de SQL. Por favor, entre em contato com administrador do sistema.");
                    }
                    catch (Exception b) {
                        JOptionPane.showMessageDialog(null,"Erro desconhecido. Por favor entre em contato com administrador do sistema. \n" + b.getMessage());
                    }
                }
            }
        });
    }

    @Override
    public void preencheTabela() {
        try {
            conexao.preencheTabela(tabela, "select * FROM usuarios ORDER BY usrCodigo");            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage()+ "\n" + e.getMessage());
        }
    }

    @Override
    public void setaNomes() {
        txtNome.setName("Usuário");
        txtSenha.setName("Senha");
    }

    @Override
    public void mostraBotoesAlteracao() {
        panelBotoesAlteracao.setVisible(true);
        panelBotoesCadastro.setVisible(false);
    }

    @Override
    public void mostraBotoesCadastro() {
        panelBotoesAlteracao.setVisible(false);
        panelBotoesCadastro.setVisible(true);
    }

    @Override
    public boolean cadastrar() {
        if(auxiliar.validaCampos(telaUsuario.getListaComponentes())){ 
            this.strUsuario = txtNome.getText();
            this.strSenha = txtSenha.getText();
            if(chkAdministrador.isSelected()){
                this.strAdm = "S";
            }else{
                this.strAdm = "N";
            }
            return true;            
        }else{            
           return false;
        }
    }

    @Override
    public boolean alterar() {
        if(auxiliar.validaCampos(telaUsuario.getListaComponentes())){ 
            this.strUsuario = txtNome.getText();
            this.strSenha = txtSenha.getText();
            if(chkAdministrador.isSelected()){
                this.strAdm = "S";
            }else{
                this.strAdm = "N";
            }
            return true;            
        }else{            
           return false;
        }
    }

    @Override
    public boolean deletar() {
        if(JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o registro?", "Confirmação", JOptionPane.YES_NO_OPTION) == 0){
            return true;                       
        }else{
            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent botao) {
        boolean ok ;
        if (botao.getSource() == botCadastrar) {            
            ok = cadastrar();
            if(ok){	
                try {
                    ResultSet rs;
                    conexao.executaProcedure("INSERT_USUARIOS ('" + this.strUsuario + "', '" + this.strSenha + "', '" + this.strAdm + "' )");
                    JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso");
                    auxiliar.limpaCampos(telaUsuario.getListaComponentes());
                    this.preencheTabela();
                }catch (SQLException b) {
                    JOptionPane.showMessageDialog(null, b.getMessage() + ". Ocorreu um erro de SQL. Por favor, entre em contato com administrador do sistema.");
                }
                catch (Exception b) {
                    JOptionPane.showMessageDialog(null,"Erro desconhecido. Por favor entre em contato com administrador do sistema. \n" + b.getMessage());
                }                                    
            }
        }
        if (botao.getSource() == botExcluir) {
            ok = deletar();
            if(ok){	
                try {
                    conexao.executaProcedure("DELETE_USUARIOS (" + this.intCodigo + ")");
                    JOptionPane.showMessageDialog(null, "Dados deletados com sucesso");
                    auxiliar.limpaCampos(telaUsuario.getListaComponentes());
                    this.mostraBotoesCadastro();
                    this.preencheTabela();
                }catch (SQLException b) {
                    JOptionPane.showMessageDialog(null, b.getMessage() + ". Ocorreu um erro de SQL. Por favor, entre em contato com administrador do sistema.");
                }
                catch (Exception b) {
                    JOptionPane.showMessageDialog(null,"Erro desconhecido. Por favor entre em contato com administrador do sistema. \n" + b.getMessage());
                }                                    
            }           
        }
        if (botao.getSource() == botInserir) {
            auxiliar.limpaCampos(telaUsuario.getListaComponentes());
            this.mostraBotoesCadastro();
        }
        if (botao.getSource() == botLimpar) {
            auxiliar.limpaCampos(telaUsuario.getListaComponentes());
        }
        if (botao.getSource() == botAlterarRegistro) {            
            ok = alterar();
            if(ok){	
                try {
                    conexao.executaProcedure("UPDATE_USUARIOS (" + this.intCodigo + ",'" + this.strUsuario + "', '" + this.strSenha + "', '" + this.strAdm + "' )");
                    JOptionPane.showMessageDialog(null, "Dados Alterados com sucesso");
                    auxiliar.limpaCampos(telaUsuario.getListaComponentes());
                    this.mostraBotoesCadastro();
                    this.preencheTabela();
                }catch (SQLException b) {
                    JOptionPane.showMessageDialog(null, b.getMessage() + ". Ocorreu um erro de SQL. Por favor, entre em contato com administrador do sistema.");
                }
                catch (Exception b) {
                    JOptionPane.showMessageDialog(null,"Erro desconhecido. Por favor entre em contato com administrador do sistema. \n" + b.getMessage());
                }                                    
            }
        }
    }
}
