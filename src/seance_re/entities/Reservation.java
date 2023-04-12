package seance_re.entities;

import java.util.Date;

public class Reservation {
    int id;
    int seance_id;
    Date date;
    public Reservation(){};
    public Reservation(int id, int seance_id, Date date) {
        this.id = id;
        this.seance_id = seance_id;
        this.date = date;
    }
    public Reservation(int seance_id, Date date) {
        this.seance_id = seance_id;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", seance_id=" + seance_id +
                ", date=" + date +
                '}';
    }

    public int getId() {
        return id;
    }

    public int getSeance_id() {
        return seance_id;
    }

    public Date getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSeance_id(int seance_id) {
        this.seance_id = seance_id;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
