package et3.projetjig.fenetre.animateurobs.exceptions;

import et3.projetjig.fenetre.animateurobs.AnimObsPartitionListener;
import et3.projetjig.fenetre.animateurobs.AnimateurOccsPartition;

public class AucuneOccsPartitionException extends Exception {

    public AnimObsPartitionListener parent;
    public AnimateurOccsPartition animateur;

    public AucuneOccsPartitionException(AnimObsPartitionListener parent, AnimateurOccsPartition animateur) {
        super("L'animateur ne peut pas lancer la lecture sans OccurrencesPartition.");
        this.parent = parent;
        this.animateur = animateur;
    }

}
