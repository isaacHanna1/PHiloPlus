
package philoplus.philoPlusClasses;

import java.awt.geom.Area;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javax.swing.JOptionPane;
import philoplus.FXMLFILES.BillFromCompanyToLiftController;
import philoplus.FXMLFILES.TypeOfLifts;
import philoplus.philoPlusClasses.*;
public class Database {
     static  String url = ""; // to contain the url for date base
     static String database_name = "philo-plus";//to contain database name 
     static Connection connection;  // the variable that is make connection betwen database and java
     static void setUrl() {//to intialize the url of date base 

         String serverIP = ReadingFile.getLinePathToSaveImage("server");
        url = serverIP + database_name
                + "?useUnicode=true&characterEncoding=UTF-8";
    }

    public void setDatebaseName(String databaseName) { // to set new database 

        database_name = databaseName;
    }
    public  Connection getConnection (){
        return  connection ;
    }
    public  static void setConection() {//to intializ my conection for date base usring url , username , paseword
        try {
             Class.forName("com.mysql.jdbc.Driver");
            setUrl();
            connection = DriverManager.getConnection(url, "ENGSEHA", "");
        } catch (SQLException ex) {
            JOptionPane.showConfirmDialog(null, ex.getMessage());
        }  catch (ClassNotFoundException ex) {
             alertMessage(ex.getMessage());
           }
    }
    
    
    //== >  Start utilites functions that serve  database functions
    
    //dialog message to alert user
    public static void alertMessage(String message){
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("رسالة خطأ");
        alert.setHeaderText(" خطأ !");
        Label label = new Label(message);
        label.setStyle("-fx-font-size: 16pt;");
        alert.getDialogPane().setContent(label);
        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        Optional<ButtonType> result = alert.showAndWait();
     
    }
   
        public static void alertInformation(String message){
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("رسالة تأكيد");
        alert.setHeaderText("رسالة تأكيد");
        Label label = new Label(message);
        label.setStyle("-fx-font-size: 14pt;");
        alert.getDialogPane().setContent(label);
        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        Optional<ButtonType> result = alert.showAndWait();
       
    }
    // function return the largest value in column to make auto number by add one to the largest value 
    public static int autoNumber(String tableName, String columnName) throws SQLException                                                        {

        int autoNumber = 1; //intial with 1 if table is empty
      
             setConection();
             //sql querey to get the max value in any column in any tabel
             String sql ="SELECT MAX("+columnName+") AS "+columnName+" from "+tableName +" having Max("+columnName+") is not null";
             Statement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery(sql);
             while (resultSet.next()) { // if return empty value  this means the table is empty  so put auto number = 1              
                   int  currentMax = resultSet.getInt(columnName); // max value in column 
                   autoNumber =currentMax +1 ; // why we addded one to autop number variable ? cause max function return max value but i want add new row with new value = max value + 1
             }
            connection.close();
         
             return autoNumber;
        
        }
    

    
    //== >  End utilites functions that serve  data base functions
    
    
    
    //==> Start queries of companies class 
    
    //companies class 
    //insert new company 
    public static int insertNewCompanies(Companies newCompany) throws SQLException{
        setConection();
        String sql = "INSERT INTO `companies`(`id`, `company-name`, `country`, `province`, `police-station`, `street-num`, `building-num`, `apartment-num`, `tax-num`, `notes`) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps =  connection.prepareStatement(sql);
        ps.setInt(1, newCompany.getId() );
        ps.setString(2, newCompany.getCompany_name());
        ps.setString(3, newCompany.getCountry());
        ps.setString(4, newCompany.getProvince());
        ps.setString(5, newCompany.getPolice_station());
        ps.setString(6, newCompany.getStreet_num());
        ps.setString(7, newCompany.getBuilding_num());
        ps.setString(8, newCompany.getApartment_num());
        ps.setString(9, newCompany.getTax_num());
        ps.setString(10, newCompany.getNotes());
        int isInserted = ps.executeUpdate();
        connection.close();
        return isInserted;
    }
    //search in compaies table by company name 
    public static ResultSet searchInCompanies(String companyName) throws SQLException{
        setConection();
        
               String sql = "SELECT * FROM `companies` WHERE `company-name` like ?";
               PreparedStatement statement = connection.prepareStatement(sql);
               statement.setString(1, "%"+companyName+"%");
               ResultSet resultSet = statement.executeQuery();
               return resultSet;
          
        }
    //==> select all companies in system 
      public static ResultSet searchInCompanies() throws SQLException{
        setConection();
               String sql = "SELECT * FROM `companies`";
               PreparedStatement statement = connection.prepareCall(sql);
               ResultSet resultSet = statement.executeQuery();
               return resultSet;
        }
      
      public static int editCompanyData(int id, Companies object ) throws SQLException{
          setConection();
          String sql = "update companies set id = ? ,  `company-name` = ? , `country` = ? , `province` = ? , \n" +
                            "`police-station` = ? , `street-num` = ? , `building-num` = ? , `apartment-num` =  ? , \n" +
                               "`tax-num` = ? , `notes` = ? where id = ?";
          PreparedStatement preparedStatement = connection.prepareStatement(sql);
          preparedStatement.setInt(1, id);
          preparedStatement.setString(2, object.getCompany_name());
          preparedStatement.setString(3, object.getCountry());
          preparedStatement.setString(4, object.getProvince());
          preparedStatement.setString(5, object.getPolice_station());
          preparedStatement.setString(6, object.getStreet_num());
          preparedStatement.setString(7, object.getBuilding_num());
          preparedStatement.setString(8, object.getApartment_num());
          preparedStatement.setString(9, object.getTax_num());
          preparedStatement.setString(10, object.getNotes());
          preparedStatement.setInt(11, id);
          int isUpdated = preparedStatement.executeUpdate();
          connection.close();
          return isUpdated;
      }
      
      // delete record from compnies 
      public static int deleteRecord (int id ) throws SQLException{
          setConection();
          String sql = "delete from  companies where id = "+id;
          PreparedStatement preparedStatement = connection.prepareStatement(sql);
          int isDeleted = preparedStatement.executeUpdate();
          connection.close();
          return isDeleted ;
                 
      }
      
      public static ResultSet gettingAllCampaniesName () throws SQLException{
        setConection();
               String sql = "SELECT `company-name` FROM `companies`";
               PreparedStatement statement = connection.prepareCall(sql);
               ResultSet resultSet = statement.executeQuery();
               return resultSet;
      }
      
      public static int gettingCompanyId (String companyName)throws SQLException{
      setConection();
               String sql = "SELECT id  FROM `companies` where `company-name` = ?";
               PreparedStatement statement = connection.prepareCall(sql);
               statement.setString(1, companyName);
               ResultSet resultSet = statement.executeQuery();
               int id = 0;
               while (resultSet.next()) {              
                   id = resultSet.getInt(1);
                 }
               return id ;
      }
    //==> End queries of companies class 
    
