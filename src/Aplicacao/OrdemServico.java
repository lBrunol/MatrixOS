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
import Core.PComboBox;
import Core.PFormattedTextField;
import Core.PTextField;
import Core.PadraoFormulario;
import Core.Sessao;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;


/**
 *
 * @author CASA
 */
public class OrdemServico implements ActionListener, PadraoFormulario, FocusListener, MouseListener, KeyListener {
    //Instância da classe Métodos auxliares
    MetodosAuxiliares auxiliar = new MetodosAuxiliares();
    //Instância da classe que monta a tela
    MontaInterfaces telaOS = new MontaInterfaces("Gerenciamento de Ordem de Serviço", "/imagens/os.png");
    //Instância da classe de conexão com o banco
    ConexaoBanco conexao = new ConexaoBanco();
    
    //Criação dos objetos, imprescindível criar ao menos um JPanel para servir de aba no formulário
    private PFormattedTextField txtDataAbertura = new PFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_DATA));
    private PFormattedTextField txtDataFechamento = new PFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_DATA));
    private PTextField txtDescricaoOcorrencia = new PTextField();
    private PTextField txtValorTotal = new PTextField();

    //Clientes
    private PComboBox cboCliente = new PComboBox();
    private PTextField txtEndereco = new PTextField();
    private PTextField txtNumEndereco = new PTextField();
    private PTextField txtComplemento = new PTextField();
    private PFormattedTextField txtCEP = new PFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_CEP));
    private PTextField txtBairro = new PTextField();
    private PTextField txtCidade = new PTextField();
    private PTextField txtUF = new PTextField();
    private PFormattedTextField txtTelefone = new PFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_TELEFONE));
    private PFormattedTextField txtCPF = new PFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_CPF));
    private PFormattedTextField txtRG = new PFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_RG));

    //Funcionários
    private PComboBox cboFuncionario = new PComboBox();
    private PTextField txtCargo = new PTextField();

    //Serviços
    private PComboBox cboServico = new PComboBox();
    private PTextField txtValor = new PTextField();
    private PTextField txtQtde = new PTextField();
    private PTextField txtDescricaoServico = new PTextField();

    //Botões
    private JButton botCadastrar = new JButton();
    private JButton botLimpar = new JButton();
    private JButton botInserir = new JButton();
    private JButton botFaturarNotaFiscal = new JButton();
    private JButton botExcluir = new JButton();
    private JButton botAlterarRegistro = new JButton();

    DefaultTableModel tabela = new DefaultTableModel();

    //Tabela
    private JTable tblOrdemServico = new JTable(tabela){public boolean isCellEditable(int row,int column){return false;}};

    //Panels (Abas)
    private JPanel panelConsulta = new JPanel(new GridBagLayout());
    private JPanel panelCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoesCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoesAlteracao = new JPanel(new GridBagLayout());

    //Atributos da classe relacionados ao banco
    private int intCodigoOS;
    private int intCodigoCliente;
    private int intMatricula;
    private int intCodigoServico;
    private int intQtdeServico;
    private String strDataAbertura;
    private String strDataFechamento;
    private String strOcorrencia;
    private float fltValorTotal;
    private float fltValorDesconto;
    private float fltValorServico;

    //Construtor
    public OrdemServico() {
        
        this.iniciaComponentes();
        this.atribuiIcones();
        
        telaOS.setTamanho(1000, 600);
        telaOS.setMaximizado(true);
        this.preencheCombos();
        telaOS.setVisible(true);
        
        this.adicionaEventos();
        
        this.preencheTabela();
        this.setaNomes();      
        
    }
    //Método main
    public static void main(String args[]) {
        OrdemServico os = new OrdemServico();
    }
    
    public boolean verificaFatura(){        
        try {            
            ResultSet rs;
        
            rs = conexao.executar("SELECT COUNT(notCodigo) FROM notaFiscal WHERE ordCodigo = " + intCodigoOS);
            rs.next();

            if(rs.getInt(1) == 1){
                return true;
            }else {
                return false;
            }
            
        }catch (SQLException b) {
            JOptionPane.showMessageDialog(null, b.getMessage() + ". Ocorreu um erro de SQL. Por favor, entre em contato com administrador do sistema.");
        }
        catch (Exception b) {
            JOptionPane.showMessageDialog(null,"Erro desconhecido. Por favor entre em contato com administrador do sistema. \n" + b.getMessage());
        }
        
        return false;
        
    }
    
    @Override
    public void iniciaComponentes() {        

        telaOS.addAbas(panelCadastro, "Cadastro");
        telaOS.addPanelBotoes(panelCadastro, panelBotoesCadastro);
        telaOS.addPanelBotoes(panelCadastro, panelBotoesAlteracao);

        //Botões de cadastro
        telaOS.addBotoes("Cadastrar", botCadastrar, panelBotoesCadastro);
        telaOS.addBotoes("Limpar", botLimpar, panelBotoesCadastro);

        //Botões de alteração        
        telaOS.addBotoes("Faturar Nota Fiscal", botFaturarNotaFiscal, panelBotoesAlteracao);
        telaOS.addBotoes("Modo Inserir", botInserir, panelBotoesAlteracao);
        telaOS.addBotoes("Excluir Registro", botExcluir, panelBotoesAlteracao);
        telaOS.addBotoes("Alterar Registro", botAlterarRegistro, panelBotoesAlteracao);        

        //Adiciona os componentes na tela
        telaOS.addLabelTitulo("Ordem de Serviço", panelCadastro);
        telaOS.addTresComponentes("Data de Abertura", txtDataAbertura, "Data de Fechamento", txtDataFechamento, "Valor Total", txtValorTotal, panelCadastro);
        telaOS.addUmComponente("Descrição da Ocorrência*", txtDescricaoOcorrencia, panelCadastro);
        telaOS.addLabelTitulo("Cliente", panelCadastro);
        telaOS.addUmComponente("Selecione o cliente*", cboCliente, panelCadastro);
        telaOS.addQuatroComponentes("Endereço", txtEndereco, "N°", txtNumEndereco, "Complemento", txtComplemento, "CEP", txtCEP, panelCadastro);
        telaOS.addQuatroComponentes("Bairro", txtBairro, "Cidade", txtCidade, "UF ", txtUF, "Telefone", txtTelefone, panelCadastro);
        telaOS.addDoisComponentes("CPF/CNPJ", txtCPF, "RG/IM", txtRG, panelCadastro);
        telaOS.addLabelTitulo("Funcionário", panelCadastro);
        telaOS.addDoisComponentes("Selecione o Funcionário*", cboFuncionario, "Cargo", txtCargo, panelCadastro);
        telaOS.addLabelTitulo("Serviço", panelCadastro);
        telaOS.addQuatroComponentes("Selecione o Serviço*", cboServico, "Valor", txtValor, "Quantidade*", txtQtde, "Descrição", txtDescricaoServico, panelCadastro);
        telaOS.addAbas(panelConsulta, "Consulta");
        telaOS.addTabela(tblOrdemServico, panelConsulta);
        
        //Deixa visível o panel de botões de cadastro
        panelBotoesAlteracao.setVisible(false);
        panelBotoesCadastro.setVisible(true);
        
        txtEndereco.setEnabled(false);
        txtNumEndereco.setEnabled(false);
        txtComplemento.setEnabled(false);
        txtCEP.setEnabled(false);
        txtBairro.setEnabled(false);
        txtCidade.setEnabled(false);
        txtUF.setEnabled(false);
        txtTelefone.setEnabled(false);
        txtCPF.setEnabled(false);
        txtRG.setEnabled(false);
        txtCargo.setEnabled(false);
        txtValor.setEnabled(false);
        txtDescricaoServico.setEnabled(false);
        txtQtde.setEnabled(false);
        txtDataAbertura.setEnabled(false);
        txtValorTotal.setEnabled(false);
        
        botFaturarNotaFiscal.setEnabled(false);
        
        //Formata os valores em moeda
        txtDataAbertura.setText(auxiliar.hoje());
        txtDataFechamento.setObrigatorio(false);
        txtComplemento.setObrigatorio(false);
        
        Sessao sessao = Sessao.getInstance();
        if(sessao.isAdm() == false){
            botAlterarRegistro.setEnabled(false);
            botExcluir.setEnabled(false);
        }
    }
    
    @Override
    public void atribuiIcones(){
        //Cria objetos do tipo icone para coloca-los nos botões
        Icon iconeCadastrar = new ImageIcon(getClass().getResource("/imagens/salvar.png"));
        Icon iconeExcluir = new ImageIcon(getClass().getResource("/imagens/excluir.png"));
        Icon iconeAlterar = new ImageIcon(getClass().getResource("/imagens/alterar.png"));
        Icon iconeInserir = new ImageIcon(getClass().getResource("/imagens/adicionar.png"));
        Icon iconeFaturar = new ImageIcon(getClass().getResource("/imagens/faturar-nota-fiscal.png"));
        Icon iconeLimpar = new ImageIcon(getClass().getResource("/imagens/limpar.png"));

        //Seta os icones dos botões
        botAlterarRegistro.setIcon(iconeAlterar);
        botCadastrar.setIcon(iconeCadastrar);
        botExcluir.setIcon(iconeExcluir);
        botFaturarNotaFiscal.setIcon(iconeFaturar);
        botInserir.setIcon(iconeInserir);
        botLimpar.setIcon(iconeLimpar);
    }
    
    @Override
    public void preencheCombos(){
        //Preenche as combobox
        cboCliente.addItem("");
        cboFuncionario.addItem("");
        cboServico.addItem("");

        conexao.preencheCombo(cboCliente, "SELECT cliCodigo, cliNome FROM cliente ORDER BY cliCodigo");
        conexao.preencheCombo(cboFuncionario, "SELECT funMatricula, funNome FROM funcionario ORDER BY funMatricula");
        conexao.preencheCombo(cboServico, "SELECT svcCodigo, svcNome FROM servicos ORDER BY svcCodigo");
    }
    
    @Override
    public void adicionaEventos(){
        botCadastrar.addActionListener(this);
        botInserir.addActionListener(this);
        botExcluir.addActionListener(this);
        botLimpar.addActionListener(this);
        txtQtde.addFocusListener(this);
        txtQtde.addKeyListener(this);
        botAlterarRegistro.addActionListener(this);
        botFaturarNotaFiscal.addActionListener(this);
        
        cboCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                if(cboCliente.getSelectedItem() != ""){
                    ComboItem comboItem = (ComboItem) cboCliente.getSelectedItem();
                    intCodigoCliente = Integer.parseInt(comboItem.getId());
                    try {
                        ResultSet rs = conexao.executar("SELECT UPPER(cliTipo) FROM cliente WHERE cliCodigo = " + intCodigoCliente);
                        rs.next();
                        if("J".equals(rs.getString(1).toUpperCase())){                        
                            rs = conexao.executar("SELECT cliEndereco, cliNumEndereco, cliComplemento, cliCEP, cliBairro, cliCidade, cliUF, cliTelefone, cliCNPJ, cliIM FROM cliente INNER JOIN cliPessoaJuridica ON cliente.cliCodigo = cliPessoaJuridica.cliCodigo WHERE cliente.cliCodigo = " + intCodigoCliente);
                            rs.next();
                            txtCPF.setFormatterFactory(new DefaultFormatterFactory(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_CPF)));
                            txtRG.getFormatter().uninstall();
                        }else if("F".equals(rs.getString(1).toUpperCase())){
                            rs = conexao.executar("SELECT cliEndereco, cliNumEndereco, cliComplemento, cliCEP, cliBairro, cliCidade, cliUF, cliTelefone, cliCPF, cliRG FROM cliente INNER JOIN cliPessoaFisica ON cliente.cliCodigo = cliPessoaFisica.cliCodigo WHERE cliente.cliCodigo = " + intCodigoCliente);
                            rs.next();
                            txtCPF.setFormatterFactory(new DefaultFormatterFactory(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_CPF)));
                            txtRG.setFormatterFactory(new DefaultFormatterFactory(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_RG)));
                        }else{
                            throw new IllegalArgumentException("Este cliente foi cadastrado de forma incorreta. Por favor, entre em contato com administrador do sistema.");
                        }

                        txtEndereco.setText(rs.getString(1));
                        txtNumEndereco.setText(rs.getString(2));
                        txtComplemento.setText(rs.getString(3));
                        txtCEP.setText(rs.getString(4));
                        txtBairro.setText(rs.getString(5));
                        txtCidade.setText(rs.getString(6));
                        txtUF.setText(rs.getString(7));
                        txtTelefone.setText(rs.getString(8));
                        txtCPF.setText(rs.getString(9));
                        txtRG.setText(rs.getString(10));

                    } catch(IllegalArgumentException b){
                        JOptionPane.showMessageDialog(null, b.getCause());
                    }                
                    catch (SQLException b) {
                        b.printStackTrace();
                        JOptionPane.showMessageDialog(null, b.getMessage() + ". Ocorreu um erro de SQL. Por favor, entre em contato com administrador do sistema.");
                    }
                    catch (Exception b) {
                        JOptionPane.showMessageDialog(null,"Erro desconhecido. Por favor entre em contato com administrador do sistema. \n" + b.getMessage());
                    }
                }else{
                    txtEndereco.setText("");
                    txtNumEndereco.setText("");
                    txtComplemento.setText("");
                    txtCEP.setText("");
                    txtBairro.setText("");
                    txtCidade.setText("");
                    txtUF.setText("");
                    txtTelefone.setText("");
                    txtCPF.setText("");
                    txtRG.setText("");
                }
            }
        });
        
        cboFuncionario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cboFuncionario.getSelectedItem() != ""){
                    ComboItem comboItem = (ComboItem) cboFuncionario.getSelectedItem();
                    intMatricula = Integer.parseInt(comboItem.getId());
                    try {
                        ResultSet rs = conexao.executar("SELECT carDescricao FROM funcionario INNER JOIN cargos ON funcionario.carCodigo = cargos.carCodigo WHERE funcionario.funMatricula= " + intMatricula);
                        rs.next();                        
                        txtCargo.setText(rs.getString(1));
                        rs.close();
                    }             
                    catch (SQLException b) {
                        JOptionPane.showMessageDialog(null, b.getMessage() + ". Ocorreu um erro de SQL. Por favor, entre em contato com administrador do sistema.");
                    }
                    catch (Exception b) {
                        JOptionPane.showMessageDialog(null,"Erro desconhecido. Por favor entre em contato com administrador do sistema. \n" + b.getMessage());
                    }
                }else{
                    txtCargo.setText("");
                }
            }
        });
        
        cboServico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cboServico.getSelectedItem() != ""){
                    ComboItem comboItem = (ComboItem) cboServico.getSelectedItem();                   
                    intCodigoServico = Integer.parseInt(comboItem.getId());
                    try {
                        ResultSet rs = conexao.executar("SELECT svcValorHora, svcDescricao FROM servicos WHERE svcCodigo = " + intCodigoServico);
                        rs.next();                  

                        txtValor.setText(rs.getString(1));
                        txtDescricaoServico.setText(rs.getString(2));
                        txtQtde.setEnabled(true);
                        rs.close();
                    }             
                    catch (SQLException b) {
                        JOptionPane.showMessageDialog(null, b.getMessage() + ". Ocorreu um erro de SQL. Por favor, entre em contato com administrador do sistema.");
                    }
                    catch (Exception b) {
                        JOptionPane.showMessageDialog(null,"Erro desconhecido. Por favor entre em contato com administrador do sistema. \n" + b.getMessage());
                    }
                }else{
                    txtValor.setText("");
                    txtDescricaoServico.setText("");
                    txtQtde.setText("");
                    txtQtde.setEnabled(false);
                }
            }
        });
        
        tblOrdemServico.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                              
                if (me.getClickCount() == 2) {                    
                    try {
                        
                        JTable table =(JTable) me.getSource();
                        Point p = me.getPoint();  
                        int row = table.rowAtPoint(p);

                        intCodigoOS = Integer.parseInt(tblOrdemServico.getValueAt(row, 0).toString());
                        
                        ResultSet rs;
                        rs = conexao.executaProcedureSelect("CONSULTA_ORDEMSERVICO (" + intCodigoOS + ")");
                        rs.next();                    
                        txtDescricaoOcorrencia.setText(rs.getString(2));
                        txtDataAbertura.setText(auxiliar.formataData(rs.getDate(3)));
                        txtDataFechamento.setText(auxiliar.formataData(rs.getDate(4)));
                        txtValorTotal.setText(auxiliar.formataValor(rs.getFloat(5)));
                        
                        auxiliar.setSelectedValue(cboFuncionario, rs.getInt(7));
                        auxiliar.setSelectedValue(cboCliente,rs.getInt(8));                    
                        
                        rs.close();
                        
                        rs = conexao.executar("SELECT * FROM servicosOS WHERE ordCodigo =" + intCodigoOS);
                        rs.next();
                        auxiliar.setSelectedValue(cboServico,rs.getInt(4));
                        txtQtde.setText(rs.getString(2));

                        mostraBotoesAlteracao();
                        botFaturarNotaFiscal.setEnabled(false);
                        if(verificaFatura() == false){
                            botFaturarNotaFiscal.setEnabled(true);
                        }
                        telaOS.getTabbedPane().setSelectedIndex(0);
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

    @Override
    public void mostraBotoesAlteracao() {
        panelBotoesAlteracao.setVisible(true);
        panelBotoesCadastro.setVisible(false);
    }

    @Override
    public void mostraBotoesCadastro() {
        panelBotoesAlteracao.setVisible(false);
        panelBotoesCadastro.setVisible(true);
        txtDataAbertura.setText(auxiliar.hoje());
    }
    
    @Override
    public void preencheTabela(){
        try {
            conexao.preencheTabelaSelect(tabela, "SELECT ordCodigo, ordOcorrencia, ordDataAbertura, ordValorTotal, cliNome, funNome FROM cliente INNER JOIN (funcionario INNER JOIN ordemServico ON funcionario.funMatricula = ordemServico.funMatricula ) ON cliente.cliCodigo = ordemServico.cliCodigo ORDER BY ordCodigo");            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage()+ "\n" + e.getMessage());
        }
       formataValoresTabela();
    }
    
        @Override
        public void setaNomes(){
            txtEndereco.setName("Endereço");
            txtNumEndereco.setName("Número do endereço");
            txtComplemento.setName("Complemento");
            txtBairro.setName("Bairro");
            txtCEP.setName("CEP");
            txtCPF.setName("CPF");
            txtCargo.setName("Cargo");
            txtCidade.setName("Cidade");
            txtDataAbertura.setName("Data de Abertura");
            txtDataFechamento.setName("Data de Fechamento");
            txtDescricaoOcorrencia.setName("Descrição da Ocorrência");
            txtDescricaoServico.setName("Descrição do Serviço");
            txtQtde.setName("Quantidade do serviço");
            txtRG.setName("RG");
            txtTelefone.setName("Telefone");
            txtUF.setName("UF");
            txtValor.setName("Valor do Serviço");
            txtValorTotal.setName("Valor Total");     
            cboCliente.setName("Cliente");
            cboFuncionario.setName("Funcionário");
            cboServico.setName("Serviço");
        }
    
    private void formataValoresTabela(){
         auxiliar.formataValorTabela(tblOrdemServico, 3);
    }

    //Métodos herdados da interface PadraoFormulario
    @Override
    public boolean cadastrar() {
        if(auxiliar.validaCampos(telaOS.getListaComponentes())){ 
            this.strDataAbertura = txtDataAbertura.getText();
            this.strDataFechamento = txtDataFechamento.getText();
            this.strOcorrencia =  txtDescricaoOcorrencia.getText();
            this.fltValorTotal = auxiliar.calculaValorTotal(this.fltValorServico, this.intQtdeServico);
            this.intQtdeServico = Integer.parseInt(txtQtde.getText());         
            return true;
            
        }else{            
           return false;
        }
    }

    @Override
    public boolean alterar() {
        if(auxiliar.validaCampos(telaOS.getListaComponentes())){            
            this.strDataAbertura = txtDataAbertura.getText();
            this.strDataFechamento = txtDataFechamento.getText();
            this.strOcorrencia =  txtDescricaoOcorrencia.getText();
            this.fltValorTotal = auxiliar.calculaValorTotal(this.fltValorServico, this.intQtdeServico);
            this.intQtdeServico = Integer.parseInt(txtQtde.getText());
            
            ComboItem comboItem = (ComboItem) cboServico.getSelectedItem();
            this.intCodigoServico = Integer.parseInt(comboItem.getId());
            
            comboItem = (ComboItem) cboCliente.getSelectedItem();
            this.intCodigoCliente = Integer.parseInt(comboItem.getId());
            
            comboItem = (ComboItem) cboFuncionario.getSelectedItem();
            this.intMatricula = Integer.parseInt(comboItem.getId());
            return true;
        }else{            
           return false; 
        }
    }

    @Override
    public boolean deletar() {
        if(JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o registro?", "Confirmação", JOptionPane.YES_NO_OPTION) == 0){                                
            return true;                       
        }else{
            return false;
        }
    }
    
    //Eventos
    @Override
    public void actionPerformed(ActionEvent botao) {
        //Retorna qual o botão clicado e gera a ação
        boolean ok ;
        if (botao.getSource() == botCadastrar) {            
            ok = cadastrar();
            if(ok){	
                try {
                    String hoje = auxiliar.hoje();
                    ResultSet rs;
                    conexao.executaProcedure("INSERT_ORDEMSERVICO ('" + this.strOcorrencia + "','" + this.strDataAbertura + "', '" + this.strDataFechamento + "', " + this.fltValorTotal + ", " + this.fltValorDesconto + " , " + this.intMatricula + " , " + this.intCodigoCliente + " )");
                    rs = conexao.executar("SELECT MAX(ORDCODIGO) FROM ORDEMSERVICO");
                    rs.next();
                    intCodigoOS = rs.getInt(1);
                    conexao.executaProcedure("INSERT_SERVICOSOS ('"+ hoje + "', " + this.intQtdeServico + ", " + this.intCodigoOS + ", " + this.intCodigoServico + ")");
                    JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso");
                    
                    if(JOptionPane.showConfirmDialog(null, "Deseja Faturar está ordem de serviço?", "Faturar Ordem", JOptionPane.YES_NO_OPTION) == 0){
                        ConfirmaNotaFiscal cfn = new ConfirmaNotaFiscal(intCodigoOS);
                    }
                    auxiliar.limpaCampos(telaOS.getListaComponentes());
                    txtDataAbertura.setText(auxiliar.hoje());
                    botFaturarNotaFiscal.setEnabled(false);
                    this.preencheTabela();
                }catch (SQLException b) {
                    JOptionPane.showMessageDialog(null, b.getMessage() + ". Ocorreu um erro de SQL. Por favor, entre em contato com administrador do sistema.");
                }
                catch (Exception b) {
                    JOptionPane.showMessageDialog(null,"Erro desconhecido. Por favor entre em contato com administrador do sistema. \n" + b.getMessage());
                }                                    
            }
        }
        if (botao.getSource() == botExcluir) {
            ok = deletar();
            if(ok){	
                try {
                    conexao.executaProcedure("DELETE_SERVICOSOS(" + this.intCodigoOS + ")");
                    conexao.executaProcedure("DELETE_ORDEMSERVICO(" + this.intCodigoOS + ")");
                    JOptionPane.showMessageDialog(null, "Dados deletados com sucesso");
                    auxiliar.limpaCampos(telaOS.getListaComponentes());
                    txtDataAbertura.setText(auxiliar.hoje());
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
        if (botao.getSource() == botInserir) {
            auxiliar.limpaCampos(telaOS.getListaComponentes());
            this.mostraBotoesCadastro();
        }
        if (botao.getSource() == botLimpar) {            
            auxiliar.limpaCampos(telaOS.getListaComponentes());
        }
        if (botao.getSource() == botAlterarRegistro) {            
            ok = alterar();
            if(ok){	
                try {
                    String hoje = auxiliar.hoje();
                    conexao.executaProcedure("UPDATE_ORDEMSERVICO (" + this.intCodigoOS + ",'" + this.strOcorrencia + "','" + this.strDataAbertura + "', '" + this.strDataFechamento + "', " + this.fltValorTotal + ", " + this.fltValorDesconto + " , " + this.intMatricula + " , " + this.intCodigoCliente + " )");
                    conexao.executaProcedure("UPDATE_SERVICOSOS('" + hoje + "', " + this.intQtdeServico + ", " + this.intCodigoOS + ", " + this.intCodigoServico + ")");
                    JOptionPane.showMessageDialog(null, "Dados Alterados com sucesso");
                    auxiliar.limpaCampos(telaOS.getListaComponentes());
                    txtDataAbertura.setText(auxiliar.hoje());
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
        if (botao.getSource() == botFaturarNotaFiscal){
            if(auxiliar.validaCampos(telaOS.getListaComponentes())){
                if(JOptionPane.showConfirmDialog(null, "Deseja Faturar está ordem de serviço?", "Faturar Ordem", JOptionPane.YES_NO_OPTION) == 0){
                    ConfirmaNotaFiscal cnf = new ConfirmaNotaFiscal(intCodigoOS);
                    auxiliar.limpaCampos(telaOS.getListaComponentes());
                }
            }
        }
    }  

    @Override
    public void focusLost(FocusEvent e) {
        if(e.getSource() == txtQtde){
            if(txtValor.getText().isEmpty() == false && txtQtde.getText().isEmpty() == false){                
                
                this.intQtdeServico = Integer.parseInt(txtQtde.getText());
                this.fltValorServico = Integer.parseInt(txtValor.getText());
                this.fltValorTotal = auxiliar.calculaValorTotal(this.fltValorServico, this.intQtdeServico);
                String strValorTotal;           
                strValorTotal = auxiliar.formataValor(this.fltValorTotal);
                txtValorTotal.setText(strValorTotal);
                
            }else if(txtQtde.getText().isEmpty() == true && txtValor.getText().isEmpty() == false){            
                txtValorTotal.setText("");
            }            
        }
    }    

    @Override
    public void keyTyped(KeyEvent e) {
        char key = e.getKeyChar();
        int k = key;
        if(!auxiliar.apenasNumeros(k)){
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
       
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
       
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
    @Override
    public void focusGained(FocusEvent e) {
        
    }
}
