/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import trienhk.cart.CartObject;
import trienhk.checkout.CheckOutError;
import trienhk.tblorder.TblOrderDAO;
import trienhk.tblorder.TblOrderDTO;
import trienhk.tblorderdetail.TblOrderDetailDAO;
import trienhk.tblorderdetail.TblOrderDetailDTO;
import trienhk.tblpayment.TblPaymentDAO;
import trienhk.tblpayment.TblPaymentDTO;
import trienhk.tblproduct.TblProductDAO;
import trienhk.tblproduct.TblProductDTO;
import trienhk.utils.DBHelpers;

/**
 *
 * @author Treater
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

    private final String CHECK_OUT = "checkOutPageMember";
    private final String CHECK_OUT_SUCCESS = "checkOutSuccessMember";
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
    public void init() {
        logger = Logger.getLogger(CheckOutServlet.class.getName());
        BasicConfigurator.configure();
    }

    private String random() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 3;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString.toUpperCase();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String txtId = request.getParameter("txtUserId");
        String txtRecipientName = request.getParameter("txtRecipientName");
        String address = request.getParameter("txtAddress");
        String phone = request.getParameter("txtPhone");
        ServletContext context = request.getServletContext();
        ResourceBundle bundle = (ResourceBundle) context.getAttribute("SITE_MAP");
        String url = bundle.getString(CHECK_OUT);
        CheckOutError error = new CheckOutError();
        Connection conn = null;
        boolean isValid = true;

        if (txtRecipientName.length() == 0) {
            isValid = false;
            error.setNameError("Name can not be empty!");
        }

        if (address.length() == 0) {
            isValid = false;
            error.setAddressError("Address can not be empty!");
        }

        if (phone.length() == 0) {
            isValid = false;
            error.setPhoneError("Phone can not be empty!");
        } else {
            Pattern pattern = Pattern.compile("[0-9]{10}");
            Matcher matcher = pattern.matcher(phone);
            if (!matcher.find()) {
                isValid = false;
                error.setPhoneError("Wrong format must input 10 number!");
            }
        }

        try {
            if (isValid) {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    CartObject cart = (CartObject) session.getAttribute("CART");
                    if (cart != null) {
                        // everything works fine
                        TblOrderDAO orderDAO = new TblOrderDAO();

                        // create idOrder txtId_3 random character_yyyy_MM_dd format
                        Timestamp ts = new Timestamp(System.currentTimeMillis());
                        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
                        String[] dateSplit = date.toString().split("-");
                        String idOrder = "";
                        TblOrderDTO orderDTO;
                        if (txtId.length() > 0) {
                            idOrder = txtId + "_" + random() + "_" + dateSplit[0] + dateSplit[1] + dateSplit[2];
                            orderDTO = new TblOrderDTO(idOrder, ts, address, cart.totalPrice(), txtId, phone, "");
                        } else {
                            idOrder = random() + "_" + dateSplit[0] + dateSplit[1] + dateSplit[2];
                            orderDTO = new TblOrderDTO(idOrder, ts, address, cart.totalPrice(), null, phone, "");
                        }

                        // get all product in cart to prepare for db insert
                        TblOrderDetailDAO orderDetailDAO = new TblOrderDetailDAO();
                        List<TblOrderDetailDTO> listOrderDetail = new ArrayList<>();
                        List<TblProductDTO> listProduct = new ArrayList<>();
                        for (String key : cart.getItems().keySet()) {
                            TblProductDTO dto = cart.getItems().get(key);
                            String productId = key;
                            int quantity = dto.getQuantity();
                            double price = dto.getPrice();
                            TblOrderDetailDTO orderDetailDTO = new TblOrderDetailDTO(idOrder, Integer.parseInt(productId), quantity, price);
                            listOrderDetail.add(orderDetailDTO);
                            listProduct.add(dto);
                        }

                        // transaction insert
                        conn = DBHelpers.getConnection();
                        TblProductDAO productDAO = new TblProductDAO();
                        TblPaymentDAO paymentDAO = new TblPaymentDAO();
                        TblPaymentDTO paymentDTO = new TblPaymentDTO("P_" + idOrder, idOrder, "Not yet", "COD");
                        boolean checkInsetPayment = paymentDAO.insert(paymentDTO, conn);
                        orderDTO.setPaymentId("P_" + idOrder);
                        boolean checkOrderInsert = orderDAO.insert(orderDTO, conn);
                        boolean checkOrderDetailInsert = orderDetailDAO.insert(listOrderDetail, conn);
                        boolean checkDecreaseQuantity = productDAO.decreaseQuantity(listProduct, conn);
                        if (checkInsetPayment && checkOrderInsert && checkOrderDetailInsert && checkDecreaseQuantity) {
                            conn.commit();
                            request.setAttribute("CHECK_OUT_SUCCESS", "Check out successfully!");
                            session.removeAttribute("CART");
                            request.setAttribute("ORDER_ID", idOrder);
                            url = bundle.getString(CHECK_OUT_SUCCESS);
                        } else {
                            conn.rollback();
                        }

                    }
                }
            } else {
                request.setAttribute("ERROR_CHECKOUT", error);
            }
        } catch (NamingException ex) {
            logger.error(ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                logger.error(ex1);
            }
        } catch (SQLException ex) {
            logger.error(ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                logger.error(ex1);
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e);
            }

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
