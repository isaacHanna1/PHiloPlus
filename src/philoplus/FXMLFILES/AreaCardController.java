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
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import philoplus.philoPlusClasses.Areas;
import philoplus.philoPlusClasses.Companies;
import philoplus.philoPlusClasses.Database;
import static philoplus.philoPlusClasses.Database.autoNumber;

/**
 * FXML Controller class
 *
 * @author Seha
 */
public class AreaCardController implements Initializable {

    @FXML
    private GridPane parentGrid;
    @FXML
    private TextField txt_country;
    @FXML
    private Label lbl_id;
    @FXML
    private TextField txt_province;
    @FXML
    private TextField txt_area;
    @FXML
    private TextField txt_notes;
    @FXML
    private TableView<Areas> areaTableView;
    @FXML
    private TableColumn<Areas, Integer> col_id;
    @FXML
    private TableColumn<Areas, String> col_countryName;
    @FXML
    private TableColumn<Areas, String> col_province;
    @FXML
    private TableColumn<Areas, String> col_area;
    @FXML
    private TableColumn<Areas, String> col_notes;
  
    @FXML
    private Button add_btn;
    @FXML
    private Button delete_btn;
    @FXML
    private Button edit_btn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 //Start column intalisation of table view areas
            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_countryName.setCellValueFactory(new PropertyValueFactory<>("country"));
            col_province.setCellValueFactory(new PropertyValueFactory<>("province"));
            col_area.setCellValueFactory(new PropertyValueFactory<>("areaName"));
            col_notes.setCellValueFactory(new PropertyValueFactory<>("notes"));       
   //==> End column intalisation of table view areas    
        PauseTransition pause = new PauseTransition(Duration.millis(200));
  // i make event to get area data when user typing 
           txt_area.setOnKeyReleased(event->{
               pause.playFromStart();    // Restart the pause timer every time a key is typed   
             if(event.getCode().toString().equals("DOWN")){
                 areaTableView.requestFocus();
                 areaTableView.getSelectionModel().selectFirst();
             }
             if(event.getCode().toString().equals("ESCAPE")){
                 txt_area.setText("");
                 loadAllAreasData();
                 clearTextFields();
                 txt_area.requestFocus();
             }
             if(event.getCode().toString().equals("ENTER")){
                 areaTableView.requestFocus();
                 areaTableView.getSelectionModel().selectFirst();
             }
               
           });
           areaTableView.setOnKeyReleased(event->{
           if(event.getCode().toString().equals("ENTER")){
                 txt_area.requestFocus();
                 selectItemAreaToTxtBox();
             }
           if(event.getCode().toString().equals("ESCAPE")){
               txt_area.setText("");
               loadAllAreasData();
               clearTextFields();
               txt_area.requestFocus();
             }
           });
                    pause.setOnFinished(event -> {
                     searchAndDisplay();                  // This code will be executed after the pause duration has elapsed       
              });       
                   loadAllAreasData();      // load all area info to table view 
                   invisible(edit_btn, false);
                   invisible(delete_btn, false);
    }
    public void insertNewArea(){
         try {
            int id = autoNumber("area", "id");
             String country = txt_country.getText();
             String province = txt_province.getText();
             String area = txt_area.getText();
             String notes = txt_notes.getText();
             Areas newArea = new Areas(id, country, province, area, notes);  
             int isInserted = Database.insertNewAreas(newArea); // return int 1 if inserted completed well 
             if(isInserted > 0){ //to make sure that recored is inserted
             Database.alertInformation("عملية ناجحة ");
             searchAndDisplay();
             txt_area.requestFocus();
            }
        }catch(IllegalArgumentException ex){ // i thrown exception in constructor method  of campany class if variable comapnyName is empty 
            Database.alertMessage("لا يمكن ان يكون حقل اسم المنطقة فارغ");
        } catch(SQLIntegrityConstraintViolationException  ex){//this exception is thrown because i put constarint on area name col be unique 
            Database.alertMessage("هناك منطقة مسجلة بتلك الاسم في قاعدة البيانات");
        }  catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());  
        }
         catch(Exception ex){
             Database.alertInformation(ex.getMessage());
         }
         invisible(edit_btn, false);
         invisible(delete_btn, false);
    }
        //this method called every time user type on keyboad and search with area name in database 
    public void searchAndDisplay(){
        areaTableView.getItems().clear(); // to clear all old searched data on table view  after user change company name 
         Platform.runLater(()->{ // work as thread
          try {
            String areaName = txt_area.getText();
            if(areaName.equals("")){
            clearTextFields();   // clear old data in txtBox cause empty area name that i uses to search 
            lbl_id.setText("");
            invisible(edit_btn, false);
            invisible(delete_btn, false);
            invisible(add_btn, true);
            }
            ResultSet rs = Database.searchInAreas(areaName);    //dabase query 
            while (rs.next()) {        //when match an have result         
                int id                    = rs.getInt(1);
                String country = rs.getString(2);
                String province       = rs.getString(3);
                String area     = rs.getString(4);
                String note = rs.getString(5);
                Areas getOne = new Areas(id, country, province, area, note);
                areaTableView.getItems().add(getOne);//added new area in table view 
               areaTableView.getSelectionModel().selectFirst();

            }
            if(areaTableView.getItems().size()== 1 ){ // if table have one record so take data in table and put it in txtBoxes
              lbl_id.setText(""+areaTableView.getItems().get(0).getId()); // get id and put it in label to be used in update and delete query 
              invisible(edit_btn, true);
              invisible(delete_btn, true);
              invisible(add_btn, false);
            }
          
            rs.close();
        }catch(IllegalArgumentException ex){
            // this eception happened when user empty textFiled 
            // so i want to 
            areaTableView.getItems().clear(); // clear pervious record
           loadAllAreasData(); // load all areas again 
            clearTextFields();
        }
        catch (Exception ex) {
            Database.alertMessage(ex.getMessage());
        }
        });
             invisible(add_btn, true);

    }
       public void updateAreaData(){
      try {
            int id = Integer.parseInt(lbl_id.getText());
            String country = txt_country.getText();
            String province = txt_province.getText();
            String area = txt_area.getText();
            String notes  =txt_notes.getText();
            Areas updatedObject = new Areas(id, country, province, area, notes);
            int isUpdated = Database.editAreaData(id,updatedObject);
             if (isUpdated>0){
                    Database.alertInformation("عملية ناجحة ");
                    clearTextFields();  
                    txt_area.setText(area);
                    searchAndDisplay();
                    areaTableView.requestFocus();
                    lbl_id.setText("");                    
                    invisible(edit_btn, false);
                    invisible(delete_btn, false);          
                    }
        }catch(SQLIntegrityConstraintViolationException  ex){//this exception is thrown because i put constarint on area name col be unique 
            Database.alertMessage("هناك شركة مسجلة بتلك الاسم في قاعدة البيانات");
        }  catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());
        }catch(NumberFormatException ex )    {
          Database.alertMessage("قم بتحديد الشركة المراد تعديل بياناتها من الجدول");
        }
   }
          // METHOD TO DELETE RECORD FROM DATABASE
   public void DeleteRecord(){
           try {     
            int id  = Integer.parseInt(lbl_id.getText());
            Database.deleteRecordArea(id);
            Database.alertInformation("عملية ناجحة ");
            loadAllAreasData();
            clearTextFields();
        } catch (SQLException ex) {
              if (ex.getErrorCode() == 1451) {// this code error is thrown when user try to delete parent row used as refrenced key to forign key 
        Database.alertMessage("لا تسطيع مسحه لانه مرتبط بكثير من بيانات ");
            }
              else{
                  Database.alertInformation(ex.getMessage());
              }
        }catch(NumberFormatException ex )    {
          Database.alertMessage("قم بتحديد الشركة المراد مسح بياناتها من الجدول");
        }
            invisible(edit_btn, false);
            invisible(delete_btn, false);
            lbl_id.setText("");
            txt_area.requestFocus();
   }
    public void loadAllAreasData(){
       areaTableView.getItems().clear();// ensure table are empty from records 
         Platform.runLater(()->{
          try {
              //select * all
            ResultSet rs = Database.searchInAreas(); 
            while (rs.next()) {                
                int id                    = rs.getInt(1);
                String country = rs.getString(2);
                String province       = rs.getString(3);
                String area     = rs.getString(4);
                String note = rs.getString(5);
                Areas getOne = new Areas(id, country, province, area, note);
                areaTableView.getItems().add(getOne);       
            }
            rs.close();
        }
        catch (Exception ex) {
            Database.alertMessage(ex.getMessage());
        }
        });
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
    // i use this function to get selectItem data and put it to txtboxes
   public void selectItemAreaToTxtBox(){
            Areas selectedItem = areaTableView.getSelectionModel().getSelectedItem();
            lbl_id.setText(selectedItem.getId()+"");
            txt_country.setText(selectedItem.getCountry());
            txt_province.setText(selectedItem.getProvince());
            txt_area.setText(selectedItem.getAreaName()); 
            txt_notes.setText(selectedItem.getNotes());
            invisible(edit_btn, true);
            invisible(delete_btn, true);
            invisible(add_btn, false);
   }
   //make button invisible 
public void invisible(Button button , boolean visibility){
    button.setVisible(visibility);
}
}
