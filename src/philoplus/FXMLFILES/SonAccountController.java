/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package philoplus.FXMLFILES;

import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import philoplus.philoPlusClasses.Database;
import philoplus.philoPlusClasses.Son;

/**
 * FXML Controller class
 *
 * @author Seha
 */
public class SonAccountController implements Initializable {

    @FXML
    private TextField txt_sonAccountDetails;
    @FXML
    private Label lbl_sonID;
    @FXML
    private Label lbl_fatherID;
    @FXML
    private ComboBox<String> combo_fatherAccount;
    @FXML
    private Button btn_add;
    @FXML
    private HBox add_btn;
    @FXML
    private Button edit_btn;
    @FXML
    private Button delete_btn;
    @FXML
    private TableView<Son> table_son;
    @FXML
    private TableColumn<Son, Integer> co_id;
    @FXML
    private TableColumn<Son, Integer> col_father;
    @FXML
    private TableColumn<Son, String> col_son;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           
          co_id.setCellValueFactory(new PropertyValueFactory<>("id"));
          col_father.setCellValueFactory(new PropertyValueFactory<>("fatherAccountName"));
          col_son.setCellValueFactory(new PropertyValueFactory<>("sonAccount"));
          
          loadAllFatherAccounts();
          combo_fatherAccount.valueProperty().addListener((obs, oldVal, newVal) -> {
              gettingFatherAccountId();
              loadAllPervouisSonAccounts();
          });
    }    

    @FXML
    private void addSonAccount() {
        try {
            int id = Database.autoNumber("son", "id");
            int fatherAccountID = Integer.parseInt(lbl_fatherID.getText());
            String sonAccount = txt_sonAccountDetails.getText();
            Son s = new Son(id, fatherAccountID, sonAccount);
            int isInserted = Database.insertNewSonAccount(s);
            if(isInserted > 0){
                Database.alertInformation("عملية ناجحة ");
                loadAllPervouisSonAccounts();
            }
        }catch (SQLException ex) {
            if(ex.getSQLState().startsWith("23")){
                Database.alertInformation("هذا البند مضاف  ");
            }else{
            Database.alertMessage(ex.getMessage());
            }
        
    }
    }

    @FXML
    private void editSonAccount() {
        
         try {
            int id = Integer.parseInt(lbl_sonID.getText());
            int fatherAccountID = Integer.parseInt(lbl_fatherID.getText());
            String sonAccount = txt_sonAccountDetails.getText();
            Son s = new Son(id, fatherAccountID, sonAccount);
            int isUpdated = Database.updateSonAccount(s);
            if(isUpdated > 0){
                Database.alertInformation("عملية ناجحة ");
                loadAllPervouisSonAccounts();
            }
        }catch (SQLException ex) {
            if(ex.getSQLState().startsWith("23")){
                Database.alertInformation("هذا البند مضاف  ");
            }else{
            Database.alertMessage(ex.getMessage());
            }
        
    }
    }

    @FXML
    private void deleteAccount() {
        
        try {
            int sonId = Integer.parseInt(lbl_sonID.getText());
            int isDeleted = Database.deleteSonAccount(sonId);
            if(isDeleted >0){
            Database.alertInformation("عملية ناجحة ");
            loadAllPervouisSonAccounts();
                   }
            
        } catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());
        }
    }

    @FXML
    private void selectRecordToTxt() {

        Son s = table_son.getSelectionModel().getSelectedItem();
        lbl_sonID.setText(s.getId()+"");
        txt_sonAccountDetails.setText(s.getSonAccount());
        combo_fatherAccount.getSelectionModel().select(s.getFatherAccountName());
    }
    
    public  void loadAllPervouisSonAccounts(){  
        table_son.getItems().clear();
        try {
            int fatherId = Integer.parseInt(lbl_fatherID.getText());
            ResultSet rs = Database.loadAllSonAccount(fatherId);
            while (rs.next()) {                
                int id = rs.getInt(1);
                String fatherAccountName = rs.getString(2);
                String sonAccountName = rs.getString(3);
                Son s = new Son(id, sonAccountName, fatherAccountName);
                table_son.getItems().add(s);
            }
        } catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());
        }
    }
    public void loadAllFatherAccounts(){
        try{
            ResultSet rs = Database.loaddingAllFatherAccounts();
             while (rs.next()) {                
                combo_fatherAccount.getItems().add(rs.getString(1));
            }
        }catch(SQLException ex)
        {
            Database.alertMessage(ex.getMessage());
        }
    }
    
    public void gettingFatherAccountId(){
        try {
            String fatherAccountName = combo_fatherAccount.getSelectionModel().getSelectedItem();
            int fatherId = Database.gettingFatherId(fatherAccountName);
            lbl_fatherID.setText(fatherId+"");
        } catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());            
        }
    }
    
}
