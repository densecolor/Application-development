package fr.jdcoaching.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.Exercice;
import metier.HibernateUtil;
import metier.Organiser;
import metier.Planifier;
import metier.PlanifierId;
import metier.Programme;
import metier.Seance;
import metier.Seancetype;
import metier.Utilisateur;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author edupin
 */
public class ServletCreerSeancePerso extends HttpServlet {

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

        //récupération des paramètres
        String codeSeanceType = request.getParameter("pcodeSeanceType");
        int codeU = Integer.parseInt(request.getParameter("pCodeU"));
        int codeP = Integer.parseInt(request.getParameter("pCodeP"));

        //on récupère la session hibernate
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        //on démarre la transaction de la session
        Transaction t = session.beginTransaction();

        //requête permettant de récupérer l'objet SeanceType
        Query q = session.createQuery("from Seancetype as st where st.codest=" + codeSeanceType);
        Seancetype st = (Seancetype) q.list().get(0);

        //ATTENTION A MODIFIER
        //requête permettant de récupérer l'objet Utilisateur
        Query qUtil = session.createQuery("from Utilisateur as u where u.codeu=" + codeU);
        Utilisateur u = (Utilisateur) qUtil.list().get(0);
        Query qProg = session.createQuery("from Programme as p where p.codep=" + codeP);
        Programme prog = (Programme) qProg.list().get(0);

        //ATTENTION A MODIFIER
        //création de l'objet Seance et affectation des paramètres
        Seance se = new Seance();
        se.setDescrips(st.getDescripst());
        se.setLibelles(st.getLibellest());
        se.setUtilisateur(u);
        se.setProgramme(prog);
        se.setNbreps(2);
        se.setNumsemaine(0);

        //sauvegarde de l'objet Séance
        session.save(se);

        //récupération des objets Organiser de la Séance Type pour récupérer ses exercices
        Query qExo = session.createQuery("from Organiser as org where org.seancetype=" + codeSeanceType);

        //on entame une requête qui récupère la dernière séance rajoutée
        Query qS = session.createQuery("select max(s.codes) from Seance as s");
        int maxCodeSeance = (int) qS.list().get(0);

        //on récupère les exercices de Organiser et on les dupplique dans des objets Planifier
        List<Organiser> listeOrganiser = (List) qExo.list();
        for (int i = 0; i < listeOrganiser.size(); i++) {
            Exercice ex = (Exercice) listeOrganiser.get(i).getExercice();

            PlanifierId pId = new PlanifierId(ex.getCodee(), maxCodeSeance, 1);

            Planifier pla = new Planifier();

            pla.setExercice(ex);
            pla.setId(pId);
            pla.setSeance(se);
            pla.setOrdrep(listeOrganiser.get(i).getOrdreo());
            pla.setNbrepe(0);
            pla.setTempse(0);
            pla.setTempsrepose(0);

            //on sauvegarde chaque Planifier créé
            session.save(pla);

        }

        //Création du xml de message alerte
        try (PrintWriter out = response.getWriter()) {
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<informations>");
            out.println("<message>Ajout reussi!</message>");
            out.println("<code>" + maxCodeSeance + "</code>");
            out.println("<libelle>" + se.getLibelles() + "</libelle>");
            out.println("<description>" + se.getDescrips() + "</description>");
            out.println("</informations>");

        }
        t.commit();

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
