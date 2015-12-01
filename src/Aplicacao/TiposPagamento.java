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
import Core.Sessao;
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
 * @author Renan
 */
public class TiposPagamento implements PadraoFormulario, ActionListener {
    MetodosAuxiliares auxiliar = new MetodosAuxiliares();
    MontaInterfaces telaTiposPagamento = new MontaInterfaces("Gerenciamento de Tipos de Pagamento", "/imagens/icone-tipos-pagamento.png");
    ConexaoBanco conexao = new ConexaoBanco();
    
    //Panels
    private JPanel panelConsulta = new JPanel(new GridBagLayout());
    private JPanel panelCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoesCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoesAlteracao = new JPanel(new GridBagLayout());

    //caixas de texto
    private PTextField txtCodigo = new PTextField();
    private PTextField txtDescricao = new PTextField();
    
    //Botões
    private JButton botCadastrar = new JButton();
    private JButton botLimpar = new JButton();
    private JButton botInserir = new JButton();
    private JButton botExcluir = new JButton();
    private JButton botAlterarRegistro = new JButton();
    
    //Tabela
    DefaultTableModel tabela = new DefaultTableModel();
    private JTable tblTiposPagamento = new JTable(tabela){public boolean isCellEditable(int row,int column){return false;}};
    
    //Atributos da classe relacionados ao banco
    private int intCodigo;
    private String strDescricao;
    
    public TiposPagamento() {
        this.iniciaComponentes();
        this.atribuiIcones();
        
        telaTiposPagamento.setTamanho(600, 650);
        telaTiposPagamento.setVisible(true);
        
        this.preencheCombos();
        this.adicionaEventos();
        
        this.preencheTabela();
        this.setaNomes();
    }
    public static void main (String[]args){
        TiposPagamento tp = new TiposPagamento();
    }
    
    public void iniciaComponentes(){
        
        telaTiposPagamento.addAbas(panelCadastro, "Cadastro");
        telaTiposPagamento.addAbas(panelConsulta, "Consulta");
        telaTiposPagamento.addPanelBotoes(panelCadastro, panelBotoesCadastro);
        telaTiposPagamento.addPanelBotoes(panelCadastro, panelBotoesAlteracao);
       
        telaTiposPagamento.addBotoes("Cadastrar", botCadastrar, panelBotoesCadastro);
        telaTiposPagamento.addBotoes("Limpar", botLimpar, panelBotoesCadastro);
        telaTiposPagamento.addBotoes("Modo Inserir", botInserir, panelBotoesAlteracao);
        telaTiposPagamento.addBotoes("Excluir Registro", botExcluir, panelBotoesAlteracao);
        telaTiposPagamento.addBotoes("Alterar Registro", botAlterarRegistro, panelBotoesAlteracao); 
        
        telaTiposPagamento.addLabelTitulo("Tipo de Pagamento", panelCadastro);
        
        telaTiposPagamento.addUmComponente("Descrição", txtDescricao, panelCadastro);
        telaTiposPagamento.addTabela(tblTiposPagamento, panelConsulta);
        
        panelBotoesAlteracao.setVisible(false);
        panelBotoesCadastro.setVisible(true);
        
        Sessao sessao = Sessao.getInstance();
        if(sessao.isAdm() == false){
            botAlterarRegistro.setEnabled(false);
            botExcluir.setEnabled(false);
        }
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
        
        tblTiposPagamento.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {                              
                if (me.getClickCount() == 2) {                    
                    try {
                        
                        JTable table =(JTable) me.getSource();
                        Point p = me.getPoint();  
                        int row = table.rowAtPoint(p);
                        intCodigo = Integer.parseInt(tblTiposPagamento.getValueAt(row, 0).toString());
                        
                        ResultSet rs;
                        rs = conexao.executaProcedureSelect("CONSULTA_TIPOSPAGAMENTO(" + intCodigo + ")");
                        rs.next();                    
                        txtDescricao.setText(rs.getString(2));                
                        
                        mostraBotoesAlteracao();
                        telaTiposPagamento.getTabbedPane().setSelectedIndex(0);
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
            conexao.preencheTabela(tabela, "CONSULTA_TIPOSPAGAMENTO(0)");            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage()+ "\n" + e.getMessage());
        }
    }

    @Override
    public void setaNomes() {
        txtDescricao.setName("Descrição");
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
        if(auxiliar.validaCampos(telaTiposPagamento.getListaComponentes())){ 
            this.strDescricao = txtDescricao.getText();
            return true;            
        }else{            
           return false;
        }
    }

    @Override
    public boolean alterar() {
        if(auxiliar.validaCampos(telaTiposPagamento.getListaComponentes())){ 
            this.strDescricao = txtDescricao.getText();
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
                    conexao.executaProcedure("INSERT_TIPOSPAGAMENTO ('" + this.strDescricao + "' )");
                    JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso");
                    auxiliar.limpaCampos(telaTiposPagamento.getListaComponentes());
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
                    conexao.executaProcedure("DELETE_TIPOSPAGAMENTO(" + this.intCodigo + ")");
                    JOptionPane.showMessageDialog(null, "Dados deletados com sucesso");
                    auxiliar.limpaCampos(telaTiposPagamento.getListaComponentes());
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
            auxiliar.limpaCampos(telaTiposPagamento.getListaComponentes());
            this.mostraBotoesCadastro();
        }
        if (botao.getSource() == botLimpar) {
            auxiliar.limpaCampos(telaTiposPagamento.getListaComponentes());
        }
        if (botao.getSource() == botAlterarRegistro) {            
            ok = alterar();
            if(ok){	
                try {
                    conexao.executaProcedure("UPDATE_TIPOSPAGAMENTO (" + this.intCodigo + ",'" + this.strDescricao + "')");
                    JOptionPane.showMessageDialog(null, "Dados Alterados com sucesso");
                    auxiliar.limpaCampos(telaTiposPagamento.getListaComponentes());
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
