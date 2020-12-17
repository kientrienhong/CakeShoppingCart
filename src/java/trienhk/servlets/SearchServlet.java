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
import trienhk.tblproduct.TblProductDAO;
import trienhk.tblproduct.TblProductDTO;
import trienhk.tblproduct.TblProductError;

/**
 *
 * @author Treater
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

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
        logger = Logger.getLogger(SearchServlet.class.getName());
        BasicConfigurator.configure();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String name = request.getParameter("txtName");
        String category = request.getParameter("categoryCB");
        String[] categorySplit = category.split("_");
        String priceFromString = request.getParameter("txtPriceFrom");
        String priceToString = request.getParameter("txtPriceTo");
        String roleName = (String) request.getAttribute("NAME_ROLE");
        ServletContext context = request.getServletContext();
        ResourceBundle bundle = (ResourceBundle) context.getAttribute("SITE_MAP");
        String url = bundle.getString("");
        String currentPageString = request.getParameter("txtCurrentPage");
        double priceFrom = 0;
        double priceTo = 0;
        boolean isValid = true;
        TblProductError error = new TblProductError();
        if (currentPageString == null) {
            currentPageString = "1";
        }

        if (currentPageString.length() == 0) { // in case when click update there is parameter txtCurrentPage althought empty, not null
            currentPageString = "1";
        }

        if (priceFromString.length() > 0 && priceToString.length() > 0) {
            try {
                priceFrom = Double.parseDouble(priceFromString);
            } catch (Exception e) {
                isValid = false;
                error.setPriceFromError("Price From must be numeric!");
            }

            try {
                priceTo = Double.parseDouble(priceToString);
            } catch (Exception e) {
                isValid = false;
                error.setPriceToError("Price To must be numeric");
            }

            if (isValid) {
                if (priceFrom > priceTo) {
                    isValid = false;
                    error.setPriceError("Price From must be less than Price To");
                }
            }
        } else {
            if (priceToString.length() == 0 && priceFromString.length() > 0) {
                isValid = false;
                error.setPriceToError("Price To must be filled");
            }

            if (priceFromString.length() == 0 && priceToString.length() > 0) {
                isValid = false;
                error.setPriceFromError("Price From must be filled");
            }
        }

        if (roleName == null){ // if there is's any session role = failed
            roleName = "failed";
        }
        
        try {
            if (isValid) {
                int currentPage = Integer.parseInt(currentPageString);
                TblProductDAO dao = new TblProductDAO();
                List<TblProductDTO> list = null;
                int countRecord = 0;
                if (roleName.equals("failed") || roleName.equals("member") || roleName.equals("googleAccount")) {
                    if (priceFromString.length() == 0 && priceFromString.length() == 0) {
                        list = dao.searchWithoutPrice(name, categorySplit[0], currentPage, categorySplit[1]);
                        countRecord = dao.countAllCakeWithoutPriceSearch(name, categorySplit[0]);
                    } else {
                        list = dao.searchIncludedPrice(name, priceFrom, priceTo, categorySplit[0], currentPage, categorySplit[1]);
                        countRecord = dao.countAllCakeIncludePriceSearch(name, priceFrom, priceTo, categorySplit[0]);
                    }
                } else if (roleName.equals("admin")) {
                    if (priceFromString.length() == 0 && priceFromString.length() == 0) {
                        list = dao.searchWithoutPriceAdmin(name, categorySplit[0], currentPage, categorySplit[1]);
                        countRecord = dao.countAllCakeWithoutPriceSearchAdmin(name, categorySplit[0]);
                    } else {
                        list = dao.searchIncludedPriceAdmin(name, priceFrom, priceTo, categorySplit[0], currentPage, categorySplit[1]);
                        countRecord = dao.countAllCakeIncludePriceSearchAdmin(name, priceFrom, priceTo, categorySplit[0]);
                    }
                }

                int totalPage;
                if (countRecord % 20 == 0) {
                    totalPage = countRecord / 20;
                } else {
                    totalPage = countRecord / 20 + 1;
                }
                
                request.setAttribute("CURRENT_PAGE", currentPageString);
                request.setAttribute("CURRENT_AMOUNT_POSTS", list.size());
                request.setAttribute("TOTAL_PAGE", totalPage);
                request.setAttribute("LIST_PRODUCT", list);
            } else {
                request.setAttribute("ERROR_PRICE", error);
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
