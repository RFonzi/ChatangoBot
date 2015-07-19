package io.github.rfonzi.chatangobot;


import io.github.rfonzi.chatangobot.chatango.Bot;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ChatangoBot");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(10);

        Text accountNameTitle = new Text("Account:");
        grid.add(accountNameTitle, 0, 0);

        TextField accountNameField = new TextField();
        grid.add(accountNameField, 1, 0);

        Text passwordTitle = new Text("Pass:");
        grid.add(passwordTitle, 0, 1);

        TextField passwordField = new TextField();
        grid.add(passwordField, 1, 1);

        Text roomNameTitle = new Text("Room:");
        grid.add(roomNameTitle, 0, 2);

        TextField roomNameField = new TextField();
        grid.add(roomNameField, 1, 2);

        Button startButton = new Button();
        startButton.setText("Join");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    Bot bot = new Bot();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        grid.add(startButton, 2, 0);



        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
