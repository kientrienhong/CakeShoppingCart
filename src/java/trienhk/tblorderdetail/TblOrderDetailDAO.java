/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblorderdetail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import trienhk.utils.DBHelpers;

/**
 *
 * @author Treater
 */
public class TblOrderDetailDAO implements Serializable {

    public boolean insert(List<TblOrderDetailDTO> list, Connection conn)
            throws SQLException {
        PreparedStatement preStm = null;
        boolean result = true;
        try {
            if (conn != null) {
                conn.setAutoCommit(false);
                String sql = "Insert tblOrderDetail(orderId, productId, quantity, price) "
                        + "values(?, ?, ?, ?)";
                preStm = conn.prepareStatement(sql);
                for (TblOrderDetailDTO dto : list) {
                    preStm.setString(1, dto.getOrderId());
                    preStm.setInt(2, dto.getProductId());
                    preStm.setInt(3, dto.getQuantity());
                    preStm.setDouble(4, dto.getPrice());
                    preStm.addBatch();
                }

                int[] check = preStm.executeBatch();
                for (int i = 0; i < check.length; i++) {
                    if (check[i] == 0) {
                        result = false;
                        break;
                    }
                }
            }

        } finally {
            if (preStm != null) {
                preStm.close();
            }
        }

        return result;
    }

    public List<TblOrderDetailDTO> findByOrderId(String orderId) 
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<TblOrderDetailDTO> list = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT productId, quantity, price "
                        + "From tblOrderDetail "
                        + "Where orderId = ? ";
                preStm = conn.prepareStatement(sql);

                preStm.setString(1, orderId);
                
                rs = preStm.executeQuery(); 
                
                list = new ArrayList<>();
                while (rs.next()) {
                    int productId = rs.getInt("productId"); 
                    int quantity = rs.getInt("quantity"); 
                    double price = rs.getDouble("price"); 
                    TblOrderDetailDTO dto = new TblOrderDetailDTO(orderId, productId, quantity, price);
                    list.add(dto);
                }
            }

        } finally {
            if (rs != null) {
                rs.close();
            }

            if (preStm != null) {
                preStm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return list;
    }
}
