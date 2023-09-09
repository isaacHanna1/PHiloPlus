package philoplus.philoPlusClasses;

import java.sql.Date;

public class Technician{
    
    private int id ;
    private String isTechnician;
    private String name ;
    private String nationalId;
    private String telephones;
    private Date startWork ;
    private int experienceYear ;
    private String notes ;
    private int liftId ; 

    public Technician(int id, String isTechnician, String name, String nationalId, String telephones, Date startWork, int experienceYear, String notes) {

        if(name.equals(null)|| name.isEmpty()){
            System.out.println("name exception thrown ");
          throw new IllegalArgumentException("لا يمكن ان يكون اسم الفني فارغ");
        }else if(nationalId.equals(null)|| nationalId.isEmpty()){
            System.out.println("id exception thrown ");
        throw new IllegalArgumentException("لا يمكن ان يكون الرقم القومي فارغ ");
    }
        else{
        this.id = id;
        this.isTechnician = isTechnician;
        this.name = name;
        this.nationalId = nationalId;
        this.telephones = telephones;
        this.startWork = startWork;
        this.experienceYear = experienceYear;
        this.notes = notes;
        }
    }

    public Technician(int id, int liftId) {
        this.id = id;
        this.liftId = liftId;
    }
    public  Technician(int id ,String techncianName){
        this.id = id ;
        this.name = techncianName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsTechnician() {
        return isTechnician;
    }

    public void setIsTechnician(String isTechnician) {
        this.isTechnician = isTechnician;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getTelephones() {
        return telephones;
    }

    public void setTelephones(String telephones) {
        this.telephones = telephones;
    }

    public Date getStartWork() {
        return startWork;
    }

    public void setStartWork(Date startWork) {
        this.startWork = startWork;
    }

    public int getExperienceYear() {
        return experienceYear;
    }

    public void setExperienceYear(int experienceYear) {
        this.experienceYear = experienceYear;
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public int getLiftId() {
        return liftId;
    }
    public void setLiftId(int liftId) {
        this.liftId = liftId;
    }   
   
}
