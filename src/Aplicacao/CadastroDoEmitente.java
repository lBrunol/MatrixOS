/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplicacao;

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
    private PTextField txtCNPJ = new PTextField();
    private PTextField txtEndereco= new PTextField();
    private PTextField txtBairro= new PTextField();
    private PTextField txtCEP = new PTextField();
    private PTextField txtTelefone = new PTextField();
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
    private String strNomeFantasia;
    private String strRazaoSocial;
    private int intInscricaoMunicipal;
    private int intInscricaoEstadual;
    private int intCEP;
    private String strEndereco;
    private int intTelefone;
    private String strEmail;
    
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
        telaEmitente.addDoisComponentes("Munícipio", txtMunicipio, "Telefone", txtTelefone, panelCadastro);
        
        conexao.preencheTabela(tabela, "select *from dadosEmitente");
        
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
                        rs = conexao.executar("SELECT * FROM dadosEmitente WHERE demCodigo =" + intCodigo);
                        rs.next();                    
                        txtRazaoSocial.setText(rs.getString(2));
                        
                        txtRazaoSocial.setText(rs.getString(2));
                        txtNomeFantasia.setText(rs.getString(3));
                        txtCNPJ.setText(auxiliar.removeCaracteresString(rs.getString(4)));
                        txtEndereco.setText(rs.getString(5));
                        txtBairro.setText(rs.getString(6));
                        txtCEP.setText(auxiliar.removeCaracteresString(rs.getString(7)));
                        txtTelefone.setText(auxiliar.removeCaracteresString(rs.getString(8)));
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setaNomes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mostraBotoesAlteracao() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mostraBotoesCadastro() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean cadastrar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean alterar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
