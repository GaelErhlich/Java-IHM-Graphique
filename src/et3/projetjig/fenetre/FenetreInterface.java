package et3.projetjig.fenetre;

import et3.projetjig.donnees.types.Observation;
import et3.projetjig.donnees.types.OccurrencesPartition;
import et3.projetjig.donnees.types.Taxon;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;

public interface FenetreInterface {

    /**
     * Notifie la fenêtre que la base de données a envoyé une OccurrencesPartition
     * @param op reçue
     */
    void recoitOccurrencesParBDD(OccurrencesPartition op);

    /**
     * Notifie la fenêtre que la base de données a envoyé des noms d'espèces
     * @param nomsEspeces un tableau de noms d'espèces possibles
     */
    void recoitEspecesParBDD(String[] nomsEspeces);

    /**
     * Notifie la fenêtre que la base de données a envoyé une erreur pour noms invalides
     * @param nomInvalide nom n'ayant donné aucun résultat
     */
    void recoitErreurEspece(String nomInvalide);

    /**
     * Notifie la fenêtre que la base de données a envoyé des Observation
     * @param geoHash GeoHash étudié
     * @param obs tableau d'Observation reçu
     */
    void recoitObservationsParBDD(GeoHash geoHash, Observation[] obs);

}
