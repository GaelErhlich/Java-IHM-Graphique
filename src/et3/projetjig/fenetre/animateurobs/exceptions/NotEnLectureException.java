package et3.projetjig.fenetre.animateurobs.exceptions;

import et3.projetjig.fenetre.animateurobs.AnimObsPartitionListener;
import et3.projetjig.fenetre.animateurobs.AnimateurOccsPartition;

public class NotEnLectureException extends Exception {

    public AnimObsPartitionListener parent;
    public AnimateurOccsPartition animateur;

    public NotEnLectureException(AnimObsPartitionListener parent, AnimateurOccsPartition animateur) {
        super("L'animateur ne peut pas mettre en pause alors que l'animation n'est pas en lecture.");
        this.parent = parent;
        this.animateur = animateur;
    }

}
