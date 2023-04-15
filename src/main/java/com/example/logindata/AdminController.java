package com.example.logindata;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
public class AdminController implements Initializable {



    @FXML
    private AnchorPane line_chart_page;
    private File selectedFile;
    @FXML
    private ImageView imageView;
    @FXML
    private LineChart<String, Integer> lineChart;
    @FXML
    private Button hideBtn;
    @FXML
    private AnchorPane paneProfileCard;

    @FXML
    private Button line_chart_button;
    @FXML
    private AreaChart<String, Integer> areaChart;

    @FXML
    private Button area_chart_button;
    @FXML
    private Button changerPasswordBtn;

    @FXML
    private AnchorPane panePassword;
    @FXML
    private AnchorPane area_chart_page;

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private Button bar_chart_button;

    @FXML
    private AnchorPane bar_chart_page;

    @FXML
    private TextField address;

    @FXML
    private Button btnListChangerRoles;
    @FXML
    private TextField fullNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField cinTextField;

    @FXML
    private Button btnProfile;

    @FXML
    private Label totalSimpleUser;

    @FXML
    private Label totalIsVeri;
    @FXML
    private Button btnOverview;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Button btnMenus;

    @FXML
    private PasswordField ConfirmerPasswordTextField;
    @FXML
    private Button btnOrders;

    @FXML
    private Button btnUsers;

    @FXML
    private Label totalUsers;

    @FXML
    private Label totalUsersChangerRoles;

    @FXML
    private Button btnPackages;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;

    @FXML
    private TextField cin;

    @FXML
    private Label totalCoach;
    @FXML
    private Label totalCoach1;
    @FXML
    private TableColumn<UserData, String> cin_col;

    @FXML
    private Button clearBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private TextField email;

    @FXML
    private TableColumn<UserData, String> email_col;

    @FXML
    private TextField fullName;

    @FXML
    private TextField fullNameSelectionner;
    @FXML
    private TextField emailSelectionner;

    @FXML
    private TableColumn<UserData, String> fullName_col;

    @FXML
    private ComboBox<?> isverified;

    @FXML
    private TableColumn<UserData, String> isverified_col;

    @FXML
    private TableColumn<UserData, String> address_col;
    @FXML
    private Pane pnlProfile;
    @FXML
    private Label addressProfileLabel;
    @FXML
    private Label cinProfileLabel;
    @FXML
    private Label fullnameProfileLabel;
    @FXML
    private Label rolesProfileLabel;
    @FXML
    private Label specialiteProfileLabel;

    @FXML
    private Label emailProfileLabel;
    @FXML
    private Pane pnlMenus;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlUsers;

    @FXML
    private Button qddBtn;

    @FXML
    private ComboBox<?> roles;
    private FileChooser fileChooser;

    @FXML
    private ImageView imageViewUser;

    @FXML
    private PasswordField newpasswordtextFielduser;
    @FXML
    private TableColumn<UserData, String> roles_col;

    @FXML
    private TableView<UserData> tableView;
    @FXML
    private TableView<UserData> tableView2;
    @FXML
    private Button qddBtn1;

    @FXML
    private Button updateBtn;
    private Connection con;
    private PreparedStatement prepare;
    private ResultSet result;

    private Alert alert;
    @FXML
    private Pane pnlUsersChangerList;

    private Statement statement;
    @FXML
    private Label titleusername;

    @FXML
    private Pane pnlDataAnalysis;

    @FXML
    private Button btnDataAnalysis;


    @FXML
    private AnchorPane editpane;

    @FXML
    private TableColumn<UserData, String> fullName_col1;
    @FXML
    private TableColumn<UserData, String> email_col1;
    @FXML
    private TableColumn<UserData, String> cin_col1;
    @FXML
    private TableColumn<UserData, String> specialte_col1;
    @FXML
    private TableColumn<UserData, String> roles_col1;
    @FXML
    private TableColumn<UserData, String> cv_col1;


