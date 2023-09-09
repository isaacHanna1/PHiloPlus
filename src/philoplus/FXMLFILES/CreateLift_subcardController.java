/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package philoplus.FXMLFILES;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import jdk.nashorn.internal.objects.NativeArray;
import philoplus.philoPlusClasses.AutoCompleteComboBox;
import philoplus.philoPlusClasses.Lift;
import philoplus.philoPlusClasses.Database;


/**
 * FXML Controller class
 *
 * @author Seha
 */
public class CreateLift_subcardController implements Initializable {

    @FXML
    private TextField txt_liftNumber;
    @FXML
    private Label lbl_id;
    @FXML
    private TextField txt_po;
    @FXML
    private AutoCompleteComboBox<String> combo_liftType;
    @FXML
    private TextField txt_floor_number;
    @FXML
    private TextField txt_well_number;
    @FXML
    private Button techncian_btn;
    @FXML
    private TextField txt_technicainName;
    @FXML
    private DatePicker date_startWork;
    @FXML
    private TextField txt_number_of_days;
    @FXML
    private Label lbl_finishDate;
    @FXML
    private AutoCompleteComboBox<String> combBoxComapny;
    @FXML
    private AutoCompleteComboBox<String> comboBox_sites;
    @FXML
    private Button add_btn;
    @FXML
    private Button edit_btn;
    @FXML
    private Button delete_btn;
    @FXML
    private TableView<Lift> table_createdLifts;
    @FXML
    private TableColumn<Lift, Integer> col_id;
    @FXML
    private TableColumn<Lift, String> col_liftNumber;
    @FXML
    private TableColumn<Lift, String> col_po;
    @FXML
    private TableColumn<Lift, String> col_liftType;
    @FXML
    private TableColumn<Lift, Integer> col_floor_num;
    @FXML
    private TableColumn<Lift, Integer> col_wellNumber;
    @FXML
    private TableColumn<Lift,Integer> col_techncianNumber;
    @FXML
    private TableColumn<Lift,String> col_techncianName;
    @FXML
    private TableColumn<Lift,Date> col_startWork;
    @FXML
    private TableColumn<Lift, Integer> col_numberOfDays;
    @FXML
    private TableColumn<Lift, Date> col_finishDate;
    @FXML
    private TableColumn<Lift, String> col_siteName;
    @FXML
    private TableColumn<Lift, String> col_company;
    @FXML
    private Label lbl_techncianName;
    
