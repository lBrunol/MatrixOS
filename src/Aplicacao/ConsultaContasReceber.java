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
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class ConsultaContasReceber implements ActionListener {
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
    private JTable tblConsultaContasReceber = new JTable(tabela);
    
    //Botões
    private JButton botLancaPagamento = new JButton();
    private JButton botPesquisar = new JButton();
    
    //Select
    private String query = "SELECT contasReceber.ctrCodigo as Código, cliente.cliNome as Nome, cliente.cliEndereco as Endereço, contasReceber.ctrDataEmissao, contasReceber.ctrDataVencimento, contasReceber.ctrValorEmitido , contasReceber.ctrDataPagamento, contasReceber.ctrDesconto as Desconto, contasReceber.ctrJuros as Juros, contasReceber.ctrValorPago, tiposPagamento.tpaDescricao, notaFiscal.notCodigo FROM ((((tiposPagamento INNER JOIN contasReceber ON tiposPagamento.tpaCodigo = contasReceber.tpaCodigo) INNER JOIN notaFiscal ON contasReceber.notCodigo = notaFiscal.notCodigo) INNER JOIN ordemServico ON notaFiscal.ordCodigo = ordemServico.ordCodigo) INNER JOIN cliente ON ordemServico.cliCodigo = cliente.cliCodigo)";
    
    public ConsultaContasReceber(){
        this.iniciaComponentes();
        this.atribuiIcones();
        this.preencheTabela();
        
        telaConsultaContas.setVisible(true);
        telaConsultaContas.setTamanho(1000, 1000);
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
    }
    
    public void atribuiIcones() {
         Icon iconeLancaPagamento = new ImageIcon(getClass().getResource("/imagens/lancar-pagamento.png"));
        Icon iconePesquisar = new ImageIcon(getClass().getResource("/imagens/pesquisar.png"));

        botLancaPagamento.setIcon(iconeLancaPagamento);
        botPesquisar.setIcon(iconePesquisar);
    }
    
    public void preencheTabela() {
        try {
            conexao.preencheTabela(tabela, query);            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage()+ "\n" + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent botao) {
        if(botao.getSource() == botPesquisar){
            try {
                ResultSet rs;
                rs = conexao.executar("select * from USUARIOS");
                rs.next();
                
            }catch (SQLException b) {
                JOptionPane.showMessageDialog(null, b.getMessage() + ". Ocorreu um erro de SQL. Por favor, entre em contato com administrador do sistema.");
            }
            catch (Exception b) {
                JOptionPane.showMessageDialog(null,"Erro desconhecido. Por favor entre em contato com administrador do sistema. \n" + b.getMessage());
            }        
        }
    }
}
