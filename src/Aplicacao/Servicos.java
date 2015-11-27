
package Aplicacao;

import Core.ComboItem;
import Core.ConexaoBanco;
import Core.MetodosAuxiliares;
import Core.MontaInterfaces;
import Core.PTextField;
import Core.PadraoFormulario;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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


public class Servicos implements PadraoFormulario, ActionListener, KeyListener{
    MetodosAuxiliares auxiliar = new MetodosAuxiliares();
    MontaInterfaces telaServicos = new MontaInterfaces ("Gerenciamento de Serviços", "/imagens/icone-servico.png");
    ConexaoBanco conexao = new ConexaoBanco();
    
    //Panels
    private JPanel panelConsulta = new JPanel(new GridBagLayout());
    private JPanel panelCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoesCadastro = new JPanel(new GridBagLayout());
    private JPanel panelBotoesAlteracao = new JPanel(new GridBagLayout());  
    
    //Caixas de texto
    private PTextField txtCodigoServico = new PTextField();
    private PTextField txtNome = new PTextField();
    private PTextField txtValorHora= new PTextField();
    private PTextField txtAliquota = new PTextField();
    private PTextField txtDescricao = new PTextField();
    
    //Botões
    private JButton botCadastrar = new JButton();
    private JButton botLimpar = new JButton();
    private JButton botInserir = new JButton();
    private JButton botExcluir = new JButton();
    private JButton botAlterarRegistro = new JButton();
    
    //Tabela
    DefaultTableModel tabela = new DefaultTableModel();
    private JTable tblServicos = new JTable(tabela){public boolean isCellEditable(int row,int column){return false;}};
    
    //Atributos da classe relacionados ao banco
    private int intCodigo;
    private int intCodigoServico;
    private String strNomeServico;
    private float fltValorHora;
    private float fltAliquota;
    private String strDescricao;
    
    public Servicos(){
        this.iniciaComponentes();
        this.atribuiIcones();
        
        telaServicos.setTamanho(600, 650);
        telaServicos.setVisible(true);
        
        this.preencheCombos();
        this.adicionaEventos();
        
        this.preencheTabela();
        this.setaNomes(); 
    }
    
    public static void main(String[] args){
        Servicos sv = new Servicos();
    }
    
