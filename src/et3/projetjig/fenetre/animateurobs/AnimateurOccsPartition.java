package et3.projetjig.fenetre.animateurobs;

import et3.projetjig.donnees.types.OccurrencesPartition;
import et3.projetjig.donnees.types.Taxon;
import et3.projetjig.fenetre.animateurobs.exceptions.AucuneOccsPartitionException;
import et3.projetjig.fenetre.animateurobs.exceptions.NotEnLectureException;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.util.concurrent.*;

public class AnimateurOccsPartition {

    public final static int PERIODE_ANIMATION = 1000;


    AnimObsPartitionListener parent;

    private final Button lireBtn;
    private final Button globalBtn;


    private final static short MODE_ATTENTE = 0;
    private final static short MODE_GLOBAL = 1;
    private final static short MODE_LIRE = 2;
    private final static short MODE_PAUSE = 3;
    private short mode = MODE_ATTENTE;

    private OccurrencesPartition occPartition = null;
    private final ScheduledExecutorService execService = Executors.newSingleThreadScheduledExecutor();
    ScheduledFuture scheduleActuel = null;


    public AnimateurOccsPartition(AnimObsPartitionListener parent, Button lireBtn, Button globalBtn) {
        this.parent = parent;

        this.lireBtn = lireBtn;
        this.globalBtn = globalBtn;

        attente();

        initialiseEvents();
    }



    private void initialiseEvents() {

        lireBtn.addEventHandler(MouseEvent.MOUSE_PRESSED, (event -> {
            try {

                if(mode == MODE_GLOBAL) {
                    lire();
                } else if(mode == MODE_LIRE) {
                    pause();
                } else if(mode == MODE_PAUSE) {
                    lire();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }));


        globalBtn.addEventHandler(MouseEvent.MOUSE_PRESSED, (event -> {
            try {

                if(mode == MODE_LIRE || mode == MODE_PAUSE) {
                    global();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

    }




    private void setMode(short animateurMode) {
        if(animateurMode != MODE_LIRE && scheduleActuel != null) {
            scheduleActuel.cancel(true);
            scheduleActuel = null;
        }

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
        Platform.runLater(()->{
            if(scheduleActuel != null) {
                parent.recoitOccurrencesParAnim(occPartition.suivant(),
                        occPartition.getMinPourInterv(), occPartition.getMaxPourInterv());

                lireBtn.setText("Pauser évolution ("
                        +occPartition.actuelle().getAnneeDebut()+"-"+occPartition.actuelle().getAnneeFin()+")");
            }
        });
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

        Platform.runLater(()->{
            lireBtn.setText("Pauser évolution ("
                    +occPartition.actuelle().getAnneeDebut()+"-"+occPartition.actuelle().getAnneeFin()+")");
            lireBtn.setDisable(false);
            globalBtn.setText("Occurrences globales ("
                    +occPartition.getAnneeDebut()+"-"+occPartition.getAnneeFin()+")");
            globalBtn.setDisable(false);
        });


    }


    public void pause() throws NotEnLectureException {
        if(mode != MODE_LIRE) { throw new NotEnLectureException(parent, this); }
        setMode(MODE_PAUSE);


        Platform.runLater(()->{
            lireBtn.setText("Lire évolution ("
                    +occPartition.getAnneeDebut()+"-"+occPartition.getAnneeFin()+")");
            lireBtn.setDisable(false);
            globalBtn.setText("Occurrences globales ("
                    +occPartition.getAnneeDebut()+"-"+occPartition.getAnneeFin()+")");
            globalBtn.setDisable(false);
        });

    }


    public void global() throws AucuneOccsPartitionException {
        if(mode == MODE_ATTENTE) { throw new AucuneOccsPartitionException(parent, this); }
        setMode(MODE_GLOBAL);

        occPartition.mettreDebut();
        parent.recoitOccurrencesParAnim(occPartition.getOccsGlobales(),
                occPartition.getMinGlobales(), occPartition.getMaxGlobales());


        Platform.runLater(()->{
            lireBtn.setText("Lire évolution ("
                    +occPartition.getAnneeDebut()+"-"+occPartition.getAnneeFin()+")");
            lireBtn.setDisable(false);
            globalBtn.setText("Occurrences globales ("
                    +occPartition.getAnneeDebut()+"-"+occPartition.getAnneeFin()+")");
            globalBtn.setDisable(true);
        });

    }


    public void attente() {
        setMode(MODE_ATTENTE);

        occPartition = null;

        Platform.runLater(()->{
            lireBtn.setText("Lire évolution ( - )");
            lireBtn.setDisable(true);
            globalBtn.setText("Occurrences globales ( - )");
            globalBtn.setDisable(true);
        });

    }



    public Taxon getEspece() throws AucuneOccsPartitionException {
        if(mode == MODE_ATTENTE) { throw new AucuneOccsPartitionException(parent, this); }

        return occPartition.getEspece();
    }


}