    //==> Start queries for areas
          public static int insertNewAreas(Areas newAreas) throws SQLException{
        setConection();
        String sql = "INSERT INTO `area`(`id`, `country`, `province`, `area-name`, `notes`)  "
                + "VALUES (?,?,?,?,?)";
        PreparedStatement ps =  connection.prepareStatement(sql);
        ps.setInt(1, newAreas.getId() );
        ps.setString(2, newAreas.getCountry());
        ps.setString(3, newAreas.getProvince());
        ps.setString(4, newAreas.getAreaName());
        ps.setString(5, newAreas.getNotes());
        int isInserted = ps.executeUpdate();
        connection.close();
        return isInserted;
    }
         //search in compaies table by company name 
    public static ResultSet searchInAreas(String areaName) throws SQLException{
        setConection();
        
               String sql = "SELECT * FROM `area` WHERE `area-name` like ?";
               PreparedStatement statement = connection.prepareStatement(sql);
               statement.setString(1, "%"+areaName+"%");
               ResultSet resultSet = statement.executeQuery();
               return resultSet;
          
        }
        //==> select all companies in system 
      public static ResultSet searchInAreas() throws SQLException{
        setConection();
               String sql = "SELECT * FROM `area`";
               PreparedStatement statement = connection.prepareCall(sql);
               ResultSet resultSet = statement.executeQuery();
               return resultSet;
        }
         public static int editAreaData(int id, Areas object ) throws SQLException{
          setConection();
          String sql = "update area set id = ? ,  `country` = ? , `province` = ? , `area-name` = ? , \n" +
                            "`notes` = ?  where id = ?";
          PreparedStatement preparedStatement = connection.prepareStatement(sql);
          preparedStatement.setInt(1, id);
          preparedStatement.setString(2, object.getCountry());
          preparedStatement.setString(3, object.getProvince());
          preparedStatement.setString(4, object.getAreaName());
          preparedStatement.setString(5, object.getNotes());
          preparedStatement.setInt(6, id);
          int isUpdated = preparedStatement.executeUpdate();
          connection.close();
          return isUpdated;
      }
           public static int deleteRecordArea (int id ) throws SQLException{
          setConection();
          String sql = "delete from  area where id = "+id;
          PreparedStatement preparedStatement = connection.prepareStatement(sql);
          int isDeleted = preparedStatement.executeUpdate();
          connection.close();
          return isDeleted ;           
      }
    //==> End queries for areas  
    // Start technician 
     public static int insertNewTechnical(Technician one) throws SQLException {
        setConection();
        String sql = "INSERT INTO `technician-aides`(`id`, `is-technician`, `name`, `technician-id`, `national-id`,\n "
                + "`tel-nums`, `start-work`, `experience-year`, `notes`) "
                + "VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps =  connection.prepareStatement(sql);
        ps.setInt(1, one.getId());
        ps.setString(2, one.getIsTechnician());
        ps.setString(3, one.getName());
        ps.setObject(4, null);//i insert zero because i insert technical not Adie so there no technical lead technical 
        ps.setString(5, one.getNationalId());
        ps.setString(6, one.getTelephones());
        ps.setDate(7, one.getStartWork());
        ps.setInt(8, one.getExperienceYear());
        ps.setString(9, one.getNotes());
        int isInserted = ps.executeUpdate();
        connection.close();
        return isInserted;
    }
     public static int  editTechnicainData(int id , Technician updatedObject) throws SQLException{
     setConection();
          String sql = "update `technician-aides` set `id` = ? ,`is-technician` = ? , `name` = ? ,`technician-id` = ? , `national-id` = ? ,`tel-nums` = ? , `start-work` = ? ,`experience-year` = ? , `notes` = ?  where `id` = ?";
          PreparedStatement preparedStatement = connection.prepareStatement(sql);
          preparedStatement.setInt(1, id);
          preparedStatement.setString(2, updatedObject.getIsTechnician());
          preparedStatement.setString(3, updatedObject.getName());
          preparedStatement.setObject(4,null);
          preparedStatement.setString(5, updatedObject.getNationalId());
          preparedStatement.setString(6, updatedObject.getTelephones());
          preparedStatement.setDate(7, updatedObject.getStartWork());
          preparedStatement.setInt(8, updatedObject.getExperienceYear());
          preparedStatement.setString(9, updatedObject.getNotes());
          preparedStatement.setInt(10, id);
          int isUpdated = preparedStatement.executeUpdate();
          connection.close();
          return isUpdated;
     }
     
     public static int deleteTechnical(int id) throws SQLException{
          setConection();
          String sql = "delete from  `technician-aides` where id = "+id;
          PreparedStatement preparedStatement = connection.prepareStatement(sql);
          int isDeleted = preparedStatement.executeUpdate();
          connection.close();
          return isDeleted ;  
     }
         public static ResultSet searchInTechnican(String Technician_Name) throws SQLException{
               setConection();
               String sql = "SELECT * FROM `technician-aides` WHERE name like ? AND `is-technician` = 'فني'";
               PreparedStatement statement = connection.prepareStatement(sql);
               statement.setString(1, "%"+Technician_Name+"%");
               ResultSet resultSet = statement.executeQuery();
               return resultSet;
        }
            public static ResultSet searchInTechnican() throws SQLException{
               setConection();
               String sql = "SELECT * FROM `technician-aides` where `is-technician` = 'فني'";
               PreparedStatement statement = connection.prepareCall(sql);
               ResultSet resultSet = statement.executeQuery();
               return resultSet;
        } 
    // End technician   
            
    // Start adie 
          public static int insertNewAdie(adie one) throws SQLException {
        setConection();
        String sql = "INSERT INTO `technician-aides`(`id`, `is-technician`, `name`, `technician-id`, `national-id`,\n "
                + "`tel-nums`, `start-work`, `experience-year`, `notes`) "
                + "VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps =  connection.prepareStatement(sql);
        ps.setInt(1, one.getId());
        ps.setString(2, one.getIsTechnician());
        ps.setString(3, one.getName());
        ps.setInt(4, one.getTechnical_id());//i insert zero because i insert technical not Adie so there no technical lead technical 
        ps.setString(5, one.getNationalId());
        ps.setString(6, one.getTelephones());
        ps.setDate(7, one.getStartWork());
        ps.setInt(8, one.getExperienceYear());
        ps.setString(9, one.getNotes());
        int isInserted = ps.executeUpdate();
        connection.close();
        return isInserted;
    }
       public static int  editAdieData(int id , adie updatedObject) throws SQLException{
          setConection();
          String sql = "update `technician-aides` set `id` = ? ,`is-technician` = ? , `name` = ? ,`technician-id` = ? , `national-id` = ? ,`tel-nums` = ? , `start-work` = ? ,`experience-year` = ? , `notes` = ?  where `id` = ?";
          PreparedStatement preparedStatement = connection.prepareStatement(sql);
          preparedStatement.setInt(1, id);
          preparedStatement.setString(2, updatedObject.getIsTechnician());
          preparedStatement.setString(3, updatedObject.getName());
          preparedStatement.setInt(4,updatedObject.getTechnical_id());
          preparedStatement.setString(5, updatedObject.getNationalId());
          preparedStatement.setString(6, updatedObject.getTelephones());
          preparedStatement.setDate(7, updatedObject.getStartWork());
          preparedStatement.setInt(8, updatedObject.getExperienceYear());
          System.out.println("experience "+updatedObject.getExperienceYear());
          preparedStatement.setString(9, updatedObject.getNotes());
          System.out.println("notes  "+updatedObject.getNotes() );
          preparedStatement.setInt(10, id);
          System.out.println(sql);
          int isUpdated = preparedStatement.executeUpdate();
          connection.close();
          return isUpdated;
     }
         public static int deleteAdie(int id) throws SQLException{
          setConection();
          String sql = "delete from  `technician-aides` where id = "+id;
          PreparedStatement preparedStatement = connection.prepareStatement(sql);
          int isDeleted = preparedStatement.executeUpdate();
          connection.close();
          return isDeleted ;  
     }
       public static ResultSet searchInAdie(String adie_Name) throws SQLException{
               setConection();
               String sql = "SELECT  a.* , t.name AS technician_name FROM `technician-aides` a INNER JOIN `technician-aides` t ON a.`technician-id` = t.id WHERE a.`is-technician` = 'مساعد' and a.name like ?";
               PreparedStatement statement = connection.prepareStatement(sql);
              statement.setString(1, "%"+adie_Name+"%");
               System.out.println(sql);
               ResultSet resultSet = statement.executeQuery();
               return resultSet;
        }
            public static ResultSet searchInAdie() throws SQLException{
                System.out.println("im called ");
                setConection();
               String sql = "SELECT  a.* , t.name AS technician_name FROM `technician-aides` a INNER JOIN `technician-aides` t ON a.`technician-id` = t.id WHERE a.`is-technician` = 'مساعد' ";
               PreparedStatement statement = connection.prepareStatement(sql);
               ResultSet resultSet = statement.executeQuery();
               return resultSet;
        } 
            
