package Core;

import java.awt.*;


import javax.swing.*;
public class Janela extends JFrame {

	private static final long serialVersionUID = 5138474335105346926L;
	
	public Janela(String titulo, Dimension tamanho)
	{
		setTitle(titulo); 
		setSize(tamanho);
		Image Ico;
		Ico = Toolkit.getDefaultToolkit().getImage("/src/imagens/cadastro-os.png");
		
	    this.setIconImage(Ico);

		centralize(); 
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(null); 
		getContentPane().setBackground(new Color (250,250,250)); 
		
		
		 		
		}
	
	public void centralize() {
	
		Dimension T = Toolkit.getDefaultToolkit().getScreenSize();
		
		Dimension J = getSize();
		
		if (J.height > T.height) setSize(J.width,T.height); 
		if (J.width > T.width) setSize(T.width,J.height); 
		
		setLocation((T.width - J.width )/2,(T.height-J.height)/2);
	}
	public static void main(String[] args) {
	
		Janela jan = new Janela("Janela ",new Dimension(300,200));
		jan.setVisible(true); 
		
	}
}