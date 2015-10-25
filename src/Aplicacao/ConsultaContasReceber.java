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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

/**
 *
 * @author Fabiano
 */
public class ConsultaContasReceber {
    //Instância da classe Métodos auxliares
    MetodosAuxiliares auxiliar = new MetodosAuxiliares();
    
    //Panels
    private JPanel panelConsulta = new JPanel(new GridBagLayout());
    private JPanel panelBotoesConsulta = new JPanel(new GridBagLayout());
    
    //Caixas de texto
    private JTextField txtCliente = new JTextField();
    private JTextField txtDataInicial = new JFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_DATA));
    private JCheckBox  chkEntreDatas = new JCheckBox("EntreDatas",false); 
    private JTextField txtDataFinal = new JFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_DATA));
    private JComboBox cboStatus = new JComboBox();
    
    //Tabela
    DefaultTableModel tabela = new DefaultTableModel();
    private JTable tblConsultaContasReceber = new JTable(tabela);
    
    //Botões
    private JButton botLancaPagamento = new JButton();
    private JButton botGerarRelatorio = new JButton();
    
    public ConsultaContasReceber(){
        this.iniciaComponentes();
    }
     public static void main(String[] args){
        ConsultaContasReceber ccr = new ConsultaContasReceber();
    }
    //Adiciona os componentes na tela
    public void iniciaComponentes(){
        
        //Instância da classe monta interfaces, passei o nome do formulário e o caminho onde a imagem dele está
        MontaInterfaces telaOS = new MontaInterfaces("Consulta Contas a Receber", "/imagens/contas-receber-2.png");
       
        //Deixei a janela visível
        telaOS.setVisible(true);        
        
        //Adicionei as abas com o método addAbas e o panel para os botões com o método addPanelBotoes
        //Adiciona os componentes na tela
        telaOS.addAbas(panelConsulta, "Consulta");
        telaOS.addPanelBotoes(panelConsulta,panelBotoesConsulta);
        telaOS.addBotoes("Lançar Pagamento", botLancaPagamento, panelBotoesConsulta);
        telaOS.addBotoes("Gerar Relatório", botGerarRelatorio, panelBotoesConsulta); 
        telaOS.addCincoComponentes("Cliente", txtCliente, "Data Inicial", txtDataInicial,"Entre Datas",chkEntreDatas,"DataFinal",txtDataFinal,"Status",cboStatus,panelConsulta);
        telaOS.addTabela(tblConsultaContasReceber,panelConsulta);
        ConexaoBanco teste=new ConexaoBanco();
        teste.preencheTabela(tabela, "select *from contasreceber");
        
        //Cria objetos do tipo icone para coloca-los nos botões
        Icon iconeLancaPagamento = new ImageIcon(getClass().getResource("/imagens/lancar-pagamento.png"));
        Icon iconeGerarRelatorio = new ImageIcon(getClass().getResource("/imagens/relatorio.png"));
        
        //Seta os icones dos botões
        botLancaPagamento.setIcon(iconeLancaPagamento);
        botGerarRelatorio.setIcon(iconeGerarRelatorio);
    }
}
