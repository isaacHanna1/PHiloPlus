
package philoplus.philoPlusClasses;


public class Areas {
    private int id ;
    private String  country ; 
    private String province ; 
    private String areaName ;
    private String notes;

    public Areas(int id, String country, String province, String areaName, String notes) {
        
        if(areaName.equals("")){
            throw new IllegalArgumentException("can not company name empty ");
        }else{
        this.id = id;
        this.country = country;
        this.province = province;
        this.areaName = areaName;
        this.notes = notes;
        }
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getAreaName() {
        return areaName;
    }
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

}
