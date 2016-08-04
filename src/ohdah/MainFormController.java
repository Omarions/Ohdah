/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohdah;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ohdah.controller.cOhdah;
import ohdah.model.Ohdah;

/**
 *
 * @author Omar
 */
public class MainFormController implements Initializable {

    @FXML
    private TabPane mainTabPane;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnMinimize;
    @FXML
    private VBox vbDetails;

    private HBox hbIdInputs;
    private HBox hbNameInputs;
    private RadioButton rbID;
    private RadioButton rbName;

    private cOhdah cohdah;
    //ohdah which get from db according to ID or Name.
    private Ohdah ohdah;
    //constants for process so, create appropriate tab.
    private final static int NEW_OHDAH = 1;
    private final static int UPDATE_OHDAH = 2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            cohdah = new cOhdah();
        } catch (InstantiationException | IllegalAccessException | SQLException | ClassNotFoundException ex) {
            Alert alertConfirm = new Alert(Alert.AlertType.ERROR);
            alertConfirm.setContentText("There was an error while connecting to server!\n\nErrorMessage:\n" + ex);
            alertConfirm.setTitle("Connection Error...");
            alertConfirm.showAndWait();
        }
    }

    /**
     * The action listener of Close button to close the application
     *
     * @param event the click event
     */
    @FXML
    public void close(ActionEvent event) {
        Platform.exit();
    }

    /**
     * The action listener of minimize button to minimize the application to the
     * task-bar
     *
     * @param event the click event
     */
    @FXML
    public void minimize(ActionEvent event) {
        Stage stage = (Stage) btnMinimize.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Action listener for new ohdah button to create new tab
     *
     * @param event the click event
     */
    @FXML
    public void newOhdah(ActionEvent event) {
        mainTabPane.getTabs().add(newOhdahTabBuilder(new Ohdah(), "New...", NEW_OHDAH));
    }

    /**
     * Action listener for edit ohdah button to create new tab
     *
     * @param event the click event
     */
    @FXML
    public void editOhdah(ActionEvent event) {

        GridPane gridPane = new GridPane();
        double width = mainTabPane.getPrefWidth();
        double height = mainTabPane.getPrefHeight();

        gridPane.setPrefSize(width, height);
        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        for (int i = 0; i < 6; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(10);
            gridPane.getRowConstraints().add(row);
        }

        ColumnConstraints colLabels = new ColumnConstraints();
        ColumnConstraints colInputs = new ColumnConstraints();
        ColumnConstraints colCommands = new ColumnConstraints();

        colLabels.setMaxWidth(100);
        colInputs.setMaxWidth(100);
        colCommands.setMaxWidth(100);

        //Contorls of row1, 2 --------------------------------------------
        rbID = new RadioButton("Get By ID");
        rbID.setSelected(true);

        TextField tfID = new TextField();
        tfID.promptTextProperty().set("Employee ID");
        tfID.setPadding(new Insets(10, 10, 10, 10));
        tfID.setId("tfID");

        Button btnGetByID = new Button("Get");
        btnGetByID.setPadding(new Insets(10, 10, 10, 10));
        btnGetByID.setDefaultButton(true);
        btnGetByID.setOnAction((ActionEvent event1) -> {
            if (!tfID.getText().isEmpty()) {
                try {
                    Optional<Ohdah> opOhdah = cohdah.get(Integer.parseInt(tfID.getText()));
                    opOhdah.ifPresent((retOhdah) -> {
                        ohdah = retOhdah;
                        mainTabPane.getTabs().add(newOhdahTabBuilder(ohdah, "Edit...", 2));
                    });
                } catch (SQLException ex) {
                    Alert alertConfirm = new Alert(Alert.AlertType.ERROR);
                    alertConfirm.setContentText("Error in retrieving data from DB...\n" + ex.toString());
                    alertConfirm.setTitle("Server Data Error...");
                    alertConfirm.showAndWait();
                }
            } else {
                Alert alertConfirm = new Alert(Alert.AlertType.ERROR);
                alertConfirm.setContentText("Employee ID is required...");
                alertConfirm.setTitle("Required Data Error...");
                alertConfirm.showAndWait();

            }
        });

        hbIdInputs = new HBox(5);
        hbIdInputs.getChildren().addAll(tfID, btnGetByID);

        VBox vbID = new VBox(10);
        vbID.getChildren().addAll(hbIdInputs);

        gridPane.add(rbID, 0, 0, 2, 1);
        gridPane.add(vbID, 0, 1, 2, 2);
        //-------------------------------------------------------------

        //Controls of row3,4 --------------------------------------------
        rbName = new RadioButton("Get By Name");
        rbName.setSelected(false);

        TextField tfName = new TextField();
        tfName.promptTextProperty().set("Employee Name");
        tfName.setPadding(new Insets(10, 10, 10, 10));
        tfName.setId("tfName");

        Button btnGetByName = new Button("Get");
        btnGetByName.setPadding(new Insets(10, 10, 10, 10));
        btnGetByName.setDefaultButton(true);
        //btnGetByName.setDisable(true);
        btnGetByName.setOnAction((ActionEvent event1) -> {
            if (!tfName.getText().isEmpty()) {
                try {
                    Optional<Ohdah> opOhdah = cohdah.get(tfName.getText());
                    opOhdah.ifPresent((retOhdah) -> {
                        ohdah = retOhdah;
                        mainTabPane.getTabs().add(newOhdahTabBuilder(ohdah, "Edit...", UPDATE_OHDAH));
                    });
                } catch (SQLException ex) {
                    Alert alertConfirm = new Alert(Alert.AlertType.ERROR);
                    alertConfirm.setContentText("Error in retrieving data from DB...");
                    alertConfirm.setTitle("Server Data Error...");
                    alertConfirm.showAndWait();
                }
            } else {
                Alert alertConfirm = new Alert(Alert.AlertType.ERROR);
                alertConfirm.setContentText("Employee name is required...");
                alertConfirm.setTitle("Required Data Error...");
                alertConfirm.showAndWait();

            }
        });

        hbNameInputs = new HBox(5);
        hbNameInputs.getChildren().addAll(tfName, btnGetByName);
        hbNameInputs.setDisable(true);
        
        VBox vbName = new VBox(10);
        vbName.getChildren().addAll(hbNameInputs);

        gridPane.add(rbName, 0, 2, 2, 1);
        gridPane.add(vbName, 0, 3, 2, 2);
        //-------------------------------------------------------------

        //add change listener for select property of ID radio button, so
        //change the other options
        rbID.selectedProperty().addListener((new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (observable.getValue()) {
                    hbIdInputs.setDisable(false);
                    hbNameInputs.setDisable(true);
                    rbName.setSelected(false);
                } 
            }

        }));
        //add change listener for select property of name radio button, so
        //change the other options
        rbName.selectedProperty().addListener((new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (observable.getValue()) {
                    hbNameInputs.setDisable(false);
                    hbIdInputs.setDisable(true);
                    rbID.setSelected(false);
                } 
            }

        }));

        vbDetails.getChildren().add(gridPane);
    }

    /**
     * Build the tab for new vendor to be added to the main TabPane.
     *
     * @param Ohdah the ohdah to be displayed
     * @param title the title of the tab
     * @param process an integer to identify the process to be done (add or edit)
     * 
     * @return the tab for new vendor
     */
    private Tab newOhdahTabBuilder(Ohdah ohdah, String title, int process) {
        double width = mainTabPane.getPrefWidth();
        double height = mainTabPane.getPrefHeight();

        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(width, height);
        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        for (int i = 0; i < 9; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(10);
            gridPane.getRowConstraints().add(row);
        }

        System.out.println("Rows of Grid: "
                + gridPane.getRowConstraints().size());

        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        ColumnConstraints col4 = new ColumnConstraints();

        col1.setPercentWidth(15);
        col2.setPercentWidth(40);
        col3.setPercentWidth(15);
        col4.setPercentWidth(30);

        gridPane.getColumnConstraints().addAll(col1, col2, col3, col4);

        //controls for row1 --------------------------------------------
        Label lblID = new Label("ID");
        lblID.setPadding(new Insets(10, 10, 10, 10));
        lblID.setStyle("-fx-font-weight: bold");

        TextField tfID = new TextField();
        tfID.promptTextProperty().set("Employee Name");
        tfID.setPadding(new Insets(10, 10, 10, 10));
        tfID.setDisable(true);
        try {
            tfID.setText(String.valueOf(ohdah.getId()));
        } catch (NumberFormatException e) {
            tfID.setText("Auto Generated");
        }

        gridPane.add(lblID, 0, 0);
        gridPane.add(tfID, 1, 0);

        //controls for row2 -------------------------------------------
        Label lblDate = new Label("Date");
        lblDate.setPadding(new Insets(10, 10, 10, 10));
        lblDate.setStyle("-fx-font-weight: bold");

        DatePicker dpDate = new DatePicker(LocalDate.now());
        dpDate.promptTextProperty().set("Date");
        dpDate.setPadding(new Insets(10, 10, 10, 10));
        dpDate.setId("dpDate");
        dpDate.setValue(ohdah.getDate());

        gridPane.add(lblDate, 0, 1);
        gridPane.add(dpDate, 1, 1);
        //-------------------------------------------------------------

        //controls for row3 -------------------------------------------
        Label lblName = new Label("Name");
        lblName.setPadding(new Insets(10, 10, 10, 10));
        lblName.setStyle("-fx-font-weight: bold");

        TextField tfName = new TextField();
        tfName.promptTextProperty().set("Employee Name");
        tfName.setPadding(new Insets(10, 10, 10, 10));
        tfName.setId("tfName");
        tfName.setText(ohdah.getName());

        gridPane.add(lblName, 0, 2);
        gridPane.add(tfName, 1, 2, 2, 1);
        //-------------------------------------------------------------

        //controls for row4 -------------------------------------------
        Label lblAmount = new Label("Amount");
        lblAmount.setPadding(new Insets(10, 10, 10, 10));
        lblAmount.setStyle("-fx-font-weight: bold");

        TextField tfAmount = new TextField();
        tfAmount.promptTextProperty().set("Amount");
        tfAmount.setPadding(new Insets(10, 10, 10, 10));
        tfAmount.setId("tfAmount");
        tfAmount.setText(String.valueOf(ohdah.getAmount()));

        gridPane.add(lblAmount, 0, 3);
        gridPane.add(tfAmount, 1, 3, 1, 1);
        //------------------------------------------------------------

        //controls for row5,6,7 ------------------------------------------
        Label lblReason = new Label("Reason");
        lblReason.setPadding(new Insets(10, 10, 10, 10));
        lblReason.setStyle("-fx-font-weight: bold");

        TextArea taReason = new TextArea();
        taReason.promptTextProperty().set("Reason");
        taReason.setPadding(new Insets(10, 10, 10, 10));
        taReason.setId("taReason");
        taReason.setText(ohdah.getReason());

        gridPane.add(lblReason, 0, 4);
        gridPane.add(taReason, 1, 4, 4, 3);
        //-------------------------------------------------------------

        //Controls of row 9 -------------------------------------------
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setSpacing(10);

        Button btnSave = new Button("Save");
        btnSave.setPadding(new Insets(10, 10, 10, 10));
        btnSave.setOnAction((ActionEvent event) -> {
            System.out.println(event.getSource().toString() + " is clicked");

            Ohdah newOhdah = ohdahBuilder(tfName, tfAmount,
                    taReason);

            if (newOhdah != null) {
                newOhdah.setDate(dpDate.getValue());
                switch (process) {
                    case NEW_OHDAH:     //for insert new record
                        //save the representative to db.
                        SaveNewOhdah(newOhdah);
                        break;
                    case UPDATE_OHDAH:     //for update exist record
                        updateOhdah(newOhdah, Integer.parseInt(tfID.getText()));
                        break;
                }

            } else {
                Alert alertConfirm = new Alert(Alert.AlertType.ERROR);
                alertConfirm.setContentText("Fill all the fields of the record...");
                alertConfirm.setTitle("Required Data Error...");
                alertConfirm.showAndWait();
            }

        });

        Button btnCancel = new Button("Cancel");
        btnCancel.setPadding(new Insets(10, 10, 10, 10));
        btnCancel.setOnAction((ActionEvent event) -> {
            System.out.println(event.getSource().toString() + " is clicked");
            //close the tab.
            mainTabPane.getTabs().remove(
                    mainTabPane.getSelectionModel().getSelectedItem()
            );
        });

        Button btnPrint = new Button("Print");
        btnPrint.setPadding(new Insets(10, 10, 10, 10));
        btnPrint.setOnAction((ActionEvent event) -> {
            System.out.println(event.getSource().toString() + " is clicked");
            
        });
        
        hBox.getChildren().addAll(btnCancel, btnSave, btnPrint);

        gridPane.add(hBox, 3, 8);
        //-----------------------------------------------------------

        return new Tab(title, gridPane);
    }

    /**
     * To build new record of Ohdah from input controls
     *
     * @param inputs the input controls
     * @return the new record of Ohdah from input controls
     */
    private Ohdah ohdahBuilder(TextInputControl... inputs) {
        //create object to be returned
        Ohdah representative = new Ohdah();
        //loop on controls and check for data and assign each property of Ohdah
        //to its data from the appropriate control
        for (TextInputControl input : inputs) {
            //get the text entered in the control
            String data = input.getText();
            //check for each control and assign the its data to the appropriate 
            //property of the representative.
            switch (input.getId()) {

                case "tfName":
                    if (!data.isEmpty()) {
                        representative.setName(data);
                        break;
                    } else {
                        Alert alertConfirm = new Alert(Alert.AlertType.ERROR);
                        alertConfirm.setContentText("Employee name is required...");
                        alertConfirm.setTitle("Required Data Error...");
                        alertConfirm.showAndWait();
                        break;
                    }
                case "tfAmount":
                    if (!data.isEmpty()) {
                        try {
                            representative.setAmount(Double.parseDouble(data));
                            break;
                        } catch (NumberFormatException e) {
                            Alert alertConfirm = new Alert(Alert.AlertType.ERROR);
                            alertConfirm.setContentText("Amount should be numbers only...");
                            alertConfirm.setTitle("Required Data Error...");
                            alertConfirm.showAndWait();
                            return null;
                        }
                    } else {
                        Alert alertConfirm = new Alert(Alert.AlertType.ERROR);
                        alertConfirm.setContentText("Amount is required...");
                        alertConfirm.setTitle("Required Data Error...");
                        alertConfirm.showAndWait();
                        return null;
                    }
                case "taReason":
                    if (!data.isEmpty()) {
                        representative.setReason(data);
                        break;
                    } else {
                        Alert alertConfirm = new Alert(Alert.AlertType.ERROR);
                        alertConfirm.setContentText("Reason is required...");
                        alertConfirm.setTitle("Required Data Error...");
                        alertConfirm.showAndWait();
                        return null;
                    }
            }

        }
        return representative;
    }

    /**
     * Save new record of Ohdah to db
     *
     * @param ohdah the new record to be saved
     */
    private void SaveNewOhdah(Ohdah ohdah) {
        int genID = cohdah.Add(ohdah);
        if (genID > -1) {
            Alert alertConfirm = new Alert(Alert.AlertType.INFORMATION);
            alertConfirm.setContentText("The new record is successfully save with ID( " + genID + " )...");
            alertConfirm.setTitle("Saving to DB...");
            alertConfirm.showAndWait();
        } else {
            Alert alertConfirm = new Alert(Alert.AlertType.ERROR);
            alertConfirm.setContentText("There was an error while saving the record to DB!");
            alertConfirm.setTitle("Saving Error...");
            alertConfirm.showAndWait();
        }

    }

    /**
     * update the record of Ohdah in db
     *
     * @param ohdah the new record to be saved
     * @param id the ohdah record id to be updated
     */
    private void updateOhdah(Ohdah newOhdah, int id) {
        if (id > 0) {
            try {
                int affectedRows = cohdah.update(newOhdah, id);
                if (affectedRows > 0) {
                    Alert alertConfirm = new Alert(Alert.AlertType.INFORMATION);
                    alertConfirm.setContentText("The record is saved successfully...");
                    alertConfirm.setTitle("Saving to DB...");
                    alertConfirm.showAndWait();
                } else {
                    Alert alertConfirm = new Alert(Alert.AlertType.ERROR);
                    alertConfirm.setContentText("There was an error while saving the record to DB!");
                    alertConfirm.setTitle("Saving Error...");
                    alertConfirm.showAndWait();
                }
            } catch (SQLException ex) {
                Alert alertConfirm = new Alert(Alert.AlertType.ERROR);
                alertConfirm.setContentText("There was an error while saving the record to DB!");
                alertConfirm.setTitle("Saving Error...");
                alertConfirm.showAndWait();
            }
        }
    }
}
