package Core;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ConexaoBanco {

    private String host;
    private String usuario;
    private String senha;
   
    public Connection c;
   
    /**
     * Construtor da classe
     * @param host Host em que se deseja conectar
     * @param usuario Nome do usuário
     * @param senha Senha do usuário
     */
    public ConexaoBanco(String host, String usuario, String senha ) {
        this.senha = senha;
        this.usuario = usuario;
        this.host = host;
    }
    
    public ConexaoBanco() {
        this.senha = "fanbno022";
        this.usuario = "system";
        this.host = "localhost";
    }
   
    /**
     * Método que estabelece a conexão com o banco de dados
     *
     * @return True se conseguir conectar, falso em caso contrário.
     */
    public boolean conectar() {
        boolean estaConectado = false;
        
        String nomeServidor = this.host;
        String numeroPorta = "1521";
        String servico    = "xe";
        String userName   = this.usuario;
        String passName   = this.senha;
        String url = "jdbc:oracle:thin:@"+nomeServidor + ":" + numeroPorta + ":" + servico; 
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            this.c = DriverManager.getConnection(url,userName, passName);            
            estaConectado = true;
        } catch( SQLException e ) {
            //System.out.println(e.getMessage());
        	JOptionPane.showMessageDialog(null, e.getMessage());
            estaConectado = false;
        } catch ( ClassNotFoundException e ) {
            //System.out.println(e.getMessage());
        	JOptionPane.showMessageDialog(null, e.getMessage());
            estaConectado = false;
        } catch ( InstantiationException e ) {
            //System.out.println(e.getMessage());
        	JOptionPane.showMessageDialog(null, e.getMessage());
            estaConectado = false;
        } catch ( IllegalAccessException e ) {
            //System.out.println(e.getMessage());
        	JOptionPane.showMessageDialog(null, e.getMessage());
            estaConectado = false;
        }
        //JOptionPane.showMessageDialog(null, "Conectado");
        return estaConectado;
    }
   
    /**
     * Método que estabelece a desconexão com o banco de dados
     *
     * @return True se conseguir desconectar, falso em caso contrário.
     */
    public boolean desconectar() {
        boolean estaConectado = false;

        String nomeServidor = this.host;
        String numeroPorta = "1521";
        String servico    = "xe";
        String userName   = this.usuario;
        String passName   = this.senha;
        String url = "jdbc:oracle:thin:@"+nomeServidor + ":" + numeroPorta + ":" + servico; 
        
             
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            this.c = DriverManager.getConnection(url,userName, passName);
            this.c.close();
            estaConectado = true;
        } catch( SQLException e ) {
            System.out.println(e.getMessage());
            estaConectado = false;
        } catch ( ClassNotFoundException e ) {
            System.out.println(e.getMessage());
            estaConectado = false;
        } catch ( InstantiationException e ) {
            System.out.println(e.getMessage());
            estaConectado = false;
        } catch ( IllegalAccessException e ) {
            System.out.println(e.getMessage());
            estaConectado = false;
        }
       
        //Retorna se está ou não conectado
        return estaConectado;
    }

    /**
     * Esse método executa a query dada, e retorna um ResultSet
     * Talvez fosse melhor idéia fazer esse método lançar uma exception
     * a faze-lo retornar null como eu fiz, porém isso é apenas um exemplo
     * para demonstrar a funcionalidade do comando execute
     *
     * @param query String contendo a query que se deseja executar
     * @return ResultSet em caso de estar tudo Ok, null em caso de erro.
     */
    public ResultSet executar( String query ) {            	
    	PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
        	conectar();
        	stmt = this.c.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(query);
            return rs;
        } catch ( SQLException e ) {
            e.printStackTrace();
        }finally{
        	desconectar();            
        }
        return null;        
    }
   
    /**
     * Executa uma query como update, delete ou insert.
     * Retorna o número de registros afetados quando falamos de um update ou delete
     * ou retorna 1 quando o insert é bem sucedido. Em outros casos retorna -1
     *
     * @param query A query que se deseja executar
     * @return 0 para um insert bem sucedido. -1 para erro
     */
    public int inserir( String query ) {
        Statement st = null;
        int result = -1;
       
        try {
            conectar();
            st = this.c.createStatement();
            result = st.executeUpdate(query);
        } catch ( SQLException e ) {
            e.printStackTrace();
        }finally{
            desconectar();
        }
       
        return result;
    }
    /**
     * Este método preenche uma JTable com os dados provindos do método Executar .
     * @param tabela o nome da TableModel que você deseja preencher
     * @param query select da tabela que você deseja trazer os dados
     */
    
    public void preencheTabela(DefaultTableModel tabela,  String query) {  
    	ResultSet rs;
        ResultSetMetaData rsmd;
    	tabela.setNumRows(0);
        
        rs = executar(query);        
        try {
                //Armazena no objeto os metadados do resultset
                rsmd = rs.getMetaData();
                //Pega o numero de colunas do resultset
        	int indice = rsmd.getColumnCount();
                //Cria um array para armzenar o nome das colunas
                String[] campos = new String[indice];
                //Armazena na variável campos os nomes das colunas
                for (int i = 0; i < indice; i++){
                    campos[i] = rsmd.getColumnName(i+1);
                }
                //Adiciona as colunas
        	for(int i = 0; i < indice; i++){
                    tabela.addColumn(campos[i]);	
        	}
        	Object row[] = new Object[indice];
            while(rs.next()) {            	
                for(int i = 0; i < indice; i++){
                    row[i] = rs.getString(campos[i]);
                }
                tabela.addRow(row);
            }
        }  
        catch(SQLException e){  
            JOptionPane.showMessageDialog(null,"Erro ao listar no JTable " + e.getCause());        
        }  
    }
    
    public void executaProcedure(String procedure){        
        try {
            conectar();
            CallableStatement stmt = c.prepareCall("{call " + procedure + "}");    
            stmt.execute();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            desconectar();
        }
    }    
}
