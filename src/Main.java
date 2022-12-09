import classesDAO.ColaboradorDAO;

import classesDAO.IdeiaDAO;

import classesDAO.SetorDAO;
import classesDAO.VotoDAO;
import entidades.*;
import enums.OpcaoVoto;
import javax.swing.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import tableForms.RelatorioIdeias;


public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        System.out.println(VotoDAO.contarVotosPorOpcaoNaIdeia(OpcaoVoto.DESLIKE, 1));
        // CADASTRO FIXO DO ADMIN
        if (SetorDAO.buscarPorId(1).getIdSetor() == null) {
            Setor setor = new Setor();
            setor.setIdSetor(1);
            setor.setNomeSetor("ADM");

            SetorDAO.salvar(setor);

            if (ColaboradorDAO.buscarPorID(1).getId() == null) {
                Colaborador colaborador = new Colaborador();
                colaborador.setNome("Admin");
                colaborador.setLogin("ADMIN");
                colaborador.setSenha("12345678");
                colaborador.setSetor(setor);

                ColaboradorDAO.salvar(colaborador);
            }
        }  // CADASTRO FIXO DO ADMIN

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
        String login = "";
        login = JOptionPane.showInputDialog(null, "Digite seu login");

        String senha = "";
        senha = JOptionPane.showInputDialog(null, "Digite sua Senha de 8 caracteres númericos");

        System.out.println(login + "   " + senha);
        Colaborador colaborador = ColaboradorDAO.buscarPorLoginESenhaBD(login, senha);
        if (colaborador != null) {
            if (colaborador.getNome().equals("Admin")) {
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
            JOptionPane.showMessageDialog(null, "Não existe nenhum colaborador cadastrado com este dados, tente novamente", "Erro de dados não encontrados",JOptionPane.ERROR_MESSAGE);
            menuInicial();
        }


    }

    private static void menuCadastro() throws  SQLException, ClassNotFoundException {

        Colaborador colaborador = new Colaborador();

        while (true) {
            colaborador.setNome(JOptionPane.showInputDialog(null, "Digite o nome: "));

            if (colaborador.getNome().equals("Admin")) {
                JOptionPane.showMessageDialog(null, "Erro! não é possível cadastrar um colaborador com este nome");
            }
            else if(colaborador.getNome().length() == 0) {
                JOptionPane.showMessageDialog(null, "Erro! nome é um dado obrigatório", "erro de dado obrigatório", JOptionPane.ERROR_MESSAGE);
            }

            else if (colaborador.getNome().length() > 0) {
                break;
            }
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

    private static void cadastroSetor() throws  SQLException, ClassNotFoundException{

        Setor setor = new Setor();

        do {
            setor.setNomeSetor(JOptionPane.showInputDialog(null, "Digite o nome do setor a ser cadastrado!"));

            if (setor.getNomeSetor().length() > 0) {
                break;
            }

            JOptionPane.showMessageDialog(null, "Erro! O nome do setor é um dado obrigatório", "Erro de dado obrigatório", JOptionPane.ERROR_MESSAGE);
        } while (setor.getNomeSetor().length() == 0);


        SetorDAO.salvar(setor);
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
                    editarColaborador(colaborador, false);
                    menuOpcoesColaborador(colaborador);

                    break;

                case 1:
                    realizarIdeais(colaborador);
                    menuOpcoesColaborador(colaborador);
                    break;

                case 2:
                    visualizacaoDeIdeias();
                    //menuOpcoesColaborador(colaborador);
                    break;

                case 3:
                    votarIdeia(colaborador);
                    menuOpcoesColaborador(colaborador);
                    break;
            }
    }

    private static Ideia selecaoDeIdeias() throws SQLException, ClassNotFoundException{

        Object[] selectionValues = getIdeiasDAO().buscarTitulos();
        String initialSelection = (String) selectionValues[0];
        Object selection =  JOptionPane.showInputDialog(null, "Selecione a ideia desejada",
                "Seleção de ideias", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);


        Integer id = IdeiaDAO.buscarIdPorTitulo(selection.toString());
        return IdeiaDAO.buscarPorId(id);
    }

    public static Setor selecaoSetor() throws  SQLException, ClassNotFoundException{

        Object[] selectionValues = SetorDAO.buscarNomes();

        String initialSelection = (String) selectionValues[0];
        Object selection = JOptionPane.showInputDialog(null, "Selecione o setor desejado!",
                "Seleção de setores", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);


        Integer id = SetorDAO.buscarIdPorNome(selection.toString());
        return SetorDAO.buscarPorId(id);
    }

    public static void visualizarSetor() throws SQLException, ClassNotFoundException{
        JOptionPane.showMessageDialog(null, "Setores: " + SetorDAO.buscarTodos());

    }

    private static void visualizacaoDeIdeias() throws SQLException, ClassNotFoundException{

//        JOptionPane.showMessageDialog(null, "Título: "+ideia.getTitulo() + "\n"
//                +"Setor: "+ ideia.getSetor() + "\n"
//                +"Descriçao: "+ ideia.getDescricao() + "\n"
//                +"Data: "+ ideia.getData()
//                +"Voto: " + ideia.getVoto()
//                +"FeedBack: " + ideia.getFeedBack());
        //JOptionPane.showMessageDialog(null, "Ideias: " + IdeiaDAO.buscarTodosBD());
        RelatorioIdeias.emitirRelatorio(IdeiaDAO.buscarTodos());

    }

    private static void votarIdeia(Colaborador colaborador) throws  SQLException, ClassNotFoundException{


        Ideia ideia = selecaoDeIdeias();
        System.out.println(VotoDAO.verificarVotoNaIdeia(ideia, colaborador));
        if (!VotoDAO.verificarVotoNaIdeia(ideia, colaborador)) {
            String[] selectionValues = {"LIKE", "DESLIKE"};
            String initialSelection = selectionValues[0];
            int id = JOptionPane.showOptionDialog(null, "selecione a opção de voto", "Seleção de voto", JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, selectionValues, selectionValues[0]);

            Voto voto = new Voto();
            voto.setColaborador(colaborador);
            voto.setIdeia(ideia);

            if (id == 0) {
                voto.setOpcaoVoto(OpcaoVoto.LIKE);
            } else {
                voto.setOpcaoVoto(OpcaoVoto.DESLIKE);
            }

            VotoDAO.salvar(voto);
        }

        else {

            Voto voto = VotoDAO.buscarPorIdeiaEColaborador(colaborador.getId(), ideia.getId());
            String[] opcoes = {"SIM", "NÃO"};
            int idOp;
            if (voto.getOpcaoVoto().toString().equals("LIKE")) {
                idOp = JOptionPane.showOptionDialog(null, "Deseja alterar a opção para DESLIKE?", "Alteração de opção de voto", JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

                if (idOp == 0) {
                    voto.setOpcaoVoto(OpcaoVoto.DESLIKE);
                    VotoDAO.editar(voto);
                }
            }

            else if (voto.getOpcaoVoto().toString().equals("DESLIKE")) {
                idOp = JOptionPane.showOptionDialog(null, "Deseja alterar a opção para LIKE?", "Alteração de opção de voto", JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

                if (idOp == 0) {
                    voto.setOpcaoVoto(OpcaoVoto.LIKE);
                    VotoDAO.editar(voto);
                }
            }
         }
    }

    private static void feedbackIdeias(Ideia ideia) throws  SQLException, ClassNotFoundException{

        if (ideia.getFeedback().equals("")) {
            ideia.setFeedback(JOptionPane.showInputDialog(null,"Digite o feedback da ideia!", ideia.getFeedback()));
            IdeiaDAO.definirFeedbackIdeia(ideia);
        }else{

            String[] opcoes = {"SIM", "NÃO"};
            int idOp = JOptionPane.showOptionDialog(null, "Ideia ja possui um feedback deseja alterar?", "Alteração de opção de feedback", JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

            if (idOp == 0){
                ideia.setFeedback(JOptionPane.showInputDialog(null,"Digite o feedback da ideia!" , ideia.getFeedback()));
                IdeiaDAO.definirFeedbackIdeia(ideia);
            }
        }
    }

    private static void menuOpcoesAdmin() throws SQLException, ClassNotFoundException {

        String[] opcoesMenu = {"Cadastros Colaborador","Cadastro Setor","Visualizar Ideias", "FeedBack","Sair"};
        int selecao = JOptionPane.showOptionDialog(null, "Selecione a opção desejada", "Menu", JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);
        switch (selecao){

            case 0:
            menuEdicaoCadastro(true);
            menuOpcoesAdmin();
            break;

            case 1:
                menuCadastroSetor();
                menuOpcoesAdmin();
                break;

            case 2:
                visualizacaoDeIdeias();
                menuOpcoesAdmin();
                break;

            case 3:
                feedbackIdeias(selecaoDeIdeias());
                menuOpcoesAdmin();
                break;
        }
    }

    private static void editarColaborador(Colaborador colaborador, boolean admLogado) throws  SQLException, ClassNotFoundException{

        String nome = "";
        String login = "";
        String senha = "";
        Setor setor;

        if (!colaborador.getNome().equals("Admin")) {
            while (true) {
                nome = JOptionPane.showInputDialog(null, "Digite o nome: ", colaborador.getNome());

                if (nome.equals("Admin")) {
                    JOptionPane.showMessageDialog(null, "Erro! não é possível cadastrar um colaborador com este nome");
                }
                else if(nome.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Erro! nome é um dado obrigatório", "erro de dado obrigatório", JOptionPane.ERROR_MESSAGE);
                }

                else if (nome.length() > 0) {
                    break;
                }
            }
            colaborador.setNome(nome);
        }


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


        if (admLogado) {
            List<String> nomeSetores = new ArrayList<>();
            Object setorSelecionado  = new Object();
            for (Object nomeSetor: SetorDAO.buscarTodos()) {
                nomeSetores.add(nomeSetor.toString());

                if (colaborador.getSetor().getNomeSetor().equals(nomeSetor.toString())) {
                    setorSelecionado = nomeSetor;
                }
             }

            Object nomeSetor = JOptionPane.showInputDialog(null, "Selecione o setor desejado", "Alteração dados cadastrais", JOptionPane.INFORMATION_MESSAGE,null,nomeSetores.toArray(), setorSelecionado);
            colaborador.setSetor(SetorDAO.buscarPorId(SetorDAO.buscarIdPorNome(nomeSetor.toString())));
        }

        ColaboradorDAO.editar(colaborador);

        JOptionPane.showMessageDialog(null, "DADOS EDITADOS SALVOS COM SUCESSO!", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void editarSetor() throws  SQLException, ClassNotFoundException{

        Setor setor = selecaoSetor();
        setor.setNomeSetor(JOptionPane.showInputDialog(null, "Digite o nome do setor: " , setor.getNomeSetor()));
        SetorDAO.editar(setor);

        JOptionPane.showMessageDialog(null, "DADOS EDITADOS SALVOS COM SUCESSO!", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
    }

    private static Colaborador selecaoColaborador() throws  SQLException, ClassNotFoundException{

        Object[] selectionValues = getColaboradorDAO().buscarNomes().toArray();
        String initialSelection = (String) selectionValues[0];
        Object selection = JOptionPane.showInputDialog(null, "Selecione o colaborador para editar",
                "Colaboradores", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);

        Integer id = ColaboradorDAO.buscarIdPorNome(selection.toString());
        return ColaboradorDAO.buscarPorID(id);
    }

    private static void excluirColaborador(Colaborador colaborador) throws SQLException, ClassNotFoundException{
        int id = colaborador.getId();
        if (id > 1) {
            ColaboradorDAO.excluir(colaborador.getId());
        }

        else {
            JOptionPane.showMessageDialog(null, "não é possível excluir o cadastro do administrador");
        }
    }

    private static void excluirSetor(Setor setor) throws SQLException, ClassNotFoundException{

        SetorDAO.excluir(setor.getIdSetor());

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
    }

    private static void menuEdicaoCadastro(Boolean admLogado) throws SQLException, ClassNotFoundException{

        String[] opcoesMenu = {"Cadastrar","Editar ", "Excluir", "Voltar"};
        int selecao = JOptionPane.showOptionDialog(null, "Selecione a opção desejada", "Cadastros", JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);

        switch (selecao){

            case 0:
                menuCadastro();
                break;

            case 1:
                editarColaborador(selecaoColaborador(), true);
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

