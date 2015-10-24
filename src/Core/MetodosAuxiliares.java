/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.awt.Component;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author CASA
 */
public class MetodosAuxiliares {
    
    //Instância a classe local e pega o local padrão da JVM.
    //Isto serve para formatar datas e moeda
    private static final Locale localPadrao = Locale.getDefault();
    
    //Constantes das mascaras de texto
    public static final String MASCARA_CPF = "###.###.###-##";
    public static final String MASCARA_RG = "##.###.###-#";
    public static final String MASCARA_TELEFONE = "(##) ####-####";
    public static final String MASCARA_DATA = "##/##/####";
    public static final String MASCARA_CNPJ = "##.###.###/####-##";
    public static final String MASCARA_CEP = "#####-###";
    
    public MetodosAuxiliares(){
    
    }
    
    /** Função que calcula um valor total a partir do valor unitário * a quantidade
     * 
     * @param valor variavel do tipo float que serve como valor unitário
     * @param quantidade variável do tipo inteiro que serve para calcular o valor total
     * @return resultado valor total
     */
    public float calculaValorTotal(float valor, int quantidade){
        float resultado;
        
        resultado = valor * quantidade;
        
        return resultado;
    }
    /** Retorna a mascara
     * 
     * @param mascara variavel do tipo string que contém a mascara, utilizar as constantes definidas nesta classe
     * @return ms retorna um objeto do tipo MaskFormatter com a mascara
     */
    public MaskFormatter inseriMascara (String mascara){
        MaskFormatter ms = new MaskFormatter();     
        try {                   
            ms.setMask(mascara);
            ms.setPlaceholderCharacter(' ');
        } catch (Exception e) {            
            try {
                ms.setMask("");
            } catch (ParseException err) {
                JOptionPane.showMessageDialog(null, "Erro grave, favor reportar ao administrador do sistema");
            }
            return ms;
        }       
        return ms;
    }
    
    /** Método para inserir a formatação de moeda em um valor
     * 
     * @param valor passar um valor do tipo float para que seja formatado em moeda
     * @return valorFormatado retorna o valor String com a formatação
     */
    public String formataValor(float valor){
        
        String valorFormatado;
        
        //Cria uma variável para pegar o tipo de formatação da moeda
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(localPadrao);

        //Atribui a string formatada
        valorFormatado = formatoMoeda.format(valor);
        
        return valorFormatado;
    }
    
    public String formataData(Date data){
        String dataFormatada;
        DateFormat formatoData = DateFormat.getDateInstance(2,localPadrao);
        dataFormatada = formatoData.format(data);
        
        return dataFormatada;
    }
    
    
    
    /** Método para inserir a formatação de moeda em um valor de uma tabela
     * 
     * @param tabela passar o JTable no qual a coluna que você deseja formatar está
     * @param coluna passar a coluna onde os valores estã
     */    
    public void formataValorTabela(JTable tabela, int coluna){
        int j;
        float valor;
        String valorFormatado;
        
        for(j=0; j < tabela.getRowCount(); j++){
            valor = Float.parseFloat(tabela.getValueAt(j, coluna).toString());
            valorFormatado = this.formataValor(valor);
            tabela.setValueAt(valorFormatado, j, coluna);
        }
    }
    
    public String removeCaracteresString(String valor){
        // Remove todas . da string 
        valor = valor.replaceAll("\\.?", "");  
        // Remove todas , da string  
        valor = valor.replaceAll(",?", "");  
        // Remove todas - da string  
        valor = valor.replaceAll("-?", "");  
        // Remove todas : da string       
        valor = valor.replaceAll(":?", "");  
        // Remove todas ( da string  
        valor = valor.replaceAll("\\(?", "");  
        // Remove todas ) da string  
        valor = valor.replaceAll("\\)?", "");  
        // Remove todas ª da string  
        valor = valor.replaceAll("ª?", "");  
        // Remove todas | da string  
        valor = valor.replaceAll("\\|?", "");  
        // Remove todas \ da string       
        valor = valor.replaceAll("\\\\?", "");
        // Remove todas espaço da string       
        valor = valor.replaceAll(" ?", "");
        
        return valor;
    }
    
    public float removeCaracteresFloat(String valor){
        float valorRetorno;
        
        // Remove todas . da string 
        valor = valor.replaceAll("\\.?", "");  
        // Remove todas , da string  
        valor = valor.replaceAll(",", ".");  
        // Remove todas - da string  
        valor = valor.replaceAll("-?", "");  
        // Remove todas : da string       
        valor = valor.replaceAll(":?", "");  
        // Remove todas ( da string  
        valor = valor.replaceAll("\\(?", "");  
        // Remove todas ) da string  
        valor = valor.replaceAll("\\)?", "");  
        // Remove todas ª da string  
        valor = valor.replaceAll("ª?", "");  
        // Remove todas | da string  
        valor = valor.replaceAll("\\|?", "");  
        // Remove todas \ da string       
        valor = valor.replaceAll("\\\\?", "");
        // Remove todas espaço da string       
        valor = valor.replaceAll(" ?", "");
        // Remove todas espaço da string       
        valor = valor.replace("R$", "");
        
        valorRetorno = Float.parseFloat(valor);
        
        return valorRetorno;
    }
    
    public float removeCaracteresInt(String valor){
        int valorRetorno;
        
        // Remove todas . da string 
        valor = valor.replaceAll("\\.?", "");  
        // Remove todas , da string  
        valor = valor.replaceAll(",?", "");  
        // Remove todas - da string  
        valor = valor.replaceAll("-?", "");  
        // Remove todas : da string       
        valor = valor.replaceAll(":?", "");  
        // Remove todas ( da string  
        valor = valor.replaceAll("\\(?", "");  
        // Remove todas ) da string  
        valor = valor.replaceAll("\\)?", "");  
        // Remove todas ª da string  
        valor = valor.replaceAll("ª?", "");  
        // Remove todas | da string  
        valor = valor.replaceAll("\\|?", "");  
        // Remove todas \ da string       
        valor = valor.replaceAll("\\\\?", "");
        // Remove todas espaço da string       
        valor = valor.replaceAll(" ?", "");
        
        valorRetorno = Integer.parseInt(valor);
        
        return valorRetorno;
    }
    /**
     * Este método itera sobre todos os componentes provindos de uma lista que vem da classe MontaInterfaces
     * @param lista passar um arraylist com os componentes
     */
    public void limpaCampos(ArrayList<JComponent> lista){        
        //Loop que itera sobre a lista
        for (Component C : lista){
            //Verifica se os tipos são de caixas de texto, se sim ele limpa os campos
            if (C instanceof JTextField || C instanceof JTextArea || C instanceof JFormattedTextField){
                ((JTextComponent) C).setText(""); //abstract superclass
            //Verifica se é do tipo combobox se sim ele manda para o indice 0 da lista
            }else if(C instanceof JComboBox){
                ((JComboBox) C).setSelectedIndex(0); //abstract superclass
            }
            
        }
    }
}