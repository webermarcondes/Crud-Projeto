package classesDAO;
import entidades.Colaborador;
import entidades.Ideia;
import entidades.Setor;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class IdeiaDAO {

    private static List<Ideia> ideias = new ArrayList<>();

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/sgdibd";
        Connection connection = DriverManager.getConnection(url, "root", "");

        return connection;
    }

    public static void salvarIdeia(Ideia ideia){
        ideias.add(ideia);
    }

    public static void salvarIdeiaBD(Ideia ideia) throws SQLException, ClassNotFoundException {

        //titulo, descricao, datapublicacao, feedback, status, idcolaborador, idsetor
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("insert into ideia(titulo, descricao, datapublicacao, feedback, status, idcolaborador, idsetor) values (?, ?, ?, ?, ?, ?, ?)");
        stmt.setString(1, ideia.getTitulo());
        stmt.setString(2, ideia.getDescricao());
        stmt.setString(3, ideia.getData());
        stmt.setString(4, ideia.getFeedback());
        stmt.setString(5, ideia.getStatus());
        stmt.setInt(6, ideia.getColaborador().getId());
        stmt.setInt(7, ideia.getSetor().getIdSetor());

        int x = stmt.executeUpdate();
        System.out.println(x + " Linhas inseridas");
        connection.close();
    }

    public static void editarIdeiaBD(Ideia ideia) throws  SQLException, ClassNotFoundException {

        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("update ideia set titulo=?, descricao=?, datapublicacao=?, feedback=?, status=?, idcolaborador=?, idsetor=? where idideia=?");

        stmt.setString(1, ideia.getTitulo());
        stmt.setString(2, ideia.getDescricao());
        stmt.setString(3, ideia.getData());
        stmt.setString(4, ideia.getFeedback());
        stmt.setString(5, ideia.getStatus());
        stmt.setInt(6, ideia.getColaborador().getId());
        stmt.setInt(7, ideia.getSetor().getIdSetor());
        stmt.setInt(8, ideia.getId());

        int x = stmt.executeUpdate();
    }

    public static void excluir(Integer id) throws  SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("delete from ideia where idideia = ?");
        stmt.setInt(1, id);

        stmt.executeUpdate();
        connection.close();
    }

    public  static List<Ideia> buscarTodosBD() throws SQLException, ClassNotFoundException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Ideia> ideias = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("select * from ideia");

        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            Ideia ideia = new Ideia();
            ideia.setId(resultSet.getInt(1));
            ideia.setTitulo(resultSet.getString(2));
            ideia.setDescricao(resultSet.getString(3));
//            ideia.setData(LocalDate.parse(resultSet.getString(4), formatter));
            ideia.setFeedback(resultSet.getString(5));
            ideia.setStatus(resultSet.getString(6));
            ideia.setColaborador(ColaboradorDAO.BuscarPorId(resultSet.getInt(7)));

            ideia.setSetor(SetorDAO.BuscarPorId(resultSet.getInt(8)));

            ideias.add(ideia);

        }

        return ideias;

    }



    public static List<Ideia> buscarTodos(){
        return ideias;
    }

    public static Object[] findIdeiasInArray(){
        //List<Ideia> ideiaBusca = IdeiaDAO.buscarTodos();
        List<String> ideiaBuscaTitulo = new ArrayList<>();

        for (Ideia ideia1 : ideias) {
            ideiaBuscaTitulo.add(ideia1.getTitulo());
        }
        return ideiaBuscaTitulo.toArray();
    }

    public List<Ideia> buscarPorTitulo(Object titulo) {
        List<Ideia>ideiasFiltradas = new ArrayList<>();
        for (Ideia busca : ideias){
            if(busca.getTitulo().equals(titulo)){
            ideiasFiltradas.add(busca);
            }
        }
        return ideiasFiltradas;
    }

    public Object[] BuscarTitulos() throws  SQLException, ClassNotFoundException {
        List<String> titulos = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("select titulo from ideia");

        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            titulos.add(resultSet.getString(1));
        }


        return titulos.toArray();

    }

    public static Integer buscarIdPorTitulo(String titulo) throws SQLException, ClassNotFoundException{
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("select * from ideia where titulo = ?");
        stmt.setString(1, titulo);

        ResultSet resultSet = stmt.executeQuery();
        resultSet.next();

        Integer id = resultSet.getInt(1);

        return id;

    }

    public static Ideia buscarPorId(Integer id) throws SQLException, ClassNotFoundException{
        Ideia ideia = new Ideia();
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("select * from ideia where idideia=?");
        stmt.setInt(1, id);

        ResultSet resultSet = stmt.executeQuery();
        resultSet.next();


        ideia.setId(resultSet.getInt(1));
        ideia.setTitulo(resultSet.getString(2));
        ideia.setDescricao(resultSet.getString(3));
        ideia.setData(resultSet.getString(4));
//        String[] dataBD = resultSet.getString(4).split("-");
//        String dataFormatada = LocalDate.of(Integer.parseInt(dataBD[0]), Integer.parseInt(dataBD[1]), Integer.parseInt(dataBD[2])).format(formatter);
//        System.out.println(dataFormatada);
//        ideia.setDate(LocalDate.parse(dataFormatada, formatter));
        ideia.setFeedback(resultSet.getString(5));
        ideia.setStatus(resultSet.getString(6));
        ideia.setColaborador(ColaboradorDAO.BuscarPorId(resultSet.getInt(7)));
        ideia.setSetor(SetorDAO.BuscarPorId(resultSet.getInt(8)));
        return ideia;
    }

    public static List<Ideia> buscarIdeiasPorColaborador(Colaborador colaborador) throws SQLException, ClassNotFoundException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Ideia> ideias = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("select * from ideia where idcolaborador=?");
        stmt.setInt(1, colaborador.getId());

        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            Ideia ideia = new Ideia();
            ideia.setId(resultSet.getInt(1));
            ideia.setTitulo(resultSet.getString(2));
            ideia.setDescricao(resultSet.getString(3));

//            String[] dataBD = resultSet.getString(4).split("-");
//            String dataFormatada = LocalDate.of(Integer.parseInt(dataBD[0]), Integer.parseInt(dataBD[1]), Integer.parseInt(dataBD[2])).format(formatter);
//            System.out.println(dataFormatada);
//            ideia.setDate(LocalDate.parse(dataFormatada, formatter));
            ideia.setFeedback(resultSet.getString(5));
            ideia.setStatus(resultSet.getString(6));
            ideia.setColaborador(colaborador);

            ideia.setSetor(SetorDAO.BuscarPorId(resultSet.getInt(8)));

            ideias.add(ideia);

        }

        return ideias;
    }
}