    public void getUserData(){
        fullNameTextField.setText(UserData.fullname);
        emailTextField.setText(UserData.email);
        addressTextField.setText(UserData.address);
        cinTextField.setText(UserData.cin);


    }
    public void clear(){

        fullName.setText("");
        address.setText("");
        cin.setText("");
        email.setText("");


    }
    public void userAddBtn(){


        database database=new database();
        database.connect();
        try {

            if (fullName.getText().isEmpty()||email.getText().isEmpty()||address.getText().isEmpty()||
                    cin.getText().isEmpty()||roles.getSelectionModel().getSelectedItem()==null||
                    isverified.getSelectionModel().getSelectedItem()==null){

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank field");
                alert.showAndWait();


            }else {
                String checkData="SELECT fullname FROM user WHERE fullname='"+fullName.getText()+"'";
                prepare =con.prepareStatement(checkData);
                result=prepare.executeQuery();

                if (result.next()) {

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("this FullName is"+fullName.getText()+" is already exist");
                    alert.showAndWait();
                }else {
                    String insertData="INSERT INTO user (fullname,email,cin,address,roles,isVerified,date)"+"VALUES(?,?,?,?,?,?,?)";

                    prepare = con.prepareStatement(insertData);
                    prepare.setString(1, fullName.getText());
                    prepare.setString(2, email.getText());
                    prepare.setString(3, cin.getText());
                    prepare.setString(4, address.getText());
                    prepare.setString(5, (String)roles.getSelectionModel().getSelectedItem());
                    prepare.setString(6,(String) isverified.getSelectionModel().getSelectedItem());

                    java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
                    prepare.setDate(7, java.sql.Date.valueOf(String.valueOf(currentDate)));

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("information message");
                    alert.setHeaderText(null);
                    alert.setContentText("successfully added ! ");
                    alert.showAndWait();


                    prepare.executeUpdate();

                    UserShowData();
                    userClearBtn();

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void userUpdateBtnn(){
        database database=new database();
        database.connect();
        try {

            if (fullName.getText().isEmpty()){

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank field");
                alert.showAndWait();


            }else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to update fullname = "+fullName.getText()+"?");
                Optional<ButtonType> option =alert.showAndWait();

                if (option.get().equals(ButtonType.OK)){

                    String updateData="UPDATE user SET "+"fullname = '"+fullName.getText()+
                            "' ,email = '"+email.getText()+
                            "' ,cin = '"+cin.getText()+
                            "' ,address = '"+address.getText()+
                            "' ,roles = '"+roles.getSelectionModel().getSelectedItem()+
                            "' ,isVerified = '"+isverified.getSelectionModel().getSelectedItem()+
                            "' WHERE fullname = '"+fullName.getText()+"'"

                            ;
                    prepare =con.prepareStatement(updateData);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("information message");
                    alert.setHeaderText(null);
                    alert.setContentText("successfully updated ! ");
                    alert.showAndWait();
                    UserShowData();
                    userClearBtn();


                }else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled !");
                    alert.showAndWait();
                }



            }




        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void updateProfileImage(ActionEvent event) {
        database database=new database();

        database.connect();

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        fileChooser.setTitle("Select Profile Image");

        selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {

            try (FileInputStream fis = new FileInputStream(selectedFile)) {


                String updateImageQuery = "UPDATE user SET image=? WHERE email=?";
                prepare = database.con.prepareStatement(updateImageQuery);
                prepare.setString(1, selectedFile.toURI().toString());
                prepare.setString(2, UserData.email);
                int rowsAffected = prepare.executeUpdate();
                if (rowsAffected > 0) {
                    Image image = new Image(selectedFile.toURI().toString());
                    imageView.setImage(image);
                    imageViewUser.setImage(image);



                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Profile image updated successfully");
                    alert.showAndWait();
                }  else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to update profile image");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    public void userDeleteBtn(){

        database database=new database();
        database.connect();
        try {

            if (fullName.getText().isEmpty()){

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank field");
                alert.showAndWait();


            }else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete fullname = "+fullName.getText()+"?");
                Optional<ButtonType> option =alert.showAndWait();

                if (option.get().equals(ButtonType.OK)){

                    String deleteData="DELETE FROM user WHERE fullname = '"+fullName.getText()+"'";
                    prepare =con.prepareStatement(deleteData);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("information message");
                    alert.setHeaderText(null);
                    alert.setContentText("successfully Deleted ! ");
                    alert.showAndWait();
                    UserShowData();
                    userClearBtn();


                }else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled !");
                    alert.showAndWait();
                }



            }




        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @FXML
    private void updateUserPasswordd(ActionEvent event) {
        String newPasswordStr = newpasswordtextFielduser.getText();
        String confirmPasswordStr = ConfirmerPasswordTextField.getText();

        // check if fields are not empty
        if ( newPasswordStr.isEmpty() || confirmPasswordStr.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all fields.");
            alert.showAndWait();
            return;
        }

        // check if new password and confirm password match
        if (!newPasswordStr.equals(confirmPasswordStr)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("New password and confirm password do not match.");
            alert.showAndWait();
            return;
        }

        // check if current password is correct
        //if (!currentPasswordStr.equals(UserData.password)) {
        //        Alert alert = new Alert(Alert.AlertType.ERROR);
        //        alert.setTitle("Error");
        //       alert.setHeaderText(null);
        //        alert.setContentText("Current password is incorrect.");
        //       alert.showAndWait();
        //       return;
        //}

        // update password in database
        try {
            database database = new database();
            database.connect();
            String updatePassword = "UPDATE user SET password=? WHERE email=?";
            prepare = database.con.prepareStatement(updatePassword);
            prepare.setString(1, newPasswordStr);
            prepare.setString(2, UserData.email);
            int rowsAffected = prepare.executeUpdate();
            if (rowsAffected > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Password updated successfully!");
                alert.showAndWait();
                //currentPassword.clear();
                newpasswordtextFielduser.clear();
                ConfirmerPasswordTextField.clear();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Could not update password.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not update password.");
            alert.showAndWait();
        }
    }
    public void clearPassword(){
        newpasswordtextFielduser.setText("");
        ConfirmerPasswordTextField.setText("");


    }
    public  void userClearBtn(){

        fullName.setText("");
        address.setText("");
        cin.setText("");
        email.setText("");
        roles.getSelectionModel().clearSelection();
        isverified.getSelectionModel().clearSelection();



    }
    private String[] rolesList={"ROLE_USER","ROLE_COACH","ROLE_ADMIN"};
    public void userRolesList(){
        List<String>uList=new ArrayList<>();
        for (String data :rolesList){
            uList.add(data);
        }
        ObservableList listData =FXCollections.observableArrayList(uList);
        roles.setItems(listData);
    }
    private String[] isverifiedList={"1","2"};
    public void userIsverifiedList(){
        List<String>isvList=new ArrayList<>();
        for (String data :isverifiedList){
            isvList.add(data);
        }
        ObservableList listData =FXCollections.observableArrayList(isvList);
        isverified.setItems(listData);
    }

    public AdminController() {

        con = database.createorgetInstance().getCon();

    }
    public ObservableList<UserData> userListData()  {

        ObservableList<UserData> listData= FXCollections.observableArrayList();
        String selectData ="SELECT * FROM user";
        database database=new database();

        database.connect();
        try {
            prepare = con.prepareStatement(selectData);
            result = prepare.executeQuery();
            UserData sData;

            while(result.next()){
                sData=new UserData(result.getString("fullname"),result.getString("email"),
                        result.getString("cin"),result.getString("address"),
                        result.getString("roles"),result.getInt("isverified"),
                        result.getString("password"), result.getString("description"),
                        result.getString("cv"), result.getString("image"), result.getString("specialite"));
                listData.add(sData);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }
    private ObservableList<UserData> UserDataa;
    public void UserShowData()  {

        UserDataa = userListData();
        fullName_col.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        cin_col.setCellValueFactory(new PropertyValueFactory<>("cin"));
        roles_col.setCellValueFactory(new PropertyValueFactory<>("roles"));
        address_col.setCellValueFactory(new PropertyValueFactory<>("address"));
        isverified_col.setCellValueFactory(new PropertyValueFactory<>("isverified"));

        tableView.setItems(UserDataa);


    }
    public void account(){

        titleusername.setText(UserData.email);

    }
    public void userSelectData(){
        UserData sData =tableView.getSelectionModel().getSelectedItem();
        int num =tableView.getSelectionModel().getSelectedIndex();
        if ((num-1)<-1)return;

            fullName.setText(String.valueOf(sData.getFullname()));
            email.setText(String.valueOf(sData.getEmail()));
            address.setText(String.valueOf(sData.getAddress()));
            cin.setText(String.valueOf(sData.getCin()));




    }
    public void signoutBtnOnAction(){
        btnSignout.getScene().getWindow().hide();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();


        }catch(Exception ex){
            ex.printStackTrace();
        }




    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userRolesList();
        userIsverifiedList();
        UserShowData();
        getUserData();
        account();
        UserShowDataChangerRoles();
        getUserDataProfile();


    }
    public void switchForm (ActionEvent e){
        if(e.getSource()==btnProfile){
            editpane.setVisible(true);
            pnlUsers.setVisible(false);
            pnlDataAnalysis.setVisible(false);
            pnlOverview.setVisible(false);
            panePassword.setVisible(false);
            pnlProfile.setVisible(true);
            pnlUsersChangerList.setVisible(false);


        }else if (e.getSource()==btnUsers){
            editpane.setVisible(false);
            pnlUsers.setVisible(true);
            pnlDataAnalysis.setVisible(false);
            pnlOverview.setVisible(false);
            panePassword.setVisible(false);
            pnlProfile.setVisible(false);
            pnlUsersChangerList.setVisible(false);




        }else if (e.getSource()==btnDataAnalysis){
            pnlDataAnalysis.setVisible(true);
            editpane.setVisible(false);
            pnlUsers.setVisible(false);
            pnlOverview.setVisible(false);
            panePassword.setVisible(false);
            pnlProfile.setVisible(false);
            pnlUsersChangerList.setVisible(false);



        } else if (e.getSource()==changerPasswordBtn) {

            pnlDataAnalysis.setVisible(false);
            editpane.setVisible(false);
            pnlUsers.setVisible(false);
            pnlOverview.setVisible(false);
            panePassword.setVisible(true);
            pnlProfile.setVisible(true);
            pnlUsersChangerList.setVisible(false);

            
        } else if (e.getSource()==btnOverview){
            pnlDataAnalysis.setVisible(false);
            editpane.setVisible(false);
            pnlUsers.setVisible(false);
            pnlOverview.setVisible(true);
            panePassword.setVisible(false);
            pnlProfile.setVisible(false);
            pnlUsersChangerList.setVisible(false);


            totalUsers();
            totalCoachs();
            totalIsVerified();
            totalSimpleUsers();
            totalUsersChangerRoles();


        } else if (e.getSource()==btnListChangerRoles) {
            pnlDataAnalysis.setVisible(false);
            editpane.setVisible(false);
            pnlUsers.setVisible(false);
            pnlOverview.setVisible(false);
            panePassword.setVisible(false);
            pnlProfile.setVisible(false);
            pnlUsersChangerList.setVisible(true);
        }

    }
    public void navigationChartButton(){

        if(line_chart_button.isFocused()){

            line_chart_page.setVisible(true);
            bar_chart_page.setVisible(false);
            area_chart_page.setVisible(false);

        }else if(bar_chart_button.isFocused()){

            line_chart_page.setVisible(false);
            bar_chart_page.setVisible(true);
            area_chart_page.setVisible(false);

        }else if(area_chart_button.isFocused()){

            line_chart_page.setVisible(false);
            bar_chart_page.setVisible(false);
            area_chart_page.setVisible(true);

        }

    }
    public void totalUsers(){

        database database=new database();
        database.connect();


        try{
            String sql = "SELECT count(fullname) FROM user WHERE roles != ''";
            prepare = con.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){

                String totalUser = result.getString("count(fullname)");

                totalUsers.setText(totalUser);

            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public void totalCoachs(){
        database database=new database();
        database.connect();


        try{
            String sql = "SELECT count(fullname) FROM user WHERE roles = 'ROLE_COACH'";

            prepare = con.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){

                String totalCoachs = result.getString("count(fullname)");

                totalCoach.setText(totalCoachs);
                totalCoach1.setText(totalCoachs);

            }

        }catch(Exception e){}

    }
    public void totalSimpleUsers(){
        database database=new database();
        database.connect();


        try{
            String sql = "SELECT count(fullname) FROM user WHERE roles = 'ROLE_USER'";

            prepare = con.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){

                String totalSimple = result.getString("count(fullname)");

                totalSimpleUser.setText(totalSimple);

            }

        }catch(Exception e){}

    }
    public void totalIsVerified(){
        database database=new database();
        database.connect();


        try{
            String sql = "SELECT count(fullname) FROM user WHERE isVerified = '2'";

            prepare = con.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){

                String totalIsverifiedd= result.getString("count(fullname)");

                totalIsVeri.setText(totalIsverifiedd);

            }

        }catch(Exception e){}

    }
    public void showChart(){
//        THAT IS FOR JULY 28 DATE THAT THE STUDENT WAS CERTIFIED ENROLLED, GRADUATED, INACTIVE
        database database=new database();
        database.connect();
//        ASC MEANING, ASCENDING ORDER
        String sql = "SELECT count(fullname),date FROM user WHERE roles != '' ORDER BY date asc";

        XYChart.Series<String, Integer> chart = new XYChart.Series<>();

        try{

            prepare = con.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){

                Integer count = Integer.parseInt(result.getString("count(fullname)"));

                chart.getData().add(new XYChart.Data<>(result.getString("date"), count));
            }
            if(line_chart_page.isVisible()){

                lineChart.getData().add(chart);

            }else if(bar_chart_page.isVisible()){

                barChart.getData().add(chart);

            }else if(area_chart_page.isVisible()){

                areaChart.getData().add(chart);

            }
        }catch(Exception e){}

    }
    public void userUpdateBtn(){
        database database=new database();
        database.connect();
        try {

            if (fullNameTextField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter your full name.");
                alert.showAndWait();
                return;
            } else if (database.con == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Could not establish a database connection.");
                alert.showAndWait();
                return;
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to update "+fullNameTextField.getText()+"?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK){
                    String updateData = "UPDATE user SET  fullname=?, email=?, cin=?, address=? WHERE email=?";
                    prepare = database.con.prepareStatement(updateData);
                    prepare.setString(1, fullNameTextField.getText());
                    prepare.setString(2, emailTextField.getText());
                    prepare.setString(3, cinTextField.getText());
                    prepare.setString(4, addressTextField.getText());
                    prepare.setString(5, UserData.email);
                    int rowsAffected = prepare.executeUpdate();
                    if (rowsAffected > 0){
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Information");
                        alert2.setHeaderText(null);
                        alert2.setContentText("User data updated successfully!");
                        alert2.showAndWait();
                    } else {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Error");
                        alert2.setHeaderText(null);
                        alert2.setContentText("Could not update user data.");
                        alert2.showAndWait();
                    }
                } else {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Information");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Update cancelled.");
                    alert2.showAndWait();
                }
            }




        }catch (Exception e){
            e.printStackTrace();
        }

    }



    //////////////////
    public void UserShowDataChangerRoles()  {

        UserDataa = userListDataCangerRoles();
        fullName_col1.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        email_col1.setCellValueFactory(new PropertyValueFactory<>("email"));
        cin_col1.setCellValueFactory(new PropertyValueFactory<>("cin"));
        roles_col1.setCellValueFactory(new PropertyValueFactory<>("roles"));
        specialte_col1.setCellValueFactory(new PropertyValueFactory<>("specialite"));
        cv_col1.setCellValueFactory(new PropertyValueFactory<>("cv"));

        tableView2.setItems(UserDataa);





    }
    public ObservableList<UserData> userListDataCangerRoles()  {

        ObservableList<UserData> listData= FXCollections.observableArrayList();
        String selectData ="SELECT * FROM user WHERE roles='ROLE_USER' AND specialite is not null AND cv is not null ";
        database database=new database();

        database.connect();
        try {
            prepare = con.prepareStatement(selectData);
            result = prepare.executeQuery();
            UserData sData;

            while(result.next()){
                sData=new UserData(result.getString("fullname"),result.getString("email"),
                        result.getString("cin"),result.getString("address"),
                        result.getString("roles"),result.getInt("isverified"),
                        result.getString("password"), result.getString("description"),
                        result.getString("cv"), result.getString("image"), result.getString("specialite"));
                listData.add(sData);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }
    public void totalUsersChangerRoles(){

        database database=new database();
        database.connect();


        try{
            String sql = "SELECT count(fullname) FROM user WHERE roles='ROLE_USER' AND specialite is not null AND cv is not null";
            prepare = con.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){

                String totalUser = result.getString("count(fullname)");

                totalUsersChangerRoles.setText(totalUser);

            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }


    public void userSelectDataRoles(){
        UserData sData =tableView2.getSelectionModel().getSelectedItem();
        int num =tableView2.getSelectionModel().getSelectedIndex();
        if ((num-1)<-1)return;

        fullNameSelectionner.setText(String.valueOf(sData.getFullname()));
        emailSelectionner.setText(String.valueOf(sData.getEmail()));





    }

    public void userDeleteBtn1(){

        database database=new database();
        database.connect();
        try {

            if (fullNameSelectionner.getText().isEmpty()){

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank field");
                alert.showAndWait();


            }else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete fullname = "+fullNameSelectionner.getText()+"?");
                Optional<ButtonType> option =alert.showAndWait();

                if (option.get().equals(ButtonType.OK)){

                    String deleteData="DELETE FROM user WHERE fullname = '"+fullNameSelectionner.getText()+"'";
                    prepare =con.prepareStatement(deleteData);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("information message");
                    alert.setHeaderText(null);
                    alert.setContentText("successfully Deleted ! ");
                    alert.showAndWait();
                    UserShowData();
                    userClearBtn();


                }else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled !");
                    alert.showAndWait();
                }



            }




        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void showusercardBtn (ActionEvent e){
        if(e.getSource()==qddBtn1){
            paneProfileCard.setVisible(true);
            pnlUsersChangerList.setVisible(true);




        }else if (e.getSource()==hideBtn){
            paneProfileCard.setVisible(false);
            pnlUsersChangerList.setVisible(true);





        }

    }


    public void getUserDataProfile(){
        fullnameProfileLabel.setText(UserData.fullname);
        emailProfileLabel.setText(UserData.email);
        cinProfileLabel.setText(UserData.cin);
        addressProfileLabel.setText(UserData.address);
        specialiteProfileLabel.setText(UserData.specialite);
        rolesProfileLabel.setText(UserData.roles);



    }


    public void userShowProfile(){

        database database=new database();
        database.connect();
        try {

            if (fullNameSelectionner.getText().isEmpty()){

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank field");
                alert.showAndWait();


            }else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete fullname = "+fullNameSelectionner.getText()+"?");
                Optional<ButtonType> option =alert.showAndWait();

                if (option.get().equals(ButtonType.OK)){

                    String deleteData="DELETE FROM user WHERE fullname = '"+fullNameSelectionner.getText()+"'";
                    prepare =con.prepareStatement(deleteData);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("information message");
                    alert.setHeaderText(null);
                    alert.setContentText("successfully Deleted ! ");
                    alert.showAndWait();
                    UserShowData();
                    userClearBtn();


                }else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled !");
                    alert.showAndWait();
                }



            }




        }catch (Exception e){
            e.printStackTrace();
        }

    }


}

