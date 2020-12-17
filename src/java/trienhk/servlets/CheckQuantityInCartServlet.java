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
@WebServlet(name = "CheckQuantityInCartServlet", urlPatterns = {"/CheckQuantityInCartServlet"})
public class CheckQuantityInCartServlet extends HttpServlet {
    private final String CHECK_OUT_PAGE = "checkOutPageMember"; 
    private final String VIEW_CART_PAGE = "viewCartMember";
    private Logger logger = null;

    public void init() {
        logger = Logger.getLogger(DeleteItemInCartServlet.class.getName());
        BasicConfigurator.configure();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ServletContext context = request.getServletContext();
        ResourceBundle bundle = (ResourceBundle) context.getAttribute("SITE_MAP");
        String url = bundle.getString(VIEW_CART_PAGE);
        
        try {
            HttpSession session = request.getSession(false);
            boolean isValid = true;
            if (session != null) {
                CartObject cart = (CartObject) session.getAttribute("CART");
                if (cart != null) {
                    TblProductDAO dao = new TblProductDAO();
                    List<TblProductDTO> list = dao.getQuantityOfProductInCart(cart);

                    String errorCheckQuantity = "";
                    for (TblProductDTO dto : list) {
                        if (dto.getQuantity() <= 0) {
                            isValid = false;
                            errorCheckQuantity += dto.getName() + " out of stock <br/>";
                            String idString = Integer.toString(dto.getId());
                            cart.delete(idString);
                        }
                    }
                    
                    if (!isValid){
                        CheckOutError error = new CheckOutError();
                        error.setQuantityError(errorCheckQuantity);
                        request.setAttribute("ERROR_CHECKOUT", error);
                    } else {
                        url = bundle.getString(CHECK_OUT_PAGE);
                    }
                }
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
