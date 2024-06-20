package com.example.trabalhosistemasdistribuidos;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.trabalhosistemasdistribuidos.banco.IpDAO;
import com.example.trabalhosistemasdistribuidos.modelo.Ips;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ServerController{

    private static ObservableList<Ips> list;
    private static SocketServer server;
    private static Thread thread;
    private static Thread thread2;
    private static IpDAO jpaIp;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Ips, String> columnIps;

    @FXML
    private TableView<Ips> tableIps;

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
        jpaIp = new IpDAO();
        columnIps.setCellValueFactory(new PropertyValueFactory<>("ip"));
        tableIps.setItems(list);

        iniciarServidor();

        thread2 = new Thread("Thread Update Table"){
            public void run(){
                while (true) {
                    try {
                        list.clear();
                        list.addAll(jpaIp.buscarTodos());
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread2.start();
    }
}