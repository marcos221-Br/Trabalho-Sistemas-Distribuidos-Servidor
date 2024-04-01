package com.example.trabalhosistemasdistribuidos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;

public class ServerApplication extends Application {
    private static Stage stage;
    private static Scene server;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        primaryStage.setTitle("Portal de Vagas - Servidor");

        Parent fxmlServer = FXMLLoader.load(getClass().getResource("Server.fxml"));
        server = new Scene(fxmlServer);

        primaryStage.setScene(server);
        primaryStage.show();
    }

    public static void escreverLog(String texto){ // Realiza a escrita no log
        System.out.println(texto);
    }

    public static void main(String[] args) {
        //Inicia o programa
        launch();
    }
}