package et3.projetjig.donnees.types;

public class Taxon {
    private int id;
    private String nomScientifique;
    private String rang;

    public int getId() {
        return id;
    }

    public String getNomScientifique() {
        return nomScientifique;
    }

    public String getRang() {
        return rang;
    }

    public String getPhylum() {
        return phylum;
    }

    private String phylum;

    public Taxon(int id, String nomScientifique, String rang, String phylum) {
        this.id = id;
        this.nomScientifique = nomScientifique;
        this.rang = rang;
        this.phylum = phylum;
    }
}
