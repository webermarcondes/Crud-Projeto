package entidades;

import classesDAO.Setor2;
import enums.Setor;

import java.time.LocalDate;

public class Ideia3 {

    private Integer id;
    private String titulo;
    private String descricao;
    private LocalDate data;
    private Colaborador2 colaborador2;
    private Setor setor;
    private Setor2 setor2;

    public String feedback;

    private String status;

    public Ideia3() {

    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Colaborador2 getColaborador() {
        return colaborador2;
    }

    public void setColaborador(Colaborador2 colaborador2) {
        this.colaborador2 = colaborador2;
    }

    public Setor2 getSetor2() {
        return setor2;
    }

    public void setSetor2(Setor2 setor2) {
        this.setor2 = setor2;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ideia{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", data=" + data +
                ", colaborador=" + colaborador2 +
                ", setor=" + setor +
                '}';
    }
}
