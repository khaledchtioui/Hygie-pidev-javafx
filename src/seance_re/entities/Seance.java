package seance_re.entities;


import java.util.Date;

public class Seance {
    int id;
    String titre;
    String image;
    String description;
    float prix;
    Date date;
    public Seance() {
        // Default constructor
    }
    public Seance(int id, String titre, String image, String description, float prix, Date date) {
        this.id = id;
        this.titre = titre;
        this.image = image;
        this.description = description;
        this.prix = prix;
        this.date = date;
    }
    public Seance(String titre, String image, String description, float prix, Date date) {
        this.titre = titre;
        this.image = image;
        this.description = description;
        this.prix = prix;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Seance{" +
                "titre='" + titre + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", date=" + date +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
