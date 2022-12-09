package classesDAO;

import entidades.Setor;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SetorDAO {


    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/sgdibd";
        Connection connection = DriverManager.getConnection(url, "root", "");

        return connection;
    }



    public static void salvar(Setor setor) throws SQLException, ClassNotFoundException {

        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("insert into setor(nome) values (?)");
        stmt.setString(1, setor.getNomeSetor());

        int x = stmt.executeUpdate();
        System.out.println(x + " Linhas inseridas");
        connection.close();
    }



    public static void excluir(Integer id) throws  SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("delete from setor where idsetor = ?");
        stmt.setInt(1, id);

        try {
            stmt.executeUpdate();
        }
        catch(SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "Não é possível excluir este setor, há registros de colaboradores e ideia que o utilizam", "Erro de exclusão",JOptionPane.ERROR_MESSAGE);
             }
        connection.close();
    }

    public static void editar(Setor setor) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("update setor set nome =? where idsetor=?");

        stmt.setString(1, setor.getNomeSetor());
        stmt.setInt(2, setor.getIdSetor());

        stmt.executeUpdate();
        connection.close();
    }

    public static List<Setor> buscarTodos() throws  SQLException, ClassNotFoundException {
        List<Setor> setore = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("select * from setor");

        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            setore.add(new Setor(resultSet.getInt(1), resultSet.getString(2)));
        }

        System.out.println(setore);
        return setore;
    }




    public static Setor buscarPorId(Integer id) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("select * from setor where idsetor=?");
        stmt.setInt(1, id);

        ResultSet resultSet = stmt.executeQuery();

        Setor setor = new Setor();
        if (resultSet.next()) {
            setor.setIdSetor(resultSet.getInt(1));
            setor.setNomeSetor(resultSet.getString(2));
            System.out.println(setor);
        }
        return setor;
    }

    public static Integer buscarIdPorNome(String nome) throws  SQLException, ClassNotFoundException {
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("select * from setor where nome = ?");
        stmt.setString(1, nome);

        ResultSet resultSet = stmt.executeQuery();
        resultSet.next();
        Integer id = resultSet.getInt(1);

        return id;
    }

    public static Object[] buscarNomes() throws SQLException, ClassNotFoundException {

        List<String> nomeSetores = new ArrayList<>();

        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("select nome from setor");


        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            nomeSetores.add(resultSet.getString(1));
        };


        return nomeSetores.toArray();

    }
}
