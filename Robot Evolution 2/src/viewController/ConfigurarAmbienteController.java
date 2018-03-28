/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewController;

import com.jfoenix.controls.JFXTextField;
import controller.MainController;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.RobotEvolution;

/**
 *
 * @author grmir
 */
public class ConfigurarAmbienteController implements Initializable {
    
    @FXML
    private TableView<Computer> table;
    @FXML
    private TableColumn<Computer, String> ipCollum;
    @FXML
    private TableColumn<Computer, String> portaCollum;
    @FXML
    private JFXTextField ip;
    @FXML
    private JFXTextField porta;
    
    private List<Computer> tableItens = new ArrayList<Computer>();

    private MainController controller;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //inicializando o controller 
        this.controller = new MainController();
        // configurando exibição de tabela
        ipCollum.setCellValueFactory(new PropertyValueFactory<Computer, String>("ip"));
        portaCollum.setCellValueFactory(new PropertyValueFactory<Computer, String>("porta"));
        ObservableList<Computer> itens = FXCollections.observableArrayList(tableItens);
        this.table.setItems(itens);
    }    

    @FXML
    private void adicionar(ActionEvent event) {
        if(!this.ip.getText().equals("") && !this.porta.getText().equals("")){
        tableItens.add(new Computer(this.ip.getText(), this.porta.getText()));
        ObservableList<Computer> itens = FXCollections.observableArrayList(tableItens);
        this.table.setItems(itens);
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Verifique os campos");
            alert.setHeaderText("Verifique os campos");
            alert.setContentText("Preencha corretamente os campos");
            alert.showAndWait();
        }
    }

    @FXML
    private void importarArquivo(ActionEvent event) {
        // Variavies locais
        Stage seletorStage = new Stage(); // Scene secundária para o seletor de arquivos
        File arquivo;
        
        // Selecionar o arquivo para importação
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir arquivo de importação");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        arquivo = fileChooser.showOpenDialog(seletorStage);
        
        //scanner para o arquivo
        try {
            Scanner scanner = new Scanner(arquivo);
            while (scanner.hasNext()) {
               String line = scanner.nextLine();
               String[] dado = line.split("-");
               tableItens.add(new Computer(dado[0], dado[1]));
            }
            scanner.close();
            ObservableList<Computer> itens = FXCollections.observableArrayList(tableItens);
            this.table.setItems(itens);
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro na Importação");
            alert.setHeaderText("Erro na Importação");
            alert.setContentText("Ocorreu um erro durante a importação do arquivo");
            alert.showAndWait();
        }
        
    }

    @FXML
    private void continuar(ActionEvent event) {
        String[] dados = new String[tableItens.size()];
        for (int i = 0; i < tableItens.size(); i++) {
            Computer aux = tableItens.get(i);
            dados[i] = aux.getIp() + ";" + aux.getPorta();
        }
        if(dados.length != 0){
            controller.setConnections(dados);
            RobotEvolution.changeScreen("Evolve");
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Informe as conexões");
            alert.setHeaderText(null);
            alert.setContentText("Informe, pelo menos, os dados de uma conexão");
            alert.showAndWait();
        }
    }

    @FXML
    private void limpar(ActionEvent event) {
        tableItens.clear(); //limpa a lista de maquinas 
        ObservableList<Computer> itens = FXCollections.observableArrayList(tableItens);
        this.table.setItems(itens);
    }

    @FXML
    private void salvar(ActionEvent event) {
        //salvar dados no arquivo de texto selecionado
    }

    
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class Computer {
 
        private final SimpleStringProperty ip;
        private final SimpleStringProperty porta;
 
        private Computer(String ip, String porta) {
            this.ip = new SimpleStringProperty(ip);
            this.porta = new SimpleStringProperty(porta);
        }
 
        public String getIp() {
            return ip.get();
        }
 
        public void setIp(String ip) {
            this.ip.set(ip);
        }
 
        public String getPorta() {
            return porta.get();
        }
 
        public void setPorta(String porta) {
            this.porta.set(porta);
        }
    }
}
