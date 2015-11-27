/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplicacao;

import Core.ConexaoBanco;
import Core.MetodosAuxiliares;
import Core.MontaInterfaces;
import Core.PFormattedTextField;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CASA
 */
public class CadastroDoEmitente implements PadraoFormulario, ActionListener {
    MetodosAuxiliares auxiliar = new MetodosAuxiliares();
    MontaInterfaces telaEmitente = new MontaInterfaces("Gerenciamento de Emitentes", "/imagens/dados-emitente.png");
    ConexaoBanco conexao = new ConexaoBanco();
    
    //Panels
    private JPanel panelConsulta = new JPanel(new GridBagLayout());
    private JPanel panelCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoesCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoesAlteracao = new JPanel(new GridBagLayout());
    
    //Caixas de texto
    private PTextField txtRazaoSocial = new PTextField();
    private PTextField txtNomeFantasia = new PTextField();
    private PFormattedTextField txtCNPJ = new PFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_CNPJ));
    private PTextField txtEndereco= new PTextField();
    private PTextField txtBairro= new PTextField();
    private PFormattedTextField txtCEP = new PFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_CEP));
    private PFormattedTextField txtTelefone = new PFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_TELEFONE));
    private PTextField txtMunicipio = new PTextField();
    private PTextField txtUF = new PTextField();
    
    //Botões
    private JButton botCadastrar = new JButton();
    private JButton botLimpar = new JButton();
    private JButton botInserir = new JButton();
    private JButton botExcluir = new JButton();
    private JButton botAlterarRegistro = new JButton();
    
    //Tabela
    DefaultTableModel tabela = new DefaultTableModel();
    private JTable tblEmitente = new JTable(tabela){public boolean isCellEditable(int row,int column){return false;}};
    
    //Caixas de texto
    private int intCodigo;
    private String strRazaoSocial;
    private String strNomeFantasia;
    private String strCNPJ;
    private String strEndereco;
    private String strBairro;
    private String strCEP;
    private String strTelefone;
    private String strMunicipio;
    private String strUF;
    
    public CadastroDoEmitente(){
        this.iniciaComponentes();
        this.atribuiIcones();
        
        telaEmitente.setTamanho(600, 650);
        telaEmitente.setVisible(true);
        
        this.preencheCombos();
        this.adicionaEventos();
        
        this.preencheTabela();
        this.setaNomes();
    }
    
    public static void main(String[] args){
        CadastroDoEmitente emitente = new CadastroDoEmitente();
    }
    
    public void iniciaComponentes(){
   
        telaEmitente.addAbas(panelCadastro, "Cadastro");
        telaEmitente.addAbas(panelConsulta, "Consulta");
        telaEmitente.addPanelBotoes(panelCadastro, panelBotoesCadastro);
        telaEmitente.addPanelBotoes(panelCadastro, panelBotoesAlteracao);
        
        telaEmitente.addBotoes("Cadastrar", botCadastrar, panelBotoesCadastro);
        telaEmitente.addBotoes("Limpar", botLimpar, panelBotoesCadastro);
        telaEmitente.addBotoes("Modo Inserir", botInserir, panelBotoesAlteracao);
        telaEmitente.addBotoes("Excluir Registro", botExcluir, panelBotoesAlteracao);
        telaEmitente.addBotoes("Alterar Registro", botAlterarRegistro, panelBotoesAlteracao);        
        
        telaEmitente.addLabelTitulo("Emitente", panelCadastro);
        
        telaEmitente.addDoisComponentes("Nome Fantasia", txtNomeFantasia, "Razão Social", txtRazaoSocial, panelCadastro);
        telaEmitente.addQuatroComponentes("CNPJ", txtCNPJ, "Endereço", txtEndereco, "Bairro", txtBairro, "CEP", txtCEP, panelCadastro);
        telaEmitente.addDoisComponentes("Município", txtMunicipio, "Telefone", txtTelefone, panelCadastro);
        telaEmitente.addTabela(tblEmitente, panelConsulta);
        
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
        
        tblEmitente.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {                              
                if (me.getClickCount() == 2) {                    
                    try {
                        
                        JTable table =(JTable) me.getSource();
                        Point p = me.getPoint();  
                        int row = table.rowAtPoint(p);
                        intCodigo = Integer.parseInt(tblEmitente.getValueAt(row, 0).toString());
                        
                        ResultSet rs;
                        rs = conexao.executaProcedureSelect("CONSULTA_DADOSEMITENTE(" + intCodigo + ")");
                        rs.next();                    
                        
                        txtRazaoSocial.setText(rs.getString(2));
                        txtNomeFantasia.setText(rs.getString(3));
                        txtCNPJ.setText(rs.getString(4));
                        txtEndereco.setText(rs.getString(5));
                        txtBairro.setText(rs.getString(6));
                        txtCEP.setText(rs.getString(7));
                        txtTelefone.setText(rs.getString(8));
                        txtMunicipio.setText(rs.getString(9));
                        txtUF.setText(rs.getString(10));
                        
                        mostraBotoesAlteracao();
                        telaEmitente.getTabbedPane().setSelectedIndex(0);
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
            conexao.preencheTabela(tabela, "CONSULTA_DADOSEMITENTE(0)");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage()+ "\n" + e.getMessage());
        }
    }

    @Override
    public void setaNomes() {
        txtRazaoSocial.setName("Razão Social");
        txtNomeFantasia.setName("Nome Fantasia");
        txtCNPJ.setName("CNPJ");
        txtEndereco.setName("Endereço");
        txtBairro.setName("Bairro");
        txtCEP.setName("CEP");
        txtTelefone.setName("Telefone");
        txtMunicipio.setName("Município");
        txtUF.setName("UF");
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
        if(auxiliar.validaCampos(telaEmitente.getListaComponentes())){
            this.strNomeFantasia = txtNomeFantasia.getText();
            this.strRazaoSocial = txtRazaoSocial.getText();
            this.strCNPJ = auxiliar.removeCaracteresString(txtCNPJ.getText());
            this.strEndereco = txtEndereco.getText();
            this.strBairro = txtBairro.getText();
            this.strCEP = auxiliar.removeCaracteresString(txtCEP.getText());
            this.strTelefone = auxiliar.removeCaracteresString(txtTelefone.getText());
            this.strMunicipio = txtMunicipio.getText();
            this.strUF = "SP";
            return true;            
        }else{            
           return false;
        }
    }

    @Override
    public boolean alterar() {
        if(auxiliar.validaCampos(telaEmitente.getListaComponentes())){
            this.strNomeFantasia = txtNomeFantasia.getText();
            this.strRazaoSocial = txtRazaoSocial.getText();
            this.strCNPJ = auxiliar.removeCaracteresString(txtCNPJ.getText());
            this.strEndereco = txtEndereco.getText();
            this.strBairro = txtBairro.getText();
            this.strCEP = auxiliar.removeCaracteresString(txtCEP.getText());
            this.strTelefone = auxiliar.removeCaracteresString(txtTelefone.getText());
            this.strMunicipio = txtMunicipio.getText();
            this.strUF = "SP";
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
                    conexao.executaProcedure("INSERT_DADOSEMITENTE ('" + this.strRazaoSocial + "', '" + this.strNomeFantasia + "' , '" + this.strCNPJ + "', '" + this.strEndereco + "' , '" + this.strBairro + "' , '" + this.strCEP + "', '" + this.strTelefone + "' , '" + this.strMunicipio + "', '" + this.strUF + "' )");
                    JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso");
                    auxiliar.limpaCampos(telaEmitente.getListaComponentes());
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
                    conexao.executaProcedure("DELETE_DADOSEMITENTE(" + this.intCodigo + ")");
                    JOptionPane.showMessageDialog(null, "Dados deletados com sucesso");
                    auxiliar.limpaCampos(telaEmitente.getListaComponentes());
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
            auxiliar.limpaCampos(telaEmitente.getListaComponentes());
            this.mostraBotoesCadastro();
        }
        if (botao.getSource() == botLimpar) {
            auxiliar.limpaCampos(telaEmitente.getListaComponentes());          
        }
        if (botao.getSource() == botAlterarRegistro) {            
            ok = alterar();
            if(ok){	
                try {
                    conexao.executaProcedure("UPDATE_DADOSEMITENTE (" + this.intCodigo + ",'" + this.strRazaoSocial + "', '" + this.strNomeFantasia + "' , '" + this.strCNPJ + "', '" + this.strEndereco + "' , '" + this.strBairro + "' , '" + this.strCEP + "', '" + this.strTelefone + "' , '" + this.strMunicipio + "', '" + this.strUF + "' )");
                    JOptionPane.showMessageDialog(null, "Dados Alterados com sucesso");
                    auxiliar.limpaCampos(telaEmitente.getListaComponentes());
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
