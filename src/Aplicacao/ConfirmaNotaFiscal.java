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
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
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
    private int intCodigoOS;
    private int intCodigoEmitente;
    private int intCodigoNota;
    private int intCodigoPagamento;
    private String strDescricao;
    private String strCodigoVerificacao;
    private String strOutrasInformacoes;
    private double dblValorTotal;
    
    public ConfirmaNotaFiscal(int codigoOS){
        this.intCodigoOS = codigoOS;
        this.iniciaComponentes();
        this.atribuiIcones();
        this.preenchaCampos(this.intCodigoOS);
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
        telaConfirmaNotaFiscal.addCincoComponentes("CNPJ", txtCNPJCPFEmitente, "Razão Social", txtRazaoSocial, "Inscrição Municipal", txtIM, "Endereço", txtEnderecoEmitente, "Município", txtMunicipioEmitente, panelCadastro);
        telaConfirmaNotaFiscal.addLabelTitulo("Tomador de Serviços", panelCadastro);
        telaConfirmaNotaFiscal.addTresComponentes("CNPJ/CPF", txtCNPJCPFTomador, "Razão Social/Nome", txtRazaoSocialTomador, "Inscrição Municipal", txtIMTomador, panelCadastro);
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
        
        txtCNPJCPFEmitente.setEnabled(false);
        txtCNPJCPFTomador.setEnabled(false);
        txtCodigoServico.setEnabled(false);
        txtEmail.setEnabled(false);
        txtEnderecoEmitente.setEnabled(false);
        txtEnderecoTomador.setEnabled(false);
        txtIM.setEnabled(false);
        txtIMTomador.setEnabled(false);
        txtMunicipioEmitente.setEnabled(false);
        txtMunicipioTomador.setEnabled(false);
        txtQuantidadeServico.setEnabled(false);
        txtRazaoSocial.setEnabled(false);
        txtRazaoSocialTomador.setEnabled(false);
        txtRazaoSocialTomador.setEnabled(false);
        
        rdbBoleto.addActionListener(this);
        rdbCartao.addActionListener(this);
        rdbVista.addActionListener(this);
        
    }
    
    public static void main(String[] args){
        ConfirmaNotaFiscal cnf = new ConfirmaNotaFiscal(1);
    }
    
    public void preenchaCampos(int codigo){        
        
        int codigoCliente;
        
        try {
            ResultSet rs;
            rs = conexao.executar("SELECT demCodigo FROM dadosEmitente WHERE ROWNUM = 1");
            rs.next();
            
            this.intCodigoEmitente = rs.getInt(1);
            
            rs.close();
            
            rs = conexao.executar("SELECT demCodigo, demCNPJ, demRazaoSocial, demEndereco, demMunicipio FROM dadosEmitente WHERE demCodigo = " + this.intCodigoEmitente);
            rs.next();
            
            txtCNPJCPFEmitente.setText(rs.getString(2));
            txtRazaoSocial.setText(rs.getString(3));
            txtIM.setText("-");
            txtEnderecoEmitente.setText(rs.getString(4));
            txtMunicipioEmitente.setText(rs.getString(5));
            
            rs.close();
            
            rs = conexao.executar("SELECT NVL(ordValorTotal,0) - NVL(ordValorDesconto,0), ordemServico.cliCodigo, servicos.svcNome, servicos.svcCodigoServico, servicosOS.seoQuantidade FROM ((ordemServico INNER JOIN servicosOS ON ordemServico.ordCodigo = servicosOS.ordCodigo) INNER JOIN servicos ON servicos.svcCodigo = servicosOS.svcCodigo) WHERE ordemServico.ordCodigo = " + codigo);
            rs.next();
            
            txtValorTotal.setText(rs.getString(1));
            codigoCliente = rs.getInt(2);
            txtCodigoServico.setText(rs.getString(4) + " - " + rs.getString(3));
            txtServico.setText(rs.getString(3));
            txtQuantidadeServico.setText(rs.getString(5));
            
            rs.close();
            
            rs = conexao.executar("SELECT cliTipo FROM cliente WHERE cliCodigo = " + codigoCliente);
            rs.next();
            
            if("J".equals(rs.getString(1).toUpperCase())){
                
                rs.close();
                
                rs = conexao.executar("SELECT cliPessoaJuridica.cliCNPJ, cliPessoaJuridica.cliRazaoSocial, cliPessoaJuridica.cliIM, cliente.cliEndereco, cliente.cliCidade, cliente.cliUF, cliente.cliEmail FROM cliente INNER JOIN cliPessoaJuridica ON cliente.cliCodigo = cliPessoaJuridica.cliCodigo WHERE cliente.cliCodigo = "+ codigoCliente);
                rs.next();
                
                txtCNPJCPFTomador.setText(rs.getString(1));
                txtRazaoSocialTomador.setText(rs.getString(2));
                txtIMTomador.setText(rs.getString(3));
                txtEnderecoTomador.setText(rs.getString(4));
                txtMunicipioTomador.setText(rs.getString(5));
                txtUfTomador.setText(rs.getString(6));
                txtEmail.setText(rs.getString(7));                
            
            }else if("F".equals(rs.getString(1).toUpperCase())){
                
                rs.close();
                
                rs = conexao.executar("SELECT cliPessoaFisica.cliCPF, cliente.cliNome, cliente.cliEndereco, cliente.cliCidade, cliente.cliUF, cliente.cliEmail FROM cliente INNER JOIN cliPessoaFisica ON cliente.cliCodigo = cliPessoaFisica.cliCodigo WHERE cliente.cliCodigo = "+ codigoCliente);
                rs.next();
                
                txtCNPJCPFTomador.setText(rs.getString(1));
                txtRazaoSocialTomador.setText(rs.getString(2));
                txtIMTomador.setText(" - ");
                txtEnderecoTomador.setText(rs.getString(3));
                txtMunicipioTomador.setText(rs.getString(4));
                txtUfTomador.setText(rs.getString(5));
                txtEmail.setText(rs.getString(6));
                
            }else{
                throw new IllegalArgumentException("Este cliente foi cadastrado de forma incorreta. Por favor, entre em contato com administrador do sistema.");
            }            
            
        } catch (SQLException b) {
            JOptionPane.showMessageDialog(null, b.getMessage() + ". Ocorreu um erro de SQL. Por favor, entre em contato com administrador do sistema.");
        }
        catch (IllegalArgumentException b){
            JOptionPane.showMessageDialog(null, b.getMessage());
        }
        catch (Exception b) {
            JOptionPane.showMessageDialog(null,"Erro desconhecido. Por favor entre em contato com administrador do sistema. \n" + b.getMessage());
        }
        
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
        cboBandeira.addItem("");
        cboBandeira.addItem("Master Card");
        cboBandeira.addItem("Visa");
        
        cboDias.addItem("");
        cboDias.addItem("30");
        cboDias.addItem("60");
        cboDias.addItem("90");
        
        cboParcelas.addItem("");
        cboParcelas.addItem("1");
        cboParcelas.addItem("2");
        cboParcelas.addItem("3");
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
        if(auxiliar.validaCampos(telaConfirmaNotaFiscal.getListaComponentes())){
            this.strOutrasInformacoes = txtOutrasInformacoes.getText();
            this.dblValorTotal = Double.parseDouble(txtValorTotal.getText());
            
            return true;
        }
        
        return false;
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
        
        boolean ok ;
        if (botao.getSource() == botCadastrar) {            
            ok = cadastrar();
            if(ok){	
                try {
                    ResultSet rs;
                    conexao.executaProcedure("INSERT_NOTAFISCAL ('" + auxiliar.hoje() + "', '" + auxiliar.hoje() + "', " + this.dblValorTotal + ", " )");
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
            
            this.intCodigoPagamento = 1;
            
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
            
            this.intCodigoPagamento = 2;
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
            
            this.intCodigoPagamento = 3;
        }
        
        
    }
    
}
