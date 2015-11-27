/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplicacao;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author CASA
 */
public class MenuPrincipal extends javax.swing.JFrame implements ActionListener {

    /**
     * Creates new form MenuPrincipal
     */
    public MenuPrincipal() {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        centralize();
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        txtGerenciamento = new javax.swing.JLabel();
        JButtonOrdemdeServico = new javax.swing.JButton();
        JButtonContasaReceber = new javax.swing.JButton();
        JButtonFuncionario = new javax.swing.JButton();
        JButtonServiços = new javax.swing.JButton();
        JButtonNotasFiscais = new javax.swing.JButton();
        JButtonBackupeRestauracao = new javax.swing.JButton();
        JButtonEmitente = new javax.swing.JButton();
        JButtonCliente = new javax.swing.JButton();
        JButtonAjuda = new javax.swing.JButton();
        txtAjuda = new javax.swing.JLabel();
        JButtonLogoff = new javax.swing.JButton();
        txtLogoff = new javax.swing.JLabel();
        JButtonRelatorioContasaReceber = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        JButtonOrdemdeServico1 = new javax.swing.JButton();
        JButtonOrdemdeServico2 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        JMenuUsuarios = new javax.swing.JMenu();
        JMenuCriacaoUsuarios = new javax.swing.JMenuItem();
        JMenuAlteracaoUsuarios = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Menu Principal - Matrix OS");
        setMaximumSize(new java.awt.Dimension(617, 768));
        setMinimumSize(new java.awt.Dimension(617, 768));
        setSize(new java.awt.Dimension(617, 768));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        txtGerenciamento.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtGerenciamento.setText("Menu Principal - MatrixOS");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 10, 0, 0);
        getContentPane().add(txtGerenciamento, gridBagConstraints);

        JButtonOrdemdeServico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icone-tipos-pagamento.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.gridheight = 7;
        gridBagConstraints.ipadx = 127;
        gridBagConstraints.ipady = 101;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(28, 29, 0, 0);
        getContentPane().add(JButtonOrdemdeServico, gridBagConstraints);

        JButtonContasaReceber.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/contas-receber-low.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 67;
        gridBagConstraints.ipady = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(27, 11, 0, 0);
        getContentPane().add(JButtonContasaReceber, gridBagConstraints);

