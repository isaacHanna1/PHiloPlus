/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package philoplus.FXMLFILES;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import philoplus.philoPlusClasses.Areas;
import philoplus.philoPlusClasses.Database;
import static philoplus.philoPlusClasses.Database.autoNumber;
import philoplus.philoPlusClasses.Sites;

/**
 * FXML Controller class
 *
 * @author Seha
 */
public class ProjectsCardController implements Initializable {

    @FXML
    private GridPane parentGrid;
    @FXML
    private TextField txt_siteName;
    @FXML
    private Label lbl_id;
    @FXML
    private ComboBox<String> combo_areaName;
    @FXML
    private TextField txt_po;
    @FXML
    private TextField txt_siteEngineerName;
    @FXML
    private TextField txt_siteEngineerTelephone;
    @FXML
    private TextField txt_siteAdminName;
    @FXML
    private TextField txt_siteAdminTelephone;
    @FXML
    private TextField txt_OursiteEngineerName;
    @FXML
    private TextField txt_OursiteEngineerTelephone;
    @FXML
    private TextField txt_ourSiteAdminName;
    @FXML
    private TextField txt_ourSiteAdminTelephone;
    @FXML
    private Button add_btn;
    @FXML
    private Button edit_btn;
    @FXML
    private Button delete_btn;
    @FXML
    private TableView<Sites> siteTableView;
    @FXML
    private TableColumn<Sites,Integer> col_id;
    @FXML
    private TableColumn<Sites, String> col_siteName;
    @FXML
    private TableColumn<Sites, String> col_areaName;
    @FXML
    private TableColumn<Sites, String> col_po;
    @FXML
    private TableColumn<Sites, String> col_siteEngineer;
    @FXML
    private TableColumn<Sites, String> col_siteEngineerTel;
    @FXML
    private TableColumn<Sites, String> col_adminName;
    @FXML
    private TableColumn<Sites, String> col_adminTel;
    @FXML
    private TableColumn<Sites, String> col_ourEnginner;
    @FXML
    private TableColumn<Sites, String> col_ourEnginnerTel;
    @FXML
    private TableColumn<Sites, String> col_ourAdmin;
    @FXML
    private TableColumn<Sites, String> col_ourAdminTel;
    @FXML
    private TableColumn<Sites, String> col_note;
    @FXML
    private TextField txt_notes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            //load all areas name into combobox
            gettingAreaName();   
             //Start column intalisation of table view areas
            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_siteName.setCellValueFactory(new PropertyValueFactory<>("siteName"));
            col_areaName.setCellValueFactory(new PropertyValueFactory<>("areaName"));     
            col_po.setCellValueFactory(new PropertyValueFactory<>("PO"));     
            col_siteEngineer.setCellValueFactory(new PropertyValueFactory<>("siteEngineer"));     
            col_siteEngineerTel.setCellValueFactory(new PropertyValueFactory<>("SiteEngineerTelephone"));     
            col_adminName.setCellValueFactory(new PropertyValueFactory<>("siteAdmin"));     
            col_adminTel.setCellValueFactory(new PropertyValueFactory<>("siteAdminTelephone"));     
            col_ourEnginner.setCellValueFactory(new PropertyValueFactory<>("ourSiteEngineer"));     
            col_ourEnginnerTel.setCellValueFactory(new PropertyValueFactory<>("ourSiteEngineerTelephone"));     
            col_ourAdmin.setCellValueFactory(new PropertyValueFactory<>("ourSiteAdmin"));     
            col_ourAdminTel.setCellValueFactory(new PropertyValueFactory<>("ourSiteAdminTelephone"));     
            col_note.setCellValueFactory(new PropertyValueFactory<>("note"));     
             //==> End column intalisation of table view areas   
                PauseTransition pause = new PauseTransition(Duration.millis(200));
                // i make event to get site data when user typing 
           txt_siteName.setOnKeyReleased(event->{
               pause.playFromStart();    // Restart the pause timer every time a key is typed   
             if(event.getCode().toString().equals("DOWN")){
                 siteTableView.requestFocus();
                 siteTableView.getSelectionModel().selectFirst();
             }
             if(event.getCode().toString().equals("ESCAPE")){
                 txt_siteName.setText("");
                 loadAllSites();
                 clearTextFields();
                 txt_siteName.requestFocus();
             }
             if(event.getCode().toString().equals("ENTER")){
                 siteTableView.requestFocus();
                 siteTableView.getSelectionModel().selectFirst();
             }
               
           });
           siteTableView.setOnKeyReleased(event->{
           if(event.getCode().toString().equals("ENTER")){
                 txt_siteName.requestFocus();
                 selectSiteToTxt();
             }
           if(event.getCode().toString().equals("ESCAPE")){
               txt_siteName.setText("");
               loadAllSites();
               clearTextFields();
               txt_siteName.requestFocus();
             }
           });
                    pause.setOnFinished(event -> {
                     searchAndDisplay();                  // This code will be executed after the pause duration has elapsed       
              });       
                   loadAllSites();      // load all site info to table view 
                   invisible(edit_btn, false);
                   invisible(delete_btn, false);
    }    
    
    
    public void insertNewSite(){
    
        try{
    
        String siteName  = txt_siteName.getText() ;
        if(siteName.equals("")){
            Database.alertMessage("ادخل اسم الموقع ");
        }
        String areaName = combo_areaName.getSelectionModel().getSelectedItem();
        if(areaName.equals("")){
            Database.alertMessage("اختر المنطقة  ");
        }
        int id = Database.autoNumber("sites", "id");
        int areaId = Database.gettingAreaId(areaName);
        String po = txt_po.getText();
        String siteEngineer = txt_siteEngineerName.getText();
        String siteEngineerTelephone = txt_siteEngineerTelephone.getText();
        String siteAdmin = txt_siteAdminName.getText();
        String siteAdminTelephone = txt_siteAdminTelephone.getText();
        String ourSiteEngineer = txt_OursiteEngineerName.getText();
        String ourSiteEngineerTelephone = txt_OursiteEngineerTelephone.getText();
        String ourSiteAmin = txt_ourSiteAdminName.getText();
        String ourSiteAdminTelephone = txt_ourSiteAdminTelephone.getText();
        String notes = txt_notes.getText();
        Sites newOne = new Sites(id, siteName, areaId, po, siteEngineer, siteEngineerTelephone, siteAdmin, siteAdminTelephone, ourSiteEngineer, ourSiteEngineerTelephone, ourSiteAmin, ourSiteAdminTelephone, notes);
        int isInserted = Database.insertIntoSites(newOne);
            if(isInserted>0){
              Database.alertInformation("عملية ناجحة ");
              searchAndDisplay();
             txt_siteName.requestFocus();
            }
       
        } catch(SQLIntegrityConstraintViolationException  ex){//this exception is thrown because i put constarint on area name col be unique 
            Database.alertMessage("هناك موقع  مسجلة بتلك الاسم في قاعدة البيانات");
        }  catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());  
        }
         catch(Exception ex){
             Database.alertInformation(ex.getMessage());
         }
         invisible(edit_btn, false);
         invisible(delete_btn, false);
    }
              // METHOD TO DELETE RECORD FROM DATABASE
   public void DeleteRecord(){
           try {     
            int id  = Integer.parseInt(lbl_id.getText());
            Database.deleteSite(id);
            Database.alertInformation("عملية ناجحة ");
            loadAllSites();
            clearTextFields();
        } catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());
        }catch(NumberFormatException ex )    {
          Database.alertMessage("قم بتحديد الموقع المراد مسح بياناته من الجدول");
        }
            invisible(edit_btn, false);
            invisible(delete_btn, false);
            lbl_id.setText("");
            txt_siteName.requestFocus();
   }
     public void updateSiteData(){
      try {
        int id = Integer.parseInt(lbl_id.getText());
        String siteName  = txt_siteName.getText() ;
        if(siteName.equals("")){
            Database.alertMessage("ادخل اسم الموقع ");
        }
        String areaName = combo_areaName.getSelectionModel().getSelectedItem();
        if(areaName.equals("")){
            Database.alertMessage("اختر المنطقة  ");
        }
        int areaId = Database.gettingAreaId(areaName);
        String po = txt_po.getText();
        String siteEngineer = txt_siteEngineerName.getText();
        String siteEngineerTelephone = txt_siteEngineerTelephone.getText();
        String siteAdmin = txt_siteAdminName.getText();
        String siteAdminTelephone = txt_siteAdminTelephone.getText();
        String ourSiteEngineer = txt_OursiteEngineerName.getText();
        String ourSiteEngineerTelephone = txt_OursiteEngineerTelephone.getText();
        String ourSiteAmin = txt_ourSiteAdminName.getText();
        String ourSiteAdminTelephone = txt_ourSiteAdminTelephone.getText();
        String notes = txt_notes.getText();
        Sites newOne = new Sites(id, siteName, areaId, po, siteEngineer, siteEngineerTelephone, siteAdmin, siteAdminTelephone, ourSiteEngineer, ourSiteEngineerTelephone, ourSiteAmin, ourSiteAdminTelephone, notes);
            int isUpdated = Database.editSiteData(id,newOne);
             if (isUpdated>0){
                    Database.alertInformation("عملية ناجحة ");
                    clearTextFields();  
                    txt_siteName.setText(siteName);
                    searchAndDisplay();
                    siteTableView.requestFocus();
                    lbl_id.setText("");                    
                    invisible(edit_btn, false);
                    invisible(delete_btn, false);          
                    }
        }catch(SQLIntegrityConstraintViolationException  ex){//this exception is thrown because i put constarint on area name col be unique 
            Database.alertMessage("هناك موقع مسجة بتلك الاسم في قاعدة البيانات");
        }  catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());
        }catch(NumberFormatException ex )    {
          Database.alertMessage("قم بتحديد الموقع المراد تعديل بياناته من الجدول");
        }
   }
    //this method called every time user type on keyboad and search with area name in database 
    public void searchAndDisplay(){
        siteTableView.getItems().clear(); // to clear all old searched data on table view  after user change company name 
         Platform.runLater(()->{ // work as thread
          try {
            String siteName = txt_siteName.getText();
            if(siteName.equals("")){
            clearTextFields();   // clear old data in txtBox cause empty area name that i uses to search 
            lbl_id.setText("");
            invisible(edit_btn, false);
            invisible(delete_btn, false);
            invisible(add_btn, true);
            }
            ResultSet rs = Database.searchInSites(siteName);    //dabase query 
            while (rs.next()) {        //when match an have result         
             int id =   rs.getInt(1);
             String site_Name = rs.getString(2);
             int areaId = rs.getInt(3);
             String areaName = Database.gettingAreaName(areaId);
             String po = rs.getString(4);
             String siteEngineer = rs.getString(5);
             String siteEngineerTelephone = rs.getString(6);
             String siteAdmin = rs.getString(7);
             String siteAdminTelephone = rs.getString(8);
             String ourSiteEngineer = rs.getString(9);
             String ourSiteEngineerTelephone = rs.getString(10);
             String ourSiteAmin = rs.getString(11);
             String ourSiteAdminTelephone = rs.getString(12);
             String notes = rs.getString(13);
             Sites newOne = new Sites(id, site_Name, areaName, po, siteEngineer, siteEngineerTelephone, siteAdmin, siteAdminTelephone, ourSiteEngineer, ourSiteEngineerTelephone, ourSiteAmin, ourSiteAdminTelephone, notes);
             siteTableView.getItems().add(newOne);
             siteTableView.getSelectionModel().selectFirst();

            }
            if(siteTableView.getItems().size()== 1 ){ // if table have one record so take data in table and put it in txtBoxes
              lbl_id.setText(""+siteTableView.getItems().get(0).getId()); // get id and put it in label to be used in update and delete query 
              invisible(edit_btn, true);
              invisible(delete_btn, true);
              invisible(add_btn, false);
            }
          
            rs.close();
        }catch(IllegalArgumentException ex){
            // this eception happened when user empty textFiled 
            // so i want to 
            siteTableView.getItems().clear(); // clear pervious record
            loadAllSites(); // load all areas again 
            clearTextFields();
        }
        catch (Exception ex) {
            Database.alertMessage(ex.getMessage());
        }
        });
             invisible(add_btn, true);

    }
    //geting area name to insert into combobox 
    public void gettingAreaName(){
    
        try {
            ResultSet rs = Database.gettingAreaName();
            while (rs.next()) {                
                combo_areaName.getItems().add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());
        }
        
    }
    public void loadAllSites(){
       siteTableView.getItems().clear(); // to clear all old searched data on table view  after user change company name 
        try{
        ResultSet rs = Database.loadAllsites();
        while (rs.next()) {                
        int id =   rs.getInt(1);
        String siteName = rs.getString(2);
        int areaId = rs.getInt(3);
        String areaName = Database.gettingAreaName(areaId);
        String po = rs.getString(4);
        String siteEngineer = rs.getString(5);
        String siteEngineerTelephone = rs.getString(6);
        String siteAdmin = rs.getString(7);
        String siteAdminTelephone = rs.getString(8);
        String ourSiteEngineer = rs.getString(9);
        String ourSiteEngineerTelephone = rs.getString(10);
        String ourSiteAmin = rs.getString(11);
        String ourSiteAdminTelephone = rs.getString(12);
        String notes = rs.getString(13);;
        Sites newOne = new Sites(id, siteName, areaName, po, siteEngineer, siteEngineerTelephone, siteAdmin, siteAdminTelephone, ourSiteEngineer, ourSiteEngineerTelephone, ourSiteAmin, ourSiteAdminTelephone, notes);
        siteTableView.getItems().add(newOne);
            }
        }catch(SQLException ex){
            Database.alertMessage(ex.getMessage());
        }
    }   
    public  void selectSiteToTxt(){
             Sites selectedItem = siteTableView.getSelectionModel().getSelectedItem();
            lbl_id.setText(selectedItem.getId()+"");
            txt_siteName.setText(selectedItem.getSiteName());
            combo_areaName.getSelectionModel().select(selectedItem.getAreaName());
            txt_po.setText(selectedItem.getPO());
            txt_siteEngineerName.setText(selectedItem.getSiteEngineer());
            txt_siteEngineerTelephone.setText(selectedItem.getSiteEngineerTelephone());
            txt_siteAdminName.setText(selectedItem.getSiteAdmin());
            txt_siteAdminTelephone.setText(selectedItem.getSiteAdminTelephone());
            txt_OursiteEngineerName.setText(selectedItem.getOurSiteEngineer());
            txt_OursiteEngineerTelephone.setText(selectedItem.getOurSiteEngineerTelephone());
            txt_ourSiteAdminName.setText(selectedItem.getOurSiteAdmin());
            txt_ourSiteAdminTelephone.setText(selectedItem.getOurSiteAdminTelephone());
            txt_notes.setText(selectedItem.getNote());
            invisible(edit_btn, true);
            invisible(delete_btn, true);
            invisible(add_btn, false);
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
}   
//make button invisible 
public void invisible(Button button , boolean visibility){
    button.setVisible(visibility);
}
}
