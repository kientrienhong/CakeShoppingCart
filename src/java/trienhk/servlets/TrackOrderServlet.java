/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
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
import trienhk.tblorder.TblOrderDAO;
import trienhk.tblorder.TblOrderDTO;
import trienhk.tblorderdetail.TblOrderDetailDAO;
import trienhk.tblorderdetail.TblOrderDetailDTO;
import trienhk.tblpayment.TblPaymentDAO;
import trienhk.tblpayment.TblPaymentDTO;
import trienhk.tblproduct.TblProductDAO;

/**
 *
 * @author Treater
 */
@WebServlet(name = "TrackOrderServlet", urlPatterns = {"/TrackOrderServlet"})
public class TrackOrderServlet extends HttpServlet {

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
        logger = Logger.getLogger(TrackOrderServlet.class.getName());
        BasicConfigurator.configure();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String txtIdOrder = request.getParameter("txtIdOrder");
        String txtUserId = request.getParameter("txtUserId");
        ServletContext context = request.getServletContext();
        ResourceBundle bundle = (ResourceBundle) context.getAttribute("SITE_MAP");
        String url = bundle.getString("trackOrderPageMember");
        try {
            TblOrderDAO tblOrderDAO = new TblOrderDAO();
            TblOrderDTO tblOrderDTO = tblOrderDAO.findById(txtIdOrder, txtUserId);
            if (tblOrderDTO != null) {
                TblOrderDetailDAO tblOrderDetailDAO = new TblOrderDetailDAO();
                List<TblOrderDetailDTO> listOrderDetail = tblOrderDetailDAO.findByOrderId(txtIdOrder);

                TblProductDAO productDAO = new TblProductDAO();
                List<String> listNameProduct = productDAO.getListNameFromProductIdsInListOrder(listOrderDetail);
                TblPaymentDAO paymentDAO = new TblPaymentDAO(); 
                TblPaymentDTO tblPaymentDTO = paymentDAO.findById(tblOrderDTO.getPaymentId());
                
                request.setAttribute("PAYMENT", tblPaymentDTO);
                request.setAttribute("ORDER_DETAIL", tblOrderDTO);
                request.setAttribute("LIST_ORDER_DETAIL", listOrderDetail);
                request.setAttribute("LIST_NAME", listNameProduct);
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
