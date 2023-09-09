/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package philoplus.FXMLFILES;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import philoplus.philoPlusClasses.Companies;
import philoplus.philoPlusClasses.Database;
import static philoplus.philoPlusClasses.Database.autoNumber;
import  javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.util.Duration;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 * FXML Controller class
 *
 * @author Seha
 */
public class CampanyCardController implements Initializable {

    @FXML
    private GridPane parentGrid;
     @FXML
    private VBox vBoxParent;
    @FXML
    private TextField txt_companyName;
    @FXML
    private TextField txt_country;
    @FXML
    private TextField txt_province;
    @FXML
    private TextField txt_police_station;
    @FXML
    private TextField txt_street_num;
    @FXML
    private TextField txt_building_num;
    @FXML
    private TextField txt_apartmentNum;
    @FXML
    private TextField txt_tax_num;
    @FXML
    private TextField txt_notes;
    @FXML
    private Label lbl_id; // this labe is hidden from user , i used it to update and delete queries 
    @FXML
    private TableView<Companies> campanyTableView;
    @FXML
    private TableColumn<Companies, String> col_aprtment;
    @FXML
    private TableColumn<Companies, String> col_building;
    @FXML
    private TableColumn<Companies, String> col_company_name;
    @FXML
    private TableColumn<Companies, String> col_country;
    @FXML
    private TableColumn<Companies, Integer> col_id;
    @FXML
    private TableColumn<Companies, String> col_note;
    @FXML
    private TableColumn<Companies, String> col_police_station;
    @FXML
    private TableColumn<Companies, String> col_province;
    @FXML
    private TableColumn<Companies, String> col_street;
    @FXML
    private TableColumn<Companies, String> col_tax_num;
    @FXML
    private Button delete_btn;
    @FXML
    private Button edit_btn;
    @FXML
    private Button add_btn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Start column intalisation of table view companies
            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_company_name.setCellValueFactory(new PropertyValueFactory<>("company_name"));
            col_country.setCellValueFactory(new PropertyValueFactory<>("country"));
            col_province.setCellValueFactory(new PropertyValueFactory<>("province"));
            col_police_station.setCellValueFactory(new PropertyValueFactory<>("police_station"));
            col_street.setCellValueFactory(new PropertyValueFactory<>("street_num"));
            col_building.setCellValueFactory(new PropertyValueFactory<>("building_num"));
            col_aprtment.setCellValueFactory(new PropertyValueFactory<>("apartment_num"));
            col_tax_num.setCellValueFactory(new PropertyValueFactory<>("tax_num"));
            col_note.setCellValueFactory(new PropertyValueFactory<>("notes"));
        //==> End column intalisation of table view companies
            PauseTransition pause = new PauseTransition(Duration.millis(100));
       // i make event to get companies data when user typing 
           txt_companyName.setOnKeyReleased(event->{
             if(event.getCode().toString().equals("DOWN")){
                 campanyTableView.requestFocus();
                 campanyTableView.getSelectionModel().selectFirst();
             }
             if(event.getCode().toString().equals("ESCAPE")){
                 txt_companyName.setText("");
                 loadAllCompaniesData();
                 clearTextFields();
                 txt_companyName.requestFocus();
             }
             if(event.getCode().toString().equals("ENTER")){
                 campanyTableView.requestFocus();
                 campanyTableView.getSelectionModel().selectFirst();
             }
               pause.playFromStart();    // Restart the pause timer every time a key is typed   
           });
           campanyTableView.setOnKeyReleased(event->{
           if(event.getCode().toString().equals("ENTER")){
                 txt_companyName.requestFocus();
                 selectItemCompanyToTxtBox();
             }
           if(event.getCode().toString().equals("ESCAPE")){
               txt_companyName.setText("");
               loadAllCompaniesData();
               clearTextFields();
               txt_companyName.requestFocus();
             }
           });
                    pause.setOnFinished(event -> {
                   searchAndDisplay();                  // This code will be executed after the pause duration has elapsed       
              });       
                   loadAllCompaniesData();      // load all companies info to table view            
                   invisible(edit_btn, false);
                   invisible(delete_btn, false);
    }    
    
    public void insertNewCompany(){
         try {
            int id = autoNumber("companies", "id");
             String companyName = txt_companyName.getText();
             String country = txt_country.getText();
             String province = txt_province.getText();
             String police_station = txt_police_station.getText();
             String StreetNum = txt_street_num.getText();
             String building_num = txt_building_num.getText();
             String apartment_num = txt_apartmentNum.getText();
             String tax_num =txt_tax_num.getText();
             String notes  =txt_notes.getText();
             Companies newCompany = new Companies(id, companyName, country, province, police_station, StreetNum, building_num, apartment_num, tax_num, notes);  
             int isInserted = Database.insertNewCompanies(newCompany); // return int 1 if inserted completed well 
             if(isInserted > 0){ //to make sure that recored is inserted
             Database.alertInformation("عملية ناجحة");
             searchAndDisplay();
             txt_companyName.requestFocus();
            }
        }catch(IllegalArgumentException ex){ // i thrown exception in constructor method  of campany class if variable comapnyName is empty 
            Database.alertMessage("لا يمكن ان يكون حقل اسم الشركة فارغ");
        } catch(SQLIntegrityConstraintViolationException  ex){//this exception is thrown because i put constarint on campany_name col be unique 
            Database.alertMessage("هناك شركة مسجلة بتلك الاسم في قاعدة البيانات");
        }  catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());  
        }
         catch(Exception ex){
             Database.alertInformation(ex.getMessage());
         }
             invisible(edit_btn, false);
             invisible(delete_btn, false);
    }
    //this method called every time user type on keyboad and search with comapany name in database 
    public void searchAndDisplay(){
        campanyTableView.getItems().clear(); // to clear all old searched data on table view  after user change company name 
         Platform.runLater(()->{ // work as thread
          try {
            String companyName = txt_companyName.getText().trim();
            if(companyName.equals("")){
            clearTextFields(); // clear old data in txtBox cause empty comapny name that i uses to search 
            lbl_id.setText("");
            invisible(edit_btn, false);
            invisible(delete_btn, false);
            invisible(add_btn, true);
            }
            ResultSet rs = Database.searchInCompanies(companyName);    //dabase query 
            while (rs.next()) {        //when match an have result         
                int id                    = rs.getInt(1);
                String campanyName = rs.getString(2);
                String country       = rs.getString(3);
                String province     = rs.getString(4);
                String police_station = rs.getString(5);
                String street             = rs.getString(6);
                String building          = rs.getString(7);
                String apartment        = rs.getString(8);
                String tax_num           = rs.getString(9);
                String notes               = rs.getString(10);
                Companies getOne = new Companies(id, campanyName, country, province, police_station, street, building, apartment, tax_num, notes);
                campanyTableView.getItems().add(getOne);//added new company in table view 
                campanyTableView.getSelectionModel().selectFirst();
            }
            if(campanyTableView.getItems().size()== 1 ){ // if table have one record so take data in table and put it in txtBoxes
              lbl_id.setText(""+campanyTableView.getItems().get(0).getId()); // get id and put it in label to be used in update and delete query 
              invisible(edit_btn, true);
              invisible(delete_btn, true);
              invisible(add_btn, false);
            }
           
            rs.close();
        }catch(IllegalArgumentException ex){
             
            // this eception happened when user empty textFiled 
            // so i want to 
            campanyTableView.getItems().clear(); // clear pervious record
            loadAllCompaniesData(); // load all companies again 
            clearTextFields();
        }
        catch (Exception ex) {
            Database.alertMessage(ex.getMessage());
        }
        });
            invisible(add_btn, true);
    }
   public void loadAllCompaniesData(){
       campanyTableView.getItems().clear();// ensure table are empty from records 
         Platform.runLater(()->{
          try {
              //select * all
            ResultSet rs = Database.searchInCompanies(); 
            while (rs.next()) {                
                int id                    = rs.getInt(1);
                String campanyName = rs.getString(2);
                String country       = rs.getString(3);
                String province     = rs.getString(4);
                String police_station = rs.getString(5);
                String street             = rs.getString(6);
                String building          = rs.getString(7);
                String apartment        = rs.getString(8);
                String tax_num           = rs.getString(9);
                String notes               = rs.getString(10);
                Companies getOne = new Companies(id, campanyName, country, province, police_station, street, building, apartment, tax_num, notes);
                campanyTableView.getItems().add(getOne);       
            }
            rs.close();
        }catch(IllegalArgumentException ex){ // i thrown exception in constructor method  of campany class if variable comapnyName is empty 
            Database.alertMessage("لا يمكن ان يكون حقل اسم الشركة فارغ");
        } 
        catch (Exception ex) {
            Database.alertMessage(ex.getMessage());
        }
        });
   }
   public void updateCompanyData(){
      try {
            int id = Integer.parseInt(lbl_id.getText());
            String companyName = txt_companyName.getText();
            String country = txt_country.getText();
            String province = txt_province.getText();
            String police_station = txt_police_station.getText();
            String StreetNum = txt_street_num.getText();
            String building_num = txt_building_num.getText();
            String apartment_num = txt_apartmentNum.getText();
            String tax_num = txt_tax_num.getText();
            String notes  =txt_notes.getText();
            Companies updatedObject = new Companies(id, companyName, country, province, police_station, StreetNum, building_num, apartment_num, tax_num, notes);
            int isUpdated = Database.editCompanyData(id,updatedObject);
             if (isUpdated>0){
                    Database.alertInformation("عملية ناجحة ");
                    clearTextFields();  
                    txt_companyName.setText(companyName);
                    searchAndDisplay();
                    campanyTableView.requestFocus();
                    lbl_id.setText("");                    
                    invisible(edit_btn, false);
                    invisible(delete_btn, false);
                    }
        }catch(SQLIntegrityConstraintViolationException  ex){//this exception is thrown because i put constarint on campany_name col be unique 
            Database.alertMessage("هناك شركة مسجلة بتلك الاسم في قاعدة البيانات");
        } 
      catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());
        }
      catch(NumberFormatException ex )    {
          Database.alertMessage("قم بتحديد الشركة المراد تعديل بياناتها من الجدول");
        }
   }
   // METHOD TO DELETE RECORD FROM DATABASE companies
   public void DeleteRecord(){
           try {     
            int id  = Integer.parseInt(lbl_id.getText());
            Database.deleteRecord(id);
            Database.alertInformation("عملية ناجحة ");
            loadAllCompaniesData();
            clearTextFields();
        } catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());
        }catch(NumberFormatException ex )    {
          Database.alertMessage("قم بتحديد الشركة المراد مسح بياناتها من الجدول");
        }
            invisible(edit_btn, false);
            invisible(delete_btn, false);
            lbl_id.setText("");
            txt_companyName.requestFocus();
   }
   // i use this function to get selectItem data and put it to txtboxes
   public void selectItemCompanyToTxtBox(){
            Companies selectedItem = campanyTableView.getSelectionModel().getSelectedItem();
            lbl_id.setText(selectedItem.getId()+"");
            txt_companyName.setText(selectedItem.getCompany_name());
            txt_country.setText(selectedItem.getCountry());
            txt_province.setText(selectedItem.getProvince());
            txt_police_station.setText(selectedItem.getPolice_station());
            txt_street_num.setText(selectedItem.getStreet_num());
            txt_building_num.setText(selectedItem.getBuilding_num());
            txt_apartmentNum.setText(selectedItem.getApartment_num());
            txt_tax_num.setText(selectedItem.getTax_num());     
            txt_notes.setText(selectedItem.getNotes());
            invisible(edit_btn, true);
            invisible(delete_btn, true);
            invisible(add_btn, false);

   }
      public void selectItemCompanyToTxtBoxWithoutComapnyName(){
          try{
            Companies selectedItem = campanyTableView.getSelectionModel().getSelectedItem();
            lbl_id.setText(selectedItem.getId()+"");
            txt_country.setText(selectedItem.getCountry());
            txt_province.setText(selectedItem.getProvince());
            txt_police_station.setText(selectedItem.getPolice_station());
            txt_street_num.setText(selectedItem.getStreet_num());
            txt_building_num.setText(selectedItem.getBuilding_num());
            txt_apartmentNum.setText(selectedItem.getApartment_num());
            txt_tax_num.setText(selectedItem.getTax_num());     
            txt_notes.setText(selectedItem.getNotes());
          }
        catch(NumberFormatException ex){
            Database.alertMessage("لابد من تحديد اسم الشركة المراد تعديل بياناتها ");
        }catch(Exception ex){
              Database.alertMessage(ex.getMessage());
        }
          
        
   }
 //clear all textfields content 
