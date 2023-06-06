package com.example.farmacy.controllers;

import com.example.farmacy.Main;
import com.example.farmacy.domain.Manager;
import com.example.farmacy.domain.*;
import com.example.farmacy.repository.RepoException;
import com.example.farmacy.service.Service;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerController implements Initializable {
    Service service;
    private Manager loggedUser =null;
    @FXML
    private Button adaugaFarmacistButton;

    @FXML
    private Button adaugaMedicamentButton;
    @FXML
    private Button adaugaPersonalButton;
    @FXML
    private Button modificaFarmacistButton;

    @FXML
    private Button modificaMedicamentButton;

    @FXML
    private Button modificaPersonalButton;
    @FXML
    private Button stergeFarmacistButton;

    @FXML
    private Button stergeMedicamentButton;

    @FXML
    private Button stergePersonalButton;
    @FXML
    private TextField cantitateMedicamentField;
    @FXML
    private TextField denumireMedicamentField;

    @FXML
    private TextField utilizatorPersonalField;

    @FXML
    private TextField parolaPersonalField;
    @FXML
    private TextField utilizatorFarmacistField;
    @FXML
    private TextField parolaFarmacistField;

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
    @FXML
    private TableColumn<PersonalMedical, Sectie> sectiaPersonalColumn;

    @FXML
    private TextField sectiaPersonalField;

    ObservableList<Medicament> listaAllMedicamente = FXCollections.observableArrayList();
    ObservableList<Farmacist> listaAllFarmacisti = FXCollections.observableArrayList();
    ObservableList<PersonalMedical> listaAllPersonalMedical = FXCollections.observableArrayList();
    public void SetService(Service service, Manager user) throws RepoException {
        this.service = service;
        this.loggedUser = user;


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
        sectiaPersonalColumn.setCellValueFactory((new PropertyValueFactory<>("sectie")));

        personalTable.setItems(listaAllPersonalMedical);


    }
    public void init(){
        listaAllPersonalMedical.setAll((List<PersonalMedical>) service.findAllPersonalMedical());
        listaAllFarmacisti.setAll((List<Farmacist>) service.findAllFarmacisti());
        listaAllMedicamente.setAll((List<Medicament>) service.findAllMedicamente());

        denumireColumn.setCellValueFactory(new PropertyValueFactory<>("denumire"));
        cantitateColumn.setCellValueFactory(new PropertyValueFactory<>("cantitate"));

        medicamenteTable.setItems(listaAllMedicamente);


        utilizatorFarmacistColumn.setCellValueFactory((new PropertyValueFactory<>("username")));
        parolaFarmacistColumn.setCellValueFactory((new PropertyValueFactory<>("password")));

        farmacistiTable.setItems(listaAllFarmacisti);

        utilizatorPersonalColumn.setCellValueFactory((new PropertyValueFactory<>("username")));
        parolaPersonalColumn.setCellValueFactory((new PropertyValueFactory<>("password")));
        sectiaPersonalColumn.setCellValueFactory((new PropertyValueFactory<>("sectie")));


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


//-------------------------------MEDICAMENTE-------------------------------//
    public void adaugaMedicament(javafx.event.ActionEvent actionEvent) throws RepoException{
        if(denumireMedicamentField.getText().isEmpty() || cantitateMedicamentField.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Completati toate campurile!", ButtonType.OK);
            alert.show();
        }
        else {

            try {
                String denumire = denumireMedicamentField.getText();

                Integer cantitate = Integer.parseInt(cantitateMedicamentField.getText());

                service.addMedicament(denumire, cantitate);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Medicament adaugat cu succes!", ButtonType.OK);
                alert.show();
                init();
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
    }

    public void modificaMedicament(ActionEvent actionEvent) {
        Medicament selectedMedicament = medicamenteTable.getSelectionModel().getSelectedItem();
        if (selectedMedicament != null) {
            try {
                String denumire;
                Integer cantitate;

                if(denumireMedicamentField.getText().isEmpty()){denumire = selectedMedicament.getDenumire();}
                else{denumire = denumireMedicamentField.getText();}

                if(cantitateMedicamentField.getText().isEmpty()){cantitate = selectedMedicament.getCantitate();}
                else {cantitate = Integer.parseInt(cantitateMedicamentField.getText());}

                selectedMedicament.setDenumire(denumire);
                selectedMedicament.setCantitate(cantitate);

                service.updateMedicament(selectedMedicament);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Medicament modificat cu succes!", ButtonType.OK);
                alert.show();
                init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Alegeți un medicament!", ButtonType.OK);
            alert.show();
        }
    }

    public void stergeMedicament(ActionEvent actionEvent) {
        Medicament selectedMedicament = medicamenteTable.getSelectionModel().getSelectedItem();
        if (selectedMedicament != null) {
            try {
                service.deleteMedicament(selectedMedicament);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Medicament șters cu succes!", ButtonType.OK);
                alert.show();
                init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Alegeți un medicament!", ButtonType.OK);
            alert.show();
        }
    }

//-------------------------------FARMACISTI-------------------------------//
    public void adaugaFarmacist(javafx.event.ActionEvent actionEvent) throws RepoException{
        if(utilizatorFarmacistField.getText().isEmpty() || parolaFarmacistField.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Completati toate campurile!", ButtonType.OK);
            alert.show();
        }
        else {

            try {
                String utilizator = utilizatorFarmacistField.getText();
                String parola = parolaFarmacistField.getText();


                service.addFarmacist(utilizator, parola);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Farmacist adaugat cu succes!", ButtonType.OK);
                alert.show();
                init();
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
    }

    public void modificaFarmacist(ActionEvent actionEvent) {
        Farmacist selectedFarmacist = farmacistiTable.getSelectionModel().getSelectedItem();
        if (selectedFarmacist != null) {
            try {
                String utilizator, parola;
                if(utilizatorFarmacistField.getText().isEmpty()){utilizator = selectedFarmacist.getUsername();}
                else{utilizator = utilizatorFarmacistField.getText();}

                if(parolaFarmacistField.getText().isEmpty()){parola = selectedFarmacist.getPassword();}
                else {parola = parolaFarmacistField.getText();}

                selectedFarmacist.setUsername(utilizator);
                selectedFarmacist.setPassword(parola);

                service.updateFarmacist(selectedFarmacist);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Farmacist modificat cu succes!", ButtonType.OK);
                alert.show();
                init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Alegeți un farmacist!", ButtonType.OK);
            alert.show();
        }
    }

    public void stergeFarmacist(ActionEvent actionEvent) {
        Farmacist selectedFarmacist = farmacistiTable.getSelectionModel().getSelectedItem();
        if (selectedFarmacist != null) {
            try {
                service.deleteFarmacist(selectedFarmacist);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Farmacist șters cu succes!", ButtonType.OK);
                alert.show();
                init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Alegeți un farmacist!", ButtonType.OK);
            alert.show();
        }
    }


//-------------------------------PERSONAL-------------------------------//
    public void adaugaPersonal(javafx.event.ActionEvent actionEvent) throws RepoException{
        if(utilizatorPersonalField.getText().isEmpty() || parolaPersonalField.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Completati toate campurile!", ButtonType.OK);
            alert.show();
        }
        else {

            try {
                String utilizator = utilizatorPersonalField.getText();
                String parola = parolaPersonalField.getText();
                String sectieText = sectiaPersonalField.getText();
                Sectie sectia = Sectie.valueOf(sectieText);

                service.addPersonalMedical(utilizator, parola, sectia);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Personal medical adaugat cu succes!", ButtonType.OK);
                alert.show();
                init();
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
    }

    public void modificaPersonal(ActionEvent actionEvent) {
        PersonalMedical selectedPersonal = personalTable.getSelectionModel().getSelectedItem();
        if (selectedPersonal != null) {
            try {
                String utilizator, parola;
                Sectie sectia;
                if(utilizatorPersonalField.getText().isEmpty()){utilizator = selectedPersonal.getUsername();}
                else{utilizator = utilizatorPersonalField.getText();}

                if(parolaPersonalField.getText().isEmpty()){parola = selectedPersonal.getPassword();}
                else {parola = parolaPersonalField.getText();}

                if(sectiaPersonalField.getText().isEmpty()){sectia = selectedPersonal.getSectie();}
                else {String sectieText = sectiaPersonalField.getText();
                     sectia = Sectie.valueOf(sectieText);}

                selectedPersonal.setUsername(utilizator);
                selectedPersonal.setPassword(parola);
                selectedPersonal.setSectie(sectia);

                service.updatePersonalMedical(selectedPersonal);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Personal medical modificat cu succes!", ButtonType.OK);
                alert.show();
                init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Alegeți un personal medical!", ButtonType.OK);
            alert.show();
        }
    }

    public void stergePersonal(ActionEvent actionEvent) {
        PersonalMedical selectedPersonal = personalTable.getSelectionModel().getSelectedItem();
        if (selectedPersonal != null) {
            try {
                service.deletePersonalMedical(selectedPersonal);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Personal medical șters cu succes!", ButtonType.OK);
                alert.show();
                init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Alegeți un personal medical!", ButtonType.OK);
            alert.show();
        }
    }
}
