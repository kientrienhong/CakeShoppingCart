/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblorder;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Treater
 */
public class TblOrderDTO implements Serializable{
    private String orderId; 
    private Timestamp orderDate; 
    private String shippingAddress; 
    private double totalPrice; 
    private String userId; 
    private String phone; 
    private String paymentId;
    
    public TblOrderDTO(String orderId, Timestamp orderDate, String shippingAddress, double totalPrice, String userId, String phone, String paymentId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.phone = phone;
        this.paymentId = paymentId;
    }

    public TblOrderDTO() {
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
    
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
}
