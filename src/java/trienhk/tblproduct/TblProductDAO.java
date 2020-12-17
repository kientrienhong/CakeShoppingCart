/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.tblproduct;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import trienhk.cart.CartObject;
import trienhk.tblorderdetail.TblOrderDetailDTO;
import trienhk.utils.DBHelpers;

/**
 *
 * @author Treater
 */
public class TblProductDAO implements Serializable {

    private List<TblProductDTO> list;

    public List<TblProductDTO> getList() {
        return list;
    }

    public List<TblProductDTO> searchIncludedPrice(String name, double priceFrom, double priceTo, String categoryId, int currentPage, String categoryName)
            throws SQLException, NamingException {
        PreparedStatement preStm = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT temp.name, temp.createDate, temp.image, temp.description, temp.id, temp.price, temp.expirationDate, temp.categoryId, temp.quantity, temp.status "
                        + "FROM ("
                        + " SELECT ROW_NUMBER() OVER (ORDER BY createDate DESC) AS RowNr, name, createDate, description, image, id, price, expirationDate, categoryId, quantity, status"
                        + " From tblProduct"
                        + " Where name LIKE ? AND categoryId = ? AND price Between ? AND ? AND status = 'Active' AND quantity > 0 AND expirationDate > ? "
                        + ")temp "
                        + "Where temp.RowNr Between ? AND ?";

                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%" + name + "%");
                preStm.setString(2, categoryId);
                preStm.setDouble(3, priceFrom);
                preStm.setDouble(4, priceTo);
                preStm.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                preStm.setInt(6, 20 * (currentPage - 1) + 1);
                preStm.setInt(7, 20 * (currentPage - 1) + 1 + 19);

                rs = preStm.executeQuery();

                list = new ArrayList<>();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nameProduct = rs.getString("name");
                    String image = rs.getString("image");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    Date dateCreate = rs.getDate("createDate");
                    Date expirationDate = rs.getDate("expirationDate");
                    String description = rs.getString("description");
                    String status = rs.getString("status");
                    TblProductDTO dto = new TblProductDTO(id, description, nameProduct, price, image, categoryId, categoryName, dateCreate, expirationDate, quantity, status);
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

    public List<TblProductDTO> searchIncludedPriceAdmin(String name, double priceFrom, double priceTo, String categoryId, int currentPage, String categoryName)
            throws SQLException, NamingException {
        PreparedStatement preStm = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT temp.name, temp.createDate, temp.image, temp.description, temp.id, temp.price, temp.expirationDate, temp.categoryId, temp.quantity, temp.status "
                        + "FROM ("
                        + " SELECT ROW_NUMBER() OVER (ORDER BY createDate DESC) AS RowNr, name, createDate, description, image, id, price, expirationDate, categoryId, quantity, status"
                        + " From tblProduct"
                        + " Where name LIKE ? AND categoryId = ? AND price Between ? AND ? AND quantity > 0 AND expirationDate > ? "
                        + ")temp "
                        + "Where temp.RowNr Between ? AND ?";

                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%" + name + "%");
                preStm.setString(2, categoryId);
                preStm.setDouble(3, priceFrom);
                preStm.setDouble(4, priceTo);
                preStm.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                preStm.setInt(6, 20 * (currentPage - 1) + 1);
                preStm.setInt(7, 20 * (currentPage - 1) + 1 + 19);

                rs = preStm.executeQuery();

                list = new ArrayList<>();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nameProduct = rs.getString("name");
                    String image = rs.getString("image");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    Date dateCreate = rs.getDate("createDate");
                    Date expirationDate = rs.getDate("expirationDate");
                    String description = rs.getString("description");
                    String status = rs.getString("status");
                    TblProductDTO dto = new TblProductDTO(id, description, nameProduct, price, image, categoryId, categoryName, dateCreate, expirationDate, quantity, status);
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

    public List<TblProductDTO> searchWithoutPrice(String name, String categoryId, int currentPage, String categoryName)
            throws SQLException, NamingException {
        PreparedStatement preStm = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT temp.name, temp.createDate, temp.image, temp.description, temp.id, temp.price, temp.expirationDate, temp.categoryId, temp.quantity, temp.status, temp.id "
                        + "FROM ("
                        + " SELECT ROW_NUMBER() OVER (ORDER BY createDate DESC) AS RowNr, name, createDate, description, image, id, price, expirationDate, categoryId, quantity, status"
                        + " From tblProduct"
                        + " Where name LIKE ? AND categoryId = ? AND status = 'Active' And quantity > 0 AND expirationDate > ? "
                        + ")temp "
                        + "Where temp.RowNr Between ? AND ?";

                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%" + name + "%");
                preStm.setString(2, categoryId);
                preStm.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                preStm.setInt(4, 20 * (currentPage - 1) + 1);
                preStm.setInt(5, 20 * (currentPage - 1) + 1 + 19);

                rs = preStm.executeQuery();

                list = new ArrayList<>();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nameProduct = rs.getString("name");
                    String image = rs.getString("image");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    Date dateCreate = rs.getDate("createDate");
                    Date expirationDate = rs.getDate("expirationDate");
                    String description = rs.getString("description");
                    String status = rs.getString("status");
                    TblProductDTO dto = new TblProductDTO(id, description, nameProduct, price, image, categoryId, categoryName, dateCreate, expirationDate, quantity, status);
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

    public List<TblProductDTO> searchWithoutPriceAdmin(String name, String categoryId, int currentPage, String categoryName)
            throws SQLException, NamingException {
        PreparedStatement preStm = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT temp.name, temp.createDate, temp.image, temp.description, temp.id, temp.price, temp.expirationDate, temp.categoryId, temp.quantity, temp.status, temp.id "
                        + "FROM ("
                        + " SELECT ROW_NUMBER() OVER (ORDER BY createDate DESC) AS RowNr, name, createDate, description, image, id, price, expirationDate, categoryId, quantity, status"
                        + " From tblProduct"
                        + " Where name LIKE ? AND categoryId = ? And quantity > 0 AND expirationDate > ? "
                        + ")temp "
                        + "Where temp.RowNr Between ? AND ?";

                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%" + name + "%");
                preStm.setString(2, categoryId);
                preStm.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                preStm.setInt(4, 20 * (currentPage - 1) + 1);
                preStm.setInt(5, 20 * (currentPage - 1) + 1 + 19);

                rs = preStm.executeQuery();

                list = new ArrayList<>();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nameProduct = rs.getString("name");
                    String image = rs.getString("image");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    Date dateCreate = rs.getDate("createDate");
                    Date expirationDate = rs.getDate("expirationDate");
                    String description = rs.getString("description");
                    String status = rs.getString("status");
                    TblProductDTO dto = new TblProductDTO(id, description, nameProduct, price, image, categoryId, categoryName, dateCreate, expirationDate, quantity, status);
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

    public List<TblProductDTO> getQuantityOfProductInCart(CartObject cart) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<TblProductDTO> result = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT quantity, name "
                        + "From tblProduct "
                        + "Where id = ?";
                preStm = conn.prepareStatement(sql);
                result = new ArrayList<>();
                for (String id : cart.getItems().keySet()) {
                    preStm.setString(1, id);
                    rs = preStm.executeQuery();
                    if (rs.next()) {
                        String name = rs.getString("name");
                        int quantity = rs.getInt("quantity");
                        int idInterger = Integer.parseInt(id);
                        TblProductDTO dto = new TblProductDTO(idInterger, name, quantity);
                        result.add(dto);
                    }
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

        return result;
    }

    public List<String> getListNameFromProductIdsInListOrder(List<TblOrderDetailDTO> list)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<String> result = null;

        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT name "
                        + "From tblProduct "
                        + "Where id = ?";
                preStm = conn.prepareStatement(sql);
                result = new ArrayList<>();
                for (TblOrderDetailDTO dto : list) {
                    preStm.setInt(1, dto.getProductId());
                    rs = preStm.executeQuery();

                    if (rs.next()) {
                        result.add(rs.getString("name"));
                    }
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

        return result;
    }

    public String getNameById(int id) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        String result = "";

        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "SELECT name "
                        + "From tblProduct "
                        + "Where id = ?";
                preStm = conn.prepareStatement(sql);
                preStm.setInt(1, id);

                rs = preStm.executeQuery();

                if (rs.next()) {
                    result = rs.getString("name");
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

        return result;
    }

    public String getProductImageById(String id)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        String result = "";
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "Select image "
                        + "From tblProduct "
                        + "Where id = ?";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, id);
                rs = preStm.executeQuery();

                if (rs.next()) {
                    result = rs.getString("image");
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

        return result;
    }

    public TblProductDTO findById(int id)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        TblProductDTO result = null;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "Select name, price, quantity, expirationDate, status "
                        + "From tblProduct "
                        + "Where id = ?";
                preStm = conn.prepareStatement(sql);
                preStm.setInt(1, id);
                rs = preStm.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("name");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    Date expirationDate = rs.getDate("expirationDate");
                    String status = rs.getString("status");

                    result = new TblProductDTO(id, name, price, expirationDate, quantity, status);
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

        return result;
    }

    public boolean insert(TblProductDTO dto)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        boolean check = false;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "INSERT "
                        + "tblProduct(image, description, price, createDate, expirationDate, categoryId, quantity, name, status) "
                        + "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, dto.getImage());
                preStm.setString(2, dto.getDescription());
                preStm.setDouble(3, dto.getPrice());
                preStm.setDate(4, dto.getDateCreated());
                preStm.setDate(5, dto.getDateExpired());
                preStm.setString(6, dto.getCategoryId());
                preStm.setInt(7, dto.getQuantity());
                preStm.setString(8, dto.getName());
                preStm.setString(9, "Active");
                int row = preStm.executeUpdate();
                check = row > 0;
            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public boolean update(TblProductDTO dto)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        boolean check = false;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "Update tblProduct "
                        + "Set image = ?, description = ?, price = ?, expirationDate = ?, categoryId = ?, quantity = ?, name = ?, status = ? "
                        + "Where id = ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, dto.getImage());
                preStm.setString(2, dto.getDescription());
                preStm.setDouble(3, dto.getPrice());
                preStm.setDate(4, dto.getDateExpired());
                preStm.setString(5, dto.getCategoryId());
                preStm.setInt(6, dto.getQuantity());
                preStm.setString(7, dto.getName());
                preStm.setString(8, dto.getStatus());
                preStm.setInt(9, dto.getId());
                int row = preStm.executeUpdate();
                check = row > 0;
            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public boolean decreaseQuantity(List<TblProductDTO> list, Connection conn)
            throws SQLException {
        PreparedStatement preStm = null;
        boolean result = true;

        try {
            if (conn != null) {
                conn.setAutoCommit(false);
                String sql = "Update tblProduct "
                        + "Set quantity = quantity - ? "
                        + "Where id = ? ";
                preStm = conn.prepareStatement(sql);
                for (TblProductDTO dto : list) {
                    preStm.setInt(1, dto.getQuantity());
                    preStm.setInt(2, dto.getId());
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

    public int countAllCakeWithoutPriceSearch(String name, String categoryId) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "Select COUNT(*) "
                        + "From tblProduct "
                        + "Where name LIKE ? AND categoryId = ? AND status = 'Active' And quantity > 0 AND expirationDate > ?";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%" + name + "%");
                preStm.setString(2, categoryId);
                preStm.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                rs = preStm.executeQuery();

                if (rs.next()) {
                    count = rs.getInt(1);
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

        return count;
    }

    public int countAllCakeWithoutPriceSearchAdmin(String name, String categoryId) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "Select COUNT(*) "
                        + "From tblProduct "
                        + "Where name LIKE ? AND categoryId = ? And quantity > 0 AND expirationDate > ?";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%" + name + "%");
                preStm.setString(2, categoryId);
                preStm.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                rs = preStm.executeQuery();

                if (rs.next()) {
                    count = rs.getInt(1);
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

        return count;
    }

    public int countAllCakeIncludePriceSearch(String name, double priceFrom, double priceTo, String categoryId) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "Select COUNT(id) "
                        + "From tblProduct "
                        + "Where name LIKE ? AND categoryId = ? AND price BETWEEN ? AND ? AND status = 'Active' And quantity > 0 AND expirationDate > ?";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%" + name + "%");
                preStm.setString(2, categoryId);
                preStm.setDouble(3, priceFrom);
                preStm.setDouble(4, priceTo);
                preStm.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                rs = preStm.executeQuery();

                if (rs.next()) {
                    count = rs.getInt(1);
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

        return count;
    }

    public int countAllCakeIncludePriceSearchAdmin(String name, double priceFrom, double priceTo, String categoryId) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "Select COUNT(id) "
                        + "From tblProduct "
                        + "Where name LIKE ? AND categoryId = ? AND price BETWEEN ? AND ? AND quantity > 0 AND expirationDate > ?";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%" + name + "%");
                preStm.setString(2, categoryId);
                preStm.setDouble(3, priceFrom);
                preStm.setDouble(4, priceTo);
                preStm.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                rs = preStm.executeQuery();

                if (rs.next()) {
                    count = rs.getInt(1);
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

        return count;
    }

    public String loadStatusById(String id)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        String status = "failed";
        try {
            conn = DBHelpers.getConnection();
            if (conn != null) {
                String sql = "Select status "
                        + "From tblProduct "
                        + "Where id = ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, id);
                rs = preStm.executeQuery();

                if (rs.next()) {
                    status = rs.getString("status");
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

        return status;
    }
}
