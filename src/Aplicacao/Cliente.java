/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplicacao;



import Core.ComboItem;
import Core.ConexaoBanco;
import Core.MetodosAuxiliares;
import Core.MontaInterfaces;
import Core.PFormattedTextField;
import Core.PTextField;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JRadioButton;

/**
 *
 * @author CASA
 */
public class Cliente implements ActionListener{
    //Instância da classe Métodos auxliares
    MetodosAuxiliares auxiliar = new MetodosAuxiliares();
    //Instância da classe que monta a tela
    MontaInterfaces telaOS = new MontaInterfaces("Gerenciamento de Clientes", "/imagens/clientes.png");
    ConexaoBanco conexao = new ConexaoBanco();
    
    //Atributos da classe relacionados ao banco
    private int cliCod;
    private String cliNome;
    private String cliEndereco;
    private String cliComplemento;
    private String cliBairro;
    private int cliCep;
    private String cliCidade;
    private String cliEstado;
    private int cliTelefone;
    private int cliCelular;
    private String cliEmail;
    private String cliDataCadastro;
    private String cliObersavacao;
    
    
    
    //Panels
    private JPanel panelConsulta = new JPanel(new GridBagLayout());
    private JPanel panelCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoes = new JPanel(new GridBagLayout());
    private JPanel panelCliPF = new JPanel(new GridBagLayout());
    private JPanel panelCliPJ = new JPanel(new GridBagLayout());
    private JPanel panelBotoesCadastro = new JPanel(new GridBagLayout());
    

    
    
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
    
