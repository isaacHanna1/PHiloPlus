/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package philoplus.FXMLFILES;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import philoplus.philoPlusClasses.Database;
import philoplus.philoPlusClasses.User;

/**
 * FXML Controller class
 *
 * @author Seha
 */
public class User_loginController implements Initializable {

    @FXML
    private TextField txt_userName;
    @FXML
    private Button login_btn;
    @FXML
    private Button cancel_btn;
    @FXML
    private PasswordField txt_password;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt_userName.setOnKeyReleased(event->{
            if(event.getCode().toString().equals("ENTER")){
            txt_password.requestFocus();
            }
        });
        txt_password.setOnKeyReleased(event->{
            if(event.getCode().toString().equals("ENTER")){
            checkLogin();
            }
        });
    }    
    
    
    public void checkLogin(){
        try{
        String userName = txt_userName.getText();
        String password = txt_password.getText();
        int userId = Database.gettingUserId(userName, password);
        String pervililage = Database.gettingPerivilageOfUser(userName, password);
        User loginUser = new User(userId, userName, password, pervililage);
        boolean isLogin = loginUser.chechLoginAvability() ;
         Parent  root ;
        if(isLogin){
                User.activeUser(loginUser);
                root = FXMLLoader.load (getClass().getResource("home"+".fxml"));
                                Stage stage = new Stage();
                                stage.setTitle("صفحة الرئيسية");
                                stage.setScene(new Scene(root));
                                login_btn.getScene().getWindow().hide();
                                stage.show();
                                   stage.setOnCloseRequest((event) -> {
                                    event.consume();
                                    System.exit(0);
                                 });
        }else{
            Database.alertMessage("اسم المستخدم او الرقم السري غير صحيح");
        }
        
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(User_loginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cancelStage(){
    
       cancel_btn.getScene().getWindow().hide();

    }
}
