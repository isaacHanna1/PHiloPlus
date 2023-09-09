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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import philoplus.philoPlusClasses.BillPHilo_techncian;
import philoplus.philoPlusClasses.Database;
/**
 * FXML Controller class
 *
 * @author Seha
 */
public class BillFromCompanyToLiftController implements Initializable {

    @FXML
    private ComboBox<String> combo_liftNumber;
    @FXML
    private TextField txt_liftType;
    @FXML
    private DatePicker datePicker_bill_date;
    @FXML
    private TextField txt_techncianName;
    @FXML
    private TextField siteName;
    @FXML
    private TextField txt_company_name;
    @FXML
    private TextField txt_areaName;
    @FXML
    private TextField txt_floorNumber;
    @FXML
    private TextField txt_priced_floorNumber;
    @FXML
    private TextField txt_wellNUmber;
    @FXML
    private TextField txt_priceOfFloor;
    @FXML
    private TextField txt_total;
    @FXML
    private Button add_btn;
    @FXML
    private Button edit_btn;
    @FXML
    private Button delete_btn;
    @FXML
    private TableView<BillPHilo_techncian> table_bill;
    @FXML
    private TableColumn<BillPHilo_techncian, Integer> col_id;
    @FXML
    private TableColumn<BillPHilo_techncian, Integer> col_BillNumber;
    @FXML
    private TableColumn<BillPHilo_techncian,Date> col_BillDate;
    @FXML
    private TableColumn<BillPHilo_techncian, String> col_liftNum;
    @FXML
    private TableColumn<BillPHilo_techncian, String> col_liftType;
    @FXML
    private TableColumn<BillPHilo_techncian, String> col_techncianName;
    @FXML
    private TableColumn<BillPHilo_techncian, String> col_siteName;
    @FXML
    private TableColumn<BillPHilo_techncian, String> col_comapnyName;
    @FXML
    private TableColumn<BillPHilo_techncian, Integer> col_liftFloorNumber;
    @FXML
    private TableColumn<BillPHilo_techncian, Integer> col_pricedFloorNumber;
    @FXML
    private TableColumn<BillPHilo_techncian, Integer> col_wellNumber;
    @FXML
    private TableColumn<BillPHilo_techncian, Float> col_priceOfFloor;
    @FXML
    private TableColumn<BillPHilo_techncian, Float> col_total;

    
    @FXML
    private Label lbl_BillID;
    @FXML
    private Label lbl_idOfLift;
    
    private ObservableList<BillPHilo_techncian> allBill = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         //Start column intalisation of table view bill
            col_id.setCellValueFactory(new PropertyValueFactory<>("billId"));
            col_BillNumber.setCellValueFactory(new PropertyValueFactory<>("billNumber"));
            col_BillDate.setCellValueFactory(new PropertyValueFactory<>("billDate"));
            col_liftNum.setCellValueFactory(new PropertyValueFactory<>("liftNumber"));
            col_liftType.setCellValueFactory(new PropertyValueFactory<>("liftType"));
            col_techncianName.setCellValueFactory(new PropertyValueFactory<>("technciansName"));
            col_siteName.setCellValueFactory(new PropertyValueFactory<>("projectName"));
            col_comapnyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
            col_liftFloorNumber.setCellValueFactory(new PropertyValueFactory<>("numberOfFloor"));
            col_pricedFloorNumber.setCellValueFactory(new PropertyValueFactory<>("priced_floor_num"));
            col_wellNumber.setCellValueFactory(new PropertyValueFactory<>("numberOfWell"));
            col_priceOfFloor.setCellValueFactory(new PropertyValueFactory<>("priceOfFloor"));
            col_total.setCellValueFactory(new PropertyValueFactory<>("total")); 
         //End column intalisation of table view bill  
        
        loadAllLiftNumberToComboBox();
        loadAllBill();
        combo_liftNumber.requestFocus();
          //start event when user change comboBox item 
          combo_liftNumber.valueProperty().addListener((obs, oldVal, newVal) -> {
              setLbl_lift_id(newVal);
              settingLabelData();
//              for (int i = 0; i < allBill.size(); i++) {
//                  if(newVal.equals(allBill.get(i).getLiftNumber())){
//                     BillPHilo_techncian obj =   allBill.get(i);
//                      table_bill.getItems().clear();
//                      table_bill.getItems().add(obj);         
//                  }
//              }
        });
          //end event when user change comboBox item 
          
