package et3.projetjig.donnees.types;

public class OccurencesPartition {

  private Taxon espece;
  private Occurences[] occurences;
  private Occurence[] occGlobales;
  private int minOccurences;
  private int maxOccurences;
  private short anneeDebut;
  private short anneeFin;

  public Occurence[] suivant() {
    return null;
  }

  public boolean estDernier() {
    return true;
  }
}
