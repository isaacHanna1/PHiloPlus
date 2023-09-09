/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package philoplus.FXMLFILES;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Seha
 */
public class HomeController implements Initializable {

    @FXML
    private BorderPane borderPaneContainer;
    
    @FXML
    private ImageView image_icon;
    
    @FXML
    private VBox view;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
  
}
      public void loadCampanyCard(){
           setimageToImageView("src\\philoplus\\FXMLFILES\\icons\\office-building.png", image_icon);
            loadAnotherFxml("campanyCard");
    }
       public void loadAreaCard(){
           setimageToImageView("src\\philoplus\\FXMLFILES\\icons\\gpsicon.png", image_icon);
            loadAnotherFxml("areaCard");
    }
       public void loadTechnicalCard(){
           setimageToImageView("src\\philoplus\\FXMLFILES\\icons\\technical-supportj.png", image_icon);
            loadAnotherFxml("technicalCard");
    }
           public void loadAdieCard(){
           setimageToImageView("src\\philoplus\\FXMLFILES\\icons\\technical-supportj.png", image_icon);
            loadAnotherFxml("adieCard");
    }
       public void loadProjectCard(){
           setimageToImageView("src\\philoplus\\FXMLFILES\\icons\\lift-2.png", image_icon);
            loadAnotherFxml("projectsCard");
    }
       public void loadLiftCard(){
           setimageToImageView("src\\philoplus\\FXMLFILES\\icons\\lift-2.png", image_icon);
            loadAnotherFxml("liftCard");
       }
         public void loadMoneyCard(){
           //setimageToImageView("src\\philoplus\\FXMLFILES\\icons\\bill.png", image_icon);
            loadAnotherFxml("moneyCard");
       }
         public void loadUserCard(){
           setimageToImageView("src\\philoplus\\FXMLFILES\\icons\\data-processing.png", image_icon);
            loadAnotherFxml("users");
       }
       
    public void loadAnotherFxml (String fxmlName){
    
    
        try {
            System.out.println();
            Parent    root = FXMLLoader.load(getClass().getResource(fxmlName+".fxml"));
            view.getChildren().clear();
            view.getChildren().add(root);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        // putting the scene in the center of layour borderPane 
        
}
    public void setimageToImageView(String imagePath , ImageView imageView){
            
                    try {
            FileInputStream input = new FileInputStream(imagePath);
            Image image = new Image(input);
            imageView.setImage(image);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }


    }
}
