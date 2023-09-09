
package philoplus.FXMLFILES;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import philoplus.philoPlusClasses.Areas;
import philoplus.philoPlusClasses.Database;
import philoplus.philoPlusClasses.Technician;
import javafx.scene.layout.GridPane;
import static philoplus.philoPlusClasses.Database.autoNumber;

/**
 * FXML Controller class
 *
 * @author Seha
 */
public class TechnicalCardController implements Initializable {
    @FXML
    private GridPane parentGrid;
    @FXML
    private TextField txt_technicianName;
    @FXML
    private Label lbl_id;
    @FXML
    private TextField txt_nationalId;
    @FXML
    private TextField txt_telephones;
    @FXML
    private TextField txt_notes;
    @FXML
    private Button add_btn;
    @FXML
    private Button edit_btn;
    @FXML
    private Button delete_btn;
    @FXML
    private TableView<Technician> technician_aides_tableView;
    @FXML
    private TableColumn<Technician,Integer> col_id;
    @FXML
    private TableColumn<Technician, String> col_isTechnician;
    @FXML
    private TableColumn<Technician,String> col_fullName;
    @FXML
    private TableColumn<Technician, Integer> col_technicainId;
    @FXML
    private TableColumn<Technician,Integer> col_nationalId;
    @FXML
    private TableColumn<Technician, String> col_telephones;
    @FXML
    private TableColumn<Technician,Date> col_start_work;
    @FXML
    private TableColumn<Technician,Integer> col_experienceYear;
    @FXML
    private TableColumn<Technician,String> col_notes;
    @FXML
    private TextField txt_imagePath;
    @FXML
    private Button imgAdd_btn;
    @FXML
    private Button showImg_btn;
    @FXML
    private TextField txt_experienceYear;
    @FXML
    private DatePicker date_startWork;
    List<String> suggestionsOfTechnical = new ArrayList<>();
    PauseTransition pause ;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         //Start column intalisation of table view areas
            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_isTechnician.setCellValueFactory(new PropertyValueFactory<>("isTechnician"));
            col_fullName.setCellValueFactory(new PropertyValueFactory<>("name"));
            col_nationalId.setCellValueFactory(new PropertyValueFactory<>("nationalId"));
            col_telephones.setCellValueFactory(new PropertyValueFactory<>("telephones"));
            col_start_work.setCellValueFactory(new PropertyValueFactory<>("startWork"));
            col_experienceYear.setCellValueFactory(new PropertyValueFactory<>("experienceYear"));
            col_notes.setCellValueFactory(new PropertyValueFactory<>("notes"));
         //End column intalisation of table view areas  
             pause = new PauseTransition(Duration.millis(200));
             txt_technicianName.setOnKeyReleased(event->{
              pause.playFromStart();    // Restart the pause timer every time a key is typed   
             if(event.getCode().toString().equals("DOWN")){
                 technician_aides_tableView.requestFocus();
                 technician_aides_tableView.getSelectionModel().selectFirst();
             }
             if(event.getCode().toString().equals("ESCAPE")){
                 txt_technicianName.setText("");
                 loadAllTechnicianData();
                 clearTextFields();
                 txt_technicianName.requestFocus();
             }
             if(event.getCode().toString().equals("ENTER")){
                 technician_aides_tableView.requestFocus();
                 technician_aides_tableView.getSelectionModel().selectFirst();
             }      
           });
           technician_aides_tableView.setOnKeyReleased(event->{
           if(event.getCode().toString().equals("ENTER")){
                 txt_technicianName.requestFocus();
                 selectItemTechnicalToTxtBox();
             }
           if(event.getCode().toString().equals("ESCAPE")){
               txt_technicianName.setText("");
               loadAllTechnicianData();
               clearTextFields();
               txt_technicianName.requestFocus();
             }
           });
                    pause.setOnFinished(event -> {
                     searchAndDisplay();                  // This code will be executed after the pause duration has elapsed       
              });       
                   loadAllTechnicianData();      // load all technicain info to table view 
                   invisible(edit_btn, false);
                   invisible(delete_btn, false);  
    }    
     public void insertNewTechnical_adies(){
         try {
                int id = autoNumber("`technician-aides`", "id");
                String Istechnical = "فني";
                String fullName      = txt_technicianName.getText().trim();
                if(fullName.equals("")){
                Database.alertMessage("لابد من ادخال اسم الفني ");
                return;
                }
                String nationaId    = txt_nationalId.getText();
                if(nationaId.equals("")){
                Database.alertInformation("ادخل رقم القومي للفني ");
                return;
                }
                String telephone    = txt_telephones.getText();
                if(date_startWork.getValue() == null);{
                    date_startWork.setValue(LocalDate.now());    
                }
                Date startWork       = Date.valueOf(date_startWork.getValue());
                int experienceYear = Integer.parseInt(txt_experienceYear.getText());
                String notes          = txt_notes.getText();
                Technician getOne  = new Technician(id, Istechnical, fullName, nationaId, telephone, startWork, experienceYear, notes);
               int isInserted = Database.insertNewTechnical(getOne);        
                if(isInserted > 0){ //to make sure that recored is inserted
                Database.alertInformation("عملية ناجحة ");
                searchAndDisplay(); 
                txt_technicianName.requestFocus();
            }
        }catch(IllegalArgumentException ex){ // i thrown exception in constructor method  of campany class if variable full name is empty 
             Database.alertMessage(ex.getMessage());
        } catch(SQLIntegrityConstraintViolationException  ex){//this exception is thrown because i put constarint on full_name col be unique 
             System.err.println(ex.getMessage());
            Database.alertMessage("هناك فني مسجل بتلك الاسم في قاعدة البيانات");
        }  catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());  
        }catch(NullPointerException ex ){
            Database.alertMessage("ادخل التاريخ عمل الفني بشكل صحيح");
        }
         catch(Exception ex){
             Database.alertInformation(ex.getMessage());
         }
         invisible(edit_btn, false);
         invisible(delete_btn, false);
    }
      public void updateTechnicalData(){
      try {
           
                int id = Integer.parseInt(lbl_id.getText());
                String Istechnical = "فني";
                String fullName      = txt_technicianName.getText().trim();
                if(fullName.equals("")){
                Database.alertMessage("لابد من ادخال اسم الفني ");
                return;
                }
                String nationaId    = txt_nationalId.getText();
                if(nationaId.equals("")){
                Database.alertInformation("ادخل رقم القومي للفني ");
                return;
                }
                String telephone    = txt_telephones.getText();
                if(date_startWork.getValue() == null);{
                    date_startWork.setValue(LocalDate.now());    
                }
                Date startWork       = Date.valueOf(date_startWork.getValue());
                int experienceYear = Integer.parseInt(txt_experienceYear.getText());
                String notes          = txt_notes.getText();
                Technician getOne  = new Technician(id, Istechnical, fullName, nationaId, telephone, startWork, experienceYear, notes);
                System.out.println("done");
                int isUpdated = Database.editTechnicainData(id,getOne);
                if (isUpdated>0){
                    Database.alertInformation("عملية ناجحة ");
                    loadAllTechnicianData();
                    clearTextFields();  
                    txt_technicianName.setText(fullName);
                    searchAndDisplay();
                    technician_aides_tableView.requestFocus();
                    lbl_id.setText("");                    
                    invisible(edit_btn, false);
                    invisible(delete_btn, false);          
                    }
        }catch(SQLIntegrityConstraintViolationException  ex){//this exception is thrown because i put constarint on full name  col be unique 
            Database.alertMessage("هناك فني مسجل بتلك الاسم في قاعدة البيانات");
        }  catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());
        }catch(NumberFormatException ex )    {
          Database.alertMessage("قم بتحديد الفني المراد تعديل بياناته من الجدول");
        }
   }
                // METHOD TO DELETE RECORD FROM DATABASE
   public void DeleteRecord(){
           try {     
            int id  = Integer.parseInt(lbl_id.getText());
            Database.deleteTechnical(id);
            Database.alertInformation("عملية ناجحة ");
            loadAllTechnicianData();
            clearTextFields();
        } catch (SQLException ex) {
              if (ex.getErrorCode() == 1451) {// this code error is thrown when user try to delete parent row used as refrenced key to forign key 
        Database.alertMessage("لا تسطيع مسحه لانه مرتبط بكثير من بيانات ");
    } else {
            Database.alertMessage(ex.getMessage());
    }
        }catch(NumberFormatException ex )    {
          Database.alertMessage("قم بتحديد الفني المراد مسحه بياناته من الجدول");
        }
            invisible(edit_btn, false);
            invisible(delete_btn, false);
            lbl_id.setText("");
            txt_technicianName.requestFocus();
   }
    public void searchAndDisplay(){
            technician_aides_tableView.getItems().clear(); // to clear all old searched data on table view  after user change techncian  name 
            Platform.runLater(()->{ // work as thread
          try {
            String technicalName = txt_technicianName.getText();
            if(technicalName.equals("")){
            clearTextFields();   // clear old data in txtBox cause empty techncian name that i uses to search 
            lbl_id.setText("");
            invisible(edit_btn, false);
            invisible(delete_btn, false);
            invisible(add_btn, true);
            }
            ResultSet rs = Database.searchInTechnican(technicalName);    //dabase query 
            while (rs.next()) {        //when match an have result         
               int id                    = rs.getInt(1);
                String Istechnical = rs.getString(2);
                String fullName      = rs.getString(3);
                String nationaId    = rs.getString(5);
                String telephone    = rs.getString(6);
                Date startWork       = rs.getDate(7);
                int experienceYear = rs.getInt(8);
                String notes          = rs.getString(9);
                Technician getOne = new Technician(id, Istechnical, fullName, nationaId, telephone, startWork, experienceYear, notes);
                technician_aides_tableView.getItems().add(getOne);     
               technician_aides_tableView.getSelectionModel().selectFirst();

            }
            if(technician_aides_tableView.getItems().size()== 1 ){ // if table have one record so take data in table and put it in txtBoxes
              lbl_id.setText(""+technician_aides_tableView.getItems().get(0).getId()); // get id and put it in label to be used in update and delete query 
              invisible(edit_btn, true);
              invisible(delete_btn, true);
              invisible(add_btn, false);
            }
          
            rs.close();
        }catch(IllegalArgumentException ex){
            // this eception happened when user empty textFiled 
            // so i want to 
            technician_aides_tableView.getItems().clear(); // clear pervious record
           loadAllTechnicianData(); // load all techncian  again 
            clearTextFields();
        }
        catch (Exception ex) {
            Database.alertMessage(ex.getMessage());
        }
        });
             invisible(add_btn, true);
    }
   
  public void loadAllTechnicianData()  {
            technician_aides_tableView.getItems().clear();// ensure table are empty from records 
            suggestionsOfTechnical.clear();
         Platform.runLater(()->{
          try {
              //select * all
            ResultSet rs = Database.searchInTechnican(); 
            while (rs.next()) {                
                int id                    = rs.getInt(1);
                String Istechnical = rs.getString(2);
                String fullName      = rs.getString(3);
                int technical_id = rs.getInt(4);
                String nationaId    = rs.getString(5);
                String telephone    = rs.getString(6);
                Date startWork       = rs.getDate(7);
                int experienceYear = rs.getInt(8);
                String notes          = rs.getString(9);
                Technician getOne = new Technician(id, Istechnical, fullName, nationaId, telephone, startWork, experienceYear, notes);
                technician_aides_tableView.getItems().add(getOne); 
                suggestionsOfTechnical.add(fullName);
            }
            rs.close();
        }
        catch (Exception ex) {
            Database.alertMessage(ex.getMessage());
        }
        });
  
  }
     // i use this function to get selectItem data and put it to txtboxes
   public void selectItemTechnicalToTxtBox(){
            Technician selectedItem = technician_aides_tableView.getSelectionModel().getSelectedItem();
            lbl_id.setText(selectedItem.getId()+"");
            txt_technicianName.setText(selectedItem.getName());
            txt_nationalId.setText(selectedItem.getNationalId());
            txt_telephones.setText(selectedItem.getTelephones()); 
            date_startWork.setValue(selectedItem.getStartWork().toLocalDate());
            txt_notes.setText(selectedItem.getNotes());
            invisible(edit_btn, true);
            invisible(delete_btn, true);
            invisible(add_btn, false);
   }
   

     //make button invisible 
public void invisible(Button button , boolean visibility){
    button.setVisible(visibility);
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
            txt_experienceYear.setText("0"); // when user not enter experience year not make exception when use parse integer
}
}
