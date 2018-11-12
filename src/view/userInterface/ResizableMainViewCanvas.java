package view.userInterface;

import controller.ControlCenter;
import controller.InputEvent;
import controller.InputEventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import model.SimulationModel;

public class ResizableMainViewCanvas extends Canvas {

    private ControlCenter controlCenter = ControlCenter.getControlCenter();

    public ResizableMainViewCanvas() {
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }


    private void draw() {
        /* TODO find out how to inject the model directly in the constructor so that this also re-renders the screen
            currently this just does nothing, and the screen will get re-rendered on the next tick
         */
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }

    /**
     * Methods for drawing to the canvas
     */

    public void render(SimulationModel model) {
        double width = getWidth();
        double height = getHeight();

        GraphicsContext gc = getGraphicsContext2D();

        /* first we want to wipe the screen */
        gc.clearRect(0, 0, width, height);

        /* now start drawing the fun stuff !!
            We need to make sure that we draw everything in the correct order (back to front) so we dont draw over anything
            In this case we will draw in this order:
            1. Ground
            2. Roads
            3. Any sort of plants/lightpoles/etc
            4. Car
            5. Buildings
         */

        drawGround(gc, model);
        drawRoads(gc, model);
        drawItems(gc, model);
        drawCar(gc, model);
        drawBuildings(gc, model);
    }

    private void drawGround(GraphicsContext gc, SimulationModel model) {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawRoads(GraphicsContext gc, SimulationModel model) {
        // TODO
    }

    private void drawItems(GraphicsContext gc, SimulationModel model) {
        // TODO
    }

    private void rotate(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    private void drawRotatedImage(GraphicsContext gc, Image image, double angle, double tlpx, double tlpy) {
        gc.save();
        rotate(gc, angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2);
        gc.drawImage(image, tlpx, tlpy);
        gc.restore();
    }

    private void drawCar(GraphicsContext gc, SimulationModel model) {
        final double carX = getXCoordinate(model.carState.location.getLatitude());
        final double carY = getYCoordinate(model.carState.location.getLongitude());


        System.out.println("Drawing the car at postion:  [X=" + carX + ", Y=" + carY + "]");

        Image carImage;
        if(model.carState.isBraking()) {
            if (model.carState.getFrontWheelDeviation() == -45.0) {
                carImage = new Image(getClass().getResourceAsStream("/res/audir8leftbraking.png"));
            } else if (model.carState.getFrontWheelDeviation() == 0.0) {
                carImage = new Image(getClass().getResourceAsStream("/res/audir8normalbraking.png"));
            } else {
                carImage = new Image(getClass().getResourceAsStream("/res/audir8rightbraking.png"));
            }
        } else {
            if (model.carState.getFrontWheelDeviation() == -45.0) {
                carImage = new Image(getClass().getResourceAsStream("/res/audir8left.png"));
            } else if (model.carState.getFrontWheelDeviation() == 0.0) {
                carImage = new Image(getClass().getResourceAsStream("/res/audir8normal.png"));
            } else {
                carImage = new Image(getClass().getResourceAsStream("/res/audir8right.png"));
            }
        }

        drawRotatedImage(gc, carImage, model.carState.location.getDirectionDegrees()-270, carX, carY);
    }

    private void drawBuildings(GraphicsContext gc, SimulationModel model) {
        // TODO
    }


    /* we want the middle to the screen to represent 0,0 so all we need to do is add width/2 and height/2 to the lat and long */
    private int getXCoordinate(double latitude) {
        return checkXOutOfBounds((int) (latitude + (getWidth() / 2)));
    }

    private int getYCoordinate(double longitude) {
        return checkYOutOfBounds((int) (longitude + (getHeight() / 2)));
    }

    private int checkXOutOfBounds(int x) {
        if (x < 0 || x > getWidth()) {
            controlCenter.handleUserInput(new InputEvent(InputEventType.OUT_OF_BOUNDS));
        }
        return x;
    }

    private int checkYOutOfBounds(int y) {
        if (y < 0 || y > getHeight()) {
            controlCenter.handleUserInput(new InputEvent(InputEventType.OUT_OF_BOUNDS));
        }
        return y;
    }
}
