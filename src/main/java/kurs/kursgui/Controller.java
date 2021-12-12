package kurs.kursgui;

import Classes.Timer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Controller {

    private final Stage stage = new Stage();

    @FXML
    private TextArea completedArea;

    @FXML
    private Button exit;

    @FXML
    private Button generate;

    @FXML
    private TextArea queueArea;

    @FXML
    private TextArea rejectedArea;

    @FXML
    void initialize() {
        Scheduler scheduler = new Scheduler(4,4096);
        Thread print = new PrintThread(queueArea, completedArea, rejectedArea ,scheduler);
        print.start();
        scheduler.start();
        Thread timer = new Timer(scheduler.getCpu());
        timer.start();
        exit.setOnAction(actionEvent -> {
            ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
            stage.close();
            scheduler.getCpu().setActive(false);
            System.exit(0);
        });
        generate.setOnAction(actionEvent -> scheduler.getQueueCPU().add(10));


    }

}
