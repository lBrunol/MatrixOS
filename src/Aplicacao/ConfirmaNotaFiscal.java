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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CASA
 */
public class ConfirmaNotaFiscal implements PadraoFormulario, ActionListener {
    
    MetodosAuxiliares auxiliar = new MetodosAuxiliares();
    MontaInterfaces telaConfirmaNotaFiscal = new MontaInterfaces("Confirmar dados para Nota Fiscal", "/imagens/nota-fiscal-eletronica.png");
    ConexaoBanco conexao = new ConexaoBanco();
    
    //Panels
    private JPanel panelCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoes = new JPanel(new GridBagLayout());

    //caixas de texto
    private PTextField txtCNPJCPFEmitente = new PTextField();
    private PTextField txtIM = new PTextField();
    private PTextField txtRazaoSocial = new PTextField();
    private PTextField txtEnderecoEmitente = new PTextField();
    private PTextField txtMunicipioEmitente = new PTextField();
    private PTextField txtUfEmitente = new PTextField();
    private PTextField txtCNPJCPFTomador = new PTextField();
    private PTextField txtRazaoSocialTomador = new PTextField();
    private PTextField txtIMTomador = new PTextField();
    private PTextField txtEnderecoTomador = new PTextField();
    private PTextField txtMunicipioTomador = new PTextField();
    private PTextField txtUfTomador = new PTextField();
    private PTextField txtEmail = new PTextField();
    private PTextField txtValorTotal = new PTextField();
    private PTextField txtCodigoServico = new PTextField();
    private PTextField txtOutrasInformacoes = new PTextField();
    private JRadioButton rdbVista = new JRadioButton();
    private JRadioButton rdbBoleto = new JRadioButton();
    private JRadioButton rdbCartao = new JRadioButton();
    private PTextField txtQuantidadeDias = new PTextField();
    private PTextField txtBandeira = new PTextField();
    private PTextField txtNumeroCartao = new PTextField();
    private PTextField txtSSN = new PTextField();
    private PTextField txtNomeTitular  = new PTextField();
    private PTextField txtParcelas = new PTextField();
    
    //Botões
    private JButton botFaturarNotaFiscal = new JButton();
    private JButton botCancelar = new JButton(); 
   
    //Atributos da classe relacionados ao banco
    private int intCodigo;
    private String strDescricao;
    
    public ConfirmaNotaFiscal(int codigoOS){
    
    }

    @Override
    public void iniciaComponentes() {
        telaConfirmaNotaFiscal.addAbas(panelCadastro, "Cadastro");
        telaConfirmaNotaFiscal.addPanelBotoes(panelCadastro, panelBotoes);
       
        telaConfirmaNotaFiscal.addBotoes("Cadastrar", botFaturarNotaFiscal, panelBotoes);
        telaConfirmaNotaFiscal.addBotoes("Limpar", botCancelar, panelBotoes);
        
        telaConfirmaNotaFiscal.addLabelTitulo("Confirmar Nota Fiscal", panelCadastro);
    }

    @Override
    public void atribuiIcones() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void preencheCombos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void adicionaEventos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
