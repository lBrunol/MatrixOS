/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

/**
 *
 * @author CASA
 */
public class DadosGraficos {
    private int lenghtArrays = 1;
    private double[] xi = new double[150];
    private double[] xiExclusivos = new double[150];
    private int[] fi = new int[150];
    private double[] fri  =new double[150];
    private double[] fa = new double[150];
    private double[] fra = new double[150];
    private double[] xifi = new double[150];
    private double somaFi = 0;
    private double somaXi = 0;
    private double somaXiFi = 0;    
    private double media;
    private double mediana;
    private double[] x;

    
    public DadosGraficos(double[] xi){
        
        this.lenghtArrays = xi.length;        
        
        this.fi = this.calculaFi(this.ordenaXi(xi));
        this.xiExclusivos = this.valoresExclusivosXi(xi);
        
        for(int i = 0; i < fi.length; i++){
            if(fi[i] == 0){
                break;
            }
            System.out.println(fi[i]);        
        }
        
        for(int i = 0; i < xiExclusivos.length; i++){
            if(xiExclusivos[i] == 0){
                break;
            }
            System.out.println(xiExclusivos[i]);        
        }
        
        System.out.println(this.somaFi(fi));
        System.out.println(this.somaXi(xi));
    }
    
    private double[] ordenaXi(double[] xi){
        double[] xiReordenado;
        xiReordenado = new double[150];
        
        xiReordenado = this.insertionSort(xi);
        
        return xiReordenado;
    }
    
    private int[] calculaFi(double[] xi){
        
        int[] auxFi = new int[150];
        
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
    
    private double[] valoresExclusivosXi(double[] xi){
        
        double[] valoresExclusivos = new double[150];
        
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

    /**
     * @return the xi
     */
    public double[] getXi() {
        return xi;
    }

    /**
     * @param xi the xi to set
     */
    public void setXi(double[] xi) {
        this.xi = xi;
    }
    
    public static void main(String[] args){
        double[] xii = new double[10];
        xii[0] = 5;
        xii[1] = 5;
        xii[2] = 6;
        xii[3] = 4;
        xii[4] = 4;
        xii[5] = 4;
        xii[6] = 3;
        xii[7] = 2;
        xii[8] = 5;
        xii[9] = 2;
        
        DadosGraficos d = new DadosGraficos(xii);
    }
}
