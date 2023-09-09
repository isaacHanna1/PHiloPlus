/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package philoplus.FXMLFILES;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import philoplus.philoPlusClasses.Following;
import philoplus.philoPlusClasses.Database;
import philoplus.philoPlusClasses.Deposite;
import philoplus.philoPlusClasses.ReadingFile;
import philoplus.philoPlusClasses.User;
/**
 * FXML Controller class
 *
 * @author Seha
 */
public class Following_subCardController implements Initializable {


    @FXML
    private ComboBox<String> combBox_techncianName;
    @FXML
    private ComboBox<String> comboBox_liftNumber;
    @FXML
    private ComboBox<String> comboBox_techncianResult;
    @FXML
    private ComboBox<String> comboBox_underFollowing;
    @FXML
    private ComboBox<String> comboBox_ratio;
    @FXML
    private Label lbl_liftNumber;
    @FXML
    private Label lbl_liftType;
    @FXML
    private Label lbl_lift_floorNumber;
    @FXML
    private Label lbl_techncialName;
    @FXML
    private Label lbl_lift_WellNumber;
    @FXML
    private Label lbl_startWork;
    @FXML
    private Label lbl_number_of_workDay;
    @FXML
    private Label lbl_finishDay;
    @FXML
    private Label lbl_remainingDay;
    @FXML
    private TextArea txt_followingDetails;
    @FXML
    private DatePicker datePicker_followingDate;
    @FXML
    private Button add_btn;
    @FXML
    private Button edit_btn;
    @FXML
    private Button delete_btn;
    @FXML
    private TableView<Following> table_follwoing;
    @FXML
    private TableColumn<Following, Integer> col_id;
    @FXML
    private TableColumn<Following, String> col_followingDetails;
    @FXML
    private TableColumn<Following, String> col_progress;
    @FXML
    private TableColumn<Following, Float> col_ratio;
    @FXML
    private TableColumn<Following, Date> col_followingDate;
    @FXML
    private TableColumn<Following, String> col_user_login;
    @FXML
    private TableColumn<Following, String> col_user_img;
    @FXML
    private Label lbl_lift_id;
    @FXML
    private Label lbl_CompanyName;
    @FXML
    private Label lbl_SiteName;
     @FXML
    private Label lbl_TechncianNumber;
     @FXML
     private Label lbl_followingID;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Start column intalisation of table view following
            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_followingDetails.setCellValueFactory(new PropertyValueFactory<>("following_Details"));
            col_progress.setCellValueFactory(new PropertyValueFactory<>("progressDetails"));
            col_ratio.setCellValueFactory(new PropertyValueFactory<>("progress_radtio"));
            col_followingDate.setCellValueFactory(new PropertyValueFactory<>("following_Date"));
            col_followingDate.setCellValueFactory(new PropertyValueFactory<>("following_Date"));
            col_user_login.setCellValueFactory(new PropertyValueFactory<>("userloginName"));
            col_user_img.setCellValueFactory(new PropertyValueFactory<>("imgPath"));
            
         //End column intalisation of table view following  
         
         //start load comboBox data
            loadAllLiftNumberToComboBox();
            loadAllTechnicalNameToCombBox();
            loadUderFollowingLift();
         //end load comboBox data
         
