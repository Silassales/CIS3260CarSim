package view.userInterface;

import controller.ControlCenter;
import controller.InputEvent;
import controller.InputEventType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.CountDownLatch;

public class TopDownCarView extends Application {

    public static final CountDownLatch latch = new CountDownLatch(1);
    public static TopDownCarView topDownCarView = null;

    ViewController viewController;

    public static TopDownCarView waitForTopDownCarView() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return topDownCarView;
    }

    public static void setTopDownCarView(TopDownCarView topDownCarView0) {
        topDownCarView = topDownCarView0;
        latch.countDown();
    }

    public TopDownCarView() {
        setTopDownCarView(this);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainView.fxml"));
        Parent root = loader.load();
        viewController = loader.getController();
        primaryStage.setTitle("Car Sim");
        Scene mainScene = new Scene(root, 1000, 800);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        ControlCenter.getControlCenter().handleUserInput(new InputEvent(InputEventType.QUIT_PROGRAM));
    }

    public void updateTest() {
        viewController.testUpdate();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
