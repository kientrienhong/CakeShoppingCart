/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trienhk.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
import trienhk.tblproduct.TblProductDAO;
import trienhk.tblproduct.TblProductDTO;

/**
 *
 * @author Treater
 */
@WebServlet(name = "ModifyQuantityServlet", urlPatterns = {"/ModifyQuantityServlet"})
public class ModifyQuantityServlet extends HttpServlet {

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
        logger = Logger.getLogger(ModifyQuantityServlet.class.getName());
        BasicConfigurator.configure();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String txtId = request.getParameter("txtId");
        String txtIsIncrease = request.getParameter("txtIsIncrease");
        boolean isIncrease = txtIsIncrease.equals("yes");
        ServletContext context = request.getServletContext();
        ResourceBundle bundle = (ResourceBundle) context.getAttribute("SITE_MAP");
        String url = bundle.getString("viewCartMember");
        CheckOutError error = new CheckOutError();
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                CartObject cart = (CartObject) session.getAttribute("CART");
                if (cart != null) {
                    int quantityOfProductCurrentInCart = cart.getQuantityOfProduct(txtId);
                    TblProductDAO dao = new TblProductDAO();
                    int idInteger = Integer.parseInt(txtId);
                    TblProductDTO dto = dao.findById(idInteger);
                    if (dto != null) { // check is it existed
                        if (dto.getQuantity() > 0) {
                            if (isIncrease) {
                                if (quantityOfProductCurrentInCart < dto.getQuantity()) {
                                    cart.modifyQuantityOfProduct(txtId, isIncrease);
                                } else {
                                    request.setAttribute("ERROR_OUT_STOCK", "Out of stock!");
                                }
                            } else {
                                cart.modifyQuantityOfProduct(txtId, isIncrease);
                            }
                        } else {
                            String name = dao.getNameById(idInteger);
                            error.setQuantityError(name + " is not available");
                            request.setAttribute("ERROR_CHECKOUT", error);
                            cart.delete(txtId);
                        }
                    } else {
                        error.setQuantityError("product has id: " + txtId  + " not available");
                        request.setAttribute("ERROR_CHECKOUT", error);
                        cart.delete(txtId);
                    }
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
        } catch (NamingException ex) {
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
