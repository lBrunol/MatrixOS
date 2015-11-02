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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Fabiano
 */
public class LancaPagamento {
    //Instância da classe Métodos auxliares
    MetodosAuxiliares auxiliar = new MetodosAuxiliares();
    
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
    
    //Tabela
    DefaultTableModel tabela = new DefaultTableModel();
    private JTable tblClientes = new JTable(tabela);
    
    public LancaPagamento(){
        this.iniciaComponentes();
    }
public static void main(String[] args){
        LancaPagamento lp = new LancaPagamento();
    }
public void iniciaComponentes(){
        
        //Instância da classe monta interfaces, passei o nome do formulário e o caminho onde a imagem dele está
        MontaInterfaces telaOS = new MontaInterfaces("Lançamento de Pagamentos", "/imagens/lancar-pagamento-maior.png");
        //Deixei a janela visível
        telaOS.setVisible(true);        
        //Adicionei as abas com o método addAbas e o panel para os botões com o método addPanelBotoes
        telaOS.addAbas(panelLancamento, "Lançamentos");
        telaOS.addPanelBotoes(panelLancamento, panelBotoes);
        
        //Adicionei os botões dentro do panelBotoes
        telaOS.addBotoes("Salvar", botSalvar, panelBotoes);
        telaOS.addBotoes("Cancelar", botCancelar, panelBotoes);
        
        //Criei objetos do tipo icone com o caminho do icone para coloca-los nos botões 
        Icon iconeSalvar = new ImageIcon(getClass().getResource("/imagens/salvar.png"));
        Icon iconeCancelar = new ImageIcon(getClass().getResource("/imagens/cancelar.png"));
       
        //Seta os icones dos botões
        botSalvar.setIcon(iconeSalvar);
        botCancelar.setIcon(iconeCancelar);
       
        //Adiciona os componentes na tela
        telaOS.addLabelTitulo("Lançamento", panelLancamento);
        telaOS.addQuatroComponentes("Numero do Documento", txtNumeroDocumento, "Data de Vencimento", txtDataVencimento, "Data Pagamento", txtDataPagamento, "Valor Pago", txtValorPago, panelLancamento);
    }
}
