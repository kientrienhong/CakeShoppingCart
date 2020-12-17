/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblorder;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;
import trienhk.utils.DBHelpers;

/**
 *
 * @author Treater
 */
public class TblOrderDAO implements Serializable {

    public boolean insert(TblOrderDTO dto, Connection conn) throws SQLException {
        PreparedStatement preStm = null;
        boolean result = false;
        try {
            if (conn != null) {
                String sql = "INSERT "
                        + "tblOrder(orderId, orderDate, shippingAddress, totalPrice, registrationId, phone, paymentId) "
                        + "values(?, ?, ?, ?, ?, ?, ?)";
                preStm = conn.prepareStatement(sql);
                conn.setAutoCommit(false);

                preStm.setString(1, dto.getOrderId());
                preStm.setTimestamp(2, dto.getOrderDate());
                preStm.setString(3, dto.getShippingAddress());
                preStm.setDouble(4, dto.getTotalPrice());
                preStm.setString(5, dto.getUserId());
                preStm.setString(6, dto.getPhone());
                preStm.setString(7, dto.getPaymentId());
                int row = preStm.executeUpdate();

                result = row > 0;
            }

        } finally {
            if (preStm != null) {
                preStm.close();
            }
        }

        return result;
    }

    public TblOrderDTO findById(String id, String userId) 
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        TblOrderDTO result = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT orderDate, shippingAddress, totalPrice, phone, paymentId "
                        + "From tblOrder "
                        + "Where orderId = ? AND registrationId = ?";
                preStm = conn.prepareStatement(sql);
                conn.setAutoCommit(false);

                preStm.setString(1, id);
                preStm.setString(2, userId);
                rs = preStm.executeQuery();
                if (rs.next()){
                    String shippingAddress = rs.getString("shippingAddress");
                    double totalPrice = rs.getDouble("totalPrice");
                    String phone = rs.getString("phone");
                    Timestamp ts = rs.getTimestamp("orderDate"); 
                    String payment = rs.getString("paymentId");
                    result = new TblOrderDTO(id, ts, shippingAddress, totalPrice, userId, phone, payment);
                }
            }

        } finally {
            if (rs != null){
                rs.close();
            }
            
            if (preStm != null) {
                preStm.close();
            }
            
            if (conn != null){
                conn.close();
            }
        }

        return result;
    }
}
