
package philoplus.philoPlusClasses;



public class Sites {
    
    private int id ; 
    private String siteName ; 
    private int areaId;
    private String areaName ;
    private String  PO;
    private String siteEngineer;
    private String SiteEngineerTelephone;
    private String siteAdmin;
    private String siteAdminTelephone;
    private String ourSiteEngineer;
    private String ourSiteEngineerTelephone;
    private String ourSiteAdmin;
    private String ourSiteAdminTelephone;
    private String note;

    public Sites(int id, String siteName, int areaId, String PO, String siteEngineer, String SiteEngineerTelephone, String siteAdmin, String siteAdminTelephone, String ourSiteEngineer, String ourSiteEngineerTelephone, String ourSiteAdmin, String ourSiteAdminTelephone , String note ) {
        this.id = id;
        this.siteName = siteName;
        this.areaId = areaId;
        this.PO = PO;
        this.siteEngineer = siteEngineer;
        this.SiteEngineerTelephone = SiteEngineerTelephone;
        this.siteAdmin = siteAdmin;
        this.siteAdminTelephone = siteAdminTelephone;
        this.ourSiteEngineer = ourSiteEngineer;
        this.ourSiteEngineerTelephone = ourSiteEngineerTelephone;
        this.ourSiteAdmin = ourSiteAdmin;
        this.ourSiteAdminTelephone = ourSiteAdminTelephone;
        this.note = note;
    }

    public Sites(int id, String siteName, String areaName, String PO, String siteEngineer, String SiteEngineerTelephone, String siteAdmin, String siteAdminTelephone, String ourSiteEngineer, String ourSiteEngineerTelephone, String ourSiteAdmin, String ourSiteAdminTelephone , String note) {
        this.id = id;
        this.siteName = siteName;
        this.areaName = areaName;
        this.PO = PO;
        this.siteEngineer = siteEngineer;
        this.SiteEngineerTelephone = SiteEngineerTelephone;
        this.siteAdmin = siteAdmin;
        this.siteAdminTelephone = siteAdminTelephone;
        this.ourSiteEngineer = ourSiteEngineer;
        this.ourSiteEngineerTelephone = ourSiteEngineerTelephone;
        this.ourSiteAdmin = ourSiteAdmin;
        this.ourSiteAdminTelephone = ourSiteAdminTelephone;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPO() {
        return PO;
    }

    public void setPO(String PO) {
        this.PO = PO;
    }

    public String getSiteEngineer() {
        return siteEngineer;
    }

    public void setSiteEngineer(String siteEngineer) {
        this.siteEngineer = siteEngineer;
    }

    public String getSiteEngineerTelephone() {
        return SiteEngineerTelephone;
    }

    public void setSiteEngineerTelephone(String SiteEngineerTelephone) {
        this.SiteEngineerTelephone = SiteEngineerTelephone;
    }

    public String getSiteAdmin() {
        return siteAdmin;
    }

    public void setSiteAdmin(String siteAdmin) {
        this.siteAdmin = siteAdmin;
    }

    public String getSiteAdminTelephone() {
        return siteAdminTelephone;
    }

    public void setSiteAdminTelephone(String siteAdminTelephone) {
        this.siteAdminTelephone = siteAdminTelephone;
    }

    public String getOurSiteEngineer() {
        return ourSiteEngineer;
    }

    public void setOurSiteEngineer(String ourSiteEngineer) {
        this.ourSiteEngineer = ourSiteEngineer;
    }

    public String getOurSiteEngineerTelephone() {
        return ourSiteEngineerTelephone;
    }

    public void setOurSiteEngineerTelephone(String ourSiteEngineerTelephone) {
        this.ourSiteEngineerTelephone = ourSiteEngineerTelephone;
    }

    public String getOurSiteAdmin() {
        return ourSiteAdmin;
    }

    public void setOurSiteAdmin(String ourSiteAdmin) {
        this.ourSiteAdmin = ourSiteAdmin;
    }

    public String getOurSiteAdminTelephone() {
        return ourSiteAdminTelephone;
    }

    public void setOurSiteAdminTelephone(String ourSiteAdminTelephone) {
        this.ourSiteAdminTelephone = ourSiteAdminTelephone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    
    
    
    
}