        JButtonFuncionario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/funcionario.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 55;
        gridBagConstraints.ipady = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 8, 0, 0);
        getContentPane().add(JButtonFuncionario, gridBagConstraints);

        JButtonServiços.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/servicos.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 65;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 8, 0, 0);
        getContentPane().add(JButtonServiços, gridBagConstraints);

        JButtonNotasFiscais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nota-fiscal-eletronica-low.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 67;
        gridBagConstraints.ipady = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 0, 0);
        getContentPane().add(JButtonNotasFiscais, gridBagConstraints);

        JButtonBackupeRestauracao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icone-cargos-mid.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 22;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 27;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.ipady = -14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 20, 0, 20);
        getContentPane().add(JButtonBackupeRestauracao, gridBagConstraints);

        JButtonEmitente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/dados-emitente-low.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 22;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 27;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 38;
        gridBagConstraints.ipady = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(29, 20, 0, 20);
        getContentPane().add(JButtonEmitente, gridBagConstraints);

        JButtonCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/clientes.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 54;
        gridBagConstraints.ipady = 45;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 11, 0, 0);
        getContentPane().add(JButtonCliente, gridBagConstraints);

        JButtonAjuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botao-ajuda.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 22;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = -17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 34, 0, 0);
        getContentPane().add(JButtonAjuda, gridBagConstraints);

        txtAjuda.setText("Ajuda");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 22;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 44, 0, 0);
        getContentPane().add(txtAjuda, gridBagConstraints);

        JButtonLogoff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/logoff.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 34;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 14;
        gridBagConstraints.ipady = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 0);
        getContentPane().add(JButtonLogoff, gridBagConstraints);

        txtLogoff.setText("Logoff");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 46;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 0, 0);
        getContentPane().add(txtLogoff, gridBagConstraints);

        JButtonRelatorioContasaReceber.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/relatorio.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.ipadx = 87;
        gridBagConstraints.ipady = 76;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 34, 0, 0);
        getContentPane().add(JButtonRelatorioContasaReceber, gridBagConstraints);

        jLabel1.setText("Clientes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 100, 0, 0);
        getContentPane().add(jLabel1, gridBagConstraints);

        jLabel2.setText("Serviços");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 57, 0, 0);
        getContentPane().add(jLabel2, gridBagConstraints);

        jLabel3.setText("Ordens de Serviço");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 5, 0, 0);
        getContentPane().add(jLabel3, gridBagConstraints);

        jLabel4.setText("Funcionários");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 58, 0, 0);
        getContentPane().add(jLabel4, gridBagConstraints);

        jLabel5.setText("Consulta Contas a Receber");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 30, 0, 0);
        getContentPane().add(jLabel5, gridBagConstraints);

        jLabel6.setText("Consulta de Notas Fiscais");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        getContentPane().add(jLabel6, gridBagConstraints);

        jLabel7.setText("Emitente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 22;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 13;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 58, 0, 0);
        getContentPane().add(jLabel7, gridBagConstraints);

        jLabel8.setText("Cargos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 22;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 12;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 59, 0, 0);
        getContentPane().add(jLabel8, gridBagConstraints);

        JButtonOrdemdeServico1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/os.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 37;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 54;
        gridBagConstraints.ipady = 45;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 5, 0, 20);
        getContentPane().add(JButtonOrdemdeServico1, gridBagConstraints);

        JButtonOrdemdeServico2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/os.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 37;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 54;
        gridBagConstraints.ipady = 45;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 5, 0, 20);
        getContentPane().add(JButtonOrdemdeServico2, gridBagConstraints);

        jLabel9.setText("Variável Discreta - Valores totais das Ordens de Serviço");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 19, 27, 0);
        getContentPane().add(jLabel9, gridBagConstraints);

        jLabel10.setText("Tipos de Pagamento");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 54, 0, 0);
        getContentPane().add(jLabel10, gridBagConstraints);

        JMenuUsuarios.setText("Usuários");

        JMenuCriacaoUsuarios.setText("Criação de Usuários");
        JMenuUsuarios.add(JMenuCriacaoUsuarios);

        JMenuAlteracaoUsuarios.setText("Alteração de Usuários");
        JMenuUsuarios.add(JMenuAlteracaoUsuarios);

        jMenuBar1.add(JMenuUsuarios);

        setJMenuBar(jMenuBar1);

        pack();
        
        JButtonOrdemdeServico.addActionListener(this);
        JButtonOrdemdeServico1.addActionListener(this);
        JButtonContasaReceber.addActionListener(this);
        JButtonFuncionario.addActionListener(this); 
        JButtonServiços.addActionListener(this); 
        JButtonNotasFiscais.addActionListener(this); 
        JButtonBackupeRestauracao.addActionListener(this); 
        JButtonEmitente.addActionListener(this); 
        JButtonCliente.addActionListener(this); 
        JButtonAjuda.addActionListener(this);
        JButtonRelatorioContasaReceber.addActionListener(this);
        JButtonNotasFiscais.addActionListener(this);
    }// </editor-fold>         
    
    public void centralize() {	
        Dimension T = Toolkit.getDefaultToolkit().getScreenSize();

        Dimension J = getSize();

        if (J.height > T.height) setSize(J.width,T.height); 
        if (J.width > T.width) setSize(T.width,J.height); 

        setLocation((T.width - J.width )/2,(T.height-J.height)/2);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton JButtonAjuda;
    private javax.swing.JButton JButtonBackupeRestauracao;
    private javax.swing.JButton JButtonCliente;
    private javax.swing.JButton JButtonContasaReceber;
    private javax.swing.JButton JButtonEmitente;
    private javax.swing.JButton JButtonFuncionario;
    private javax.swing.JButton JButtonLogoff;
    private javax.swing.JButton JButtonNotasFiscais;
    private javax.swing.JButton JButtonOrdemdeServico;
    private javax.swing.JButton JButtonOrdemdeServico1;
    private javax.swing.JButton JButtonOrdemdeServico2;
    private javax.swing.JButton JButtonRelatorioContasaReceber;
    private javax.swing.JButton JButtonServiços;
    private javax.swing.JMenuItem JMenuAlteracaoUsuarios;
    private javax.swing.JMenuItem JMenuCriacaoUsuarios;
    private javax.swing.JMenu JMenuUsuarios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel txtAjuda;
    private javax.swing.JLabel txtGerenciamento;
    private javax.swing.JLabel txtLogoff;
    // End of variables declaration      
    
    @Override
    public void actionPerformed(ActionEvent botao) {
         if (botao.getSource() == JButtonCliente) {             
             Aplicacao.Cliente c = new Aplicacao.Cliente();
         }else if(botao.getSource() == JButtonContasaReceber){
            // Aplicacao.ConsultaContasReceber cons = new Aplicacao.ConsultaContasReceber();
             JOptionPane.showMessageDialog(null,"Em construção");
         }else if(botao.getSource() == JButtonEmitente){
             Aplicacao.CadastroDoEmitente emi = new Aplicacao.CadastroDoEmitente();
         }else if(botao.getSource() == JButtonFuncionario){
             Aplicacao.Funcionario fun = new Aplicacao.Funcionario();
         }else if(botao.getSource() == JButtonNotasFiscais){
             //Aplicacao.ConsultaNotaFiscal not = new Aplicacao.ConsultaNotaFiscal();
             JOptionPane.showMessageDialog(null,"Em construção"); 
         }else if(botao.getSource() == JButtonOrdemdeServico1){
             Aplicacao.OrdemServico os = new Aplicacao.OrdemServico();
         }else if(botao.getSource() == JButtonOrdemdeServico){
             Aplicacao.TiposPagamento tpa = new Aplicacao.TiposPagamento();
         }else if(botao.getSource() == JButtonBackupeRestauracao){
             Aplicacao.Cargos car = new Aplicacao.Cargos();
         }else if(botao.getSource() == JButtonServiços){
             Aplicacao.Servicos svc = new Aplicacao.Servicos();
         }else if(botao.getSource() == JButtonRelatorioContasaReceber){
            JOptionPane.showMessageDialog(null,"Em construção");             
           // Aplicacao.Servicos svc = new Aplicacao.Servicos();
         }
    }    
}
