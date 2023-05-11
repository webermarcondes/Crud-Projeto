package classesDAO;

import entidades.Colaborador;
import entidades.Ideia;
import entidades.Voto;
import enums.OpcaoVoto;

import java.sql.*;

public class VotoDAO {

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/sgdibd";
        Connection connection = DriverManager.getConnection(url, "postgres", "1010");

        return connection;
    }

    public static void salvar(Voto voto) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();


        PreparedStatement stmt = connection.prepareStatement("insert into voto(opcao, idideia, idcolaborador) values (?, ?, ?)");
        stmt.setString(1, voto.getOpcaoVoto().toString());
        stmt.setInt(2, voto.getIdeia().getId());
        stmt.setInt(3, voto.getColaborador().getId());

        stmt.executeUpdate();
        connection.close();
    }

    public static void editar(Voto voto) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("update voto set opcao=? where idvoto=?");

        stmt.setString(1, voto.getOpcaoVoto().toString());
        stmt.setInt(2, voto.getId());

        stmt.executeUpdate();
        connection.close();
    }


    public static Voto buscarPorIdeiaEColaborador(Integer idcolaborador, Integer idideia) throws SQLException, ClassNotFoundException {

        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("select * from voto where idcolaborador=? and idideia=?");

        stmt.setInt(1, idcolaborador);
        stmt.setInt(2, idideia);

        ResultSet resultSet = stmt.executeQuery();

        Voto voto = new Voto();
        if (resultSet.next()) {
            voto.setId(resultSet.getInt(1));
            voto.setOpcaoVoto(OpcaoVoto.getOpcaoByDescricao(resultSet.getString(2)));
            voto.setColaborador(ColaboradorDAO.BuscarPorId(resultSet.getInt(3)));
        }

        return voto;

    }

    public static Integer contarVotosPorOpcao(OpcaoVoto opcao) throws SQLException, ClassNotFoundException{

        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("select count(*) from voto where opcao=?");

        stmt.setString(1, opcao.toString());

        ResultSet resultSet = stmt.executeQuery();
        resultSet.next();

        Integer qtdeVotos = resultSet.getInt(1);

        connection.close();
        return  qtdeVotos;
    }

    public static Boolean verificarVotoNaIdeia(Ideia ideia, Colaborador colaborador) throws SQLException, ClassNotFoundException {

        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("select * from voto where idcolaborador=? and idideia=?");

        stmt.setInt(1, colaborador.getId());
        stmt.setInt(2, ideia.getId());

        ResultSet resultSet = stmt.executeQuery();


        if (resultSet.next()) {

            return true;
        }

        return false;
    }

}
