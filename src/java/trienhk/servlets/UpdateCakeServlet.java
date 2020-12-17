/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import trienhk.tblproduct.TblProductDAO;
import trienhk.tblproduct.TblProductDTO;
import trienhk.tblproduct.TblProductError;
import trienhk.tblupdaterecord.TblUpdateRecordDAO;
import trienhk.tblupdaterecord.TblUpdateRecordDTO;

/**
 *
 * @author Treater
 */
@WebServlet(name = "UpdateCakeServlet", urlPatterns = {"/UpdateCakeServlet"})
public class UpdateCakeServlet extends HttpServlet {

    private final String LOAD_CATEGORY_SERVLET = "";
    private final String SEARCH_SERLVET = "search";
    private Logger logger = null;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private byte[] openFile(String fileName) {
        FileInputStream f = null;
        byte[] kq = null;
        try {
            f = new FileInputStream(fileName);
            if (f != null) { // lấy kích thước file
                int size = f.available();
                kq = new byte[size];
                f.read(kq); // đọc dữ liệu của f và cất vào kq 

            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            try {
                if (f != null) {
                    f.close();
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }

        return kq;
    }

    private void writeImageToSource(String fileName, byte[] kq) {
        FileOutputStream f = null;

        try {
            f = new FileOutputStream(fileName);
            if (f != null && kq != null) {
                f.write(kq);
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            try {
                if (f != null) {
                    f.close();
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }
    }

    public String getPath() throws UnsupportedEncodingException {

        String path = this.getClass().getClassLoader().getResource("").getPath();
        String fullPath = URLDecoder.decode(path, "UTF-8");
        String pathArr[] = fullPath.split("/build/web/WEB-INF/classes/");
        fullPath = pathArr[0];

        return fullPath;
    }

    public void init() {
        logger = Logger.getLogger(UpdateCakeServlet.class.getName());
        BasicConfigurator.configure();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String idProduct = request.getParameter("txtId");
        String name = request.getParameter("txtNameUpdate");
        String description = request.getParameter("txtDescription");
        String priceString = request.getParameter("txtPrice");
        String expiredDate = request.getParameter("txtExpiredDate");
        String quantityString = request.getParameter("txtQuantity");
        String txtFile = request.getParameter("filename");
        String status = request.getParameter("cbStatus");
        String categoryId = request.getParameter("categoryId");
        String userId = request.getParameter("txtUserId");
        int quantity = 0;
        double price = 0;
        Date expiredDateSQL = null;
        boolean isValid = true;
        TblProductError error = new TblProductError();
        ServletContext context = request.getServletContext();
        ResourceBundle bundle
                = (ResourceBundle) context.getAttribute("SITE_MAP");
        String url = bundle.getString(LOAD_CATEGORY_SERVLET);
        String fileName = "";
        java.util.Date currentDate = new java.util.Date();
        Date updateDate = new Date(currentDate.getTime());
        String currentImagePath = getPath() + "/web/img/";
        File fileCheckList = new File(currentImagePath);
        String[] listFile = fileCheckList.list();

        if (txtFile != null) {
            int lastIndexOf = txtFile.lastIndexOf("\\");
            fileName = txtFile.substring(lastIndexOf + 1);
            if (!fileName.contains(".jpg") && !fileName.contains(".png") && fileName.length() > 0) {
                isValid = false;
                error.setImageError("Your file must be .jpg or .png");
            }
        }

        if (name.trim().length() == 0) {
            error.setNameError("Name can not be empty!");
            isValid = false;
        }

        if (description.trim().length() == 0) {
            error.setDescriptionError("Description can not be empty!");
            isValid = false;
        }

        if (priceString.trim().length() == 0) {
            error.setPriceError("Price can not be empty!");
            isValid = false;
        } else {
            try {
                price = Double.parseDouble(priceString);

                if (price <= 0) {
                    isValid = false;
                    error.setPriceError("Price must be larger than 0");
                }
            } catch (Exception e) {
                isValid = false;
                error.setPriceError("Price must be numeric!");
            }
        }

        if (quantityString.trim().length() == 0) {
            error.setQuantityError("Quantity can not be empty!");
            isValid = false;
        } else {
            try {
                quantity = Integer.parseInt(quantityString);
                if (quantity <= 0) {
                    isValid = false;
                    error.setQuantityError("Quantity must be larger than 0");
                }
            } catch (Exception e) {
                isValid = false;
                error.setQuantityError("Quantity must be numeric and interger!");
            }
        }

        if (expiredDate.trim().length() == 0) {
            isValid = false;
            error.setExpiredDateError("Expired date can not be empty!");
        } else {
            String pattern = "yyyy-MM-dd";
            try {
                DateFormat sdf = new SimpleDateFormat(pattern);
                sdf.setLenient(false);
                java.util.Date expiredDateUtil = sdf.parse(expiredDate);
                expiredDateSQL = new Date(expiredDateUtil.getTime());

                if (expiredDateSQL.before(updateDate)) {
                    isValid = false;
                    error.setExpiredDateError("Expired Date must be after the current time!");
                }
            } catch (Exception e) {
                isValid = false;
                error.setExpiredDateError("Wrong format!");
            }
        }

        for (int i = 0; i < listFile.length; i++) {
            if (fileName.equals(listFile[i])) {
                isValid = false;
                error.setImageError("Already has this name please change name!");
                break;
            }
        }

        try {
            if (isValid) {
                TblProductDAO dao = new TblProductDAO();
                TblProductDTO dto = new TblProductDTO(Integer.parseInt(idProduct), description, name, price, null, categoryId, expiredDateSQL, quantity, status);
                String imageProduct = dao.getProductImageById(idProduct);
                if (txtFile.length() == 0) {
                    dto.setImage(imageProduct);
                } else {
                    byte[] image = openFile(txtFile);
                    writeImageToSource(currentImagePath + fileName, image);
                    dto.setImage(fileName);
                }
                boolean result = dao.update(dto);
                if (result) {
                    if (txtFile.length() > 0) {
                        File file = new File(getPath() + "/web/img/" + imageProduct);
                        file.delete();
                    }
                    TblUpdateRecordDAO updateRecordDAO = new TblUpdateRecordDAO();
                    Timestamp ts = new Timestamp(System.currentTimeMillis());
                    java.util.Date dateCurrentUpdate = ts;
                    String idUpdateRecord = userId + "_" + dateCurrentUpdate.toString();
                    TblUpdateRecordDTO updateRecordDTO = new TblUpdateRecordDTO(idUpdateRecord, userId, idProduct, ts);
                    if (updateRecordDAO.insert(updateRecordDTO)) {
                        url = bundle.getString(SEARCH_SERLVET);
                    }
                }
            } else {
                request.setAttribute("ERROR_UPDATE", error);
            }
        } catch (NamingException ex) {
            logger.error(ex);
        } catch (SQLException ex) {
            logger.error(ex);
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
