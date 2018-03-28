/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * @author grmir
 */
public class RobotEvolution extends Application {

    private static Stage stage;

    private static Scene ambientConfigScene;
    private static Scene evolveConfigScene;
    private static Scene evolutionPrincipalScene;

    @Override
    public void start(Stage primarystage) throws Exception {
        stage = primarystage;

        Parent ambientConfig = FXMLLoader.load(getClass().getResource("AmbientConfig.fxml"));
        ambientConfigScene = new Scene(ambientConfig);

        Parent evolveConfig = FXMLLoader.load(getClass().getResource("EvolveConfig.fxml"));
        evolveConfigScene = new Scene(evolveConfig);

        Parent evolutionPrincipal = FXMLLoader.load(getClass().getResource("EvolutionPrincipal.fxml"));
        evolutionPrincipalScene = new Scene(evolutionPrincipal);

        primarystage.initStyle(StageStyle.UNIFIED);
        primarystage.setTitle("Robot Evolution");
        primarystage.setScene(ambientConfigScene);
        primarystage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static void changeScreen(String tela){
        switch(tela){
            case "Ambient":
                stage.setScene(ambientConfigScene);
                break;
            case "Evolve":
                stage.setScene(evolveConfigScene);
                break;
            case "MainScreen":
                stage.setScene(evolutionPrincipalScene);
                break;
        }
    }
    
}
