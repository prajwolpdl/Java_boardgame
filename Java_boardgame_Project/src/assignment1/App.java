package assignment1;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application{

	ThreeMusketeers model;
	View view;
	Stage stage;

	    @Override
	    public void start(Stage stage) {
	        stage.setTitle("Three Musketeers");
	        stage.setMinHeight(900);
	        stage.setMinWidth(600);

	        this.model = new ThreeMusketeers();
	        this.view = new View(model, stage);
	    }

	    public static void main(String[] args) {
	        launch();
	    }
	}
