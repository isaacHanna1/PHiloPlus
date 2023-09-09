
package philoplus.philoPlusClasses;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class Lift {
    private int id ;
    private String po ;
    private String liftNum;
    private int lift_type_id;
    private String lift_type_details;
    private int lift_floor_number ;
    private int lift_well_num;
    private int techniciansNumber;//number of techncian work in lift
    private Date startWork;
    private int num_work_day;
    private Date finsihDate;
    private int siteId;
    private String siteName;
    private int companyID;
    private String CampanyName;

    public Lift(int id, String po, String liftNum, int lift_type_id, int lift_floor_number, int lift_well_num, Date startWork, int num_work_day, int siteId, int companyID) {
        this.id = id;
        this.po = po;
        this.liftNum = liftNum;
        this.lift_type_id = lift_type_id;
        this.lift_floor_number = lift_floor_number;
        this.lift_well_num = lift_well_num;
        this.startWork = startWork;
        this.num_work_day = num_work_day;
        this.siteId = siteId;
        this.companyID = companyID;
    }

    public Lift(int id,String liftNumber,String po, String lift_type_details, int lift_floor_number, int lift_well_num, int techniciansNumber, Date startWork, int num_work_day, String siteName, String CampanyName) {
        this.id = id;
        this.liftNum = liftNumber;
        this.po = po;
        this.lift_type_details = lift_type_details;
        this.lift_floor_number = lift_floor_number;
        this.lift_well_num = lift_well_num;
        this.techniciansNumber = techniciansNumber;
        this.startWork = startWork;
        this.num_work_day = num_work_day;
        this.finsihDate = getFinsihDate();
        this.siteName = siteName;
        this.CampanyName = CampanyName;
    }

  

  

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public String getLiftNum() {
        return liftNum;
    }

    public void setLiftNum(String liftNum) {
        this.liftNum = liftNum;
    }

    public int getLift_type_id() {
        return lift_type_id;
    }

    public void setLift_type_id(int lift_type_id) {
        this.lift_type_id = lift_type_id;
    }

    public String getLift_type_details() {
        return lift_type_details;
    }

    public void setLift_type_details(String lift_type_details) {
        this.lift_type_details = lift_type_details;
    }

    public int getLift_floor_number() {
        return lift_floor_number;
    }

    public void setLift_floor_number(int lift_floor_number) {
        this.lift_floor_number = lift_floor_number;
    }

    public int getLift_well_num() {
        return lift_well_num;
    }

    public void setLift_well_num(int lift_well_num) {
        this.lift_well_num = lift_well_num;
    }

    public int getTechniciansNumber() {
        return techniciansNumber;
    }

    public void setTechniciansNumber(int techniciansNumber) {
        this.techniciansNumber = techniciansNumber;
    }

 
    public Date getStartWork() {
        return startWork;
    }

    public void setStartWork(Date startWork) {
        this.startWork = startWork;
    }

    public int getNum_work_day() {
        return num_work_day;
    }

    public Date getFinsihDate() {
        // Create a date string in the format of "yyyy-MM-dd"
        String dateString = getStartWork().toString();

        // Parse the date string to a LocalDate object using a formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);

        // Add 5 days to the date
        LocalDate futureDate = date.plusDays(getNum_work_day());

        return Date.valueOf(futureDate);
    }

    public void setFinsihDate(Date finsihDate) {
        
        this.finsihDate = finsihDate;
    }

    public void setNum_work_day(int num_work_day) {
        this.num_work_day = num_work_day;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public String getCampanyName() {
        return CampanyName;
    }

    public void setCampanyName(String CampanyName) {
        this.CampanyName = CampanyName;
    }


    
    
}
