/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package philoplus.FXMLFILES;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import philoplus.philoPlusClasses.Database;
import philoplus.philoPlusClasses.Deposite;
import philoplus.philoPlusClasses.Technician;
import philoplus.philoPlusClasses.User;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
/**
 * FXML Controller class
 *
 * @author Seha
 */
public class LifMoney_subcardController implements Initializable {

    @FXML
    private Label lbl_transactionId;
    @FXML
    private Label lbl_liftId;
    @FXML
    private ComboBox<String> combo_liftNumber;
    @FXML
    private TextField txt_billTechnicalTotal;
    @FXML
    private TextField txt_billAddationalCostTechnical;
    @FXML
    private TextField txt_wePayed;
    @FXML
    private TextField txt_TechnicalReminderBefore;
    @FXML
    private ComboBox<String> comboTechncianName;
    @FXML
    private Label lbl_technicalId;
    @FXML
    private TextField txt_newDeposit;
    @FXML
    private DatePicker datePicer_depostiteDate;
    @FXML
    private Button add_supportButton;
    @FXML
    private TextField txt_pathImgSuport;
    @FXML
    private TextField txt_TechnicalReminderAfter;
    @FXML
    private Button add_btn;
    @FXML
    private Button edit_btn;
    @FXML
    private Button delete_btn;
    @FXML
    private TableView<Deposite> table_transactions;
    @FXML
    private TableColumn<Deposite, Integer> col_id;
    @FXML
    private TableColumn<Deposite, Date> col_transactionDate;
    @FXML
    private TableColumn<Deposite, String> col_details;
    @FXML
    private TableColumn<Deposite, Float> col_deposite;
    @FXML
    private TableColumn<Deposite, Float> col_reminder;
    @FXML
    private TableColumn<Deposite, String> col_techncianName;    
    @FXML
    private TableColumn<Deposite, String> col_imgPathSuporter;

    @FXML
    private TextField txt_notes;
    @FXML
    private Label lbl_totalCostOfLift;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        col_id.setCellValueFactory(new PropertyValueFactory<>("depositeId"));
        col_transactionDate.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        col_details.setCellValueFactory(new PropertyValueFactory<>("details"));
        col_deposite.setCellValueFactory(new PropertyValueFactory<>("transactionValue"));
        col_reminder.setCellValueFactory(new PropertyValueFactory<>("reminder"));
        col_techncianName.setCellValueFactory(new PropertyValueFactory<>("technicalName"));
        col_imgPathSuporter.setCellValueFactory(new PropertyValueFactory<>("imgSuportPath"));
        datePicer_depostiteDate.setValue(LocalDate.now());
           combo_liftNumber.valueProperty().addListener((obs, oldVal, newVal) -> {
               setLbl_lift_id(newVal);
               loadAllTechnciansOfLift();
               gettingDataOFLift();
               loadAllpervouisTranscationOfLift();

           });
           comboTechncianName.valueProperty().addListener((obs, oldVal, newVal) -> {
               lbl_technicalId.setText(gettingTechncianId()+"");
           });
           
            table_transactions.setOnMouseClicked(event->{
                if (event.getClickCount() == 2 && !event.isConsumed()) {
                 event.consume();
                //handle double click event. 
                Deposite selected = table_transactions.getSelectionModel().getSelectedItem();
                openImage(selected.getImgSuportPath());
             }
                else if(event.getClickCount() == 1 ){
                    selectRecordInTableTOTxt();
                }
            });
        
