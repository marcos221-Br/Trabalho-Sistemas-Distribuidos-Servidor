package com.example.trabalhosistemasdistribuidos;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class PrincipalCandidatoController {

    private Parent fxmlCandidato = null;
    private Parent fxmlCompetencia = null;
    private Parent fxmlVisualizar = null;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane principal;

    //Eventos de botão
    @FXML
    void visualizar(ActionEvent event) {
        setTela("Visualizar");
    }

    @FXML
    void CadastrarCompetencia(ActionEvent event) {
        setTela("Competencia");
    }

    @FXML
    void cadastrarCandidato(ActionEvent event) {
        setTela("Candidato");
    }

    @FXML
    void deslogar(ActionEvent event) {
        ClientApplication.trocarTela("Login");
    }

    //Eventos de teclado
    @FXML
    void telas(KeyEvent event) {
        if(event.getCode() == KeyCode.F1){
            visualizar(null);
        }
        if(event.getCode() == KeyCode.F2){
            cadastrarCandidato(null);
        }
        if(event.getCode() == KeyCode.F4){
            CadastrarCompetencia(null);
        }
        if(event.getCode() == KeyCode.ESCAPE){
            deslogar(null);
        }
    }

    private void setTela(String tela){
        switch (tela) {
            case "Candidato":
                principal.setCenter(fxmlCandidato);
                break;
            
            case "Competencia":
                principal.setCenter(fxmlCompetencia);
                break;
            
            case "Visualizar":
                principal.setCenter(fxmlVisualizar);
                break;
        }
    }

    //Adicionado evento de mouse, clique direito no menu lista as telas
    @FXML
    void initialize() {
        principal.setCenter(null);
        try {
            fxmlCandidato = FXMLLoader.load(getClass().getResource("Candidato.fxml"));
            fxmlCompetencia = FXMLLoader.load(getClass().getResource("CadastrarCompetencia.fxml"));
            fxmlVisualizar = FXMLLoader.load(getClass().getResource("VisualizarRemedioIdoso.fxml"));
            principal.setCenter(fxmlVisualizar);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
