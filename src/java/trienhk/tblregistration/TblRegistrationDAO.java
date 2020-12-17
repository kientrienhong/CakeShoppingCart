/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblregistration;

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
public class TblRegistrationDAO implements Serializable {

    public TblRegistrationDTO checkLogin(String email, String password)
            throws NamingException, SQLException {
        Connection connection = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        TblRegistrationDTO dto = null;
        try {
            connection = DBHelpers.getConnection();

            if (connection != null) {
                String sql = "SELECT phone, roleId, name, address "
                        + "FROM tblRegistration "
                        + "WHERE id = ? AND password = ? AND status = 'Active' AND (roleId  = 'AD' OR roleId = 'MEM')";
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, email);
                preStm.setString(2, password);
                rs = preStm.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("name");
                    String roleId = rs.getString("roleId");
                    String address = rs.getString("address");
                    String phone = rs.getString("phone");
                    dto = new TblRegistrationDTO(email, "***", name, roleId, phone, address);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (preStm != null) {
                preStm.close();
            }

            if (connection != null) {
                connection.close();
            }
        }

        return dto;
    }

    public boolean insertGoogleAccount(TblRegistrationDTO dto)
            throws NamingException, SQLException {
        Connection connection = null;
        PreparedStatement preStm = null;
        boolean check = false;
        try {
            connection = DBHelpers.getConnection();

            if (connection != null) {
                String sql = "INSERT tblRegistration(id, password, name, phone, address, roleId, status) "
                        + "values(?, ?, ?, ?, ?, ?, ?)";
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, dto.getId());
                preStm.setString(2, dto.getPassword());
                preStm.setString(3, dto.getName());
                preStm.setString(4, dto.getPhone());
                preStm.setString(5, dto.getAddress());
                preStm.setString(6, dto.getRoleId());
                preStm.setString(7, "Active");

                int row = preStm.executeUpdate();

                check = row > 0;
            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }

            if (connection != null) {
                connection.close();
            }
        }

        return check;
    }

    public boolean isGoogleAccoutnExisted(String email)
            throws NamingException, SQLException {
        Connection connection = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            connection = DBHelpers.getConnection();

            if (connection != null) {
                String sql = "SELECT name "
                        + "FROM tblRegistration "
                        + "WHERE id = ? ";
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, email);
                rs = preStm.executeQuery();

                if (rs.next()) {
                    result = true;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (preStm != null) {
                preStm.close();
            }

            if (connection != null) {
                connection.close();
            }
        }

        return result;
    }
}
