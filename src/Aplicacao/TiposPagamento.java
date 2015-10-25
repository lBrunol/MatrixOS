/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplicacao;

import Core.ConexaoBanco;
import Core.MontaInterfaces;
import java.awt.GridBagLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Renan
 */
public class TiposPagamento {
    
    //panels
    private JPanel panelConsulta = new JPanel (new GridBagLayout());
    private JPanel panelLancar = new JPanel (new GridBagLayout());
    private JPanel panelBotoes = new JPanel (new GridBagLayout());

    //caixas de texto
    private JTextField txtCodigo = new JTextField();
    private JTextField  txtDescricao = new JTextField();
    
    //Botões
    private JButton botCadastrar = new JButton ();
    private JButton botExcluir = new JButton ();
    private JButton botAlterar = new JButton ();
    
    //Tabela
    DefaultTableModel Tabela = new DefaultTableModel();
    private JTable tblTiposPagamento = new JTable (Tabela);
    
    public TiposPagamento() {
                this.iniciaComponentes();

}
    public static void main (String[]args){
        TiposPagamento tp = new TiposPagamento ();
    }
    
    public void iniciaComponentes(){
        //Instância da classe monta interfaces, passei o nome do formulário e o caminho onde a imagem dele está
        MontaInterfaces telaOS = new MontaInterfaces("Tipos de Pagamentos", "/imagens/icone-tipos-pagamento.png");
        //Deixei a janela visível
        telaOS.setVisible(true);        
        //Adicionei as abas com o método addAbas e o panel para os botões com o método addPanelBotoes
        telaOS.addAbas(panelLancar, "Cadastro");
        telaOS.addAbas(panelConsulta, "Consulta");
        telaOS.addPanelBotoes(panelLancar, panelBotoes);
        
        //Adicionei os botões dentro do panelBotoes
        telaOS.addBotoes("Salvar", botCadastrar, panelBotoes);
        telaOS.addBotoes("Cancelar", botExcluir, panelBotoes);
        
        //Criei objetos do tipo icone com o caminho do icone para coloca-los nos botões 
        Icon iconeCadastrar = new ImageIcon(getClass().getResource("/imagens/salvar.png"));
        Icon iconeExcluir = new ImageIcon(getClass().getResource("/imagens/cancelar.png"));
        
        //Coloca icone nos botões
        botCadastrar.setIcon(iconeCadastrar);
        botExcluir.setIcon(iconeExcluir);
        
         //Adiciona os componentes na tela
        telaOS.addLabelTitulo("Lançamento", panelLancar);
        telaOS.addDoisComponentes("Codigo", txtCodigo, "Descrição", txtDescricao, panelLancar);
        telaOS.addTabela(tblTiposPagamento, panelConsulta);
        ConexaoBanco teste=new ConexaoBanco();
        teste.preencheTabela(Tabela, "select *from tipospagamento");
    }
}
