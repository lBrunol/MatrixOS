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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;


public class ConsultaNotaFiscal implements ActionListener, FocusListener{
    
    MetodosAuxiliares auxiliar = new MetodosAuxiliares();
    MontaInterfaces telaConsultaNotaFiscal = new MontaInterfaces("Consulta Notas Fiscais", "/imagens/nota-fiscal-eletronica-s-f-2.png");
    ConexaoBanco conexao = new ConexaoBanco();
    
    //Panels
    private JPanel panelConsulta = new JPanel(new GridBagLayout());
    private JPanel panelBotoesConsulta = new JPanel(new GridBagLayout());
    
    //Caixas de texto
    private JTextField txtCodigo = new JTextField();
    private JTextField txtCliente = new JTextField();
    private JTextField txtData = new JFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_DATA));
    
    //Tabela
    DefaultTableModel tabela = new DefaultTableModel();
    private JTable tblConsultaNotaFiscal = new JTable(tabela){public boolean isCellEditable(int row,int column){return false;}};
    
    //Botões
    private JButton botPesquisar = new JButton();
    
    //Select
    String teste = "Data Emissão";
    private String query = "SELECT notaFiscal.notCodigo, cliente.cliNome as Nome , notaFiscal.notValor as Valor, notaFiscal.notData, notaFiscal.notCodVerificacao, notaFiscal.notOutrasInformacoes FROM cliente INNER JOIN (ordemServico INNER JOIN notaFiscal ON ordemServico.ordCodigo = notaFiscal.ordCodigo) ON cliente.cliCodigo = ordemServico.cliCodigo";
    
    public ConsultaNotaFiscal(){
        this.iniciaComponentes();
        this.atribuiIcones();
        this.preencheTabela();
        
        telaConsultaNotaFiscal.setVisible(true);
        telaConsultaNotaFiscal.setTamanho(1000, 1000);
        telaConsultaNotaFiscal.setMaximizado(true);
    }
    
    public static void main(String[] args){
        ConsultaNotaFiscal cnf = new ConsultaNotaFiscal();
    }

    public void iniciaComponentes(){        
        telaConsultaNotaFiscal.addAbas(panelConsulta, "Consulta");
        telaConsultaNotaFiscal.addPanelBotoes(panelConsulta,panelBotoesConsulta);
        telaConsultaNotaFiscal.addBotoes("Buscar", botPesquisar, panelBotoesConsulta); 
        telaConsultaNotaFiscal.addTresComponentes("Código", txtCodigo, "Cliente", txtCliente, "Data", txtData, panelConsulta);
        telaConsultaNotaFiscal.addTabela(tblConsultaNotaFiscal,panelConsulta);
        
        botPesquisar.addActionListener(this);
        txtCliente.addFocusListener(this);
        txtData.addFocusListener(this);
        
    }
    
     public void atribuiIcones() {   
        Icon iconeGerarRelatorio = new ImageIcon(getClass().getResource("/imagens/pesquisar.png"));  
        botPesquisar.setIcon(iconeGerarRelatorio);
     }
     
     public void preencheTabela(){
        try {
            conexao.preencheTabela(tabela, query);            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage()+ "\n" + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent botao) {
        String queryFilter = query;
        if(botao.getSource() == botPesquisar){
            if(!"".equals(txtCliente.getText())){
                queryFilter = query  + " WHERE cliente.cliNome LIKE '%" + txtCliente.getText() + "%'";
            }else if(!"".equals(txtCodigo.getText())){
                queryFilter = query  + " WHERE notaFiscal.notCodigo= " + txtCodigo.getText() + "";
            }else if(!"".equals(auxiliar.removeCaracteresString(txtData.getText()))){
                queryFilter = query  + " WHERE notaFiscal.notData = '" + txtData.getText() + "'";
            }
            conexao.preencheTabela(tabela, queryFilter);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(e.getSource() == txtCliente && !"".equals(txtData.getText()) || !"".equals(txtCodigo.getText())){
            txtData.setText("");
            txtCodigo.setText("");
        }
        if(e.getSource() == txtCodigo && !"".equals(txtData.getText()) || !"".equals(txtCliente.getText())){
            txtData.setText("");
            txtCliente.setText("");
        }
        if(e.getSource() == txtData && !"".equals(txtCliente.getText()) || !"".equals(txtCodigo.getText())){
            txtCliente.setText("");
            txtCodigo.setText("");
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        
    }
}