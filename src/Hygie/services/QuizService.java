package hygie.services;


import java.util.List;

public interface QuizService<T> {
    public void Ajouter(T t );
    public List<T> getAll();
    public void supprimer(T t);



}



