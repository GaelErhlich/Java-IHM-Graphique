package et3.projetjig.donnees.types;

public class Observation {

  private String nomScientifique;
  private String nomEspece;
  private String ordre;
  private String superClasse;

  public String getNomScientifique() {
    return nomScientifique;
  }

  public String getNomEspece() {
    return nomEspece;
  }

  public String getOrdre() {
    return ordre;
  }

  public String getSuperClasse() {
    return superClasse;
  }

  public String getOrigineEnregistrement() {
    return origineEnregistrement;
  }

  private String origineEnregistrement;

  public Observation(String nomScientifique, String nomEspece, String ordre, String superClasse, String origineEnregistrement) {
    this.nomScientifique = nomScientifique;
    this.nomEspece = nomEspece;
    this.ordre = ordre;
    this.superClasse = superClasse;
    this.origineEnregistrement = origineEnregistrement;
  }
}
