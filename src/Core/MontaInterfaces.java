package Core;
import java.awt.*;  
import javax.swing.*;  
public class MontaInterfaces extends JFrame {

    /**
    *Classe responsável por prover os métodos para criação das telas gráficas do sistema
    */
    private static final long serialVersionUID = 633256218760738756L;
    private JPanel panelConsulta;
    private JPanel panelCadastro;
    private JTabbedPane tabbedPane;
    private JPanel panelPrincipal;
	
    /** 
    * Método construtor da classe de interface
    * @param tituloForm onde será passado o título do seu formulário (janela e cabeçalho)
    */
    public MontaInterfaces(String tituloForm) {          
        
        //Faz com que ao clicar no fechar da janela a aplicação feche junto
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new GridBagLayout());
        this.setSize(600,600);
        this.setTitle(tituloForm);
        this.centralize();
        
        panelPrincipal = new JPanel();
        JPanel panelTopo = new JPanel();
        JPanel panelAbas = new JPanel();
        panelCadastro = new JPanel();
        panelConsulta = new JPanel();
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        JLabel lblTitulo = new JLabel (tituloForm);
        JLabel lblImagem = new JLabel("");
        
        //Seta o gerenciador de layouts dos containers
        panelPrincipal.setLayout(new GridBagLayout());
        panelAbas.setLayout(new GridBagLayout());
        panelCadastro.setLayout(new GridBagLayout());
        tabbedPane.setLayout(new GridBagLayout());
        panelTopo.setLayout(new BorderLayout());
        panelConsulta.setLayout(new GridBagLayout());
        panelCadastro.setLayout(new GridBagLayout());
        
        //Variável que guarda as regras para o GridBagLayout        
        GridBagConstraints cons = new GridBagConstraints();
        
        
        cons.insets = new Insets(10,10,10,10);           
        cons.fill = GridBagConstraints.BOTH;  
        cons.weightx = 1;  
        cons.weighty = 1;
        cons.gridwidth = GridBagConstraints.REMAINDER;
        
        panelPrincipal.add(panelTopo, cons);
        
        cons.weightx = 10;  
        cons.weighty = 10;
        cons.insets = new Insets(0,0,0,0);
        panelPrincipal.add(panelAbas, cons);		

        lblTitulo.setFont(new Font("Calibri",Font.BOLD, 26));
        Icon icone = new ImageIcon(getClass().getResource("/imagens/icone-servico.png"));	

        lblImagem.setIcon(icone);

        panelTopo.add(lblTitulo, BorderLayout.WEST);
        panelTopo.add(lblImagem, BorderLayout.EAST);       


        panelAbas.add(tabbedPane, cons);	

