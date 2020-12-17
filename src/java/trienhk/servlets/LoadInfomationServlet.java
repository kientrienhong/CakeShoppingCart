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
import trienhk.tblcategory.TblCategoryDAO;
import trienhk.tblcategory.TblCategoryDTO;
import trienhk.tblproduct.TblProductError;

/**
 *
 * @author Treater
 */
@WebServlet(name = "LoadInfomationServlet", urlPatterns = {"/LoadInfomationServlet"})
public class LoadInfomationServlet extends HttpServlet {

    private final String STORE = "store";
    private final String CREATE_CAKE_PAGE = "createCakePageAdmin";
    private final String UPDATE_CAKE_PAGE = "updatePageAdmin";
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
        logger = Logger.getLogger(LoadInfomationServlet.class.getName());
        BasicConfigurator.configure();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ServletContext context = request.getServletContext();
        ResourceBundle bundle = (ResourceBundle) context.getAttribute("SITE_MAP");
        String isCreatePage = request.getParameter("txtIsCreatePage");
        String txtIsUpdatePage = request.getParameter("txtIsUpdatePage");
        String url = bundle.getString(STORE);
        TblProductError errorCreate = (TblProductError) request.getAttribute("ERROR_CREATE");
        TblProductError errorUpdate = (TblProductError) request.getAttribute("ERROR_UPDATE");
        if (isCreatePage != null || errorCreate != null) {
            url = bundle.getString(CREATE_CAKE_PAGE);
        } else if (txtIsUpdatePage != null || errorUpdate != null) {
            url = bundle.getString(UPDATE_CAKE_PAGE);
        }

        try {
            TblCategoryDAO dao = new TblCategoryDAO();
            List<TblCategoryDTO> list = dao.loadListCategory();
            request.setAttribute("LIST_CATEGORY", list);
            HttpSession session = request.getSession(false);
            if (session != null) { // load amount of add products in cart 
                CartObject cart = (CartObject) session.getAttribute("CART");
                if (cart != null) {
                    request.setAttribute("CART_SIZE", cart.countTotalProducts());
                } else {
                    request.setAttribute("CART_SIZE", 0);
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
