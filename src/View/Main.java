
package View;

import Exceptions.*;
import Model.*;
import Facade.Facade;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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
    
    private static Facade facade;
    private static Scene main;
    private static Scene signUp;
    private static Scene userScene;
    private static Scene adminScene;
    private static Scene addCityScene;
    private static Scene addRoadScene;
    private static Scene addPlaceToEatScene;

    /**
     * Main method.
     * @param args 
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, UnsupportedEncodingException, DuplicatedDataException{
        facade = new Facade();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        drawSignUpScene(primaryStage);
        drawUserScene(primaryStage);
        drawAdminScene(primaryStage);
        drawAddCityScene(primaryStage);
        
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
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
                alert("There are Empty Fields.");
            }
            else{
                try{
                    User user = facade.doLogin(cpfGetter.getText(), pwGetter.getText());
                    if(user == null){
                        alert("Password incorrect.");
                        pwGetter.setText("");
                    }
                    else if(user.getType() == UserType.USER){
                        cpfGetter.setText("");
                        pwGetter.setText("");
                        primaryStage.setScene(userScene);
                    }
                    else{
                        cpfGetter.setText("");
                        pwGetter.setText("");
                        primaryStage.setScene(adminScene);
                    }
                } catch (NotFoundException ex) {
                    alert("There's no User registered with this CPF.");
                    cpfGetter.setText("");
                    pwGetter.setText("");
                } catch (NoSuchAlgorithmException ex){
                } catch (UnsupportedEncodingException ex){
                }
                
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
                alert("There are Empty Fields.");
            }
            else if(!pwGetter.getText().equals(CpwGetter.getText())){
                alert("Confirmation Password doesn't Match.");
                pwGetter.setText("");
                CpwGetter.setText("");
            }
            else{
                try{
                    facade.newUser(nameGetter.getText(), pwGetter.getText(), cpfGetter.getText(), emailGetter.getText());
                    alertInfo("Sucessfully Registered");
                    nameGetter.setText("");
                    cpfGetter.setText("");
                    emailGetter.setText("");
                    pwGetter.setText("");
                    CpwGetter.setText("");
                    primaryStage.setScene(main);
                }
                catch(DuplicatedDataException dde){
                    alert("There's already an User with this CPF.");
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
        cancel.setTextFill(Color.RED);
        cancel.setOnAction((ActionEvent event)->{
            nameGetter.setText("");
            cpfGetter.setText("");
            emailGetter.setText("");
            pwGetter.setText("");
            CpwGetter.setText("");
            primaryStage.setScene(main);
        });
        bpButton.setRight(cancel);
        grid.add(bpButton, 0, 12, 2, 1);
        
        BorderPane footerPane = new BorderPane();
        Label footer = new Label("® Developed by Jaevillen and Almir");
        footer.setFont(Font.font("Comic Sans MS",  12));
        footerPane.setCenter(footer);
        grid.add(footerPane, 0, 32, 2, 1);
    }
    
    private void drawUserScene(Stage primaryStage){
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(60, 60, 60, 60));
        
        userScene = new Scene(grid, 720, 650);
    }
    
    private void drawAdminScene(Stage primaryStage){
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(60, 60, 60, 60));
        
        adminScene = new Scene(grid, 720, 650);
        
        Label title = new Label("Menu Admin");
        title.setFont(Font.font("Comic Sans MS",  50));
        title.setTextFill(Color.MEDIUMTURQUOISE);
        grid.add(title, 0, 0, 2, 1);
        
        BorderPane bp = new BorderPane();
        grid.add(bp, 0, 1, 2, 1);
        
        FlowPane fp = new FlowPane(Orientation.VERTICAL);
        fp.setVgap(10);
        fp.setAlignment(Pos.CENTER);
        fp.setColumnHalignment(HPos.CENTER);
        bp.setTop(fp);
        
        Button addCity = new Button("AddCity");
        addCity.setMinWidth(100);
        addCity.setOnAction((ActionEvent event)->{
            primaryStage.setScene(addCityScene);
        });
        
        
        Button addRoad = new Button("AddRoad");
        addRoad.setMinWidth(100);
        addRoad.setOnAction((ActionEvent event)->{
            drawAddRoadScene(primaryStage);
            primaryStage.setScene(addRoadScene);
        });
        
        Button addPlaceToEat = new Button("Add Place to Eat");
        addPlaceToEat.setMinWidth(100);
        addPlaceToEat.setOnAction((ActionEvent event)->{
            primaryStage.setScene(addPlaceToEatScene);
        });
        
        fp.getChildren().add(addCity);
        fp.getChildren().add(addRoad);
        fp.getChildren().add(addPlaceToEat);   
    }
    
    private void drawAddCityScene(Stage primaryStage){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(60, 60, 60, 60));
        
        addCityScene = new Scene(grid, 720, 650);
        
        Label title = new Label("Adding City");
        title.setFont(Font.font("Comic Sans MS",  50));
        title.setTextFill(Color.MEDIUMTURQUOISE);
        grid.add(title, 0, 0, 2, 1);
        
        Text cityName = new Text("City Name: ");
        grid.add(cityName, 0 ,2);
        
        TextField cityNameGetter = new TextField();
        grid.add(cityNameGetter, 1, 2);
        
        Text cityCode = new Text("City Code: ");
        grid.add(cityCode, 0, 4);
        
        TextField cityCodeGetter = new TextField();
        grid.add(cityCodeGetter, 1, 4);
        
        Text longitude = new Text("Longitude: ");
        grid.add(longitude, 0, 6);
        
        TextField longitudeGetter = new TextField();
        grid.add(longitudeGetter, 1, 6);
        
        Text latitude = new Text("Latitude: ");
        grid.add(latitude, 0, 8);
        
        TextField latitudeGetter = new TextField();
        grid.add(latitudeGetter, 1, 8);
        
        Text population = new Text("Population: ");
        grid.add(population, 0, 10);
        
        TextField popGetter = new TextField();
        grid.add(popGetter, 1, 10);
        
        BorderPane btnPane = new BorderPane();
        grid.add(btnPane, 0, 12, 2, 1);
        
        Button add = new Button("Add");
        add.setOnAction((ActionEvent event)->{
            boolean isOk = true;
            if(cityNameGetter.getText().isEmpty() || cityCodeGetter.getText().isEmpty() || latitudeGetter.getText().isEmpty() || longitudeGetter.getText().isEmpty()){
                alert("There are Empty Fields.");
            }
            else{
                int code = 0;
                try{
                    code = Integer.parseInt(cityCodeGetter.getText());
                }
                catch(NumberFormatException nfx){
                    alert("Type a Number in \"City Code\" Field.");
                    cityCodeGetter.setText("");
                    isOk = false;
                }
                
                double latitudeDouble = 0 ;
                try{
                    latitudeDouble = Double.parseDouble(latitudeGetter.getText());
                }
                catch(NumberFormatException nfx){
                    alert("Type a Number in \"Latitude\" Field like this: X.XXX");
                    latitudeGetter.setText("");
                    isOk = false;
                }
                
                double longitudeDouble = 0;
                try{
                    longitudeDouble = Double.parseDouble(longitudeGetter.getText());
                }
                catch(NumberFormatException nfx){
                    alert("Type a Number in \"Longitude\" Field like this: X.XXX");
                    longitudeGetter.setText("");
                    isOk = false;
                }
                
                double popDouble = 0;
                try{
                    popDouble = Double.parseDouble(popGetter.getText());
                }
                catch(NumberFormatException nfx){
                    alert("Type a Number in \"Population\" Field like this: X.XXX");
                    popGetter.setText("");
                    isOk = false;
                }
                if(isOk == true){
                    try{
                        facade.addCity(cityNameGetter.getText(), latitudeDouble, longitudeDouble, code, popDouble);
                        alertInfo("Successfully Registered!");
                        cityNameGetter.setText("");
                        cityCodeGetter.setText("");
                        latitudeGetter.setText("");
                        longitudeGetter.setText("");
                        popGetter.setText("");
                        primaryStage.setScene(adminScene);
                    } catch (DuplicateEntryException ex) {
                        alert("There's already a City with this Code.");
                        cityNameGetter.setText("");
                        cityCodeGetter.setText("");
                        latitudeGetter.setText("");
                        longitudeGetter.setText("");
                        popGetter.setText("");
                    }
                }
            } 
        });
        btnPane.setLeft(add);
        
        Button cancel = new Button("Cancel");
        cancel.setTextFill(Color.RED);
        cancel.setOnAction((ActionEvent event)->{
            cityNameGetter.setText("");
            cityCodeGetter.setText("");
            popGetter.setText("");
            longitudeGetter.setText("");
            latitudeGetter.setText("");
            primaryStage.setScene(adminScene);
        });
        btnPane.setRight(cancel);
    }
    
    private void drawAddRoadScene(Stage primaryStage){
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(100, 100, 100, 100));
        
        addRoadScene = new Scene(grid, 720, 650);
        
        Label title = new Label("Adding Road");
        title.setFont(Font.font("Comic Sans MS",  50));
        title.setTextFill(Color.MEDIUMTURQUOISE);
        grid.add(title, 0, 0, 2, 1);
        
        List<City> cities = facade.getCities();
        
        ComboBox firstCbox = new ComboBox();
        ComboBox secondCbox = new ComboBox();
        for(City city : cities){
            firstCbox.getItems().add(city.getCode() + "-" + city.getName());
        }
        
        for(City city : cities){
            secondCbox.getItems().add(city.getCode() + "-" + city.getName());
        }

        Text roadSource = new Text("CityCode that Starts: ");
        grid.add(roadSource, 0, 2);
        
        grid.add(firstCbox, 1, 2);
        
        Text roadDestiny = new Text("CityCode  that Ends: ");
        grid.add(roadDestiny, 0, 4);    
        
        grid.add(secondCbox, 1, 4);
        
        Text roadSize = new Text("Length in KM: ");
        grid.add(roadSize, 0, 6);
        
        TextField roadSizeGetter = new TextField();
        grid.add(roadSizeGetter, 1, 6);
        
        BorderPane btnBP= new BorderPane();
        grid.add(btnBP, 0, 8, 2, 1);
        
        Button add = new Button("Add");
        add.setOnAction((ActionEvent event)->{
            boolean isOk = true;
            
            String fullCityA = (String)firstCbox.getSelectionModel().getSelectedItem();
            String newCityNameA = "";
            for(int i = 0; fullCityA.charAt(i) != '-'; i++){
                newCityNameA += fullCityA.charAt(i);
            }
            
            String fullCityB = (String)secondCbox.getSelectionModel().getSelectedItem();
            String newCityNameB = "";
            for(int i = 0; fullCityB.charAt(i) != '-'; i++){
                newCityNameB += fullCityB.charAt(i);
            }
            double sizeDouble = 0;
            try{
                sizeDouble = Double.parseDouble(roadSizeGetter.getText());
            }
            catch(NumberFormatException nfx){
                isOk = false;
                alert("Type a Number in \"Road Lenght\" like this: X.XXX");
                roadSizeGetter.setText("");
            }
            
            int cityCodeA = 0;
            try{
                cityCodeA = Integer.parseInt(newCityNameA);
            }
            catch(NumberFormatException nfx){
            }
            
            int cityCodeB = 0;
            try{
                cityCodeB = Integer.parseInt(newCityNameB);
            }
            catch(NumberFormatException nfx){
            }
            if(isOk == true){
                
                City cityA = null;
                try {
                    cityA = facade.searchCity(cityCodeA);
                } catch (InexistentEntryException ex) {
                }
                City cityB = null;
                try {
                    cityB = facade.searchCity(cityCodeB);
                } catch (InexistentEntryException ex) {
                }
                
                try{
                    facade.addRoad(cityA, cityB, sizeDouble);
                } catch (DuplicateEntryException ex) {
                    alert("There's already a Road between these Cities.");
                } catch (AlreadyHasAdjacency ex) {
                   alert("There's already a Road between these Cities. 2");
                } catch (LoopIsNotAllowedException ex) {
                   alert("You can't add a Road on The Same City.");
                } catch (InexistentVertexException ex) {
                    //Won't enter here.
                } catch (InexistentEntryException ex) {
                    //Won't enter here.
                }
            }
                        
        });
        Button cancel = new Button("Cancel");
        cancel.setOnAction((ActionEvent event)->{
            primaryStage.setScene(adminScene);
            roadSizeGetter.setText("");
        });
        cancel.setTextFill(Color.RED);
        
        btnBP.setLeft(add);
        btnBP.setRight(cancel); 
    }
    
    private void alert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error!");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void alertInfo(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Info!");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
