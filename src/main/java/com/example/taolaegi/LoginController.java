package com.example.taolaegi;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class LoginController implements Initializable {

    @FXML
    private CheckBox CheckboxLogin;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btsBackRegister;

    @FXML
    private TextField field_UserName;

    @FXML
    private PasswordField field_password;

    @FXML
    private TextField field_passwordtext;

    @FXML
    private Label informationtxt;

    private Stage stage;

    @FXML
    void ClickCheckBox(ActionEvent event) {

        if(CheckboxLogin.isSelected())
        {
            field_passwordtext.setText(field_password.getText());
            field_passwordtext.setVisible(true);
            field_password.setVisible(false);
        }else {
            field_password.setText(field_passwordtext.getText());
            field_password.setVisible(true);
            field_passwordtext.setVisible(false);
        }
    }



    @FXML
    void handle(ActionEvent event) throws Exception {
        String username = field_UserName.getText().trim();
        String password = field_password.getText().trim();

        boolean İslogin = false;
        if(username.isEmpty() || password.isEmpty() ){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");

            alert.setContentText("Please Enter your Username and Password");
            Optional<ButtonType> result = alert.showAndWait();

        }else {
            try {

                File files = new File("RegisterUser.txt");
                Scanner scanner = new Scanner(files);
                scanner.useDelimiter("[,\n]");
                while (scanner.hasNextLine()) {

                    String usernamer = scanner.next().trim();
                    System.out.println(usernamer);
                    String passworder = scanner.next().trim();
                    System.out.println(passworder);


                    if (username.equals(usernamer) && password.equals(passworder)) {

                        switchSceneManu(event);
                        System.out.println("enter");
                        İslogin = true;
                    }else {

                        informationtxt.setText("Your ID or Password is incorrect");

                    }

                }

            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }


    }

    @FXML
    void switchScenesiRegister(ActionEvent event) throws IOException {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
            Parent root = loader.load();
            stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Tao-lae-gi Shop");
            stage.show();

    }


    public void switchSceneManu(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPage.fxml"));
        Parent root = loader.load();
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Tao-lae-gi Shop");
        stage.show();
        }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
