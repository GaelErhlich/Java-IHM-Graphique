package et3.projetjig.fenetre;

import et3.projetjig.donnees.types.Observation;
import et3.projetjig.donnees.types.Taxon;

public interface FenetreInterface {

    void recoitEspeceParBDD(Taxon espece);

    void recoitEspecesParBDD(String[] nomsEspeces);

    void recoitObservationParBDD(Observation obs);

}
