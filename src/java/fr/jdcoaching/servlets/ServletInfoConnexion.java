package fr.jdcoaching.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.Coach;
import metier.HibernateUtil;
import metier.Message;
import metier.Utilisateur;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author tpereira
 */
@WebServlet(name = "ServletInfoConnexion", urlPatterns = {"/ServletInfoConnexion"})
public class ServletInfoConnexion extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.
    //Click on the + sign on the left to edit the code.">
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

        /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        try (PrintWriter out = response.getWriter()) {
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");

            out.println("<verifCo>");
            /*----- Récupération des paramètres -----*/
            String mail = request.getParameter("mail");
            String mdp = request.getParameter("mdp");
            //  out.println("<erreur>" + Message.ERRMAIL + "</erreur>");//                }
//            }
            Query q = session.createQuery("FROM Utilisateur as ut where ut.mailu = '" + mail + "'");

            ArrayList<Utilisateur> lu = (ArrayList) q.list();
            if (lu.isEmpty()) {
                out.println("<erreur>" + Message.ERRMAIL + "</erreur>");
                Query q3 = session.createQuery("FROM Coach as ut where ut.mailc = '" + mail + "' and ut.mdpc = '" + mdp + "'");
                ArrayList<Coach> lu3 = (ArrayList) q3.list();
                if (!lu3.isEmpty()) {
                    for (Coach coach : lu3) {
                        out.println("<coach>" + Message.OK + "<id>" + coach.getCodec() + "</id><nom>" + coach.getNomc() + "</nom> <prenom>" + coach.getPrenomc() + "</prenom> </coach>");
                    }

                }
            } else {
                Query q2 = session.createQuery("FROM Utilisateur as ut where ut.mailu = '" + mail + "' and ut.mdpu = '" + mdp + "'");
                ArrayList<Utilisateur> lu2 = (ArrayList) q2.list();
                if (lu2.isEmpty()) {
                    out.println("<erreur>" + Message.ERRMDP + "</erreur>");
                } else {
                    for (Utilisateur utilisateur : lu2) {
                        out.println("<ok>" + Message.OK + "<id>" + utilisateur.getCodeu() + "</id><nom>" + utilisateur.getNomu() + "</nom> <prenom>" + utilisateur.getPrenomu() + "</prenom> </ok>");
                    }

                }
            }
            out.println("</verifCo>");
        }
        t.commit();
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
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

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
