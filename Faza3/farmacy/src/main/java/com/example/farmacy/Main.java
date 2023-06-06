package com.example.farmacy;

import com.example.farmacy.controllers.LoginController;
import com.example.farmacy.repository.HibernateUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import com.example.farmacy.service.Service;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 300);
        LoginController controller=fxmlLoader.getController();
        SessionFactory sessionFactory = HibernateUtils.initialize();
        controller.SetService(Service.getInstance(sessionFactory));
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
