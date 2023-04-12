/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.security.Provider;
import javafxapplication1.entities.Article;
import javafxapplication1.services.ArticleService;
import javafxapplication1.utils.MyDB;


/**
 *
 * @author Andrew
 */
public class JavaFXApplication1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      /* A a1 = A.getInstance();
       A a2 = A.getInstance();
       
       
        System.out.println(a1.hashCode());
        System.out.println(a2.hashCode());*/
      
      
        MyDB.createorgetInstance();
        
        Article article =new Article("Kalbousi", "Yassine", "klfdhksjdhfsjlkdvnkjDSBV");
        
        ArticleService sp = new ArticleService();
        

        //sp.Ajouter(p1);
       // sp.Ajouter2(p2);
        System.out.println(article);
    }
    
}
