package view.userInterface;

import controller.ControlCenter;
import controller.InputEvent;
import controller.InputEventType;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class Controller {

    public void testAction(ActionEvent actionEvent) {
        ControlCenter.getControlCenter().handleUserInput(new InputEvent(InputEventType.TEST));
    }
}
