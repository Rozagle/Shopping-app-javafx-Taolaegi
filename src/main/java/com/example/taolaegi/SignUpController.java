package com.example.taolaegi;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;


public class SignUpController implements Initializable {

    @FXML
    private Button btnBackLogin;

    @FXML
    private Button btnRegister;

    @FXML
    private TextField field_Email;

    @FXML
    private TextField field_UserName;

    @FXML
    private TextField field_password;

    private Stage stage;

    @FXML
    void switchScenesilogin(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        /*Stage Loginstage = new Stage();*/
        stage.setScene(new Scene(root,600,400));
        stage.setTitle("Tao-lae-gi Shop");
        stage.setResizable(false);
        stage.show();

    }





    public boolean isEmailValid(String email) {
        String Email = field_Email.getText();
        Pattern EMAIL_REGEX = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+", Pattern.CASE_INSENSITIVE);
        Matcher m = EMAIL_REGEX.matcher(field_Email.getText());
        if(m.find() && m.group().equals(field_Email.getText())){
          return true;
        } else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");

            alert.setContentText("Please be sure enter valid email");
            Optional<ButtonType> result = alert.showAndWait();

            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnRegister.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {


                String Username = field_UserName.getText().trim();
                String Password = field_password.getText().trim();
                String Email = field_Email.getText().trim();

                 //isEmailValid(Email);


                if(Username.isEmpty() || Password.isEmpty() || Email.isEmpty()  ) {


                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("WARNING");

                    alert.setContentText("Please be sure fill all information");
                    Optional<ButtonType> result = alert.showAndWait();



                }else if( !isEmailValid(Email)){
                    System.out.println("oldu");
                }else{


                    try {

                        FileOutputStream fileOutputStream = new FileOutputStream("RegisterUser.txt", true);
                        PrintWriter printerWriter = new PrintWriter(fileOutputStream);
                        printerWriter.println(Username + "," + Password);
                        //JOptionPane.showMessageDialog(null, "register done");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Registeration");
                        alert.setContentText("Register Done");
                        Optional<ButtonType> result = alert.showAndWait();
                        printerWriter.close();

                        System.out.print("ok");
                    } catch (Exception e2) {
                        JOptionPane.showMessageDialog(null, "Sorry We Can't Do Registration Progress");
                    }

                }





            }
        });


    }
}
