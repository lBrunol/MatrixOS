package Aplicacao;

import Core.ComboItem;
import Core.ConexaoBanco;
import Core.MetodosAuxiliares;
import Core.MontaInterfaces;
import Core.PComboBox;
import Core.PFormattedTextField;
import Core.PTextField;
import Core.PadraoFormulario;
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
/**
 *
 * @author Adriana
 */
public class Funcionario implements PadraoFormulario, ActionListener{
    MetodosAuxiliares auxiliar = new MetodosAuxiliares();
    MontaInterfaces telaFuncionario = new MontaInterfaces("Gerenciamento de Funcionários", "/imagens/funcionario-form.png");
    ConexaoBanco conexao = new ConexaoBanco();
    
    //Panels
    private JPanel panelConsulta = new JPanel(new GridBagLayout());
    private JPanel panelCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoesCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoesAlteracao = new JPanel(new GridBagLayout());
    
    //Caixas de texto
    private PTextField txtNome = new PTextField();
    private PFormattedTextField txtTelefone= new PFormattedTextField(auxiliar.inseriMascara(MetodosAuxiliares.MASCARA_TELEFONE));
    private PComboBox cboCargo = new PComboBox();
    
    //Botões
    private JButton botCadastrar = new JButton();
    private JButton botLimpar = new JButton();
    private JButton botInserir = new JButton();
    private JButton botExcluir = new JButton();
    private JButton botAlterarRegistro = new JButton();
    
    //Tabela
    DefaultTableModel tabela = new DefaultTableModel();
    private JTable tblFuncionario = new JTable(tabela){public boolean isCellEditable(int row,int column){return false;}};
    
    //Atributos da classe relacionados ao banco
    private int intMatricula;
    private int intCodigoCargo;
    private int intTelefone;
    private String strNome;
    
    
    public Funcionario(){
        this.iniciaComponentes();
        this.atribuiIcones();
        
        telaFuncionario.setTamanho(600, 650);
        telaFuncionario.setVisible(true);
        
        this.preencheCombos();
        this.adicionaEventos();
        
        this.preencheTabela();
        this.setaNomes(); 
    }
    
    public static void main(String[] args){
        Funcionario fun = new Funcionario();
    }
    
    public void iniciaComponentes(){       
        
        telaFuncionario.addAbas(panelCadastro, "Cadastro");
        telaFuncionario.addAbas(panelConsulta, "Consulta");
        telaFuncionario.addPanelBotoes(panelCadastro, panelBotoesCadastro);
        telaFuncionario.addPanelBotoes(panelCadastro, panelBotoesAlteracao);
       
        telaFuncionario.addBotoes("Cadastrar", botCadastrar, panelBotoesCadastro);
        telaFuncionario.addBotoes("Limpar", botLimpar, panelBotoesCadastro);
        telaFuncionario.addBotoes("Modo Inserir", botInserir, panelBotoesAlteracao);
        telaFuncionario.addBotoes("Excluir Registro", botExcluir, panelBotoesAlteracao);
        telaFuncionario.addBotoes("Alterar Registro", botAlterarRegistro, panelBotoesAlteracao); 
       
        telaFuncionario.addLabelTitulo("Funcionários", panelCadastro);
        
        telaFuncionario.addUmComponente("Nome", txtNome, panelCadastro);
        telaFuncionario.addUmComponente("Telefone", txtTelefone, panelCadastro);
        telaFuncionario.addUmComponente("Cargo", cboCargo, panelCadastro);
        telaFuncionario.addTabela(tblFuncionario, panelConsulta);
        
        panelBotoesAlteracao.setVisible(false);
        panelBotoesCadastro.setVisible(true);
    }

    @Override
    public boolean cadastrar() {
        if(auxiliar.validaCampos(telaFuncionario.getListaComponentes())){ 
            this.intTelefone = Integer.parseInt(auxiliar.removeCaracteresString(txtTelefone.getText()));
            this.strNome = txtNome.getText();
            ComboItem comboItem = (ComboItem) cboCargo.getSelectedItem();
            this.intCodigoCargo = Integer.parseInt(comboItem.getId());
            return true;            
        }else{            
           return false;
        }
    }

