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

/**
 *
 * @author Fabiano
 */
public class LancaPagamento implements ActionListener {
    MetodosAuxiliares auxiliar = new MetodosAuxiliares();
    MontaInterfaces telaLancaPagamentos = new MontaInterfaces("Lançamento de Pagamentos", "/imagens/lancar-pagamento-maior.png");
    ConexaoBanco conexao = new ConexaoBanco();
    
    //Painels
    private JPanel panelLancamento = new JPanel(new GridBagLayout());
    private JPanel panelBotoes = new JPanel(new GridBagLayout());
    
    //Caixas de texto
    private PTextField txtNumeroDocumento = new PTextField();
    private PFormattedTextField txtDataVencimento = new PFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_DATA));
    private PFormattedTextField txtDataPagamento = new PFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_DATA));
    private PTextField txtValorPago = new PTextField();
    
    //Botões
    private JButton botSalvar = new JButton();
    private JButton botCancelar = new JButton();
    
    //Atributos relacionados ao banco
    
    private int intCodigo;
    private String strDataPagamento;
    private float fltValorPago;
    
    public LancaPagamento(int codigoDocumento){
        ResultSet rs;
        
        this.iniciaComponentes();
        this.atribuiIcones();        
        this.intCodigo = codigoDocumento;
        txtNumeroDocumento.setText(Integer.toString(intCodigo));
        try {
            
            rs = conexao.executaProcedureSelect("CONSULTA_PAGAMENTOS(" + this.intCodigo + ")");
            rs.next();
            txtDataVencimento.setText(auxiliar.formataData(rs.getDate(1)));
            txtDataPagamento.setText(auxiliar.hoje());
            this.fltValorPago = rs.getFloat(2);
            txtValorPago.setText(auxiliar.formataValor(fltValorPago));
            
        }catch (SQLException b) {
            JOptionPane.showMessageDialog(null, b.getMessage() + ". Ocorreu um erro de SQL. Por favor, entre em contato com administrador do sistema.");
        }
        catch (Exception b) {
            JOptionPane.showMessageDialog(null,"Erro desconhecido. Por favor entre em contato com administrador do sistema. \n" + b.getMessage());
        }
        
        txtDataVencimento.setEnabled(false);
        txtDataVencimento.setObrigatorio(false);        
        txtNumeroDocumento.setEnabled(false);
        txtNumeroDocumento.setObrigatorio(false);        
        
        telaLancaPagamentos.setVisible(true);
        
        this.setaNomes();
    }
    
    public static void main(String[] args){
        LancaPagamento lp = new LancaPagamento(1);
    }
    
    public void iniciaComponentes(){ 

        telaLancaPagamentos.addAbas(panelLancamento, "Lançamentos");
        telaLancaPagamentos.addPanelBotoes(panelLancamento, panelBotoes);
        
        telaLancaPagamentos.addBotoes("Salvar", botSalvar, panelBotoes);
        telaLancaPagamentos.addBotoes("Cancelar", botCancelar, panelBotoes);
        
        telaLancaPagamentos.addLabelTitulo("Lançamento", panelLancamento);
        telaLancaPagamentos.addDoisComponentes("Numero do Documento", txtNumeroDocumento, "Data de Vencimento", txtDataVencimento, panelLancamento);
        telaLancaPagamentos.addDoisComponentes("Data Pagamento", txtDataPagamento, "Valor Pago", txtValorPago, panelLancamento);
        
        botSalvar.addActionListener(this);
        botCancelar.addActionListener(this);
    }
    
    public void atribuiIcones(){
        
        Icon iconeSalvar = new ImageIcon(getClass().getResource("/imagens/salvar.png"));
        Icon iconeCancelar = new ImageIcon(getClass().getResource("/imagens/cancelar.png"));
       
        botSalvar.setIcon(iconeSalvar);
        botCancelar.setIcon(iconeCancelar);
    
    }
    
    public boolean cadastrar(){
        if(auxiliar.validaCampos(telaLancaPagamentos.getListaComponentes())){
            this.intCodigo = Integer.parseInt(txtNumeroDocumento.getText());
            this.strDataPagamento = txtDataPagamento.getText();
            this.fltValorPago = auxiliar.removeCaracteresFloat(txtValorPago.getText());
            
            return true;
        }else{
            return false;
        }
    }
    
    public void setaNomes(){
        txtDataPagamento.setName("Data de Pagamento");
        txtValorPago.setName("Valor pago");
    }

    @Override
    public void actionPerformed(ActionEvent botao) {
        boolean ok ;
        if (botao.getSource() == botSalvar) {            
            ok = cadastrar();
            if(ok){	
                try {
                    ResultSet rs;
                    conexao.executaProcedure("LANCA_PAGAMENTO (" + this.intCodigo + ",'" + this.strDataPagamento + "', " + this.fltValorPago + ")");
                    JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso");
                    telaLancaPagamentos.dispose();                    
                }catch (SQLException b) {
                    JOptionPane.showMessageDialog(null, b.getMessage() + ". Ocorreu um erro de SQL. Por favor, entre em contato com administrador do sistema.");
                }
                catch (Exception b) {
                    JOptionPane.showMessageDialog(null,"Erro desconhecido. Por favor entre em contato com administrador do sistema. \n" + b.getMessage());
                }                                    
            }
        }        
        if (botao.getSource() == botCancelar){
            telaLancaPagamentos.dispose();
        }
    }
}
