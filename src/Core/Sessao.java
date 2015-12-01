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
public class Sessao {
    // Variável estática que conterá a instancia da classe
    private static Sessao instance;
    private static boolean adm;
    private static String user;
    private static String senha;

    /**
     * @return the adm
     */
    public static boolean isAdm() {
        return adm;
    }

    /**
     * @param aAdm the adm to set
     */
    public static void setAdm(boolean aAdm) {
        adm = aAdm;
    }

    /**
     * @return the user
     */
    public static String getUser() {
        return user;
    }

    /**
     * @return the senha
     */
    public static String getSenha() {
        return senha;
    }

    /**
     * @param aUser the user to set
     */
    public static void setUser(String aUser) {
        user = aUser;
    }

    /**
     * @param aSenha the senha to set
     */
    public static void setSenha(String aSenha) {
        senha = aSenha;
    }

    // Construtor privado (suprime o construtor público padrão).
    private Sessao() {}

    // Método público estático de acesso único ao objeto!
    public static Sessao getInstance() {
        if (instance == null)
            instance = new Sessao();
        return instance;
    }

    /*
    Pode criar outros métodos que precise aqui, como getters e setters.
    */
}
