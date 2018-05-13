package fr.jdcoaching.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.Coach;
import metier.Exercice;
import metier.HibernateUtil;
import metier.Planifier;
import metier.PlanifierId;
import metier.Seance;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author edupin
 */
public class ServletAjouterExerciceSeance extends HttpServlet {

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

        //récupération des paramètres et conversion en int
        int codeExo = Integer.parseInt(request.getParameter("pCodeExercice"));
        int codeS = Integer.parseInt(request.getParameter("pCodeSeance"));

        //on récupère la session hibernate
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        //on débute la transaction
        Transaction t = session.beginTransaction();

        //on récupère l'objet la Séance
        Query q1 = session.createQuery("from Seance as s where s.codes=" + codeS + "");
        Seance s = (Seance) q1.list().get(0);

        //on récupère l'objet Exercice à rajouter à la Séance
        Query q2 = session.createQuery("from Exercice as ex where ex.codee=" + codeExo + "");
        Exercice ex = (Exercice) q2.list().get(0);

        //on récupère l'objet Exercice à rajouter à la Séance
        Query q3 = session.createQuery("from Coach as ex where ex.codec=1");
        Coach ch = (Coach) q3.list().get(0);

//on créé un objet PlanifierId        
        PlanifierId plaid = new PlanifierId();
        plaid.setCodee(codeExo);
        plaid.setCodes(codeS);
        plaid.setCodec(1);
        

        //Création de l'objet Planifier et affectation des éléments d'affectation de l'exercice à la séance
        Planifier pla = new Planifier();
        pla.setId(plaid);
        pla.setExercice(ex);
        pla.setSeance(s);
        pla.setCoach(ch);
        pla.setSeriep(3);

        //sauvegarde de l'objet Planifier
        session.save(pla);

        //rajout de l'objet Planifier à la liste de la Séance et de l'Exercice
        s.getPlanifiers().add(pla);
        ex.getPlanifiers().add(pla);

        //sauvegarde des modifications apportées à l'Exercice et à la Séance
        session.save(ex);
        session.save(s);

        t.commit();

        //Création du xml de message alerte
        try (PrintWriter out = response.getWriter()) {
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<informations>");
            out.println("<message>Ajout reussi!</message>");
            out.println("</informations>");

        } catch (Exception e) {
            try (PrintWriter out = response.getWriter()) {
                /*----- Ecriture de la page XML -----*/
                out.println("<?xml version=\"1.0\"?>");
                out.println("<message> Erreur - " + e + "</message>");
            }

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
