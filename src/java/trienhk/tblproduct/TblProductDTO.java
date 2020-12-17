/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblproduct;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Treater
 */
public class TblProductDTO implements Serializable {

    private int id;
    private String description;
    private String name;
    private double price;
    private String image;
    private String categoryId;
    private String categoryName;
    private Date dateCreated;
    private Date dateExpired;
    private int quantity;
    private String status;

    public TblProductDTO(String description, String name, double price, String image, String categoryId, Date dateCreated, Date dateExpired, int quantity) {
        this.description = description;
        this.name = name;
        this.price = price;
        this.image = image;
        this.categoryId = categoryId;
        this.dateCreated = dateCreated;
        this.dateExpired = dateExpired;
        this.quantity = quantity;
    }

    public TblProductDTO(int id, String description, String name, double price, String image, String categoryId, String categoryName, Date dateCreated, Date dateExpired, int quantity, String status) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.price = price;
        this.image = image;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.dateCreated = dateCreated;
        this.dateExpired = dateExpired;
        this.quantity = quantity;
        this.status = status;
    }

    public TblProductDTO(int id, String description, String name, double price, String image, String categoryId, Date dateExpired, int quantity, String status) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.price = price;
        this.image = image;
        this.categoryId = categoryId;
        this.dateExpired = dateExpired;
        this.quantity = quantity;
        this.status = status;
    }

    public TblProductDTO(int id, String name, double price, Date dateExpired, int quantity, String status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.dateExpired = dateExpired;
        this.quantity = quantity;
        this.status = status;
    }

    public TblProductDTO(int id, String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
        this.id = id;
    }
    
    
    public TblProductDTO() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateExpired() {
        return dateExpired;
    }

    public void setDateExpired(Date dateExpired) {
        this.dateExpired = dateExpired;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
