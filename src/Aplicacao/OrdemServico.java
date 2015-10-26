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
import Core.PTextField;
import Core.PadraoFormulario;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
    
    //Criação dos objetos, imprescindível criar ao menos um JPanel para servir de aba no formulário
    private JTextField txtCodigo = new JTextField();
    private JFormattedTextField txtDataAbertura = new JFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_DATA));
    private JFormattedTextField txtDataFechamento = new JFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_DATA));
    private JTextArea txtDescricaoOcorrencia = new JTextArea();
    private JTextField txtValorTotal = new JTextField();

    //Clientes
    private JComboBox cboCliente = new JComboBox();
    private JTextField txtEndereco = new PTextField();
    private JTextField txtNumEndereco = new PTextField();
    private JTextField txtComplemento = new PTextField();
    private JFormattedTextField txtCEP = new JFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_CEP));
    private JTextField txtBairro = new JTextField();
    private JTextField txtCidade = new JTextField();
    private JTextField txtUF = new JTextField();
    private JFormattedTextField txtTelefone = new JFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_TELEFONE));
    private JFormattedTextField txtCPF = new JFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_CPF));
    private JFormattedTextField txtRG = new JFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_RG));

    //Funcionários
    private JComboBox cboFuncionario = new JComboBox();
    private JTextField txtCargo = new JTextField();

    //Serviços
    private JComboBox cboServico = new JComboBox();
    private JTextField txtValor = new JTextField();
    private JTextField txtQtde = new JTextField();
    private JTextArea txtDescricaoServico = new JTextArea();

    //Botões
    private JButton botCadastrar = new JButton();
    private JButton botLimpar = new JButton();
    private JButton botInserir = new JButton();
    private JButton botFaturarNotaFiscal = new JButton();
    private JButton botExcluir = new JButton();
    private JButton botAlterarRegistro = new JButton();

    DefaultTableModel tabela = new DefaultTableModel();

    //Tabela
    private JTable tblOrdemServico = new JTable(tabela);

    //Panels (Abas)
    private JPanel panelConsulta = new JPanel(new GridBagLayout());
    private JPanel panelCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoesCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoesAlteracao = new JPanel(new GridBagLayout());

    //Instância da classe de conexão com o banco
    ConexaoBanco conexao = new ConexaoBanco();

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

    public OrdemServico() {
        this.iniciaComponentes();

    }

    public static void main(String args[]) {
        OrdemServico os = new OrdemServico();
    }

    private void iniciaComponentes() {
        
        telaOS.setVisible(true);
        telaOS.setTamanho(1000, 1000);

        telaOS.addAbas(panelCadastro, "Cadastro");
        telaOS.addPanelBotoes(panelCadastro, panelBotoesCadastro);
        telaOS.addPanelBotoes(panelCadastro, panelBotoesAlteracao);

        //Botões de cadastro
        telaOS.addBotoes("Cadastrar", botCadastrar, panelBotoesCadastro);
        telaOS.addBotoes("Limpar", botLimpar, panelBotoesCadastro);

        //Botões de alteração
        panelBotoesAlteracao.setVisible(false);
        panelBotoesCadastro.setVisible(true);
        telaOS.addBotoes("Faturar Nota Fiscal", botFaturarNotaFiscal, panelBotoesAlteracao);
        telaOS.addBotoes("Modo Inserir", botInserir, panelBotoesAlteracao);
        telaOS.addBotoes("Excluir Registro", botExcluir, panelBotoesAlteracao);
        telaOS.addBotoes("Alterar Registro", botAlterarRegistro, panelBotoesAlteracao);

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

        //Adiciona eventos
        botCadastrar.addActionListener(this);
        botInserir.addActionListener(this);
        botExcluir.addActionListener(this);
        botLimpar.addActionListener(this);
        txtQtde.addFocusListener(this);
        txtQtde.addKeyListener(this);
        botAlterarRegistro.addActionListener(this);

        //Adiciona os componentes na tela
        telaOS.addLabelTitulo("Ordem de Serviço", panelCadastro);
        telaOS.addQuatroComponentes("Código", txtCodigo, "Data de Abertura", txtDataAbertura, "Data de Fechamento", txtDataFechamento, "Valor Total", txtValorTotal, panelCadastro);
        telaOS.addUmComponente("Descrição da Ocorrência", txtDescricaoOcorrencia, panelCadastro);
        telaOS.addLabelTitulo("Cliente", panelCadastro);
        telaOS.addUmComponente("Selecione o cliente*", cboCliente, panelCadastro);
        telaOS.addQuatroComponentes("Endereço", txtEndereco, "N°", txtNumEndereco, "Complemento", txtComplemento, "CEP", txtCEP, panelCadastro);
        telaOS.addQuatroComponentes("Bairro", txtBairro, "Cidade", txtCidade, "UF ", txtUF, "Telefone", txtTelefone, panelCadastro);
        telaOS.addDoisComponentes("CPF/CNPJ", txtCPF, "RG/IM", txtRG, panelCadastro);
        telaOS.addLabelTitulo("Funcionário", panelCadastro);
        telaOS.addDoisComponentes("Selecione o Funcionário*", cboFuncionario, "Cargo", txtCargo, panelCadastro);
        telaOS.addLabelTitulo("Serviço", panelCadastro);
        telaOS.addQuatroComponentes("Selecione o Serviço*", cboServico, "Valor", txtValor, "Quantidade", txtQtde, "Descrição", txtDescricaoServico, panelCadastro);
        telaOS.addAbas(panelConsulta, "Consulta");
        telaOS.addTabela(tblOrdemServico, panelConsulta);

        //txtEndereco.setEnabled(false);
        //txtNumEndereco.setEnabled(false);
        //txtComplemento.setEnabled(false);
        //txtCEP.setEnabled(false);
        //txtBairro.setEnabled(false);
        //txtCidade.setEnabled(false);
        //txtUF.setEnabled(false);
        //txtTelefone.setEnabled(false);
        //txtCPF.setEnabled(false);
        //txtRG.setEnabled(false);
        //txtCargo.setEnabled(false);
        //txtValor.setEnabled(false);
        //txtDescricaoServico.setEnabled(false);
        txtQtde.setEnabled(false);
        this.setaNomes();
        
                    
        conexao.preencheTabela(tabela, "SELECT ordCodigo Código, ordOcorrencia Ocorrência, ordDataAbertura, ordValorTotal Valor, cliNome, funNome FROM cliente INNER JOIN (funcionario INNER JOIN ordemServico ON funcionario.funMatricula = ordemServico.funMatricula ) ON cliente.cliCodigo = ordemServico.cliCodigo ORDER BY ordCodigo");
        //Formata os valores em moeda
        auxiliar.formataValorTabela(tblOrdemServico, 3);
        
        //Preenche as combobox
        cboCliente.addItem("");
        cboFuncionario.addItem("");
        cboServico.addItem("");

        conexao.preencheCombo(cboCliente, "SELECT cliCodigo, cliNome FROM cliente");
        conexao.preencheCombo(cboFuncionario, "SELECT funMatricula, funNome FROM funcionario");
        conexao.preencheCombo(cboServico, "SELECT svcCodigo, svcNome FROM servicos");
        
        cboCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                if(cboCliente.getSelectedItem() != ""){
                    ComboItem comboItem = (ComboItem) cboCliente.getSelectedItem();
                    intCodigoCliente = Integer.parseInt(comboItem.getId());
                    try {
                        ResultSet rs = conexao.executar("SELECT cliTipo FROM cliente WHERE cliCodigo = " + intCodigoCliente);
                        rs.next();
                        if("J".equals(rs.getString(1).toUpperCase())){                        
                            rs = conexao.executar("SELECT cliEndereco, cliNumEndereco, cliComplemento, cliCEP, cliBairro, cliCidade, cliUF, cliTelefone, cliCNPJ, cliIM FROM cliente INNER JOIN cliPessoaJuridica ON cliente.cliCodigo = cliPessoaJuridica.cliCodigo WHERE cliente.cliCodigo = " + intCodigoCliente);
                            rs.next();
                            txtCPF.setFormatterFactory(new DefaultFormatterFactory(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_CNPJ)));
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
                        JOptionPane.showMessageDialog(null, b.getMessage() + ". Entre em contato com administrador do sistema.");
                    }
                    catch (Exception b) {
                        JOptionPane.showMessageDialog(null,"erro" + b.getMessage());
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
             
                    }catch (SQLException b) {
                        JOptionPane.showMessageDialog(null, b.getMessage() + ". Entre em contato com administrador do sistema.");
                    }
                    catch (Exception b) {
                        JOptionPane.showMessageDialog(null,"erro" + b.getMessage());
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
             
                    }catch (SQLException b) {
                        JOptionPane.showMessageDialog(null, b.getMessage() + ". Entre em contato com administrador do sistema.");
                    }
                    catch (Exception b) {
                        JOptionPane.showMessageDialog(null,"erro" + b.getMessage());
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
            JTable table =(JTable) me.getSource();
            Point p = me.getPoint();
            int row = table.rowAtPoint(p);
            if (me.getClickCount() == 2) {
                int intCodigoSelecionado;
                intCodigoSelecionado = Integer.parseInt(tblOrdemServico.getValueAt(row, 0).toString());
                try {
                    ResultSet rs;
                    rs = conexao.executar("SELECT * FROM ordemServico WHERE ordCodigo =" + intCodigoSelecionado);
                    rs.next();                    
                    txtCodigo.setText(rs.getString(1));
                    txtDescricaoOcorrencia.setText(rs.getString(2));
                    txtDataAbertura.setText(auxiliar.formataData(rs.getDate(3)));
                    txtDataFechamento.setText(auxiliar.formataData(rs.getDate(4)));
                    txtValorTotal.setText(auxiliar.formataValor(rs.getFloat(5)));
                    cboFuncionario.setSelectedIndex(rs.getInt(7));
                    cboCliente.setSelectedIndex(rs.getInt(8));                    
                    
                    rs = conexao.executar("SELECT * FROM servicosOS WHERE ordCodigo =" + intCodigoSelecionado);
                    rs.next();
                    cboServico.setSelectedIndex(rs.getInt(4));
                    txtQtde.setText(rs.getString(2));
                    
                    mostraBotoesAlteracao();
                    
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }                
                telaOS.getTabbedPane().setSelectedIndex(0);
                
            }
        }

        });
    }

    public void mostraBotoesAlteracao() {
            panelBotoesAlteracao.setVisible(true);
            panelBotoesCadastro.setVisible(false);
    }

    public void mostraBotoesCadastro() {
            panelBotoesAlteracao.setVisible(false);
            panelBotoesCadastro.setVisible(true);
    }
    
    public void setaNomes(){
        txtEndereco.setName("Endereço");
        txtNumEndereco.setName("Número do endereço");
        txtComplemento.setName("Complemento");
    }    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 

    @Override
    public void actionPerformed(ActionEvent botao) {
        //Retorna qual o botão clicado e gera a ação
        boolean ok ;
        if (botao.getSource() == botCadastrar) {            
            ok = cadastrar();
            if(ok){	
                try {
                    Calendar c = new GregorianCalendar();
                    conexao.executaProcedure("INSERT_ORDEMSERVICO (" + this.intCodigoOS + ",'" + this.strOcorrencia + "','" + this.strDataAbertura + "', '" + this.strDataFechamento + "', " + this.fltValorTotal + ", " + this.fltValorDesconto + " , " + this.intMatricula + " , " + this.intCodigoCliente + " )");
                    conexao.executaProcedure("INSERT_SERVICOSOS ('24/10/2015', " + this.intQtdeServico + ", " + this.intCodigoOS + ", " + this.intCodigoServico + ")");
                    JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso");
                    auxiliar.limpaCampos(telaOS.getListaComponentes());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());    
                }                                    
            }
        }
        if (botao.getSource() == botExcluir) {
            ok = deletar();
            if(ok){	
                try {
                    this.intCodigoOS = Integer.parseInt(txtCodigo.getText());
                    System.out.println(this.intCodigoOS);
                    conexao.executaProcedure("DELETE_SERVICOSOS(" + this.intCodigoOS + ")");
                    conexao.executaProcedure("DELETE_ORDEMSERVICO(" + this.intCodigoOS + ")");
                    JOptionPane.showMessageDialog(null, "Dados deletados com sucesso");
                    auxiliar.limpaCampos(telaOS.getListaComponentes());
                    this.mostraBotoesAlteracao();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());    
                }                                    
            }           
        }
        if (botao.getSource() == botInserir) {
            auxiliar.limpaCampos(telaOS.getListaComponentes());
            this.mostraBotoesCadastro();
        }
        if (botao.getSource() == botLimpar) {            
            //auxiliar.limpaCampos(telaOS.getListaComponentes());
            auxiliar.validaCampos(telaOS.getListaComponentes());
        }
        if (botao.getSource() == botAlterarRegistro) {            
            ok = alterar();
            if(ok){	
                try {
                    conexao.executaProcedure("UPDATE_ORDEMSERVICO (" + this.intCodigoOS + ",'" + this.strOcorrencia + "','" + this.strDataAbertura + "', '" + this.strDataFechamento + "', " + this.fltValorTotal + ", " + this.fltValorDesconto + " , " + this.intMatricula + " , " + this.intCodigoCliente + " )");
                    conexao.executaProcedure("UPDATE_SERVICOSOS('24/10/2015', " + this.intQtdeServico + ", " + this.intCodigoOS + ", " + this.intCodigoServico + ")");
                    JOptionPane.showMessageDialog(null, "Dados Alterados com sucesso");
                    auxiliar.limpaCampos(telaOS.getListaComponentes());
                    this.mostraBotoesCadastro();
                } catch (SQLException | HeadlessException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());    
                }                                    
            }
        }
    }    

    @Override
    public boolean cadastrar() {
        if(cboCliente.getSelectedItem() == "" || cboFuncionario.getSelectedItem() == "" || cboServico.getSelectedItem() == "" || txtValorTotal.getText().isEmpty() || txtDataAbertura.getText().isEmpty() || txtDescricaoOcorrencia.getText().isEmpty() || txtCodigo.getText().isEmpty() || txtQtde.getText().isEmpty()){			
            JOptionPane.showMessageDialog(null, "Preencha os campos corretamente");
            return false;
        }else{            
            this.intCodigoOS = Integer.parseInt(txtCodigo.getText());
            this.strDataAbertura = txtDataAbertura.getText();
            this.strDataFechamento = txtDataFechamento.getText();
            this.strOcorrencia =  txtDescricaoOcorrencia.getText();
            this.fltValorTotal = auxiliar.removeCaracteresFloat(txtValorTotal.getText());
            this.intQtdeServico = Integer.parseInt(txtQtde.getText());
            this.intCodigoServico = cboServico.getSelectedIndex();            
            return true;
        }
    }

    @Override
    public boolean alterar() {
        if(cboCliente.getSelectedItem() == "" || cboFuncionario.getSelectedItem() == "" || cboServico.getSelectedItem() == "" || txtValorTotal.getText().isEmpty() || txtDataAbertura.getText().isEmpty() || txtDescricaoOcorrencia.getText().isEmpty() || txtCodigo.getText().isEmpty() || txtQtde.getText().isEmpty()){			
            JOptionPane.showMessageDialog(null, "Preencha os campos corretamente");
            return false;
        }else{            
            this.intCodigoOS = Integer.parseInt(txtCodigo.getText());
            this.strDataAbertura = txtDataAbertura.getText();
            this.strDataFechamento = txtDataFechamento.getText();
            this.strOcorrencia =  txtDescricaoOcorrencia.getText();
            this.fltValorTotal = auxiliar.removeCaracteresFloat(txtValorTotal.getText());
            this.intCodigoCliente = cboCliente.getSelectedIndex();
            this.intCodigoServico = cboServico.getSelectedIndex();
            this.intMatricula = cboFuncionario.getSelectedIndex();
            return true;
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

    @Override
    public void focusGained(FocusEvent e) {
        
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
        //JOptionPane.showMessageDialog(null, k);
        if(!auxiliar.apenasNumeros(k)){
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
       //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
        
    

}
