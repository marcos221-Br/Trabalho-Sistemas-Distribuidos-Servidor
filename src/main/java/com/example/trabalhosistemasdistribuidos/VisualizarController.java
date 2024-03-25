package com.example.trabalhosistemasdistribuidos;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.trabalhosistemasdistribuidos.excecao.CampoVazioExcecao;
import com.example.trabalhosistemasdistribuidos.modelo.HorarioRemedio;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class VisualizarController {

    private ObservableList<HorarioRemedio> lista;
    private String[] listaHorario;
    private String[] listaDias;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<HorarioRemedio, String> domingo;

    @FXML
    private TableColumn<HorarioRemedio, String> horario;

    @FXML
    private TableColumn<HorarioRemedio, String> quarta;

    @FXML
    private TableColumn<HorarioRemedio, String> quinta;

    @FXML
    private TableColumn<HorarioRemedio, String> sabado;

    @FXML
    private TableColumn<HorarioRemedio, String> segunda;

    @FXML
    private TableColumn<HorarioRemedio, String> sexta;

    @FXML
    private TableColumn<HorarioRemedio, String> terca;

    @FXML
    private TableView<HorarioRemedio> tabela;

    @FXML
    private TextField nome;

    @FXML
    private Label excecao;

    @FXML
    void buscar(ActionEvent event) {
        excecao.setVisible(false);
        try{
            if(nome.getText().equals("")){
                throw new CampoVazioExcecao();
            }
            lista = FXCollections.observableArrayList();
            for(int i = 0;i<24;i++){
                if(listaHorario[i] != ""){
                    lista.add(new HorarioRemedio(listaHorario[i],listaDias[0],listaDias[1],listaDias[2],listaDias[3],listaDias[4],listaDias[5],listaDias[6]));
                }
            }
            montarTabela();
        }catch(CampoVazioExcecao CVE){
            System.out.println(CVE);
            excecao.setText("Necessário fornecer um nome!");
            excecao.setVisible(true);
        }catch(Exception e){
            System.out.println("Idoso não encontrado");
            excecao.setText("Nome não encontrado!");
            excecao.setVisible(true);
        }
    }

    @FXML
    void buscarTeclado(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            buscar(null);
        }
    }

    private void montarTabela(){
        tabela.setItems(lista);
        horario.setCellValueFactory(new PropertyValueFactory<>("horario"));
        segunda.setCellValueFactory(new PropertyValueFactory<>("segunda"));
        terca.setCellValueFactory(new PropertyValueFactory<>("terca"));
        quarta.setCellValueFactory(new PropertyValueFactory<>("quarta"));
        quinta.setCellValueFactory(new PropertyValueFactory<>("quinta"));
        sexta.setCellValueFactory(new PropertyValueFactory<>("sexta"));
        sabado.setCellValueFactory(new PropertyValueFactory<>("sabado"));
        domingo.setCellValueFactory(new PropertyValueFactory<>("domingo"));
    }

    @FXML
    void initialize() {
        
    }
}
