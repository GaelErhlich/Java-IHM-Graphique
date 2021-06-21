package et3.projetjig.donnees.types;

public class OccurrencesPartition {

  private Taxon espece;
  private Occurrences[] occurrences;
  private Occurrence[] occGlobales;
  private int minOccurrences;
  private int maxOccurrences;
  private short anneeDebut;
  private short anneeFin;


  public OccurrencesPartition(Taxon espece, Occurrences[] occurrences, Occurrence[] occGlobales, int minOccurrences, int maxOccurrences, short anneeDebut, short anneeFin) {
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

  public Occurrence[] getOccurrences() {
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

  public Occurrence[] suivant() {
    return null;
  }

  public boolean estDernier() {
    return true;
  }
}
