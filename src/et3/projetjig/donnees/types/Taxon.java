package et3.projetjig.donnees.types;

public class Taxon {
    int id;
    String nomScientifique;
    String rang;
    String phylum;

    public Taxon(int id, String nomScientifique, String rang, String phylum) {
        this.id = id;
        this.nomScientifique = nomScientifique;
        this.rang = rang;
        this.phylum = phylum;
    }
}
