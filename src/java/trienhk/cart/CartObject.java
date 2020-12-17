package trienhk.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import trienhk.tblproduct.TblProductDTO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Treater
 */
public class CartObject implements Serializable {

    private Map<String, TblProductDTO> items;

    public Map<String, TblProductDTO> getItems() {
        return items;
    }

    public void add(TblProductDTO dto) {
        if (this.getItems() == null) {
            this.items = new HashMap<>();
        }

        String id = Integer.toString(dto.getId());
        if (this.items.containsKey(id)) {
            int quantity = this.items.get(id).getQuantity() + 1;
            dto.setQuantity(quantity);
        }

        this.items.put(id, dto);
    }

    public void delete(String id) {
        if (this.getItems() == null) {
            return;
        }

        if (this.getItems().get(id) != null) {
            this.items.remove(id);
            if (this.items.isEmpty()) {
                this.items = null;
            }
        }

    }

    public int getQuantityOfProduct(String id) {
        if (this.getItems() == null) {
            return -1;
        }

        if (this.getItems().get(id) != null) {
            return this.getItems().get(id).getQuantity();
        }

        return -1;
    }

    public void modifyQuantityOfProduct(String id, boolean isIncrease) {
        if (this.getItems() == null) {
            return;
        }
        TblProductDTO dto = this.getItems().get(id);
        int quantity = dto.getQuantity();
        if (isIncrease) {
            dto.setQuantity(quantity + 1);
        } else {
            if (quantity == 1) {
                delete(id);
            } else {
                dto.setQuantity(quantity - 1);
            }
        }
    }

    public int countTotalProducts() {
        int sum = 0;

        if (this.getItems() != null) {
            for (String item : this.items.keySet()) {
                sum += this.items.get(item).getQuantity();
            }
        }

        return sum;
    }

    public double totalPrice() {
        double sum = 0;

        for (String item : items.keySet()) {
            sum += items.get(item).getPrice() * items.get(item).getQuantity();
        }

        return sum;
    }
}
