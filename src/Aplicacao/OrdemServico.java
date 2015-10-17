/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplicacao;

import Core.ComboItem;
import Core.ConexaoBanco;
import Core.MontaInterfaces;
import Core.PadraoFormulario;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.spi.DirStateFactory;
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

/**
 *
 * @author CASA
 */
public class OrdemServico implements ActionListener, PadraoFormulario {

    //Criação dos objetos, imprescindível criar ao menos um JPanel para servir de aba no formulário
    private JTextField txtCodigo = new JTextField();
    private JFormattedTextField txtDataAbertura = new JFormattedTextField();
    private JFormattedTextField txtDataFechamento = new JFormattedTextField();
    private JTextArea txtDescricaoOcorrencia = new JTextArea();
    private JTextField txtValorTotal = new JTextField();

    //Clientes
    private JComboBox cboCliente = new JComboBox();
    private JTextField txtEndereco = new JTextField();
    private JTextField txtNumEndereco = new JTextField();
    private JTextField txtComplemento = new JTextField();
    private JFormattedTextField txtCEP = new JFormattedTextField();
    private JTextField txtBairro = new JTextField();
    private JTextField txtCidade = new JTextField();
    private JTextField txtUF = new JTextField();
    private JFormattedTextField txtTelefone = new JFormattedTextField();
    private JFormattedTextField txtCPF = new JFormattedTextField();
    private JFormattedTextField txtRG = new JFormattedTextField();

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
    private String strDataAbertura;
    private String strDataFechamento;
    private String strOcorrencia;
    private float fltValorTotal;
    private float fltValorDesconto;

    public OrdemServico() {
        this.iniciaComponentes();

    }

    public static void main(String args[]) {
        OrdemServico os = new OrdemServico();
    }