        loadAllLiftNumberToComboBox();
  
    }  
    //هنا انا بجيب كل الفنين اللي شغالين في المصعد لو مفيش هيبقي فاضي لو في هيجبهم وومكن اشيل واضيف جديد
    public void loadAllTechnciansOfLift(){
        comboTechncianName.getItems().clear();
       try{
        int liftId = Integer.parseInt(lbl_liftId.getText());
           ResultSet rs = Database.gettingAllTechnciansAssignedToLift(liftId);
           while (rs.next()) {
               int id = rs.getInt(1);
               String name = rs.getString(2);
               Technician newOne = new Technician(id, name);
               comboTechncianName.getItems().add(name);     
           }
       }
       catch(SQLException ex){
           Database.alertMessage(ex.getMessage());
       }
    }
       public int gettingTechncianId(){
        int Techncian_id = 0;
        try {
             String techncianName = comboTechncianName.getSelectionModel().getSelectedItem();
             Techncian_id = Database.getTechnicalId(techncianName);
        } catch (SQLException ex) {
             Database.alertMessage(ex.getMessage());
        }
        return  Techncian_id;
    }
    public void loadAllLiftNumberToComboBox(){
        try {
            ResultSet rs = Database.gettingAllLiftNumber();
            while(rs.next()){
                combo_liftNumber.getItems().add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Database.alertInformation(ex.getMessage());
        }
}    
       public void setLbl_lift_id(String liftNumber){
          try {
            int lift_id = Database.gettingLiftIdFromOurLiftByLiftNumber(liftNumber);
            lbl_liftId.setText(lift_id+"");
          } catch (SQLException ex) {
         Database.alertMessage(ex.getMessage());
        } 
    }
       public void gettingDataOFLift(){
           int liftId = Integer.parseInt(lbl_liftId.getText());
           float billToTechncionTotal = Database.gettingTotalOfBillToTechncianForSpecificLift(liftId); //فاتورة المصعد
           txt_billTechnicalTotal.setText(billToTechncionTotal+"");
           float billToTechncionOfAddedCostTotal = Database.gettingTotalOfAddedCostForSpecificLift(liftId);// التكلفات الاخري 
           txt_billAddationalCostTechnical.setText(billToTechncionOfAddedCostTotal+"");
           float totalBeforAddedNewDeposite = billToTechncionTotal + billToTechncionOfAddedCostTotal ;
           lbl_totalCostOfLift.setText(totalBeforAddedNewDeposite+"");
           
       }
       public void showFileChooserDialog(){     
       FileChooser fileChooser = new FileChooser();
        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
//            Image image = new Image(file.toURI().toString());
//            myImageView.setImage(image);
                       txt_pathImgSuport.setText(file.getAbsolutePath());
        }
    }
       
       public void insertNewDeposite(){
           try{
           int id = Database.autoNumber("`lift-payement-transactions`", "id");
           int liftID = Integer.parseInt(lbl_liftId.getText());
           int techncianId = Integer.parseInt(lbl_technicalId.getText());
           float transactionValue= Float.parseFloat(txt_newDeposit.getText());
           Date transactionDate = Date.valueOf(datePicer_depostiteDate.getValue());
           String details = User.activeUser.getUserName()+" :  "+txt_notes.getText()+ " ,  "+ comboTechncianName.getSelectionModel().getSelectedItem();
           String img_support = "";
           byte [] data = {};
           if(!txt_pathImgSuport.getText().equals("")){
               img_support = txt_pathImgSuport.getText();
               BufferedImage bImage = ImageIO.read(new File(img_support));
               ByteArrayOutputStream bos = new ByteArrayOutputStream();
               ImageIO.write(bImage, "jpg", bos );
               data = bos.toByteArray();
           }
            Deposite newOne = new Deposite(id, liftID, techncianId, transactionValue, transactionDate, details, img_support, data);
            int isInserted = Database.insertNewTransction(newOne);
            if(isInserted >=0 ){
                Database.alertInformation("عملية ناجحة");
                loadAllpervouisTranscationOfLift();
                openImage(img_support);
                txt_newDeposit.setText("");
                txt_notes.setText("");
                txt_pathImgSuport.setText("");
            }    
           }
           catch(SQLException ex){
               Database.alertMessage(ex.getMessage());
            } catch (IOException ex) {
               Database.alertMessage(ex.getMessage());
        }
       }
       
       public  void deleteTransaction(){
           try{
               int transactionId = Integer.parseInt(lbl_transactionId.getText());
               int isDeleted = Database.deleteTranscation(transactionId);
               if(isDeleted > 0 ){
                   Database.alertInformation("عملية ناجحة");
                   loadAllpervouisTranscationOfLift();
                   txt_newDeposit.setText("");
                   txt_notes.setText("");
                   txt_pathImgSuport.setText("");
               }
           }catch(SQLException ex){
               Database.alertMessage(ex.getMessage());
           }
       }
       
       public void editTransaction(){
         try{
           int id = Integer.parseInt(lbl_transactionId.getText());
           int liftID = Integer.parseInt(lbl_liftId.getText());
           int techncianId = Integer.parseInt(lbl_technicalId.getText());
           float transactionValue= Float.parseFloat(txt_newDeposit.getText());
           Date transactionDate = Date.valueOf(datePicer_depostiteDate.getValue());
           String details = User.activeUser.getUserName()+" :  "+txt_notes.getText()+ " ,  "+ comboTechncianName.getSelectionModel().getSelectedItem();
           String img_support = "";
           byte [] data = {};
           if(!txt_pathImgSuport.getText().equals("")){
               img_support = txt_pathImgSuport.getText();
               BufferedImage bImage = ImageIO.read(new File(img_support));
               ByteArrayOutputStream bos = new ByteArrayOutputStream();
               ImageIO.write(bImage, "jpg", bos );
               data = bos.toByteArray();
           }
            Deposite newOne = new Deposite(id, liftID, techncianId, transactionValue, transactionDate, details, img_support, data);
            int isUpdated = Database.editTransaction(newOne);
            if(isUpdated >=0 ){
                Database.alertInformation("عملية ناجحة");
                loadAllpervouisTranscationOfLift();
                openImage(img_support);
                txt_newDeposit.setText("");
                txt_notes.setText("");
                txt_pathImgSuport.setText("");
            }    
           }
           catch(SQLException ex){
               Database.alertMessage(ex.getMessage());
            } catch (IOException ex) {
               Database.alertMessage(ex.getMessage());
        }
       }
       public void loadAllpervouisTranscationOfLift(){
          table_transactions.getItems().clear();
          float total = Float.parseFloat(lbl_totalCostOfLift.getText());
          float reminder = total ;
           try{
               int liftID = Integer.parseInt(lbl_liftId.getText());
               ResultSet rs = Database.loaddingAllPervouisTranscation(liftID);
               while (rs.next()) {                   
                   int id = rs.getInt(1);
                   int techncianId = rs.getInt(3);
                   String techncianName = Database.getTechnicalName(techncianId);
                   float transactionValue= rs.getFloat(4);
                   reminder = reminder - transactionValue;
                   Date transactionDate = rs.getDate(5);
                   String details =rs.getString(6);
                   String img_support = rs.getString(7);
                   Deposite obj = new Deposite(id, liftID, techncianId, transactionValue,reminder, transactionDate, details, img_support, techncianName);
                   table_transactions.getItems().add(obj);
               }
           }catch(SQLException ex){
               Database.alertMessage(ex.getMessage());
           } 
       }
       
      public void selectRecordInTableTOTxt(){
          
          Deposite select = table_transactions.getSelectionModel().getSelectedItem() ; 
          System.out.println(select.getDepositeId());
          lbl_transactionId.setText(select.getDepositeId()+"");
          comboTechncianName.getSelectionModel().select(select.getTechnicalName());
          txt_newDeposit.setText(select.getTransactionValue()+"");
          datePicer_depostiteDate.setValue(select.getTransactionDate().toLocalDate());
          txt_notes.setText(select.getDetails());
          txt_pathImgSuport.setText(select.getImgSuportPath());
                  
      }
       public void openImage(String path){
       
           if(path.equals("")){
               return;
           }
           InputStream stream = null;
        try {
            stream = new FileInputStream(path);
            Image image = new Image(stream);
            //Creating the image view
            ImageView imageView = new ImageView();
            //Setting image to the image view
            imageView.setImage(image);
            //Setting the image view parameters
            imageView.setX(10);
            imageView.setY(10);
            imageView.setFitWidth(575);
            imageView.setPreserveRatio(true);
            //Setting the Scene object
            Group root = new Group(imageView);
            Scene scene = new Scene(root, 595, 370);
            Stage stage = new Stage();
            stage.setTitle("Displaying Image");
            stage.setScene(scene);
            stage.show();
        } catch (FileNotFoundException ex) {
               Database.alertMessage(ex.getMessage());
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
               Database.alertMessage(ex.getMessage());
            }
        }
       }
       
       
       }

