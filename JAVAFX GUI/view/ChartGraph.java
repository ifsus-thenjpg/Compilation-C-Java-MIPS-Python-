package view;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;



/**
 * Instantiates an JavaFX application which creates a GraphPane(BorderPane).
 * Sets up the scene with basic modification to the stage.
 * @author Foothill College, Bita M., Team 8
 */
public class ChartGraph extends Application
{

	/**
	 * Called by launch method of Application
	 * @param stage: Stage
	 */
	@Override
	public void start(Stage stage)
	{
		GraphPane bgp = new GraphPane();
		Scene scene2 = new Scene(bgp, 700, 500);

		Image icon = new Image(getClass().getResourceAsStream("Icon.png"));
		stage.getIcons().add(icon);
		stage.setTitle("Team 8 Application");
		stage.setScene(scene2);

		stage.show();
	}

	/**
	 * Launches a standalone JavaFx App
	 */
	public static void main(String[] args)
	{
		launch();
	}

}