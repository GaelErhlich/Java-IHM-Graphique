package et3.projetjig.donnees;

import et3.projetjig.donnees.types.CarreGrid;
import et3.projetjig.donnees.types.Observation;
import et3.projetjig.donnees.types.Taxon;

public class ObisAPI implements DonneesInterface {

  @Override
  public CarreGrid[] getOccurences(
    String nomEspece,
    short precision,
    double x,
    double y
  ) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Observation[] getObservations(
    String geohash,
    long tempsDebut,
    long dt,
    int N,
    String nomEspece
  ) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Taxon[] getTaxons(String debutNom) {
    // TODO Auto-generated method stub
    return null;
  }
}
