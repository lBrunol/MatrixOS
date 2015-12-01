package Core;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import oracle.jdbc.OracleTypes;

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
        
        //this.senha = "Tiger";
        //this.usuario = "Scott";
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
        //String servico    = "BD11G";
        String userName   = this.usuario;
        String passName   = this.senha;
        String url = "jdbc:oracle:thin:@"+nomeServidor + ":" + numeroPorta + ":" + servico; 
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            this.c = DriverManager.getConnection(url,userName, passName);            
            estaConectado = true;
        } catch( SQLException e ) {
        	JOptionPane.showMessageDialog(null, e.getMessage());
            estaConectado = false;
        } catch ( ClassNotFoundException e ) {
        	JOptionPane.showMessageDialog(null, e.getMessage());
            estaConectado = false;
        } catch ( InstantiationException e ) {
        	JOptionPane.showMessageDialog(null, e.getMessage());
            estaConectado = false;
        } catch ( IllegalAccessException e ) {
        	JOptionPane.showMessageDialog(null, e.getMessage());
            estaConectado = false;
        }
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
            JOptionPane.showMessageDialog(null, e.getMessage());
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
     * @throws java.sql.SQLException
     */    
    public void preencheTabela(DefaultTableModel tabela,  String query) throws SQLException{
        
    	ResultSet rs;
        ResultSetMetaData rsmd;    	
        Locale localPadrao = Locale.getDefault();

        tabela.setRowCount(0);
        rs = executaProcedureSelect(query);
                
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
        
        if(tabela.getColumnCount() == 0){
            //Adiciona as colunas
            for(int i = 0; i < indice; i++){
                tabela.addColumn(campos[i]);	
            }
        }
        
        Object row[] = new Object[indice];
        Object dadosCampos[] = new Object[indice];
        
        while(rs.next()) {            	
            for(int i = 0; i < indice; i++){

                dadosCampos[i] = rs.getObject(campos[i]);

                if(dadosCampos[i] instanceof Date){                        
                    DateFormat formatoData = DateFormat.getDateInstance(2,localPadrao);
                    dadosCampos[i] = formatoData.format(dadosCampos[i]);
                }

                row[i] = dadosCampos[i];
            }
            tabela.addRow(row);
        }       

        try {
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConexaoBanco.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    /**
     * Este método preenche uma JTable com os dados provindos do método Executar .
     * @param tabela o nome da TableModel que você deseja preencher
     * @param query select da tabela que você deseja trazer os dados
     * @throws java.sql.SQLException
     */    
    public void preencheTabelaSelect(DefaultTableModel tabela,  String query) throws SQLException{
        
    	ResultSet rs;
        ResultSetMetaData rsmd;    	
        Locale localPadrao = Locale.getDefault();

        tabela.setRowCount(0);
        rs = executar(query);
                
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
        
        if(tabela.getColumnCount() == 0){
            //Adiciona as colunas
            for(int i = 0; i < indice; i++){
                tabela.addColumn(campos[i]);	
            }
        }
        
        Object row[] = new Object[indice];
        Object dadosCampos[] = new Object[indice];
        
        while(rs.next()) {            	
            for(int i = 0; i < indice; i++){

                dadosCampos[i] = rs.getObject(campos[i]);

                if(dadosCampos[i] instanceof Date){                        
                    DateFormat formatoData = DateFormat.getDateInstance(2,localPadrao);
                    dadosCampos[i] = formatoData.format(dadosCampos[i]);
                }

                row[i] = dadosCampos[i];
            }
            tabela.addRow(row);
        }       

        try {
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConexaoBanco.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
        
    /**
     * Este executa procedures do tipo Update, Insert e Delete.
     * @param procedure Passar a string da procedure
     */
    public void executaProcedure(String procedure) throws SQLException{        
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
    
    public ResultSet executaProcedureSelect (String query){
        try {
            conectar();
            CallableStatement cs = c.prepareCall("{? = call " + query + "}");

            //Registra o parâmetro para retorna da função
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            //Converte o resultado em um resultset
            ResultSet rs = (ResultSet)cs.getObject(1);
          
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            desconectar();
        }       
        return null;
    }
    
    
    
    
    public void preencheCombo(JComboBox combo, String sql){
        ResultSet rs;
        try {
            int index = 1;
            rs = this.executar(sql);
            while(rs.next()){
                String id = rs.getString(1);
                String nome = rs.getString(2);
                
                ComboItem cboItem = new ComboItem(id, nome, index);
                combo.addItem(cboItem);
                index++;
            }
        }
        catch(SQLException e){  
            JOptionPane.showMessageDialog(null,"Ocorreu um erro  de SQL ao listar a combobox " + e.getMessage() + " \n Favor entrar em contato com administrador");        
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Ocorreu um erro ao listar a combobox " + e.getMessage() + " \n Favor entrar em contato com administrador");
        }
                
    }    
}
