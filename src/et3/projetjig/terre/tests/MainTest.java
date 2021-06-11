package et3.projetjig.terre.tests;

import et3.projetjig.terre.CadreTerre;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        CadreTerre cadreTerre = new CadreTerre(500,500);
        Pane content = cadreTerre;

        primaryStage.setTitle("La Terre (Composant 3D seul)");
        primaryStage.setScene( new Scene(content) );
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
