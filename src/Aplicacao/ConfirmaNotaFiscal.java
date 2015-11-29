/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplicacao;

import Core.ConexaoBanco;
import Core.MetodosAuxiliares;
import Core.MontaInterfaces;
import Core.PComboBox;
import Core.PTextField;
import Core.PadraoFormulario;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
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
    private JPanel panelBoleto = new JPanel(new GridBagLayout());
    private JPanel panelCartao = new JPanel(new GridBagLayout());

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
    private PTextField txtQuantidadeServico = new PTextField();
    private PTextField txtServico = new PTextField();
    private PTextField txtValorTotal = new PTextField();
    private PTextField txtCodigoServico = new PTextField();
    private PTextField txtOutrasInformacoes = new PTextField();
    private JRadioButton rdbVista = new JRadioButton();
    private JRadioButton rdbBoleto = new JRadioButton();
    private JRadioButton rdbCartao = new JRadioButton();
    private PComboBox cboDias = new PComboBox();
    private PComboBox cboBandeira = new PComboBox();
    private PTextField txtNumeroCartao = new PTextField();
    private PTextField txtSSN = new PTextField();
    private PTextField txtNomeTitular  = new PTextField();
    private PComboBox cboParcelas = new PComboBox();
    
    //Botões
    private JButton botFaturarNotaFiscal = new JButton();
    private JButton botCancelar = new JButton(); 
   
    //Atributos da classe relacionados ao banco
    private int intCodigo;
    private String strDescricao;
    
    public ConfirmaNotaFiscal(int codigoOS){
        this.iniciaComponentes();
        this.atribuiIcones();
        telaConfirmaNotaFiscal.setVisible(true);
        telaConfirmaNotaFiscal.setMaximizado(true);
    }

    @Override
    public void iniciaComponentes() {
        telaConfirmaNotaFiscal.addAbas(panelCadastro, "Cadastro");
        telaConfirmaNotaFiscal.addPanelBotoes(panelCadastro, panelBotoes);
       
        telaConfirmaNotaFiscal.addBotoes("Faturar", botFaturarNotaFiscal, panelBotoes);
        telaConfirmaNotaFiscal.addBotoes("Cancelar", botCancelar, panelBotoes);
        
        
        telaConfirmaNotaFiscal.addLabelTitulo("Prestador de Serviços", panelCadastro);
        telaConfirmaNotaFiscal.addCincoComponentes("CNPJ/CPF", txtCNPJCPFEmitente, "Razão Social", txtRazaoSocial, "Inscrição Municipal", txtIM, "Endereço", txtEnderecoEmitente, "Município", txtMunicipioEmitente, panelCadastro);
        telaConfirmaNotaFiscal.addLabelTitulo("Tomador de Serviços", panelCadastro);
        telaConfirmaNotaFiscal.addTresComponentes("CNPJ/CPF", txtCNPJCPFTomador, "Razão Social", txtRazaoSocialTomador, "Inscrição Municipal", txtIMTomador, panelCadastro);
        telaConfirmaNotaFiscal.addQuatroComponentes("Endereço", txtEnderecoTomador, "Município", txtMunicipioTomador, "UF", txtUfTomador, "E-mail", txtEmail, panelCadastro);
        telaConfirmaNotaFiscal.addLabelTitulo("Discriminação dos Serviços", panelCadastro);
        telaConfirmaNotaFiscal.addDoisComponentes("Quantidade",txtQuantidadeServico, "Serviço", txtServico, panelCadastro);
        telaConfirmaNotaFiscal.addLabelTitulo("Informações de Pagamento", panelCadastro);
        telaConfirmaNotaFiscal.addTresComponentes("Valor Total", txtValorTotal, "Código do serviço", txtCodigoServico, "Outra Informações", txtOutrasInformacoes, panelCadastro);
        telaConfirmaNotaFiscal.addTresComponentes("A vista", rdbVista, "Boleto", rdbBoleto, "Cartão", rdbCartao, panelCadastro);
        
        telaConfirmaNotaFiscal.addPanelComponentes(panelCadastro, panelBoleto);
        telaConfirmaNotaFiscal.addPanelComponentes(panelCadastro , panelCartao);
        
        telaConfirmaNotaFiscal.addCincoComponentes("Bandeira", cboBandeira, "Número do Cartão", txtNumeroCartao, "SSN", txtSSN, "Nome do titular", txtNomeTitular, "Parcelas", cboParcelas, panelCartao);
        telaConfirmaNotaFiscal.addUmComponente("Dias", cboDias,panelBoleto);
        
        panelBoleto.setVisible(false);
        panelCartao.setVisible(false);
        
        rdbBoleto.addActionListener(this);
        rdbCartao.addActionListener(this);
        rdbVista.addActionListener(this);
        
    }
    
    public static void main(String[] args){
        ConfirmaNotaFiscal cnf = new ConfirmaNotaFiscal(1);
    }

    @Override
    public void atribuiIcones() {
        Icon iconeCancelar = new ImageIcon(getClass().getResource("/imagens/cancelar.png"));
        Icon iconeFaturar = new ImageIcon(getClass().getResource("/imagens/faturar-nota-fiscal.png"));

        botCancelar.setIcon(iconeCancelar);
        botFaturarNotaFiscal.setIcon(iconeFaturar);
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
    public void actionPerformed(ActionEvent botao) {
        
        if(botao.getSource() == botCancelar){
            telaConfirmaNotaFiscal.dispose();
        }
        
        if(botao.getSource() == rdbVista) {
            
            rdbBoleto.setSelected(false);
            rdbCartao.setSelected(false);
            
            panelBoleto.setVisible(false);
            panelCartao.setVisible(false);
            
            txtSSN.setObrigatorio(false);
            txtNumeroCartao.setObrigatorio(false);
            cboBandeira.setObrigatorio(false);
            cboParcelas.setObrigatorio(false);
            cboDias.setObrigatorio(false);
            txtNomeTitular.setObrigatorio(false);
            
        }
        
        if(botao.getSource() == rdbBoleto) {
            rdbVista.setSelected(false);
            rdbCartao.setSelected(false);
            
            panelCartao.setVisible(false);
            panelBoleto.setVisible(true);
            
            txtSSN.setObrigatorio(false);
            txtNumeroCartao.setObrigatorio(false);
            cboBandeira.setObrigatorio(false);
            cboParcelas.setObrigatorio(false);
            cboDias.setObrigatorio(true);
            txtNomeTitular.setObrigatorio(false);
        }
        
        if(botao.getSource() == rdbCartao) {
            rdbBoleto.setSelected(false);
            rdbVista.setSelected(false);
            
            panelBoleto.setVisible(false);
            panelCartao.setVisible(true);
            
            txtSSN.setObrigatorio(true);
            txtNumeroCartao.setObrigatorio(true);
            cboBandeira.setObrigatorio(true);
            cboParcelas.setObrigatorio(true);
            cboDias.setObrigatorio(false);
            txtNomeTitular.setObrigatorio(true);
        }
        
        
    }
    
}
