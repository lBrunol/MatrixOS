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
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javax.swing.text.DefaultFormatterFactory;

/**
 *
 * @author CASA
 */
public class Cliente implements ActionListener{
    //Instância da classe Métodos auxliares
    MetodosAuxiliares auxiliar = new MetodosAuxiliares();
    //Instância da classe que monta a tela
    MontaInterfaces telaCliente = new MontaInterfaces("Gerenciamento de Clientes", "/imagens/clientes.png");
    ConexaoBanco conexao = new ConexaoBanco();
    
    //Atributos da classe relacionados ao banco
    private int cliCod;
    private String cliNome;
    private String cliEndereco;
    private int cliNumEndereco;
    private String cliComplemento;
    private String cliBairro;
    private int cliCep;
    private String cliCidade;
    private String cliEstado;
    private long cliTelefone;
    private long cliCelular;
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
    
    //
    private boolean rdbSelecionado = true;
   
    //construtor
    public  Cliente(){
        
        telaCliente.setTamanho(1000, 1000);
        telaCliente.setVisible(true); 
        
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
        telaCliente.setVisible(true);        
        //Adicionei as abas com o método addAbas e o panel para os botões com o método addPanelBotoes
        telaCliente.addAbas(panelCadastro, "Cadastro");
        telaCliente.addAbas(panelConsulta, "Consulta");
        telaCliente.addPanelBotoes(panelCadastro, panelBotoes);
        
        //Adicionei os botões dentro do panelBotoes
        telaCliente.addBotoes("Cadastrar", botCadastrar, panelBotoes);
        telaCliente.addBotoes("Limpar", botExcluir, panelBotoes);
        
        //Criei objetos do tipo icone com o caminho do icone para coloca-los nos botões 
        Icon iconeCadastrar = new ImageIcon(getClass().getResource("/imagens/salvar.png"));
        Icon iconeExcluir = new ImageIcon(getClass().getResource("/imagens/limpar.png"));
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
        telaCliente.addLabelTitulo("Cliente", panelCadastro);
       
        telaCliente.addDoisComponentes("Codigo", txtCodigo,"Nome",txtNome,panelCadastro);
        telaCliente.addUmComponente("Endereço",txtEndereco,panelCadastro);
        telaCliente.addQuatroComponentes( "Bairro", txtBairro, "Complemento", txtComplemento,"Estado", cboEstado,"Cidade",txtCidade, panelCadastro);
        telaCliente.addQuatroComponentes("CEP", txtCEP, "Telefone", txtTelefone, "Celular", txtCelular, "Data Cadastro", txtDataCadastro, panelCadastro);
        telaCliente.addUmComponente("Observação",txtObservacao,panelCadastro);
        
        //Troca de botões do radio
        telaCliente.addPanelComponentes(panelCadastro, panelCliPF);
        telaCliente.addPanelComponentes(panelCadastro, panelCliPJ);
        
        panelCliPJ.setVisible(false);
        rdbpf.setSelected(true);
        
        //Campos para PJ
        telaCliente.addQuatroComponentes("CNPJ", txtCNPJ, "Razão Social", txtRazaoSocial, "IE", txtIE, "IM", txtIM, panelCliPJ);
        //Campos para PF
        telaCliente.addDoisComponentes("RG", txtRG, "CPF", txtCPF, panelCliPF);
        
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
        telaCliente.addDoisComponentes("Pessoa Física",rdbpf,"Pessoa Jurídica",rdbpj,panelCadastro);
         
        telaCliente.addTabela(tblClientes, panelConsulta);
        ConexaoBanco teste=new ConexaoBanco();
        teste.preencheTabela(tabela, "select * from cliente");
        
        
        
    }