    //Campos para Cliente Jurídico
    private PTextField txtRazaoSocial = new PTextField();
    private PTextField txtNomeFantasia = new PTextField();
    private PTextField txtIM = new PTextField();
    private PTextField txtIE = new PTextField();
    private PFormattedTextField txtCNPJ = new PFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_CNPJ));
    
    //Campos para Cliente Físico
    private JTextField txtCPF = new  JFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_CPF));
    private JFormattedTextField txtRG = new JFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_RG));
   
  
    
    //Botões
    private JButton botCadastrar = new JButton();
    private JButton botLimpar = new JButton();
    private JButton botInserir = new JButton();
    private JButton botExcluir = new JButton();
    private JButton botAlterarRegistro = new JButton();
    
    //Tabela
    DefaultTableModel tabela = new DefaultTableModel();
    private JTable tblClientes = new JTable(tabela);
   
    //construtor
    public  Cliente(){
        
        telaOS.setTamanho(1000, 1000);
        telaOS.setVisible(true); 
        
        this.atribuiIcones();
        this.iniciaComponentes();
        this.preencheCombos();
        this.setaNomes(); 
        
               
       
    }
    //Método Main
    public static void main(String[] args){
        Cliente cli = new Cliente();
         
        
    }
    
    
    public  void iniciaComponentes(){
        
      
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
        Icon iconeInserir = new ImageIcon(getClass().getResource("/imagens/adicionar.png"));
        Icon iconeAlterar= new ImageIcon(getClass().getResource("/imagens/alterar.png"));
        Icon iconeLimpar = new ImageIcon(getClass().getResource("/imagens/limpar.png"));
        
        //Seta os icones dos botões
        botCadastrar.setIcon(iconeCadastrar);
        botExcluir.setIcon(iconeExcluir);
        botInserir.setIcon(iconeInserir);
        botAlterarRegistro.setIcon(iconeAlterar);
        botLimpar.setIcon(iconeLimpar);
        
       
        //Adiciona os componentes na tela
        telaOS.addLabelTitulo("Cliente", panelCadastro);
       
        telaOS.addDoisComponentes("Codigo", txtCodigo,"Nome",txtNome,panelCadastro);
        telaOS.addUmComponente("Endereço",txtEndereco,panelCadastro);
        telaOS.addQuatroComponentes( "Bairro", txtBairro, "Complemento", txtComplemento,"Estado", cboEstado,"Cidade",txtCidade, panelCadastro);
        telaOS.addQuatroComponentes("CEP", txtCEP, "Telefone", txtTelefone, "Celular", txtCelular, "Data Cadastro", txtDataCadastro, panelCadastro);
        telaOS.addUmComponente("Observação",txtObservacao,panelCadastro);
        
        //Troca de botões do radio
        telaOS.addPanelComponentes(panelCadastro, panelCliPF);
        telaOS.addPanelComponentes(panelCadastro, panelCliPJ);
        
        panelCliPJ.setVisible(false);
        rdbpf.setSelected(true);
        
        //Campos para PJ
        telaOS.addQuatroComponentes("CNPJ", txtCNPJ, "Razão Social", txtRazaoSocial, "IE", txtIE, "IM", txtIM, panelCliPJ);
        //Campos para PF
        telaOS.addDoisComponentes("RG", txtRG, "CPF", txtCPF, panelCliPF);
        
        rdbpf.addActionListener(this);
        rdbpj.addActionListener(this);
        
        botCadastrar.addActionListener(this);
        botExcluir.addActionListener(this);
        botInserir.addActionListener(this);
        botAlterarRegistro.addActionListener(this);
        botLimpar.addActionListener(this);
        
         //THIS IS CALLED: GAMBIARRA !!!
        
        cboEstado.addItem("");
        cboEstado.addItem("Acre");                             
        cboEstado.addItem("Alagoas");
        cboEstado.addItem("Amapá");
        cboEstado.addItem("Amazonas");
        cboEstado.addItem("Bahia");
        cboEstado.addItem("Ceará");
        cboEstado.addItem("Distrito Federal");
        cboEstado.addItem("Espírito Santo");
        cboEstado.addItem("Goiás");
        cboEstado.addItem("Maranhão");
        cboEstado.addItem("Mato Grosso");                             
        cboEstado.addItem("Mato Grosso do Sul");
        cboEstado.addItem("Minas Gerais");
        cboEstado.addItem("Pará");
        cboEstado.addItem("Paraíba");
        cboEstado.addItem("Paraná");
        cboEstado.addItem("Pernambuco");
        cboEstado.addItem("Piauí");
        cboEstado.addItem("Rio de Janeiro");
        cboEstado.addItem("Rio Grande do Norte");
        cboEstado.addItem("Rio Grande do Sul");                             
        cboEstado.addItem("Rondônia");
        cboEstado.addItem("Roraima");
        cboEstado.addItem("Santa Catarina");
        cboEstado.addItem("São Paulo");
        cboEstado.addItem("Sergipe");
        cboEstado.addItem("Tocantins"); 
        
       /* cboEstado = new javax.swing.JComboBox(); 
    
        cboEstado.setFont(new java.awt.Font("Dialog", 1, 16)); 
        cboEstado.addItem(new javax.swing.DefaultComboBoxModel(new String[] { "", "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RS", "SC", "SE", "SP", "TO" })); 
        cboEstado.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED)); */
        
        
              
        
       //Botão Radio Button
        telaOS.addDoisComponentes("Pessoa Física",rdbpf,"Pessoa Jurídica",rdbpj,panelCadastro);
         
        telaOS.addTabela(tblClientes, panelConsulta);
        ConexaoBanco teste=new ConexaoBanco();
        teste.preencheTabela(tabela, "select * from cliente");
        
        
        
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
          
        public void setaNomes(){
            txtCodigo.setName("Código");
            txtNome.setName("Nome");
            txtEndereco.setName("Endereço");
            txtBairro.setName("Bairro");
            txtCidade.setName("Cidade");
            txtDataCadastro.setName("Data de Cadastro");
            
           
            cboEstado.setName("Estado");
            txtEmail.setName("Email");
            txtCEP.setName("CEP");
            txtCelular.setName("Celular");
            txtTelefone.setName("Telefone");
            txtObservacao.setName("Observação");
            txtComplemento.setName("Complemento");
        }
        
         private void formataValoresTabela(){
         auxiliar.formataValorTabela(tblClientes, 3);
          }
       public void atribuiIcones() {
        Icon iconeCadastrar = new ImageIcon(getClass().getResource("/imagens/salvar.png"));
        Icon iconeExcluir = new ImageIcon(getClass().getResource("/imagens/excluir.png"));
        Icon iconeAlterar = new ImageIcon(getClass().getResource("/imagens/alterar.png"));
        Icon iconeInserir = new ImageIcon(getClass().getResource("/imagens/adicionar.png"));
        Icon iconeLimpar = new ImageIcon(getClass().getResource("/imagens/limpar.png"));


       //Seta os icones dos botões
        botAlterarRegistro.setIcon(iconeAlterar);
        botCadastrar.setIcon(iconeCadastrar);
        botExcluir.setIcon(iconeExcluir);
        botInserir.setIcon(iconeInserir);
        botLimpar.setIcon(iconeLimpar);
    }

          
     //Para tratar os combobox
     public void preencheCombos(){
        //Preenche as combobox
        cboEstado.addItem("");
        conexao.preencheCombo(cboEstado, "SELECT cliCodigo, cliEstado FROM cliente");
    }
    public boolean Cadastrar(){
    if(auxiliar.validaCampos(telaOS.getListaComponentes())){ 
            this.cliCod = Integer.parseInt(txtCodigo.getText());
            this.cliNome= txtNome.getText();
            this.cliEndereco= txtEndereco.getText();
            this.cliBairro= txtBairro.getText();
            this.cliCidade= txtCidade.getText();
            this.cliDataCadastro=  txtDataCadastro.getText();
            
            ComboItem comboItem = (ComboItem) cboEstado.getSelectedItem();
            this.cliCod= Integer.parseInt(comboItem.getId());
            
            this.cliEmail=  txtEmail.getText();
            this.cliCep = Integer.parseInt(txtCEP.getText());
            this.cliCelular = Integer.parseInt(txtCelular.getText());
            this.cliTelefone = Integer.parseInt(txtTelefone.getText());
            this.cliObersavacao = txtObservacao.getText();
            this.cliComplemento = txtComplemento.getText();
          
          
            return true;
            
        }else{            
           return false;
        }
    }
    
     
    public boolean alterar() {
        if(auxiliar.validaCampos(telaOS.getListaComponentes())){            
            //this.intCodigoOS = intCodigoOS;
            this.cliCod = Integer.parseInt(txtCodigo.getText());
            this.cliNome = txtNome.getText();
            this.cliEndereco= txtEndereco.getText();
            this.cliBairro =  txtBairro.getText();
            this.cliCidade =  txtCidade.getText();
            this.cliDataCadastro =  txtDataCadastro.getText();
          //this.cliEstado =  cboEstado.getText();
            this.cliEmail =  txtEmail.getText();
            this.cliCep = Integer.parseInt(txtCEP.getText());
            this.cliCelular = Integer.parseInt(txtCelular.getText());
            this.cliTelefone = Integer.parseInt(txtTelefone.getText());
            this.cliObersavacao= txtObservacao.getText();
            this.cliComplemento= txtComplemento.getText();
            
            ComboItem comboItem = (ComboItem) cboEstado.getSelectedItem();
            this.cliCod = Integer.parseInt(comboItem.getId());
          
           
            return true;
        }else{            
           return false; 
        }
    }
   
     public boolean deletar() {
        if(JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o registro?", "Confirmação", JOptionPane.YES_NO_OPTION) == 0){                                
            return true;                       
        }else{
            return false;
        }
    }
     
     
     
         public void adicionaEventos() {
        botCadastrar.addActionListener(this);
        botInserir.addActionListener(this);
        botExcluir.addActionListener(this);
        botLimpar.addActionListener(this);
        botAlterarRegistro.addActionListener(this);
        
        cboEstado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cboEstado.getSelectedItem() != ""){
                    ComboItem comboItem = (ComboItem) cboEstado.getSelectedItem();
                    cliCod= Integer.parseInt(comboItem.getId());                    
                }
            }
        });
        
      }
           

}