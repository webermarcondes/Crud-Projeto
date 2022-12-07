package classesDAO;

import entidades.Setor;

import java.util.ArrayList;
import java.util.List;

public class SetorDAO {

    public static List<Setor> setorList = new ArrayList<>();

    public static void salvarSetor(Setor setor){
        setorList.add(setor);
    }

    public static List<Setor> buscarTodos(){
        return setorList;
    }

    public static Object[] findSetorInArray(){
        List<Setor> setorBusca = SetorDAO.buscarTodos();
        List<String> setorTitulo = new ArrayList<>();

        for (Setor setor : setorBusca) {
            setorTitulo.add(setor.getNomeSetor());
        }
        return setorTitulo.toArray();
    }
}
