/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.checkout;

import java.io.Serializable;

/**
 *
 * @author Treater
 */
public class CheckOutError implements Serializable{
    private String nameError; 
    private String addressError; 
    private String phoneError; 
    private String quantityError;
    public CheckOutError(String nameError, String addressError, String phoneError) {
        this.nameError = nameError;
        this.addressError = addressError;
        this.phoneError = phoneError;
    }

    public CheckOutError() {
    }

    public String getQuantityError() {
        return quantityError;
    }

    public void setQuantityError(String quantityError) {
        this.quantityError = quantityError;
    }

    public String getNameError() {
        return nameError;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
    }

    public String getAddressError() {
        return addressError;
    }

    public void setAddressError(String addressError) {
        this.addressError = addressError;
    }

    public String getPhoneError() {
        return phoneError;
    }

    public void setPhoneError(String phoneError) {
        this.phoneError = phoneError;
    }
    
    
}
