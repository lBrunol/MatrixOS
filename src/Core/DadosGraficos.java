/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.util.Arrays;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.lang.reflect.Modifier;

/**
 *
 * @author Bruno
 */
public class DadosGraficos extends JFrame {

    JPanel painelFundo;
    JTable tabela;
    JScrollPane barraRolagem;
    private DefaultTableModel modelo = new DefaultTableModel();

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
//test

    public DadosGraficos(double[] xi) {

        
        
        this.lenghtArrays = 101;

        this.xi = new double[lenghtArrays];
        this.xiExclusivos = new double[lenghtArrays];

        //this.lenghtArrays = calculaComprimentoVetores(xiExclusivos);
        this.fi = new int[lenghtArrays];
        this.fri = new double[lenghtArrays];
        this.fa = new int[lenghtArrays];
        this.fra = new double[lenghtArrays];
        this.xifi = new double[lenghtArrays];
        this.moda = new double[lenghtArrays];

        //this.xi = xi;
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

        for (int i = 0; i < xiExclusivos.length; i++) {
            if (xiExclusivos[i] == 0) {
                break;
            }
            System.out.println("XI " + xiExclusivos[i]);

        }

        for (int i = 0; i < fi.length; i++) {
            if (fi[i] == 0) {
                break;
            }
            System.out.println("FI " + fi[i]);
        }

        for (int i = 0; i < fri.length; i++) {
            if (fri[i] == 0) {
                break;
            }
            System.out.println("FRI " + fri[i]);
        }

        for (int i = 0; i < fa.length; i++) {
            if (fa[i] == 0) {
                break;
            }
            System.out.println("FA " + fa[i]);
        }

        for (int i = 0; i < fra.length; i++) {
            if (fra[i] == 0) {
                break;
            }
            System.out.println("FRA " + fra[i]);
        }

        for (int i = 0; i < xifi.length; i++) {
            if (xifi[i] == 0) {
                break;
            }
            System.out.println("XIFI " + xifi[i]);
        }

        System.out.println("Soma XI " + this.somaXi);
        System.out.println("Soma FI " + this.somaFi);
        System.out.println("Soma XIFI " + this.somaXiFi);
        System.out.println("MÉDIA " + this.media);
        System.out.println("MEDIANA " + this.mediana);

        for (int i = 0; i < moda.length; i++) {
            if (moda[i] == 0) {
                break;
            }
            System.out.println("MODA  " + moda[i]);
        }

        Object[][] obj;
        obj = new Object[fi.length][6];
        obj = this.getDados();

        for (int i = 0; i < fi.length; i++) {
            for (int j = 0; j < 6; j++) {
                System.out.println(obj[i][j]);
            }
        }
        
        //criaJTable();
	//criaJanela();
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
            auxFra[i] = (fa[i] * 100) / auxSomaFi;
        }

        return auxFra;
    }

    private double[] calculaFri(int[] fi) {

        double[] auxFri = new double[fi.length];
        double auxSomaFi = this.somaFi(fi);

        for (int i = 0; i < fi.length; i++) {
            auxFri[i] = (fi[i] * 100) / auxSomaFi;
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

        auxMedia = somaXi / somaFi;

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

    public DadosGraficos() {
		
	}
    
    
    public void criaJanela() {
        barraRolagem = new JScrollPane(tabela);
        painelFundo = new JPanel();
        painelFundo.setLayout(new BorderLayout());
        painelFundo.add(BorderLayout.CENTER, barraRolagem);
        getContentPane().add(painelFundo);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 320);
        setTitle("Variável Discreta - Valores totais das Ordens de Serviço");
        centralize();
        setVisible(true);

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

    private void criaJTable() {
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
                System.out.println(rs.getDouble(1));                
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

    public static void main(String[] args) {
        double[] xii = new double[24];

        xii[0] = 10;
        xii[1] = 11;
        xii[2] = 11;
        xii[3] = 11;
        xii[4] = 12;
        xii[5] = 12;
        xii[6] = 12;
        xii[7] = 12;
        xii[8] = 13;
        xii[9] = 13;
        xii[10] = 13;
        xii[11] = 13;
        xii[12] = 13;
        xii[13] = 14;
        xii[14] = 14;
        xii[15] = 14;
        xii[16] = 14;
        xii[17] = 14;
        xii[18] = 14;
        xii[19] = 14;
        xii[20] = 15;
        xii[21] = 15;
        xii[22] = 16;
        xii[23] = 17;

        DadosGraficos d = new DadosGraficos(xii);
        d.criaJTable();
	d.criaJanela();
        d.setVisible(true);
    }
}