            public static String getTechnicalName(int id ) throws SQLException{
                setConection();
                String sql = "SELECT name FROM `technician-aides` WHERE id = ?" ;
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                ResultSet rs = preparedStatement.executeQuery();
                String technicanName = "";
                while (rs.next()) {                    
                    technicanName = rs.getString(1);
                }
                return  technicanName;
            }
            public static int getTechnicalId(String name) throws SQLException{
             setConection();   
             String sql = "SELECT id FROM `technician-aides` WHERE name = ? AND `is-technician` = 'فني'" ;
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             preparedStatement.setString(1, name);
             ResultSet rs = preparedStatement.executeQuery();
             int id = 0;   
             while (rs.next()) {                     
                id = rs.getInt(1);       
                }
                return id ;
            }
            
            public static ResultSet getAllTechnicalName() throws SQLException{
                setConection();
                String sql = "SELECT name FROM `technician-aides` where `is-technician` = 'فني'" ;
                PreparedStatement preparedStatement = connection.prepareStatement(sql); 
                ResultSet rs = preparedStatement.executeQuery();
                return rs ;
            }
     // End Adied
            
            
     // start sites
          public static ResultSet gettingAreaName() throws SQLException{
          setConection();
                String sql = "SELECT `area-name` FROM `area` " ;
                PreparedStatement preparedStatement = connection.prepareStatement(sql); 
                ResultSet rs = preparedStatement.executeQuery();
                return rs ;
          }
         
          public static int gettingAreaId(String areaName)throws SQLException{
                   setConection();
                String sql = "SELECT `id` FROM `area` WHERE `area-name` = ?" ;
                PreparedStatement preparedStatement = connection.prepareStatement(sql); 
                preparedStatement.setString(1, areaName);
                ResultSet rs = preparedStatement.executeQuery();
                if(rs.next()){
                    return rs.getInt(1);
                }
                else{
                return  0;
                }
          }
         
          public static String gettingAreaName(int areaId)throws SQLException{
                   setConection();
                String sql = "SELECT`area-name` FROM `area` WHERE id = ?" ;
                PreparedStatement preparedStatement = connection.prepareStatement(sql); 
                preparedStatement.setInt(1, areaId);
                ResultSet rs = preparedStatement.executeQuery();
                if(rs.next()){
                    return rs.getString(1);
                }
                else{
                return  null;
                }
          }
          
          public  static int insertIntoSites(Sites newOne)throws SQLException{
          setConection();
          String sql = "INSERT INTO `sites`(`id`, `site-name`, `area-id`, `po`, `engineer-name`, `engineer-num`, `site-admin`, `site-admin-num`, `our-engineer-name`, `our-engineer-num`, `our-site-admin`, `our-site-admin-num`, `note`) "
                  + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
          PreparedStatement statement = connection.prepareStatement(sql);
          statement.setInt(1, newOne.getId());
          statement.setString(2, newOne.getSiteName());
          statement.setInt(3, newOne.getAreaId());
          statement.setString(4, newOne.getPO());
          statement.setString(5, newOne.getSiteEngineer());
          statement.setString(6, newOne.getSiteEngineerTelephone());
          statement.setString(7, newOne.getSiteAdmin());
          statement.setString(8, newOne.getSiteAdminTelephone());
          statement.setString(9, newOne.getOurSiteEngineer());
          statement.setString(10, newOne.getOurSiteEngineerTelephone());
          statement.setString(11, newOne.getOurSiteAdmin());
          statement.setString(12, newOne.getOurSiteAdminTelephone());
          statement.setString(13, newOne.getNote());
          int isInserted  = statement.executeUpdate();
          return isInserted;
          }
          public static int editSiteData(int id , Sites newOne)throws SQLException{
          setConection();
          String sql = "UPDATE `sites` SET `site-name`= ?,`area-id`= ?,`po`= ?,`engineer-name`= ?,`engineer-num`= ?,"
                  + "`site-admin`= ?,`site-admin-num`= ?,`our-engineer-name`= ?,"
                  + "`our-engineer-num`= ?,`our-site-admin`= ?,`our-site-admin-num`= ?"
                  + ",`note`= ? WHERE `id` = ?";
           PreparedStatement statement = connection.prepareStatement(sql);
          statement.setString(1, newOne.getSiteName());
          statement.setInt(2, newOne.getAreaId());
          statement.setString(3, newOne.getPO());
          statement.setString(4, newOne.getSiteEngineer());
          statement.setString(5, newOne.getSiteEngineerTelephone());
          statement.setString(6, newOne.getSiteAdmin());
          statement.setString(7, newOne.getSiteAdminTelephone());
          statement.setString(8, newOne.getOurSiteEngineer());
          statement.setString(9, newOne.getOurSiteEngineerTelephone());
          statement.setString(10, newOne.getOurSiteAdmin());
          statement.setString(11, newOne.getOurSiteAdminTelephone());
          statement.setString(12, newOne.getNote());
          statement.setInt(13, id);
          int isInserted  = statement.executeUpdate();
          return isInserted;
          }
          public static int deleteSite(int id) throws SQLException{
          setConection();
          String sql = "delete from `sites` where id = ?";
          PreparedStatement statement = connection.prepareStatement(sql);
          statement.setInt(1, id);
          int deletted = statement.executeUpdate();
          return  deletted ;
          }
          public static ResultSet searchInSites(String siteName)throws SQLException{
              setConection();
        
               String sql = "SELECT * FROM `sites` WHERE `site-name` like ?";
               PreparedStatement statement = connection.prepareStatement(sql);
               statement.setString(1, "%"+siteName+"%");
               ResultSet resultSet = statement.executeQuery();
               return resultSet;
              
          }
          public static ResultSet loadAllsites() throws SQLException{
              setConection();
               String sql = "SELECT * FROM `sites`";
               PreparedStatement statement = connection.prepareStatement(sql);
               ResultSet resultSet = statement.executeQuery();
               return resultSet;
          }
          
          public static ResultSet gettingAllSitesName ()throws SQLException{
          setConection();
               String sql = "SELECT `site-name` FROM `sites`";
               PreparedStatement statement = connection.prepareStatement(sql);
               ResultSet resultSet = statement.executeQuery();
               return resultSet;
          }
          
         public static int gettingSiteId(String siteName) throws SQLException{
         setConection();
               String sql = "SELECT id FROM `sites` where `site-name` = ?";
               PreparedStatement statement = connection.prepareStatement(sql);
               statement.setString(1, siteName);
               ResultSet resultSet = statement.executeQuery();
               int id = 0 ;
               while (resultSet.next()) {                 
                 id = resultSet.getInt(1);
             }
         return id;
         }
     // End sites  
          
    // start of type of lifts 
          
          public static int insertTypeOfLifts(TypeOfLifts newOne)throws SQLException{
              
              setConection();
              String sql = "INSERT INTO `lift-type`(`id`, `lift-type-details`, `notes`) VALUES (?,?,?)";
              PreparedStatement statement = connection.prepareStatement(sql);
              statement.setInt(1, newOne.getId());
              statement.setString(2, newOne.getTyoeOfLift());
              statement.setString(3, newOne.getNote());
              int isInserted = statement.executeUpdate();
              return isInserted;
          }
          
