package Core;
import java.awt.*;  
import javax.swing.*;  
public class MontaInterfaces extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 633256218760738756L;
	private JPanel panelConsulta;
	private JPanel panelCadastro;
	private JTabbedPane tabbedPane;
	private JPanel panelPrincipal;
	
	/** 
     * Método construtor da classe de interface
     * @param tituloForm onde será passado o título do seu formulário
     */
	public MontaInterfaces(String tituloForm) {          
        
		//Faz com que ao clicar no fechar da janela a aplicação feche junto
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new GridBagLayout());
        this.setSize(600,600);
        this.setTitle(tituloForm);
        
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
		 
		/*add(panelCadastro, "Cadastro");
		add(panelConsulta, "Consulta");		
		add("Código", new JTextField(), "Nascimento", new JTextField(), panelCadastro);  
        add("Nome", new JTextField(), panelCadastro);  
        add("Nome Pai", new JTextField(), panelCadastro);  
        add("Nome Mãe", new JTextField(), panelCadastro);  
        add("RG", new JTextField(), "CPF", new JTextField(), panelCadastro);*/

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
     * Altera o tamanho da janela 
     * @param largura passar a largura desejada para a janela
     * @param altura passar a altura desejada para janela
     */
	public void setTamanho(int largura, int altura){
		this.setSize(new Dimension(largura, altura));		
	}
	/** 
     * Altera o tamanho máximo da janela
     * @param largura passar a largura máxima desejada para a janela
     * @param altura passar a altura máxima desejada para a janela
     */
	public void setTamanhoMaximo(int largura, int altura){
		this.setMaximumSize(new Dimension(largura, altura));	
	}
	/** 
     * Altera o tamanho mínimo da janela
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
	public void setCor(int r, int g, int b){
		getContentPane().setBackground(new Color(r,g,b));
		panelPrincipal.setBackground(new Color(r,g,b));
	}
	
	/** 
     * Altera o titulo da página
     * @param titulo valor para alterar o título da janela
     */
	public void setTitulo(String titulo){
		this.setTitle(titulo);
	}
	
	/** 
     * Inseri as abas no JTabbedPane
     * @param titulo valor que servira de titulo para a aba
     * @param panel Objeto do tipo JPanel que servirá como aba
     */
	public void add(JPanel panel, String titulo){
		tabbedPane.addTab(titulo, panel);
	}
	
    /** 
     * Adiciona um label e um componente horizontalmente 
     * @param label String que irá aparecer no label 
     * @param componente Componente de edição 
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
     * @param componente Componente de edição
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
     * @param componente Componente de edição 
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
     * @param componente Componente de edição 
     * @param label2 Label 2 
     * @param componente2 Componente de edição 2 
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
     * Adiciona um label, um componente de edição, mais um label e outro componente de edição. Todos  
     * na mesma linha 
     * @param label Label 1  
     * @param botao Botão
     * @param panel Painel que os componentes ficarão 
     */
    public void add(String label, JButton botao, JPanel panel) {  
        GridBagConstraints cons = new GridBagConstraints();
        JPanel panelBotao = new JPanel(new GridBagLayout());        
        
        cons.fill = GridBagConstraints.NONE;  
        cons.anchor = GridBagConstraints.WEST;        
        cons.insets = new Insets(4,4,4,4);  
        cons.gridx = GridBagConstraints.RELATIVE;
        cons.weightx = 0;  
        cons.gridwidth = 1;
        cons.gridy = 1;
        panelBotao.add(new JLabel(label), cons);
          
        cons.fill = GridBagConstraints.NONE;  
        cons.weightx = 1; 
        cons.gridwidth = GridBagConstraints.RELATIVE;
        cons.gridy = 0;
        
        
        panelBotao.add(botao,cons);
        cons.insets = new Insets(0,0,20,0);
        cons.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(panelBotao, cons);
  
    }
    public static void main(String[] args ) {  
        MontaInterfaces exe = new MontaInterfaces("Menu");  
        exe.setVisible(true);   
        
        
    }
}
