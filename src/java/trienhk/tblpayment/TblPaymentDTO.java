/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblpayment;

import java.io.Serializable;

/**
 *
 * @author Treater
 */
public class TblPaymentDTO implements Serializable{
    private String id; 
    private String orderId; 
    private String status; 
    private String type;
    public TblPaymentDTO(String id, String orderId, String status, String type) {
        this.id = id;
        this.orderId = orderId;
        this.status = status;
        this.type = type;
    }

    public TblPaymentDTO(String id, String status, String type) {
        this.id = id;
        this.status = status;
        this.type = type;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
