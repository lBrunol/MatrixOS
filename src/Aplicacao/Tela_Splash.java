/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplicacao;

import java.awt.Color;
import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

public class Tela_Splash extends JWindow {

    JProgressBar barraDeProgresso;

    public Tela_Splash() throws InterruptedException {

        int w = this.getToolkit().getScreenSize().width;
        int h = this.getToolkit().getScreenSize().height;
        int x = (w - 521) / 2;
        int y = (h - 335) / 2;

        JLabel img = new JLabel(new ImageIcon(getClass().getResource("/imagens/logotipo.jpg")));
        img.setLocation(new Point(0, 0));
        img.setSize(522, 261);

        this.setLayout(null);
        this.add(img);
        this.setLocation(new Point(x, y));
        this.setSize(522, 282);
        this.setVisible(true);

        barraDeProgresso = new JProgressBar();
        barraDeProgresso.setBackground(new Color(0, 102, 52));
        barraDeProgresso.setBounds(0, 260, 520, 20);
        barraDeProgresso.setStringPainted(true);
        this.add(barraDeProgresso);

        new Thread() {
            public void run() {
                for (int progress = 0; progress < 101; progress++) {
                    try {

                        barraDeProgresso.setValue(progress);
                        sleep(80);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                }

            }

        }.start();

        Thread.sleep(9500);

        this.setVisible(false);

    }

    public static void main(String[]args) throws InterruptedException {
        new Tela_Splash();
         
    }

}