         public static int editTypeOfLiftData(int id , TypeOfLifts newOne)throws SQLException{
          setConection();
          String sql = "UPDATE `lift-type` SET `lift-type-details`= ?,`notes`= ? "
                  + " WHERE `id` = ?";
           PreparedStatement statement = connection.prepareStatement(sql);
          statement.setString(1, newOne.getTyoeOfLift());
          statement.setString(2, newOne.getNote());
          statement.setInt(3, id);
          int isUpdated  = statement.executeUpdate();
          return isUpdated;
          }
       public static int deleteTypeOfDetails(int id) throws SQLException{
          setConection();
          String sql = "delete from `lift-type` where id = ?";
          PreparedStatement statement = connection.prepareStatement(sql);
          statement.setInt(1, id);
          int deletted = statement.executeUpdate();
          return  deletted ;
          }
          public static ResultSet searchIntoTypeOfLift(String typeDetails) throws SQLException{
           setConection();
        
               String sql = "SELECT * FROM `lift-type` WHERE `lift-type-details` like ?";
               PreparedStatement statement = connection.prepareStatement(sql);
               statement.setString(1, "%"+typeDetails+"%");
               ResultSet resultSet = statement.executeQuery();
               return resultSet;
          
          }
             public static ResultSet loadAllTypeDetailsOfLift() throws SQLException{
              setConection();
               String sql = "SELECT * FROM `lift-type`";
               PreparedStatement statement = connection.prepareStatement(sql);
               ResultSet resultSet = statement.executeQuery();
               return resultSet;
          }
   // end of type of lifts 
             
  // Start details of stage             
           
