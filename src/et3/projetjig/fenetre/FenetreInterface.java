package et3.projetjig.fenetre;

import et3.projetjig.donnees.types.Observation;
import et3.projetjig.donnees.types.OccurrencesPartition;
import et3.projetjig.donnees.types.Taxon;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;

public interface FenetreInterface {

    void recoitOccurrencesParBDD(OccurrencesPartition op);

    void recoitEspecesParBDD(String[] nomsEspeces);

    void recoitErreurEspece(String nomInvalide);

    void recoitObservationsParBDD(GeoHash geoHash, Observation[] obs);

}
