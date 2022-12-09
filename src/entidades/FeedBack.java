package entidades;

import java.time.LocalDate;

public class FeedBack {
    private String id;
    private String descricao;
    private String data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FeedBack{" + "\n" +
                "id='" + id + "\n" +
                ", descricao='" + descricao + "\n" +
                ", data=" + data;
    }
}
