import entidades.*;
import classesDAO.*;
import enums.Voto;

import javax.swing.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        ColaboradorDAO.carregarDados();
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

        Colaborador colaborador = ColaboradorDAO.buscarPorLoginESenha(login, senha);


        if (colaborador != null) {
            if (colaborador.getNome() == "admin") {
                menuOpcoesAdmin();
                validaLogin(null);
            }
            else {
                menuOpcoesColaborador(colaborador);

            }
            JOptionPane.showMessageDialog(null, "USUÁRIO DESCONECTADO COM SUCESSO!", "Mensagem de Saída", JOptionPane.INFORMATION_MESSAGE);
            menuInicial();
        }

        else {
            JOptionPane.showMessageDialog(null, "USUÁRIO NÃO ENCONTRADO!", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
        menuLogin();
        }
    }

    private static void menuCadastro() {

        Colaborador colaborador = new Colaborador();

        colaborador.setId(ColaboradorDAO.maiorIdCadastrado() + 1);

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

    }

    private static Setor cadastroSetor(){

        Setor setor = new Setor();
        setor.setNomeSetor(JOptionPane.showInputDialog(null,"Digite o nome do setor a ser cadastrado!"));
        SetorDAO.salvarSetor(setor);

        return setor;
    }

    private static void menuCadastroSetor(){

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


    private static void menuOpcoesColaborador(Colaborador colaborador) {

            String[] opcoesMenu = {"Editar Dados do cadastro", "Publicar Ideia", "Ver Ideias Publicadas", "Votar em uma ideia", "Sair"};
            int selecao = JOptionPane.showOptionDialog(null, "Selecione a opção desejada", "Menu", JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);
            switch (selecao){

                case 0:
                    editarColaborador(colaborador);
                    menuOpcoesColaborador(colaborador);

                    break;

                case 1:
                    realizarIdeais();
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

    private static Ideia selecaoDeIdeias(){

        Object[] selectionValues = getIdeiasDAO().findIdeiasInArray();
        String initialSelection = (String) selectionValues[0];
        Object selection =  JOptionPane.showInputDialog(null, "Selecione o colaborador para editar",
                "Colaboradores", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        List<Ideia> ideiaList = getIdeiasDAO().buscarPorTitulo(selection);

        return ideiaList.get(0);
    }

    public static Setor selecaoSetor(){

        Object[] selectionValues = SetorDAO.findSetorInArray();
        String initialSelection = (String) selectionValues[0];
        Object selection = JOptionPane.showInputDialog(null, "Selecione o setor desejado!",
                "Setores", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        List<Setor> setorList = SetorDAO.buscarPorTitulo(selection);

        return setorList.get(0);
    }

    public static void visualizarSetor(){
        JOptionPane.showMessageDialog(null, "Setores: " + SetorDAO.buscarTodos());
    }

    private static void visualizacaoDeIdeias(Ideia ideia){

        JOptionPane.showMessageDialog(null, "Título: "+ideia.getTitulo() + "\n"
                +"Setor: "+ ideia.getSetor() + "\n"
                +"Descriçao: "+ ideia.getDescricao() + "\n"
                +"Data: "+ ideia.getData()
                +"Voto: " + ideia.getVoto()
                +"FeedBack: " + ideia.getFeedBack());
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

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        FeedBack feedBack = new FeedBack();
        feedBack.setDescricao(JOptionPane.showInputDialog(null, "Digite a descricao do feedback: "));
        feedBack.setData(sdf.format(now));
        ideia.setFeedBack(feedBack);

    }

    private static void menuOpcoesAdmin() {

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

    private static void editarColaborador(Colaborador colaborador){

        colaborador.setNome(JOptionPane.showInputDialog(null, "Digite o nome: " , colaborador.getNome()));
        colaborador.setLogin(JOptionPane.showInputDialog(null, "Digite o login: " , colaborador.getLogin()));
        colaborador.setSenha(JOptionPane.showInputDialog(null, "Digite a senha: " , colaborador.getSenha()));

    }

    private static void editarSetor(){

        Setor setor = selecaoSetor();
        setor.setNomeSetor(JOptionPane.showInputDialog(null, "Digite o nome do setor: " , setor.getNomeSetor()));
    }

    private static Colaborador selecaoColaborador(){

        Object[] selectionValues = getColaboradorDAO().findColaboradorInArray();
        String initialSelection = (String) selectionValues[0];
        Object selection = JOptionPane.showInputDialog(null, "Selecione o colaborador para editar",
                "Colaboradores", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        List<Colaborador> colaboradorList = ColaboradorDAO.buscarPorNome(selection);

        return colaboradorList.get(0);
    }

    private static void excluirColaborador(Colaborador colaborador){

        if(colaborador != null){
        ColaboradorDAO.remover(colaborador);
        }
        else {
            menuCadastro();
        }
    }

    private static void excluirSetor(Setor setor){

        SetorDAO.remover(setor);

    }

    private static void realizarIdeais(){

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Ideia ideia = new Ideia();
        ideia.setSetor(selecaoSetor());
        ideia.setTitulo(JOptionPane.showInputDialog(null, "Digite o título: " ));
        ideia.setDescricao(JOptionPane.showInputDialog(null, "Digite a ideia: " ));
        ideia.setData(sdf.format(now));
        IdeiaDAO.salvarIdeia(ideia);
    }

    private static void menuEdicaoCadastro(){

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

    private static Colaborador validaLogin(Colaborador colaboradorLogin){
       Colaborador login = colaboradorLogin;
       return login;
    }

    private static IdeiaDAO getIdeiasDAO(){
        IdeiaDAO ideiaDAO = new IdeiaDAO();
        return ideiaDAO;
    }


}

