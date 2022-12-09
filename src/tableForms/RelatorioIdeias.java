package tableForms;

import entidades.Ideia;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Vector;

public class RelatorioIdeias extends JPanel{

    private static final long serialVersionUID = 1L;

    public static final String[] nomeColunas =
            {"Colaborador", "Setor", "Data", "Título","Descrição","Feedback","Likes", "Deslikes"};

    protected JTable table;
    protected JScrollPane scroller;
    protected TabelaIdeias ideia;

    public RelatorioIdeias(Vector<Ideia> vetorDados) {
        iniciarComponentes(vetorDados);
    }

    public void iniciarComponentes(Vector<Ideia> vetorDados) {
        ideia = new TabelaIdeias(nomeColunas, vetorDados);
        table = new JTable();
        table.setModel(ideia);
        table.setSurrendersFocusOnKeystroke(true);
        scroller = new javax.swing.JScrollPane(table);
        table.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));

        TableColumn colunaEscondida = table.getColumnModel().getColumn(TabelaIdeias.INDEX_ESCONDIDO);
        colunaEscondida.setMinWidth(2);
        colunaEscondida.setPreferredWidth(2);
        colunaEscondida.setMaxWidth(2);
        setLayout(new BorderLayout());
        add(scroller, BorderLayout.CENTER);
    }

    public static void emitirRelatorio(List<Ideia> ideias) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            JFrame frame = new JFrame("Relatorio");

            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) {
                    frame.setVisible(false);
                    JOptionPane.showMessageDialog(null,"Não há nenhuma idéia");
                }
            });
            Vector<Ideia> vetorDados = new Vector<Ideia>();

            for (Ideia ideaisRealizadas : ideias) {
                vetorDados.add(ideaisRealizadas);
            }

            frame.getContentPane().add(new RelatorioIdeias(vetorDados));
            frame.pack();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

