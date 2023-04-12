package coaching.entity;


import java.sql.Date;


public class Reponsee {
    private int id;
    private int user_id;
    private int sujet_id;
    private String contenu;
    private Date date;

    public Reponsee() {
    }

    public Reponsee(String contenu) {
        this.contenu = contenu;
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

    public int getSujet_id() {
        return sujet_id;
    }

    public void setSujet_id(int sujet_id) {
        this.sujet_id = sujet_id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Reponsee{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", sujet_id=" + sujet_id +
                ", contenu='" + contenu + '\'' +
                ", date=" + date +
                '}';
    }

}
