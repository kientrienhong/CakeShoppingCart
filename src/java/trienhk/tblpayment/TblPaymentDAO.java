/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblpayment;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import trienhk.utils.DBHelpers;

/**
 *
 * @author Treater
 */
public class TblPaymentDAO implements Serializable {

    public boolean insert(TblPaymentDTO dto, Connection conn)
            throws SQLException, NamingException {
        PreparedStatement preStm = null;
        boolean check = false;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "Insert tblPayment(id, orderId, status, type) "
                        + "values(?, ?, ?, ?)";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, dto.getId());
                preStm.setString(2, dto.getOrderId());
                preStm.setString(3, dto.getStatus());
                preStm.setString(4, dto.getType());
                int row = preStm.executeUpdate();
                check = row > 0;
            }

        } finally {
            if (preStm != null) {
                preStm.close();
            }
        }
        return check;
    }

    public TblPaymentDTO findById(String id)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        TblPaymentDTO dto = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT status, type "
                        + "From tblPayment "
                        + "Where id = ?";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, id);

                rs = preStm.executeQuery();

                if (rs.next()) {
                    String status = rs.getString("status");
                    String type = rs.getString("type");
                    dto = new TblPaymentDTO(id, status, type);
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

        return dto;
    }
}
