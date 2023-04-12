/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hygie.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author abedj
 */
public class MyDB {
    
      private String url="jdbc:mysql://localhost:3306/hygie";
    private String login="root";
    private String pwd="";
    private Connection cnx;
    private static MyDB instance;
    
      private MyDB () {
        try {
            cnx=DriverManager.getConnection(url, login, pwd);
            System.out.println("Connexion etablie");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
          
      }
    
      public static MyDB getInstance(){
          if (instance==null)
              instance=new MyDB();
          return instance ;
      }
      
      public Connection getCnx() {
          return cnx;
      }  
    
}
