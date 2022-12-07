package classesDAO;

import entidades.Colaborador;
import entidades.Setor;

import java.util.ArrayList;
import java.util.List;

public class ColaboradorDAO {

    static List<Colaborador> colaboradores = new ArrayList<>();

    public static void carregarDados() {
        Setor setor = new Setor();
        setor.setNomeSetor("COMERCIAL");
        Colaborador admin = new Colaborador(1, "admin", "admin", "12345678", setor);
        Setor setor2 = new Setor();
        Colaborador colaborador = new Colaborador(2,"colaborador", "colaborador","12345678", setor2);
        salvar(colaborador);
        salvar(admin);
    }

    public static void salvar(Colaborador colaborador) {
        colaboradores.add(colaborador);
    }
    public static void remover(Object colaborador) {
        colaboradores.remove(colaborador);
    }


    public static Integer maiorIdCadastrado() {
        return colaboradores.get((colaboradores.size()-1)).getId();
    }

    public static Colaborador buscarPorLoginESenha(String login, String senha) {
//        Colaborador colaboradorFiltrado = new Colaborador();

        for (Colaborador colaborador: colaboradores) {
            if (colaborador.getLogin().equals(login) && colaborador.getSenha().equals(senha)) {
                return colaborador;
            }
        }

        return null;
    }

    public static List<Colaborador> buscarTodos() {
        return colaboradores;
    }

    public static Object[]findColaboradorInArray(){
            List<Colaborador> colaboradorBusca = ColaboradorDAO.buscarTodos();
            List<String> colaboradorNome = new ArrayList<>();

        for (Colaborador colaborador1 : colaboradorBusca) {
            colaboradorNome.add(colaborador1.getNome());
        }
        return colaboradorNome.toArray();
    }

    public static List<Colaborador> buscarPorNome(Object nome) {
        List<Colaborador> colaboradorFiltrado = new ArrayList<>();
        for (Colaborador busca : colaboradores){
            if(busca.getNome().equals(nome)){
                colaboradorFiltrado.add(busca);
            }
        }
        return colaboradorFiltrado;
    }
}
