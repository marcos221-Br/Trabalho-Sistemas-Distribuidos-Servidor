package com.example.trabalhosistemasdistribuidos;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class CadastrarCompetenciaController {

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
    private TextArea experiencia;

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
        assert btnAlterar != null : "fx:id=\"btnAlterar\" was not injected: check your FXML file 'CadastrarCompetencia.fxml'.";
        assert btnCadastrar != null : "fx:id=\"btnCadastrar\" was not injected: check your FXML file 'CadastrarCompetencia.fxml'.";
        assert btnExcluir != null : "fx:id=\"btnExcluir\" was not injected: check your FXML file 'CadastrarCompetencia.fxml'.";
        assert experiencia != null : "fx:id=\"experiencia\" was not injected: check your FXML file 'CadastrarCompetencia.fxml'.";

    }

}