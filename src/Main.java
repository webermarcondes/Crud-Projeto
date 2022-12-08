import classesDAO.ColaboradorDAO;

import classesDAO.IdeiaDAO;

import classesDAO.SetorDAO;
import entidades.*;
import enums.Voto;

import javax.swing.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        ColaboradorDAO.carregarDados();
        System.out.println(ColaboradorDAO.buscarTodosBD());
        menuInicial();

    }

    private static void menuInicial() throws SQLException, ClassNotFoundException {

        String[] opcoesInicio = {"Login", "Sair"};
        int idOpInicio = JOptionPane.showOptionDialog(null, "Selecione a opção desejada", "Menu_Inicial", JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, opcoesInicio, opcoesInicio[0]);

        switch (idOpInicio) {

            case 0:
                menuLogin();
                break;

            case 1:
                System.exit(0);
                break;
        }
    }
    private static void menuLogin() throws SQLException, ClassNotFoundException {

        String login = JOptionPane.showInputDialog(null, "Digite seu login");
        String senha = JOptionPane.showInputDialog(null, "Digite sua Senha de 8 caracteres númericos");

        Colaborador colaborador = ColaboradorDAO.buscarPorLoginESenhaBD(login, senha);

        if (colaborador != null) {
            if (colaborador.getNome().equals("admin")) {
                menuOpcoesAdmin();
                validaLogin(null);
            }
            else {
                menuOpcoesColaborador(colaborador);

            }
            JOptionPane.showMessageDialog(null, "USUÁRIO DESCONECTADO COM SUCESSO!", "Mensagem de Saída", JOptionPane.INFORMATION_MESSAGE);
            menuInicial();
        }

      //  else {
//<<<<<<< main
//            JOptionPane.showMessageDialog(null, "USUÁRIO NÃO ENCONTRADO!", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
 //       menuLogin();
//=======
//
//            JOptionPane.showMessageDialog(null,"Usuário inválido!", "ERRO", 0);
//                menuInicial();
///
///>>>>>>> main
       // }
    }

    private static void menuCadastro() throws  SQLException, ClassNotFoundException {

        Colaborador colaborador = new Colaborador();

        while (true) {
            colaborador.setNome(JOptionPane.showInputDialog(null, "Digite seu nome", "Cadastro de colaborador",JOptionPane.INFORMATION_MESSAGE));

            if (colaborador.getNome().length() == 0) {
                JOptionPane.showMessageDialog(null, "Erro! nome é um dado obrigatório", "Erro de dado Obrigatório", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            break;
        }

        while (true) {
            colaborador.setLogin(JOptionPane.showInputDialog(null, "Digite seu login", "Cadastro de colaborador",JOptionPane.INFORMATION_MESSAGE));

            if (colaborador.getNome().length() == 0) {
                JOptionPane.showMessageDialog(null, "Erro! login é um dado obrigatório", "Erro de dado Obrigatório", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            break;
        }

        while (true) {
            colaborador.setSenha(JOptionPane.showInputDialog(null, "Digite sua senha númerica de 8 caracteres", "Cadastro de colaborador", JOptionPane.INFORMATION_MESSAGE));

            if (FormatoSenha.verificarFormato(colaborador.getSenha())) {
                break;
            }

            JOptionPane.showMessageDialog(null, "Erro! a senha informada deve ter apenas 8 caracteres e ser númerica", "Erro de formato de senha", JOptionPane.ERROR_MESSAGE);

        }
        colaborador.setSetor(selecaoSetor());
        JOptionPane.showMessageDialog(null, "COLABORADOR CADASTRADO COM SUCESSO!", "Cadastro de colaborador", JOptionPane.INFORMATION_MESSAGE);

        ColaboradorDAO.salvar(colaborador);
        ColaboradorDAO.salvarBD(colaborador);

    }

    private static void cadastroSetor() throws  SQLException, ClassNotFoundException{

        Setor setor = new Setor();

        do {
            setor.setNomeSetor(JOptionPane.showInputDialog(null, "Digite o nome do setor a ser cadastrado!"));

            if (setor.getNomeSetor().length() > 0) {
                break;
            }

            JOptionPane.showMessageDialog(null, "Erro! O nome do setor é um dado obrigatório", "Erro de dado obrigatório", JOptionPane.ERROR_MESSAGE);
        } while (setor.getNomeSetor().length() == 0);


        SetorDAO.salvarBD(setor);
    }

    private static void menuCadastroSetor() throws  SQLException, ClassNotFoundException{

        String[] opcoesMenu = {"Visualizar Setores","Cadastrar setor", "Editar setor", "Excluir setor", "Voltar"};
        int selecao = JOptionPane.showOptionDialog(null, "Selecione a opção desejada", "Menu", JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);
        switch (selecao){

            case 0:
                visualizarSetor();

                break;
            case 1:
                cadastroSetor();

                break;
            case 2:
                editarSetor();

                break;
            case 3:
                excluirSetor(selecaoSetor());
                break;
        }
    }


    private static void menuOpcoesColaborador(Colaborador colaborador) throws  SQLException, ClassNotFoundException {

            String[] opcoesMenu = {"Editar Dados do cadastro", "Publicar Ideia", "Ver Ideias Publicadas", "Votar em uma ideia", "Sair"};
            int selecao = JOptionPane.showOptionDialog(null, "Selecione a opção desejada", "Menu", JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);
            switch (selecao){

                case 0:
                    editarColaborador(colaborador);
                    menuOpcoesColaborador(colaborador);

                    break;

                case 1:
                    realizarIdeais(colaborador);
                    menuOpcoesColaborador(colaborador);
                    break;

                case 2:
                    visualizacaoDeIdeias(selecaoDeIdeias());
                    menuOpcoesColaborador(colaborador);
                    break;

                case 3:
                    votarIdeia(selecaoDeIdeias());
                    menuOpcoesColaborador(colaborador);
                    break;
            }
    }

    private static Ideia selecaoDeIdeias() throws SQLException, ClassNotFoundException{

        Object[] selectionValues = getIdeiasDAO().BuscarTitulos();
        String initialSelection = (String) selectionValues[0];
        Object selection =  JOptionPane.showInputDialog(null, "Selecione o colaborador para editar",
                "Colaboradores", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);


        Integer id = IdeiaDAO.buscarIdPorTitulo(selection.toString());
        return IdeiaDAO.buscarPorId(id);
    }

    public static Setor selecaoSetor() throws  SQLException, ClassNotFoundException{

        Object[] selectionValues = SetorDAO.BuscarNomes();

        String initialSelection = (String) selectionValues[0];
        Object selection = JOptionPane.showInputDialog(null, "Selecione o setor desejado!",
                "Setores", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);


        Integer id = SetorDAO.BuscarIdPorNome(selection.toString());
        return SetorDAO.BuscarPorId(id);
    }

    public static void visualizarSetor() throws SQLException, ClassNotFoundException{
        JOptionPane.showMessageDialog(null, "Setores: " + SetorDAO.BuscarTodos());
    }

    private static void visualizacaoDeIdeias(Ideia ideia) throws SQLException, ClassNotFoundException{

//        JOptionPane.showMessageDialog(null, "Título: "+ideia.getTitulo() + "\n"
//                +"Setor: "+ ideia.getSetor() + "\n"
//                +"Descriçao: "+ ideia.getDescricao() + "\n"
//                +"Data: "+ ideia.getData()
//                +"Voto: " + ideia.getVoto()
//                +"FeedBack: " + ideia.getFeedBack());
        JOptionPane.showMessageDialog(null, "Ideias: " + IdeiaDAO.buscarTodosBD());
    }

    private static void votarIdeia(Ideia ideia){

        String[] selectionValues = {"LIKE", "DESLIKE"};
        String initialSelection = selectionValues[0];
        Object selection = JOptionPane.showInputDialog(null, "Selecione o colaborador para editar",
                "Colaboradores", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);

        if(selection.equals(0)){

        ideia.setVoto(Voto.LIKE);
        }else {
            ideia.setVoto(Voto.DESLIKE);
        }
    }

    private static void feedbackIdeias(Ideia ideia){





    }

    private static void menuOpcoesAdmin() throws SQLException, ClassNotFoundException {

        String[] opcoesMenu = {"Cadastros Colaborador","Cadastro Setor","Visualizar Ideias", "FeedBack","Sair"};
        int selecao = JOptionPane.showOptionDialog(null, "Selecione a opção desejada", "Menu", JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);
        switch (selecao){

            case 0:
            menuEdicaoCadastro();
            menuOpcoesAdmin();
            break;

            case 1:
                menuCadastroSetor();
                menuOpcoesAdmin();
                break;

            case 2:
                visualizacaoDeIdeias(selecaoDeIdeias());
                menuOpcoesAdmin();
                break;

            case 3:
                feedbackIdeias(selecaoDeIdeias());
                menuOpcoesAdmin();
                break;
        }
    }

    private static void editarColaborador(Colaborador colaborador) throws  SQLException, ClassNotFoundException{

        String nome = "";
        String login = "";
        String senha = "";

        while (true) {
            nome = JOptionPane.showInputDialog(null, "Digite o nome: " , colaborador.getNome());

            if (nome.length() > 0) {
                break;
           }
            JOptionPane.showMessageDialog(null, "Erro! nome é um dado obrigatório", "erro de dado obrigatório", JOptionPane.ERROR_MESSAGE);
        }
        colaborador.setNome(nome);


        while (true) {
            login = JOptionPane.showInputDialog(null, "Digite o login: " , colaborador.getLogin());

            if (login.length() > 0) {
                break;
            }
            JOptionPane.showMessageDialog(null, "Erro! login é um dado obrigatório", "erro de dado obrigatório", JOptionPane.ERROR_MESSAGE);
        }
        colaborador.setLogin(login);

        while (true) {
            senha = JOptionPane.showInputDialog(null, "Digite a senha de 8 caracteres númericos: " , colaborador.getSenha());

            if (FormatoSenha.verificarFormato(senha)) {
                break;
            }
            JOptionPane.showMessageDialog(null, "Erro! a senha informada deve conter 8 caracteres númericos", "erro de dado obrigatório", JOptionPane.ERROR_MESSAGE);
        }
        colaborador.setSenha(senha);

        ColaboradorDAO.editar(colaborador);

        JOptionPane.showMessageDialog(null, "DADOS EDITADOS SALVOS COM SUCESSO!", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void editarSetor() throws  SQLException, ClassNotFoundException{

        Setor setor = selecaoSetor();
        setor.setNomeSetor(JOptionPane.showInputDialog(null, "Digite o nome do setor: " , setor.getNomeSetor()));
        SetorDAO.editarBD(setor);

        JOptionPane.showMessageDialog(null, "DADOS EDITADOS SALVOS COM SUCESSO!", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
    }

    private static Colaborador selecaoColaborador() throws  SQLException, ClassNotFoundException{

        Object[] selectionValues = getColaboradorDAO().BuscarNomes().toArray();
        String initialSelection = (String) selectionValues[0];
        Object selection = JOptionPane.showInputDialog(null, "Selecione o colaborador para editar",
                "Colaboradores", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        List<Colaborador> colaboradorList = ColaboradorDAO.buscarPorNome(selection);

        Integer id = ColaboradorDAO.BuscarIdPorNome(selection.toString());
        return ColaboradorDAO.BuscarPorId(id);
    }

    private static void excluirColaborador(Colaborador colaborador) throws SQLException, ClassNotFoundException{

        ColaboradorDAO.excluirBD(colaborador.getId());

    }

    private static void excluirSetor(Setor setor) throws SQLException, ClassNotFoundException{

        SetorDAO.excluirBD(setor.getIdSetor());

    }

    private static void realizarIdeais(Colaborador colaborador) throws  SQLException, ClassNotFoundException{

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Ideia ideia = new Ideia();
        ideia.setTitulo(JOptionPane.showInputDialog(null, "Digite o titulo da ídeia"));
        ideia.setDescricao(JOptionPane.showInputDialog(null, "Descreva sua ídeia de melhoria"));
        ideia.setData(sdf.format(now));

        ideia.setStatus("ABERTO");
        ideia.setColaborador(colaborador);
        ideia.setSetor(selecaoSetor());

        IdeiaDAO.salvarIdeia(ideia);
        IdeiaDAO.salvarIdeiaBD(ideia);
    }

    private static void menuEdicaoCadastro() throws SQLException, ClassNotFoundException{

        String[] opcoesMenu = {"Cadastrar","Editar ", "Excluir", "Voltar"};
        int selecao = JOptionPane.showOptionDialog(null, "Selecione a opção desejada", "Cadastros", JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);

        switch (selecao){

            case 0:
                menuCadastro();
                break;

            case 1:
                editarColaborador(selecaoColaborador());
                break;

            case 2:
                excluirColaborador(selecaoColaborador());
                break;

            case 3:
                menuOpcoesAdmin();
                break;
        }
    }

    private static ColaboradorDAO getColaboradorDAO(){
        ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
        return colaboradorDAO;
    }

    private static boolean validaColaborador(boolean valida){
        return valida;
    }

    private static Colaborador validaLogin(Colaborador colaboradorLogin){
       Colaborador login = colaboradorLogin;
       return login;
    }

    private static IdeiaDAO getIdeiasDAO(){
        IdeiaDAO ideiaDAO = new IdeiaDAO();
        return ideiaDAO;
    }


}

