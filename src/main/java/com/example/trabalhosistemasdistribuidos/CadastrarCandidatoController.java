package com.example.trabalhosistemasdistribuidos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import com.example.trabalhosistemasdistribuidos.banco.UsuarioDAO;
import com.example.trabalhosistemasdistribuidos.excecao.CampoVazioExcecao;
import com.example.trabalhosistemasdistribuidos.modelo.Usuario;

public class CadastrarCandidatoController {

    private Usuario usuario;
    private UsuarioDAO jpa;

    @FXML
    private Hyperlink voltar;

    @FXML
    private TextField login;

    @FXML
    private TextField nome;

    @FXML
    private PasswordField senha;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Label excecao;

    @FXML
    void cadastrar(ActionEvent event) { // Cadastra usuário no banco
        desativarExcecao();
        try {
            usuario = new Usuario();
            usuario.setLogin(this.login.getText());
            usuario.setNome(this.nome.getText());
            usuario.setSenha(this.senha.getText());
            jpa.cadastrar(usuario);
            novaExcecao("Usuário cadastrado com sucesso!", Color.GREEN);
        } catch (NumberFormatException NFE) {
            System.out.println("A mátricula deve conter apenas números");
            novaExcecao("A mátricula deve conter apenas números!", Color.RED);
        }catch(CampoVazioExcecao CVE){
            System.out.println("Necessário fornecer uma mátricula");
            novaExcecao("Necessário fornecer uma mátricula!", Color.RED);
        }catch (Exception ex){
            System.out.println(ex);
        }
    }

    private void desativarExcecao(){
        excecao.setVisible(false);
        excecao.textFillProperty().set(Color.RED);
    }

    private void novaExcecao(String text, Color color){
        excecao.setText(text);
        excecao.textFillProperty().set(color);
        excecao.setVisible(true);
    }

    @FXML
    void voltarLogin(ActionEvent event) {
        ClientApplication.trocarTela("Login");
    }

    @FXML
    void initialize() {
        jpa = new UsuarioDAO();
    }

}
