
package View;
import Controller.Controller;
import Exceptions.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author AlmirNeto
 */
public class Main extends Application{
    
    private static Controller controller = new Controller();

    /**
     * Main method.
     * @param args 
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, UnsupportedEncodingException{
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        BorderPane bp = new BorderPane();
        
        Scene scene = new Scene(bp, 720, 650);
        
        primaryStage.setTitle("RoadTrips");
        primaryStage.setHeight(650);
        primaryStage.setWidth(720);
       
        primaryStage.setResizable(false);
        Image image = new Image("View\\icon.jpg");
        primaryStage.getIcons().add(image);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
