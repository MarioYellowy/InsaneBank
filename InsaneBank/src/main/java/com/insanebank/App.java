package com.insanebank;

import com.insanebank.config.AppConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class App extends Application {
    private static ApplicationContext applicationContext;

    @Override
    public void init() throws IOException {
        applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/home-view.fxml"));
        fxmlLoader.setControllerFactory(applicationContext::getBean);

        Parent root = fxmlLoader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        if (applicationContext instanceof AnnotationConfigApplicationContext) {
            ((AnnotationConfigApplicationContext) applicationContext).close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
