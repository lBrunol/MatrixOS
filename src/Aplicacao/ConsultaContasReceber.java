/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplicacao;

import Core.ConexaoBanco;
import Core.MetodosAuxiliares;
import Core.MontaInterfaces;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Fabiano
 */
public class ConsultaContasReceber implements ActionListener, FocusListener {
    
    MetodosAuxiliares auxiliar = new MetodosAuxiliares();
    MontaInterfaces telaConsultaContas = new MontaInterfaces("Consulta Contas a Receber", "/imagens/contas-receber-2.png");
    ConexaoBanco conexao = new ConexaoBanco();
    
    //Panels
    private JPanel panelConsulta = new JPanel(new GridBagLayout());
    private JPanel panelBotoesConsulta = new JPanel(new GridBagLayout());
    
    //Caixas de texto    
    private JTextField txtCodigo = new JTextField();
    private JTextField txtCliente = new JTextField();
    private JTextField txtData = new JFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_DATA));
    private JComboBox cboStatus = new JComboBox();
    
    //Tabela
    DefaultTableModel tabela = new DefaultTableModel();
    private JTable tblConsultaContasReceber = new JTable(tabela){public boolean isCellEditable(int row,int column){return false;}};
    
    //Botões
    private JButton botLancaPagamento = new JButton();
    private JButton botPesquisar = new JButton();
    
    //Select
    private String query = "SELECT contasReceber.ctrCodigo as Código, cliente.cliNome as Nome, cliente.cliEndereco as Endereço, contasReceber.ctrDataEmissao, contasReceber.ctrDataVencimento, contasReceber.ctrValorEmitido , contasReceber.ctrDataPagamento, contasReceber.ctrDesconto as Desconto, contasReceber.ctrJuros as Juros, contasReceber.ctrValorPago, tiposPagamento.tpaDescricao, notaFiscal.notCodigo FROM ((((tiposPagamento INNER JOIN contasReceber ON tiposPagamento.tpaCodigo = contasReceber.tpaCodigo) INNER JOIN notaFiscal ON contasReceber.notCodigo = notaFiscal.notCodigo) INNER JOIN ordemServico ON notaFiscal.ordCodigo = ordemServico.ordCodigo) INNER JOIN cliente ON ordemServico.cliCodigo = cliente.cliCodigo)";
    
    public ConsultaContasReceber(){
        this.iniciaComponentes();
        this.atribuiIcones();
        this.preencheTabela();
        this.adicionaEventos();
        
        telaConsultaContas.setVisible(true);
        telaConsultaContas.setTamanho(1000, 600);
        telaConsultaContas.setMaximizado(true);
    }
    
    public static void main(String[] args){
        ConsultaContasReceber ccr = new ConsultaContasReceber();
    }

    public void iniciaComponentes(){
        telaConsultaContas.addAbas(panelConsulta, "Consulta");
        telaConsultaContas.addPanelBotoes(panelConsulta, panelBotoesConsulta);
        telaConsultaContas.addBotoes("Lançar Pagamento", botLancaPagamento, panelBotoesConsulta);
        telaConsultaContas.addBotoes("Buscar", botPesquisar, panelBotoesConsulta); 
        telaConsultaContas.addQuatroComponentes("Código", txtCodigo, "Cliente", txtCliente, "Data", txtData, "Status",cboStatus,panelConsulta);
        telaConsultaContas.addTabela(tblConsultaContasReceber,panelConsulta); 
        
        cboStatus.addItem("");
        cboStatus.addItem("Pago");
        cboStatus.addItem("Pendente");
        cboStatus.addItem("Atrasado");        
        
        botPesquisar.addActionListener(this);
        txtCliente.addFocusListener(this);
        txtCodigo.addFocusListener(this);
        txtData.addFocusListener(this);
        cboStatus.addFocusListener(this);
    }
    
    public void atribuiIcones() {
        Icon iconeLancaPagamento = new ImageIcon(getClass().getResource("/imagens/lancar-pagamento.png"));
        Icon iconePesquisar = new ImageIcon(getClass().getResource("/imagens/pesquisar.png"));

        botLancaPagamento.setIcon(iconeLancaPagamento);
        botPesquisar.setIcon(iconePesquisar);
    }
    
    public void preencheTabela() {
        try {
            conexao.preencheTabelaSelect(tabela, query);            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage()+ "\n" + e.getMessage());
        }
    }
    
    public void adicionaEventos(){
        tblConsultaContasReceber.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {                              
                if (me.getClickCount() == 2) {                    
                    try {
                        
                        JTable table =(JTable) me.getSource();
                        Point p = me.getPoint();  
                        int row = table.rowAtPoint(p);
                        int codigoDocumento;
                        if(tblConsultaContasReceber.getValueAt(row, 6) != null){
                            JOptionPane.showMessageDialog(null, "Este documento já tem o pagamento lançado");
                        }else{
                            codigoDocumento = Integer.parseInt(tblConsultaContasReceber.getValueAt(row, 0).toString());
                            LancaPagamento lan = new LancaPagamento(codigoDocumento);
                        }                       
                    }             
                    catch (HeadlessException | NumberFormatException b) {
                        JOptionPane.showMessageDialog(null,"Erro desconhecido. Por favor entre em contato com administrador do sistema. \n" + b.getCause());
                    }
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent botao) {
        String queryFilter = query;
        if(botao.getSource() == botPesquisar){
            if(!"".equals(txtCliente.getText())){
                queryFilter = query  + " WHERE cliente.cliNome LIKE '%" + txtCliente.getText() + "%'";
            }else if(!"".equals(txtCodigo.getText())){
                queryFilter = query  + " WHERE contasReceber.ctrCodigo = " + txtCodigo.getText() + "";
            }else if(!"".equals(auxiliar.removeCaracteresString(txtData.getText()))){
                queryFilter = query  + " WHERE contasReceber.ctrDataVencimento = '" + txtData.getText() + "'";
            }else if(cboStatus.getSelectedIndex() != 0){
                if(cboStatus.getSelectedItem().toString() == "Pago"){
                     queryFilter = query  + " WHERE contasReceber.ctrDataPagamento Is Not Null";                     
                } else if(cboStatus.getSelectedItem().toString() == "Pendente"){
                     queryFilter = query  + " WHERE contasReceber.ctrDataVencimento >= '" + auxiliar.hoje() + "' AND contasReceber.ctrDataPagamento Is Null";                     
                } else if(cboStatus.getSelectedItem().toString() == "Atrasado"){
                     queryFilter = query  + " WHERE contasReceber.ctrDataVencimento < '" + auxiliar.hoje() + "' AND  contasReceber.ctrDataPagamento Is Null";
                     System.out.println(queryFilter);
                }
                    
            }
            try{
                conexao.preencheTabelaSelect(tabela, queryFilter);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage()+ "\n" + e.getMessage());
            }
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(e.getSource() == txtCliente && (!"".equals(txtCodigo.getText()) || !"".equals(txtData.getText()) || cboStatus.getSelectedIndex() != 0)){
            txtCodigo.setText("");
            txtData.setText("");
            cboStatus.setSelectedIndex(0);
        }
        if(e.getSource() == txtCodigo && (!"".equals(txtCliente.getText()) || !"".equals(txtData.getText()) || cboStatus.getSelectedIndex() != 0)){
            txtCliente.setText("");
            txtData.setText("");
            cboStatus.setSelectedIndex(0);
        }
        if(e.getSource() == txtData && (!"".equals(txtCliente.getText()) || !"".equals(txtCodigo.getText()) || cboStatus.getSelectedIndex() != 0)){
            txtCliente.setText("");
            txtCodigo.setText("");
            cboStatus.setSelectedIndex(0);
        }
        if(e.getSource() == cboStatus && (!"".equals(txtCliente.getText()) || !"".equals(txtCodigo.getText()) || !"".equals(txtData.getText()))){
            txtCliente.setText("");
            txtCodigo.setText("");
            txtData.setText("");
        }
    }

}
