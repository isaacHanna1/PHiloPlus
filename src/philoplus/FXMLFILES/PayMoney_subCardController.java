/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package philoplus.FXMLFILES;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import philoplus.philoPlusClasses.Database;
import philoplus.philoPlusClasses.Payed;

/**
 * FXML Controller class
 *
 * @author Seha
 */
public class PayMoney_subCardController implements Initializable {

    @FXML
    private TextField txt_details;
    @FXML
    private DatePicker dataPicker;
    @FXML
    private TextField txt_valuePayed;
    @FXML
    private Label lbl_idOfPayed;
    @FXML
    private Label lbl_idOfSon;
    @FXML
    private ComboBox<String> combo_sonAccount;
    @FXML
    private Button btn_add;
    @FXML
    private HBox add_btn;
    @FXML
    private Button edit_btn;
    @FXML
    private Button delete_btn;
    @FXML
    private TableView<Payed> table_payedFor;
    @FXML
    private TableColumn<Payed, Integer> co_id;
    @FXML
    private TableColumn<Payed, String> col_details;
    @FXML
    private TableColumn<Payed, Float> col_value;
    @FXML
    private TableColumn<Payed, Date> col_date;
    @FXML
    private TableColumn<Payed, String> col_sonAccount;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          co_id.setCellValueFactory(new PropertyValueFactory<>("id"));
          col_details.setCellValueFactory(new PropertyValueFactory<>("payedDetails"));
          col_value.setCellValueFactory(new PropertyValueFactory<>("payedValue"));
          col_date.setCellValueFactory(new PropertyValueFactory<>("payedDate"));
          col_sonAccount.setCellValueFactory(new PropertyValueFactory<>("sonAccountName"));
          loadAllPerviousPayement();
          loadingAllSounAccout();
           combo_sonAccount.valueProperty().addListener((obs, oldVal, newVal) -> {
               int id =  Database.gettingSonAccountId(newVal);
               lbl_idOfSon.setText(id+"");
               
          });
          
    }    

    @FXML
    private void addPayedFor(ActionEvent event) {
        
        try {
            int id = Database.autoNumber("`pay-for`","id");
            String details = txt_details.getText();
            Date paymentDate = Date.valueOf(dataPicker.getValue());
            float paymentValue = Float.parseFloat(txt_valuePayed.getText());
            int sonAccountId = Integer.parseInt(lbl_idOfSon.getText());
            Payed p = new Payed(id, sonAccountId, paymentValue, paymentDate, details);
            int isInserted = Database.insertNewPayFor(p);
            if(isInserted > 0 ){
                Database.alertInformation("عملية ناجحة");
                loadAllPerviousPayement();
            }
           } catch (SQLException ex) {
               Database.alertMessage(ex.getMessage());
        }
    }

    @FXML
    private void editPayFor(ActionEvent event) {
            try {
            int id = Integer.parseInt(lbl_idOfPayed.getText());
            String details = txt_details.getText();
            Date paymentDate = Date.valueOf(dataPicker.getValue());
            float paymentValue = Float.parseFloat(txt_valuePayed.getText());
            int sonAccountId = Integer.parseInt(lbl_idOfSon.getText());
            Payed p = new Payed(id, sonAccountId, paymentValue, paymentDate, details);
            int isEdit = Database.editPayedForRecord(p);
            if(isEdit > 0 ){
                Database.alertInformation("عملية ناجحة");
                loadAllPerviousPayement();
            }
           } catch (SQLException ex) {
               Database.alertMessage(ex.getMessage());
        }
    }

    @FXML
    private void deletePayFor(ActionEvent event) {
        try {
            int payedId = Integer.parseInt(lbl_idOfPayed.getText());
            int isDeleted = Database.deletePayedForRecord(payedId);
            if(isDeleted>0){
                Database.alertInformation("عملية ناجحة");
                loadAllPerviousPayement();
            }
        } catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());
        }
    }
    
    public void selectRecordToTxt(){
        
        Payed selected = table_payedFor.getSelectionModel().getSelectedItem();
        lbl_idOfPayed.setText(selected.getId()+"");
        txt_details.setText(selected.getPayedDetails());
        dataPicker.setValue(selected.getPayedDate().toLocalDate());
        txt_valuePayed.setText(selected.getPayedValue()+"");
    }
    public void loadingAllSounAccout(){
        try {
            ResultSet rs =  Database.getttingAllSounAccount();
            while (rs.next()) {
                String AccountName = rs.getString(1);
                combo_sonAccount.getItems().add(AccountName);
                        }
        } catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());
        }
    }
    public void loadAllPerviousPayement(){
        table_payedFor.getItems().clear();
        try {
            ResultSet rs = Database.selectAllPayedFor();
            while (rs.next()) {                
                int id = rs.getInt(1);
                String Details = rs.getString(2);
                Date payementDate = rs.getDate(3);
                float value = rs.getFloat(4);
                String sonAccountName = rs.getString(5);
                Payed p = new Payed(id, value, payementDate, Details, sonAccountName);
                table_payedFor.getItems().add(p);
                
                
            }
        } catch (SQLException ex) {
           Database.alertMessage(ex.getMessage());
        }
    }
}
