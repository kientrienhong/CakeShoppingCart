/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblproduct;

import java.io.Serializable;

/**
 *
 * @author Treater
 */
public class TblProductError implements Serializable {
    private String priceFromError;
    private String priceToError;
    private String priceError;
    private String nameError;
    private String descriptionError;
    private String expiredDateError;
    private String quantityError;
    private String imageError;

    public TblProductError() {
    }

    public String getPriceFromError() {
        return priceFromError;
    }

    public void setPriceFromError(String priceFromError) {
        this.priceFromError = priceFromError;
    }

    public String getPriceToError() {
        return priceToError;
    }

    public void setPriceToError(String priceToError) {
        this.priceToError = priceToError;
    }

    public String getPriceError() {
        return priceError;
    }

    public void setPriceError(String priceError) {
        this.priceError = priceError;
    }

    public String getNameError() {
        return nameError;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
    }

    public String getDescriptionError() {
        return descriptionError;
    }

    public void setDescriptionError(String descriptionError) {
        this.descriptionError = descriptionError;
    }

    public String getExpiredDateError() {
        return expiredDateError;
    }

    public void setExpiredDateError(String expiredDateError) {
        this.expiredDateError = expiredDateError;
    }

    public String getQuantityError() {
        return quantityError;
    }

    public void setQuantityError(String quantityError) {
        this.quantityError = quantityError;
    }

    public String getImageError() {
        return imageError;
    }

    public void setImageError(String imageError) {
        this.imageError = imageError;
    }
    
    
}
