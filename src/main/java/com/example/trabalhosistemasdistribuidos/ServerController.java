package com.example.trabalhosistemasdistribuidos;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ServerController{

    private static ObservableList<String> list;
    private static SocketServer server;
    private static Thread thread;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> clientes;

    public synchronized void adicionarTabela(String ip){
        list.add(ip);
        clientes.setItems(list);
    }

    public synchronized void removerTabela(String ip){
        int i = 0;
        for (String string : list) {
            if(string.equals(ip)){
                list.remove(i);
            }
            i++;
        }
        clientes.setItems(list);
    }

    private void iniciarServidor(){
        server = new SocketServer(22222);
        server.iniciar();
        thread = new Thread("Thread Server"){
            public void run(){
                server.abrirConexao();
            }
        };
        thread.start();
    }

    @FXML
    void initialize() {
        list = FXCollections.observableArrayList();
        iniciarServidor();
    }
}