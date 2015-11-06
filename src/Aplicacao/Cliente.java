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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javafx.scene.control.RadioButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JRadioButton;

/**
 *
 * @author CASA
 */
public class Cliente implements ActionListener {
    //Instância da classe Métodos auxliares
    MetodosAuxiliares auxiliar = new MetodosAuxiliares();
    //Instância da classe que monta a tela
    MontaInterfaces telaOS = new MontaInterfaces("Gerenciamento de Clientes", "/imagens/clientes.png");
    
    
    //Panels
    private JPanel panelConsulta = new JPanel(new GridBagLayout());
    private JPanel panelCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoes = new JPanel(new GridBagLayout());
    private JPanel panelCliPF = new JPanel(new GridBagLayout());
    private JPanel panelCliPJ = new JPanel(new GridBagLayout());
    
    
    //Caixas de texto
    private PTextField txtCodigo = new PTextField();
    private JTextField txtNome = new JTextField();
    private JTextField txtEndereco = new JTextField();
    private JTextField txtBairro = new JTextField();
    private JTextField txtCidade = new JTextField();
    private JTextField txtDataCadastro  = new JFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_DATA));
    private JComboBox cboEstado= new JComboBox();
    private JTextField txtEmail = new JTextField();
    private JTextField txtCEP = new JFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_CEP));
    private JTextField txtCelular = new JFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_TELEFONE));
    private JTextField txtTelefone = new JFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_TELEFONE));
    private JTextField txtObservacao = new JTextField();
    private JTextField txtComplemento= new JTextField();
    private JRadioButton rdbpf = new JRadioButton();
    private JRadioButton rdbpj = new JRadioButton();
    
    //campos pj
    private PTextField txtRazaoSocial = new PTextField();
    private PTextField txtNomeFantasia = new PTextField();
    private PTextField txtIM = new PTextField();
    private PTextField txtIE = new PTextField();
    private PFormattedTextField txtCNPJ = new PFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_CNPJ));
    
    //CAMPOS Pf
    private JTextField txtCPF = new  JFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_CPF));
    private JFormattedTextField txtRG = new JFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_RG));
   
  
    
    //Botões
    private JButton botCadastrar = new JButton();
    private JButton botExcluir = new JButton();
    private JButton botAlterar = new JButton();
    
    //Tabela
    DefaultTableModel tabela = new DefaultTableModel();
    private JTable tblClientes = new JTable(tabela);
    
    
    
   
    //construtor
    public Cliente(){
        this.iniciaComponentes();
        
     
       
       
    }
    
    public static void main(String[] args){
        Cliente cli = new Cliente();
        
         //Instância do construtor Radio Button 
          //RadioButton rdb= new RadioButton();
          
        
    }
    
    
    public void iniciaComponentes(){
        
      
        //Deixei a janela visível
        telaOS.setVisible(true);        
        //Adicionei as abas com o método addAbas e o panel para os botões com o método addPanelBotoes
        telaOS.addAbas(panelCadastro, "Cadastro");
        telaOS.addAbas(panelConsulta, "Consulta");
        telaOS.addPanelBotoes(panelCadastro, panelBotoes);
        
        //Adicionei os botões dentro do panelBotoes
        telaOS.addBotoes("Cadastrar", botCadastrar, panelBotoes);
        telaOS.addBotoes("Limpar", botExcluir, panelBotoes);
        
        //Criei objetos do tipo icone com o caminho do icone para coloca-los nos botões 
        Icon iconeCadastrar = new ImageIcon(getClass().getResource("/imagens/salvar.png"));
        Icon iconeExcluir = new ImageIcon(getClass().getResource("/imagens/excluir.png"));
        
        //Seta os icones dos bortões
        botCadastrar.setIcon(iconeCadastrar);
        botExcluir.setIcon(iconeExcluir);
        
        //Adiciona os componentes na tela
        telaOS.addLabelTitulo("Cliente", panelCadastro);
       
        telaOS.addDoisComponentes("Codigo", txtCodigo,"Nome",txtNome,panelCadastro);
        telaOS.addUmComponente("Endereço",txtEndereco,panelCadastro);
        telaOS.addQuatroComponentes( "Bairro", txtBairro, "Complemento", txtComplemento,"Estado", cboEstado,"Cidade",txtCidade, panelCadastro);
        telaOS.addQuatroComponentes("CEP", txtCEP, "Telefone", txtTelefone, "Celular", txtCelular, "Data Cadastro", txtDataCadastro, panelCadastro);
        telaOS.addUmComponente("Observação",txtObservacao,panelCadastro);
        
        telaOS.addPanelComponentes(panelCadastro, panelCliPF);
        telaOS.addPanelComponentes(panelCadastro, panelCliPJ);
        
        panelCliPJ.setVisible(false);
        rdbpf.setSelected(true);
        
        telaOS.addQuatroComponentes("CNPJ", txtCNPJ, "Razão Social", txtRazaoSocial, "IE", txtIE, "IM", txtIM, panelCliPJ);
        
        telaOS.addDoisComponentes("RG", txtRG, "CPF", txtCPF, panelCliPF);
        
        rdbpf.addActionListener(this);
        rdbpj.addActionListener(this);
        botCadastrar.addActionListener(this);
        
        cboEstado.addItem("São paulo");
     
        //aparece os radio button
        //falta chamar os atributos das classes pf e pj.
        //falta arrumar a seleção do radio, quando um aparece outro fica desativado.
        telaOS.addDoisComponentes("Pessoa Física",rdbpf,"Pessoa Jurídica",rdbpj,panelCadastro);
         
        telaOS.addTabela(tblClientes, panelConsulta);
        ConexaoBanco teste=new ConexaoBanco();
        teste.preencheTabela(tabela, "select * from cliente");
        
         telaOS.setTamanho(1000, 1000);
       
        
      

        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == botCadastrar){
            if(auxiliar.validaCampos(telaOS.getListaComponentes())){                
                
            }
        }
        
        if(e.getSource() == rdbpf){
            rdbpj.setSelected(false);
            panelCliPJ.setVisible(false);
            panelCliPF.setVisible(true);
            //txtRG.setVisible(true);
            //txtCPF.setVisible(true);
            //
            //txtRazaoSocial.setObrigatorio(false);
            //txtNomeFantasia.setObrigatorio(false);
            //txtCNPJ.setObrigatorio(false);
            //txtIM.setObrigatorio(false);
            //txtIE.setObrigatorio(false);
            
            
            
        }
        if(e.getSource() == rdbpj){
            rdbpf.setSelected(false);
            
            panelCliPJ.setVisible(true);
            panelCliPF.setVisible(false);
            
            //txtRG.setVisible(false);
            //txtCPF.setVisible(false);
            //
            //txtRazaoSocial.setVisible(true);
            //txtNomeFantasia.setVisible(true);
            //txtCNPJ.setVisible(true);
            //txtIM.setVisible(true);
            //txtIE.setVisible(true);
            
        }
    }
   

}