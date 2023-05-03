package seance_re.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDB {
    String url = "jdbc:mysql://localhost:3306/hygie_app";
    String user = "root";
    String pwd = "0000";
    Connection con;

    //2
    public static MyDB instance;


    //1
    private MyDB() {

        try {
            System.out.println("en cours de connexion");
            con = DriverManager.getConnection(url, user, pwd);
            System.out.println("conexion etablie");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    //3
    public static MyDB createorgetInstance(){
        if(instance ==null ){
            instance = new MyDB();

        }

        return instance;
    }




    public Connection getCon() {
        return con;
    }





}
