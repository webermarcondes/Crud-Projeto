import entidades.Colaborador;
import entidades.FormatoSenha;
import entidades.Ideia;
import enums.Setor;
import classesDAO.*;

import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        ColaboradorDAO.carregarDados();
        menuInicial();


    }

    private static void menuInicial(){
        String[] opcoesInicio = {"Login", "Cadastro", "Sair"};
        int idOpInicio = JOptionPane.showOptionDialog(null, "Selecione a opção desejada", "Menu_Inicial", JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, opcoesInicio, opcoesInicio[0]);

        switch (idOpInicio) {

            case 0:
                menuLogin();
                break;

            case 1:
                menuCadastro();
                break;

            case 2:
                System.exit(0);
                break;
        }
    }
    private static void menuLogin() {

        String login = JOptionPane.showInputDialog(null, "Digite seu login");
        String senha = JOptionPane.showInputDialog(null, "Digite sua Senha de 8 caracteres númericos");

        Colaborador colaborador = ColaboradorDAO.buscarPorLoginESenha(login, senha);

        if (colaborador != null) {
            if (colaborador.getNome() == "admin") {
                menuOpcoesAdmin();
            }
            else {
                menuOpcoesColaborador();
            }
            JOptionPane.showMessageDialog(null, "USUÁRIO DESCONECTADO COM SUCESSO!", "Mensagem de Saída", JOptionPane.INFORMATION_MESSAGE);
            menuInicial();
        }

        else {
            String[] opcoes = {"Sim", "Não"};
            int idOP = JOptionPane.showOptionDialog(null, "Não há nenhum cadastro com o login e senha informado, deseja se cadastrar?", "Menu Login", JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null,opcoes, opcoes[0]);

            if (idOP == 0) {
                menuCadastro();
            }
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

        Setor[] setores = Setor.values();
        colaborador.setSetor(Setor.getSetorById(JOptionPane.showOptionDialog(null, "Selecione o setor qual você faz parte", "Cadastro de colaborador", JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE,null, setores, setores[0])));

        JOptionPane.showMessageDialog(null, "COLABORADOR CADASTRADO COM SUCESSO!", "Cadastro de colaborador", JOptionPane.INFORMATION_MESSAGE);

        ColaboradorDAO.salvar(colaborador);
        menuInicial();
    }

    private static void menuOpcoesColaborador() {
            String[] opcoesMenu = {"Editar Dados do cadastro", "Publicar Ideia", "Ver Ideias Publicadas", "Votar em uma ideia", "Sair"};
            JOptionPane.showOptionDialog(null, "Selecione a opção desejada", "Menu", JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);

    }

    private static Ideia selecaoDeIdeias(){

        Object[] selectionValues = getIdeiasDAO().findIdeiasInArray();
        String initialSelection = (String) selectionValues[0];
        Object selection = JOptionPane.showInputDialog(null, "Selecione o colaborador para editar",
                "Colaboradores", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        List<Ideia> ideiaList = getIdeiasDAO().buscarTodos();

        return ideiaList.get(0);
    }

    private static void visualizacaoDeIdeias(Ideia idea){

    }

    private static void menuOpcoesAdmin() {

        String[] opcoesMenu = {"Cadastros", "Adicionar ideia","Visualizar Ideias", "Dar feedback a uma ideia", "Sair"};
        int selecao = JOptionPane.showOptionDialog(null, "Selecione a opção desejada", "Menu", JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);
        switch (selecao){
            case 0:
            menuEdicaoCadastro();
        break;

            case 1:
                realizarIdeais();
                break;

            case 2:
                visualizacaoDeIdeias(selecaoDeIdeias());
                break;
        }
    }

    private static void editarColaborador(Colaborador colaborador){

        colaborador.setNome(JOptionPane.showInputDialog(null, "Digite o nome: " , colaborador.getNome()));
        colaborador.setLogin(JOptionPane.showInputDialog(null, "Digite o login: " , colaborador.getLogin()));
        colaborador.setSenha(JOptionPane.showInputDialog(null, "Digite a senha: " , colaborador.getSenha()));
        Setor[] setores = Setor.values();
        colaborador.setSetor(Setor.getSetorById(JOptionPane.showOptionDialog(null, "Selecione o setor para editar", "Edição de colaborador", JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE,null, setores, setores[0])));
        menuOpcoesAdmin();

    }

    private static Colaborador selecaoColaborador(){

        Object[] selectionValues = getColaboradorDAO().findColaboradorInArray();
        String initialSelection = (String) selectionValues[0];
        Object selection = JOptionPane.showInputDialog(null, "Selecione o colaborador para editar",
                "Colaboradores", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        List<Colaborador> colaboradorList = getColaboradorDAO().buscarTodos();

        return colaboradorList.get(0);
    }

    private static Colaborador excluirColaborador(){

        Object[] selectionValues = getColaboradorDAO().findColaboradorInArray();
        String initialSelection = (String) selectionValues[0];
        Object selection = JOptionPane.showInputDialog(null, "Selecione o colaborador para editar",
                "Colaboradores", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        List<Colaborador> colaboradorList = getColaboradorDAO().buscarTodos();

        return colaboradorList.remove(0);
    }

    private static void realizarIdeais(){

        Ideia ideia = new Ideia();

        Setor[] setores = Setor.values();
        ideia.setSetor(Setor.getSetorById(JOptionPane.showOptionDialog(null, "Selecione o setor para adicionar a ideia", "Inserir ideia", JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE,null, setores, setores[0])));
        ideia.setDescricao(JOptionPane.showInputDialog(null, "Digite a ideia: " ));
        IdeiaDAO.salvarIdeia(ideia);

    }

    private static void menuEdicaoCadastro(){

        String[] opcoesMenu = {"Editar ", "Excluir", "Voltar"};
        int selecao = JOptionPane.showOptionDialog(null, "Selecione a opção desejada", "Cadastros", JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);

        switch (selecao){

            case 0:
                editarColaborador(selecaoColaborador());
                break;

            case 1:
                excluirColaborador();
                break;

            case 2:
                menuOpcoesAdmin();
                break;
        }
    }

    private static ColaboradorDAO getColaboradorDAO(){
        ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
        return colaboradorDAO;
    }

    private static IdeiaDAO getIdeiasDAO(){
        IdeiaDAO ideiaDAO = new IdeiaDAO();
        return ideiaDAO;
    }
}

