package et3.projetjig.donnees.types;

import et3.projetjig.donnees.types.exceptions.AuMoins1InteveralleException;

public class OccurrencesPartition {

  private final short INTERVALLE_ANNEES = 5;

  private Taxon espece;
  private Occurrences[] occurrences;
  private Occurrences occGlobales;
  private int minOccurrences;
  private int maxOccurrences;
  private short anneeDebut;
  private short anneeFin;

  private int iCourant = 0;

  public OccurrencesPartition(Taxon espece, Occurrences[] occurrences, Occurrences occGlobales,
                              int minOccurrences, int maxOccurrences, short anneeDebut, short anneeFin)
              throws AuMoins1InteveralleException {

    if(occurrences == null || occurrences.length == 0) {
      throw new AuMoins1InteveralleException(this);
    }

    this.espece = espece;
    this.occurrences = occurrences;
    this.occGlobales = occGlobales;
    this.minOccurrences = minOccurrences;
    this.maxOccurrences = maxOccurrences;
    this.anneeDebut = anneeDebut;
    this.anneeFin = anneeFin;
  }

  public Taxon getEspece() {
    return espece;
  }

  public Occurrences getOccurrences() {
    return occGlobales;
  }

  public int getMin() {
    return minOccurrences;
  }

  public int getMax() {
    return maxOccurrences;
  }

  public short getAnneeDebut() {
    return anneeDebut;
  }

  public short getAnneeFin() {
    return anneeFin;
  }

  public Occurrences suivant() {
    iCourant = (iCourant+1) % (INTERVALLE_ANNEES-1);
    return occurrences[iCourant];
  }

  public boolean estDernier() {
    return true;
  }
}
