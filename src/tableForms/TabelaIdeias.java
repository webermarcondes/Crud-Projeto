package tableForms;

import entidades.Ideia;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class TabelaIdeias extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    public static final int INDEX_COLABORADOR = 0;
    public static final int INDEX_SETOR = 1;
    public static final int INDEX_TIPO = 2;
    public static final int INDEX_TITULO = 3;
    public static final int INDEX_DESCRICAO = 4;
    public static final int INDEX_FEEDBACK = 5;

    public static final int INDEX_LIKES = 6;
    public static final int INDEX_DESLIKES = 7;
    public static final int INDEX_ESCONDIDO = 8;

    protected String[] nomeColunas;
    protected Vector<Ideia> vetorDados;

    public TabelaIdeias(String[] columnNames, Vector<Ideia> vetorDados) {
        this.nomeColunas = columnNames;
        this.vetorDados = vetorDados;
    }

    @Override
    public String getColumnName(int column) {
        return nomeColunas[column];
    }

    @Override
    public boolean isCellEditable(int linha, int coluna) {
        if (coluna == INDEX_ESCONDIDO) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        Ideia registroIdeia = (Ideia) vetorDados.get(linha);
        switch (coluna) {
            case INDEX_COLABORADOR:
                return registroIdeia.getColaborador().getNome();
            case INDEX_SETOR:
                return registroIdeia.getSetor().getNomeSetor();
            case INDEX_TIPO:
                return registroIdeia.getData();
            case INDEX_TITULO:
                return registroIdeia.getTitulo();
            case INDEX_DESCRICAO:
                return registroIdeia.getDescricao();
            case INDEX_FEEDBACK:
                return registroIdeia.getFeedback();
            case INDEX_LIKES:
                System.out.println("b");
            case INDEX_DESLIKES:
                System.out.println("a");
            default:
                return new Object();
        }
    }

    @Override
    public int getRowCount() {
        return vetorDados.size();
    }

    @Override
    public int getColumnCount() {
        return nomeColunas.length;
    }
}

