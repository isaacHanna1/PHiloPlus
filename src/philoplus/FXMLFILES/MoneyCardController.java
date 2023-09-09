
package philoplus.FXMLFILES;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javax.swing.JOptionPane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;

public class MoneyCardController implements Initializable {
    @FXML
    private VBox vboxContainer;
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
   public void loadLatestSubCard() {
       loadAnotherFxml("latest_subcard");
   }
   public void loadBillFromCompanyToLeft() {
       loadAnotherFxml("billFromCompanyToLift");
   }
   public void loadBillFromOurCompanyToMotherCompany() {
       loadAnotherFxml("billFromOurCompanyToMotherCompany");
   }
   public void loadAddationBillToProject() {
       loadAnotherFxml("addationBillToProject");
   }
   public void loadliftMoneycard() {
       loadAnotherFxml("lifMoney_subcard");
   }
    public void loadPayMoneycard() {
       loadAnotherFxml("payMoney_subCard");
   }
       public void loadFatherAccount() {
       loadAnotherFxml("fatherAcount");
   }
         public void loadSonAccount() {
       loadAnotherFxml("sonAccount");
   }
      public void loadAnotherFxml (String fxmlName){
        try {

            Parent    root = FXMLLoader.load(getClass().getResource(fxmlName+".fxml"));
            vboxContainer.getChildren().clear();
            vboxContainer.getChildren().add(root);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        // putting the scene in the center of layour borderPane

}
}
