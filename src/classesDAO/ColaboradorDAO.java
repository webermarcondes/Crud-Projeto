package classesDAO;

import entidades.Colaborador;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColaboradorDAO {


    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/sgdibd";
        Connection connection = DriverManager.getConnection(url, "root", "");

        return connection;
    }


    public static void salvar(Colaborador colaborador) throws  SQLException, ClassNotFoundException{

        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("insert into colaborador(nome, login, senha, idsetor) values (?, ?, ?, ?)");
        stmt.setString(1, colaborador.getNome());
        stmt.setString(2, colaborador.getLogin());
        stmt.setString(3, colaborador.getSenha());
        stmt.setInt(4, colaborador.getSetor().getIdSetor());

        int x = stmt.executeUpdate();

        System.out.println(x + " Linhas inseridas");
        connection.close();
    }

    public static void excluir(Integer id) throws  SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("delete from colaborador where idcolaborador = ?");
        stmt.setInt(1, id);
        try {
            stmt.executeUpdate();
        }
        catch(SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null,"não é possível apagar este colaborador pois ele tem ideias e/ou votos cadastrados", "erro de exclusão", JOptionPane.ERROR_MESSAGE);
        }
        connection.close();
    }

    public static void editar(Colaborador colaborador) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("update colaborador set nome=?, login=?, senha=?, idsetor=? where idcolaborador=?");

        stmt.setString(1, colaborador.getNome());
        stmt.setString(2, colaborador.getLogin());
        stmt.setString(3, colaborador.getSenha());
        stmt.setInt(4, colaborador.getSetor().getIdSetor());
        stmt.setInt(5, colaborador.getId());

        stmt.executeUpdate();
        connection.close();
    }

    public static Colaborador buscarPorID(Integer id) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("select * from colaborador where idcolaborador=?");
        stmt.setInt(1, id);

        ResultSet resultSet = stmt.executeQuery();
        Colaborador colaborador = new Colaborador();
        if (resultSet.next()) {

            colaborador.setId(resultSet.getInt(1));
            colaborador.setNome(resultSet.getString(2));
            colaborador.setLogin(resultSet.getString(3));
            colaborador.setSenha(resultSet.getString(4));
            colaborador.setSetor(classesDAO.SetorDAO.buscarPorId(resultSet.getInt(5)));

        }
        return colaborador;
    }

    public static List<Colaborador> buscarTodos() throws  SQLException, ClassNotFoundException{
        List<Colaborador> colabs = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("select * from colaborador");

        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            Colaborador colab = new Colaborador();
            colab.setId(resultSet.getInt(1));
            colab.setNome(resultSet.getString(2));
            colab.setLogin(resultSet.getString(3));
            colab.setSenha(resultSet.getString(4));
            colab.setSetor(classesDAO.SetorDAO.buscarPorId(resultSet.getInt(5)));

            colabs.add(colab);

        }

        return colabs;

    }
    public static Colaborador buscarPorLoginESenhaBD(String login, String senha) throws SQLException, ClassNotFoundException {

        Colaborador colab = null;

        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("select * from colaborador where login = ? and senha = ?");
        stmt.setString(1, login);
        stmt.setString(2, senha);

        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            String loginCadastrado = resultSet.getString(3);
            String senhaCadastrada = resultSet.getString(4);

            if (loginCadastrado.equals(login) && senhaCadastrada.equals(senha)) {
                colab = new Colaborador();
                colab.setId(resultSet.getInt(1));
                colab.setNome(resultSet.getString(2));
                colab.setLogin(resultSet.getString(3));
                colab.setSenha(resultSet.getString(4));
                colab.setSetor(classesDAO.SetorDAO.buscarPorId(resultSet.getInt(5)));

            }
//            if (result.getString("login").toString().equals(login) && result.getString("senha").equals(colab.getSenha())) {
//                System.out.println("encontrei o colaborador " + login + " com a senha " + senha);
//            }
        }

        connection.close();

        return colab;
    }

    public static List<String> buscarNomes() throws SQLException, ClassNotFoundException {

        List<String> nomeColaboradores = new ArrayList<>();

        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("select nome from colaborador");


        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            nomeColaboradores.add(resultSet.getString(1));
        };


        return nomeColaboradores;

    }

    public static Integer buscarIdPorNome(String nome) throws  SQLException, ClassNotFoundException {
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("select * from colaborador where nome = ?");
        stmt.setString(1, nome);

        ResultSet resultSet = stmt.executeQuery();
        resultSet.next();

        Integer id = resultSet.getInt(1);

        return id;
    }
}
