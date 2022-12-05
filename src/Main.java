import entidades.Colaborador;
import entidades.FormatoSenha;
import enums.Setor;
import classes_dao.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        ColaboradorDAO.carregarDados();
        MenuInicial();

    }
    private static void MenuInicial(){
        String[] opcoesInicio = {"Login", "Cadastro", "Sair"};
        int idOpInicio = JOptionPane.showOptionDialog(null, "Selecione a opção desejada", "Menu_Inicial", JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, opcoesInicio, opcoesInicio[0]);

        if (idOpInicio == 0) {
            MenuLogin();
            MenuInicial();
        }
        else if (idOpInicio == 1) {
            MenuCadastro();
            MenuInicial();
        }

        else {
            System.exit(0);
        }
        // login (0), cadastro(1), Sair(2)  x(-1)
    }

    private static void MenuLogin() {
        String login = JOptionPane.showInputDialog(null, "Digite seu login");
        String senha = JOptionPane.showInputDialog(null, "Digite sua Senha de 8 caracteres númericos");

        Colaborador colaborador = ColaboradorDAO.buscarPorLoginESenha(login, senha);

        if (colaborador != null) {
            if (colaborador.getNome() == "admin") {
                MenuOpcoesAdmin();
            }
            else {
                MenuOpcoesColaborador();
            }
            JOptionPane.showMessageDialog(null, "USUÁRIO DESCONECTADO COM SUCESSO!", "Mensagem de Saída", JOptionPane.INFORMATION_MESSAGE);
            MenuInicial();
        }

        else {
            String[] opcoes = {"Sim", "Não"};
            int idOP = JOptionPane.showOptionDialog(null, "Não há nenhum cadastro com o login e senha informado, deseja se cadastrar?", "Menu Login", JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null,opcoes, opcoes[0]);

            if (idOP == 0) {
                MenuCadastro();
            }

        }
    }

    private static void MenuCadastro() {
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
        MenuInicial();
    }

    private static void MenuOpcoesColaborador() {
            String[] opcoesMenu = {"Editar Dados do cadastro", "Publicar Ideia", "Ver Ideias Publicadas", "Votar em uma ideia", "Sair"};
            JOptionPane.showOptionDialog(null, "Selecione a opção desejada", "Menu", JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);

    }

    private static void MenuOpcoesAdmin() {
        String[] opcoesMenu = {"Editar Dados do cadastro", "Excluir cadastro de um colaborador", "Ver Ideias Publicadas", "Dar feedback a uma ideia", "Sair"};
        JOptionPane.showOptionDialog(null, "Selecione a opção desejada", "Menu", JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);

    }


}

