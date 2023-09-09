/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package philoplus.philoPlusClasses;

import java.sql.Date;


public class Deposite {

    private int depositeId ; 
    private int liftId ; 
    private int techncian_id;
    private float transactionValue; 
    private Date transactionDate ;
    private String details ; 
    private float reminder ;
    private String imgSuportPath;
    private byte []imageData ; 
    
    
    private String technicalName;

    public Deposite(int depositeId, int liftId, int techncian_id, float transactionValue,Date transactionDate, String details, String imgSuportPath, byte[] imageData) {
        this.depositeId = depositeId;
        this.liftId = liftId;
        this.techncian_id = techncian_id;
        this.transactionValue = transactionValue;
        this.transactionDate = transactionDate;
        this.details = details;
        this.imgSuportPath = imgSuportPath;
        this.imageData = imageData;
    }

    public Deposite(int depositeId, int liftId, int techncian_id, float transactionValue,float reminder , Date transactionDate, String details, String imgSuportPath, String technicalName) {
        this.depositeId = depositeId;
        this.liftId = liftId;
        this.techncian_id = techncian_id;
        this.transactionValue = transactionValue;
        this.reminder = reminder;
        this.transactionDate = transactionDate;
        this.details = details;
        this.imgSuportPath = imgSuportPath;
        this.technicalName = technicalName;
    }

    public int getDepositeId() {
        return depositeId;
    }

    public void setDepositeId(int depositeId) {
        this.depositeId = depositeId;
    }

    public int getLiftId() {
        return liftId;
    }

    public void setLiftId(int liftId) {
        this.liftId = liftId;
    }

    public int getTechncian_id() {
        return techncian_id;
    }

    public void setTechncian_id(int techncian_id) {
        this.techncian_id = techncian_id;
    }

    public float getTransactionValue() {
        return transactionValue;
    }

    public void setTransactionValue(float transactionValue) {
        this.transactionValue = transactionValue;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public float getReminder() {
        return reminder;
    }

    public void setReminder(float reminder) {
        this.reminder = reminder;
    }

    public String getImgSuportPath() {
        return imgSuportPath;
    }

    public void setImgSuportPath(String imgSuportPath) {
        this.imgSuportPath = imgSuportPath;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public Deposite(String technicalName) {
        this.technicalName = technicalName;
    }

    public String getTechnicalName() {
        return technicalName;
    }

    public void setTechnicalName(String technicalName) {
        this.technicalName = technicalName;
    }
    
    
    
}
