package com.example.farmacy.controllers;

import com.example.farmacy.Main;
import com.example.farmacy.domain.Manager;
import com.example.farmacy.domain.*;
import com.example.farmacy.repository.RepoException;
import com.example.farmacy.service.Service;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerController implements Initializable {
    Service service;
    private Manager loggedUser =null;
    @FXML
    public Button logout;
    @FXML
    private TableColumn<Medicament, Integer> cantitateColumn;

    @FXML
    private TableColumn<Medicament, String> denumireColumn;
    @FXML
    private TableColumn<Farmacist, Integer> parolaFarmacistColumn;

    @FXML
    private TableColumn<PersonalMedical, String> parolaPersonalColumn;

    @FXML
    private TableColumn<Farmacist, String> utilizatorFarmacistColumn;

    @FXML
    private TableColumn<PersonalMedical, String> utilizatorPersonalColumn;
    @FXML
    private TableView<Medicament> medicamenteTable;
    @FXML
    private TableView<Farmacist> farmacistiTable;
    @FXML
    private TableView<PersonalMedical> personalTable;

    ObservableList<Medicament> listaAllMedicamente = FXCollections.observableArrayList();
    ObservableList<Farmacist> listaAllFarmacisti = FXCollections.observableArrayList();
    ObservableList<PersonalMedical> listaAllPersonalMedical = FXCollections.observableArrayList();
    public void SetService(Service service, Manager user) throws RepoException {
        this.service = service;
        this.loggedUser = user;
        //init_lists();
        //initialize();

        try {
            listaAllMedicamente.setAll((List<Medicament>) service.findAllMedicamente());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            listaAllFarmacisti.setAll((List<Farmacist>) service.findAllFarmacisti());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            listaAllPersonalMedical.setAll((List<PersonalMedical>) service.findAllPersonalMedical());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        denumireColumn.setCellValueFactory(new PropertyValueFactory<>("denumire"));
        cantitateColumn.setCellValueFactory(new PropertyValueFactory<>("cantitate"));

        medicamenteTable.setItems(listaAllMedicamente);


        utilizatorFarmacistColumn.setCellValueFactory((new PropertyValueFactory<>("username")));
        parolaFarmacistColumn.setCellValueFactory((new PropertyValueFactory<>("password")));

        farmacistiTable.setItems(listaAllFarmacisti);

        utilizatorPersonalColumn.setCellValueFactory((new PropertyValueFactory<>("username")));
        parolaPersonalColumn.setCellValueFactory((new PropertyValueFactory<>("password")));

        personalTable.setItems(listaAllPersonalMedical);
    }
    @FXML
    public void logoutButton(){
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene;
        try{
            scene = new Scene(loader.load(), 350, 300);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return;
        }
        LoginController controller=loader.getController();
        controller.SetService(service);
        Stage currentStage= (Stage) logout.getScene().getWindow();

        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.setTitle("login");
        currentStage.close();
        newStage.show();
    }
}
