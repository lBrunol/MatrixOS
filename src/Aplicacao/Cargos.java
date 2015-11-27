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
 * @author Bruno
 */
public class Cargos implements PadraoFormulario, ActionListener {
    MetodosAuxiliares auxiliar = new MetodosAuxiliares();
    MontaInterfaces telaCargos = new MontaInterfaces("Gerenciamento de Cargos", "/imagens/icone-cargos-mid.png");
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
    private JTable tblCargos = new JTable(tabela){public boolean isCellEditable(int row,int column){return false;}};
    
    //Atributos da classe relacionados ao banco
    private int intCodigo;
    private String strDescricao;
    
    public Cargos() {
        this.iniciaComponentes();
        this.atribuiIcones();
        
        telaCargos.setTamanho(600, 650);
        telaCargos.setVisible(true);
        
        this.preencheCombos();
        this.adicionaEventos();
        
        this.preencheTabela();
        this.setaNomes();
    }
    public static void main (String[]args){
        Cargos cg = new Cargos();
    }
    
    public void iniciaComponentes(){
        
        telaCargos.addAbas(panelCadastro, "Cadastro");
        telaCargos.addAbas(panelConsulta, "Consulta");
        telaCargos.addPanelBotoes(panelCadastro, panelBotoesCadastro);
        telaCargos.addPanelBotoes(panelCadastro, panelBotoesAlteracao);
       
        telaCargos.addBotoes("Cadastrar", botCadastrar, panelBotoesCadastro);
        telaCargos.addBotoes("Limpar", botLimpar, panelBotoesCadastro);
        telaCargos.addBotoes("Modo Inserir", botInserir, panelBotoesAlteracao);
        telaCargos.addBotoes("Excluir Registro", botExcluir, panelBotoesAlteracao);
        telaCargos.addBotoes("Alterar Registro", botAlterarRegistro, panelBotoesAlteracao); 
        
        telaCargos.addLabelTitulo("Cargos", panelCadastro);
        
        telaCargos.addUmComponente("Descrição", txtDescricao, panelCadastro);
        telaCargos.addTabela(tblCargos, panelConsulta);       
     
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
        
        tblCargos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {                              
                if (me.getClickCount() == 2) {                    
                    try {
                        
                        JTable table =(JTable) me.getSource();
                        Point p = me.getPoint();  
                        int row = table.rowAtPoint(p);
                        intCodigo = Integer.parseInt(tblCargos.getValueAt(row, 0).toString());
                        
                        ResultSet rs;
                        rs = conexao.executaProcedureSelect("CONSULTA_CARGOS(" + intCodigo + ")");
                        rs.next();                    
                        txtDescricao.setText(rs.getString(2));                
                        
                        mostraBotoesAlteracao();
                        telaCargos.getTabbedPane().setSelectedIndex(0);
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
            String teste;
            teste = "CARCODIGO";
            conexao.preencheTabela(tabela, "CONSULTA_CARGOS(0)");            
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
        if(auxiliar.validaCampos(telaCargos.getListaComponentes())){ 
            this.strDescricao = txtDescricao.getText();
            return true;            
        }else{            
           return false;
        }
    }

    @Override
    public boolean alterar() {
        if(auxiliar.validaCampos(telaCargos.getListaComponentes())){ 
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
                    conexao.executaProcedure("INSERT_CARGOS ('" + this.strDescricao + "' )");
                    JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso");
                    auxiliar.limpaCampos(telaCargos.getListaComponentes());
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
                    conexao.executaProcedure("DELETE_CARGOS(" + this.intCodigo + ")");
                    JOptionPane.showMessageDialog(null, "Dados deletados com sucesso");
                    auxiliar.limpaCampos(telaCargos.getListaComponentes());
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
            auxiliar.limpaCampos(telaCargos.getListaComponentes());
            this.mostraBotoesCadastro();
        }
        if (botao.getSource() == botLimpar) {
            auxiliar.limpaCampos(telaCargos.getListaComponentes());
            //try {
            //    ResultSet rs;
            //rs = conexao.executaProcedureSelect();
            //rs.next();
            //txtDescricao.setText(rs.getString(2));
            //    
            //} catch (Exception e) {
            //}            
        }
        if (botao.getSource() == botAlterarRegistro) {            
            ok = alterar();
            if(ok){	
                try {
                    conexao.executaProcedure("UPDATE_CARGOS (" + this.intCodigo + ",'" + this.strDescricao + "')");
                    JOptionPane.showMessageDialog(null, "Dados Alterados com sucesso");
                    auxiliar.limpaCampos(telaCargos.getListaComponentes());
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