    public void iniciaComponentes() {
        MontaInterfaces telaOS = new MontaInterfaces("Gerenciamento de Ordem de Serviço", "/imagens/os.png");
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

        //Seta os icones dos bortões
        botAlterarRegistro.setIcon(iconeAlterar);
        botCadastrar.setIcon(iconeCadastrar);
        botExcluir.setIcon(iconeExcluir);
        botFaturarNotaFiscal.setIcon(iconeFaturar);
        botInserir.setIcon(iconeInserir);
        botLimpar.setIcon(iconeLimpar);

        //Adiciona eventos aos botões 
        botCadastrar.addActionListener(this);
        botInserir.addActionListener(this);

        conexao.preencheCaixasTexto(txtBairro);

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
                    
        conexao.preencheTabela(tabela, "SELECT ordCodigo Código, ordOcorrencia Ocorrência, ordDataAbertura, cliNome, funNome FROM cliente INNER JOIN (funcionario INNER JOIN ordemServico ON funcionario.funMatricula = ordemServico.funMatricula ) ON cliente.cliCodigo = ordemServico.cliCodigo");

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
                    setIntCodigoCliente(Integer.parseInt(comboItem.getId()));
                    try {
                        ResultSet rs = conexao.executar("SELECT cliTipo FROM cliente WHERE cliCodigo = " + getIntCodigoCliente());
                        rs.next();
                        if("J".equals(rs.getString(1).toUpperCase())){                        
                            rs = conexao.executar("SELECT cliEndereco, cliNumEndereco, cliComplemento, cliCEP, cliBairro, cliCidade, cliUF, cliTelefone, cliCNPJ, cliIM FROM cliente INNER JOIN cliPessoaJuridica ON cliente.cliCodigo = cliPessoaJuridica.cliCodigo WHERE cliente.cliCodigo = " + getIntCodigoCliente());
                            rs.next();
                        }else if("F".equals(rs.getString(1).toUpperCase())){
                            rs = conexao.executar("SELECT cliEndereco, cliNumEndereco, cliComplemento, cliCEP, cliBairro, cliCidade, cliUF, cliTelefone, cliCPF, cliRG FROM cliente INNER JOIN cliPessoaFisica ON cliente.cliCodigo = cliPessoaFisica.cliCodigo WHERE cliente.cliCodigo = " + getIntCodigoCliente());
                            rs.next();
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
                    setIntMatricula(Integer.parseInt(comboItem.getId()));
                    try {
                        ResultSet rs = conexao.executar("SELECT carDescricao FROM funcionario INNER JOIN cargos ON funcionario.carCodigo = cargos.carCodigo WHERE funcionario.funMatricula= " + getIntMatricula());
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
                    setIntCodigoServico(Integer.parseInt(comboItem.getId()));
                    try {
                        ResultSet rs = conexao.executar("SELECT svcValorHora, svcDescricao FROM servicos WHERE svcCodigo = " + getIntCodigoServico());
                        rs.next();                  

                        txtValor.setText(rs.getString(1));
                        txtDescricaoServico.setText(rs.getString(2));
             
                    }catch (SQLException b) {
                        JOptionPane.showMessageDialog(null, b.getMessage() + ". Entre em contato com administrador do sistema.");
                    }
                    catch (Exception b) {
                        JOptionPane.showMessageDialog(null,"erro" + b.getMessage());
                    }
                }else{
                    txtValor.setText("");
                    txtDescricaoServico.setText("");
                }
            }
        });

    }

    public void mostraBotoesAlteracao() {
        if (panelBotoesAlteracao.isVisible()) {
            panelBotoesAlteracao.setVisible(false);
            panelBotoesCadastro.setVisible(true);
        } else {
            panelBotoesAlteracao.setVisible(true);
            panelBotoesCadastro.setVisible(false);
        }
    }

    public void mostraBotoesCadastro() {
        if (panelBotoesCadastro.isVisible()) {
            panelBotoesAlteracao.setVisible(true);
            panelBotoesCadastro.setVisible(false);
        } else {
            panelBotoesAlteracao.setVisible(false);
            panelBotoesCadastro.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent botao) {
        //Retorna qual o botão clicado e gera a ação
        boolean ok ;
        if (botao.getSource() == botCadastrar) {            
            ok = cadastrar();
            if(ok){	
                try {
                    conexao.executaProcedure("INSERT_ORDEMSERVICO (" + this.intCodigoOS + ",'" + this.strOcorrencia + "','" + this.strDataAbertura + "', '" + this.strDataFechamento + "', " + this.fltValorTotal + ", " + this.fltValorDesconto + " , " + this.intMatricula + " , " + this.intCodigoCliente + " )");
                    JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso"); 
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());    
                }                                    
            }
        }
        if (botao.getSource() == botInserir) {
            this.mostraBotoesCadastro();
        }
    }

    /**
     * @return the intCodigoOS
     */
    public int getIntCodigoOS() {
        return intCodigoOS;
    }

    /**
     * @param intCodigoOS the intCodigoOS to set
     */
    public void setIntCodigoOS(int intCodigoOS) {
        this.intCodigoOS = intCodigoOS;
    }

    /**
     * @return the intCodigoCliente
     */
    public int getIntCodigoCliente() {
        return intCodigoCliente;
    }

    /**
     * @param intCodigoCliente the intCodigoCliente to set
     */
    public void setIntCodigoCliente(int intCodigoCliente) {
        this.intCodigoCliente = intCodigoCliente;
    }

    /**
     * @return the intMatricula
     */
    public int getIntMatricula() {
        return intMatricula;
    }

    /**
     * @param intMatricula the intMatricula to set
     */
    public void setIntMatricula(int intMatricula) {
        this.intMatricula = intMatricula;
    }

    /**
     * @return the strDataAbertura
     */
    public String getStrDataAbertura() {
        return strDataAbertura;
    }

    /**
     * @param strDataAbertura the strDataAbertura to set
     */
    public void setStrDataAbertura(String strDataAbertura) {
        this.strDataAbertura = strDataAbertura;
    }

    /**
     * @return the strDataFechamento
     */
    public String getStrDataFechamento() {
        return strDataFechamento;
    }

    /**
     * @param strDataFechamento the strDataFechamento to set
     */
    public void setStrDataFechamento(String strDataFechamento) {
        this.strDataFechamento = strDataFechamento;
    }

    /**
     * @return the strOcorrencia
     */
    public String getStrOcorrencia() {
        return strOcorrencia;
    }

    /**
     * @param strOcorrencia the strOcorrencia to set
     */
    public void setStrOcorrencia(String strOcorrencia) {
        this.strOcorrencia = strOcorrencia;
    }

    /**
     * @return the fltValorTotal
     */
    public float getFltValorTotal() {
        return fltValorTotal;
    }

    /**
     * @param fltValorTotal the fltValorTotal to set
     */
    public void setFltValorTotal(float fltValorTotal) {
        this.fltValorTotal = fltValorTotal;
    }

    /**
     * @return the fltValorDesconto
     */
    public float getFltValorDesconto() {
        return fltValorDesconto;
    }

    /**
     * @param fltValorDesconto the fltValorDesconto to set
     */
    public void setFltValorDesconto(float fltValorDesconto) {
        this.fltValorDesconto = fltValorDesconto;
    }

    /**
     * @return the intCodigoServico
     */
    public int getIntCodigoServico() {
        return intCodigoServico;
    }

    /**
     * @param intCodigoServico the intCodigoServico to set
     */
    public void setIntCodigoServico(int intCodigoServico) {
        this.intCodigoServico = intCodigoServico;
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
            this.fltValorTotal = Float.parseFloat(txtValorTotal.getText());            
            return true;
        }
    }

    @Override
    public boolean alterar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean inserir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
