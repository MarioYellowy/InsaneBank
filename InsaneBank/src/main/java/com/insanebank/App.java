package com.insanebank;

import com.insanebank.config.AppConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class App extends Application {
    private static ApplicationContext springContext;

    @Override
    public void init() throws Exception {
        springContext = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("home-view.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);

        Scene scene = new Scene(fxmlLoader.load(), 1320, 798);
        primaryStage.setTitle("InsaneBank");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        if (springContext instanceof AnnotationConfigApplicationContext) {
            ((AnnotationConfigApplicationContext) springContext).close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}