open module com.example.trabalhosistemasdistribuidos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires org.json;
    requires com.auth0.jwt;

    exports com.example.trabalhosistemasdistribuidos;
}