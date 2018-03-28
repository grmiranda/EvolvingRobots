/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewController;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import view.RobotEvolution;

/**
 * FXML Controller class
 *
 * @author grmir
 */
public class ConfigurarEvolucaoController implements Initializable {

    /**
     * Initializes the controller class.
     */

    @FXML
    private JFXTextField geracoes;
    @FXML
    private JFXTextField individuos;
    @FXML
    private JFXTextField controlador;
    @FXML
    private JFXTextField repData;
    @FXML
    private JFXTextField variacao;
    @FXML
    private JFXRadioButton negativo;

    private MainController controller;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //inicializando o controller
        this.controller = new MainController();
    }

    @FXML
    private void continuar(ActionEvent event) {

        if( !this.geracoes.getText().equals("") &&
            !this.individuos.getText().equals("") &&
            !this.controlador.getText().equals("") &&
            !this.repData.getText().equals("") &&
            !this.variacao.getText().equals(""))
        {
            this.controller.setEvolveConfig(
                    Integer.parseInt(this.individuos.getText()), Integer.parseInt(this.geracoes.getText()),
                    Integer.parseInt(this.repData.getText()), Integer.parseInt(this.variacao.getText()),
                    this.controlador.getText());
            RobotEvolution.changeScreen("MainScreen");
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Verifique os campos");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos");
            alert.showAndWait();
        }

    }


    
}
