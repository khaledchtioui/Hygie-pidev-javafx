/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hygie.services;

import hygie.utils.MyDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.*;
import java.util.List;
import hygie.entities.Reclamation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author abedj
 */
public class ServiceReclamation implements IService<Reclamation> {
        Connection cnx = MyDB.getInstance().getCnx();

    @Override
    public void ajouter(Reclamation s) {
        try {
            String req = "INSERT INTO `reclamation` (`date`,`titre`,`type`,`descreption`,`status`) VALUES (?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(2, s.getTitre());
            ps.setString(3, s.getType());
            ps.setString(4, s.getDescreption());
            ps.setString(5, s.getStatus());
            ps.executeUpdate();
            System.out.println("Reclamation created !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void delete(int id) {
        try {
            String req = "DELETE FROM `reclamation` WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("reclamation deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

     @Override
public void modifier(Reclamation m){
    try {
        String req = "UPDATE `reclamation` SET `date`=?,`titre`=?,`type`=?,`descreption`=?,`status`=? WHERE id=?";
        PreparedStatement ps = cnx.prepareStatement(req);
       ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(2, m.getTitre());
        ps.setString(3, m.getType());
        ps.setString(4, m.getDescreption());
        ps.setString(5, m.getStatus());
        ps.setInt(6, m.getId());

        ps.executeUpdate();
        System.out.println("Reclamation Updated !");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}


    @Override
    public List<Reclamation> getAll() {
        List<Reclamation> list = new ArrayList<>();
        try {
            String req = "SELECT * FROM `Reclamation`";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Reclamation s = new Reclamation(rs.getInt("id"),rs.getTimestamp("date").toLocalDateTime(), rs.getString("titre"), rs.getString("type"),rs.getString("descreption"), rs.getString("status"));
                list.add(s);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    @Override
    public Reclamation getOneById(int id) {
        Reclamation s = null;
        try {
            String req = "SELECT * FROM `reclamation` WHERE id = " + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                s = new Reclamation(rs.getInt("id"),rs.getTimestamp("date").toLocalDateTime(), rs.getString("titre"), rs.getString("type"),rs.getString("descreption"), rs.getString("status"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return s;
    }


}
