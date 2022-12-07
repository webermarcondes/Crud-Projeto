package classesDAO;

import entidades.Ideia;
import entidades.Setor;

import java.util.ArrayList;
import java.util.List;

public class SetorDAO {

    public static List<Setor> setorList = new ArrayList<>();

    public static void salvarSetor(Setor setor){
        setorList.add(setor);
    }

    public static void remover(Setor setor){
        setorList.remove(setor);
    }


    public static List<Setor> buscarTodos(){
        return setorList;
    }

    public static Object[] findSetorInArray(){
        List<String> setorTitulo = new ArrayList<>();

        for (Setor setor : setorList) {
            setorTitulo.add(setor.getNomeSetor());
        }
        return setorTitulo.toArray();
    }

    public static List<Setor> buscarPorTitulo(Object titulo) {
        List<Setor> setorFiltrado = new ArrayList<>();
        for (Setor busca : setorList){
            if(busca.getNomeSetor().equals(titulo)){
                setorFiltrado.add(busca);
            }
        }
        return setorFiltrado;
    }
}
