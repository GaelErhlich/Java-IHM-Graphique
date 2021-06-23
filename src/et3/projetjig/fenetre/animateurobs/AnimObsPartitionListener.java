package et3.projetjig.fenetre.animateurobs;

import et3.projetjig.donnees.types.Occurrences;

public interface AnimObsPartitionListener {

    void recoitOccurrencesParAnim(Occurrences occurrences, int min, int max);

    void recoitDeselectOccsParAnim();

}
