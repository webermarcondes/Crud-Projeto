package entidades;

import enums.Setor;

public class Colaborador {
    private Integer id;
    private String nome;
    private String login;
    private String senha;
    private Setor setor;


    public Colaborador() {

    }

    public Colaborador(Integer id, String nome, String login, String senha, Setor setor) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.setor = setor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome (String nome) throws NullPointerException {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    @Override
    public String toString() {
        return "Colaborador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", setor=" + setor +
                '}';
    }
}
