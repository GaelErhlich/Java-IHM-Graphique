package et3.projetjig.donnees.types;

import et3.projetjig.donnees.types.exceptions.AuMoins1InteveralleException;

/**
 * Donne les occurrences d'une espèce sur une période de temps, ainsi que sur la même
 * période divisée en intervalles plus courts
 */
public class OccurrencesPartition {

  private Taxon espece;
  private Occurrences[] occurrences;
  private Occurrences occGlobales;
  private int minOccsLocales;
  private int maxOccsLocales;

  private int iCourant = 0;

  public OccurrencesPartition(Taxon espece, Occurrences[] occurrences, Occurrences occGlobales,
                              int minOccsLocales, int maxOccsLocales)
              throws AuMoins1InteveralleException {

    if(occurrences == null || occurrences.length == 0) {
      throw new AuMoins1InteveralleException(this);
    }

    this.espece = espece;
    this.occurrences = occurrences;
    this.occGlobales = occGlobales;
    this.minOccsLocales = minOccsLocales;
    this.maxOccsLocales = maxOccsLocales;
  }

  /**
   * Indique l'espèce concernée par ces occurrences
   * @return une espèce sous forme de Taxon
   */
  public Taxon getEspece() {
    return espece;
  }

  /**
   * Donne les occurrences pour tout l'intervalle de temps (non-partitionné)
   * @return des occurrences sous forme de Occurrences
   */
  public Occurrences getOccsGlobales() {
    return occGlobales;
  }

  /**
   * Donne le nombre d'occurrences minimal présent dans l'ensemble des sous-intervalles
   * de temps.
   * @return la valeur minimale
   */
  public int getMinPourInterv() {
    return minOccsLocales;
  }

  /**
   * Donne le nombre d'occurrences maximal présent dans l'ensemble des sous-intervalles
   * de temps.
   * @return la valeur maximale
   */
  public int getMaxPourInterv() {
    return maxOccsLocales;
  }

  /**
   * Donne le nombre d'occurrences du GeoHash ayant le plus petit nombre d'occurrences sur
   * toute la période (non-partitionnée)
   * @return la valeur minimale
   */
  public int getMinGlobales() {
    return occGlobales.getMin();
  }

  /**
   * Donne le nombre d'occurrences du GeoHash ayant le plus grand nombre d'occurrences sur
   * toute la période (non-partitionnée)
   * @return la valeur maximale
   */
  public int getMaxGlobales() {
    return occGlobales.getMax();
  }

  /**
   * Donne l'année au tout début de l'intervalle (non-partitionné)
   * @return l'année minimale
   */
  public short getAnneeDebut() {
    return occGlobales.getAnneeDebut();
  }

  /**
   * Donne la dernière année de l'intervalle (non-partitionné)
   * @return l'année maximale
   */
  public short getAnneeFin() {
    return occGlobales.getAnneeFin();
  }

  /**
   * Donne les occurrences dans l'intervalle de temps (partitionné) suivant celui donné au
   * dernier appel de la fonction. On commence au premier intervalle et on revient au début
   * après le dernier intervalle.
   * @return un objet Occurrences contenant les occurrences sur la partition de temps suivante
   */
  public Occurrences suivant() {
    iCourant = (iCourant+1) % (occurrences.length);
    return occurrences[iCourant];
  }

  /**
   * Indique si on est au dernier intervalle de temps, donc si la fonction suivant() va
   * renvoyer le premier intervalle.
   * @return true si c'est le dernier intervalle, false sinon
   */
  public boolean estDernier() {
    return iCourant+1 == occurrences.length;
  }
}
