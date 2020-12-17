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
import trienhk.accessgoogle.GooglePojo;
import trienhk.accessgoogle.GoogleUtils;
import trienhk.tblregistration.TblRegistrationDAO;
import trienhk.tblregistration.TblRegistrationDTO;
import trienhk.tblrole.TblRoleDAO;

/**
 *
 * @author Treater
 */
@WebServlet(name = "LoginByGoogleServlet", urlPatterns = {"/loginGoogle"})
public class LoginByGoogleServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Logger logger = null;
    private final String SUCCESS = "";
    private final String ERROR = "error";

    public LoginByGoogleServlet() {
        super();
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
        logger = Logger.getLogger(LoginByGoogleServlet.class.getName());
        BasicConfigurator.configure();
        response.setContentType("text/html;charset=UTF-8");
        String code = request.getParameter("code");
        ServletContext context = request.getServletContext();
        ResourceBundle bundle = (ResourceBundle) context.getAttribute("SITE_MAP");
        PrintWriter out = response.getWriter();
        String url = bundle.getString(ERROR);
        try {
            if (code != null) {
                String accessToken = GoogleUtils.getToken(code);
                GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);
                String email = googlePojo.getEmail();
                String[] splitedEmail = email.split("@");
                TblRegistrationDTO dto = new TblRegistrationDTO(email, "", splitedEmail[0], "GG", "", "");
                TblRegistrationDAO dao = new TblRegistrationDAO();

                boolean isExisted = dao.isGoogleAccoutnExisted(dto.getId());
                if (!isExisted) {
                    boolean insertResult = dao.insertGoogleAccount(dto);
                    if (insertResult) {
                        url = bundle.getString(SUCCESS);
                        HttpSession session = request.getSession();
                        session.setAttribute("DTO", dto);
                        TblRoleDAO roleDAO = new TblRoleDAO();
                        String nameRole = roleDAO.getNameById(dto.getRoleId());
                        request.setAttribute("NAME_ROLE", nameRole);
                    }
                } else {
                    url = bundle.getString(SUCCESS);
                    HttpSession session = request.getSession();
                    session.setAttribute("DTO", dto);
                    TblRoleDAO roleDAO = new TblRoleDAO();
                    String nameRole = roleDAO.getNameById(dto.getRoleId());
                    request.setAttribute("NAME_ROLE", nameRole);
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
