package view.userInterface;

import controller.ControlCenter;
import controller.InputEvent;
import controller.InputEventType;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class TopDownCarView extends Application {

    public static final CountDownLatch latch = new CountDownLatch(1);
    public static TopDownCarView topDownCarView = null;
    
    private CyclicBarrier drawSignaler;

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
    
    public CyclicBarrier getDrawSignaler() {
    	return drawSignaler;
    }

    public TopDownCarView() {
    	drawSignaler = new CyclicBarrier(2);
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
        
        new Thread() {
        	public void run() {
        		while(true) {
        			try {
						drawSignaler.await();
					} catch (InterruptedException | BrokenBarrierException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        			
        			// Update the UI on it's own terms
        			Platform.runLater(new Runnable() {
						@Override
						public void run() {
							viewController.updateView();						}
					});
        		}
        	}
        }.start();
    }

    @Override
    public void stop() {
        ControlCenter.getControlCenter().handleUserInput(new InputEvent(InputEventType.QUIT_PROGRAM));
    }

}
