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
        JTextField txtNome;
	 	
	public static void main(String[] args) {
		//Chama a função Classe Login
	    Login login = new Login();
		//Torna visível o objeto Tela que foi criado acima
	    login.setVisible(true);
	}
        
	public Login(){
		super("Login de Acesso" ,"", new Dimension(800, 800));
		mostrar();
	}
	 void mostrar(){
             
             
                JTabbedPane panelGenerico;
                JLabel label1, label2;
                label1 = new JLabel("teste");
                label2 = new JLabel("teste2");
                
                panelGenerico = new JTabbedPane();
                
                panelGenerico.addTab("Cadastro", label1);
                panelGenerico.addTab("Consulta", label2);
                
                panelGenerico.setBounds(10, 800, 600, 600);
             
		 DefaultTableModel tabela = new DefaultTableModel();
		 tblTeste = new JTable(tabela);
                 txtNome = new JTextField();
                 
                 txtNome.setBounds(10, 180, 300, 20);
                 
		 JScrollPane scrollpane = new JScrollPane(tblTeste); 		 
		try{   	
			ResultSet dados;
			dados = cn.executar("SELECT * FROM CARGOS");

                        String[] valores = new String[]{"carCodigo","carDescricao"};
			cn.preencheTabela(tabela,"SELECT * FROM CARGOS", valores);
			tblTeste.getColumnModel().getColumn(0).setHeaderValue("Código");
			tblTeste.getColumnModel().getColumn(1).setHeaderValue("Descrição");
			
		}catch(Exception erro){
			JOptionPane.showMessageDialog(null, erro.getMessage());			
		}
                scrollpane.setBounds(10, 10, 1600 , 800);
                scrollpane.add(txtNome); 

                getContentPane().add(scrollpane);
                getContentPane().add(panelGenerico);
                
                        
	}
	 void criaTabela(){
		 
		 
	 }

	
}
