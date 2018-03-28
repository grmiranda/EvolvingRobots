package viewController;

import com.jfoenix.controls.JFXTextArea;
import controller.MainController;
import exception.ConnectErrorException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;


import java.net.URL;
import java.util.ResourceBundle;

public class ConfigurarEvolutionPrincipal implements Initializable {

    @FXML
    private JFXTextArea consoleLog;
    @FXML
    private Label contadorGeracao;
    @FXML
    private ProgressBar progressBar;

    public static Label publicContadorGeracao;
    public static JFXTextArea publicConsoleLog;
    private MainController controller;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //inicializando o controller
        this.controller = new MainController();
        publicConsoleLog = this.consoleLog;
        publicContadorGeracao = this.contadorGeracao;
    }

    private void setProgress(Double progress){
        this.progressBar.setProgress(progress);
    }

    private void setGeneration(int generation){
        this.contadorGeracao.setText(Integer.toString(generation));
    }

    public static void consoleLogPrint(String text){
        publicConsoleLog.setText(publicConsoleLog.getText() + text + "\n");
    }

    public static void changeContadorGeração(int geracao){
        publicContadorGeracao.setText(Integer.toString(geracao));
    }

    @FXML
    private void iniciar(ActionEvent event) {
        try {
            if(this.controller.connect()){
                ConfigurarEvolutionPrincipal.consoleLogPrint("Simuladores conectados");
            }
            // inicia o processo de evolução
            this.controller.start();


        } catch (ConnectErrorException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Não foi possível se conectar com o(s) simlador(es)");
            alert.showAndWait();
        }
    }
}
