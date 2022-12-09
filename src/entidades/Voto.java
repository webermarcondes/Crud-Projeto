package entidades;

import entidades.Ideia;
import enums.OpcaoVoto;

public class Voto {
    private Integer id;
    private Ideia ideia;
    private OpcaoVoto opcaoVoto;
    private Colaborador colaborador;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Ideia getIdeia() {
        return ideia;
    }

    public void setIdeia(Ideia ideia) {
        this.ideia = ideia;
    }

    public OpcaoVoto getOpcaoVoto() {
        return opcaoVoto;
    }

    public void setOpcaoVoto(OpcaoVoto opcaoVoto) {
        this.opcaoVoto = opcaoVoto;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }
}