      public static ResultSet gettingAllLiftType()throws SQLException{      
            setConection();
            String sql = "select `lift-type-details` from `lift-type` ";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            return  rs; 
            }
    public static int gettingIdOfLiftType(String liftType) throws SQLException{
        setConection();
        String sql = "SELECT `id` FROM `lift-type` WHERE `lift-type-details` like ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%"+liftType+"%");
        ResultSet rs = preparedStatement.executeQuery();
        int idOfLiftType = 0;
        while (rs.next()) {
            idOfLiftType = rs.getInt(1);
        }
        return idOfLiftType;
    }
    public static String gettingDetailsOfLiftType(int id) throws SQLException{
        setConection();
        String sql = "SELECT `lift-type-details`  FROM `lift-type` WHERE `lift-type-details` `id` like ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        ResultSet rs = preparedStatement.executeQuery();
        String LiftType = "";
        while (rs.next()) {
            LiftType = rs.getString(1);
        }
        return LiftType;
    }
    public static int insertNewLiftProgressDetails(liftProgress lift)throws SQLException{
        setConection();
        String sql  = "INSERT INTO `lift-type-progress`(`id`, `lift-type-id`, `progress-details`, `progress-ratio`) VALUES  (?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, lift.getId());
        ps.setInt(2, lift.getLiftTypeId());
        ps.setString(3, lift.getLiftProgressDetails());
        ps.setFloat(4, lift.getRatio());
        int isInserted = ps.executeUpdate();
        return isInserted;
    }
    
    public static int editLiftProgressDetails(int id , liftProgress updated) throws SQLException{
        setConection();
        String sql = "UPDATE `lift-type-progress` SET `lift-type-id`= ?,`progress-details`= ?,`progress-ratio`= ? WHERE id = ? ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, updated.getLiftTypeId());
        ps.setString(2, updated.getLiftProgressDetails());
        ps.setFloat(3, updated.getRatio());
        ps.setInt(4, id);
        return ps.executeUpdate();
    
    }
    
    public static int deleteRecordOfLiftProgress (int id)throws  SQLException{
    
           setConection();
           String sql = "delete from `lift-type-progress` where id = ?";
           PreparedStatement statement = connection.prepareStatement(sql);
           statement.setInt(1, id);
          int deletted = statement.executeUpdate();
          return  deletted ;
        
    }
    
    // return the stages progess of lift type 
    public static ResultSet gettingAllStagesOfSpecialLiftType(int liftTypeId)throws SQLException{
        setConection();
        String sql = "SELECT `lift-type-progress`.`id` , `lift-type-progress`.`progress-details` , `lift-type-progress`.`progress-ratio` from `lift-type-progress` where `lift-type-progress`.`lift-type-id` =  ? order by  `lift-type-progress`.`progress-ratio` ";
        PreparedStatement ps = connection.prepareStatement(sql);
        System.out.println(sql);
        ps.setInt(1, liftTypeId);
        return ps.executeQuery();
    }
 // Start details of stage      
 
 // Start lifts 
    
    public static int insertNewLift(Lift newOne)throws SQLException{
        
        setConection();
        String sql ="INSERT INTO `our-lifts`(`id`, `po`, `lift-num`, `lift-type-id`, `lift-floor-num`, `lift-well-num`, `start-work`, `num-work-day`, `site-id`, `company-id`) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, newOne.getId());
        ps.setString(2, newOne.getPo());
        ps.setString(3, newOne.getLiftNum());
        ps.setInt(4, newOne.getLift_type_id());
        ps.setInt(5, newOne.getLift_floor_number());
        ps.setInt(6, newOne.getLift_well_num());
        ps.setDate(7, newOne.getStartWork());
        ps.setInt(8, newOne.getNum_work_day());
        ps.setInt(9, newOne.getSiteId());
        ps.setInt(10, newOne.getCompanyID());
       return ps.executeUpdate();       
    }
    public static int editLiftData( Lift updatedOne)throws SQLException{
        setConection();
        String sql = "UPDATE `our-lifts` SET `po`= ?,`lift-num`= ?,`lift-type-id`= ?,`lift-floor-num`= ?,"
                + "`lift-well-num`= ?,`start-work`= ?,`num-work-day`= ?,`site-id`= ?,`company-id`= ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, updatedOne.getPo());
        ps.setString(2, updatedOne.getLiftNum());
        ps.setInt(3, updatedOne.getLift_type_id());
        ps.setInt(4, updatedOne.getLift_floor_number());
        ps.setInt(5, updatedOne.getLift_well_num());
        ps.setDate(6, updatedOne.getStartWork());
        ps.setInt(7, updatedOne.getNum_work_day());
        ps.setInt(8, updatedOne.getSiteId());
        ps.setInt(9, updatedOne.getSiteId());
        ps.setInt(10, updatedOne.getId());
        return ps.executeUpdate();
    }
    
    public static int deleteLift(int id ) throws SQLException{
        setConection(); 
        String sql = "delete from `our-lifts` where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        return  ps.executeUpdate();
    }
    
    // loadAllSavedCreatedLifts
    public static  ResultSet loadAllSavedLifts() throws SQLException{
        setConection();
        String sql = "SELECT `our-lifts`.`id`,`our-lifts`.`po`,`lift-num`,`lift-type`.`lift-type-details`,`lift-floor-num`,`lift-well-num`,`start-work`,`num-work-day`,`sites`.`site-name`, `companies`.`company-name`, count(`technician-of-lift`.`lift-id` ) "
                + "FROM `our-lifts`LEFT join `technician-of-lift` ON `our-lifts`.`id` = `technician-of-lift`.`lift-id` "
                + "LEFT join `lift-type`ON `lift-type`.`id` = `our-lifts`.`lift-type-id` "
                + "LEFT join `sites` on `sites`.`id` = `our-lifts`.`site-id` LEFT join `companies` on "
                + "`companies`.`id` = `our-lifts`.`company-id` GROUP by `our-lifts`.`id` ";
            PreparedStatement ps = connection.prepareStatement(sql);
            return  ps.executeQuery();
    }
    
    // Start `technician-of-lift`
    public static int insertIntoTechnician_of_lift(int id , Technician newOne)throws SQLException{
            setConection();
            String sql = "INSERT INTO `technician-of-lift`(`id`, `technician-id`, `lift-id`) VALUES (?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id); // auto increment to insert new record 
            ps.setInt(2, newOne.getId()); // techncian id 
            ps.setInt(3, newOne.getLiftId());// lift id that techncian responsible for 
            return  ps.executeUpdate();
    }  
    public static ResultSet gettingAllTechnciansAssignedToLift(int liftId)throws  SQLException{
        setConection(); 
        String sql = "SELECT `technician-aides`.id ,`technician-aides`.`name` ,`technician-aides`.`tel-nums` FROM `technician-aides` "
                + "INNER join `technician-of-lift` on "
                + "`technician-of-lift`.`technician-id` = `technician-aides`.`id` and `technician-of-lift`.`lift-id` = ? ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, liftId);
        return  ps.executeQuery();
    }
    //delete techncian from lift
    public static int deleteTechncianFromLiftRespomsibility(int id_techncian , int liftId) throws SQLException{
        setConection();
        String sql = "delete from `technician-of-lift` where  `technician-id` = ? and `lift-id` = ?"; 
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id_techncian);
        ps.setInt(2, liftId);
        return  ps.executeUpdate();
    }
       //  End `technician-of-lift` 
    
 // End lifts    
    
    
    
    
   //start following 
      public static ResultSet gettingAllLiftNumber() throws SQLException{
          setConection();
          String sql = "SELECT `lift-num` FROM `our-lifts`";
          PreparedStatement ps = connection.prepareStatement(sql);
          return ps.executeQuery();
      }
    public static ResultSet gettingAllTechnicalName () throws SQLException{
          setConection();
          String sql = "SELECT `name` FROM `technician-aides` WHERE `is-technician` = 'فني'";
          PreparedStatement ps = connection.prepareStatement(sql);
          return ps.executeQuery();
    }
    
    public static int gettingLiftIdFromOurLiftByLiftNumber(String liftNumber)throws SQLException{
    
        setConection(); 
        String sql = "SELECT `id` from `our-lifts` WHERE `lift-num` = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, liftNumber);
        ResultSet rs = ps.executeQuery();
        int liftid = 0;
        while (rs.next()) {            
            liftid = rs.getInt(1);
        }
        return liftid;
    }
    public static ResultSet gettingAllStageOfLiftType(int liftTypeId)throws SQLException{
        
        setConection();
        String sql = "SELECT `progress-details` from `lift-type-progress` where `lift-type-id` = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, liftTypeId);
        return  ps.executeQuery();
    }
    public static ResultSet  gettingLiftDataByLiftId(int id )throws SQLException{
        setConection();
        String sql = "SELECT `lift-num` , `lift-type`.`lift-type-details` ,`lift-floor-num`,`lift-well-num`,`companies`.`company-name`,`sites`.`site-name`,`start-work`,`num-work-day` FROM `our-lifts` JOIN `lift-type` on `lift-type`.`id`= `our-lifts`.`lift-type-id` JOIN `sites` on `sites`.`id` = `our-lifts`.`site-id` JOIN `companies` on`companies`.`id` = `our-lifts`.`company-id` WHERE `our-lifts`.id = ? ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeQuery();
    }
    public static int  gettingTechncianId(String techncianName)throws SQLException{
        setConection();
        String sql = "SELECT id from `technician-aides` where `name` = ?" ;
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, techncianName);
        ResultSet rs = ps.executeQuery();
        int id = 0;
        while(rs.next()){
            id = rs.getInt(1);
        }
        return id;
    }
    public static ResultSet gettingLiftNumberAssignedToTechncian(int techncianId ) throws SQLException{
        setConection();
        String sql = "SELECT `our-lifts`.`lift-num` from `our-lifts` INNER JOIN `technician-of-lift` on `technician-of-lift`.`lift-id` = `our-lifts`.`id` WHERE `technician-of-lift`.`technician-id` = ? ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, techncianId);
        return  ps.executeQuery();
    }
    public static int gettingIdOfProgressDetails(String details ) throws SQLException{
        setConection();
        String sql = "SELECT `id` FROM `lift-type-progress` WHERE `progress-details` = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, details);
        ResultSet rs = ps.executeQuery();
        int id = 0;
        while (rs.next()) {            
            id = rs.getInt(1);
        }
        return id ; 
    } 
    public static int insertNewFollowing(Following newFollowing ) throws SQLException{
    
        setConection();
        String sql = "INSERT INTO `following-lift`(`id`, `lift-id`, `following-details`, `following-date`, `progress-id`,`userId`) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, newFollowing.getId());
        ps.setInt(2, newFollowing.getLift_Id());
        ps.setString(3, newFollowing.getFollowing_Details());
        ps.setDate(4, newFollowing.getFollowing_Date());
        ps.setInt(5, newFollowing.getProgress_Id());
        ps.setInt(6, newFollowing.getUserId());
        return  ps.executeUpdate();
    }
    
    public static ResultSet loadAllLastFollowingOfLift(int liftID) throws SQLException{
      setConection();
      String sql = "SELECT `following-lift`.`id` , `following-details`, `lift-type-progress`.`progress-details`, `lift-type-progress`.`progress-ratio` , `following-date` , `users`.`userName` , `img-path`"
              + " from `following-lift`"
                      + "left JOIN `lift-type-progress` on `following-lift`.`progress-id` = `lift-type-progress`.`id` left JOIN `users` On `users`.id =  `following-lift`.`userId` WHERE `lift-id`= ? ";
      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setInt(1, liftID);
      return  ps.executeQuery();
    }
    public static int updateFollowingDetails(Following updatedObject) throws SQLException{
      setConection();
      String sql = "UPDATE `following-lift` SET `following-details`= ? ,`following-date`= ? ,`following-lift`.`progress-id`= ?"
              + " ,`userId` = ?   WHERE `id` = ? and `lift-id` = ? ";
      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setString(1, updatedObject.getFollowing_Details());
      ps.setDate(2, updatedObject.getFollowing_Date());
      ps.setFloat(3, updatedObject.getProgress_Id());
      ps.setInt(4, updatedObject.getUserId());
      ps.setInt(5, updatedObject.getId());
      ps.setInt(6, updatedObject.getLift_Id());
      return ps.executeUpdate();
    }
    public static int deleteFollowingRecord (int id )throws SQLException{
        setConection();
        String sql = "delete from `following-lift` where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        return  ps.executeUpdate();
    }
    
    public static ResultSet UnderFollowingLifts() throws SQLException{
       setConection(); 
// بص ياسيدي انا عملت فيز بيجيب كل المصاعد اللي انا شغال عليها وكمان بيجيب اخر متابعه ليها 
       // عن طريق اي دي بتاع المتابعه وبعد كدة بستثني المصاعد اللي وصل المرحلة بتاعتها مية في مية 
       String sql ="SELECT `lastfollowingofeachlift`.`lift_id`,`our-lifts`.`lift-num` ,`lastfollowingofeachlift`.`LastFollowingId`,`following-lift`.`progress-id` ,`lift-type-progress`.`progress-ratio` AS `ratio` from `lastfollowingofeachlift` LEFT JOIN `following-lift` on `lastfollowingofeachlift`.`lift_id` = `following-lift`.`lift-id` and `lastfollowingofeachlift`.`LastFollowingId` = `following-lift`.`id` LEFT JOIN `lift-type-progress` on `following-lift`.`progress-id` = `lift-type-progress`.`id` LEFT join `our-lifts` on `lastfollowingofeachlift`.`lift_id` = `our-lifts`.`id` WHERE `lift-type-progress`.`progress-ratio` IS NULL OR `lift-type-progress`.`progress-ratio` !=100";
       PreparedStatement ps = connection.prepareStatement(sql);
       return ps.executeQuery();

    }
  
    
    public static Blob gettingImgFile(int follwoingId)throws SQLException{
        
        setConection();
        String sql = "SELECT `img` from `following-lift` WHERE `following-lift`.`id` = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, follwoingId);
        ResultSet rs = ps.executeQuery();
       Blob blob = null;
        while (rs.next()) {            
            blob =rs.getBlob(1);
        }
        return blob;
    }
  // End following 
    //---------------------------------------------------------------------------------------------
    
    
    //Start Bill 
