package com.example.trabalhosistemasdistribuidos;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CadastrarVagaController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnExcluir;

    @FXML
    private TextField categoria;

    @FXML
    private Label excecao;

    @FXML
    private TextField nome;

    @FXML
    private TextField nomeGenerico;

    @FXML
    void alterar(ActionEvent event) {

    }

    @FXML
    void cadastrar(ActionEvent event) {

    }

    @FXML
    void excluir(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnAlterar != null : "fx:id=\"btnAlterar\" was not injected: check your FXML file 'CadastrarVaga.fxml'.";
        assert btnCadastrar != null : "fx:id=\"btnCadastrar\" was not injected: check your FXML file 'CadastrarVaga.fxml'.";
        assert btnExcluir != null : "fx:id=\"btnExcluir\" was not injected: check your FXML file 'CadastrarVaga.fxml'.";
        assert categoria != null : "fx:id=\"categoria\" was not injected: check your FXML file 'CadastrarVaga.fxml'.";
        assert excecao != null : "fx:id=\"excecao\" was not injected: check your FXML file 'CadastrarVaga.fxml'.";
        assert nome != null : "fx:id=\"nome\" was not injected: check your FXML file 'CadastrarVaga.fxml'.";
        assert nomeGenerico != null : "fx:id=\"nomeGenerico\" was not injected: check your FXML file 'CadastrarVaga.fxml'.";

    }

}
