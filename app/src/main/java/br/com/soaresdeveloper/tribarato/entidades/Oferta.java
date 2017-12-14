package br.com.soaresdeveloper.tribarato.entidades;

/**
 * Created by soares on 14/12/17.
 */

public class Oferta {

    private String autor;
    private String titulo;
    private String descricao;
    private String preco;
    private String endereco;

    public Oferta() {
    }

    public Oferta(String autor, String titulo, String descricao, String preco, String endereco) {
        this.autor = autor;
        this.titulo = titulo;
        this.descricao = descricao;
        this.preco = preco;
        this.endereco = endereco;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
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
}
