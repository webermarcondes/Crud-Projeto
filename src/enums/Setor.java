package enums;

public enum Setor {
    COMERCIAL,
    MARKETING,
    PRODUCAO,
    SERVICOS,
    SISTEMAS;

    public static Setor getSetorById(Integer id) {
        if (id == 0) {
            return  COMERCIAL;
        }

        else if (id == 1) {
            return MARKETING;
        }

        else if(id == 2) {
            return PRODUCAO;
        }

        else if(id == 3) {
            return SERVICOS;
        }

        return SISTEMAS;
    }
}
