package entidades;

public class FormatoSenha{

    public static Boolean verificarFormato(String senha){

        if (senha.length() == 8) {
            try {
                Long senhaNumeros = Long.parseLong(senha);
                return true;
            }
            catch (NumberFormatException e) {
                return false;

            }

        }
       return false;

    }
}