//---------------------------------------------------------------------------------------------------------------------------------
    //Start Bill from philo to tehncian 
    
    public static int insertNewBillFromPhiloTOTechncian(BillPHilo_techncian billObject) throws SQLException{
        
        setConection();
        String sql = "INSERT INTO `bil-philo-technician`(`id`, `lift-id`, `bill_Num`, `bill-date`, `real_floorNumber`, `Priced_floor_num`, `well-num`, `price-of-floor`)"
                + " VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, billObject.getBillId());
        ps.setInt(2, billObject.getLiftId());
        ps.setInt(3, billObject.getBillNumber());
        ps.setDate(4, billObject.getBillDate());
        ps.setInt(5, billObject.getNumberOfFloor());
        ps.setInt(6, billObject.getPriced_floor_num());
        ps.setInt(7, billObject.getNumberOfWell());
        ps.setFloat(8, billObject.getPriceOfFloor());
        return ps.executeUpdate();
    
    }
    
    public static ResultSet selectAllBillFromPhiloToTechncian()throws SQLException{
        setConection();
        String sql = "SELECT `bil-philo-technician`.`id` ,`dataofcreatedlifts`.`id` , `bil-philo-technician`.`bill_Num`,`bil-philo-technician`.`bill-date` , `dataofcreatedlifts`.`lift-num`, "
                + "`dataofcreatedlifts`.`lift-type-details`,`dataofcreatedlifts`. `TechncianNumber`,"
                + "`dataofcreatedlifts`.`site-name`, `dataofcreatedlifts`.`company-name`, `bil-philo-technician`.`real_floorNumber`,"
                + "`bil-philo-technician`.`Priced_floor_num`, `bil-philo-technician`.`well-num`,`bil-philo-technician`.`price-of-floor` "
                + "from `bil-philo-technician` RIGHT JOIN `dataofcreatedlifts` "
                + "on `dataofcreatedlifts`.`id` = `bil-philo-technician`.`lift-id` ORDER by `bil-philo-technician`.`id` DESC ";
        PreparedStatement ps = connection.prepareStatement(sql);
        return  ps.executeQuery();
        
    }
    public static int deleteBillFromPhiloTOTechncian(int idOfBill ) throws SQLException{
    
        setConection();
        String sql = "Delete from  `bil-philo-technician` where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, idOfBill);
        return  ps.executeUpdate();
        
    }
    public static int updateBillFromPhiloToTechncian(BillPHilo_techncian updated) throws SQLException{
        setConection();
        String sql ="UPDATE `bil-philo-technician` "
                + "SET `lift-id`= ? ,`bill-date`= ? ,`real_floorNumber`= ? ,`Priced_floor_num`= ? ,`well-num`= ? ,`price-of-floor`= ? WHERE id = ? ";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1,updated.getLiftId() );
                ps.setDate(2, updated.getBillDate());
                ps.setInt(3, updated.getNumberOfFloor());
                ps.setInt(4, updated.getPriced_floor_num());
                ps.setInt(5, updated.getNumberOfFloor());
                ps.setFloat(6, updated.getPriceOfFloor());
                ps.setInt(7, updated.getBillId());
                return  ps.executeUpdate();

    }
    //End   Bill from philo to tehncian 
//-------------------------------------------------------------------------------------------------------------------------------- 
    //End Bill
// start  user 
public static int insertNewUser(User newUser) throws SQLException{
    setConection();
    String sql = "INSERT INTO `users`(`id`, `userName`, `paswword`, `perviliage`) VALUES  (?,?,?,?)";
    PreparedStatement ps = connection.prepareStatement(sql);
    ps.setInt(1, newUser.getId());
    ps.setString(2, newUser.getUserName());
    ps.setString(3, newUser.getPasssword());
    ps.setString(4, newUser.getPerivilage());
    return  ps.executeUpdate();
}
public static ResultSet gettingAllUsers() throws SQLException{
    setConection();
    String sql = "SELECT `id`, `userName`, `paswword`, `perviliage` FROM `users` ";
    PreparedStatement ps = connection.prepareStatement(sql);
    return  ps.executeQuery();
}
public static int deleteUser(int id )throws SQLException{
        setConection();
        String sql = "delete from users where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeUpdate();
}
public static int editUser(User updateUser )throws SQLException{
        setConection();
        String sql = "UPDATE `users` SET `userName`= ? ,`paswword`= ? ,`perviliage`= ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
          ps.setInt(4, updateUser.getId());
          ps.setString(1, updateUser.getUserName());
          ps.setString(2, updateUser.getPasssword());
          ps.setString(3, updateUser.getPerivilage());
          return ps.executeUpdate();
}
   public static ResultSet checkValidalityLogin(String name , String password)throws SQLException{
             setConection();
             String sql = "SELECT * FROM `users`  where `userName` = ? and `paswword` = ? ";
             PreparedStatement ps = connection.prepareStatement(sql);
             ps.setString(1, name);
             ps.setString(2, password);
             return ps.executeQuery();
         }
   
   public static String gettingPerivilageOfUser(String userName , String password) throws SQLException{
            
            setConection();
            String sql = "SELECT users.perviliage from users where users.userName = ? and users.paswword =? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            String perivilage = "";
            while (rs.next()) {           
                        perivilage = rs.getString(1);
             }
            return perivilage;
   }
   
   public static int gettingUserId(String userName , String password) throws SQLException{
            
            setConection();
            String sql = "SELECT users.id from users where users.userName = ? and users.paswword =? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            int  userId = 0;
            while (rs.next()) {           
               userId = rs.getInt(1);
             }
            return userId;
   }
