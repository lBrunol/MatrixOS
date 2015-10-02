package aplicacao;
import java.awt.Dimension;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.*;

import Core.ConexaoBanco;
import Core.Janela;

public final class Login extends Janela {
	private static final long serialVersionUID = -352599882118966813L;
	
	ConexaoBanco cn = new ConexaoBanco("localhost","system","fanbno022");
	JTable tblTeste;
        JTextField txtNome;
	 	
	public static void main(String[] args) {
		//Chama a função Classe Login
	    Login login = new Login();
		//Torna visível o objeto Tela que foi criado acima
	    login.setVisible(true);
	}
        
	public Login(){
		super("Login de Acesso" ,new Dimension(800, 800));
		mostrar();
	}
	 void mostrar(){
                         
		 DefaultTableModel tabela = new DefaultTableModel();
		 tblTeste = new JTable(tabela);
                 txtNome = new JTextField();
                 
                 txtNome.setBounds(10, 180, 300, 20);
                 
		 JScrollPane scrollpane = new JScrollPane(tblTeste); 		 
		try{   	
			cn.preencheTabela(tabela,"SELECT * FROM");
			//tblTeste.getColumnModel().getColumn(1).setHeaderValue("Descrição");
			//tblTeste.getColumnModel().getColumn(0).setHeaderValue("Código");
			
		}catch(Exception erro){
			JOptionPane.showMessageDialog(null, erro.getMessage());			
		}
                scrollpane.setBounds(10, 10, 1600 , 800);
                scrollpane.add(txtNome); 
                getContentPane().add(scrollpane);                
                        
	}
	 void criaTabela(){
		 
		 
	 }

	
}
