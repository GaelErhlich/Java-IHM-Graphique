package et3.projetjig.terre.tests;

import et3.projetjig.terre.CadreTerre;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FenetreDEBUG fenetre = new FenetreDEBUG();
        CadreTerre cadreTerre = new CadreTerre(500,500, fenetre);
        fenetre.setCadreTerre(cadreTerre);

        primaryStage.setTitle("La Terre (Composant 3D seul)");
        primaryStage.setScene( new Scene(cadreTerre) );
        primaryStage.show();




    }

    public static void main(String[] args) {
        launch(args);
    }

}
