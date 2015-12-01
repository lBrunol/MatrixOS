/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.util.Arrays;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Bruno
 */
public class DadosGraficos extends JFrame {
    
    MetodosAuxiliares auxiliar = new MetodosAuxiliares();
        
    JPanel painelFundo;
    JTable tabela;
    JScrollPane barraRolagem;
    private DefaultTableModel modelo = new DefaultTableModel();
    private JLabel lblmedia;
    private JLabel lblmediana;
    private JLabel lblmoda;
    private JLabel lbltitulo;
    private JLabel icoimg;

    String[] colunas = {"Xi", "Fi", "Fri", "Fa", "Fra", "XiFi"};

    private int lenghtArrays = 1;
    private double[] xi;
    private double[] xiExclusivos;
    private int[] fi;
    private double[] fri;
    private int[] fa;
    private double[] fra;
    private double[] xifi;
    private double somaFi = 0;
    private double somaXi = 0;
    private double somaXiFi = 0;
    private double media;
    private double mediana;
    private double moda[];

    public DadosGraficos() {
        
        this.lenghtArrays = 101;

        this.xi = new double[lenghtArrays];
        this.xiExclusivos = new double[lenghtArrays];

        this.fi = new int[lenghtArrays];
        this.fri = new double[lenghtArrays];
        this.fa = new int[lenghtArrays];
        this.fra = new double[lenghtArrays];
        this.xifi = new double[lenghtArrays];
        this.moda = new double[lenghtArrays];

        this.xi = this.getDadosXi();
        this.fi = this.calculaFi(this.ordenaXi(this.xi));
        this.xiExclusivos = this.valoresExclusivosXi(this.xi);
        this.fa = this.calculaFa(fi);
        this.fra = this.calculaFra(fa);
        this.fri = this.calculaFri(fi);
        this.xifi = this.calculaXiFi(xiExclusivos, fi);
        this.somaXi = this.somaXi(this.xi);
        this.somaFi = this.somaFi(this.fi);
        this.somaXiFi = this.somaXiFi(xifi);
        this.media = this.calculaMedia(somaXi, somaFi);
        this.mediana = this.calculaMediana(somaFi, this.ordenaXi(this.xi));
        this.moda = this.calculaModa(this.fi, this.xiExclusivos);	
    }

    private int[] calculaFi(double[] xi) {

        int[] auxFi = new int[xi.length];

        /**
         * @aux recebe a quantidade de cada numero do xi
         */
        int aux;
        /**
         * @pos controla a posição do vetor de fi
         */
        int pos;

        aux = 1;
        pos = 0;

        for (int i = 0; i < xi.length; i++) {
            if (i == 0) {
                auxFi[pos] = aux;
            } else {
                if (xi[i] == xi[i - 1]) {
                    aux++;
                    auxFi[pos] = aux;
                } else {
                    pos++;
                    aux = 1;
                    auxFi[pos] = aux;
                }
            }
        }

        return auxFi;

    }

    private int[] calculaFa(int[] fi) {

        int[] auxFa = new int[fi.length];

        for (int i = 0; i < fi.length; i++) {
            if (i == 0) {
                auxFa[0] = fi[0];
            } else {
                if (fi[i] == 0) {
                    break;
                }
                auxFa[i] = fi[i] + auxFa[i - 1];
            }
        }

        return auxFa;
    }

    private double[] calculaFra(int[] fa) {

        double[] auxFra = new double[fa.length];
        double auxSomaFi = this.somaFi(fi);

        for (int i = 0; i < fa.length; i++) {
            auxFra[i] = Math.round(((fa[i] * 100) / auxSomaFi)*100)/100;
        }

        return auxFra;
    }

    private double[] calculaFri(int[] fi) {

        double[] auxFri = new double[fi.length];
        double auxSomaFi = this.somaFi(fi);

        for (int i = 0; i < fi.length; i++) {
            auxFri[i] = Math.round(((fi[i] * 100) / auxSomaFi)*100)/100;
        }

        return auxFri;
    }