         //start event when user change comboBox item 
          comboBox_liftNumber.valueProperty().addListener((obs, oldVal, newVal) -> {
              setLbl_lift_id(newVal);
              settingLabelData();
        });
           combBox_techncianName.valueProperty().addListener((obs, oldVal, newVal) -> {
              comboBox_techncianResult.getItems().clear();
               LoadAllLiftNumberToCombOBoxAssignedToTechncian(newVal);
        });
          comboBox_techncianResult.valueProperty().addListener((obs, oldVal, newVal) -> {
              setLbl_lift_id(newVal);
              settingLabelData();
        }); 
          comboBox_underFollowing.valueProperty().addListener((obs, oldVal, newVal) -> {
              setLbl_lift_id(newVal);
              settingLabelData();
        }); 
         //start event when user change comboBox item 
         table_follwoing.setOnMouseClicked(event->{
               if (event.getClickCount() == 1 && !event.isConsumed()) {
                 event.consume();
                //handle double click event. 
                setSelectRecordTotxt();
             }
                if (event.getClickCount() == 2 && !event.isConsumed()) {
                 event.consume();
                //handle double click event. 
                downloadImg();
             }
            });
        
    }    
    
    
    public void loadAllLiftNumberToComboBox(){
        try {
            ResultSet rs = Database.gettingAllLiftNumber();
            while(rs.next()){
                String liftNumber = rs.getString(1);
                comboBox_liftNumber.getItems().add(liftNumber);
            }
        } catch (SQLException ex) {
         Database.alertMessage(ex.getMessage());
        }
    }
    public void loadAllTechnicalNameToCombBox(){
    try {
            ResultSet rs = Database.gettingAllTechnicalName();
            while(rs.next()){
                String techncianName = rs.getString(1);
                combBox_techncianName.getItems().add(techncianName);
            }
        } catch (SQLException ex) {
         Database.alertMessage(ex.getMessage());
        }
    }
    public void LoadAllLiftNumberToCombOBoxAssignedToTechncian(String techncianName){
        try {
            int id =  gettingTechncianId(techncianName);
            ResultSet rs = Database.gettingLiftNumberAssignedToTechncian(id);
            while (rs.next()) {                
                String liftNumber = rs.getString(1);
                comboBox_techncianResult.getItems().add(liftNumber);
            }
        } catch (SQLException ex) {
         Database.alertMessage(ex.getMessage());
        }
    }
    public void loadAlLLastFollowing(){
    table_follwoing.getItems().clear();
        try {
            int lift_id = Integer.parseInt(lbl_lift_id.getText());
            ResultSet rs = Database.loadAllLastFollowingOfLift(lift_id);
            while (rs.next()) {                
                int id = rs.getInt(1);
                String followingDetails = rs.getString(2);
                String following_progress = rs.getString(3);
                float progress_ratio = rs.getFloat(4);
                Date followingDate = rs.getDate(5);
                String userLoginname = rs.getString(6);
                String imgPath          = rs.getString(7);
                Following following = new Following(id, followingDetails, following_progress, progress_ratio, followingDate,userLoginname,imgPath);
                table_follwoing.getItems().add(following);
            }
        } catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());
        }
    }
    public void setLbl_lift_id(String liftNumber){
  try {
            int lift_id = Database.gettingLiftIdFromOurLiftByLiftNumber(liftNumber);
            lbl_lift_id.setText(lift_id+"");
  } catch (SQLException ex) {
         Database.alertMessage(ex.getMessage());
        }
    
    }
    
    public void settingLabelData(){
        try {
            int lift_id = Integer.parseInt(lbl_lift_id.getText());
            ResultSet rs = Database.gettingLiftDataByLiftId(lift_id);
            while (rs.next()) {                
                String liftNumber = rs.getString(1);
                String liftType = rs.getString(2);
                int floorNumber = rs.getInt(3);
                int wellNumber = rs.getInt(4);
                String technicanOfLifts = "";
                String TechncianNumbers = "";
                ResultSet rsTecncians = Database.gettingAllTechnciansAssignedToLift(lift_id);
                while (rsTecncians.next()) {                    
                    technicanOfLifts += rsTecncians.getString(2)+" - ";
                    TechncianNumbers += rsTecncians.getString(3)+" - ";
                }
                String liftCampany = rs.getString(5);
                String liftSite = rs.getString(6);
                Date startWork = rs.getDate(7);
                int num_of_work_day = rs.getInt(8);
                Date finishDate = getFinsihDate(startWork, num_of_work_day);
                lbl_liftNumber.setText(liftNumber);
                lbl_liftType.setText(liftType);
                loadAllProgressDetailsOfLiftTypeToComboBoxLiftType(); // i uses this fuction to load progress details after assign lift type to get id of lift type to get progress details 
                lbl_lift_floorNumber.setText(floorNumber+"");
                lbl_lift_WellNumber.setText(wellNumber+"");
                lbl_techncialName.setText(technicanOfLifts);
                lbl_TechncianNumber.setText(TechncianNumbers);
                lbl_CompanyName.setText(liftCampany+"");
                lbl_SiteName.setText(liftSite+"");
                lbl_startWork.setText(startWork.toString());
                lbl_number_of_workDay.setText(num_of_work_day+"");
                lbl_finishDay.setText(finishDate.toString());
                loadAlLLastFollowing();
            }
        }  catch (SQLException ex) {
         Database.alertMessage(ex.getMessage());
        }
    }
    public int gettingTechncianId(String techncianName){
        try {
            int id = Database.gettingTechncianId(techncianName);
            return id ;
        }  catch (SQLException ex) {
         Database.alertMessage(ex.getMessage());
         return 0;
        }   
   }
    public int gettingLiftTypeId (){
        int liftTypeId = 0;
        try {
            String liftType = lbl_liftType.getText();
             liftTypeId = Database.gettingIdOfLiftType(liftType);
        } catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());
        }
        return  liftTypeId;
    }
    public void  loadAllProgressDetailsOfLiftTypeToComboBoxLiftType(){
        try {
            comboBox_ratio.getItems().clear();
            int id = gettingLiftTypeId() ;
            ResultSet rs = Database.gettingAllStageOfLiftType(id);
            while (rs.next()) {                
                comboBox_ratio.getItems().add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());
        }
    }
    public void loadUderFollowingLift(){
        try {
            ResultSet rs = Database.UnderFollowingLifts();
            while (rs.next()) {
                String liftNumber = rs.getString(2);
                comboBox_underFollowing.getItems().add(liftNumber);
            }
        } catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());
        }
    }
    public void setSelectRecordTotxt(){
        Following selected = table_follwoing.getSelectionModel().getSelectedItem();
        lbl_followingID.setText(selected.getId()+"");
        txt_followingDetails.setText(selected.getFollowing_Details());
        comboBox_ratio.getSelectionModel().select(selected.getProgressDetails());
        datePicker_followingDate.setValue(selected.getFollowing_Date().toLocalDate());
    }
    public void insertNewFollowingDetails(){
        try {
            int id = Database.autoNumber("`following-lift`", "id");
            int lift_id = Integer.parseInt(lbl_lift_id.getText());
            String followingDetails = txt_followingDetails.getText();
            Date followingDate ;
            if(datePicker_followingDate.getValue()==null){
                followingDate = Date.valueOf(LocalDate.now());
            }
            else{
            followingDate = Date.valueOf(datePicker_followingDate.getValue());
            }
            String progressDetails = comboBox_ratio.getSelectionModel().getSelectedItem();
            int idOfProgress = Database.gettingIdOfProgressDetails(progressDetails);// return id of progrss details 
            Following newOne = new Following(id, lift_id, followingDetails, followingDate, idOfProgress,User.activeUser.getId());
            System.out.println("active user id "+User.activeUser.getUserName()+"   id   "+ User.activeUser.getId());
            int isInserted = Database.insertNewFollowing(newOne);
            if (isInserted>0){
                Database.alertMessage("عملية ناجحة");
                loadAlLLastFollowing();
            }
            
            
        }  catch (SQLException ex) {
            Database.alertMessage(ex.getMessage());
        }
        catch(NumberFormatException ex){
           Database.alertInformation("برجاء اختيار الصف المصعد الحالية ");
        }
        
    }
    public void updateRecord(){
    
        try {
            int id = Integer.parseInt(lbl_followingID.getText());
            int lift_id = Integer.parseInt(lbl_lift_id.getText());
            String followingDetails = txt_followingDetails.getText();
            Date followingDate ;
            if(datePicker_followingDate.getValue()==null){
                followingDate = Date.valueOf(LocalDate.now());
            }
            else{
            followingDate = Date.valueOf(datePicker_followingDate.getValue());
            }
            String progressDetails = comboBox_ratio.getSelectionModel().getSelectedItem();
            int idOfProgress = Database.gettingIdOfProgressDetails(progressDetails);// return id of progrss details 
            System.out.println("active user id "+User.activeUser.getUserName()+"   id   "+ User.activeUser.getId());
            Following updatedOne = new Following(id, lift_id, followingDetails, followingDate, idOfProgress,User.activeUser.getId());
            int isUpdated = Database.updateFollowingDetails(updatedOne);
            if(isUpdated > 0 ){
                Database.alertInformation("عملية ناجحة ");
                loadAlLLastFollowing();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Database.alertMessage(ex.getMessage());
        }
    }
    public void deleteRecord(){
    try{
        int id = Integer.parseInt(lbl_followingID.getText());
        int isDeleted = Database.deleteFollowingRecord(id);
        if(isDeleted>0){
            Database.alertInformation("عملية ناجحة");
            loadAlLLastFollowing();
        }
    }catch(SQLException ex){
        Database.alertMessage(ex.getMessage());
        }
    }
     public Date getFinsihDate(Date startWork , int numOfWorkDay) {
        // Create a date string in the format of "yyyy-MM-dd"
        String dateString = startWork.toString();
        // Parse the date string to a LocalDate object using a formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        // Add 5 days to the date
        LocalDate futureDate = date.plusDays(numOfWorkDay);
        return Date.valueOf(futureDate);
    }
     
     public void downloadImg(){
         
        try {
            Following selected = table_follwoing.getSelectionModel().getSelectedItem();
            Blob ImgFile = Database.gettingImgFile(selected.getId());
            byte byteArray[] = ImgFile.getBytes(1,(int)ImgFile.length());
            String liftNumber = lbl_liftNumber.getText();
            String [] liftNum = liftNumber.split(" ");
            liftNumber ="";
            for (int i = 0; i < liftNum.length ; i++) {
                liftNumber = liftNumber+"_"+liftNum[i];
            }
            String value = ReadingFile.getLinePathToSaveImage("filePath");
            Path folderPath = Paths.get(value+"\\"+liftNumber); 
            
            if(!Files.exists(folderPath)){
                Files.createDirectories(folderPath);
            }
            Path imgPath = folderPath.resolve("img");
            FileOutputStream outPutStream = new FileOutputStream(imgPath.toFile());
            System.out.println("here");
            outPutStream.write(byteArray);
            openImage(imgPath.toString());
                    } catch (SQLException ex) {
            Logger.getLogger(Following_subCardController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Following_subCardController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Following_subCardController.class.getName()).log(Level.SEVERE, null, ex);
        }
     
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
