/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblcategory;

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
public class TblCategoryDAO implements Serializable{
    
    public List<TblCategoryDTO> loadListCategory() 
            throws SQLException, NamingException{
        PreparedStatement preStm = null; 
        Connection conn = null; 
        ResultSet rs = null;
        List<TblCategoryDTO> list = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null){
                String sql = "SELECT id, name "
                        + "From tblCategory "; 
                preStm = conn.prepareStatement(sql); 
                
                rs = preStm.executeQuery(); 
                list = new ArrayList<>();
                while(rs.next()){
                    String id = rs.getString("id"); 
                    String name = rs.getString("name"); 
                    TblCategoryDTO dto = new TblCategoryDTO(id, name);
                    
                    list.add(dto);
                }
            }
        } finally {
            if (rs != null){
                rs.close();
            }
            
            if (preStm != null){
                preStm.close();
            }
            
            if (conn != null){
                conn.close();
            }
        }
        return list;
    }

}
