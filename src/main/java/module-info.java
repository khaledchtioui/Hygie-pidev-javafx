module com.example.logindata {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.logindata to javafx.fxml;
    exports com.example.logindata;
}