package philoplus.philoPlusClasses;

import java.sql.Date;

public class Following {
    
     private int id ; 
     private int lift_Id;
     private String following_Details;
     private Date following_Date;
     private int Progress_Id ;
     private String progressDetails;
     private float progress_radtio ; 
     private String progress_ratio_details;
     private int userId ;
     private String userloginName;
     private String imgPath;
    public Following(int id, int lift_Id, String following_Details, Date following_Date, int Progress_Id , int userId) {
        this.id = id;
        this.lift_Id = lift_Id;
        this.following_Details = following_Details;
        this.following_Date = following_Date;
        this.Progress_Id = Progress_Id;
        this.userId = userId;
    }
    public Following (int id , String followingDetails , String progrssDetails , float progressRatio , Date followingDate , String userloginName,String imgPath){
        this.id = id ;
        this.following_Details = followingDetails;
        this.progressDetails    =  progrssDetails ;
        this.progress_radtio    =  progressRatio ;
        this.following_Date      =  followingDate;
        this.userloginName        =  userloginName;
        this.imgPath  = imgPath;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getLift_Id() {
        return lift_Id;
    }
    public void setLift_Id(int lift_Id) {
        this.lift_Id = lift_Id;
    }
    public String getFollowing_Details() {
        return following_Details;
    }
    public void setFollowing_Details(String following_Details) {
        this.following_Details = following_Details;
    }
    public Date getFollowing_Date() {
        return following_Date;
    }
    public void setFollowing_Date(Date following_Date) {
        this.following_Date = following_Date;
    }
    public int getProgress_Id() {
        return Progress_Id;
    }
    public void setProgress_Id(int Progress_Id) {
        this.Progress_Id = Progress_Id;
    }

    public String getProgressDetails() {
        return progressDetails;
    }

    public void setProgressDetails(String progressDetails) {
        this.progressDetails = progressDetails;
    }

    public String getProgress_ratio_details() {
        return progress_ratio_details;
    }

    public void setProgress_ratio_details(String progress_ratio_details) {
        this.progress_ratio_details = progress_ratio_details;
    }
    
    public float getProgress_radtio() {
        return progress_radtio;
    }
    public void setProgress_radtio(float progress_radtio) {
        this.progress_radtio = progress_radtio;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserloginName() {
        return userloginName;
    }

    public void setUserloginName(String userloginName) {
        this.userloginName = userloginName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
