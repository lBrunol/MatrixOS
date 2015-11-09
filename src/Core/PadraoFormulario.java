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
public interface PadraoFormulario {
    
    public void iniciaComponentes();
    public void atribuiIcones();
    public void preencheCombos();
    public void adicionaEventos();
    public void preencheTabela();
    public void setaNomes();
    public void mostraBotoesAlteracao();
    public void mostraBotoesCadastro();
    public boolean cadastrar();
    public boolean alterar();
    public boolean deletar();
}
