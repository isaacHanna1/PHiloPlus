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
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import philoplus.philoPlusClasses.AutoCompleteComboBox;
import philoplus.philoPlusClasses.Database;
import static philoplus.philoPlusClasses.Database.autoNumber;
import philoplus.philoPlusClasses.Technician;
import philoplus.philoPlusClasses.adie;

/**
 * FXML Controller class
 *
 * @author Seha
 */
public class AdieCardController implements Initializable {

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
    private TextField txt_imagePath;
    @FXML
    private Button imgAdd_btn;
    @FXML
    private Button showImg_btn;
    @FXML
    private DatePicker date_startWork;
    @FXML
    private TextField txt_experienceYear;
    @FXML
    private TextField txt_notes;
    @FXML
    private Button add_btn;
    @FXML
    private Button edit_btn;
    @FXML
    private Button delete_btn;
    @FXML
    private TableView<adie> technician_aides_tableView;
    @FXML
    private TableColumn<adie,Integer> col_id;
    @FXML
    private TableColumn<adie, String> col_isTechnician;
    @FXML
    private TableColumn<adie,String> col_fullName;
    @FXML
    private TableColumn<adie, String> col_technicianName;
    @FXML
    private TableColumn<adie, String> col_nationalId;
    @FXML
    private TableColumn<adie, String> col_telephones;
    @FXML
    private TableColumn<adie, Date> col_start_work;
    @FXML
    private TableColumn<adie, Integer> col_experienceYear;
    @FXML
    private TableColumn<adie, String> col_notes;
    @FXML
    private AutoCompleteComboBox<String> comboBox_technicalName;
    List<String> suggestTechnicalName = new ArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        comboBox_technicalName.setEditable(true); // to unable user to use keyboard in combobox 
        getAlltechnicalName();// get all technical name 
//   comboBox_technicalName.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
//    String filter = newValue;
//    List<String> filteredList = suggestTechnicalName.stream().filter(item -> item.startsWith(filter))
//            .collect(Collectors.toList());
//    comboBox_technicalName.getItems().setAll(filteredList);
//});

   //Start column intalisation of table view areas
            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_isTechnician.setCellValueFactory(new PropertyValueFactory<>("isTechnician"));
            col_fullName.setCellValueFactory(new PropertyValueFactory<>("name"));
            col_technicianName.setCellValueFactory(new PropertyValueFactory<>("technicalName"));
            col_nationalId.setCellValueFactory(new PropertyValueFactory<>("nationalId"));
            col_telephones.setCellValueFactory(new PropertyValueFactory<>("telephones"));
            col_start_work.setCellValueFactory(new PropertyValueFactory<>("startWork"));
            col_experienceYear.setCellValueFactory(new PropertyValueFactory<>("experienceYear"));
            col_notes.setCellValueFactory(new PropertyValueFactory<>("notes"));
         //End column intalisation of table view areas  
         
