package com.example.farmacy.controllers;

import com.example.farmacy.Main;
import com.example.farmacy.domain.Comanda;
import com.example.farmacy.domain.Medicament;
import com.example.farmacy.domain.PersonalMedical;
import com.example.farmacy.domain.Status;
import com.example.farmacy.repository.RepoException;
import com.example.farmacy.service.Service;
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

public class PersonalMedicalController implements Initializable {
    Service service;
    private PersonalMedical loggedPersonal =null;
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
    public void SetService(Service service, PersonalMedical personal) throws RepoException {
        this.service = service;
        this.loggedPersonal = personal;
        //init_lists();
        //initialize();

        try {
            listaAllMedicamente.setAll((List<Medicament>) service.findAllMedicamente());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            listaAllComenzi.setAll((List<Comanda>)
                    service.findAllComenzi());
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
        dataComandaColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
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
}
