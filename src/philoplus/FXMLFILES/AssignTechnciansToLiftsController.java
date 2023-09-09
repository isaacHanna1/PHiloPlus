/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package philoplus.FXMLFILES;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import philoplus.philoPlusClasses.AutoCompleteComboBox;
import philoplus.philoPlusClasses.Database;
import philoplus.philoPlusClasses.Technician;

/**
 * FXML Controller class
 *
 * @author Seha
 */
public class AssignTechnciansToLiftsController implements Initializable {

    @FXML
    private GridPane gridParent;
    @FXML
    private Label lbl_id; // to show id of selected techncian to use in delete query 
    @FXML
    private AutoCompleteComboBox<String> combo_techncianName;
    @FXML
    private Button add_btn;
    @FXML
    private Button delete_btn;
    @FXML
    private TableView<Technician> tabel_techncian;
    @FXML
    private TableColumn<Technician, Integer> col_id;
    @FXML
    private TableColumn<Technician, String> col_techncianName;
    @FXML
    private Label lbl_liftNumber;
    @FXML
    private Label lbl_lift_id;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          loadAllTechncianName();
          col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
          col_techncianName.setCellValueFactory(new PropertyValueFactory<>("name"));
          
    }  
    
    public void loadAllTechncianName(){
        try {
            ResultSet rs =  Database.getAllTechnicalName();
            while (rs.next()) {
                combo_techncianName.getItems().add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());
        }
    }
    
    public void setData(String lift_id , String liftNumber ){
        lbl_lift_id.setText(lift_id);
        lbl_liftNumber.setText(liftNumber);
        loadAllTechnciansOfLift();// بص لو كنت خطيتها في انشليز ميثود مكنتشي هتنفع لان بتعتمد علي
        // set data 
        //بجيب منها ال  
        //id of lift 
        // set data are excuted after intialize method 
    }
    
    public int gettingTechncianId(){
        int Techncian_id = 0;
        try {
            String techncianName = combo_techncianName.getSelectionModel().getSelectedItem();
             Techncian_id = Database.getTechnicalId(techncianName);
        } catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());
        }
        return  Techncian_id;
    }
    
    public void insertTechncianToLift(){
        try {
            int id = Database.autoNumber("`technician-of-lift`","id");
            int liftId  = Integer.parseInt(lbl_lift_id.getText());
            int techncianId = gettingTechncianId();
            Technician newOne = new Technician(techncianId, liftId);
            int isInserted = Database.insertIntoTechnician_of_lift(id, newOne);
            if(isInserted > 0 ){
                Database.alertInformation("عملية ناجحة ");
                loadAllTechnciansOfLift();
            }
        } catch (SQLException ex) {
            if(ex.getSQLState().startsWith("23")){
                Database.alertInformation("هذا الفني مضاف للمصعد ");
            }else{
            Database.alertMessage(ex.getMessage());
            }
        }
    }    
//هنا انا بجيب كل الفنين اللي شغالين في المصعد لو مفيش هيبقي فاضي لو في هيجبهم وومكن اشيل واضيف جديد
    public void loadAllTechnciansOfLift(){
        tabel_techncian.getItems().clear();
       try{
        int liftId = Integer.parseInt(lbl_lift_id.getText());
           ResultSet rs = Database.gettingAllTechnciansAssignedToLift(liftId);
           while (rs.next()) {
               int id = rs.getInt(1);
               String name = rs.getString(2);
               Technician newOne = new Technician(id, name);
               tabel_techncian.getItems().add(newOne);     
           }
       }
       catch(SQLException ex){
           Database.alertMessage(ex.getMessage());
       }
    }
    public void setSelectedRowTotxt(){
        Technician selectedRow = tabel_techncian.getSelectionModel().getSelectedItem();
        lbl_id.setText(selectedRow.getId()+"");
        combo_techncianName.getSelectionModel().select(selectedRow.getName());
    }
    
    public void deleteRecored(){
        try {
            int techncianId = Integer.parseInt(lbl_id.getText());
            int lift_id = Integer.parseInt(lbl_lift_id.getText());
            int deleted = Database.deleteTechncianFromLiftRespomsibility(techncianId,lift_id);
            if(deleted>0){
                Database.alertMessage("عملية ناجحة");
                loadAllTechnciansOfLift();
            }
        } catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());
        }
        
    }
}
