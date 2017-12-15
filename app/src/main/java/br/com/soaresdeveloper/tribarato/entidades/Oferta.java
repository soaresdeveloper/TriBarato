package br.com.soaresdeveloper.tribarato.entidades;

/**
 * Created by soares on 14/12/17.
 */

public class Oferta {

    private Usuario usuario;
    private String titulo;
    private String descricao;
    private String preco;
    private String endereco;
    private String data;

    public Oferta() {
    }

    public Oferta(Usuario usuario, String titulo, String descricao, String preco, String endereco, String data) {
        this.usuario = usuario;
        this.titulo = titulo;
        this.descricao = descricao;
        this.preco = preco;
        this.endereco = endereco;
        this.data = data;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
