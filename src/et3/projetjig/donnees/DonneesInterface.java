package et3.projetjig.donnees;

import et3.projetjig.donnees.types.CarreGrid;
import et3.projetjig.donnees.types.Observation;
import et3.projetjig.donnees.types.Taxon;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;

public interface DonneesInterface {
  public CarreGrid[] getOccurences(
    String nomEspece,
    short precision,
    GeoHash geohash
  );

  public Observation[] getObservations(
    String geohash,
    long tempsDebut,
    long dt,
    int N,
    String nomEspece
  );

  public Taxon[] getTaxons(String debutNom);
}
