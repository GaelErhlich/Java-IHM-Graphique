package et3.projetjig.donnees.types;

public class Observation {

  String nomScientifique;
  String nomEspece;
  String ordre;
  String superClasse;
  String origineEnregistrement;

  public Observation(String nomScientifique, String nomEspece, String ordre, String superClasse, String origineEnregistrement) {
    this.nomScientifique = nomScientifique;
    this.nomEspece = nomEspece;
    this.ordre = ordre;
    this.superClasse = superClasse;
    this.origineEnregistrement = origineEnregistrement;
  }
}
