package Aplicacao;
import Core.Janela;
import java.awt.*;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
public class Teste extends Janela{
    
    public Teste(){ 
        super("Teste" , new Dimension(800, 800));
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void main(String args[]){
        
        Teste t = new Teste();
        t.setVisible(true);
        
    }
    
    void initComponent(){        
        
    }
}