// end user 
    
   // start bill from philo to mother company 
  
   public static int inserNewBillFromPhiloTOMotherCompany (BillPhiloMotherComapny bill) throws SQLException{
       setConection();
       String sql = "INSERT INTO `bill-philo-mothercompany`(`id`, `lift-id`, `bill_num`, `bill-date`, `num-of-floor`, `priced_floorNumber`, `lift-well-num`, `price-of-floor`, `value-add`, `sent`, `receive`) VALUES "
               + "(?,?,?,?,?,?,?,?,?,?,?)";
       PreparedStatement ps = connection.prepareStatement(sql);
       ps.setInt(1, bill.getBillId());
       ps.setInt(2, bill.getLiftId());
       ps.setInt(3, bill.getBillNumber());
       ps.setDate(4, bill.getBillDate());
       ps.setInt(5, bill.getNumberOfFloor());
       ps.setInt(6, bill.getPriced_floor());
       ps.setInt(7, bill.getNumberOfWell());
       ps.setFloat(8, bill.getPriceOfFloor());
       ps.setFloat(9, bill.getAddationValue());
       ps.setInt(10, bill.getIsSent());
       ps.setInt(11, bill.getIsReceived());
       return ps.executeUpdate();
    }
   
   public static int deleteBillFromMother(int bill_id )throws SQLException{
       setConection();
       String sql = "Delete from `bill-philo-mothercompany` where id= ?";
       PreparedStatement ps = connection.prepareStatement(sql);
       ps.setInt(1, bill_id);
       return  ps.executeUpdate();
   }
   
   public static  int  editBillMother(BillPhiloMotherComapny bill) throws SQLException{
       setConection();
       String sql = "UPDATE `bill-philo-mothercompany` SET `lift-id`= ?,`bill-date`= ? ,`num-of-floor`= ? ,`priced_floorNumber`= ? ,`lift-well-num`= ? ,`price-of-floor`= ? ,`value-add`= ? ,`sent`= ?,`receive`= ? WHERE id = ?";
       PreparedStatement ps = connection.prepareStatement(sql);
       ps.setInt(1, bill.getLiftId());
       ps.setDate(2, bill.getBillDate());
       ps.setInt(3, bill.getNumberOfFloor());
       ps.setInt(4, bill.getPriced_floor());
       ps.setInt(5, bill.getNumberOfWell());
       ps.setFloat(6, bill.getPriceOfFloor());
       ps.setFloat(7, bill.getAddationValue());
       ps.setInt(8, bill.getIsSent());
       ps.setInt(9, bill.getIsReceived());
       ps.setInt(10, bill.getBillId());
       return ps.executeUpdate();
   }
   public static ResultSet loadAllBillFromPhiloToMother()throws SQLException{
       setConection();
       String sql ="SELECT `bill-philo-mothercompany`.`id` ,`dataofcreatedlifts`.`id` , `bill-philo-mothercompany`.`bill_num`, `bill-philo-mothercompany`.`bill-date` , `dataofcreatedlifts`.`lift-num`, `dataofcreatedlifts`.`lift-type-details`,`dataofcreatedlifts`. `TechncianNumber`,`dataofcreatedlifts`.`site-name`, `dataofcreatedlifts`.`company-name`, `bill-philo-mothercompany`.`num-of-floor`,`bill-philo-mothercompany`.`priced_floorNumber`, `bill-philo-mothercompany`.`lift-well-num`,`bill-philo-mothercompany`.`price-of-floor` ,`bill-philo-mothercompany`.`value-add` ,`bill-philo-mothercompany`.`sent`,`bill-philo-mothercompany`.`receive` from `bill-philo-mothercompany` RIGHT JOIN `dataofcreatedlifts` on `dataofcreatedlifts`.`id` = `bill-philo-mothercompany`.`lift-id` ORDER by `bill-philo-mothercompany`.`id` DESC ";
       PreparedStatement ps = connection.prepareStatement(sql);
       return  ps.executeQuery();
   }

   // end bill from philo to mother company 
   
   
   // start addaing value bill
   
   public static int insertNewAddationValue(AddationValueToLift add) throws SQLException{
   
       setConection(); 
       String sql = "INSERT INTO `bill-add-cost`(`id`, `lift-id`, `add-cost`, `reason-to-add`, `added-date`) VALUES (?,?,?,?,?)";
       PreparedStatement ps = connection.prepareStatement(sql);
       ps.setInt(1, add.getBillId());
       ps.setInt(2, add.getLiftId());
       ps.setFloat(3, add.getAddedValue());
       ps.setString(4, add.getReasonOfAdding());
       ps.setDate(5, add.getBillDate());
       return  ps.executeUpdate();
   }
   
   
   public static  ResultSet gettingAddationalBillForEspecificBill(int liftId) throws  SQLException{

    setConection();
    String sql = "SELECT `bill-add-cost`.`id` , `our-lifts`.`lift-num` , `bill-add-cost`.`add-cost` , `bill-add-cost`.`reason-to-add`, `bill-add-cost`.`added-date` from `bill-add-cost` INNER join `our-lifts` \n" +
                       "on `bill-add-cost`.`lift-id` = `our-lifts`.`id`\n" +
                       "WHERE `bill-add-cost`.`lift-id` = ?";
    PreparedStatement ps = connection.prepareStatement(sql);
    ps.setInt(1, liftId);
    return ps.executeQuery();
   }
   
   public static int deleteBillCost(int id )throws SQLException{
   setConection();
   String sql = "delete from `bill-add-cost` where id = ?";
    PreparedStatement ps = connection.prepareStatement(sql);
    ps.setInt(1, id);
    return ps.executeUpdate();
   }
   
   public static int  editAddedBillCost(AddationValueToLift updated)throws SQLException{
       
       setConection();
       String sql = "";
       PreparedStatement ps = connection.prepareStatement("UPDATE `bill-add-cost` SET `add-cost`= ? ,`reason-to-add` = ? ,`added-date`= ? WHERE id = ?");
        ps.setFloat(1, updated.getAddedValue());
        ps.setString(2, updated.getReasonOfAdding());
        ps.setDate(3, updated.getBillDate());
        ps.setInt(4, updated.getBillId());
        return  ps.executeUpdate();
   
   }
   // end dding value bill
   
   //Start transaction 
   
    public static int insertNewTransction (Deposite newOne)throws SQLException{
    
            setConection();
            String sql = "INSERT INTO `lift-payement-transactions`(`id`, `lift_id`, `techmcianId`, `payment_transaction`, `transaction_date`, `transactionDetails`, `suporterPath`, `supportterImg`) VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, newOne.getDepositeId());
            ps.setInt(2, newOne.getLiftId());
            ps.setInt(3, newOne.getTechncian_id());
            ps.setFloat(4, newOne.getTransactionValue());
            ps.setDate(5, newOne.getTransactionDate());
            ps.setString(6, newOne.getDetails());
            ps.setString(7, newOne.getImgSuportPath());
            ps.setBytes(8, newOne.getImageData());
            return  ps.executeUpdate();
    }
    
    public static float gettingTotalOfBillToTechncianForSpecificLift(int liftId){
    try{
        setConection();
        String sql = "SELECT (`bil-philo-technician`.`well-num`* `bil-philo-technician`.`Priced_floor_num`*`bil-philo-technician`.`price-of-floor`) "
                + "from `bil-philo-technician` where `bil-philo-technician`.`lift-id` = ? ";       
        float total = 0;
          PreparedStatement ps = connection.prepareStatement(sql);
          ps.setInt(1, liftId);
          ResultSet rs = ps.executeQuery();
          while (rs.next()) {            
              total =  rs.getFloat(1);
        }
          return  total;
        }catch(SQLException ex){
          alertMessage(ex.getMessage());
          return 0;
    }
    }
    
    public static float gettingTotalOfAddedCostForSpecificLift(int liftId){
    try{
        setConection();
        String sql = "SELECT SUM(`bill-add-cost`.`add-cost`) from `bill-add-cost` WHERE `bill-add-cost`.`lift-id`  = ? ";       
        float total = 0;
          PreparedStatement ps = connection.prepareStatement(sql);
          ps.setInt(1, liftId);
          ResultSet rs = ps.executeQuery();
          while (rs.next()) {            
              total =  rs.getFloat(1);
        }
          return  total;
        }catch(SQLException ex){
          alertMessage(ex.getMessage());
          return 0;
    }
    }
    
    public static ResultSet loaddingAllPervouisTranscation(int liftID) throws SQLException{
        
        setConection();
        String sql = "SELECT `id`, `lift_id`, `techmcianId`, `payment_transaction`, `transaction_date`, `transactionDetails`, `suporterPath`"
                + " FROM `lift-payement-transactions` WHERE lift_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, liftID);
        return  ps.executeQuery();
    }
    
    public static int deleteTranscation(int transactionId ) throws SQLException{
        
        setConection();
        String sql = "delete from `lift-payement-transactions` where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, transactionId);
        return  ps.executeUpdate();
    }
    
    public static int editTransaction(Deposite newOne)throws SQLException{
        setConection();
        String sql = "UPDATE `lift-payement-transactions` SET `lift_id`= ? ,`techmcianId`=? ,`payment_transaction`= ? ,`transaction_date`= ? ,`transactionDetails`= ? ,`suporterPath`= ? ,`supportterImg`= ?  WHERE id = ? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            
            ps.setInt(1, newOne.getLiftId());
            ps.setInt(2, newOne.getTechncian_id());
            ps.setFloat(3, newOne.getTransactionValue());
            ps.setDate(4, newOne.getTransactionDate());
            ps.setString(5, newOne.getDetails());
            ps.setString(6, newOne.getImgSuportPath());
            ps.setBytes(7, newOne.getImageData());
            ps.setInt(8, newOne.getDepositeId());
            return  ps.executeUpdate();
    }
   // end transactions
    //start father Accounts
     public static int insertNewFatherAccount(FatherAccount f) throws SQLException{
         setConection();
         String sql = "INSERT INTO `father-accounts`(`id`, `account-name`) VALUES (?,?)";
         PreparedStatement ps = connection.prepareStatement(sql);
         ps.setInt(1, f.getId());
         ps.setString(2, f.getFatherAccountName());
         return  ps.executeUpdate();
     }
     public static int editFatherAccount(FatherAccount f) throws SQLException{
        setConection();
        String sql = "UPDATE `father-accounts` SET `account-name`= ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
         ps.setString(1, f.getFatherAccountName());
         ps.setInt(2, f.getId());
         return  ps.executeUpdate();
     }
     
     public static int deleteFatherAccount(int id )throws SQLException{
         setConection();
         String sql = "delete from `father-accounts` where id = ? ";
         PreparedStatement ps = connection.prepareStatement(sql);
         ps.setInt(1, id);
         return  ps.executeUpdate();
     }
     
     public static ResultSet loadAllFatherAccounts() throws SQLException{
     
         setConection();
         String sql = "select * from `father-accounts` ";
         PreparedStatement ps = connection.prepareStatement(sql);
         return ps.executeQuery();
     }
     //end father Accounts 
    

