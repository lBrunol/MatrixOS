package Core;

import java.awt.*;
import java.awt.GridLayout;
import javax.swing.*;
public class Janela extends JFrame {

	private static final long serialVersionUID = 5138474335105346926L;
	
        
	public Janela(String titulo, Dimension tamanho){
		
                setTitle(titulo); 
		setSize(tamanho);
		Image Ico;
		Ico = Toolkit.getDefaultToolkit().getImage("teste.png");
		
                this.setIconImage(Ico);

		centralize(); 
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                LayoutManager GridLayout = null;
		
                getContentPane().setLayout(GridLayout); 
		getContentPane().setBackground(new Color (250,250,250));
                initComponents();
        }
	
	public void centralize() {	
		Dimension T = Toolkit.getDefaultToolkit().getScreenSize();		
		Dimension J = getSize();
                
		if (J.height > T.height) setSize(J.width,T.height); 
		if (J.width > T.width) setSize(T.width,J.height); 
                
		setLocation((T.width - J.width )/2,(T.height-J.height)/2);
	}
        
        public void initComponents(){
            JTabbedPane panelGenerico;
            JTextField  txtNome;
            JLabel label1, label2;
            
            label1 = new JLabel("teste");
            label2 = new JLabel("teste2");
            txtNome = new JTextField ();
            
            panelGenerico = new JTabbedPane();

            panelGenerico.addTab("Cadastro", label1);
            panelGenerico.addTab("Consulta", label2);
            panelGenerico.add(txtNome);
            

            panelGenerico.setBounds(10, 10, 600, 600);

            getContentPane().add(panelGenerico);
        }
        
	public static void main(String[] args) {	
		Janela jan = new Janela("Janela ", new Dimension(800,800));
		jan.setVisible(true);
	}
}