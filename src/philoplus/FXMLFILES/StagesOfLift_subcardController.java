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
import philoplus.philoPlusClasses.Areas;
import philoplus.philoPlusClasses.Database;
import philoplus.philoPlusClasses.liftProgress;
import philoplus.philoPlusClasses.AutoCompleteComboBox;
/**
 * FXML Controller class
 *
 * @author Seha
 */
public class StagesOfLift_subcardController implements Initializable {

    @FXML
    private GridPane parentGrid;
    @FXML
    private AutoCompleteComboBox <String> comboBox_liftType;
    @FXML
    private TextField txt_liftType_details;
    @FXML
    private ComboBox<Float> combo_ratio;
    @FXML
    private Label lbl_id;
    @FXML
    private Button add_btn;
    @FXML
    private Button edit_btn;
    @FXML
    private Button delete_btn;
    @FXML
    private TableView<liftProgress> stageDetailsTableView;
    @FXML
    private TableColumn<liftProgress, Integer> col_id;
    @FXML
    private TableColumn<liftProgress,String> col_liftType;
    @FXML
    private TableColumn<liftProgress, String> col_liftStageDetails;
    @FXML
    private TableColumn<liftProgress, String> col_ratio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
        //Start column intalisation of table view areas
            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_liftType.setCellValueFactory(new PropertyValueFactory<>("liftType"));
            col_liftStageDetails.setCellValueFactory(new PropertyValueFactory<>("liftProgressDetails"));
            col_ratio.setCellValueFactory(new PropertyValueFactory<>("ratio"));
   //==> End column intalisation of table view areas    
        
        loadAllLiftTypeToCombBox(); // getting lift type details
        loadRatiosToCombBox(); // بص يا سيدي انا عملت اية ؟ انا بنسبالي المصعد بيمر بمراحل كل مرحلة همثلها في نسبة مئوية 
        //  لحد مايوصل لمئة في مئة اللي بتعبل عن تسليم الجودة 
        comboBox_liftType.valueProperty().addListener((obs, oldVal, newVal) -> {
            showStageOfLiftType();
        });
    }    
    public void loadAllLiftTypeToCombBox(){
        try {
            ResultSet rs = Database.gettingAllLiftType();
            String Lifttype = "";
            while(rs.next()){
                Lifttype = rs.getString(1);
                comboBox_liftType.getItems().add(Lifttype);
            }
        } catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());
        }
    }
    public void loadRatiosToCombBox(){
        combo_ratio.getItems().add(10f);
        combo_ratio.getItems().add(20f);
        combo_ratio.getItems().add(30f);
        combo_ratio.getItems().add(40f);
        combo_ratio.getItems().add(50f);
        combo_ratio.getItems().add(60f);
        combo_ratio.getItems().add(70f);
        combo_ratio.getItems().add(80f);
        combo_ratio.getItems().add(90f);
        combo_ratio.getItems().add(100f); 
    }
    
    public void insertIntoProgress(){
        try{
        int id = Database.autoNumber("`lift-type-progress`", "id");
        String liftType = comboBox_liftType.getSelectionModel().getSelectedItem();
        int liftTypeId = Database.gettingIdOfLiftType(liftType);
        String liftProgressDetails = txt_liftType_details.getText();
        float ratio = combo_ratio.getSelectionModel().getSelectedItem();
        liftProgress newOne = new liftProgress(id, liftTypeId, liftProgressDetails, ratio);
             if(liftProgressDetails.equals("")){
            Database.alertMessage("ادخل تفاصيل المرحلة");
        }
        int isInserted = Database.insertNewLiftProgressDetails(newOne);
        if(isInserted > 0 ){
               Database.alertInformation("عملية ناجحة ");   
               showStageOfLiftType();
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
    public void updateRecord(){
        try{
        int id = Integer.parseInt(lbl_id.getText());
        String liftType = comboBox_liftType.getSelectionModel().getSelectedItem();
        int liftTypeId = Database.gettingIdOfLiftType(liftType);
        String liftProgressDetails = txt_liftType_details.getText();
        float ratio = combo_ratio.getSelectionModel().getSelectedItem();
        liftProgress newOne = new liftProgress(id, liftTypeId, liftProgressDetails, ratio);
             if(liftProgressDetails.equals("")){
            Database.alertMessage("ادخل تفاصيل المرحلة");
        }
        int isUpdated = Database.editLiftProgressDetails(id,newOne);
        if(isUpdated > 0 ){
               Database.alertInformation("عملية ناجحة ");   
               showStageOfLiftType();
        }
        }catch(IllegalArgumentException ex){ // i thrown exception in constructor method  of campany class if variable comapnyName is empty 
            Database.alertMessage("لا يمكن ان يكون حقل اسم المنطقة فارغ");
        } catch(SQLIntegrityConstraintViolationException  ex){//this exception is thrown because i put constarint on area name col be unique 
            Database.alertMessage("هناك بيانات  مسجلة بتلك التفاصيل في قاعدة البيانات");
        }  catch (SQLException ex) {
            Database.alertInformation(ex.getMessage());
        }catch(NullPointerException ex){
            Database.alertInformation("تاكد من ادخال جميع الببيانات وتحديد الصف المراد تعديله ");
        }
    }
    public void deleteProgress(){
        try{
            int id = Integer.parseInt(lbl_id.getText());
            int isdeleted = Database.deleteRecordOfLiftProgress(id);
            if(isdeleted>0){
                Database.alertInformation("عملية ناجحة");
                showStageOfLiftType();
            }
        }catch(NullPointerException ex){
            Database.alertInformation("قم بتحديد الصف المراد مسحه ");
        }catch(SQLException ex){
            
              if (ex.getErrorCode() == 1451) {// this code error is thrown when user try to delete parent row used as refrenced key to forign key 
                Database.alertMessage("لا تسطيع مسحه لانه مرتبط بكثير من بيانات ");
            }
              else{
                  Database.alertInformation(ex.getMessage());
              }
            
        }
    }
    public void showStageOfLiftType(){
        try {
            stageDetailsTableView.getItems().clear();
            String liftTypeCombo = comboBox_liftType.getSelectionModel().getSelectedItem();
            int idOfLiftType = Database.gettingIdOfLiftType(liftTypeCombo);
            System.out.println("the id "+idOfLiftType);
            ResultSet rs = Database.gettingAllStagesOfSpecialLiftType(idOfLiftType);
            while (rs.next()) {                
                int id = rs.getInt(1);
                System.out.println(id);
                String progressDetails = rs.getString(2);
                float progress_ratio = rs.getFloat(3);
                liftProgress newOne = new liftProgress(id, liftTypeCombo, progressDetails, progress_ratio);
                stageDetailsTableView.getItems().add(newOne);
            }
        } catch (SQLException ex) {
            Database.alertInformation(ex.getMessage());
        }
    }
        // i use this function to get selectItem data and put it to txtboxes
   public void selectItemToTxtBox(){
            liftProgress selectedItem = stageDetailsTableView.getSelectionModel().getSelectedItem();
            lbl_id.setText(selectedItem.getId()+"");
            comboBox_liftType.getSelectionModel().select(selectedItem.getLiftType());
            txt_liftType_details.setText(selectedItem.getLiftProgressDetails());
            combo_ratio.getSelectionModel().select(selectedItem.getRatio());
            
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
