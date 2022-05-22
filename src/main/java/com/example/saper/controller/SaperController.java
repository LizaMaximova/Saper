package com.example.saper.controller;

import com.example.saper.Main;
import com.example.saper.model.Game;
import com.example.saper.model.items.MoveResult;
import com.example.saper.view.GameView;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

public class SaperController {

    @FXML public Label countFlag;
    @FXML private Text time;
    @FXML private Button button;
    @FXML private Canvas canvas;

    private GameView gameView;
    private Game game;

    DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public void initialize() {

        canvas.heightProperty().addListener(e -> gameView.draw());
        canvas.widthProperty().addListener(e -> gameView.draw());
    
        button.setStyle("-fx-background-image: url('file:A:/CourseWork/Saper/FirstSmile.png')");

        game = new Game();
        gameView = new GameView(game,canvas);
        gameView.draw();

        initializeTexts();
    }

    private void initializeTexts() {

        countFlag.setFont(Font.font("Cambria",22));
        countFlag.setTextFill(Color.web("#000000"));
        countFlag.setGraphic(new ImageView(new Image("A:\\CourseWork\\Saper\\flag.png")));

        time.setFont(Font.font("Cambria",22));

        setTimeText(0);
        writeText();
    }

    private void setTimeText(long elapsedSeconds) {

        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        final Date date = new Date(elapsedSeconds * 1000);
        time.setText(timeFormat.format(date));
        game.getGameTimer().setTimeListener(this::setTimeText);

    }

    private void writeText() {

        countFlag.setText("" + game.getCountFlag());
    }


    public void processMouse(MouseEvent mouseEvent) {

        int x1 = (int) (mouseEvent.getX()/32) + 1;
        int y1 = (int) (mouseEvent.getY()/32) + 1;

        if (game.getMoveResult() == MoveResult.START) {
            game.getGameTimer().start();
            game.setMoveResult(MoveResult.SIMPLE);
        }

        if (game.getMoveResult() != MoveResult.IMPOSSIBLE) {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (game.getGridView().getGrid()[x1][y1] == 11) {
                    game.changeCountFlag(1);
                    game.selectArea(x1,y1,button);
                } else game.selectArea(x1, y1,button);
            } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    if (game.getGridView().getGrid()[x1][y1] == 11) {
                        game.getGridView().getGrid()[x1][y1] = 10;
                        game.changeCountFlag(1);
                    } else if (game.getCountFlag() != 0 && game.getGridView().getGrid()[x1][y1] == 10) {
                    game.getGridView().getGrid()[x1][y1] = 11;
                    game.changeCountFlag(-1);
                }
            }
        }
        gameView.draw();
        writeText();

        if (game.getMoveResult() == MoveResult.WIN) {
            game.getGameTimer().reset();
            showAlert();
        }
    }

    private void showAlert() {

        ButtonType foo = new ButtonType("Спочатку", ButtonBar.ButtonData.OK_DONE);
        ButtonType bar = new ButtonType("Вихід", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"",foo,bar);
        alert.setTitle("Молодець!");
        alert.setHeaderText("Гра пройдена за: " + time.getText());
        Optional<ButtonType> option = alert.showAndWait();
        if (option.orElse(foo) == foo) {
            alert.close();
            ((Stage) button.getScene().getWindow()).close();
            Main main = new Main();
            try {
                main.start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (option.orElse(bar) == bar) {
            alert.close();
            ((Stage) button.getScene().getWindow()).close();
        }
    }


    public void clickOnRestart() {
        ((Stage) button.getScene().getWindow()).close();
        Main main = new Main();
        try {
            main.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}