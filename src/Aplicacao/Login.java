package aplicacao;
import java.awt.Dimension;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.*;

import Core.ConexaoBanco;
import Core.Janela;

public class Login extends Janela {
	private static final long serialVersionUID = -352599882118966813L;
	
	ConexaoBanco cn = new ConexaoBanco("localhost","system","fanbno022");
	 JTable tblTeste;
	 	
	public static void main(String[] args) {
		//Chama a função Classe Login
	    Login login = new Login();
		//Torna visível o objeto Tela que foi criado acima
	    login.setVisible(true);
		
		

	}
	public Login(){
		super("Login de Acesso" , new Dimension(800, 800));
		mostrar();
	}
	 void mostrar(){
		 DefaultTableModel tabela = new DefaultTableModel();
		 tblTeste = new JTable(tabela);
		 JScrollPane scrollpane = new JScrollPane(tblTeste); 		 
		try{    	
			ResultSet dados;
			dados = cn.executar("SELECT * FROM CARGOS");

			if(dados.next()){
				System.out.println(dados.getString(2));
			}else{
				System.out.println(dados.getString(0));
			}
			String[] valores = new String[]{"carCodigo","carDescricao"};
			cn.preencheTabela(tabela,"SELECT * FROM CARGOS", valores); 
			scrollpane.setBounds(10, 80, 600 , 600);
			getContentPane().add(scrollpane);
			
			tblTeste.getColumnModel().getColumn(0).setHeaderValue("Código");
			tblTeste.getColumnModel().getColumn(1).setHeaderValue("Descrição");
			
		}catch(Exception erro){
			JOptionPane.showMessageDialog(null, erro.getMessage());			
		}
	}
	 void criaTabela(){
		 
		 
	 }

	
}
