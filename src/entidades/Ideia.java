package entidades;

import enums.Setor;

import java.time.LocalDate;

public class Ideia {

    private String id;
    private String titulo;
    private String descricao;
    private LocalDate data;
    private Colaborador colaborador;
    private Setor setor;
    private FeedBack feedBack;



    public Ideia() {

    }

    public Setor getSetor() {
        return setor;
    }

    public FeedBack getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(FeedBack feedBack) {
        this.feedBack = feedBack;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    @Override
    public String toString() {
        return "Ideia{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", data=" + data +
                ", colaborador=" + colaborador +
                ", setor=" + setor +
                ", feedBack=" + feedBack +
                '}';
    }
}
