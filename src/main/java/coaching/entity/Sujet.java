package coaching.entity;


import java.sql.Date;

public class Sujet {
    private int id;

    private String titre ;
    private int user_id;
    private String description;
    private java.sql.Date date;
    private int nbreponse;


    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNbreponse() {
        return nbreponse;
    }

    public void setNbreponse(int nbreponse) {
        this.nbreponse = nbreponse;
    }
}
