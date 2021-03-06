/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplicacao;



import Core.ConexaoBanco;
import Core.MetodosAuxiliares;
import Core.MontaInterfaces;
import Core.PComboBox;
import Core.PFormattedTextField;
import Core.PTextField;
import Core.Sessao;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
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
    MontaInterfaces telaCliente = new MontaInterfaces("Gerenciamento de Clientes", "/imagens/clientes.png");
    ConexaoBanco conexao = new ConexaoBanco();
    
    //Atributos da classe relacionados ao banco
    private int cliCod;
    private String cliNome;
    private String cliEndereco;
    private String cliNumEndereco;
    private String cliComplemento;
    private String cliBairro;
    private String cliCep;
    private String cliCidade;
    private String cliEstado;
    private String cliTelefone;
    private String cliCelular;
    private String cliEmail;
    private String cliObersavacao;
    private String cliTipo;
    
    
    
    //Panels
    private JPanel panelConsulta = new JPanel(new GridBagLayout());
    private JPanel panelCadastro = new JPanel(new GridBagLayout());
    private JPanel panelCliPF = new JPanel(new GridBagLayout());
    private JPanel panelCliPJ = new JPanel(new GridBagLayout());
    private JPanel panelBotoesCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoesAlteracao = new JPanel(new GridBagLayout());
    

    
    
    //Caixas de texto
    private PTextField txtNome =  new PTextField();
    private PTextField txtEndereco =  new PTextField();
    private PTextField txtBairro =  new PTextField();
    private PTextField txtCidade =  new PTextField();
    private PComboBox cboEstado=  new PComboBox();
    private PTextField txtEmail =  new PTextField();
    private PFormattedTextField txtCEP =  new PFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_CEP));
    private PFormattedTextField txtCelular = new PFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_CELULAR));
    private PFormattedTextField txtTelefone =  new PFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_TELEFONE));
    private PTextField txtObservacao = new PTextField();
    private PTextField txtComplemento=  new PTextField();
    private JRadioButton rdbpf = new JRadioButton();
    private JRadioButton rdbpj = new JRadioButton();
    private PTextField txtNumEndereco =  new PTextField();
    
    //Campos para Cliente Jurídico
    private PTextField txtRazaoSocial = new PTextField();
    private PTextField txtNomeFantasia = new PTextField();
    private PTextField txtIM = new PTextField();
    private PTextField txtIE = new PTextField();
    private PFormattedTextField txtCNPJ = new PFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_CNPJ));
    
    //Campos para Cliente Físico
    private PFormattedTextField txtCPF = new  PFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_CPF));
    private PFormattedTextField txtRG = new PFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_RG));
   
   //Criação dos objetos, imprescindível criar ao menos um JPanel para servir de aba no formulário
    private PFormattedTextField txtDataAbertura = new PFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_DATA));
    
    //Botões
    private JButton botCadastrar = new JButton();
    private JButton botLimpar = new JButton();
    private JButton botInserir = new JButton();
    private JButton botExcluir = new JButton();
    private JButton botAlterarRegistro = new JButton();
    
    //Tabela
    DefaultTableModel tabela = new DefaultTableModel();
    private JTable tblClientes = new JTable(tabela){public boolean isCellEditable(int row,int column){return false;}};
    
    //
    private boolean rdbSelecionado = true;
   
    //construtor
    public  Cliente(){
        
        
        
        this.atribuiIcones();
        this.iniciaComponentes();
        this.setaNomes(); 
        this.preencheTabela();
        this.adicionaEventos();
        telaCliente.setTamanho(1000, 722);
        telaCliente.setMaximizado(true);
        telaCliente.setVisible(true); 
               
       
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
        telaCliente.addPanelBotoes(panelCadastro, panelBotoesCadastro); 
        telaCliente.addPanelBotoes(panelCadastro, panelBotoesAlteracao);
        
        //Adicionei os botões dentro do panelBotoes
        telaCliente.addBotoes("Cadastrar", botCadastrar, panelBotoesCadastro);
        telaCliente.addBotoes("Limpar", botLimpar, panelBotoesCadastro);
        telaCliente.addBotoes("Modo Inserir", botInserir,panelBotoesAlteracao);
        telaCliente.addBotoes("Excluir Registro", botExcluir,panelBotoesAlteracao);
        telaCliente.addBotoes("Alterar Registro", botAlterarRegistro, panelBotoesAlteracao);        

        
        //Deixa visível o panel de botões de cadastro
        panelBotoesAlteracao.setVisible(false);
        panelBotoesCadastro.setVisible(true);
        
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
        telaCliente.addLabelTitulo("Cliente", panelCadastro);
       
        telaCliente.addUmComponente("Nome",txtNome,panelCadastro);
        telaCliente.addDoisComponentes("Número Endereço",txtNumEndereco,"Endereço",txtEndereco,panelCadastro);
        telaCliente.addQuatroComponentes( "Bairro", txtBairro, "Complemento", txtComplemento,"UF", cboEstado,"Cidade",txtCidade, panelCadastro);
        telaCliente.addTresComponentes("CEP", txtCEP, "Telefone", txtTelefone, "Celular", txtCelular, panelCadastro);
        telaCliente.addUmComponente("Email:",txtEmail,panelCadastro);
        telaCliente.addUmComponente("Observação",txtObservacao,panelCadastro);
        
        //Troca de botões do radio
        telaCliente.addPanelComponentes(panelCadastro, panelCliPF);
        telaCliente.addPanelComponentes(panelCadastro, panelCliPJ);
        
        panelCliPJ.setVisible(false);
        rdbpf.setSelected(true);
        
        //Campos para PJ
        telaCliente.addCincoComponentes("CNPJ", txtCNPJ, "Razão Social", txtRazaoSocial, "Nome Fantasia", txtNomeFantasia, "IE", txtIE, "IM", txtIM, panelCliPJ);
        //Campos para PF
        telaCliente.addDoisComponentes("RG", txtRG, "CPF", txtCPF, panelCliPF);
        
        rdbpf.addActionListener(this);
        rdbpj.addActionListener(this);        
      
        txtRG.setObrigatorio(true);
        txtCPF.setObrigatorio(true);
        
        txtCNPJ.setObrigatorio(false);
        txtNomeFantasia.setObrigatorio(false);
        txtIE.setObrigatorio(false);
        txtIM.setObrigatorio(false);
        txtRazaoSocial.setObrigatorio(false);
       
        
         //Definição do combobox de UF.
        
        cboEstado.addItem("");
        cboEstado.addItem("AC");                             
        cboEstado.addItem("AL");
        cboEstado.addItem("AP");
        cboEstado.addItem("AM");
        cboEstado.addItem("BA");
        cboEstado.addItem("CE");
        cboEstado.addItem("DF");
        cboEstado.addItem("ES");
        cboEstado.addItem("GO");
        cboEstado.addItem("MA");
        cboEstado.addItem("MT");                             
        cboEstado.addItem("MS");
        cboEstado.addItem("MG");
        cboEstado.addItem("PA");
        cboEstado.addItem("PB");
        cboEstado.addItem("PR");
        cboEstado.addItem("PE");
        cboEstado.addItem("PI");
        cboEstado.addItem("RJ");
        cboEstado.addItem("RN");
        cboEstado.addItem("RS");                             
        cboEstado.addItem("RO");
        cboEstado.addItem("RR");
        cboEstado.addItem("SC");
        cboEstado.addItem("SP");
        cboEstado.addItem("SE");
        cboEstado.addItem("TO"); 
        
       /* cboEstado = new javax.swing.JComboBox(); 
    
        cboEstado.setFont(new java.awt.Font("Dialog", 1, 16)); 
        cboEstado.addItem(new javax.swing.DefaultComboBoxModel(new String[] { "", "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RS", "SC", "SE", "SP", "TO" })); 
        cboEstado.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED)); */
        
        
              
        
       //Botão Radio Button
        telaCliente.addDoisComponentes("Pessoa Física",rdbpf,"Pessoa Jurídica",rdbpj,panelCadastro);
         
        telaCliente.addTabela(tblClientes, panelConsulta);

        Sessao sessao = Sessao.getInstance();
        if(sessao.isAdm() == false){
            botAlterarRegistro.setEnabled(false);
            botExcluir.setEnabled(false);
        }
        
        
    }//Fim do inicia componentes
          
    public void mostraBotoesAlteracao() {
        panelBotoesAlteracao.setVisible(true);
        panelBotoesCadastro.setVisible(false);
    }

                        
    
    public void mostraBotoesCadastro() {
        panelBotoesAlteracao.setVisible(false);
        panelBotoesCadastro.setVisible(true);
        txtDataAbertura.setText(auxiliar.hoje());
    } 
    
    //falta a instrução do conexao.preencheTabela
    public void preencheTabela(){
        try {
            conexao.preencheTabela(tabela, "CONSULTA_CLIENTE(0)");            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage()+ "\n" + e.getMessage());
        }
    }
    
          
        public void setaNomes(){
            txtNome.setName("Nome");
            txtEndereco.setName("Endereço");
            txtBairro.setName("Bairro");
            txtCidade.setName("Cidade");
            
            //PF
            txtRG.setName("RG");
            txtCPF.setName("CPF");
            
            //PJ
            txtCNPJ.setName("CNPJ");
            txtRazaoSocial.setName("Razão Social");
            txtIM.setName("Inscrição Municipal");
            txtIE.setName("Inscrição Estadual");
            txtNomeFantasia.setName("Nome Fantasia");
           
            cboEstado.setName("Estado");
            txtEmail.setName("Email");
            txtCEP.setName("CEP");
            txtCelular.setName("Celular");
            txtTelefone.setName("Telefone");
            txtObservacao.setName("Observação");
            txtComplemento.setName("Complemento");
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
        //na mão
        //conexao.preencheCombo(cboEstado, "SELECT cliCodigo, cliEstado FROM cliente");
    }
     
     //metodos relacionados aos atributos da interface
    public boolean cadastrar(){
    if(auxiliar.validaCampos(telaCliente.getListaComponentes())){ 
            this.cliNome= txtNome.getText();
            this.cliEndereco= txtEndereco.getText();
            this.cliNumEndereco = txtNumEndereco.getText();
            this.cliBairro= txtBairro.getText();
            this.cliCidade= txtCidade.getText();
            this.cliEstado = cboEstado.getSelectedItem().toString();
            this.cliEmail=  txtEmail.getText();
            this.cliCep = auxiliar.removeCaracteresString(txtCEP.getText());
            this.cliCelular = auxiliar.removeCaracteresString(txtCelular.getText());
            this.cliTelefone = auxiliar.removeCaracteresString(txtTelefone.getText());
            this.cliObersavacao = txtObservacao.getText();
            this.cliComplemento = txtComplemento.getText();
           
           
            return true;
            
        }else{            
           return false;
        }
    }
    
     
    public boolean alterar() {
        if(auxiliar.validaCampos(telaCliente.getListaComponentes())){            
      
            this.cliNome= txtNome.getText();
            this.cliEndereco= txtEndereco.getText();
            this.cliNumEndereco = txtNumEndereco.getText();
            this.cliBairro= txtBairro.getText();
            this.cliCidade= txtCidade.getText();
            this.cliEstado = cboEstado.getSelectedItem().toString();
            this.cliEmail=  txtEmail.getText();
            this.cliCep = auxiliar.removeCaracteresString(txtCEP.getText());
            this.cliCelular = auxiliar.removeCaracteresString(txtCelular.getText());
            this.cliTelefone = auxiliar.removeCaracteresString(txtTelefone.getText());
            this.cliObersavacao = txtObservacao.getText();
            this.cliComplemento = txtComplemento.getText();
          
           
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
        
        tblClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                 //dois cliques no mouse---aciona             
                if (me.getClickCount() == 2) {                    
                    try {
                        
                        JTable table =(JTable) me.getSource();
                        Point p = me.getPoint();  
                        int row = table.rowAtPoint(p);
                        
                        //Pega o valor da primeira coluna da tabela e joga no código
                        cliCod = Integer.parseInt(tblClientes.getValueAt(row, 0).toString());
                        
                        ResultSet rs;
                        //Faz select com valor do código que foi pego na tabela
                        rs = conexao.executaProcedureSelect("CONSULTA_CLIENTE(" + cliCod + ")");
                        rs.next();                    
                        txtNome.setText(rs.getString(2));
                        txtEndereco.setText(rs.getString(3));
                        txtNumEndereco.setText(rs.getString(4));
                        txtComplemento.setText(rs.getString(5));
                        txtBairro.setText(rs.getString(6));
                        txtCEP.setText(rs.getString(7));
                        txtCidade.setText(rs.getString(8));
                        cboEstado.setSelectedItem(rs.getString(9));
                        txtTelefone.setText(rs.getString(10));
                        txtCelular.setText(rs.getString(11));
                        txtEmail.setText(rs.getString(12));
                        txtObservacao.setText(rs.getString(14));
                        
                        //Verifica se o campo cliTipo é J ou F para fazer o select nas tabelas especializadas
                        
                        if("J".equals(rs.getString(15).toUpperCase())){
                            
                            rs = conexao.executaProcedureSelect("CONSULTA_CLIPESSOAJURIDICA(" + cliCod + ")"); 
                           
                            rs.next();
                            txtRazaoSocial.setText(rs.getString(1));
                            txtNomeFantasia.setText(rs.getString(2));
                            txtCNPJ.setText(rs.getString(3));
                            txtIM.setText(rs.getString(4));
                            txtIE.setText(rs.getString(5));
                            
                            cliTipo = "J";
                            rdbSelecionado = false;
                            
                            panelCliPF.setVisible(false);
                            panelCliPJ.setVisible(true);
                            
                            txtRazaoSocial.setObrigatorio(true);
                            txtNomeFantasia.setObrigatorio(true);
                            txtCNPJ.setObrigatorio(true);
                            txtIM.setObrigatorio(true);
                            txtIE.setObrigatorio(true);
                            
                            txtRG.setObrigatorio(false);
                            txtCPF.setObrigatorio(false);
                            
                            rdbpj.setSelected(true);
                            rdbpf.setSelected(false);
                            
                            rs.close();
                        }else if("F".equals(rs.getString(15).toUpperCase())){
                            
                            rs = conexao.executaProcedureSelect("CONSULTA_CLIPESSOAFISICA(" + cliCod + ")");
                            
                            rs.next();
                            txtRG.setText(rs.getString(1));
                            txtCPF.setText(rs.getString(2));
                            cliTipo = "F";
                            rdbSelecionado = true;
                        
                            panelCliPF.setVisible(true);
                            panelCliPJ.setVisible(false);
                            
                            txtRazaoSocial.setObrigatorio(false);
                            txtNomeFantasia.setObrigatorio(false);
                            txtCNPJ.setObrigatorio(false);
                            txtIM.setObrigatorio(false);
                            txtIE.setObrigatorio(false);
                            
                            txtRG.setObrigatorio(true);
                            txtCPF.setObrigatorio(true);
                            
                            rdbpj.setSelected(false);
                            rdbpf.setSelected(true);
                            
                            rs.close();
                        }else {
                            throw new IllegalArgumentException("Este cliente foi cadastrado de forma incorreta. Por favor, entre em contato com administrador do sistema.");
                        }

                        mostraBotoesAlteracao();
                        telaCliente.getTabbedPane().setSelectedIndex(0);
                        rs.close();
                    }             
                    catch (SQLException b) {
                        JOptionPane.showMessageDialog(null, b.getMessage() + ". Ocorreu um erro de SQL. Por favor, entre em contato com administrador do sistema.");
                    }
                    
                    catch (Exception b) {
                        JOptionPane.showMessageDialog(null,"Erro desconhecido. Por favor entre em contato com administrador do sistema. \n" + b.getMessage());
                    }
                }
            }
        });
        
    }
 
      
      
    //Eventos
    @Override
    public void actionPerformed(ActionEvent botao) {
        
        boolean ok;
        
        //cadastrar
        if (botao.getSource() == botCadastrar) {             
            ok = cadastrar();
            if(ok){	
                try {
                    
                    this.cliTipo = rdbSelecionado == true ? "F" : "J";
                    
                    ResultSet rs;
                    conexao.executaProcedure("INSERT_CLIENTE ('" + this.cliNome + "', '" + this.cliEndereco + "', '" +this.cliNumEndereco+"','"+ this.cliComplemento + "', '" + cliBairro + "' , '" + cliCep + "', '" + this.cliCidade + "' , '" + this.cliEstado + "', '" + this.cliTelefone + "', '" + this.cliCelular + "' ,'" + this.cliEmail+"','"+ auxiliar.hoje() + "', '" + this.cliObersavacao + "','" + this.cliTipo +"' )");
                    
                    //pessoa física
                    if(rdbSelecionado == true){
                        CliPessoaFisica pf = new CliPessoaFisica();
                        pf.setCliCpf(auxiliar.removeCaracteresString(txtCPF.getText()));
                        pf.setCliRg(auxiliar.removeCaracteresString(txtRG.getText()));

                        rs = conexao.executar("SELECT MAX(cliCodigo) FROM cliente");
                        rs.next();
                        pf.setCliCodigo(rs.getInt(1));
                        ok = pf.cadastrar();
                        
                        if(ok){
                            JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso");
                           
                        }
                    }                        
                    //pessoa jurídica
                    else {
                        CliPessoaJuridica pj = new CliPessoaJuridica();
                        pj.setIntIE(txtIE.getText());
                        pj.setIntIM(txtIM.getText());
                        pj.setStrNomeFantasia(txtNomeFantasia.getText());
                        pj.setStrRazaoSocial(txtRazaoSocial.getText());
                        pj.setLongCnpj(auxiliar.removeCaracteresString(txtCNPJ.getText()));

                        rs = conexao.executar("SELECT MAX(cliCodigo) FROM cliente");
                        rs.next();

                        pj.setCliCodigo(rs.getInt(1));
                        ok = pj.cadastrar();
                        
                        if(ok){
                            JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso TO AQUI");
                        } 
                        
                            
                    }
                    auxiliar.limpaCampos(telaCliente.getListaComponentes());
                    this.preencheTabela();
                    
                }catch (SQLException b) {
                    JOptionPane.showMessageDialog(null, b.getMessage() + ". Ocorreu um erro de SQL. Por favor, entre em contato com administrador do sistema.");
                }
                catch (Exception b) {
                    JOptionPane.showMessageDialog(null,"Erro desconhecido. Por favor entre em contato com administrador do sistema. \n" + b.getMessage());
                }                                    
            }
        }
        //deletar
        if (botao.getSource() == botExcluir) {

            ok = deletar();
            if(ok){	
                try {                   
                     //pessoa física
                     if(rdbSelecionado == true){

                            CliPessoaFisica pf = new CliPessoaFisica();

                            pf.setCliCodigo(cliCod);
                            ok = pf.deletar();
                        if(ok){
                            JOptionPane.showMessageDialog(null, "Dados deletados com sucesso");
                            
                        } }
                      //pessoa jurídica
                     else {
                            CliPessoaJuridica pj = new CliPessoaJuridica();
                           
                            pj.setCliCodigo(cliCod);
                            ok = pj.deletar();
                        if(ok){
                            JOptionPane.showMessageDialog(null, "Dados deletados com sucesso");
                            
                        } 

                     }
                    conexao.executaProcedure("DELETE_CLIENTE(" + this.cliCod + ")");
                    txtDataAbertura.setText(auxiliar.hoje());
                    auxiliar.limpaCampos(telaCliente.getListaComponentes());
                    this.mostraBotoesCadastro();
                    this.preencheTabela();

                }catch (SQLException b) {
                    JOptionPane.showMessageDialog(null, b.getMessage() + ". Ocorreu um erro de SQL. Por favor, entre em contato com administrador do sistema.");
                }
                catch (Exception b) {
                    JOptionPane.showMessageDialog(null,"Erro desconhecido. Por favor entre em contato com administrador do sistema. \n" + b.getMessage());
                }           
            }
        }
            
        //inserir
        if (botao.getSource() == botInserir) {
            auxiliar.limpaCampos(telaCliente.getListaComponentes());
            this.mostraBotoesCadastro();
        }
        //limpar
        if (botao.getSource() == botLimpar) {
           auxiliar.limpaCampos(telaCliente.getListaComponentes());
        }
        //alterar
        if (botao.getSource() == botAlterarRegistro) {            
            ok = alterar();
            if(ok){	
                try {
                    String hoje = auxiliar.hoje();
                    conexao.executaProcedure("UPDATE_CLIENTE (" + this.cliCod + " ,'"  + this.cliNome + "', '" + this.cliEndereco + "', '" +this.cliNumEndereco+"','"+ this.cliComplemento + "', '" + cliBairro + "' , '" + cliCep + "', '" + this.cliCidade + "' , 'SP' , '" + this.cliTelefone + "', '" + this.cliCelular + "' ,'" + this.cliEmail+"','"+ auxiliar.hoje() + "', '" + this.cliObersavacao + "','" + this.cliTipo +"' )");
                   
                    ResultSet rs;
                    //pessoa física
                    if(rdbSelecionado == true){
                        CliPessoaFisica pf = new CliPessoaFisica();
                        pf.setCliCpf(auxiliar.removeCaracteresString(txtCPF.getText()));
                        pf.setCliRg(auxiliar.removeCaracteresString(txtRG.getText()));
                    
                        
                        pf.setCliCodigo(cliCod);
                        ok = pf.alterar();
                       if(ok){
                           JOptionPane.showMessageDialog(null, "Dados alterados com sucesso");
                          
                       } 
                    }
                    //pessoa jurídica
                    else {
                        CliPessoaJuridica pj = new CliPessoaJuridica();
                        pj.setIntIE(txtIE.getText());
                        pj.setIntIM(txtIM.getText());
                        pj.setStrNomeFantasia(txtNomeFantasia.getText());
                        pj.setStrRazaoSocial(txtRazaoSocial.getText());
                        pj.setLongCnpj(auxiliar.removeCaracteresString(txtCNPJ.getText()));
                  
                        pj.setCliCodigo(cliCod);
                        ok = pj.alterar();
                        
                        if(ok){
                            JOptionPane.showMessageDialog(null, "Dados  alterados com sucesso");
                           
                            
                        } 

                    }
                  
                    txtDataAbertura.setText(auxiliar.hoje());
                    auxiliar.limpaCampos(telaCliente.getListaComponentes());
                    this.mostraBotoesCadastro();
                    this.preencheTabela();
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
            
            txtRG.setObrigatorio(true);
            txtCPF.setObrigatorio(true);

            txtRazaoSocial.setObrigatorio(false);
            txtNomeFantasia.setObrigatorio(false);
            txtCNPJ.setObrigatorio(false);
            txtIM.setObrigatorio(false);
            txtIE.setObrigatorio(false);

            
            rdbSelecionado = true;
        
    }
     else   if(botao.getSource() == rdbpj){
            
         
            rdbpf.setSelected(false);
            rdbpj.setSelected(true);
            
            panelCliPJ.setVisible(true);
            panelCliPF.setVisible(false);
            
            txtRG.setObrigatorio(false);
            txtCPF.setObrigatorio(false);
            
            txtRazaoSocial.setObrigatorio(true);
            txtNomeFantasia.setObrigatorio(true);
            txtCNPJ.setObrigatorio(true);
            txtIM.setObrigatorio(true);
            txtIE.setObrigatorio(true);
            
            rdbSelecionado = false;
        }
      }
 
   
}

