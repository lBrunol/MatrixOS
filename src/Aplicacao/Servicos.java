
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





public class Servicos {
    
    //Panels
    private JPanel panelConsulta = new JPanel(new GridBagLayout());
    private JPanel panelCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoes = new JPanel(new GridBagLayout());  
    
    //Caixas de texto
    private JTextField txtCodigoServico = new JTextField();
    private JTextField txtNome = new JTextField();
    private JTextField txtValorHora= new JTextField();
    private JTextField txtAliquota = new JTextField();
    private JTextField txtDescricao = new JTextField();
    
    //Botões
    private JButton botCadastrar = new JButton();
    private JButton botExcluir = new JButton();
    private JButton botAlterar = new JButton();
    
    //Tabela
    DefaultTableModel tabela = new DefaultTableModel();
    private JTable tblServicos = new JTable(tabela);
    
    public Servicos(){
        this.iniciaComponentes();
    }
    
    public static void main(String[] args){
        Servicos sv = new Servicos();
    }
    
    public void iniciaComponentes(){
        
        MontaInterfaces telaServicos = new MontaInterfaces ("Servicos", "/imagens/servicos.png");
        
        telaServicos.setVisible(true);        
        
        telaServicos.addAbas(panelCadastro, "Cadastro");
        telaServicos.addAbas(panelConsulta, "Consulta");
        telaServicos.addPanelBotoes(panelCadastro, panelBotoes);
        
        telaServicos.addBotoes("Cadastrar", botCadastrar, panelBotoes);
        telaServicos.addBotoes("Limpar", botExcluir, panelBotoes);
        
        Icon iconeCadastrar = new ImageIcon(getClass().getResource("/imagens/salvar.png"));
        Icon iconeExcluir = new ImageIcon(getClass().getResource("/imagens/excluir.png"));
        
        botCadastrar.setIcon(iconeCadastrar);
        botExcluir.setIcon(iconeExcluir);
        
        telaServicos.addLabelTitulo("Servicos", panelCadastro);
        telaServicos.addUmComponente("Codigo do Servico", txtCodigoServico, panelCadastro);
        telaServicos.addDoisComponentes("Nome", txtNome, "Valor da Hora", txtValorHora, panelCadastro);
        telaServicos.addDoisComponentes("Aliquota", txtAliquota, "Descricao", txtDescricao, panelCadastro);
        telaServicos.addTabela(tblServicos, panelConsulta);
        ConexaoBanco teste = new ConexaoBanco();
        
        
    }
        
}
