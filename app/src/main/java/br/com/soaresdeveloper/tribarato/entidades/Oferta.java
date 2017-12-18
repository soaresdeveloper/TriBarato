package br.com.soaresdeveloper.tribarato.entidades;

import java.util.List;

/**
 * Created by soares on 14/12/17.
 */

public class Oferta {

    private Usuario usuario;
    private List<Comentario> comentarios;
    private String titulo;
    private String descricao;
    private String preco;
    private String estado;
    private String cidade;
    private String local;
    private String site;
    private String data;

    public Oferta() {
    }

    public Oferta(Usuario usuario, List<Comentario> comentarios, String titulo, String descricao, String preco, String estado, String cidade, String local, String site, String data) {
        this.usuario = usuario;
        this.comentarios = comentarios;
        this.titulo = titulo;
        this.descricao = descricao;
        this.preco = preco;
        this.estado = estado;
        this.cidade = cidade;
        this.local = local;
        this.site = site;
        this.data = data;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
