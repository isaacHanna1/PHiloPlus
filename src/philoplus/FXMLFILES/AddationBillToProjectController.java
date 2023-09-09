
package philoplus.FXMLFILES;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import philoplus.philoPlusClasses.AddationValueToLift;
import philoplus.philoPlusClasses.Database;

/**
 * FXML Controller class
 *
 * @author Seha
 */
public class AddationBillToProjectController implements Initializable {

    @FXML
    private ComboBox<String> combo_liftNumber;
    @FXML
    private TextField txt_liftType;
    @FXML
    private TextField txt_site;
    @FXML
    private TextField txt_company;
    @FXML
    private DatePicker date_picker;
    @FXML
    private TextField txt_addationValue;
    @FXML
    private TextField txt_reasonforAddation;
    @FXML
    private Button add_btn;
    @FXML
    private Button edit_btn;
    @FXML
    private Button delete_btn;
    @FXML
    private Label lbl_lift_id;
    @FXML
    private Label lbl_addationId;
    @FXML
    private TableView   <AddationValueToLift> table_addation;
    @FXML
    private TableColumn<AddationValueToLift, Integer> col_id;
    @FXML
    private TableColumn<AddationValueToLift, Integer> col_liftNumber;
    @FXML
    private TableColumn<AddationValueToLift, Float> col_addationalValue;
    @FXML
    private TableColumn<AddationValueToLift, Date> col_addationDate;
    @FXML
    private TableColumn<AddationValueToLift, String> col_addation_reason;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
        col_id.setCellValueFactory(new PropertyValueFactory<>("billId"));
         col_liftNumber.setCellValueFactory(new PropertyValueFactory<>("liftNumber"));
         col_addationalValue.setCellValueFactory(new PropertyValueFactory<>("addedValue"));
         col_addation_reason.setCellValueFactory(new PropertyValueFactory<>("reasonOfAdding"));
         col_addationDate.setCellValueFactory(new PropertyValueFactory<>("billDate"));
         
