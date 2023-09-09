/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package philoplus.FXMLFILES;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Seha
 */
public class LiftCardController implements Initializable {

    @FXML
    private VBox vboxContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void loadTypeOfLiftSubcard(){
        loadAnotherFxml("typeOfLiftSubcard");
    }
    public void loadStageOfLiftSubcard(){
        loadAnotherFxml("stagesOfLift_subcard");
    }
    public void loadCreateLiftSubcard(){
        loadAnotherFxml("createLift_subcard");
    }
    public void loadFollowingCard(){
        loadAnotherFxml("following_subCard");
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
