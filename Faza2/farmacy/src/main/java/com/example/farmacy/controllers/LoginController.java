package com.example.farmacy.controllers;


import com.example.farmacy.Main;
import com.example.farmacy.domain.PersonalMedical;
import com.example.farmacy.domain.Farmacist;
import com.example.farmacy.domain.Manager;
import com.example.farmacy.service.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {
    Service service;
    private Manager currentUser;
    private Farmacist currentPharmacist;
    private PersonalMedical currentMedicalStaff;
    @FXML
    private CheckBox manager;
    @FXML
    private CheckBox farmacist;
    @FXML
    private CheckBox personalMedical;
    @FXML
    private AnchorPane pane;
    @FXML
    private Button login;
        @FXML
    private TextField password;

    @FXML
    private TextField username;
    public void SetService(Service service) {
        this.service = service;
        pane.setVisible(true);
    }
    @FXML
    public void loginButton() {
        String user = username.getText();
        String parola = password.getText();
        boolean ok1=false, ok2=false, ok3=false;
        if(manager.isSelected() && !farmacist.isSelected() && !personalMedical.isSelected())
        {
            ok1=true;
            try {
                Manager trying = new Manager(user, parola);
                Object manag = service.loginManager(trying);
                if(manag==null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Datele introduse sunt incorecte!", ButtonType.OK);
                    alert.show();
                    return;

                }
                OpenManagerView();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.show();
            }

        }
        if(farmacist.isSelected() && !personalMedical.isSelected() && !manager.isSelected())
        {
            ok2=true;
            try {
                Farmacist trying = new Farmacist(user, parola);
                Object farma = service.loginFarmacist(trying);
                if(farma==null)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Datele introduse sunt incorecte!", ButtonType.OK);
                    alert.show();
                    return;
                }
                OpenFarmacieView();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.show();
            }
        }
        if(personalMedical.isSelected() && !farmacist.isSelected() && !manager.isSelected())
        {
            ok3=true;
            try {
                PersonalMedical trying = new PersonalMedical(user, parola, null);
                Object pers = service.loginPersonalMedical(trying);
                if(pers==null)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Datele introduse sunt incorecte!", ButtonType.OK);
                    alert.show();
                    return;
                }
                OpenSectieView();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.show();
            }
        }

        if(!(ok1 || ok2 || ok3)){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Selectati doar un tip de angajat!", ButtonType.OK);
            alert.show();
        }
    }
    private void OpenManagerView()
    {
        FXMLLoader loader=new FXMLLoader(Main.class.getResource("manager-view.fxml"));
        Scene scene;
        try{
            scene = new Scene(loader.load(), 700, 600);
            ManagerController controller = loader.getController();
            controller.SetService(service, currentUser);

            Stage currentStage=(Stage) login.getScene().getWindow();

            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.setTitle("Manager");
            currentStage.close();
            newStage.show();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void OpenFarmacieView()
    {
        FXMLLoader loader=new FXMLLoader(Main.class.getResource("farmacie-view.fxml"));
        Scene scene;
        try{
            scene = new Scene(loader.load(), 700, 600);
            FarmacistController controller = loader.getController();
            controller.SetService(service, currentPharmacist);

            Stage currentStage=(Stage) login.getScene().getWindow();

            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.setTitle("Farmacist");
            currentStage.close();
            newStage.show();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void OpenSectieView()
    {
        FXMLLoader loader=new FXMLLoader(Main.class.getResource("sectie-view.fxml"));
        Scene scene;
        try{
            scene = new Scene(loader.load(), 700, 600);
            PersonalMedicalController controller = loader.getController();
            controller.SetService(service, currentMedicalStaff);

            Stage currentStage=(Stage) login.getScene().getWindow();

            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.setTitle("Personal Medical");
            currentStage.close();
            newStage.show();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
