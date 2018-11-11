package view.userInterface;

import controller.ControlCenter;
import controller.InputEvent;
import controller.InputEventType;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import model.SimulationModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ViewController {

    // stuff for properties file so user can have user define keybinds for keyboard commands
    private static final String PROPERTIES_FILE_NAME = "config.properties";
    private Properties prop = new Properties();
    private InputStream input = null;

    // TODO probably change this
    private static final boolean ENGINE_STATE_ON = true;

    @FXML
    private ImageView steeringWheelImage;

    @FXML
    private BorderPane mainViewBorderPane;

    @FXML
    public void initialize() {
        setupPropertiesFile();

        mainViewBorderPane.setOnKeyPressed(key -> handleKeyboardInputKeyDown(key.getCode()));
        mainViewBorderPane.setOnKeyReleased(key -> handleKeyboardInputKeyUp(key.getCode()));
        mainViewBorderPane.setOnMouseEntered(m -> mainViewBorderPane.requestFocus());
    }

    // TODO move this somewhere else
    private void setupPropertiesFile() {
        try {
            input = ViewController.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
            if (input == null) {
                throw new FileNotFoundException("Cannot find properties file that conatins keybinds.");
            }

            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // this part is getting confusing cause the view shouldnt be showing the car the is off unless it is, which means
    // that an update of the view to reflect the engine being off needs to wait until next tick, but that means that we
    // might have some issues later on
    public void handleEngineOnOffButton() {
        if(ENGINE_STATE_ON) {
            handleEngineOff();
        } else {
            handleEngineOn();
        }
    }


    public void updateView() {
        // Update view components
        System.out.println("Updating the view components");
        SimulationModel model = ControlCenter.getControlCenter().getModel();
        
        // Draw car on screen at right position 
        
        // Update steering wheel position
        steeringWheelImage.setRotate(model.carState.getFrontWheelDeviation());
        
        // Update gas / brake pedal pictures
        
        // Update information listed on right of screen
    }

    private void handleKeyboardInputKeyDown(KeyCode keyCode) {

        System.out.println("Key down: " + keyCode);
        if (keyCode == KeyCode.getKeyCode(prop.getProperty("keybind_accelerate_primary")) || keyCode == KeyCode.getKeyCode(prop.getProperty("keybind_accelerate_secondary"))) {
            handleAccelerate();
        } else if (keyCode == KeyCode.getKeyCode(prop.getProperty("keybind_turn_left_primary")) || keyCode == KeyCode.getKeyCode(prop.getProperty("keybind_turn_left_secondary"))) {
            handleTurnCarLeftEvent();
        } else if (keyCode == KeyCode.getKeyCode(prop.getProperty("keybind_turn_right_primary")) || keyCode == KeyCode.getKeyCode(prop.getProperty("keybind_turn_right_secondary"))) {
            handleTurnCarRightEvent();
        } else if (keyCode == KeyCode.getKeyCode(prop.getProperty("keybind_slow_down_primary")) || keyCode == KeyCode.getKeyCode(prop.getProperty("keybind_slow_down_secondary"))) {
            handleSlowDown();
        } else if (keyCode == KeyCode.getKeyCode(prop.getProperty("keybind_shift_gear_down"))) {
            handleShiftGearDown();
        } else if (keyCode == KeyCode.getKeyCode(prop.getProperty("keybind_shift_gear_up"))) {
            handleShiftGearUp();
        }
    }
    
    private void handleKeyboardInputKeyUp(KeyCode keyCode) {

        System.out.println("Key up: " + keyCode);
       
        if (keyCode == KeyCode.getKeyCode(prop.getProperty("keybind_accelerate_primary")) || keyCode == KeyCode.getKeyCode(prop.getProperty("keybind_accelerate_secondary"))) {
            ControlCenter.getControlCenter().handleUserInput(new InputEvent(InputEventType.NO_GAS));
        } else if (keyCode == KeyCode.getKeyCode(prop.getProperty("keybind_turn_left_primary")) || keyCode == KeyCode.getKeyCode(prop.getProperty("keybind_turn_left_secondary"))) {
            ControlCenter.getControlCenter().handleUserInput(new InputEvent(InputEventType.STRAIGHTEN_WHEEL));
        } else if (keyCode == KeyCode.getKeyCode(prop.getProperty("keybind_turn_right_primary")) || keyCode == KeyCode.getKeyCode(prop.getProperty("keybind_turn_right_secondary"))) {
            ControlCenter.getControlCenter().handleUserInput(new InputEvent(InputEventType.STRAIGHTEN_WHEEL));
        } else if (keyCode == KeyCode.getKeyCode(prop.getProperty("keybind_slow_down_primary")) || keyCode == KeyCode.getKeyCode(prop.getProperty("keybind_slow_down_secondary"))) {
            ControlCenter.getControlCenter().handleUserInput(new InputEvent(InputEventType.NO_BRAKE));
        }
    }

    /**
     * For every event we will have the first part of the method dealing with handling UI changes the the view knows how to make
     * (if any exist, ex. turning the steering wheel when turning the car)
     * Then the second part will consist of informing the control center of the event, in which any UI changes that need to happen
     * after that, the View will be informed of later and render on the next tick
     */

    private void handleAccelerate() {
        ControlCenter.getControlCenter().handleUserInput(new InputEvent(InputEventType.SPEED_UP));
    }

    private void handleTurnCarRightEvent() {
        //steeringWheelImage.setRotate(steeringWheelImage.getRotate() + 5);
        ControlCenter.getControlCenter().handleUserInput(new InputEvent(InputEventType.TURN_RIGHT));
    }

    private void handleTurnCarLeftEvent() {
        //steeringWheelImage.setRotate(steeringWheelImage.getRotate() - 5);
        ControlCenter.getControlCenter().handleUserInput(new InputEvent(InputEventType.TURN_LEFT));
    }

    private void handleSlowDown() {
        ControlCenter.getControlCenter().handleUserInput(new InputEvent(InputEventType.SLOW_DOWN));
    }

    private void handleShiftGearDown() {
        ControlCenter.getControlCenter().handleUserInput(new InputEvent(InputEventType.SHIFT_GEAR_DOWN));
    }

    private void handleShiftGearUp() {
        ControlCenter.getControlCenter().handleUserInput(new InputEvent(InputEventType.SHIFT_GEAR_UP));
    }

    public void handleEngineOn() {
        ControlCenter.getControlCenter().handleUserInput(new InputEvent(InputEventType.ENGINE_OFF));
    }

    public void handleEngineOff() {
        ControlCenter.getControlCenter().handleUserInput(new InputEvent(InputEventType.ENGINE_OFF));
    }
}
