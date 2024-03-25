package com.example.trabalhosistemasdistribuidos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;

public class ClientApplication extends Application {
    private static Stage stage;
    private static Scene principalEmpresa;
    private static Scene principalCandidato;
    private static Scene login;
    private static Scene cadastrarCandidado;
    private static Scene cadastrarEmpresa;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        primaryStage.setTitle("Portal de Vagas");

        Parent fxmlLogin = FXMLLoader.load(getClass().getResource("Login.fxml"));
        login = new Scene(fxmlLogin);

        Parent fxmlPrincipalEmpresa = FXMLLoader.load(getClass().getResource("PrincipalEmpresa.fxml"));
        principalEmpresa = new Scene(fxmlPrincipalEmpresa);

        Parent fxmlPrincipalCandidato = FXMLLoader.load(getClass().getResource("PrincipalCandidato.fxml"));
        principalCandidato = new Scene(fxmlPrincipalCandidato);

        Parent fxmlEmpresa = FXMLLoader.load(getClass().getResource("CadastrarEmpresa.fxml"));
        cadastrarEmpresa = new Scene(fxmlEmpresa);

        Parent fxmlCandidato = FXMLLoader.load(getClass().getResource("CadastrarCandidato.fxml"));
        cadastrarCandidado = new Scene(fxmlCandidato);

        primaryStage.setScene(login);
        primaryStage.show();
    }

    public static void trocarTela(String tela){ // Realiza a troca de tela de acordo com a informada no controller
        switch (tela) {
            case "PrincipalEmpresa":
                stage.setScene(principalEmpresa);
                break;
            
            case "PrincipalCandidato":
                stage.setScene(principalCandidato);
                break;
        
            case "Login":
                stage.setScene(login);
                break;
            
            case "CadastrarEmpresa":
                stage.setScene(cadastrarEmpresa);
                break;
            
            case "CadastrarCandidato":
                stage.setScene(cadastrarCandidado);
                break;
        }
    }

    public static void escreverLog(String texto){ // Realiza a escrita no log
        System.out.println(texto);
    }

    public static void main(String[] args) {
        //Inicia o programa
        launch();
    }
}