    private double[] calculaXiFi(double[] xiExclusivos, int[] fi) {

        double[] auxXiFi = new double[fi.length];

        for (int i = 0; i < fi.length; i++) {
            auxXiFi[i] = xiExclusivos[i] * fi[i];
        }

        return auxXiFi;
    }

    private double calculaMedia(double somaXi, double somaFi) {
        double auxMedia;

        auxMedia = Math.round((somaXi / somaFi)*100)/100;

        return auxMedia;
    }

    private double calculaMediana(double somaFi, double[] xi) {
        int aux;
        double posMediana, auxMediana;
        boolean par;

        if (somaFi % 2 == 0) {
            par = true;
        } else {
            par = false;
        }

        if (par == true) {
            posMediana = somaFi / 2;
            posMediana = Math.round(posMediana);
            aux = (int) posMediana;
            auxMediana = ((xi[aux - 1]) + (xi[aux])) / 2;
        } else {
            posMediana = (somaFi + 1) / 2;
            posMediana = Math.round(posMediana);
            aux = (int) posMediana;
            auxMediana = (xi[aux - 1]) / 2;
        }

        return auxMediana;

    }

    private double[] calculaModa(int[] fi, double[] xiExclusivos) {

        double auxModa[], indiceModa[];
        int fiOrdenado[];
        int j;
        int valorMaximo;
        Object result;
        auxModa = new double[xiExclusivos.length];
        indiceModa = new double[fi.length];
        fiOrdenado = new int[fi.length];

        j = 0;
        indiceModa[0] = -1;

        for (int i = 0; i < fi.length; i++) {
            if (fi[i] == 0) {
                break;
            }
            fiOrdenado = Arrays.copyOf(fi, i + 1);
        }

        Arrays.sort(fiOrdenado);

        valorMaximo = fiOrdenado[fiOrdenado.length - 1];

        for (int i = 0; i < fi.length; i++) {
            if (fi[i] == valorMaximo) {
                indiceModa[i] = i;
            }
        }

        for (int i = 0; i < xiExclusivos.length; i++) {
            if (indiceModa[i] == i) {
                auxModa[j] = xiExclusivos[i];
                j++;
            }
        }
        return auxModa;
    }

    private double somaXi(double[] xi) {
        double somXi;

        somXi = 0;

        for (int i = 0; i < xi.length; i++) {
            somXi = somXi + xi[i];
        }

        return somXi;

    }

    private double somaFi(int[] fi) {
        double somFi;

        somFi = 0;

        for (int i = 0; i < fi.length; i++) {
            somFi = somFi + fi[i];
        }

        return somFi;

    }

    private double somaXiFi(double[] xifi) {
        double somXiFi;

        somXiFi = 0;

        for (int i = 0; i < xifi.length; i++) {
            somXiFi = somXiFi + xifi[i];
        }

        return somXiFi;

    }

    private double[] valoresExclusivosXi(double[] xi) {

        double[] valoresExclusivos = new double[xi.length];

        /**
         * @aux recebe a quantidade de cada numero do xi
         */
        int aux;
        /**
         * @pos controla a posição do vetor de fi
         */
        int pos;

        aux = 1;
        pos = 0;

        for (int i = 0; i < xi.length; i++) {
            if (i == 0) {
                valoresExclusivos[pos] = xi[i];
            } else {
                if (xi[i] != xi[i - 1]) {
                    pos++;
                    valoresExclusivos[pos] = xi[i];
                }
            }
        }

        return valoresExclusivos;
    }

    private double[] ordenaXi(double[] xi) {
        double[] xiReordenado;
        xiReordenado = new double[xi.length];

        xiReordenado = this.insertionSort(xi);

        return xiReordenado;
    }

    public double[] insertionSort(double[] input) {

        double temp;
        for (int i = 1; i < input.length; i++) {
            for (int j = i; j > 0; j--) {
                if (input[j] < input[j - 1]) {
                    temp = input[j];
                    input[j] = input[j - 1];
                    input[j - 1] = temp;
                }
            }
        }
        return input;
    }   
    
