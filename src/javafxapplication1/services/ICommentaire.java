/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1.services;

import java.util.ArrayList;

/**
 *
 * @author Yassine
 */
public interface ICommentaire  <T> {
    
        public void Ajouter(T t );

       public void Supprimer(T t);
       
       public ArrayList<T> Afficher(int id);
       
       
}
