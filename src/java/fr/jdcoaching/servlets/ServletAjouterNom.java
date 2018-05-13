package fr.jdcoaching.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.HibernateUtil;
import metier.Utilisateur;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author X1-Carbon
 */
public class ServletAjouterNom extends HttpServlet {

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
            throws ServletException, IOException, ParseException {

        String nom = request.getParameter("nomU");
        String prenom = request.getParameter("prenomU");
        String tel = request.getParameter("telU");
        String mail = request.getParameter("mailU");
        String genre = request.getParameter("genreU");
        String date = request.getParameter("dateU");
        String pwd = request.getParameter("pwdU");
        String pwd2 = request.getParameter("pwdU2");

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateNaissance = df.parse(date);
        Long telNum = Long.parseLong(tel);

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();

        Utilisateur u = new Utilisateur();
        if (pwd.equals(pwd2)) {

            u.setNomu(nom);
            u.setPrenomu(prenom);
            u.setDatenaissance(dateNaissance);
            u.setMdpu(pwd);
            u.setTelu(telNum);
            u.setGenreu(genre);
            u.setMailu(mail);
            u.setStatutu("en attente");
            session.save(u);
            t.commit();
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ServletAjouterNom.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ServletAjouterNom.class.getName()).log(Level.SEVERE, null, ex);
        }
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