    public void criaJanela() {        
        
        barraRolagem = new JScrollPane(tabela);
        painelFundo = new JPanel();
        lblmedia = new JLabel("      Média: " + String.valueOf(media));
        lblmediana = new JLabel("      Mediana: " + String.valueOf(mediana));
        lblmoda = new JLabel("      Moda: " + String.valueOf(moda[0]));
        
        Font fonte = new Font("Calibri",Font.BOLD, 23);
        lbltitulo = new JLabel("  Variável Discreta - Valor total das Ordens Emitidas");
        lbltitulo.setFont(fonte);
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/imagens/relatorio.png"));
        icoimg = new JLabel(icon);
        
        Container c = new JPanel();
        Container c2 = new JPanel();
        
        
        painelFundo.setLayout(new BorderLayout());
        c2.setLayout(new GridLayout(1,2));
        
        c2.add(lbltitulo);
        c2.add(icoimg);
        
        c.setLayout(new GridLayout(1,3));
        Font fonte2 = new Font("Calibri",Font.BOLD, 35);
                
        c.add(lblmedia);
        c.add(lblmediana);
        c.add(lblmoda);
        
        lblmedia.setFont(fonte2);
        lblmediana.setFont(fonte2);
        lblmoda.setFont(fonte2);
        
        
        painelFundo.add(BorderLayout.NORTH, c2);
        painelFundo.add(BorderLayout.CENTER, barraRolagem);
        painelFundo.add(BorderLayout.SOUTH, c);        
        
        getContentPane().add(painelFundo);
        
        setSize(1000, 600);
        setTitle("Variável Discreta - Valor total das Ordens Emitidas");
        centralize();

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

    public void criaJTable() {
        tabela = new JTable(modelo);
        modelo.addColumn("XI");
        modelo.addColumn("FI");
        modelo.addColumn("FRI");
        modelo.addColumn("FA");
        modelo.addColumn("FRA");
        modelo.addColumn("XIFI");
        tabela.getColumnModel().getColumn(0).setPreferredWidth(10);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(120);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(80);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(120);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(120);
        pesquisar(modelo);
        auxiliar.formataValorTabela(tabela, 0);
        
    }

    public void pesquisar(DefaultTableModel modelo) {
        modelo.setNumRows(0);
        
        int i, j;
        Object dados[][]=getDados();
        
        for (i = 0; i < fi.length; i++) {
            if("0.0".equals(dados[i][0].toString())){
                break;
            }
            modelo.addRow(new Object[]{dados[i][0], dados[i][1], dados[i][2], dados[i][3], dados[i][4], dados[i][5]});        
            
        }

    }

    public double[] getDadosXi() {

        ConexaoBanco cn = new ConexaoBanco();
        double dadosXi[];
        double xiSelect[];
        dadosXi = new double[100];
        int i;
        ResultSet rs;
        rs = cn.executar("SELECT ordValorTotal FROM ordemServico WHERE ROWNUM < 100 ORDER BY ordValorTotal DESC");
        i = 0;
        try {
            while (rs.next()) {              
                dadosXi[i] = rs.getDouble(1);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        xiSelect = new double[i];
        for (i = 0; i < xiSelect.length; i++) {
            xiSelect[i] = dadosXi[i];
        }

        return xiSelect;
    }

    public Object[][] getDados() {
        Object dados[][];
        dados = new Object[fi.length][6];
        int i, j;

        for (i = 0; i < fi.length; i++) {
            for (j = 0; j < 6; j++) {
                dados[i][0] = xiExclusivos[i];
                dados[i][1] = fi[i];
                dados[i][2] = fri[i];
                dados[i][3] = fa[i];
                dados[i][4] = fra[i];
                dados[i][5] = xifi[i];
            }
        }
        return dados;
    }
}
