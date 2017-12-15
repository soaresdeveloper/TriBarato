package br.com.soaresdeveloper.tribarato.entidades;

/**
 * Created by Soares on 14/12/2017.
 */

public class Usuario {

    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private String estado;
    private String dataNascimento;
    private String sexo;

    public Usuario() {
    }

    public Usuario(String nome, String sobrenome, String estado, String dataNascimento, String sexo) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.estado = estado;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
    }

    public String getNomeCompleto(){
        if (nome != null && sobrenome != null) {
            return nome.concat(" ").concat(sobrenome);
        }else{
            return "";
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