        this.addPrincipal();
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }    
    } 
	
    /** 
    * Inseri o panel principal 
    */
    public void addPrincipal(){
        add(panelPrincipal);
    }
    
    /** 
    * centraliza a janela na tela 
    */    
    private void centralize() {	
        Dimension T = Toolkit.getDefaultToolkit().getScreenSize();		
        Dimension J = getSize();

        if (J.height > T.height) setSize(J.width,T.height); 
        if (J.width > T.width) setSize(T.width,J.height); 

        setLocation((T.width - J.width )/2,(T.height-J.height)/2);
    }
  
    /** 
    * Altera o tamanho da janela com base em valores inteiros
    * @param largura passar a largura desejada para a janela
    * @param altura passar a altura desejada para janela
    */
    public void setTamanho(int largura, int altura){
        this.setSize(new Dimension(largura, altura));		
    }
    
    /** 
    * Altera o tamanho máximo da janela com base em valores inteiros
    * @param largura passar a largura máxima desejada para a janela
    * @param altura passar a altura máxima desejada para a janela
    */
    public void setTamanhoMaximo(int largura, int altura){
        this.setMaximumSize(new Dimension(largura, altura));	
    }
        
    /** 
    * Altera o tamanho mínimo da janela com base em valores inteiros
    * @param largura passar a largura mínima desejada para a janela
    * @param altura passar a altura mínima desejada para a janela
    */
    public void setTamanhoMinimo(int largura, int altura){
        this.setMinimumSize(new Dimension(largura, altura));	
    }
        
    /** 
    * Altera a cor de fundo da janela
    * @param r quantidade de vermelho
    * @param g quantidade de verde
    * @param b quantiade de azul 
    */
    private void setCor(int r, int g, int b){
        getContentPane().setBackground(new Color(r,g,b));
        panelPrincipal.setBackground(new Color(r,g,b));
    }
	
    /** 
    * Altera o titulo da página
    * @param titulo string para alterar o título da janela
    */
    public void setTitulo(String titulo){
        this.setTitle(titulo);
    }
	
    /** 
    * Inseri as abas no JTabbedPane
    * @param titulo string que servira de titulo da aba
    * @param panel Objeto do tipo JPanel que servirá como aba
    */
    public void add(JPanel panel, String titulo){
        tabbedPane.addTab(titulo, panel);
    }
	
    /** 
    * Adiciona um label e um componente horizontalmente 
    * @param label String que irá aparecer no label 
    * @param componente passar um componente para adição na janela, exemplo, JTextBox, JComboBox, JTextArea. 
    * @param panel onde os componentes serão adicionados
    */ 
    public void add(String label, JComponent componente, JPanel panel) {  
        GridBagConstraints cons = new GridBagConstraints();  
        cons.fill = GridBagConstraints.NONE;  
        cons.anchor = GridBagConstraints.NORTHWEST;  
        cons.insets = new Insets(4,4,4,4);  
  
        cons.weightx = 0;  
        cons.gridwidth = 1;  
        panel.add(new JLabel(label), cons);  
          
        cons.fill = GridBagConstraints.BOTH;  
        cons.weightx = 1;  
        cons.gridwidth = GridBagConstraints.REMAINDER;  
        panel.add(componente, cons);  
    }
    
    /** 
    * Adiciona um panel
    * @param componente passar um objeto do tipo JPanel.
    */  
    public void add(JComponent componente) {  
        GridBagConstraints cons = new GridBagConstraints();
        
        cons.insets = new Insets(10,10,10,10);           
        cons.fill = GridBagConstraints.BOTH;  
        cons.weightx = 1;  
        cons.weighty = 1;
        cons.gridwidth = GridBagConstraints.RELATIVE;  
        this.getContentPane().add(componente, cons);  
    }
  
    /** 
    * Adiciona um label e um componente horizontalmente. O componente ocupará todo o reto da tela 
    * @param label String que irá aparecer no label 
    * @param componente passar um componente para adição na janela, exemplo, JTextBox, JComboBox, JTextArea. 
    * @param panel onde os componentes serão adicionados
    */  
    public void add(String label, JScrollPane componente, JPanel panel) {  
        GridBagConstraints cons = new GridBagConstraints();  
        cons.fill = GridBagConstraints.NONE;  
        cons.anchor = GridBagConstraints.NORTHWEST;  
        cons.insets = new Insets(4,4,4,4);  
        cons.weighty = 1;  
        cons.gridheight = GridBagConstraints.REMAINDER;  
          
        cons.weightx = 0;  
        cons.gridwidth = 1;  
        panel.add(new JLabel(label), cons);  
          
        cons.fill = GridBagConstraints.BOTH;  
        cons.weightx = 1;  
        cons.gridwidth = GridBagConstraints.REMAINDER;  
        panel.add(componente, cons);  
    }      
      
    /** 
    * Adiciona um label, um componente de edição, mais um label e outro componente de edição. Todos  
    * na mesma linha 
    * @param label Label 1  
    * @param componente passar um componente para adição na janela, exemplo, JTextBox, JComboBox, JTextArea. 
    * @param label2 Label 2 
    * @param componente2 passar um componente para adição na janela, exemplo, JTextBox, JComboBox, JTextArea. 2
    * @param panel Jpanel onde os componentes ficarão
    */  
    public void add(String label, JComponent componente, String label2, JComponent componente2, JPanel panel) {  
        GridBagConstraints cons = new GridBagConstraints();  
        cons.fill = GridBagConstraints.BOTH;  
        cons.insets = new Insets(4,4,4,4);  
  
        cons.fill = GridBagConstraints.NONE;  
        cons.anchor = GridBagConstraints.NORTHWEST;  
        cons.weightx = 0;  
        cons.gridwidth = 1;  
        panel.add(new JLabel(label), cons);  
          
        cons.weightx = 1;  
        cons.gridwidth = 1;  
        cons.fill = GridBagConstraints.BOTH;  
        panel.add(componente, cons);  
  
        cons.fill = GridBagConstraints.NONE;  
        cons.weightx = 0;  
        cons.gridwidth = 1;  
        panel.add(new JLabel(label2), cons);  
          
        cons.weightx = 1;  
        cons.fill = GridBagConstraints.BOTH;  
        cons.gridwidth = GridBagConstraints.REMAINDER;  
        panel.add(componente2, cons);  
    }
    
    /** 
    * Adiciona 4 componentes junto as suas labels na mesma linha 
    * @param label Label 1  
    * @param componente passar um componente para adição na janela, exemplo, JTextBox, JComboBox, JTextArea. 
    * @param label2 Label 2 
    * @param componente2 passar um componente para adição na janela, exemplo, JTextBox, JComboBox, JTextArea.
    * @param label3 Label 3 
    * @param componente3 passar um componente para adição na janela, exemplo, JTextBox, JComboBox, JTextArea. 
    * @param label4 Label 4
    * @param componente4 passar um componente para adição na janela, exemplo, JTextBox, JComboBox, JTextArea.
    * @param panel Jpanel onde os componentes ficarão
    */  
    public void add(String label, JComponent componente, String label2, JComponent componente2, String label3, JComponent componente3, String label4, JComponent componente4, JPanel panel) {  
        GridBagConstraints cons = new GridBagConstraints();  
        cons.fill = GridBagConstraints.BOTH;  
        cons.insets = new Insets(4,4,4,4);  
  
        cons.fill = GridBagConstraints.NONE;  
        cons.anchor = GridBagConstraints.NORTHWEST;  
        cons.weightx = 0;  
        cons.gridwidth = 1;  
        panel.add(new JLabel(label), cons);  
          
        cons.weightx = 1;  
        cons.gridwidth = 1;  
        cons.fill = GridBagConstraints.BOTH;  
        panel.add(componente, cons);  
  
        cons.fill = GridBagConstraints.NONE;  
        cons.weightx = 0;  
        cons.gridwidth = 1;  
        panel.add(new JLabel(label2), cons);
          
        cons.weightx = 1;  
        cons.fill = GridBagConstraints.BOTH;  
        panel.add(componente2, cons);
        
        cons.fill = GridBagConstraints.NONE;  
        cons.weightx = 0;  
        cons.gridwidth = 1;  
        panel.add(new JLabel(label3), cons);
          
        cons.weightx = 1;  
        cons.fill = GridBagConstraints.BOTH;  
        panel.add(componente3, cons);
        
        cons.fill = GridBagConstraints.NONE;  
        cons.weightx = 0;  
        cons.gridwidth = 1;  
        panel.add(new JLabel(label4), cons);
          
        cons.weightx = 1;  
        cons.fill = GridBagConstraints.BOTH;  
        cons.gridwidth = GridBagConstraints.REMAINDER;  
        panel.add(componente4, cons);
    }
    
    /**
    * Adiciona um label, um componente de edição, mais um label e outro
    * componente de edição. Todos na mesma linha
    *
    * @param label Label 1
    * @param botao Botão
    * @param panel Painel que os componentes ficarão
    */
    public void add(String label, JButton botao, JPanel panel) {
        GridBagConstraints cons = new GridBagConstraints();
        JPanel panelBotaoContainer = new JPanel(new GridBagLayout());
        JPanel panelBotao = new JPanel(new GridBagLayout());
       
        cons.insets = new Insets(4, 4, 4, 4);
        cons.gridy = 0;
        panelBotaoContainer.add(botao, cons);
        
        cons.gridy = 1;
        panelBotaoContainer.add(new JLabel(label), cons);

        cons.insets = new Insets(0, 0, 20, 0);         
        cons.weighty = 0;
        panel.add(panelBotaoContainer, cons);

    }
    
    /**
    * Adiciona um panel para os botões, necessário para posicionar botões ao lado do outro
    * @param aba Aba em que o panel com os botões será adicionado
    * @param panelBotoes panel onde os botões ficarão
    */    
    public void add(JPanel aba, JPanel panelBotoes){
        GridBagConstraints cons = new GridBagConstraints();
        
        cons.anchor = GridBagConstraints.WEST;        
        cons.insets = new Insets(0, 0, 20, 0);
        cons.gridwidth = GridBagConstraints.REMAINDER;   
        aba.add(panelBotoes, cons);
        
    }
    
    /**
     * Adiciona uma tabela ao JPanel escolhido 
     * @param tabela JTable que será adicionada a aba
     * @param panel aba escolhida para adicionar a tabela
     */
    public void add(JTable tabela, JPanel panel){
        
        GridBagConstraints cons = new GridBagConstraints();
        JScrollPane scrollpane = new JScrollPane(tabela);
        
        cons.insets = new Insets(10,10,10,10);           
        cons.fill = GridBagConstraints.BOTH;  
        cons.weightx = 1;  
        cons.weighty = 1;
        cons.gridwidth = GridBagConstraints.RELATIVE;
 
        panel.add(scrollpane ,cons);        
    }     
    
    
    public static void main(String[] args) {
        MontaInterfaces exe = new MontaInterfaces("Menu");
        exe.setVisible(true);

    }
}
