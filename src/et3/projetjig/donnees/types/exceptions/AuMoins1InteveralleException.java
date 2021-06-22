package et3.projetjig.donnees.types.exceptions;

import et3.projetjig.donnees.types.OccurrencesPartition;

public class AuMoins1InteveralleException extends Exception {

    public OccurrencesPartition occurrencesPartition = null;

    public AuMoins1InteveralleException(OccurrencesPartition occurrencesPartition) {
        super("Cette OccurrencesPartition ne contient aucun intervalle.");
    }


}
