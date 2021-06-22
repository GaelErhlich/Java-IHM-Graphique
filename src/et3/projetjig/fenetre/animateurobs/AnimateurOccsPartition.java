package et3.projetjig.fenetre.animateurobs;

import et3.projetjig.donnees.types.OccurrencesPartition;
import et3.projetjig.fenetre.animateurobs.exceptions.AucuneOccsPartitionException;
import et3.projetjig.fenetre.animateurobs.exceptions.NotEnLectureException;
import javafx.scene.control.Button;

import java.util.concurrent.*;

public class AnimateurOccsPartition {

    public final static int PERIODE_ANIMATION = 2000;


    AnimObsPartitionListener parent;

    private final Button lireBtn;
    private final Button globalBtn;


    private final static short MODE_ATTENTE = 0;
    private final static short MODE_GLOBAL = 1;
    private final static short MODE_LIRE = 2;
    private final static short MODE_PAUSE = 3;
    private short mode = MODE_ATTENTE;

    private OccurrencesPartition occPartition = null;
    private ScheduledExecutorService execService = Executors.newSingleThreadScheduledExecutor();
    ScheduledFuture scheduleActuel = null;


    public AnimateurOccsPartition(AnimObsPartitionListener parent, Button lireBtn, Button globalBtn) {
        this.parent = parent;

        this.lireBtn = lireBtn;
        this.globalBtn = globalBtn;
    }


    private void setMode(short animateurMode) {
        mode = animateurMode;
    }


    public void setOccPartition(OccurrencesPartition occurrencesPartition) {
        this.occPartition = occurrencesPartition;
        setMode(MODE_GLOBAL);

        try {
            global();
        } catch(AucuneOccsPartitionException ignored) {}
    }



    private void envoyerOccurrencesSuiv() {
        parent.recoitOccurrencesParAnim(occPartition.suivant(),
                occPartition.getMinPourInterv(), occPartition.getMaxPourInterv());
    }


    public void lire() throws AucuneOccsPartitionException {
        if(mode == MODE_ATTENTE) { throw new AucuneOccsPartitionException(parent, this); }
        setMode(MODE_LIRE);

        // On initialise la lecture en mettant le premier intervalle sur l'interface
        parent.recoitOccurrencesParAnim(occPartition.actuelle(),
                occPartition.getMinPourInterv(), occPartition.getMaxPourInterv());

        // On programme pour régulièrement passer à l'animation suivante
        scheduleActuel = execService.scheduleAtFixedRate(this::envoyerOccurrencesSuiv,
                PERIODE_ANIMATION, PERIODE_ANIMATION, TimeUnit.MILLISECONDS);

    }


    public void pause() throws NotEnLectureException {
        if(mode != MODE_LIRE) { throw new NotEnLectureException(parent, this); }
        setMode(MODE_PAUSE);

        scheduleActuel.cancel(false);
    }


    public void global() throws AucuneOccsPartitionException {
        if(mode == MODE_ATTENTE) { throw new AucuneOccsPartitionException(parent, this); }
        setMode(MODE_GLOBAL);

        parent.recoitOccurrencesParAnim(occPartition.getOccsGlobales(),
                occPartition.getMinGlobales(), occPartition.getMaxGlobales());
    }


}
