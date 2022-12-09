package repository;

import entidades.Colaborador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IdeiaRepository {

    public static Connection getConnection () throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jbdc:mysql://localhost:3306/projetobancodedados";
        Connection connection = DriverManager.getConnection(url,"root", "");

        return connection;
    }

    public static void insere(Colaborador colaborador) throws SQLException, ClassNotFoundException{

        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("insert into compradores values(?, ?, ?)");
        stmt.setInt(1, colaborador.getId().intValue());
        stmt.setString(2, colaborador.getNome());
        stmt.setString(3, colaborador.getNome());

        int i = stmt.executeUpdate();
        System.out.println(i + "linhas Inseridas");
        connection.close();

    }
}
