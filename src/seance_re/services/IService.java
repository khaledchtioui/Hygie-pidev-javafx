package seance_re.services;

import seance_re.entities.Seance;

import java.sql.SQLException;

public interface IService<t> {
public void Ajouter(Seance t);
public void supprimerSeance(Seance t) throws SQLException;
}