         loadAllLiftNumberToComBoBox();
           combo_liftNumber.valueProperty().addListener((obs, oldVal, newVal) -> {
              setLbl_lift_id(newVal);
              settingLabelData();
              gettingPerviousCostBillForLift();
               clearTXt();
           });
           
           
           table_addation.setOnKeyReleased(event ->{
               if(event.getCode().toString().equals("UP") || event.getCode().toString().equals("DOWN")){
                   selectRecordOntableToTxt();
               }
               else if(event.getCode().toString().equals("ESCAPE")){
                   clearTXt();
                   table_addation.getItems().clear();
                   combo_liftNumber.requestFocus();
               }
           });
    }    
    
    
    public void insertNewAddCostBill(){
    
        try {
            int id = Database.autoNumber("`bill-add-cost`", "id");
            String liftnumber =combo_liftNumber.getSelectionModel().getSelectedItem();
            int liftId = Database.gettingLiftIdFromOurLiftByLiftNumber(liftnumber);
            float addedValue = Float.parseFloat(txt_addationValue.getText());
            String reasonForAdding = txt_reasonforAddation.getText();
            Date billDate = Date.valueOf(date_picker.getValue());
            AddationValueToLift add = new AddationValueToLift(addedValue,reasonForAdding, id, liftId, billDate);
            int isInserted = Database.insertNewAddationValue(add);
            if(isInserted>0){
                Database.alertInformation("عملية ناجحة");
                gettingPerviousCostBillForLift();
            }
        }catch(IllegalArgumentException ex){ // i thrown exception in constructor method  of campany class if variable comapnyName is empty 
            Database.alertMessage("لا يمكن ان يكون حقل اسم المنطقة فارغ");
        } catch(SQLIntegrityConstraintViolationException  ex){//this exception is thrown because i put constarint on area name col be unique 
            Database.alertMessage("هناك بيانات  مسجلة بتلك التفاصيل في قاعدة البيانات");
        }  catch (SQLException ex) {
            Database.alertInformation(ex.getMessage());
        }catch(NullPointerException ex){
            Database.alertInformation("تاكد من ادخال جميع الببيانات ");
        }   
    }
    
    public void deleteBill(){
    
        try{
            int id = Integer.parseInt(lbl_addationId.getText());
            int isDeleted = Database.deleteBillCost(id);
            if(isDeleted > 0 ){
            Database.alertInformation("عملية ناجحة ");
            gettingPerviousCostBillForLift();
            }
        }catch(SQLException ex){
                Database.alertMessage(ex.getMessage());
        }
    }
    public void editAddedBillCost(){
                try {
            int id = Integer.parseInt(lbl_addationId.getText());
            float addedValue = Float.parseFloat(txt_addationValue.getText());
            String reasonForAdding = txt_reasonforAddation.getText();
            Date billDate = Date.valueOf(date_picker.getValue());
            AddationValueToLift updated = new AddationValueToLift(addedValue, reasonForAdding, id,billDate);
            int isInserted = Database.editAddedBillCost(updated);
            if(isInserted>0){
                Database.alertInformation("عملية ناجحة");
                gettingPerviousCostBillForLift();
            }
        }catch(IllegalArgumentException ex){ // i thrown exception in constructor method  of campany class if variable comapnyName is empty 
            Database.alertMessage("لا يمكن ان يكون حقل اسم المنطقة فارغ");
        } catch(SQLIntegrityConstraintViolationException  ex){//this exception is thrown because i put constarint on area name col be unique 
            Database.alertMessage("هناك بيانات  مسجلة بتلك التفاصيل في قاعدة البيانات");
        }  catch (SQLException ex) {
            Database.alertInformation(ex.getMessage());
        }catch(NullPointerException ex){
            Database.alertInformation("تاكد من ادخال جميع الببيانات ");
        }  
            
    }
    public void  loadAllLiftNumberToComBoBox(){
        try {
            ResultSet rs = Database.gettingAllLiftNumber();
            while(rs.next()){
                combo_liftNumber.getItems().add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Database.alertInformation(ex.getMessage());
        }
}
          public void setLbl_lift_id(String liftNumber){
          try {
            int lift_id = Database.gettingLiftIdFromOurLiftByLiftNumber(liftNumber);
            lbl_lift_id.setText(lift_id+"");
          } catch (SQLException ex) {
         Database.alertMessage(ex.getMessage());
        }
    }
        
     public void settingLabelData(){
        try {
            int lift_id = Integer.parseInt(lbl_lift_id.getText());
            ResultSet rs = Database.gettingLiftDataByLiftId(lift_id);
            while (rs.next()) {                
                String liftNumber = rs.getString(1);
                String liftType = rs.getString(2);
//                int floorNumber = rs.getInt(3);
//                int wellNumber = rs.getInt(4);
//                String technicanOfLifts = "";
//                String TechncianNumbers = "";
//                ResultSet rsTecncians = Database.gettingAllTechnciansAssignedToLift(lift_id);
//                while (rsTecncians.next()) {                    
//                    technicanOfLifts += "( "+rsTecncians.getString(2)+" - "+rsTecncians.getString(3)+" )";
//                }
                String liftCampany = rs.getString(5);
                String liftSite = rs.getString(6);
                txt_site.setText(liftSite);
                txt_company.setText(liftCampany);
//                txt_floorNumber.setText(floorNumber+"");
                txt_liftType.setText(liftType);
//                txt_techncianName.setText(technicanOfLifts);
//                txt_wellNUmber.setText(wellNumber+"");
                date_picker.setValue(LocalDate.now());
            }
        }  catch (SQLException ex) {
         Database.alertMessage(ex.getMessage());
        }
    }
     
     public void gettingPerviousCostBillForLift(){
     
         table_addation.getItems().clear();
         try {
            int lift_id = Integer.parseInt(lbl_lift_id.getText());
            ResultSet rs = Database.gettingAddationalBillForEspecificBill(lift_id);
            while (rs.next()) {                
                int id = rs.getInt(1);
                String liftNum = rs.getString(2);
                float add_cost = rs.getFloat(3);
                String reason = rs.getString(4);
                Date date = rs.getDate(5);
                AddationValueToLift obj = new AddationValueToLift(add_cost, reason, liftNum, id, lift_id, date);
                table_addation.getItems().add(obj);
            }
        } catch (SQLException ex) {
            Database.alertInformation(ex.getMessage());
        }
     }
     
     public void selectRecordOntableToTxt(){
     
          AddationValueToLift selectedItem = table_addation.getSelectionModel().getSelectedItem();
          txt_addationValue.setText(selectedItem.getAddedValue()+"");
          txt_reasonforAddation.setText(selectedItem.getReasonOfAdding());
          lbl_addationId.setText(selectedItem.getBillId()+"");
     }
     
     public void clearTXt(){
     
         txt_addationValue.setText("");
         txt_reasonforAddation.setText("");
         lbl_addationId.setText("");
     }
}