    public void iniciaComponentes(){
        
        telaServicos.addAbas(panelCadastro, "Cadastro");
        telaServicos.addAbas(panelConsulta, "Consulta");
        telaServicos.addPanelBotoes(panelCadastro, panelBotoesCadastro);
        telaServicos.addPanelBotoes(panelCadastro, panelBotoesAlteracao);

        telaServicos.addBotoes("Cadastrar", botCadastrar, panelBotoesCadastro);
        telaServicos.addBotoes("Limpar", botLimpar, panelBotoesCadastro);
        telaServicos.addBotoes("Modo Inserir", botInserir, panelBotoesAlteracao);
        telaServicos.addBotoes("Excluir Registro", botExcluir, panelBotoesAlteracao);
        telaServicos.addBotoes("Alterar Registro", botAlterarRegistro, panelBotoesAlteracao);        
        
        telaServicos.addLabelTitulo("Servicos", panelCadastro);
        telaServicos.addUmComponente("Codigo do Servico", txtCodigoServico, panelCadastro);
        telaServicos.addDoisComponentes("Nome", txtNome, "Valor da Hora", txtValorHora, panelCadastro);
        telaServicos.addDoisComponentes("Aliquota", txtAliquota, "Descricao", txtDescricao, panelCadastro);
        telaServicos.addTabela(tblServicos, panelConsulta);
        
        panelBotoesAlteracao.setVisible(false);
        panelBotoesCadastro.setVisible(true);        
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
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void adicionaEventos() {
        botCadastrar.addActionListener(this);
        botInserir.addActionListener(this);
        botExcluir.addActionListener(this);
        botLimpar.addActionListener(this);
        botAlterarRegistro.addActionListener(this);    
        txtAliquota.addKeyListener(this);
        txtValorHora.addKeyListener(this);
        
        tblServicos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                              
                if (me.getClickCount() == 2) {                    
                    try {
                        
                        JTable table =(JTable) me.getSource();
                        Point p = me.getPoint();  
                        int row = table.rowAtPoint(p);
                        intCodigo = Integer.parseInt(tblServicos.getValueAt(row, 0).toString());
                        
                        ResultSet rs;
                        rs = conexao.executaProcedureSelect("CONSULTA_SERVICOS (" + intCodigo + ")");
                        rs.next();                    
                        txtNome.setText(rs.getString(2));
                        txtValorHora.setText(rs.getString(3));                    
                        txtDescricao.setText(rs.getString(4));
                        txtCodigoServico.setText(rs.getString(5));                    
                        txtAliquota.setText(rs.getString(6));                    
                        
                        mostraBotoesAlteracao();
                        telaServicos.getTabbedPane().setSelectedIndex(0);
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
            conexao.preencheTabela(tabela, "CONSULTA_SERVICOS(0)");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage()+ "\n" + e.getMessage());
        }
    }

    @Override
    public void setaNomes() {
        txtAliquota.setName("Aliquota");
        txtCodigoServico.setName("Código do Serviço");
        txtDescricao.setName("Descrição");
        txtNome.setName("Nome");
        txtValorHora.setName("Valor Hora");
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
    public boolean cadastrar() {
        if(auxiliar.validaCampos(telaServicos.getListaComponentes())){ 
            this.strNomeServico = txtNome.getText();
            this.strDescricao = txtDescricao.getText();
            this.fltAliquota = Float.parseFloat(txtAliquota.getText());
            this.fltValorHora = Float.parseFloat(txtValorHora.getText());
            this.intCodigoServico = Integer.parseInt(txtCodigoServico.getText());
            return true;            
        }else{            
           return false;
        }
    }

    @Override
    public boolean alterar() {
        if(auxiliar.validaCampos(telaServicos.getListaComponentes())){ 
            this.strNomeServico = txtNome.getText();
            this.strDescricao = txtDescricao.getText();
            this.fltAliquota = Float.parseFloat(txtAliquota.getText());
            this.fltValorHora = Float.parseFloat(txtValorHora.getText());
            this.intCodigoServico = Integer.parseInt(txtCodigoServico.getText());
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
    public void actionPerformed(ActionEvent botao) {
        //Retorna qual o botão clicado e gera a ação
        boolean ok ;
        if (botao.getSource() == botCadastrar) {            
            ok = cadastrar();
            if(ok){	
                try {
                    ResultSet rs;
                    conexao.executaProcedure("INSERT_SERVICOS('" + this.strNomeServico + "', " + this.fltValorHora + ", '" + this.strDescricao + "' , " + this.intCodigoServico + " , " + this.fltAliquota + ")");
                    JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso");
                    auxiliar.limpaCampos(telaServicos.getListaComponentes());
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
                    conexao.executaProcedure("DELETE_SERVICOS(" + this.intCodigo + ")");
                    JOptionPane.showMessageDialog(null, "Dados deletados com sucesso");
                    auxiliar.limpaCampos(telaServicos.getListaComponentes());
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
            auxiliar.limpaCampos(telaServicos.getListaComponentes());
            this.mostraBotoesCadastro();
        }
        if (botao.getSource() == botLimpar) {
            auxiliar.limpaCampos(telaServicos.getListaComponentes());
        }
        if (botao.getSource() == botAlterarRegistro) {            
            ok = alterar();
            if(ok){	
                try {
                    String hoje = auxiliar.hoje();
                    conexao.executaProcedure("UPDATE_SERVICOS(" + this.intCodigo + ", '" + this.strNomeServico + "', " + this.fltValorHora + ", '" + this.strDescricao + "' , " + this.intCodigoServico + " , " + this.fltAliquota + ")");
                    JOptionPane.showMessageDialog(null, "Dados Alterados com sucesso");
                    auxiliar.limpaCampos(telaServicos.getListaComponentes());
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
        
}