        PauseTransition pause = new PauseTransition(Duration.millis(200));
             txt_technicianName.setOnKeyReleased(event->{
                 // Restart the pause timer every time a key is typed   
             if(event.getCode().toString().equals("DOWN")){
                 technician_aides_tableView.requestFocus();
                 technician_aides_tableView.getSelectionModel().selectFirst();
             }
             if(event.getCode().toString().equals("ESCAPE")){
               txt_technicianName.setText("");
               loadAllAdieData();
               clearTextFields();
               txt_technicianName.requestFocus();
             }
             if(event.getCode().toString().equals("ENTER")){
                 technician_aides_tableView.requestFocus();
                 technician_aides_tableView.getSelectionModel().selectFirst();
             }
             pause.playFromStart();    // Restart the pause timer every time a key is typed   

           });
           technician_aides_tableView.setOnKeyReleased(event->{
           if(event.getCode().toString().equals("ENTER")){
                 txt_technicianName.requestFocus();
                 selectItemAdieToTxtBox();
             }
           if(event.getCode().toString().equals("ESCAPE")){
               txt_technicianName.setText("");
               clearTextFields();
               txt_technicianName.requestFocus();
               loadAllAdieData();
             }
           });
                    pause.setOnFinished(event -> {
                     searchAndDisplay();                  // This code will be executed after the pause duration has elapsed       
              });       
                   loadAllAdieData();      // load all adie info to table view 
                   invisible(edit_btn, false);
                   invisible(delete_btn, false);  
    }    
    @FXML
    private void insertNewTechnical_adies(ActionEvent event) {
           try {
                int id = autoNumber("`technician-aides`", "id");
                String Istechnical = "مساعد";
                String fullName      = txt_technicianName.getText().trim();
                String technicalName = comboBox_technicalName.getSelectionModel().getSelectedItem();
                if(technicalName.equals("")){
                Database.alertMessage("لابد من ادخال اسم مساعد ");
                return;
                }
                System.out.println("1");
                int technicainId =  Database.getTechnicalId(technicalName);
                if(fullName.equals("")){
                Database.alertMessage("لابد من ادخال اسم مساعد ");
                return;
                }
                String nationaId    = txt_nationalId.getText();
                if(nationaId.equals("")){
                Database.alertInformation("ادخل رقم القومي للمساعد ");
                return;
                }
                String telephone    = txt_telephones.getText();
                if(date_startWork.getValue() == null);{
                    date_startWork.setValue(LocalDate.now());    
                }
                Date startWork       = Date.valueOf(date_startWork.getValue());
                int experienceYear = Integer.parseInt(txt_experienceYear.getText());
                String notes          = txt_notes.getText();
                adie getOne  = new adie(id, Istechnical, fullName, technicainId, nationaId, telephone, startWork, experienceYear, notes);
               int isInserted = Database.insertNewAdie(getOne);        
                if(isInserted > 0){ //to make sure that recored is inserted
                Database.alertInformation("عملية ناجحة ");
                searchAndDisplay(); 
                txt_technicianName.requestFocus();
            }
        }catch(IllegalArgumentException ex){ // i thrown exception in constructor method  of adie class if variable adie is empty 
             Database.alertMessage(ex.getMessage());
        } catch(SQLIntegrityConstraintViolationException  ex){//this exception is thrown because i put constarint on adie name col be unique 
            Database.alertMessage("هناك مساعد مسجل بتلك الاسم في قاعدة البيانات");
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
      public void updateAdieData(){
      try {
           
                int id = Integer.parseInt(lbl_id.getText());
                String Istechnical = "مساعد";
                String fullName      = txt_technicianName.getText().trim();
                if(fullName.equals("")){
                Database.alertMessage("لابد من ادخال اسم الفني ");
                return;
                }
                String technicaianName = comboBox_technicalName.getSelectionModel().getSelectedItem();
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
                adie getOne  = new adie(id, Istechnical, fullName, Database.getTechnicalId(technicaianName), nationaId, telephone, startWork, experienceYear, notes);
                System.out.println("done");
                int isUpdated = Database.editAdieData(id,getOne);
                if (isUpdated>0){
                    Database.alertInformation("عملية ناجحة ");
                    clearTextFields();  
                    txt_technicianName.setText(fullName);
                    searchAndDisplay();
                    technician_aides_tableView.requestFocus();
                    lbl_id.setText("");                    
                    invisible(edit_btn, false);
                    invisible(delete_btn, false);          
                    }
        }catch(SQLIntegrityConstraintViolationException  ex){//this exception is thrown because i put constarint on adie col be unique 
            Database.alertMessage("هناك المساعد مسجل بتلك الاسم في قاعدة البيانات");
        }  catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());
        }catch(NumberFormatException ex )    {
          Database.alertMessage("قم بتحديد المساعد المراد تعديل بياناته من الجدول");
        }
   }
        public void DeleteRecord(){
           try {     
            int id  = Integer.parseInt(lbl_id.getText());
            Database.deleteAdie(id);
            Database.alertInformation("عملية ناجحة ");
            loadAllAdieData();
            clearTextFields();
        } catch (SQLException ex) {
        }catch(NumberFormatException ex )    {
          Database.alertMessage("قم بتحديد المساعد المراد مسحه بياناته من الجدول");
        }
            invisible(edit_btn, false);
            invisible(delete_btn, false);
            lbl_id.setText("");
            txt_technicianName.requestFocus();
   }
    public void searchAndDisplay(){
            technician_aides_tableView.getItems().clear(); // to clear all old searched data on table view  after user change adie name 
            Platform.runLater(()->{ // work as thread
          try {
            String AdieName = txt_technicianName.getText();
            if(AdieName.equals("")){
            clearTextFields();   // clear old data in txtBox cause empty area name that i uses to search 
            lbl_id.setText("");
            invisible(edit_btn, false);
            invisible(delete_btn, false);
            invisible(add_btn, true);
            loadAllAdieData();
            }else{
            ResultSet rs = Database.searchInAdie(AdieName);    //dabase query 
            while (rs.next()) {        //when match an have result         
              int id                    = rs.getInt(1);
                String Istechnical = rs.getString(2);
                String fullName      = rs.getString(3);
                int technical_id = rs.getInt(4);
                String nationaId    = rs.getString(5);
                String telephone    = rs.getString(6);
                Date startWork       = rs.getDate(7);
                int experienceYear = rs.getInt(8);
                String notes          = rs.getString(9);
                String techncianName = rs.getString(10);
                adie getOne = new adie(id, Istechnical, fullName, techncianName, nationaId, telephone, startWork, experienceYear, notes);
                System.out.println("taken");
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
            }
            
        }catch(IllegalArgumentException ex){
            // this eception happened when user empty textFiled 
            // so i want to 
            technician_aides_tableView.getItems().clear(); // clear pervious record
            loadAllAdieData(); // load all adie again 
            clearTextFields();
            Database.alertMessage(ex.getMessage());
        }
        catch (Exception ex) {
            Database.alertMessage(ex.getMessage());
        }
        });
             invisible(add_btn, true);
    }
      public void loadAllAdieData()  {
            technician_aides_tableView.getItems().clear();// ensure table are empty from records 
         Platform.runLater(()->{
          try {
              //select * all
            ResultSet rs = Database.searchInAdie(); 
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
                String techncianName = rs.getString(10);
                adie getOne = new adie(id, Istechnical, fullName, techncianName, nationaId, telephone, startWork, experienceYear, notes);
                technician_aides_tableView.getItems().add(getOne); 
            }
            rs.close();
        }
        catch (Exception ex) {
            Database.alertMessage(ex.getMessage());
        }
        });
  
  }
    public void getAlltechnicalName(){
        try {
           ResultSet rs = Database.getAllTechnicalName();
           String technicalName = "";
           while (rs.next()) {               
               technicalName = rs.getString(1);
              suggestTechnicalName.add(technicalName);   
              comboBox_technicalName.getItems().add(technicalName);
               System.out.println(technicalName);
           }
       } catch (SQLException ex) {
           Database.alertInformation(ex.getMessage());
       } 
    }
      public void selectItemAdieToTxtBox(){
            adie selectedItem = technician_aides_tableView.getSelectionModel().getSelectedItem();
            lbl_id.setText(selectedItem.getId()+"");
            txt_technicianName.setText(selectedItem.getName());
            comboBox_technicalName.getSelectionModel().select(selectedItem.getTechnicalName());
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