    List <String> allSiteName = new ArrayList();
    List <String> allCampaniesName = new ArrayList();
    List <Lift>    allLoadedLifts     = new ArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//    Start  intialize column
             col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
             col_liftNumber.setCellValueFactory(new PropertyValueFactory<>("liftNum"));          
             col_po.setCellValueFactory(new PropertyValueFactory<>("po"));
             col_liftType.setCellValueFactory(new PropertyValueFactory<>("lift_type_details"));
             col_floor_num.setCellValueFactory(new PropertyValueFactory<>("lift_floor_number"));
             col_wellNumber.setCellValueFactory(new PropertyValueFactory<>("lift_well_num"));
             col_techncianNumber.setCellValueFactory(new PropertyValueFactory<>("techniciansNumber"));
             col_startWork.setCellValueFactory(new PropertyValueFactory<>("startWork"));
             col_numberOfDays.setCellValueFactory(new PropertyValueFactory<>("num_work_day"));
             col_finishDate.setCellValueFactory(new PropertyValueFactory<>("finsihDate"));
             col_siteName.setCellValueFactory(new PropertyValueFactory<>("siteName"));
             col_company.setCellValueFactory(new PropertyValueFactory<>("CampanyName"));             
//    End intialize column 
            loadAllLiftTypeToCombBox();
            loadAllSitesName(); // into site comboBox
            loadAllCampaniesName();//  into combobox companies
            loadAllSavedLifts(); // load all lifts that saved before    
            lbl_techncianName.setText("لم يتم تخصيص فني لهذا المصعد");
            edit_btn.setVisible(false);
            delete_btn.setVisible(false);
          PauseTransition pause = new PauseTransition(Duration.millis(200));
  // i make event to get txt lift number data when user typing 
           txt_liftNumber.setOnKeyReleased(event->{
               pause.playFromStart();    // Restart the pause timer every time a key is typed   
             if(event.getCode().toString().equals("DOWN")){
                 table_createdLifts.requestFocus();
                 table_createdLifts.getSelectionModel().selectFirst();
             }
             if(event.getCode().toString().equals("ESCAPE")){
                 txt_liftNumber.setText("");
                 loadAllSavedLifts();
                 clearTextFields();
                 txt_liftNumber.requestFocus();
                 edit_btn.setVisible(false);
                 delete_btn.setVisible(false);
             }
             if(event.getCode().toString().equals("ENTER")){
                 table_createdLifts.requestFocus();
                 table_createdLifts.getSelectionModel().selectFirst();
             }      
           });
           table_createdLifts.setOnKeyReleased(event->{
           if(event.getCode().toString().equals("ENTER")){
                 txt_liftNumber.requestFocus();
                 selectedRecordToTxt();
             }
           if(event.getCode().toString().equals("ESCAPE")){
               txt_liftNumber.setText("");
               loadAllSavedLifts();
               clearTextFields();
               txt_liftNumber.requestFocus();
               edit_btn.setVisible(false);
               delete_btn.setVisible(false);
             }
              if(event.getCode().toString().equals("DOWN") || event.getCode().toString().equals("UP")){
                 table_createdLifts.requestFocus();
                 selectedRecordToTxt();
             }
           });
                    pause.setOnFinished(event -> {
                     searchByLiftNumberWhenUserTypingInLiftNumTxt();                  // This code will be executed after the pause duration has elapsed       
              });                    
    }
      public void loadAllLiftTypeToCombBox(){
        try {
            ResultSet rs = Database.gettingAllLiftType();
            String Lifttype = "";
            while(rs.next()){
                Lifttype = rs.getString(1);
                combo_liftType.getItems().add(Lifttype);
            }
        } catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());
        }
    }
      public void loadAllSitesName (){
          table_createdLifts.getItems().clear();
        try {
            ResultSet rs  = Database.gettingAllSitesName();
            while (rs.next()) {                
                allSiteName.add(rs.getString(1));
                comboBox_sites.getItems().add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());
        }
      }
       public void loadAllCampaniesName (){
        try {
            ResultSet rs  = Database.gettingAllCampaniesName();
            while (rs.next()) {                
                allCampaniesName.add(rs.getString(1));
                combBoxComapny.getItems().add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());
        }
      }
      public void insertNewLift(){  
          try{
            int id = Database.autoNumber("`our-lifts`","id");
            String liftNumber = txt_liftNumber.getText();
           if(liftNumber.equals("")){
              Database.alertMessage("ادخل رقم المصعد");
              return;
           }
            String liftType = combo_liftType.getSelectionModel().getSelectedItem();
            int liftTypeId = Database.gettingIdOfLiftType(liftType);
            String po = txt_po.getText();
            int lift_floor_number = Integer.parseInt(txt_floor_number.getText());
            int lift_well_number = Integer.parseInt(txt_well_number.getText());
            if(date_startWork.getValue()== null){
                date_startWork.setValue(LocalDate.now());
            }
            Date startWork = Date.valueOf(date_startWork.getValue());
            int workDay = Integer.parseInt(txt_number_of_days.getText());
            String siteName = comboBox_sites.getSelectionModel().getSelectedItem();
            int siteId = Database.gettingSiteId(siteName);
            String companyName = combBoxComapny.getSelectionModel().getSelectedItem();
            int companyId = Database.gettingCompanyId(companyName);
            Lift newOne = new Lift(id, po, liftNumber, liftTypeId, lift_floor_number, lift_well_number, startWork, workDay, siteId, companyId);
            int isInserted = Database.insertNewLift(newOne);
            if(isInserted>0){
            Database.alertInformation("عملية ناجحة ");
            lbl_id.setText(id+"");   // to send it to AssignTechnciansToLiftsController
            table_createdLifts.getItems().add(new  Lift(id, liftNumber, po, liftType, lift_floor_number, lift_well_number, 0, startWork, workDay, siteName, companyName)); // when inserted sucessfull added new one into table 
            allLoadedLifts.add(newOne); // when inserted sucessfull added new one into all loaded lifts  
            table_createdLifts.requestFocus();
            }
            }catch(SQLIntegrityConstraintViolationException  ex){//this exception is thrown because i put constarint on area name col be unique 
            Database.alertMessage("  هناك بيانات  مسجلة بتلك رقم المصعد  في قاعدة البيانات");
               } 
            catch(SQLException ex){
                Database.alertMessage(ex.getMessage());
            }
            catch(NullPointerException ex){
                Database.alertMessage("تاكد من ادخال جميع البيانات");
            }
            catch(Exception ex){
                Database.alertMessage("تاكد من ادخال جميع البيانات");
            }
      }
      public void editLiftData(){
        try{
            int id = Integer.parseInt(lbl_id.getText());
            String liftNumber = txt_liftNumber.getText();
           if(liftNumber.equals("")){
              Database.alertMessage("ادخل رقم المصعد");
              return;
           }
            String liftType = combo_liftType.getSelectionModel().getSelectedItem();
            int liftTypeId = Database.gettingIdOfLiftType(liftType);
            String po = txt_po.getText();
            int lift_floor_number = Integer.parseInt(txt_floor_number.getText());
            int lift_well_number = Integer.parseInt(txt_well_number.getText());
             if(date_startWork.getValue()== null){
                date_startWork.setValue(LocalDate.now());
            }
            Date startWork = Date.valueOf(date_startWork.getValue());
            int workDay = Integer.parseInt(txt_number_of_days.getText());
            String siteName = comboBox_sites.getSelectionModel().getSelectedItem();
            int siteId = Database.gettingSiteId(siteName);
            String companyName = combBoxComapny.getSelectionModel().getSelectedItem();
            int companyId = Database.gettingCompanyId(companyName);
            Lift updatedOne = new Lift(id, po, liftNumber, liftTypeId, lift_floor_number, lift_well_number, startWork, workDay, siteId, companyId);
            int isInserted = Database.editLiftData(updatedOne);
            if(isInserted>0){
            Database.alertInformation("عملية ناجحة ");
            lbl_id.setText(id+"");   // to send it to AssignTechnciansToLiftsController
            table_createdLifts.getItems().clear();
            table_createdLifts.getItems().add(new  Lift(id, liftNumber, po, liftType, lift_floor_number, lift_well_number, 0, startWork, workDay, siteName, companyName)); // when inserted sucessfull added new one into table 
            table_createdLifts.requestFocus();
            }
            }catch(SQLIntegrityConstraintViolationException  ex){//this exception is thrown because i put constarint on area name col be unique 
            Database.alertMessage("  هناك بيانات  مسجلة بتلك رقم المصعد  في قاعدة البيانات");
               } 
            catch(SQLException ex){
                Database.alertMessage(ex.getMessage());
            }
            catch(NullPointerException ex){
                Database.alertMessage("تاكد من ادخال جميع البيانات");
            }
            catch(Exception ex){
                Database.alertMessage("تاكد من ادخال جميع البيانات");
            }
      }
      public void deleteLift(){    
         try {     
            int id  = Integer.parseInt(lbl_id.getText());
            Database.deleteLift(id);
            Database.alertInformation("عملية ناجحة ");
            table_createdLifts.getItems().clear();
            loadAllSavedLifts();
            table_createdLifts.requestFocus();
            clearTextFields();
        } catch (SQLException ex) {
              if (ex.getErrorCode() == 1451) {// this code error is thrown when user try to delete parent row used as refrenced key to forign key 
                   Database.alertMessage("لا تسطيع مسحه لانه مرتبط بكثير من بيانات ");
            }else{
                  Database.alertInformation(ex.getMessage());
              }
        }catch(NumberFormatException ex )    {
          Database.alertMessage("قم بتحديد المصعد المراد مسح بياناته من الجدول");
        }
      }
      public void searchByLiftNumberWhenUserTypingInLiftNumTxt(){
             table_createdLifts.getItems().clear();
        // Create a stream from the list of names // 
             Stream<Lift> nameStream = allLoadedLifts.stream();
       // Filter the names to get only those that start with " the user enter txt_lift_number"
             Stream<Lift> filteredStream = nameStream.filter(name -> name.getLiftNum().contains(txt_liftNumber.getText()));
             filteredStream.forEach(p-> putRecordToTable(p) );
      }
      
      public void putRecordToTable(Lift lift){
          table_createdLifts.getItems().add(lift);
                }
      public void loadCardToAssignTechcianToLift(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("assignTechnciansToLifts.fxml"));
            String dataOfLiftId = lbl_id.getText();
            String liftNumber = txt_liftNumber.getText();
            Scene newScene = new Scene(loader.load());
            AssignTechnciansToLiftsController controller = loader.getController();
            controller.setData(dataOfLiftId,liftNumber );
             Stage stage = new Stage();
             stage.setScene(newScene);
             stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CreateLift_subcardController.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    public void loadAllSavedLifts(){
        try {
                table_createdLifts.getItems().clear();
                allLoadedLifts.clear();
                ResultSet rs = Database.loadAllSavedLifts();
                while (rs.next()) {                
                int id = rs.getInt(1);
                String po = rs.getString(2);
                String liftNum = rs.getString(3);
                String lift_type_details = rs.getString(4);
                int lift_floor_number = rs.getInt(5);
                int lift_well_num  = rs.getInt(6);
                Date startWork = rs.getDate(7);
                int num_work_day = rs.getInt(8);         
                String siteName = rs.getString(9);
                String CampanyName = rs.getString(10);
                int techniciansNumber = rs.getInt(11);
                Lift one = new Lift(id, liftNum, po, lift_type_details, lift_floor_number, lift_well_num, techniciansNumber, startWork, num_work_day, siteName, CampanyName);
                table_createdLifts.getItems().add(one);
                this.allLoadedLifts.add(one);
            }
             } catch (SQLException ex) {
                Database.alertMessage(ex.getMessage());
        }
    }
    public void selectedRecordToTxt(){
        Lift selectedLift = table_createdLifts.getSelectionModel().getSelectedItem();
        int id = selectedLift.getId();
        lbl_id.setText(id+"");
        txt_liftNumber.setText(selectedLift.getLiftNum());
        combo_liftType.getSelectionModel().select(selectedLift.getLift_type_details());
        txt_po.setText(selectedLift.getPo());
        txt_floor_number.setText(selectedLift.getLift_floor_number()+"");
        txt_well_number.setText(selectedLift.getLift_well_num()+"");
        date_startWork.setValue(selectedLift.getStartWork().toLocalDate());
        txt_number_of_days.setText(selectedLift.getNum_work_day()+"");
        lbl_finishDate.setText(selectedLift.getFinsihDate()+"");
        comboBox_sites.getSelectionModel().select(selectedLift.getSiteName());
        combBoxComapny.getSelectionModel().select(selectedLift.getCampanyName());
        edit_btn.setVisible(true);
        delete_btn.setVisible(true);
        try{
        String allTechncianWorkedInLift = "";
        ResultSet rs = Database.gettingAllTechnciansAssignedToLift(id);
        while (rs.next()) { 
            allTechncianWorkedInLift += rs.getString(2)+"   ";
        }
        if(allTechncianWorkedInLift.equals("")){
            lbl_techncianName.setText("لم يتم تخصيص فني لهذا المصعد");
        }
        else
        lbl_techncianName.setText(allTechncianWorkedInLift);
        }
        catch(SQLException ex ){
            Database.alertMessage(ex.getMessage());
        }
    }   
    public void clearTextFields(){
        lbl_id.setId("");
        txt_liftNumber.setText("");
        combo_liftType.getSelectionModel().select("");
        txt_po.setText("");
        txt_floor_number.setText("");
        txt_well_number.setText("");
        date_startWork.setValue(null);
        txt_number_of_days.setText("");
        lbl_finishDate.setText("");
        comboBox_sites.getSelectionModel().select("");
        combBoxComapny.getSelectionModel().select("");     
        lbl_techncianName.setText("");
   }
   //make button invisible 
    public void invisible(Button button , boolean visibility){
    button.setVisible(visibility);
}
}

