package et3.projetjig.donnees.types;

public class Occurrences {

  private Taxon espece;
  private Occurrence[] occurrences;
  private int minOccurrences;
  private int maxOccurrences;
  private short anneeDebut;
  private short anneeFin;

  public Occurrences(Taxon espece, Occurrence[] occurences, int minOccurrences, int maxOccurrences, short anneeDebut, short anneeFin) {
    this.espece = espece;
    this.occurrences = occurences;
    this.minOccurrences = minOccurrences;
    this.maxOccurrences = maxOccurrences;
    this.anneeDebut = anneeDebut;
    this.anneeFin = anneeFin;
  }

  public Taxon getEspece() {
    return espece;
  }

  public Occurrence[] getOccurrences() {
    return occurrences;
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
}
