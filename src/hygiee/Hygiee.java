/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hygiee;

import Hygie.entities.Questionnaire;
import Hygie.entities.Questions;
import Hygie.services.ServiceQuestion;
import Hygie.services.ServiceQuiz;
import Hygie.utils.MyDB;

/**
 *
 * @author Khaled
 */
public class Hygiee {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
                MyDB.createorgetInstance();
                 Questionnaire p1 =new Questionnaire("Kalbousi");
                 p1.setId(15);
                 Questions ques = new Questions("zz",1,1,p1);
                 ServiceQuiz sp = new ServiceQuiz();
        
                 ServiceQuestion sq = new ServiceQuestion()    ;
     
        ques.setId(19);
sq.supprimer(ques); 
System.out.println(sp.getAll());
//        System.out.println(sq.getAll2());

    }
    
}
