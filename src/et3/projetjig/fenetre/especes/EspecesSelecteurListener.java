package et3.projetjig.fenetre.especes;

public interface EspecesSelecteurListener {

    /**
     * Notifie la fenêtre qu'un nouveau nom d'espèce a été sélectionné par l'utilisateur
     * @param nom le nouveau nom d'espèce
     */
    void recoitEspeceParUser(String nom);

}
