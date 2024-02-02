package com.example.taolaegi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MenuPageCollector implements Initializable {


    @FXML
    private CheckBox CheckboxChapsal;

    @FXML
    private CheckBox CheckboxFlatMagnet;

    @FXML
    private CheckBox CheckboxMiniPush;

    @FXML
    private CheckBox CheckboxTaelaegi;

    @FXML
    private ComboBox<String> comboPayment;

    @FXML
    private TextField TotalTextField;

    @FXML
    private TextField txtMenuEmailfield;

    @FXML
    private TextField txtMenuName;

    @FXML
    private TextArea txtMenuAddress;

    @FXML
    private TextField lastNamefield;

    @FXML
    private TextField txtDelivery;

    @FXML
    private TextField Numberofcardfield;

    @FXML
    private Button btnClaer;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnTotal;

    @FXML
    private Button add1;

    @FXML
    private Button add2;

    @FXML
    private Button add3;

    @FXML
    private Button add4;


    @FXML
    private Spinner<Integer> spinnerChapsal;
    final int initialvalue =0;
    SpinnerValueFactory<Integer> ValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100 , initialvalue);

    @FXML
    private Spinner<Integer> spinnerFlatMagnet;
    SpinnerValueFactory<Integer> ValueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);

    @FXML
    private Spinner<Integer> spinnerMiniPlush;
    SpinnerValueFactory<Integer> ValueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);

    @FXML
    private Spinner<Integer> spinnerTaelaegi;
    SpinnerValueFactory<Integer> ValueFactory3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);

    private Stage stage;

    private static Product product;

    private int sum = 0;

    protected List<Product> İtemsList = new ArrayList<>();

    ObservableList<Product> DataList = FXCollections.observableArrayList(İtemsList);

    @FXML
    private TableView<Product> TableView = new TableView<>(DataList);

    @FXML
    private TableColumn<Product, String> ColumName;

    @FXML
    private TableColumn<Product, Double> ColumPrice;

    @FXML
    private TableColumn<Product, Integer> ColumQty;

    @FXML
    private TableColumn<Product, Double> ColumTotal;

    @FXML
    private ScrollPane TabPaneİtem;

    @FXML
    private ImageView imageChapsal;

    @FXML
    private ImageView imageFlatMagnet;

    @FXML
    private ImageView imageMiniPlush;

    @FXML
    private ImageView imageTaeLaegi;


    @FXML
    private Label textcredikartalert;


    public void initialize(URL url, ResourceBundle resourceBundle) {

        spinnerChapsal.setValueFactory(ValueFactory);
        spinnerFlatMagnet.setValueFactory(ValueFactory1);
        spinnerMiniPlush.setValueFactory(ValueFactory2);
        spinnerTaelaegi.setValueFactory(ValueFactory3);

        ColumName.setCellValueFactory(new PropertyValueFactory<Product, String>("Name"));
        ColumPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("Price"));
        ColumQty.setCellValueFactory(new PropertyValueFactory<Product, Integer>("Qty"));
        ColumTotal.setCellValueFactory(new PropertyValueFactory<Product, Double>("Total"));
        TableView.setEditable(true);

        ObservableList<String> comolist = FXCollections.observableArrayList("paypal", "Alipay","Credit/Debit Card","Wire Transfer");
        comboPayment.setItems(comolist);
        //Numberofcardfield.textProperty().bind(comboPayment.valueProperty());



        btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               Product item= TableView.getSelectionModel().getSelectedItem();
               İtemsList.remove(item);
               TableView.getItems().remove(item);
                Totalclick();
            }
        });

        btnOrders.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Date currtendate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                String Datenow= dateFormat.format(currtendate);

                String Name = txtMenuName.getText();
                String lastName= lastNamefield.getText();
                String Email =  txtMenuEmailfield.getText();
                String Address = txtMenuAddress.getText();
                String cardnumber = Numberofcardfield.getText();
                String s = comboPayment.getSelectionModel().getSelectedItem().toString();
                System.out.println(s);
                List<Object> columnData1 = new ArrayList<>();
                Totalclick();
                double Totaltext = Double.parseDouble(TotalTextField.getText());


                if ( Name.isEmpty() || lastName.isEmpty() || Email.isEmpty() || Address.isEmpty() || Totaltext==0.0  ){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("WARNING");
                    alert.setContentText("Please be sure you fill all information");
                    Optional<ButtonType> result = alert.showAndWait();
                }else if (!isEmailValid(Email)) {
                    System.out.println("okk");
                } else if(!isCardValid(cardnumber)) {

                    System.out.println("card check");
                }else{

                    for (Product item : TableView.getItems()) {
                    columnData1.add(ColumTotal.getCellObservableValue(item).getValue());
                    columnData1.add(ColumName.getCellObservableValue(item).getValue());
                    columnData1.add(ColumPrice.getCellObservableValue(item).getValue());
                    columnData1.add(ColumQty.getCellObservableValue(item).getValue());

                    System.out.println(Totaltext);

                    try {

                            txtDelivery.setEditable(false);
                            Double DeliveryCost = Double.parseDouble(TotalTextField.getText());
                            if (DeliveryCost < 51.0) {
                                DeliveryCost = DeliveryCost + 20.0;
                                txtDelivery.setText("20.0");
                                txtDelivery.setEditable(false);
                            } else if (DeliveryCost < 101.0) {
                                DeliveryCost = DeliveryCost + 10.0;
                                txtDelivery.setText("10.0");
                                txtDelivery.setEditable(false);
                            } else {
                                DeliveryCost = DeliveryCost + 0.0;
                                txtDelivery.setText("Free Delivery :) ");
                                txtDelivery.setEditable(false);
                            }
                            FileOutputStream fileOutputStream = new FileOutputStream("Order.txt", true);
                            PrintWriter printerWriter = new PrintWriter(fileOutputStream);
                            printerWriter.println("---------------");
                            printerWriter.println(" Username  " + Name + " place Order ");
                            printerWriter.println(" Name : " + Name );
                            printerWriter.println(" Last Name : " + lastName);
                            printerWriter.println("Time : " + Datenow);
                            printerWriter.println(" Email : " + Email);
                            printerWriter.println(" Address : " + Address);
                            printerWriter.println(" Total Payment " + DeliveryCost);
                            printerWriter.println(" Payment Method : " + s);
                            printerWriter.println( " Card Number : " + cardnumber);
                            printerWriter.println(" Order List " + DataList);
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("CONFIRMATION");
                            alert.setContentText("Your Order done thnaks for shopping");
                            Optional<ButtonType> result = alert.showAndWait();
                            TableView.getItems().clear();
                            TotalTextField.setText("");
                            txtMenuName.setText("");
                            txtDelivery.setText("");
                            txtMenuAddress.setText("");
                            txtMenuEmailfield.setText("");
                            lastNamefield.setText("");
                            Numberofcardfield.setText("");
                            textcredikartalert.setText("");
                            comboPayment.setPromptText("Select payment method" );
                            clearClick();
                            resetspinner();
                            resetcheckbox();
                            printerWriter.close();
                            System.out.print("ok");
                        } catch (Exception e2) {
                            JOptionPane.showMessageDialog(null, "Sorry we can't place you order progress");
                        }
                    }

                }




            }
        });



    }
    public boolean isEmailValid(String email) {
        String Email = txtMenuEmailfield.getText();
        Pattern EMAIL_REGEX = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+", Pattern.CASE_INSENSITIVE);
        Matcher m = EMAIL_REGEX.matcher(txtMenuEmailfield.getText());
        if(m.find() && m.group().equals(txtMenuEmailfield.getText())){
            return true;
        } else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");

            alert.setContentText("Please be sure enter valid email");
            Optional<ButtonType> result = alert.showAndWait();

            return false;
        }}

    public boolean isCardValid(String text) {
        String text1 = Numberofcardfield.getText();
        Pattern EMAIL_REGEX = Pattern.compile("^[0-9]{16,16}$", Pattern.CASE_INSENSITIVE);
        Matcher m = EMAIL_REGEX.matcher(Numberofcardfield.getText());
        if(m.find() && m.group().equals(Numberofcardfield.getText())){
            return true;
        } else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");

            alert.setContentText("Please be sure enter valid cradit card ");
            Optional<ButtonType> result = alert.showAndWait();
            textcredikartalert.setText("card number can't be less then 16 numbers");
            return false;
        }}

    public void resetspinner(){
        spinnerChapsal.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100 , initialvalue));
        spinnerFlatMagnet.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100 , initialvalue));
        spinnerMiniPlush.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100 , initialvalue));
        spinnerTaelaegi.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100 , initialvalue));


    }

    public void resetcheckbox(){
        CheckboxChapsal.setSelected(false);
        CheckboxFlatMagnet.setSelected(false);
        CheckboxTaelaegi.setSelected(false);
        CheckboxMiniPush.setSelected(false);
    }




    @FXML
    void SwitchSceneChapsal(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Chapsal.fxml"));
        Parent root = loader.load();
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Tao-lae-gi Shop");
        stage.show();

    }

    @FXML
    void SwitchSceneFlatMagnet(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FlatMagnet.fxml"));
        Parent root = loader.load();
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Tao-lae-gi Shop");
        stage.show();


    }

    @FXML
    void SwitchSceneMiniPlush(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MiniPlush.fxml"));
        Parent root = loader.load();
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Tao-lae-gi Shop");
        stage.show();

    }

    @FXML
    void SwitchSceneTaeLaegi(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Taelaegi.fxml"));
        Parent root = loader.load();
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Tao-lae-gi Shop");
        stage.show();

    }


    @FXML
    void clickExist(MouseEvent event) {System.exit(0);}



    @FXML
    public void add1Click(){

        if (CheckboxChapsal.isSelected()) {
            int q = spinnerChapsal.getValue();
            if (q == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");

                alert.setContentText("Please enter quality");
                Optional<ButtonType> result = alert.showAndWait();
            } else {
                Product product1 = new Product();
                product1.setId("123");
                product1.setQty(q);
                product1.setPrice(18.0);
                product1.setItemscode("Chapsal", "123");
                product1.setName("Chapsal");
                product1.setTotal(q, 18.0);
                String pN = product1.getName();
                String pI = product1.getId();
                String Pt = product1.getItemscode("Chapsal", "123");
                double pP = product1.getPrice();
                int pQ = product1.getQty();
                double pT = product1.getTotal();
                System.out.println(pI + " " + pN + " " + pP + " " + pQ + " " + pT);

                İtemsList.add(product1);
                DataList.setAll(İtemsList);
                TableView.setItems(DataList);
                System.out.println("Using For Loop\n ");
                for (int i = 0; i < İtemsList.size(); i++) {
                    System.out.println(İtemsList.get(i));
                }

                Totalclick();
                resetspinner();
                resetcheckbox();
                System.out.println("selam");
            }
        }

    }


    @FXML
    public void add2Click() {

        if (CheckboxFlatMagnet.isSelected()) {

            int q = spinnerFlatMagnet.getValue();
            if (q == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");

                alert.setContentText("Please enter quality");
                Optional<ButtonType> result = alert.showAndWait();
            } else {
                Product product2 = new Product();
                product2.setQty(q);
                product2.setPrice(15.0);
                product2.setId("124");
                product2.setName("FlatMagnet");
                product2.setTotal(q, 15.0);
                String pN = product2.getName();
                String pI = product2.getId();
                double pP = product2.getPrice();
                int pQ = product2.getQty();
                double pT = product2.getTotal();

                System.out.println(pI + " " + pN + " " + pP + " " + pQ + " " + pT);
                İtemsList.add(product2);
                DataList.setAll(İtemsList);
                TableView.setItems(DataList);
                Totalclick();
                resetspinner();
                resetcheckbox();
                System.out.println("Using For Loop\n ");
                for (int i = 0; i < İtemsList.size(); i++) {
                    System.out.println(İtemsList.get(i));
                }
            } }
    }


    @FXML
    public void add3Click() {

        if (CheckboxTaelaegi.isSelected()) {

            int q = spinnerTaelaegi.getValue();
            if (q == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");

                alert.setContentText("Please enter quality");
                Optional<ButtonType> result = alert.showAndWait();
            } else {
            Product product2 = new Product();
            product2.setQty(q);
            product2.setPrice(30.0);
            product2.setId("125");
            product2.setName("Taelaegi");
            product2.setTotal(q, 30.0);
            String pN = product2.getName();
            String pI = product2.getId();
            double pP = product2.getPrice();
            int pQ = product2.getQty();
            double pT = product2.getTotal();

            System.out.println(pI + " " + pN + " " + pP + " " + pQ + " " + pT);
            İtemsList.add(product2);
            DataList.setAll(İtemsList);
            TableView.setItems(DataList);
            Totalclick();
            resetspinner();
            resetcheckbox();
            System.out.println("Using For Loop\n ");
            for (int i = 0; i < İtemsList.size(); i++) {
                System.out.println(İtemsList.get(i));
            }
        }}
    }


    @FXML
    void add4Click(ActionEvent event) {
        if (CheckboxMiniPush.isSelected()) {

            int q = spinnerMiniPlush.getValue();
            if (q == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");

                alert.setContentText("Please enter quality");
                Optional<ButtonType> result = alert.showAndWait();
            } else {
            Product product2 = new Product();
            product2.setQty(q);
            product2.setPrice(20.0);
            product2.setId("126");
            product2.setName("Mini Plush");
            product2.setTotal(q, 20.0);
            String pN = product2.getName();
            String pI = product2.getId();
            double pP = product2.getPrice();
            int pQ = product2.getQty();
            double pT = product2.getTotal();
            System.out.println(pI + " " + pN + " " + pP + " " + pQ + " " + pT);
            İtemsList.add(product2);
            DataList.setAll(İtemsList);
            TableView.setItems(DataList);
            Totalclick();
            resetspinner();
            resetcheckbox();
            System.out.println("Using For Loop\n ");
            for (int i = 0; i < İtemsList.size(); i++) {
                System.out.println(İtemsList.get(i));
            }
        }}

    }

    @FXML
    public void clearClick(){
        TableView.getItems().clear();
        DataList.clear();
        İtemsList.clear();
        TotalTextField.setText("");
    }



    @FXML
    public void Totalclick(){
        double sum = 0;
        List<Double> columnData = new ArrayList<>();
        for (Product item : TableView.getItems()) {
            columnData.add(ColumTotal.getCellObservableValue(item).getValue());
            System.out.println(item);
            sum = sum + ColumTotal.getCellObservableValue(item).getValue();
        }
        TotalTextField.setText(String.valueOf(sum));
    }









    }





