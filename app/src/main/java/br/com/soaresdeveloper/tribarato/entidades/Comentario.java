package br.com.soaresdeveloper.tribarato.entidades;

/**
 * Created by Soares on 17/12/2017.
 */

public class Comentario {

    private Usuario usuario;
    private String mensagem;
    private String data;

    public Comentario() {
    }

    public Comentario(Usuario usuario, String mensagem, String data) {
        this.usuario = usuario;
        this.mensagem = mensagem;
        this.data = data;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