    @Override
    public boolean alterar() {
        if(auxiliar.validaCampos(telaFuncionario.getListaComponentes())){ 
            this.intTelefone = Integer.parseInt(auxiliar.removeCaracteresString(txtTelefone.getText()));
            this.strNome = txtNome.getText();
            ComboItem comboItem = (ComboItem) cboCargo.getSelectedItem();
            this.intCodigoCargo = Integer.parseInt(comboItem.getId());
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

    @Override
    public void atribuiIcones() {
        Icon iconeCadastrar = new ImageIcon(getClass().getResource("/imagens/salvar.png"));
        Icon iconeExcluir = new ImageIcon(getClass().getResource("/imagens/excluir.png"));
        Icon iconeAlterar = new ImageIcon(getClass().getResource("/imagens/alterar.png"));
        Icon iconeInserir = new ImageIcon(getClass().getResource("/imagens/adicionar.png"));
        Icon iconeLimpar = new ImageIcon(getClass().getResource("/imagens/limpar.png"));

        botAlterarRegistro.setIcon(iconeAlterar);
        botCadastrar.setIcon(iconeCadastrar);
        botExcluir.setIcon(iconeExcluir);
        botInserir.setIcon(iconeInserir);
        botLimpar.setIcon(iconeLimpar);
    }

    @Override
    public void preencheCombos() {
        cboCargo.addItem("");

        conexao.preencheCombo(cboCargo, "SELECT carCodigo, carDescricao FROM cargos");
    }

    @Override
    public void adicionaEventos() {
        botCadastrar.addActionListener(this);
        botInserir.addActionListener(this);
        botExcluir.addActionListener(this);
        botLimpar.addActionListener(this);
        botAlterarRegistro.addActionListener(this);
        
        cboCargo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cboCargo.getSelectedItem() != ""){
                    ComboItem comboItem = (ComboItem) cboCargo.getSelectedItem();
                    intCodigoCargo = Integer.parseInt(comboItem.getId());                    
                }
            }
        });
        
        tblFuncionario.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                              
                if (me.getClickCount() == 2) {                    
                    try {
                        
                        JTable table =(JTable) me.getSource();
                        Point p = me.getPoint();  
                        int row = table.rowAtPoint(p);
                        intMatricula = Integer.parseInt(tblFuncionario.getValueAt(row, 0).toString());
                        
                        ResultSet rs;
                        rs = conexao.executar("SELECT * FROM funcionario WHERE funMatricula =" + intMatricula);
                        rs.next();                    
                        txtNome.setText(rs.getString(2));
                        txtTelefone.setText(rs.getString(3));
                        cboCargo.setSelectedIndex(rs.getInt(4));                    
                        
                        mostraBotoesAlteracao();
                        telaFuncionario.getTabbedPane().setSelectedIndex(0);
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
    public void preencheTabela() {
       try {
            conexao.preencheTabela(tabela, "select funMatricula, funNome, funTelefone, carDescricao FROM Funcionario INNER JOIN cargos ON funcionario.carCodigo = cargos.carCodigo ORDER BY funMatricula");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage()+ "\n" + e.getMessage());
        }
    }

    @Override
    public void setaNomes() {
        cboCargo.setName("Cargo");
        txtNome.setName("Nome");
        txtTelefone.setName("Telefone");
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
    }

    @Override
    public void actionPerformed(ActionEvent botao) {
        boolean ok ;
        if (botao.getSource() == botCadastrar) {            
            ok = cadastrar();
            if(ok){	
                try {
                    ResultSet rs;
                    conexao.executaProcedure("INSERT_FUNCIONARIO ('" + this.strNome + "', " + this.intTelefone + ", " + this.intCodigoCargo + " )");
                    JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso");
                    auxiliar.limpaCampos(telaFuncionario.getListaComponentes());
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
                    conexao.executaProcedure("DELETE_FUNCIONARIO(" + this.intMatricula + ")");
                    JOptionPane.showMessageDialog(null, "Dados deletados com sucesso");
                    auxiliar.limpaCampos(telaFuncionario.getListaComponentes());
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
            auxiliar.limpaCampos(telaFuncionario.getListaComponentes());
            this.mostraBotoesCadastro();
        }
        if (botao.getSource() == botLimpar) {
            auxiliar.limpaCampos(telaFuncionario.getListaComponentes());
        }
        if (botao.getSource() == botAlterarRegistro) {            
            ok = alterar();
            if(ok){	
                try {
                    conexao.executaProcedure("UPDATE_FUNCIONARIO (" + this.intMatricula + ",'" + this.strNome + "', " + this.intTelefone + ", " + this.intCodigoCargo + " )");
                    JOptionPane.showMessageDialog(null, "Dados Alterados com sucesso");
                    auxiliar.limpaCampos(telaFuncionario.getListaComponentes());
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
    }
    
}