// start son account  
    
  public static int insertNewSonAccount(Son s)throws SQLException{
        
    setConection();
    String sql = "INSERT INTO `son`(`id`, `fatheId`, `sonAccount`) VALUES (?,?,?)";
    PreparedStatement ps = connection.prepareStatement(sql);
    ps.setInt(1, s.getId());
    ps.setInt(2, s.getFatherAccountId());
    ps.setString(3, s.getSonAccount());
    return  ps.executeUpdate();
}
    public static int deleteSonAccount(int id )throws SQLException{
        setConection();
        String sql = "delete from `son` where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeUpdate();
    }
    
    public static int updateSonAccount(Son s)throws SQLException{
        setConection();
        String sql = "UPDATE `son` SET `fatheId`= ?,`sonAccount`= ?  WHERE id = ? ";
         PreparedStatement ps = connection.prepareStatement(sql);
         ps.setInt(1, s.getFatherAccountId());
         ps.setString(2, s.getSonAccount());
         ps.setInt(3, s.getId());
         return  ps.executeUpdate();
    }
   public static ResultSet loadAllSonAccount (int fatherId)throws SQLException{
       setConection();
       String sql = " SELECT son.id , `father-accounts`.`account-name` , son.sonAccount from son INNER JOIN `father-accounts` on `father-accounts`.`id` = `son`.`fatheId` where  `fatheId` = ?";
       PreparedStatement ps = connection.prepareStatement(sql);
       ps.setInt(1, fatherId);
       return  ps.executeQuery();
   }
   
   public static int gettingFatherId(String fatherAcountName) throws SQLException{
      
       setConection();
       String sql = " select id  from `father-accounts` where   `account-name`= ? ";
       PreparedStatement ps = connection.prepareStatement(sql);
       ps.setString(1, fatherAcountName);
       ResultSet rs = ps.executeQuery();
       int faherAccountId = 0 ;
       while (rs.next()) {           
        faherAccountId =    rs.getInt(1);
       }
       return faherAccountId;
   }
   
   public static ResultSet loaddingAllFatherAccounts() throws SQLException{
       setConection();
       String sql = "select `account-name` from `father-accounts` ";
       PreparedStatement ps = connection.prepareStatement(sql);
       return  ps.executeQuery();
   }
// end son account 
   
   
   //start pay for 
   
    public static int insertNewPayFor(Payed p) throws SQLException{
       setConection();
       String sql = "INSERT INTO `pay-for`(`id`, `paymentDetails`, `paymentDate`, `paymentValue`, `sonAccountId`)  VALUES (?,?,?,?,?)";
       PreparedStatement ps = connection.prepareStatement(sql);
       ps.setInt(1, p.getId());
       ps.setString(2,p.getPayedDetails());
       ps.setDate(3, p.getPayedDate());
       ps.setFloat(4, p.getPayedValue());
       ps.setInt(5, p.getSonAccountId());
       return  ps.executeUpdate();
   }
   
   public static int deletePayedForRecord(int id )throws SQLException{
       setConection();
       String sql = "delete from `pay-for` where id = ?";
       PreparedStatement ps = connection.prepareStatement(sql);
       ps.setInt(1, id);
       return ps.executeUpdate();
   }
   public static int  editPayedForRecord(Payed p) throws SQLException{
       setConection();
       String sql = "UPDATE `pay-for` SET `paymentDetails`= ? ,`paymentDate` = ? ,`paymentValue`= ? ,`sonAccountId`= ? WHERE id = ? ";
       PreparedStatement ps = connection.prepareStatement(sql);
       ps.setString(1,p.getPayedDetails());
       ps.setDate(2, p.getPayedDate());
       ps.setFloat(3, p.getPayedValue());
       ps.setInt(4, p.getSonAccountId());
       ps.setInt(5, p.getId());
       return  ps.executeUpdate();
   }
   
   public static int gettingSonAccountId (String sonAccountName){
       int id = 0 ;  
       try {
             setConection();
             String sql = "select id  from `son` where  `sonAccount` = ? ";
             PreparedStatement ps = connection.prepareStatement(sql);
             ps.setString(1, sonAccountName);
             ResultSet rs = ps.executeQuery();
             while (rs.next()) {               
               id = rs.getInt(1);
           }
         } catch (SQLException ex) {
             alertMessage(ex.getMessage());
         }
       return id;
   }
   public static ResultSet getttingAllSounAccount() throws SQLException{

             setConection();
             String sql = "SELECT `sonAccount` FROM `son`";
             PreparedStatement ps = connection.prepareStatement(sql);
             return ps.executeQuery();
   }         
   public static ResultSet selectAllPayedFor() throws SQLException{
       setConection();
       String sql = "SELECT `pay-for`.`id`, paymentDetails ,paymentDate, paymentValue , `son`.`sonAccount` FROM`son` "
               + "INNER join `pay-for` on `pay-for`.`sonAccountId` = `son`.`id` ";
       PreparedStatement ps = connection.prepareStatement(sql);
       return  ps.executeQuery();
   }
   
   // end pay for 

   //start latest 
    
    public static int  gettingNumberOfUnSentBill(){
        int num = 0 ; 
        try {
             setConection();
             String sql = "SELECT count(`bill-philo-mothercompany`.`sent`) from `bill-philo-mothercompany` WHERE `bill-philo-mothercompany`.`sent` = 0 ";
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             while (rs.next()) {                
                num = rs.getInt(1);
            }
         } catch (SQLException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
        return num  ;    
    }
    
    public static int  gettingNumberOfUnReceiveBill(){
        int num = 0 ; 
        try {
             setConection();
             String sql = "SELECT count(`bill-philo-mothercompany`.`receive`) from `bill-philo-mothercompany` WHERE `bill-philo-mothercompany`.`receive` = 0 ";
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             while (rs.next()) {                
                num = rs.getInt(1);
            }
         } catch (SQLException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
        return num  ;    
    }
        //الفواتير اللي احنا مقفلنهاش للمصاعد بس بالنسبة للشركة الام 
    public static int  gettingNumberOfUnSetMoneyToMotherCompany(){
        int num = 0 ; 
        try {
             setConection();
             String sql = "SELECT COUNT(*) from `bill-philo-mothercompany` RIGHT JOIN `dataofcreatedlifts` on `dataofcreatedlifts`.`id` = `bill-philo-mothercompany`.`lift-id` WHERE `bill-philo-mothercompany`.`id`IS NULL ";
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             while (rs.next()) {                
                num = rs.getInt(1);
            }
         } catch (SQLException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
        return num  ;    
    }
    
        //الفواتير اللي احنا مقفلنهاش للمصاعد بس بالنسبة للفني 
    public static int  gettingNumberOfUnSetMoneyTechncain(){
        int num = 0 ; 
        try {
             setConection();
             String sql = "SELECT COUNT(*) from `bil-philo-technician` RIGHT JOIN `dataofcreatedlifts` on `dataofcreatedlifts`.`id` = `bil-philo-technician`.`lift-id` WHERE `bil-philo-technician`.`id` IS NULL ";
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             while (rs.next()) {                
                num = rs.getInt(1);
            }
         } catch (SQLException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
        return num  ;    
    }
    
     // end latest 

}