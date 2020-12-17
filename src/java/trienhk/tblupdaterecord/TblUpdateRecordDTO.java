/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblupdaterecord;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Treater
 */
public class TblUpdateRecordDTO implements Serializable{
    private String id; 
    private String registrationId;
    private String productId; 
    private Timestamp date;

    public TblUpdateRecordDTO(String id, String registrationId, String productId, Timestamp date) {
        this.id = id;
        this.registrationId = registrationId;
        this.productId = productId;
        this.date = date;
    }

    public TblUpdateRecordDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
    
    
}
