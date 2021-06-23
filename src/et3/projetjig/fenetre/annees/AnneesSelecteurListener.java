package et3.projetjig.fenetre.annees;

public interface AnneesSelecteurListener {

    /**
     * Notifie la fenêtre qu'une ou deux nouvelles années ont été sélectionnées par l'utilisateur
     * @param debutAnnee année du début de l'intervalle
     * @param finAnnee année de la fin de l'intervalle
     */
    void recoitAnneesParUser(short debutAnnee, short finAnnee);

}
