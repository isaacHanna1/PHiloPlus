
package philoplus.philoPlusClasses;

public class Companies {
    private int id ;
    private String company_name ;
    private String country ;
    private String province ;
    private String police_station ;
    private String street_num ;
    private String building_num;
    private String apartment_num;
    private String tax_num;
    private String notes;

    public Companies(int id, String company_name, String country, String province, String police_station, String street_num, String building_num, String apartment_num, String tax_num, String notes) {
    
        if(company_name.equals("")){
            throw new IllegalArgumentException("can ntot company name empty ");
        }
        else{
        this.id = id;
        this.company_name = company_name;
        this.country = country;
        this.province = province;
        this.police_station = police_station;
        this.street_num = street_num;
        this.building_num = building_num;
        this.apartment_num = apartment_num;
        this.tax_num = tax_num;
        this.notes = notes;
        }
    }


    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
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

    public String getPolice_station() {
        return police_station;
    }

    public void setPolice_station(String police_station) {
        this.police_station = police_station;
    }

    public String getStreet_num() {
        return street_num;
    }

    public void setStreet_num(String street_num) {
        this.street_num = street_num;
    }

    public String getBuilding_num() {
        return building_num;
    }

    public void setBuilding_num(String building_num) {
        this.building_num = building_num;
    }

    public String getApartment_num() {
        return apartment_num;
    }

    public void setApartment_num(String apartment_num) {
        this.apartment_num = apartment_num;
    }

    public String getTax_num() {
        return tax_num;
    }

    public void setTax_num(String tax_num) {
        this.tax_num = tax_num;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    
    
         
}