    @Override
    public void actionPerformed(ActionEvent botao) {
        if (botao.getSource() == botCadastrar) { 
            boolean ok ;
            ok = Cadastrar();
            if(ok){	
                try {
                    ResultSet rs;
                    conexao.executaProcedure("INSERT_CLIENTE ('" + this.cliNome + "', '" + this.cliEndereco + "', 10, '" + this.cliComplemento + "', '" + cliBairro + "' , " + cliCep + ", '" + this.cliCidade + "' , 'SP', " + this.cliTelefone + ", " + this.cliCelular + ", 'teste@teste', '" + auxiliar.hoje() + "', '" + this.cliObersavacao + "', 'F')");
                    
                    
                    if(rdbSelecionado){
                        CliPessoaFisica pf = new CliPessoaFisica();
                        pf.setCliCpf(Long.parseLong(auxiliar.removeCaracteresString(txtCPF.getText())));
                        pf.setCliRg(Integer.parseInt(auxiliar.removeCaracteresString(txtRG.getText())));

                        rs = conexao.executar("SELECT MAX(cliCodigo) FROM cliente");
                        rs.next();
                        pf.setCliCodigo(rs.getInt(1));
                        ok = pf.cadastrar();
                        if(ok){
                            JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso");
                        }
                    }
                                      
                    
                    auxiliar.limpaCampos(telaCliente.getListaComponentes());
                    
                }catch (SQLException b) {
                    JOptionPane.showMessageDialog(null, b.getMessage() + ". Ocorreu um erro de SQL. Por favor, entre em contato com administrador do sistema.");
                }
                catch (Exception b) {
                    JOptionPane.showMessageDialog(null,"Erro desconhecido. Por favor entre em contato com administrador do sistema. \n" + b.getMessage());
                }                                    
            }
        }
        
        if(botao.getSource() == rdbpf){
            rdbpj.setSelected(false);
            rdbpf.setSelected(true);
            panelCliPJ.setVisible(false);
            panelCliPF.setVisible(true);
            txtRG.setVisible(true);
            txtCPF.setVisible(true);

            txtRazaoSocial.setObrigatorio(false);
            txtNomeFantasia.setObrigatorio(false);
            txtCNPJ.setObrigatorio(false);
            txtIM.setObrigatorio(false);
            txtIE.setObrigatorio(false);
            
            rdbSelecionado = true;
            
            
            
        }
        if(botao.getSource() == rdbpj){
            rdbpf.setSelected(false);
            rdbpj.setSelected(true);
            
            panelCliPJ.setVisible(true);
            panelCliPF.setVisible(false);
            
            txtRG.setVisible(false);
            txtCPF.setVisible(false);
            
            txtRazaoSocial.setVisible(true);
            txtNomeFantasia.setVisible(true);
            txtCNPJ.setVisible(true);
            txtIM.setVisible(true);
            txtIE.setVisible(true);
            
            rdbSelecionado = false;
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
    if(auxiliar.validaCampos(telaCliente.getListaComponentes())){ 
            this.cliCod = Integer.parseInt(txtCodigo.getText());
            this.cliNome= txtNome.getText();
            this.cliEndereco= txtEndereco.getText();
            this.cliBairro= txtBairro.getText();
            this.cliCidade= txtCidade.getText();
            this.cliDataCadastro=  txtDataCadastro.getText();
            
            
            //ComboItem comboItem = (ComboItem) cboEstado.getSelectedItem();
            //this.cliCod= Integer.parseInt(comboItem.getId());
            
            this.cliEmail=  txtEmail.getText();
            this.cliCep = Integer.parseInt(auxiliar.removeCaracteresString(txtCEP.getText()));
            this.cliCelular = Long.parseLong(auxiliar.removeCaracteresString(txtCelular.getText()));
            this.cliTelefone = Long.parseLong(auxiliar.removeCaracteresString(txtTelefone.getText()));
            this.cliObersavacao = txtObservacao.getText();
            this.cliComplemento = txtComplemento.getText();
            
            
             
            //CliPessoaJuridica pj =new CliPessoaJuridica();
             //pj.setIntIE(Integer.parseInt(txtIE.getText()));
             //pj.setIntIM(Integer.parseInt(txtIM.getText()));
             //pj.setStrNomeFantasia(txtNomeFantasia.getText());
             //pj.setStrRazaoSocial(txtRazaoSocial.getText());
           
            return true;
            
        }else{            
           return false;
        }
    }
    
     
    public boolean alterar() {
        if(auxiliar.validaCampos(telaCliente.getListaComponentes())){            
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
            
              
            CliPessoaFisica pf = new CliPessoaFisica();
             pf.setCliCpf(Integer.parseInt(txtCPF.getText()));
             pf.setCliRg(Integer.parseInt(txtRG.getText()));
             
            CliPessoaJuridica pj =new CliPessoaJuridica();
             pj.setIntIE(Integer.parseInt(txtIE.getText()));
             pj.setIntIM(Integer.parseInt(txtIM.getText()));
             pj.setStrNomeFantasia(txtNomeFantasia.getText());
             pj.setStrRazaoSocial(txtRazaoSocial.getText());
           
            
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
        
        
        
         }
        
        
      }
           

