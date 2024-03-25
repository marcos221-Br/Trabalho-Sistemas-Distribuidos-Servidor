package com.example.trabalhosistemasdistribuidos;

import com.example.trabalhosistemasdistribuidos.banco.UsuarioDAO;
import com.example.trabalhosistemasdistribuidos.excecao.CampoVazioExcecao;
import com.example.trabalhosistemasdistribuidos.modelo.Login;
import com.example.trabalhosistemasdistribuidos.modelo.Usuario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class CandidatoController {

    private Usuario usuario;
    private UsuarioDAO jpa;

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnExcluir;

    @FXML
    private TextField login;

    @FXML
    private TextField nome;

    @FXML
    private PasswordField senha;

    @FXML
    private Label excecao;

    @FXML
    private Button dados;

    @FXML
    void alterar(ActionEvent event) { // Altera usuário no banco
        desativarExcecao();
        try {
            usuario = new Usuario();
            usuario = jpa.buscar(usuario);
            if(usuario != null){
                usuario.setLogin(login.getText());
                usuario.setNome(nome.getText());
                usuario.setSenha(senha.getText());
                jpa.editar(usuario);
            }
            novaExcecao("Usuário alterado com sucesso!", Color.GREEN);
        } catch (NumberFormatException NFE) {
            System.out.println("A mátricula deve conter apenas números");
            novaExcecao("A mátricula deve conter apenas números!", Color.RED);
        } catch(CampoVazioExcecao CVE){
            System.out.println("Campo matrícula ou senha vazio!");
            novaExcecao("Necessário fornecer uma mátricula!", Color.RED);
        } catch(Exception ex){
            System.out.println(ex);
        }
    }

    @FXML
    void excluir(ActionEvent event) { // Exclui um usuário no banco
        desativarExcecao();
        try {
            usuario = new Usuario();
            jpa.excluir(usuario);
            this.login.setText("");
            this.nome.setText("");
            this.senha.setText("");
            novaExcecao("Usuário excluido com sucesso!", Color.GREEN);
        } catch (NumberFormatException NFE) {
            System.out.println("A mátricula deve conter apenas números");
            novaExcecao("A mátricula deve conter apenas números!", Color.RED);
        } catch(CampoVazioExcecao CVE){
            System.out.println("A matricula está vazia!");
            novaExcecao("Necessário fornecer uma mátricula!", Color.RED);
        } catch(Exception ex){
            System.out.println(ex);
        }
    }

    @FXML
    void carregar(ActionEvent event) {
        login.setText(Login.getLogin());
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
    void initialize() {
        
    }
}