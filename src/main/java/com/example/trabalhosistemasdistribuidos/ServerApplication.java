package com.example.trabalhosistemasdistribuidos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;

public class ServerApplication extends Application {
    private static Scene server;

    @Override
    public void start(@SuppressWarnings("exports") Stage primaryStage) throws IOException {
        primaryStage.setTitle("Portal de Vagas - Servidor");

        Parent fxmlServer = FXMLLoader.load(getClass().getResource("Server.fxml"));
        server = new Scene(fxmlServer);

        primaryStage.setScene(server);
        primaryStage.show();
    }

    public static void main(String[] args) {
        //Inicia o programa
        launch();
    }
}