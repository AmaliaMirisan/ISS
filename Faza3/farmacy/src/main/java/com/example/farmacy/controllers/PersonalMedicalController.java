package com.example.farmacy.controllers;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.example.farmacy.Main;
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

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.StreamSupport;

public class PersonalMedicalController implements Initializable {
    Service service;
    private PersonalMedical loggedPersonal ;
    @FXML
    private TextField idMedicamentField;
    @FXML
    private Spinner<Integer> cantitateMedicamentField;
    int currentValue;

    @FXML
    public Button logout;
    ObservableList<Medicament> listaAllMedicamente = FXCollections.observableArrayList();
    ObservableList<Comanda> listaAllComenzi = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Medicament, Integer> cantitateMedicamentColumn;

    @FXML
    private TableColumn<Medicament, String> denumireMedicamentColumn;
    @FXML
    private TableView<Medicament> medicamenteTable;

    @FXML
    private TableView<Comanda> comenziTable;

    @FXML
    private TableColumn<Comanda, String> dataComandaColumn;

    @FXML
    private TableColumn<Comanda, Integer> nrComandaColumn;

    @FXML
    private TableColumn<Comanda, Status> statusComandaColumn;
    @FXML
    private TableColumn<Comanda, Integer> cantitateComandaColumn;

    @FXML
    private TableColumn<Comanda, Sectie> sectieComandaColumn;
    @FXML
    private TableColumn<Comanda, String> medicamentComandaColumn;
    @FXML
    private TableColumn<Medicament, String> idMedicamentColumn;
    public void SetService(Service service, PersonalMedical personal) throws RepoException {
        this.service = service;
        this.loggedPersonal = personal;


        try {
            listaAllMedicamente.setAll((List<Medicament>) service.findAllMedicamente());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            listaAllComenzi.setAll((List<Comanda>)
                    service.getComenziSectie(loggedPersonal));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,1000) ;
        valueFactory.setValue(1);
        cantitateMedicamentField.setValueFactory(valueFactory);

        idMedicamentColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        denumireMedicamentColumn.setCellValueFactory(new PropertyValueFactory<>("denumire"));
        cantitateMedicamentColumn.setCellValueFactory(new PropertyValueFactory<>("cantitate"));

        medicamenteTable.setItems(listaAllMedicamente);

        nrComandaColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dataComandaColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        statusComandaColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        medicamentComandaColumn.setCellValueFactory(cellData -> {
            Comanda comanda = cellData.getValue();
            Medicament medicament = comanda.getMedicament();
            if (medicament != null) {
                return new SimpleStringProperty(medicament.getDenumire());
            } else {
                return new SimpleStringProperty("");
            }
        });
        cantitateComandaColumn.setCellValueFactory(new PropertyValueFactory<>("cantitate"));
        sectieComandaColumn.setCellValueFactory(new PropertyValueFactory<>("sectie"));
        statusComandaColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        comenziTable.setItems(listaAllComenzi);

    }
    public void init(){
        listaAllComenzi.setAll((List<Comanda>)
                service.findAllComenzi());
        listaAllComenzi.setAll((List<Comanda>)
                service.getComenziSectie(loggedPersonal));

        idMedicamentColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        denumireMedicamentColumn.setCellValueFactory(new PropertyValueFactory<>("denumire"));
        cantitateMedicamentColumn.setCellValueFactory(new PropertyValueFactory<>("cantitate"));

        medicamenteTable.setItems(listaAllMedicamente);

        nrComandaColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dataComandaColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        statusComandaColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        medicamentComandaColumn.setCellValueFactory(cellData -> {
            Comanda comanda = cellData.getValue();
            Medicament medicament = comanda.getMedicament();
            if (medicament != null) {
                return new SimpleStringProperty(medicament.getDenumire());
            } else {
                return new SimpleStringProperty("");
            }
        });
        cantitateMedicamentColumn.setCellValueFactory(new PropertyValueFactory<>("cantitate"));
        sectieComandaColumn.setCellValueFactory(new PropertyValueFactory<>("sectie"));
        statusComandaColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        comenziTable.setItems(listaAllComenzi);
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

    public void plaseazaComanda(ActionEvent actionEvent) {
        if(idMedicamentField.getText().isEmpty() || cantitateMedicamentField.getValue() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Completati toate campurile!", ButtonType.OK);
            alert.show();
        }
        else{
            try {
                Integer idDeCautat = Integer.parseInt(idMedicamentField.getText());
                Iterable<Medicament> meds = service.findAllMedicamente();

                boolean ok = false;

                for (Medicament medicament : meds) {
                    if (medicament.getId() == idDeCautat) {
                        ok = true;
                        break;
                    }
                }

                if(ok == true){
                    Integer cantitate = cantitateMedicamentField.getValue();

                    Iterable<Farmacist> farmacisti = service.findAllFarmacisti();
                    Farmacist primulFarmacist = StreamSupport.stream(farmacisti.spliterator(), false)
                            .findFirst()
                            .orElse(null);

                    LocalDate dataActuala = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    String dataString = dataActuala.format(formatter);

                    Integer personalId = null;
                    Object pers = service.loginPersonalMedical(loggedPersonal);
                    if (pers instanceof PersonalMedical) {
                        PersonalMedical p = (PersonalMedical) pers;
                        personalId = p.getId();
                    }
                    Comanda newComanda = new Comanda(personalId, primulFarmacist.getId(),idDeCautat, cantitate, dataString, loggedPersonal.getSectie(),Status.NEONORATA);
                    service.addComanda(newComanda);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Comanda adaugata cu succes!", ButtonType.OK);
                    alert.show();
                    init();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Medicamentul cu ID-ul dat nu exista!", ButtonType.OK);
                    alert.show();
                }


            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }
}
