/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package philoplus.FXMLFILES;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import philoplus.philoPlusClasses.Database;

/**
 * FXML Controller class
 *
 * @author Seha
 */
public class Latest_subcardController implements Initializable {

    @FXML
    private Label lbl_billTechncian;
    @FXML
    private Label lbl_billMother;
    @FXML
    private Label lbl_billSent;
    @FXML
    private Label lbl_billRecevie;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        int unSent = Database.gettingNumberOfUnSentBill();
        int unRecived= Database.gettingNumberOfUnReceiveBill();
        int unSetMonyToLiftsMotherCompany = Database.gettingNumberOfUnSetMoneyToMotherCompany();
        int unSetMoneyToLiftTechncian = Database.gettingNumberOfUnSetMoneyTechncain();
        
        lbl_billTechncian.setText(unSetMoneyToLiftTechncian+"");
        lbl_billMother.setText(unSetMonyToLiftsMotherCompany+"");
        lbl_billSent.setText(unSent+"");
        lbl_billRecevie.setText(unRecived+"");
        
                
    }  
    
    
    
}
