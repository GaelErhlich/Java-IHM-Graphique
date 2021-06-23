package et3.projetjig.fenetre.animateurobs;

import et3.projetjig.donnees.types.Occurrences;

public interface AnimObsPartitionListener {

    /**
     * Notifie la fenêtre qu'une nouvelle occurrence doit être affichée
     * @param occurrences la nouvelle occurrence à afficher
     * @param min la valeur minimale parmi ces occurrences
     * @param max la valeur maximale parmi ces occurrences
     */
    void recoitOccurrencesParAnim(Occurrences occurrences, int min, int max);

}
