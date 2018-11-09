package view.userInterface;

import controller.ControlCenter;
import controller.InputEvent;
import controller.InputEventType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ViewController {

    @FXML
    private Button testButton;

    public void testAction(ActionEvent actionEvent) {
        ControlCenter.getControlCenter().handleUserInput(new InputEvent(InputEventType.TEST));
    }

    public void testUpdate() {
        testButton.setText("UPDATEDDDDD!");
    }

}