protected void clearTextFields() {

    // parentGid = is grid pane contain HBox VBox 
    // ==> next line i get childeren and by loop get textbox and clear content
            for (Node node :  parentGrid.getChildren()) {
                if (node instanceof HBox) {
                    ObservableList<Node> children = ((HBox) node).getChildren();
                    for (int i = 0; i < children.size(); i++) {
                        if (children.get(i) instanceof TextField){
                            ((TextField)children.get(i)).clear();
                        }
                    }
                    }
            }
        // vBoxParent  is parent VBox contain Hbox so i repeat pervious code again 
                 for (Node node :  vBoxParent.getChildren()) {
                if (node instanceof HBox) {
                    ObservableList<Node> children = ((HBox) node).getChildren();
                    for (int i = 0; i < children.size(); i++) {
                        if (children.get(i) instanceof TextField){
                            ((TextField)children.get(i)).clear();
                        }
                    }
                    }
            }
        
}
public void clearTextWithoutCompanyName(){
txt_country.setText("");
txt_province.setText("");
txt_police_station.setText("");
txt_street_num.setText("");
txt_building_num.setText("");
txt_apartmentNum.setText("");
txt_tax_num.setText("");
txt_notes.setText("");
     
}

//make button invisible 
public void invisible(Button button , boolean visibility){
    button.setVisible(visibility);
}


}
