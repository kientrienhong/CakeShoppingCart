/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblupdaterecord;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.NamingException;
import trienhk.utils.DBHelpers;

/**
 *
 * @author Treater
 */
public class TblUpdateRecordDAO implements Serializable{
    public boolean insert(TblUpdateRecordDTO dto) throws SQLException, NamingException{
        Connection conn = null;
        PreparedStatement preStm = null; 
        boolean result = false; 
        try {
            conn = DBHelpers.getConnection(); 
            if (conn != null){
                String sql = "INSERT tblUpdateRecord(id, registrationId, productId, date) "
                        + "values(?, ?, ?, ?)";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, dto.getId());
                preStm.setString(2, dto.getRegistrationId());
                preStm.setString(3, dto.getProductId());
                preStm.setTimestamp(4, dto.getDate());
                
                int row = preStm.executeUpdate(); 
                
                result = row > 0;
            }
        } finally {
            if (preStm != null){
                preStm.close();
            }
            
            if (conn != null){
                conn.close();
            }
        }
        
        return result;
    }
}
