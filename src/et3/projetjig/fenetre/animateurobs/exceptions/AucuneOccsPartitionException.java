package et3.projetjig.fenetre.animateurobs.exceptions;

import et3.projetjig.fenetre.animateurobs.AnimObsPartitionListener;
import et3.projetjig.fenetre.animateurobs.AnimateurOccsPartition;

/**
 * Exception lancée lorsqu'aucune OccurrencesPartition n'est disponible mais est nécessaire
 */
public class AucuneOccsPartitionException extends Exception {

    public AnimObsPartitionListener parent;
    public AnimateurOccsPartition animateur;

    public AucuneOccsPartitionException(AnimObsPartitionListener parent, AnimateurOccsPartition animateur) {
        super("L'animateur ne peut pas effectuer cette action en l'absence d'une OccurrencesPartition.");
        this.parent = parent;
        this.animateur = animateur;
    }

}
