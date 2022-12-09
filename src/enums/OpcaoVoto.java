package enums;

public enum OpcaoVoto {

    LIKE,
    DESLIKE;

    public static OpcaoVoto getOpcaoByDescricao(String descricao) {
        if (descricao.equals("LIKE")) {
            return LIKE;
        }

        return DESLIKE;
    }
}
