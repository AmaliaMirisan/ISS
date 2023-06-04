package com.example.farmacy.controllers;

import com.example.farmacy.Main;
import com.example.farmacy.domain.Comanda;
import com.example.farmacy.domain.Farmacist;
import com.example.farmacy.domain.Medicament;
import com.example.farmacy.domain.Sectie;
import com.example.farmacy.repository.RepoException;
import com.example.farmacy.service.Service;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FarmacistController implements Initializable {
    Service service;
    private Farmacist loggedFarmacist =null;
    @FXML
    private TableColumn<Medicament, Integer> cantitateMedicamentColumn;

    @FXML
    private TableColumn<Medicament, String> denumireMedicamentColumn;

    @FXML
    private Button logout;

    @FXML
    private TableView<Medicament> medicamenteTable;

    ObservableList<Medicament> listaAllMedicamente = FXCollections.observableArrayList();
    ObservableList<Comanda> listaComenziNeonorate = FXCollections.observableArrayList();

    @FXML
    private TableView<Comanda> comenziNeonorateTable;

    @FXML
    private TableColumn<Comanda, String> dataColumn;
    @FXML
    private TableColumn<Comanda, Sectie> denumireSectieColumn;

    @FXML
    private TableColumn<Comanda, Integer> nrComandaColumn;
    @FXML
    private TableColumn<Comanda,String> medicamentComandaColumn;
    @FXML
    private TableColumn<Comanda,Integer> cantitateColumn;


    public void SetService(Service service, Farmacist farmacist) throws RepoException {
        this.service = service;
        this.loggedFarmacist = farmacist;

        try {
            listaAllMedicamente.setAll((List<Medicament>) service.findAllMedicamente());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            listaComenziNeonorate.setAll((List<Comanda>) service.getComenziNeonorate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        denumireMedicamentColumn.setCellValueFactory(new PropertyValueFactory<>("denumire"));
        cantitateMedicamentColumn.setCellValueFactory(new PropertyValueFactory<>("cantitate"));


        medicamenteTable.setItems(listaAllMedicamente);

        nrComandaColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        denumireSectieColumn.setCellValueFactory(new PropertyValueFactory<>("sectie"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        cantitateColumn.setCellValueFactory(new PropertyValueFactory<>("cantitate"));
        medicamentComandaColumn.setCellValueFactory(cellData -> {
            Comanda comanda = cellData.getValue();
            Medicament medicament = comanda.getMedicament();
            if (medicament != null) {
                return new SimpleStringProperty(medicament.getDenumire());
            } else {
                return new SimpleStringProperty("");
            }
        });



        comenziNeonorateTable.setItems(listaComenziNeonorate);
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
