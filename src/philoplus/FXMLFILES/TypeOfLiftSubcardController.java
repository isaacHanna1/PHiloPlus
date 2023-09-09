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
import philoplus.philoPlusClasses.Database;
import static philoplus.philoPlusClasses.Database.autoNumber;

/**
 * FXML Controller class
 *
 * @author Seha
 */
public class TypeOfLiftSubcardController implements Initializable {

    @FXML
    private GridPane parentGrid;
    @FXML
    private TextField txt_liftType;
    @FXML
    private Label lbl_id;
    @FXML
    private TextField txt_lnote;
    @FXML
    private Button add_Btn;
    @FXML
    private Button edit_btn;
    @FXML
    private Button delete_btn;
    @FXML
    private TableView<TypeOfLifts> liftTypeTableView;
    @FXML
    private TableColumn<TypeOfLifts, Integer> col_id;
    @FXML
    private TableColumn<TypeOfLifts, String> lift_type;
    @FXML
    private TableColumn<TypeOfLifts, String> lift_note;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    //Start column intalisation of table view typeOfLifts
            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            lift_type.setCellValueFactory(new PropertyValueFactory<>("tyoeOfLift"));
            lift_note.setCellValueFactory(new PropertyValueFactory<>("note"));       
    //==> End column intalisation of table view typeOfLifts    
      PauseTransition pause = new PauseTransition(Duration.millis(200));
  // i make event to get area data when user typing 
           txt_liftType.setOnKeyReleased(event->{
               pause.playFromStart();    // Restart the pause timer every time a key is typed   
             if(event.getCode().toString().equals("DOWN")){
                 liftTypeTableView.requestFocus();
                 liftTypeTableView.getSelectionModel().selectFirst();
             }
             if(event.getCode().toString().equals("ESCAPE")){
                 txt_liftType.setText("");
                 loadAllLiftTypeData();
                 clearTextFields();
                 txt_liftType.requestFocus();
             }
             if(event.getCode().toString().equals("ENTER")){
                 liftTypeTableView.requestFocus();
                 liftTypeTableView.getSelectionModel().selectFirst();
             }
               
           });
           liftTypeTableView.setOnKeyReleased(event->{
           if(event.getCode().toString().equals("ENTER")){
                 txt_liftType.requestFocus();
                 selectItemToTxtBox();
             }
           if(event.getCode().toString().equals("ESCAPE")){
               txt_liftType.setText("");
               loadAllLiftTypeData();
               clearTextFields();
               txt_liftType.requestFocus();
             }
           });
                    pause.setOnFinished(event -> {
                     searchAndDisplay();                  // This code will be executed after the pause duration has elapsed       
              });       
                   loadAllLiftTypeData();      // load all area info to table view 
                   invisible(edit_btn, false);
                   invisible(delete_btn, false);
    
        
    }    
    public void insertNewType(){
        try {
            int id = autoNumber("`lift-type`", "id");
            String liftTypeDetails = txt_liftType.getText();
             String notes = txt_lnote.getText();
             TypeOfLifts type = new TypeOfLifts(id, liftTypeDetails, notes);
             int isInserted = Database.insertTypeOfLifts(type); // return int 1 if inserted completed well 
             if(isInserted > 0){ //to make sure that recored is inserted
             Database.alertInformation("عملية ناجحة ");
             searchAndDisplay();
             txt_liftType.requestFocus();
            }
        }catch(IllegalArgumentException ex){ // i thrown exception in constructor method  of campany class if variable comapnyName is empty 
            Database.alertMessage("لا يمكن ان يكون حقل نوع المصعد فارغ");
        } catch(SQLIntegrityConstraintViolationException  ex){//this exception is thrown because i put constarint on area name col be unique 
            Database.alertMessage("هناك نوع  مصعد مسجل بتلك الاسم في قاعدة البيانات");
        }  catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());  
        }
         catch(Exception ex){
             Database.alertInformation(ex.getMessage());
         }
         invisible(edit_btn, false);
         invisible(delete_btn, false);
    }
         public void updateAreaData(){
      try {
            int id = Integer.parseInt(lbl_id.getText());
             String liftTypeDetails = txt_liftType.getText();
             String notes = txt_lnote.getText();
             TypeOfLifts type = new TypeOfLifts(id, liftTypeDetails, notes);
            int isUpdated = Database.editTypeOfLiftData(id,type);
             if (isUpdated>0){
                    Database.alertInformation("عملية ناجحة ");
                    clearTextFields();  
                    txt_liftType.setText(liftTypeDetails);
                    searchAndDisplay();
                    liftTypeTableView.requestFocus();
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
    public void DeleteRecord(){
         try {     
            int id  = Integer.parseInt(lbl_id.getText());
            Database.deleteTypeOfDetails(id);
            Database.alertInformation("عملية ناجحة ");
            loadAllLiftTypeData();
            clearTextFields();
        } catch (SQLException ex) {
              if (ex.getErrorCode() == 1451) {// this code error is thrown when user try to delete parent row used as refrenced key to forign key 
        Database.alertMessage("لا تسطيع مسحه لانه مرتبط بكثير من بيانات ");
            }
              else{
                  Database.alertInformation(ex.getMessage());
              }
        }catch(NumberFormatException ex )    {
          Database.alertMessage("قم بتحديد النوع المراد مسح بياناته من الجدول");
        }
            invisible(edit_btn, false);
            invisible(delete_btn, false);
            lbl_id.setText("");
            txt_liftType.requestFocus();
   }
    
     //this method called every time user type on keyboad and search with area name in database 
    public void searchAndDisplay(){
        liftTypeTableView.getItems().clear(); // to clear all old searched data on table view  after user change company name 
         Platform.runLater(()->{ // work as thread
          try {
            String typeOfLift = txt_liftType.getText();
            if(typeOfLift.equals("")){
            clearTextFields();   // clear old data in txtBox cause empty area name that i uses to search 
            lbl_id.setText("");
            invisible(edit_btn, false);
            invisible(delete_btn, false);
            invisible(add_Btn, true);
            }
            ResultSet rs = Database.searchIntoTypeOfLift(typeOfLift);    //dabase query 
            while (rs.next()) {        //when match an have result         
                int id                    = rs.getInt(1);
                String typeLiftDetails = rs.getString(2);
                String note = rs.getString(3);
                TypeOfLifts getOne = new TypeOfLifts(id,typeLiftDetails, note);
                liftTypeTableView.getItems().add(getOne);//added new area in table view 
               liftTypeTableView.getSelectionModel().selectFirst();

            }
            if(liftTypeTableView.getItems().size()== 1 ){ // if table have one record so take data in table and put it in txtBoxes
              lbl_id.setText(""+liftTypeTableView.getItems().get(0).getId()); // get id and put it in label to be used in update and delete query 
              invisible(edit_btn, true);
              invisible(delete_btn, true);
              invisible(add_Btn, false);
            }
          
            rs.close();
        }catch(IllegalArgumentException ex){
            // this eception happened when user empty textFiled 
            // so i want to 
            liftTypeTableView.getItems().clear(); // clear pervious record
           loadAllLiftTypeData(); // load all areas again 
            clearTextFields();
        }
        catch (Exception ex) {
            Database.alertMessage(ex.getMessage());
        }
        });
             invisible(add_Btn, true);

    }
     public void loadAllLiftTypeData(){
       liftTypeTableView.getItems().clear();// ensure table are empty from records 
         Platform.runLater(()->{
          try {
              //select * all
            ResultSet rs = Database.loadAllTypeDetailsOfLift(); 
            while (rs.next()) {                
                int id                    = rs.getInt(1);
                String typeOfLift = rs.getString(2);
                String note = rs.getString(3);
                TypeOfLifts getOne = new TypeOfLifts(id, typeOfLift, note);
                liftTypeTableView.getItems().add(getOne);       
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
   public void selectItemToTxtBox(){
            TypeOfLifts selectedItem = liftTypeTableView.getSelectionModel().getSelectedItem();
            lbl_id.setText(selectedItem.getId()+"");
            txt_liftType.setText(selectedItem.getTyoeOfLift());
            txt_lnote.setText(selectedItem.getNote());
            invisible(edit_btn, true);
            invisible(delete_btn, true);
            invisible(add_Btn, false);
   }
   //make button invisible 
public void invisible(Button button , boolean visibility){
    button.setVisible(visibility);
}
}
