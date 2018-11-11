package view.userInterface;

import controller.ControlCenter;

import controller.InputEvent;
import controller.InputEventType;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.SimulationModel;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.ResourceBundle;


public class ViewController {

    // stuff for properties file so user can have user define keybinds for keyboard commands
    private static final String PROPERTIES_FILE_NAME = "config.properties";
    private Properties prop = new Properties();
    private InputStream input = null;

    // TODO probably change this
    private static final boolean ENGINE_STATE_ON = true;

    @FXML
    private ImageView brake;
    private Image brakeDownImage;
    private Image brakeImage;
    
    @FXML
    private ImageView gas;
    private Image gasDownImage;
    private Image gasImage;
    
    @FXML
    private ImageView steeringWheelImage;

    @FXML
    private BorderPane mainViewBorderPane;

    
    /* Primary Car Tab Info ~ IN ORDER */ 
    @FXML 
    private ProgressBar progressBarGasLevel; 
    
    @FXML 
    private Label lblGasLevel; 
    
    @FXML 
    private Label lblCurrentSpeed; 
    
    @FXML 
    private Label lblRPM; 
    
    @FXML 
    private Label lblCurrentGear; 
    
    @FXML 
    private Label lblEngineTemp;
    
    @FXML 
    private Label lblLongLat; 
  
    @FXML 
    private Label lblDirectionDegrees; 
    
    
    
    /* Secondary Car Tab Info  ~ IN ORDER */
    
    @FXML 
    private Label lblFrontWheelDeviation; 
    
    @FXML 
    private Label lblAltitude; 
    
    @FXML 
    private Label lblIntegrity; 
    

    

    static DoubleProperty gasLevelUpdater = new SimpleDoubleProperty(.0);
    DecimalFormat df = new DecimalFormat();

    
    /* Updating Primary Car Tab Info ~IN ORDER */ 
    public void updateGasLevel(double newGasLevel) { 
    	this.gasLevelUpdater.set(newGasLevel/100);
        this.lblGasLevel.setText(df.format(newGasLevel) + "%");
    }
    
    public void updateCurrentSpeed(double currentSpeed) {
    	this.lblCurrentSpeed.setText("Speed: " + df.format(currentSpeed) + " m/s");
    }
    
    public void updateRPM(double rpm) {
    	this.lblRPM.setText("RPM: " + df.format(rpm));
    }
    
    public void updateCurrentGear(int currentGear) {
    	this.lblCurrentGear.setText("CurrentGear: " + df.format(currentGear));
    }
    
    public void updateEngineTemp(double engineTemp) {
    	this.lblEngineTemp.setText("Engine Temp (c): " + df.format(engineTemp));
    }
    
    public void updateLongLat(double latitude, double longitude) {
    	this.lblLongLat.setText("latitude: " + df.format(latitude) + ", longitude: " + df.format(longitude));
    }
    
    
    public void updateDirectionDegrees(double directionDegrees) {
    	this.lblDirectionDegrees.setText("Direction in Degrees: " + df.format(directionDegrees));
    }
    
    /* Updating Primary Car Tab Info ~IN ORDER */ 

    public void updateFrontWheelDeviation(double frontWheelDeviation) {
    	this.lblFrontWheelDeviation.setText("Front Wheel Deviation: " + df.format(frontWheelDeviation));
    }
    
    public void updateAltitude(double altitude) {
    	this.lblAltitude.setText("Altitude: " + df.format(altitude));
    }
   
    public void updateIntegrity(double integrity) {
    	this.lblIntegrity.setText("Car Integrity %: " + df.format(integrity));
    }
   
    
    @FXML
    private AnchorPane mainSimulationViewPane;

    @FXML
    private ResizableMainViewCanvas mainViewCanvas;

    @FXML
    public void initialize() {
        setupPropertiesFile();
        mainViewBorderPane.setOnKeyPressed(key -> handleKeyboardInputKeyDown(key.getCode()));
        mainViewBorderPane.setOnKeyReleased(key -> handleKeyboardInputKeyUp(key.getCode()));
        mainViewBorderPane.setOnMouseEntered(m -> mainViewBorderPane.requestFocus());
        
        String brakePath = brake.getImage().impl_getUrl();
        String resPath = brakePath.substring(0, brakePath.lastIndexOf('/')) + "/";
        
        brakeDownImage = new Image(resPath + "brakedown.png");
        brakeImage = new Image(resPath + "brake.png");
        System.out.println(brakeDownImage.impl_getUrl());
        System.out.println(brakeImage.impl_getUrl());


        gasDownImage = new Image(resPath + "gasdown.png");
        gasImage = new Image(resPath + "gas.png");


        mainViewCanvas.widthProperty().bind(mainSimulationViewPane.widthProperty());
        mainViewCanvas.heightProperty().bind(mainSimulationViewPane.heightProperty());

        //set reusable decimal formatter to only print 1 decimal 
    	df.setMaximumFractionDigits(1);
        this.progressBarGasLevel.progressProperty().bind(gasLevelUpdater);


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
    	   if (ENGINE_STATE_ON) {
               handleEngineOff();
           } else {
               handleEngineOn();
           }
    }
    
  

    public void updateView() {
        // Update view components
        System.out.println("Updating the view components");
        SimulationModel model = ControlCenter.getControlCenter().getModel();

        // Draw everything on the canvas
        mainViewCanvas.render(model);

        // Update steering wheel position
        steeringWheelImage.setRotate(model.carState.getFrontWheelDeviation());
        

        
        // Update gas / brake pedal pictures
        if (model.carState.isBraking()) {
        	brake.setImage(brakeDownImage);
        } else {
        	brake.setImage(brakeImage);
        }
        
        if (model.carState.isAccelerating()) {
        	gas.setImage(gasDownImage);
        } else {
        	gas.setImage(gasImage);
        }
        

        
        //Updating Primary Car Info ~IN ORDER 
        this.updateGasLevel(model.carState.getGasLevel());
        this.updateCurrentSpeed(model.carState.location.getSpeedms());
        this.updateRPM(model.carState.engine.getRpm());
        this.updateCurrentGear(model.carState.transmission.getCurrentGear());
        this.updateEngineTemp(model.carState.engine.getTemperatureC());
        this.updateLongLat(model.carState.location.getLatitude(), model.carState.location.getLongitude());
        this.updateDirectionDegrees(model.carState.location.getDirectionDegrees());
        
        //Updating Secondary Car Info 
        this.updateFrontWheelDeviation(model.carState.getFrontWheelDeviation());
        this.updateAltitude(model.carState.location.getAltitudeM());
        this.updateIntegrity(model.carState.getStructuralIntegrity());
        
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

    private void handleAccelerate() {
        ControlCenter.getControlCenter().handleUserInput(new InputEvent(InputEventType.SPEED_UP));
    }

    private void handleTurnCarRightEvent() {
        ControlCenter.getControlCenter().handleUserInput(new InputEvent(InputEventType.TURN_RIGHT));
    }

    private void handleTurnCarLeftEvent() {
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
