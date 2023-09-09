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
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import philoplus.philoPlusClasses.BillPhiloMotherComapny;
import philoplus.philoPlusClasses.Database;
/**
 * FXML Controller class
 *
 * @author Seha
 */
public class BillFromOurCompanyToMotherCompanyController implements Initializable {

    @FXML
    private ComboBox<String> combo_liftNumber;
    @FXML
    private TextField txt_liftType;
    @FXML
    private Label lbl_liftNumber;
    @FXML
    private Label lbl_bill_ID;
    @FXML
    private TextField txt_Techncian;
    @FXML
    private TextField txt_site;
    @FXML
    private TextField txt_campany;
    @FXML
    private DatePicker date_picker_billDate;
    @FXML
    private TextField txt_liftFloor_Number;
    @FXML
    private TextField txt_pricedNumber;
    @FXML
    private TextField txt_wellNumber;
    @FXML
    private TextField txt_floorPrice;
    @FXML
    private TextField txt_total;
    @FXML
    private TextField txt_addationValue;
    @FXML
    private Button add_btn;
    @FXML
    private Button edit_btn;
    @FXML
    private Button delete_btn;
    @FXML
    private CheckBox radio_billSent;
    @FXML
    private CheckBox radio_BillMoneyReceive;
    @FXML
    private TableView<BillPhiloMotherComapny> tabel_bill;
    @FXML
    private TableColumn<BillPhiloMotherComapny, Integer> col_id;
    @FXML
    private TableColumn<BillPhiloMotherComapny, Integer> col_bill_id;
    @FXML
    private TableColumn<BillPhiloMotherComapny, Date> col_bill_date;
    @FXML
    private TableColumn<BillPhiloMotherComapny, String> col_liftNumber;
    @FXML
    private TableColumn<BillPhiloMotherComapny, String> col_liftType;
    @FXML
    private TableColumn<BillPhiloMotherComapny, Integer> col_techncianNumber;
    @FXML
    private TableColumn<BillPhiloMotherComapny, String> col_site;
    @FXML
    private TableColumn<BillPhiloMotherComapny, String> col_campany;
    @FXML
    private TableColumn<BillPhiloMotherComapny, Integer> col_floorNumber;
    @FXML
    private TableColumn<BillPhiloMotherComapny, Integer> col_priecedFloor;
    @FXML
    private TableColumn<BillPhiloMotherComapny, Integer> col_wellNumber;
    @FXML
    private TableColumn<BillPhiloMotherComapny, Float> col_priceOFFloor;
    @FXML
    private TableColumn<BillPhiloMotherComapny, Float> coL_totalWithoutAddationalRatio;
    @FXML
    private TableColumn<BillPhiloMotherComapny, Float> col_addtionalRatio;
    @FXML
    private TableColumn<BillPhiloMotherComapny, Float> col_total_afterAddationalRatio;
    @FXML
    private TableColumn<BillPhiloMotherComapny, Integer> col_isSent;
    @FXML
    private TableColumn<BillPhiloMotherComapny, Integer> col_isReceive;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         //Start column intalisation of table view bill
            col_id.setCellValueFactory(new PropertyValueFactory<>("billId"));
            col_bill_id.setCellValueFactory(new PropertyValueFactory<>("billNumber"));
            col_bill_date.setCellValueFactory(new PropertyValueFactory<>("billDate"));
            col_liftNumber.setCellValueFactory(new PropertyValueFactory<>("liftNumber"));
            col_liftType.setCellValueFactory(new PropertyValueFactory<>("liftType"));
            col_techncianNumber.setCellValueFactory(new PropertyValueFactory<>("technciansNumber"));
            col_site.setCellValueFactory(new PropertyValueFactory<>("projectName"));
            col_campany.setCellValueFactory(new PropertyValueFactory<>("companyName"));
            col_floorNumber.setCellValueFactory(new PropertyValueFactory<>("numberOfFloor"));
            col_priecedFloor.setCellValueFactory(new PropertyValueFactory<>("priced_floor"));
            col_wellNumber.setCellValueFactory(new PropertyValueFactory<>("numberOfWell"));
            col_priceOFFloor.setCellValueFactory(new PropertyValueFactory<>("priceOfFloor"));
            coL_totalWithoutAddationalRatio.setCellValueFactory(new PropertyValueFactory<>("totalWithoutAddationValue"));
            col_addtionalRatio.setCellValueFactory(new PropertyValueFactory<>("addationValue"));
            col_total_afterAddationalRatio.setCellValueFactory(new PropertyValueFactory<>("totalWithAddationValue"));
            col_isSent.setCellValueFactory(new PropertyValueFactory<>("isSent"));
            col_isReceive.setCellValueFactory(new PropertyValueFactory<>("isReceived"));
         //End column intalisation of table view bill  
        
