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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import philoplus.philoPlusClasses.Database;
import philoplus.philoPlusClasses.User;

/**
 * FXML Controller class
 *
 * @author Seha
 */
public class UsersController implements Initializable {

    @FXML
    private TextField txt_userName;
    @FXML
    private PasswordField txt_passwordName;
    @FXML
    private ComboBox<String> comb_perivilage;
    @FXML
    private Button add_btn;
    @FXML
    private Button edit_btn;
    @FXML
    private Button delete_btn;
    @FXML
    private TableView<User> table_user;
    @FXML
    private TableColumn<User, Integer> col_id;
    @FXML
    private TableColumn<User, String> col_userName;
    @FXML
    private TableColumn<User, String> col_password;
    @FXML
    private TableColumn<User, String> col_perivilage;
    @FXML
    private Label lbl_id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        comb_perivilage.getItems().add("المالك");
        comb_perivilage.getItems().add("مهندس");
        comb_perivilage.getItems().add("محاسب");
        comb_perivilage.getItems().add("مشرف");
        comb_perivilage.getItems().add("فني");
        
        comb_perivilage.getSelectionModel().select(2);
        loadAllUser();
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("passsword"));
        col_perivilage.setCellValueFactory(new PropertyValueFactory<>("perivilage"));
        
          table_user.setOnKeyReleased(event->{
           if(event.getCode().toString().equals("DOWN")
                   ||event.getCode().toString().equals("UP")){
               selectItemToTxt();
           }
                 });
             
    }    
    
    
    public void insertNewUser(){
  try{ 
    int id = Database.autoNumber("users", "id");
    String userName = txt_userName.getText();
    String password = txt_passwordName.getText();
    if(userName.equals("") || password.equals("")){
        Database.alertInformation("تاكد من ادخال اسم المستخدم والرقم السري");
        return;
    }
    String perivilage = comb_perivilage.getSelectionModel().getSelectedItem();
    User user = new User(id, userName, password, perivilage);
    int isInserted = Database.insertNewUser(user);
    if(isInserted> 0){
        Database.alertInformation("عملية ناجحة");
        txt_passwordName.setText("");
        txt_userName.setText("");
        loadAllUser();
    }
    }
        catch(SQLIntegrityConstraintViolationException  ex){//this exception is thrown because i put constarint on campany_name col be unique 
            Database.alertMessage("هناك حساب مسجة بتلك البيانات من قبل في قاعدة البيانات");
        }catch(SQLException ex){
      Database.alertMessage(ex.getMessage());
  }
    }
    
    
    public void loadAllUser(){
        table_user.getItems().clear();
        try {
            ResultSet rs =   Database.gettingAllUsers();
            while (rs.next()) {                
                int id = rs.getInt(1);
                String userName = rs.getString(2);
                String password = rs.getString(3);
                String perivilage = rs.getString(4);
                User u = new User(id, userName, password, perivilage);
                table_user.getItems().add(u);
            }
        } catch (SQLException e) {
        }
    
    }
   public void selectItemToTxt(){
   
       User selected = table_user.getSelectionModel().getSelectedItem();
       lbl_id.setText(selected.getId()+"");
       txt_passwordName.setText(selected.getPasssword());
       txt_userName.setText(selected.getUserName());
       comb_perivilage.getSelectionModel().select(selected.getPerivilage());
   }
   
   public void deleteUser (){
       try{
           int id = Integer.parseInt(lbl_id.getText());
           int isDeleted = Database.deleteUser(id);
           if(isDeleted>0){
           Database.alertMessage("عملية ناجحة");
              txt_passwordName.setText("");
               txt_userName.setText("");
           loadAllUser();
           }
       }
       catch(SQLException ex){
                  if (ex.getErrorCode() == 1451) {// this code error is thrown when user try to delete parent row used as refrenced key to forign key 
        Database.alertMessage("لا تسطيع مسحه لانه مرتبط بكثير من بيانات ");
    } else {
            Database.alertMessage(ex.getMessage());
    }
        }catch(NumberFormatException ex )    {
          Database.alertMessage("قم بتحديد المستخدم المراد مسحه بياناته من الجدول");
        }
       }
   
   public void EditUser(){
 try{ 
    int id = Integer.parseInt(lbl_id.getText());
    String userName = txt_userName.getText();
    String password = txt_passwordName.getText();
    if(userName.equals("") || password.equals("")){
        Database.alertInformation("تاكد من ادخال اسم المستخدم والرقم السري");
        return;
    }
    String perivilage = comb_perivilage.getSelectionModel().getSelectedItem();
    User updatedUser = new User(id, userName, password, perivilage);
    int isInserted = Database.editUser(updatedUser);
    if(isInserted> 0){
        Database.alertInformation("عملية ناجحة");
        txt_passwordName.setText("");
        txt_userName.setText("");
        loadAllUser();
    }
    }
        catch(SQLIntegrityConstraintViolationException  ex){//this exception is thrown because i put constarint on campany_name col be unique 
            Database.alertMessage("هناك حساب مسجة بتلك البيانات من قبل في قاعدة البيانات");
        }catch(SQLException ex){
      Database.alertMessage(ex.getMessage());
  }
}
}
