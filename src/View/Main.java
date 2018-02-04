
package View;
import Controller.Controller;
import Exceptions.*;
import Facade.Facade;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author AlmirNeto
 */
public class Main extends Application{
    
    private static Facade facade = new Facade();
    private static Scene main;
    private static Scene signUp;

    /**
     * Main method.
     * @param args 
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, UnsupportedEncodingException{
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        drawSignUpScene(primaryStage);
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setVgap(10);
        grid.setPadding(new Insets(140, 140, 140, 140));
        
        main = new Scene(grid, 720, 650);
        
        Label title = new Label("RoadTrips");
        title.setFont(Font.font("Comic Sans MS",  50));
        title.setTextFill(Color.MEDIUMTURQUOISE);
        grid.add(title, 0, 0, 2, 1);
        
        Text cpf = new Text("CPF: ");
        grid.add(cpf, 0, 2);
        
        TextField cpfGetter = new TextField();
        grid.add(cpfGetter, 1, 2);
        
        Text password = new Text("Password: ");
        grid.add(password, 0, 4);
        
        PasswordField pwGetter = new PasswordField();
        grid.add(pwGetter, 1, 4);
        
        BorderPane bp = new BorderPane();
        grid.add(bp, 0, 6, 2, 1);
        Button signIn = new Button("SignIn");
        signIn.setOnAction((ActionEvent event)->{
            if(cpfGetter.getText().isEmpty() || pwGetter.getText().isEmpty()){
                alert();
            }
        });
        bp.setCenter(signIn);
        
        Text register = new Text("Don't have an Account? CREATE ONE.");
        register.setOnMouseEntered((MouseEvent event)->{
            register.setCursor(Cursor.HAND);
            register.setFill(Color.BLUE);
            register.setUnderline(true);
        });
        register.setOnMouseExited((MouseEvent event)->{
            register.setFill(Color.BLACK);
            register.setUnderline(false);
        });
        register.setOnMouseClicked((MouseEvent event)->{
            register.setCursor(Cursor.DEFAULT);
            cpfGetter.setText("");
            pwGetter.setText("");
            primaryStage.setScene(signUp);
        });
        BorderPane registerBP = new BorderPane();
        grid.add(registerBP, 0, 8, 2, 1);
        registerBP.setCenter(register);
        
        BorderPane footerPane = new BorderPane();
        Label footer = new Label("® Developed by Jaevillen and Almir");
        footer.setFont(Font.font("Comic Sans MS",  12));
        footerPane.setCenter(footer);
        grid.add(footerPane, 0, 30, 2, 1);
        
        primaryStage.setTitle("RoadTrips");
        primaryStage.setHeight(650);
        primaryStage.setWidth(720);
       
        primaryStage.setResizable(false);
        Image image = new Image("View\\icon.jpg");
        primaryStage.getIcons().add(image);
        
        primaryStage.setScene(main);
        primaryStage.show();
    }
    
    private void drawSignUpScene(Stage primaryStage){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setVgap(10);
        grid.setPadding(new Insets(60, 60, 60, 60));
        
        signUp = new Scene(grid, 720, 650);
        
        Label title = new Label("Register");
        title.setFont(Font.font("Comic Sans MS",  50));
        title.setTextFill(Color.MEDIUMTURQUOISE);
        grid.add(title, 0, 0, 2, 1);
        
        Text name = new Text("Name: ");
        grid.add(name, 0, 2);
        
        TextField nameGetter = new TextField();
        grid.add(nameGetter, 1, 2);
        
        Text cpf = new Text("CPF: ");
        grid.add(cpf, 0, 4);
        
        TextField cpfGetter = new TextField();
        grid.add(cpfGetter, 1, 4);
        
        Text email = new Text("E-Mail: ");
        grid.add(email, 0, 6);
        
        TextField emailGetter = new TextField();
        grid.add(emailGetter, 1, 6);
        
        Text password = new Text("Password: ");
        grid.add(password, 0, 8);
        
        PasswordField pwGetter = new PasswordField();
        grid.add(pwGetter, 1, 8);
        
        Text Cpassword = new Text("Confirm Password: ");
        grid.add(Cpassword, 0, 10);
        
        PasswordField CpwGetter = new PasswordField();
        grid.add(CpwGetter, 1, 10);
        
        BorderPane bpButton = new BorderPane();
        
        Button register = new Button("Register");
        register.setOnAction((ActionEvent event)->{
            if(nameGetter.getText().isEmpty() || cpfGetter.getText().isEmpty() || pwGetter.getText().isEmpty() || CpwGetter.getText().isEmpty()){
                alert();
            }
            else if(!pwGetter.getText().equals(CpwGetter.getText())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Confirmation Password doesn't Match.");
                alert.setHeaderText(null);
                alert.setTitle("ERROR!");
                alert.showAndWait();
            }
            else{
                try{
                    facade.newUser(nameGetter.getText(), pwGetter.getText(), cpfGetter.getText(), emailGetter.getText());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Sucess!");
                    alert.setContentText("Sucessfully Registered");
                    alert.showAndWait();
                    nameGetter.setText("");
                    cpfGetter.setText("");
                    emailGetter.setText("");
                    pwGetter.setText("");
                    CpwGetter.setText("");
                    primaryStage.setScene(main);
                }
                catch(DuplicatedDataException dde){
                    Alert alert  = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Error");
                    alert.setContentText("There's already an User with this CPF.");
                    alert.showAndWait();
                    nameGetter.setText("");
                    cpfGetter.setText("");
                    emailGetter.setText("");
                    pwGetter.setText("");
                    CpwGetter.setText("");
                } catch (NoSuchAlgorithmException ex) { 
                } catch (UnsupportedEncodingException ex) {   
                }
            }
        });
        bpButton.setLeft(register);
        
        Button cancel = new Button("Cancel");
        cancel.setOnAction((ActionEvent event)->{
            nameGetter.setText("");
            cpfGetter.setText("");
            emailGetter.setText("");
            pwGetter.setText("");
            CpwGetter.setText("");
            primaryStage.setScene(main);
        });
        cancel.setTextFill(Color.RED);
        bpButton.setRight(cancel);
        
        grid.add(bpButton, 0, 12, 2, 1);
        
        BorderPane footerPane = new BorderPane();
        Label footer = new Label("® Developed by Jaevillen and Almir");
        footer.setFont(Font.font("Comic Sans MS",  12));
        footerPane.setCenter(footer);
        grid.add(footerPane, 0, 32, 2, 1);
    }
    
    private void alert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error!");
        alert.setContentText("There are Empty Fields.");
        alert.show();
    }

}
