/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.util.Arrays;

/**
 *
 * @author Bruno
 */
public class DadosGraficos {
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

    
    public DadosGraficos(double[] xi){
        
        this.lenghtArrays = xi.length;
      
        this.xi = new double[lenghtArrays];
        this.xiExclusivos = new double[lenghtArrays];
        
        //this.lenghtArrays = calculaComprimentoVetores(xiExclusivos);
        
        this.fi = new int[lenghtArrays];
        this.fri  =new double[lenghtArrays];
        this.fa = new int[lenghtArrays];
        this.fra = new double[lenghtArrays];
        this.xifi = new double[lenghtArrays];
        this.moda = new double[lenghtArrays];
        
        this.xi = xi;        
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
        
        
        for(int i = 0; i < xiExclusivos.length; i++){
            if(xiExclusivos[i] == 0){
                break;
            }
            System.out.println("XI " + xiExclusivos[i]);        
        }
        
        for(int i = 0; i < fi.length; i++){
            if(fi[i] == 0){
                break;
            }
            System.out.println("FI " + fi[i]);        
        }
        
        for(int i = 0; i < fri.length; i++){
            if(fri[i] == 0){
                break;
            }
            System.out.println("FRI " + fri[i]);
        }
        
        for(int i = 0; i < fa.length; i++){
            if(fa[i] == 0){
                break;
            }
            System.out.println("FA " + fa[i]);
        }
        
        for(int i = 0; i < fra.length; i++){
            if(fra[i] == 0){
                break;
            }
            System.out.println("FRA " + fra[i]);
        }
        
        for(int i = 0; i < xifi.length; i++){
            if(xifi[i] == 0){
                break;
            }
            System.out.println("XIFI " + xifi[i]);
        }
        
        
        
        System.out.println("Soma XI " + this.somaXi);
        System.out.println("Soma FI " + this.somaFi);
        System.out.println("Soma XIFI " + this.somaXiFi);
        System.out.println("MÉDIA " + this.media);
        System.out.println("MEDIANA " + this.mediana);
        
        for(int i = 0; i < moda.length; i++){
            if(moda[i] == 0){
                break;
            }
            System.out.println("MODA  " + moda[i]);
        }
    }
    
    
    
    private int[] calculaFi(double[] xi){
        
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
        
        for(int i = 0; i < xi.length; i++){
            if(i == 0){
                auxFi[pos] = aux;
            }else{
                if(xi[i] == xi[i -1]){
                    aux++;
                    auxFi[pos] = aux;
                }else{
                    pos++;
                    aux = 1;
                    auxFi[pos] = aux;
                }
            }
        }
        
        return auxFi;
        
    }
    
    private int[] calculaFa(int[] fi){
        
        int[] auxFa = new int[fi.length];
        
        for(int i=0; i < fi.length; i++){
            if(i==0){
                auxFa[0] = fi[0];                
            }else{
                if(fi[i] == 0){
                    break;
                }
                auxFa[i] = fi[i] + auxFa[i-1];
            }
        }
        
        return auxFa;
    }
    
    private double[] calculaFra(int[] fa){
        
        double[] auxFra = new double[fa.length];
        double auxSomaFi = this.somaFi(fi);
        
        for (int i=0; i <fa.length; i++){
            auxFra[i] = (fa[i]*100)/auxSomaFi;
        }
        
        return auxFra;
    }
    
    private double[] calculaFri(int[] fi){
        
        double[] auxFri = new double[fi.length];
        double auxSomaFi = this.somaFi(fi);
        
        for (int i=0; i <fi.length; i++){
            auxFri[i] = (fi[i]*100)/auxSomaFi;
        }
        
        return auxFri;
    }
    
    private double[] calculaXiFi (double[] xiExclusivos, int[] fi){
        
        double[] auxXiFi = new double[fi.length];
        
        for(int i=0; i < fi.length; i++){
            auxXiFi[i] = xiExclusivos[i] * fi[i];
        }
        
        return auxXiFi;
    }
    