          table_bill.setOnKeyReleased((event)->{
              if(event.getCode().toString().equals("UP")||event.getCode().toString().equals("DOWN")){
                  setSelectedRecordToTxt();
              }else if(event.getCode().toString().equals("ESCAPE")){
                  loadAllBill();
          }
          });
              
    }    
    
    
    public void loadAllLiftNumberToComboBox(){
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
            lbl_idOfLift.setText(lift_id+"");
          } catch (SQLException ex) {
         Database.alertMessage(ex.getMessage());
        }
    
    }
          public void settingLabelData(){
        try {
            int lift_id = Integer.parseInt(lbl_idOfLift.getText());
            ResultSet rs = Database.gettingLiftDataByLiftId(lift_id);
            while (rs.next()) {                
                String liftNumber = rs.getString(1);
                String liftType = rs.getString(2);
                int floorNumber = rs.getInt(3);
                int wellNumber = rs.getInt(4);
                String technicanOfLifts = "";
                String TechncianNumbers = "";
                ResultSet rsTecncians = Database.gettingAllTechnciansAssignedToLift(lift_id);
                while (rsTecncians.next()) {                    
                    technicanOfLifts += "( "+rsTecncians.getString(2)+" - "+rsTecncians.getString(3)+" )";
                }
                String liftCampany = rs.getString(5);
                String liftSite = rs.getString(6);
                siteName.setText(liftSite);
                txt_company_name.setText(liftCampany);
                txt_floorNumber.setText(floorNumber+"");
                txt_liftType.setText(liftType);
                txt_techncianName.setText(technicanOfLifts);
                txt_wellNUmber.setText(wellNumber+"");
                datePicker_bill_date.setValue(LocalDate.now());
            }
        }  catch (SQLException ex) {
         Database.alertMessage(ex.getMessage());
        }
    }
   public void insertNewBill(){
   
        try {
            int id = Database.autoNumber("`bil-philo-technician`", "id"); 
            int lift_id = Integer.parseInt(lbl_idOfLift.getText());
            int billNumber = Database.autoNumber(" `bil-philo-technician` ", "bill_Num");
            Date billDate = Date.valueOf(datePicker_bill_date.getValue());
            int realFloorNumber = Integer.parseInt(txt_floorNumber.getText());
            int pricedFloorNumber = Integer.parseInt(txt_priced_floorNumber.getText());
            int wellNumber = Integer.parseInt(txt_wellNUmber.getText());
            float floorPrice = Float.parseFloat(txt_priceOfFloor.getText());
            BillPHilo_techncian newBill = new BillPHilo_techncian(id, lift_id, billNumber, billDate, realFloorNumber, wellNumber, floorPrice, pricedFloorNumber);
            int isInserted = Database.insertNewBillFromPhiloTOTechncian(newBill);
            if(isInserted > 0){
                Database.alertInformation("عملية ناجحة");
                loadAllBill();
             }
        }catch(SQLException ex){
            if(ex.getMessage().contains("Duplicate"))
            {
                Database.alertInformation("هناك فاتورة مسجلة للمصعد من قبل");
            }else{
                Database.alertInformation(ex.getMessage());
            }
        }
              
          }
   
   public void updateBill(){
   
        try {
            int billId = Integer.parseInt(lbl_BillID.getText()); 
            int lift_id = Integer.parseInt(lbl_idOfLift.getText());
            int billNumber = 0; // كدة كدة مش هعدل رقم الفاتورة 
            Date billDate = Date.valueOf(datePicker_bill_date.getValue());
            int realFloorNumber = Integer.parseInt(txt_floorNumber.getText());
            int pricedFloorNumber = Integer.parseInt(txt_priced_floorNumber.getText());
            int wellNumber = Integer.parseInt(txt_wellNUmber.getText());
            float floorPrice = Float.parseFloat(txt_priceOfFloor.getText());
            BillPHilo_techncian updated = new BillPHilo_techncian(billId, lift_id, billNumber, billDate, realFloorNumber, wellNumber, floorPrice, pricedFloorNumber);
            int isInserted = Database.updateBillFromPhiloToTechncian(updated);
            if(isInserted > 0){
                Database.alertInformation("عملية ناجحة");
                loadAllBill();
             }
        }catch(SQLException ex){
            if(ex.getMessage().contains("Duplicate"))
            {
                Database.alertInformation("هناك فاتورة مسجلة للمصعد من قبل");
            }else{
                Database.alertInformation(ex.getMessage());
            }
        }
            
   
   
   }
   public void deleteBill(){
           
         try {     
            int id  = Integer.parseInt(lbl_BillID.getText());
            Database.deleteBillFromPhiloTOTechncian(id);
            Database.alertInformation("عملية ناجحة ");
            loadAllBill();
        } catch (SQLException ex) {
              if (ex.getErrorCode() == 1451) {// this code error is thrown when user try to delete parent row used as refrenced key to forign key 
                   Database.alertMessage("لا تسطيع مسحه لانه مرتبط بكثير من بيانات ");
            }else{
                  Database.alertInformation(ex.getMessage());
              }
        }catch(NumberFormatException ex )    {
          Database.alertMessage("قم بتحديد الصف المراد مسح بياناته من الجدول");
        }
   }
   
   public void editBillMother(){
   
   }
   public void calcTotalOFLiftPrice(){
       try{
            int pricedFloorNumber = Integer.parseInt(txt_priced_floorNumber.getText());
            int wellNumber = Integer.parseInt(txt_wellNUmber.getText());
            float floorPrice = Float.parseFloat(txt_priceOfFloor.getText());
            float total = pricedFloorNumber * wellNumber * floorPrice;
            txt_total.setText(total+"");
       }
       catch(NumberFormatException ex){
       // im not handled it to give opertunity to user enter all data
       }
   }
   
   public void loadAllBill(){
        try {
            table_bill.getItems().clear();
            allBill.clear();
            ResultSet rs = Database.selectAllBillFromPhiloToTechncian();
            while (rs.next()) {                                
                int billId = rs.getInt(1);
                int liftId = rs.getInt(2);
                int billNumber = rs.getInt(3);
                Date billDate = rs.getDate(4);
                String liftNumber = rs.getString(5);
                String liftType = rs.getString(6);
                int techncianNumber = rs.getInt(7);
                String siteName = rs.getString(8);
                String companyName = rs.getString(9);
                int realFloorNumber = rs.getInt(10);
                int pricedFloorNumber = rs.getInt(11);
                int wellNumber = rs.getInt(12);
                float priceFloor = rs.getFloat(13);
                BillPHilo_techncian bill = new BillPHilo_techncian(pricedFloorNumber, liftNumber, liftType, techncianNumber, siteName, companyName, priceFloor, billId, liftId, billNumber, billDate, realFloorNumber, wellNumber, priceFloor);
                table_bill.getItems().add(bill);
                allBill.add(bill);
            }
        } catch (SQLException ex) {
            Database.alertInformation(ex.getMessage());
        }
   }
   public void setSelectedRecordToTxt(){
   
        BillPHilo_techncian selectedItem = table_bill.getSelectionModel().getSelectedItem();
        String liftNumber = selectedItem.getLiftNumber();
        combo_liftNumber.getSelectionModel().select(liftNumber);
        setLbl_lift_id(liftNumber);
        settingLabelData();
        if(selectedItem.getBillDate()== null){
            selectedItem.setBillDate(Date.valueOf(LocalDate.now()));
        }else{
        datePicker_bill_date.setValue(selectedItem.getBillDate().toLocalDate());
        }
        txt_priced_floorNumber.setText(selectedItem.getPriced_floor_num()+"");
        txt_priceOfFloor.setText(selectedItem.getPriceOfFloor()+"");
        txt_total.setText(selectedItem.getTotal()+"");
       lbl_BillID.setText(selectedItem.getBillId()+"");
       
   
   }
}
