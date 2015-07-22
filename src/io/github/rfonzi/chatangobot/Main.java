package io.github.rfonzi.chatangobot;


import io.github.rfonzi.chatangobot.chatango.Connection;
import io.github.rfonzi.chatangobot.chatango.State;
import io.github.rfonzi.chatangobot.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class Main extends Application {

    Connection connection;
    TextArea consoleArea;
    Console console;


    @Override
    public void start(Stage primaryStage) throws Exception {

        consoleArea = new TextArea();
        consoleArea.setPrefColumnCount(64);
        consoleArea.setPrefRowCount(16);
        consoleArea.setWrapText(true);

        console = new Console(consoleArea);
        PrintStream ps = new PrintStream(console, true);
        Logger.setOutputStream(ps);

        connection = new Connection();

        primaryStage.setTitle("ChatangoBot");
        primaryStage.setResizable(false);

        Scene scene = new Scene(buildGrid());

        scene.getStylesheets().add("/io/github/rfonzi/chatangobot/style.css");


        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {


            if (State.getInstance().running) {
                connection.stop();
            }


        });
        primaryStage.show();

    }

    GridPane buildGrid() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(10);

        Text accountNameTitle = new Text("Account:");
        accountNameTitle.setId("account-name-title");
        grid.add(accountNameTitle, 0, 0);

        TextField accountNameField = new TextField();
        grid.add(accountNameField, 1, 0);

        Text passwordTitle = new Text("Pass:");
        passwordTitle.setId("password-title");
        grid.add(passwordTitle, 0, 1);

        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 1);

        Text roomNameTitle = new Text("Room:");
        roomNameTitle.setId("room-name-title");
        grid.add(roomNameTitle, 0, 2);

        TextField roomNameField = new TextField();
        grid.add(roomNameField, 1, 2);

        consoleArea.setEditable(false);
        consoleArea.setId("console");
        consoleArea.setText("test test test");
        grid.add(consoleArea, 0, 3, 3, 1);

        Button startButton = new Button();
        startButton.setText("Join");
        startButton.setOnAction(event -> {

            if (State.getInstance().running) {
                connection.stop();
            }

            connection.setLoginInfo(accountNameField.getCharacters().toString(), passwordField.getCharacters().toString());

            try {
                connection.setRoom(roomNameField.getCharacters().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            connection.start();


        });

        grid.add(startButton, 0, 4);


        return grid;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class Console extends OutputStream {

        private TextArea out;

        public Console(TextArea out) {
            this.out = out;
        }

        @Override
        public void write(int b) throws IOException {
            Platform.runLater(() -> out.appendText(String.valueOf((char) b)));
        }
    }
}