    private double calculaMedia (double somaXi, double somaFi){
        double auxMedia;
        
        auxMedia = somaXi / somaFi;
        
        return auxMedia;
    }
    
    private double calculaMediana (double somaFi, double[] xi){
        int aux;
        double posMediana, auxMediana;
        boolean par;
        
        if(somaFi % 2 == 0){
            par = true;
        }else{
            par = false;
        }
        
        if(par== true){
            posMediana = somaFi/2;
            posMediana = Math.round(posMediana);
            aux = (int) posMediana;
            auxMediana = ((xi[aux -1 ]) + (xi[aux]))/2;
        }else{
            posMediana = (somaFi + 1)/2;
            posMediana = Math.round(posMediana);
            aux = (int) posMediana;
            auxMediana = (xi[aux - 1])/2; 
        }
        
        return auxMediana;
    
    }
    
    private double[] calculaModa (int[] fi, double[] xiExclusivos){
        
        double auxModa[], indiceModa[];
        int fiOrdenado[];
        int j;
        int valorMaximo;
        Object result;
        auxModa = new double[xiExclusivos.length];
        indiceModa = new double [fi.length];
        fiOrdenado = new int [fi.length];
        
        
        j = 0;        
        indiceModa[0] = -1;
        
        for(int i = 0; i < fi.length; i++){
            if(fi[i] == 0){
                break;
            }
            fiOrdenado = Arrays.copyOf(fi, i+1);
        }        
        
        Arrays.sort(fiOrdenado);        
       
        valorMaximo = fiOrdenado[fiOrdenado.length - 1];
        
        for (int i = 0; i < fi.length; i++) {
            if (fi[i] == valorMaximo) {
                indiceModa[i] = i;
            }
        }
        
        for (int i =0; i < xiExclusivos.length; i++){            
            if(indiceModa[i] == i){
                auxModa[j] = xiExclusivos[i];
                j++;
            }
        }        
        return auxModa;
    }
    
    private double somaXi(double[] xi){
        double somXi;
        
        somXi = 0;
        
        for(int i = 0; i < xi.length; i++){            
            somXi = somXi + xi[i];
        }
        
        return somXi;
        
    }
    
    private double somaFi(int[] fi){
        double somFi;
        
        somFi = 0;
        
        for(int i = 0; i < fi.length; i++){            
            somFi = somFi + fi[i];
        }
        
        return somFi;
        
    }
    
    private double somaXiFi(double[] xifi){
        double somXiFi;
        
        somXiFi = 0;
        
        for(int i = 0; i < xifi.length; i++){            
            somXiFi = somXiFi + xifi[i];
        }
        
        return somXiFi;
        
    }
    
    private int calculaComprimentoVetores(double [] xiExclusivos){
        int i;
        for(i = 0; i < xiExclusivos.length; i++){
            if(xiExclusivos[i] == 0){
                break;
            }                   
        }
        
        return i;
    }
    
    private double[] valoresExclusivosXi(double[] xi){
        
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
        
        for(int i = 0; i < xi.length; i++){
            if(i == 0){
                valoresExclusivos[pos] = xi[i];
            }else{
                if(xi[i] != xi[i -1]){
                    pos++;
                    valoresExclusivos[pos] = xi[i];
                }
            }
        }
        
        return valoresExclusivos;
    }
    
    private double[] ordenaXi(double[] xi){
        double[] xiReordenado;
        xiReordenado = new double[xi.length];
        
        xiReordenado = this.insertionSort(xi);
        
        return xiReordenado;
    }
    
    public double[] insertionSort(double[] input){
         
        double temp;
        for (int i = 1; i < input.length; i++) {
            for(int j = i ; j > 0 ; j--){
                if(input[j] < input[j-1]){
                    temp = input[j];
                    input[j] = input[j-1];
                    input[j-1] = temp;
                }
            }
        }
        return input;
    }
   
    public static void main(String[] args){
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
    }
}
