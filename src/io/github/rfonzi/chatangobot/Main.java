package io.github.rfonzi.chatangobot;


import io.github.rfonzi.chatangobot.chatango.Connection;
import io.github.rfonzi.chatangobot.chatango.Settings;
import io.github.rfonzi.chatangobot.chatango.State;
import io.github.rfonzi.chatangobot.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Set;

public class Main extends Application {

    Connection connection;
    TextArea consoleArea;
    Console console;
    Settings settings;

    TextField accountNameField;
    PasswordField passwordField;
    TextField roomNameField;


    @Override
    public void start(Stage primaryStage) throws Exception {
        settings = new Settings("settings.ini");
        settings.load();

        consoleArea = new TextArea();
        console = new Console(consoleArea);
        PrintStream ps = new PrintStream(console, true);
        Logger.setOutputStream(ps);

        if(!Font.getFamilies().contains("Roboto Light")){
            if(Font.loadFont("https://github.com/google/fonts/raw/master/apache/roboto/Roboto-Light.ttf", 0) == null)
                Logger.warn("Couldn't download Roboto. Falling back to default font.");
        }

        if(!Font.getFamilies().contains("Roboto Mono Light")){
            if(Font.loadFont("https://github.com/google/fonts/raw/master/apache/robotomono/RobotoMono-Light.ttf", 0) == null)
                Logger.warn("Couldn't download Roboto Mono Light. Falling back to default font.");
        }


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

            settings.set(accountNameField.getText(), passwordField.getText(), roomNameField.getText());
            settings.save();


        });
        primaryStage.show();

    }

    GridPane buildGrid() {
        GridPane grid = new GridPane();
        //grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(10);

        Text roomNameTitle = new Text("Room:");
        roomNameTitle.setId("room-name-title");
        grid.add(roomNameTitle, 0, 0);

        roomNameField = new TextField();
        roomNameField.setText(settings.roomName);
        grid.add(roomNameField, 1, 0);

        Text accountNameTitle = new Text("Account:");
        accountNameTitle.setId("account-name-title");
        grid.add(accountNameTitle, 2, 0);

        accountNameField = new TextField();
        accountNameField.setText(settings.accountName);
        accountNameField.setMaxWidth(Button.USE_PREF_SIZE);
        grid.add(accountNameField, 3, 0);

        Text passwordTitle = new Text("Pass:");
        passwordTitle.setId("password-title");
        grid.add(passwordTitle, 2, 1);

        passwordField = new PasswordField();
        passwordField.setText(settings.password);
        passwordField.setMaxWidth(Button.USE_PREF_SIZE);
        grid.add(passwordField, 3, 1);

        consoleArea.setEditable(false);
        grid.add(consoleArea, 0, 3, 4, 1);

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


        startButton.setMaxWidth(Double.MAX_VALUE);

        grid.add(startButton, 1, 1);

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
