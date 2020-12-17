/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.servlets;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;
import java.sql.SQLException;
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

/**
 *
 * @author Treater
 */
@WebServlet(name = "CreateCakeServlet", urlPatterns = {"/CreateCakeServlet"})
public class CreateCakeServlet extends HttpServlet {

    private final String LOAD_CATEGORY = "";
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
            log("Error at CreateCakeServlet: " + e.getMessage());
        } finally {
            try {
                if (f != null) {
                    f.close();
                }
            } catch (Exception e) {
                log("Error at CreateCakeServlet: " + e.getMessage());
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
            log("Error at CreateCakeServlet: " + e.getMessage());
        } finally {
            try {
                if (f != null) {
                    f.close();
                }
            } catch (Exception e) {
                log("Error at CreateCakeServlet: " + e.getMessage());
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
        logger = Logger.getLogger(CreateCakeServlet.class.getName());
        BasicConfigurator.configure();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String name = request.getParameter("txtName");
        String description = request.getParameter("txtDescription");
        String priceString = request.getParameter("txtPrice");
        String currentImagePath = getPath() + "/web/img/";
        String categoryId = request.getParameter("categoryId");
        double price = 0;
        String expiredDate = request.getParameter("txtExpiredDate");
        String quantityString = request.getParameter("txtQuantity");
        int quantity = 0;
        String txtFile = request.getParameter("filename");
        Date expiredDateSQL = null;
        boolean isValid = true;
        TblProductError error = new TblProductError();
        ServletContext context = request.getServletContext();
        ResourceBundle bundle
                = (ResourceBundle) context.getAttribute("SITE_MAP");
        String url = bundle.getString(LOAD_CATEGORY);

        int lastIndexOf = txtFile.lastIndexOf("\\");
        String fileName = txtFile.substring(lastIndexOf + 1);
        java.util.Date currentDate = new java.util.Date();
        Date createdDate = new Date(currentDate.getTime());
        if (txtFile.trim().length() == 0) {
            isValid = false;
            error.setImageError("You must attach image file!");
        } else {
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

                if (expiredDateSQL.before(createdDate)) {
                    isValid = false;
                    error.setExpiredDateError("Expired Date must be after the current time!");
                }
            } catch (Exception e) {
                isValid = false;
                error.setExpiredDateError("Wrong format!");
            }
        }

        try {
            if (isValid) {
                TblProductDAO dao = new TblProductDAO();
                TblProductDTO dto = new TblProductDTO(description, name, price, fileName, categoryId, createdDate, expiredDateSQL, quantity);
                boolean result = dao.insert(dto);
                if (result) {
                    byte[] image = openFile(txtFile);
                    writeImageToSource(currentImagePath + fileName, image);
                    url = bundle.getString(LOAD_CATEGORY);
                }
            } else {
                request.setAttribute("ERROR_CREATE", error);
            }
        } catch (SQLException ex) {
            logger.error(ex);
        } catch (NamingException ex) {
            logger.error(ex);
        } finally {
            if (isValid) {
                response.sendRedirect(LOAD_CATEGORY);
            } else {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            }
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
