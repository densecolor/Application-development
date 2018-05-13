package fr.jdcoaching.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.Exercice;
import metier.HibernateUtil;
import metier.Organiser;
import metier.OrganiserId;
import metier.Seancetype;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author edupin
 */
public class ServletSeanceTypeAjouterExo extends HttpServlet {

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
        int codeExo = Integer.parseInt(request.getParameter("pExoCode"));
        int codeST = Integer.parseInt(request.getParameter("pCodeST"));
        int nbSeries = Integer.parseInt(request.getParameter("pNbSerie"));
        int OrdreEx = Integer.parseInt(request.getParameter("pOrdreEx"));

        //on récupère la session hibernate
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        //on débute la transaction
        Transaction t = session.beginTransaction();

        //on récupère l'objet la Séance Type
        Query q1 = session.createQuery("from Seancetype as st where st.codest=" + codeST + "");
        Seancetype st = (Seancetype) q1.list().get(0);

        //on récupère l'objet Exercice à rajouter à la Séance Type
        Query q2 = session.createQuery("from Exercice as ex where ex.codee=" + codeExo + "");
        Exercice ex = (Exercice) q2.list().get(0);

        //Création de l'objet OrganiserID avec affectation du code d'Exercice et du code Séance
        OrganiserId orgid = new OrganiserId();
        orgid.setCodee(codeExo);
        orgid.setCodest(codeST);

        //Création de l'objet Organiser et affectation des éléments d'affectation de l'exercice à la séance
        Organiser org = new Organiser();
        org.setId(orgid);
        org.setExercice(ex);
        org.setSeancetype(st);
        org.setSeriest(nbSeries);
        org.setOrdreo(OrdreEx);
        
        //sauvegarde de l'objet Organiser
        session.save(org);
      
       //rajout de l'objet Organiser à la liste de la Séance et de l'Exercice
        st.getOrganisers().add(org);
        ex.getOrganisers().add(org);

        //sauvegarde des modifications apportées à l'Exerice et à la Séance
        session.save(ex);
        session.save(st);

        t.commit();

        //Création du xml de message alerte
        try (PrintWriter out = response.getWriter()) {
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<informations>");
            out.println("<message>Ajout reussi!</message>");
            out.println("</informations>");

        }

    
    catch (Exception e) {
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
