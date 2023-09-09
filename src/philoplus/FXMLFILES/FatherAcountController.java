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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import philoplus.philoPlusClasses.Database;
import philoplus.philoPlusClasses.FatherAccount;

/**
 * FXML Controller class
 *
 * @author Seha
 */
public class FatherAcountController implements Initializable {

    @FXML
    private Label lbl_father_id;
    @FXML
    private TextField txt_fatherAccount;
    @FXML
    private Button add_btn;
    @FXML
    private Button edit_btn;
    @FXML
    private Button delete_btn;
    @FXML
    private TableView<FatherAccount> table_fatherAcount;
    @FXML
    private TableColumn<FatherAccount, Integer> col_id;
    @FXML
    private TableColumn<FatherAccount, String> col_fatherAccount;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
         col_fatherAccount.setCellValueFactory(new PropertyValueFactory<>("fatherAccountName"));
          loadAllfatherAccounts();
          table_fatherAcount.setOnKeyReleased(event ->{
              if(event.getCode().toString().equals("UP")||event.getCode().toString().equals("DOWN") ){
                  selectRecordToTxtBox();
                }
    });
          
    }    

    @FXML
    private void addFatherAccount(ActionEvent event) {
        try{
                int id= Database.autoNumber("`father-accounts`", "id");
                String accountName = txt_fatherAccount.getText();
                FatherAccount f = new FatherAccount(id, accountName);
                int isInserted = Database.insertNewFatherAccount(f);
                if(isInserted > 0){
                    Database.alertInformation("عملية ناجحة");
                    loadAllfatherAccounts();
                    txt_fatherAccount.setText("");
                }
        }catch(NullPointerException ex){
            Database.alertMessage("لا يمكن ان يكون اسم البند فارغ");
        }
        catch(SQLException ex){
         if(ex.getSQLState().startsWith("23")){
                Database.alertInformation("هذا البند موجود في قاعدة البيانات ");
            }else{
            Database.alertMessage(ex.getMessage());
            }
        }
    }

    @FXML
    private void editFatherAccount(ActionEvent event) {
        try{
                int id= Integer.parseInt(lbl_father_id.getText());
                String accountName = txt_fatherAccount.getText();
                FatherAccount f = new FatherAccount(id, accountName);
                int isUpdated = Database.editFatherAccount(f);
                if(isUpdated > 0){
                    Database.alertInformation("عملية ناجحة");
                    loadAllfatherAccounts();
                    txt_fatherAccount.setText("");
                }
        }catch(SQLException ex){
         if(ex.getSQLState().startsWith("23")){
                Database.alertInformation("هذاالبند موجود في قاعدة البيانات ");
            }else{
            Database.alertMessage(ex.getMessage());
            }
        
        }
    }

    @FXML
    private void deleteFatherAccount(ActionEvent event) {
        try{
        int id = Integer.parseInt(lbl_father_id.getText());
        int isDeleted = Database.deleteFatherAccount(id);
        if(isDeleted > 0 ){
            Database.alertInformation("عملية ناجحة");
            loadAllfatherAccounts();
        }
        }catch(SQLException ex){
        Database.alertMessage(ex.getMessage());
        }
    }

    @FXML
    private void selectRecordToTxtBox() {
        FatherAccount selected = table_fatherAcount.getSelectionModel().getSelectedItem();
        lbl_father_id.setText(selected.getId()+"");
        txt_fatherAccount.setText(selected.getFatherAccountName());
    }
    
    public void loadAllfatherAccounts(){
        table_fatherAcount.getItems().clear();
        try{
        ResultSet rs = Database.loadAllFatherAccounts();
            while (rs.next()) {                
                int id= rs.getInt(1);
                String accountName = rs.getString(2);
                FatherAccount f = new FatherAccount(id, accountName);
                table_fatherAcount.getItems().add(f);
            }
        }catch(SQLException ex){
            Database.alertMessage(ex.getMessage());
        }
    }
    
}