            loadAllLiftNumberToComboBox();
            loadAllBill();
            combo_liftNumber.requestFocus();
          //start event when user change comboBox item 
            combo_liftNumber.valueProperty().addListener((obs, oldVal, newVal) -> {
              setLbl_lift_id(newVal);
              settingLabelData();
        });
            
            tabel_bill.setOnKeyReleased(event->{
             if(event.getCode().toString().equals("UP")||event.getCode().toString().equals("DOWN")){
                  selectRecordToLbl();
              }
            });
    }    
         public void setLbl_lift_id(String liftNumber){
          try {
            int lift_id = Database.gettingLiftIdFromOurLiftByLiftNumber(liftNumber);
            lbl_liftNumber.setText(lift_id+"");
             } catch (SQLException ex) {
                Database.alertMessage(ex.getMessage());
                }
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
          public void settingLabelData(){
        try {
            int lift_id = Integer.parseInt(lbl_liftNumber.getText());
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
                combo_liftNumber.getSelectionModel().select(liftNumber);
                String liftCampany = rs.getString(5);
                String liftSite = rs.getString(6);
                txt_site.setText(liftSite);
                txt_campany.setText(liftCampany);
                txt_liftFloor_Number.setText(floorNumber+"");
                txt_pricedNumber.setText(floorNumber+"");
                txt_liftType.setText(liftType);
                txt_Techncian.setText(technicanOfLifts);
                txt_wellNumber.setText(wellNumber+"");
                date_picker_billDate.setValue(LocalDate.now());
                
            }
        }  catch (SQLException ex) {
         Database.alertMessage(ex.getMessage());
        }
    }
          
    public void selectRecordToLbl(){
        BillPhiloMotherComapny selected ;
        selected = tabel_bill.getSelectionModel().getSelectedItem();
        lbl_liftNumber.setText(selected.getLiftId()+"");
        lbl_bill_ID.setText(selected.getBillId()+"");
        settingLabelData();
        date_picker_billDate.setValue(selected.getBillDate().toLocalDate());
        txt_pricedNumber.setText(selected.getPriced_floor()+"");
        if(selected.getPriced_floor() == 0){
        txt_pricedNumber.setText(selected.getNumberOfFloor()+"");
        }
        txt_floorPrice.setText(selected.getPriceOfFloor()+"");
        txt_total.setText(selected.getTotalWithoutAddationValue()+"");
        txt_addationValue.setText(selected.getAddationValue()+"");
        int isSent = selected.getIsSent();
        if(isSent == 1) {
            radio_billSent.setSelected(true);
        }else{
            radio_billSent.setSelected(false);
        }
        int isRecive = selected.getIsReceived();
        if(isRecive == 1) {
            radio_BillMoneyReceive.setSelected(true);
        }else{
            radio_BillMoneyReceive.setSelected(false);
        }
    }
    public void insertNewBill(){
        
        try{
        int id = Database.autoNumber("`bill-philo-mothercompany`", "id");
        int liftId = Integer.parseInt(lbl_liftNumber.getText());
        int billNumber = Database.autoNumber("`bill-philo-mothercompany`", "bill_num");
            System.out.println("done "+ billNumber);
        Date billDate = Date.valueOf(date_picker_billDate.getValue());
        int numberOfFloor = Integer.parseInt(txt_liftFloor_Number.getText());
        int pricedFloor  = Integer.parseInt(txt_pricedNumber.getText());
        int wellNumber = Integer.parseInt(txt_wellNumber.getText());
        float priceOfFloor = Float.parseFloat(txt_floorPrice.getText());
        float valueAdded = Float.parseFloat(txt_addationValue.getText());
        int isSent = radio_billSent.isSelected()? 1 : 0;
        int isReceived = radio_BillMoneyReceive.isSelected()? 1 : 0;
        BillPhiloMotherComapny bill = new BillPhiloMotherComapny(pricedFloor, valueAdded, isSent, isReceived, id, liftId, billNumber, billDate, numberOfFloor, wellNumber, priceOfFloor);
        int isInserted = Database.inserNewBillFromPhiloTOMotherCompany(bill);
        if(isInserted>0){
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
    
        try{
            int bill_id = Integer.parseInt(lbl_bill_ID.getText());
            int isDeleted = Database.deleteBillFromMother(bill_id);
            if(isDeleted>0){
            Database.alertInformation("عملية ناجحة");
            loadAllBill();
            }
        }
        catch(SQLException ex){
          if (ex.getErrorCode() == 1451) {// this code error is thrown when user try to delete parent row used as refrenced key to forign key 
                   Database.alertMessage("لا تسطيع مسحه لانه مرتبط بكثير من بيانات ");
            }else{
                  Database.alertInformation(ex.getMessage());
              }
        }catch(NumberFormatException ex )    {
          Database.alertMessage("قم بتحديد الصف المراد مسح بياناته من الجدول");
        }
    }
    public void editBill(){
        
        try{
        int id = Integer.parseInt(lbl_bill_ID.getText());
        int liftId = Integer.parseInt(lbl_liftNumber.getText());
        Date billDate = Date.valueOf(date_picker_billDate.getValue());
        int numberOfFloor = Integer.parseInt(txt_liftFloor_Number.getText());
        int pricedFloor  = Integer.parseInt(txt_pricedNumber.getText());
        int wellNumber = Integer.parseInt(txt_wellNumber.getText());
        float priceOfFloor = Float.parseFloat(txt_floorPrice.getText());
        float valueAdded = Float.parseFloat(txt_addationValue.getText());
        int isSent = radio_billSent.isSelected()? 1 : 0;
        int isReceived = radio_BillMoneyReceive.isSelected()? 1 : 0;
        BillPhiloMotherComapny bill = new BillPhiloMotherComapny(pricedFloor, valueAdded, isSent, isReceived, id, liftId, 0, billDate, numberOfFloor, wellNumber, priceOfFloor);
        int isInserted = Database.editBillMother(bill);
        if(isInserted>0){
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
    public void  calcTotal(){
        
        int pricedFloor   = Integer.parseInt(txt_pricedNumber.getText());
        int wellNumber    = Integer.parseInt(txt_wellNumber.getText());
        float priceOfFloor = Float.parseFloat(txt_floorPrice.getText());
        float total = pricedFloor * wellNumber * priceOfFloor;
        txt_total.setText(total+"");
    }
    public void loadAllBill(){
        tabel_bill.getItems().clear();
        try{
        ResultSet rs = Database.loadAllBillFromPhiloToMother();
        while(rs.next()){
           int bill_id = rs.getInt(1);
           int lift_id = rs.getInt(2);
           int bill_num = rs.getInt(3);
           Date bill_date = rs.getDate(4);
           String liftNumber = rs.getString(5);
           String liftType = rs.getString(6);
           int techncianNumber = rs.getInt(7);
           String siteName = rs.getString(8);
           String comanyName = rs.getString(9);
           int numOfFloor = rs.getInt(10);
           int pricedFloor = rs.getInt(11);
           int well_number = rs.getInt(12);
           float priceOfFloor = rs.getFloat(13);
           float valueAdded = rs.getFloat(14);
           int isSent = rs.getInt(15);
           int isRecived = rs.getInt(16);
           BillPhiloMotherComapny bill = new BillPhiloMotherComapny(pricedFloor, valueAdded, isSent, isRecived, liftNumber, liftType, techncianNumber, siteName, comanyName, bill_id, lift_id, bill_num, bill_date, numOfFloor, well_number, priceOfFloor);
           tabel_bill.getItems().add(bill);
        }
        }catch(SQLException ex){
            Database.alertMessage(ex.getMessage());
        }
                
    }
}
