package fr.jdcoaching.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.Calendrier2;
import metier.Exercice;
import metier.HibernateUtil;
import metier.Realisernormal;
import metier.RealisernormalId;
import metier.Seance;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author tpereira
 */
@WebServlet(name = "ServletSeanceReal", urlPatterns = {"/ServletSeanceReal"})
public class ServletSeanceReal extends HttpServlet {

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

        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();

        String diff = request.getParameter("diff");
        String ses = request.getParameter("sess");
        Integer exo = Integer.valueOf(request.getParameter("ex"));
        Query q = session.createQuery("select p.exercice from Planifier as p where p.seance.codes = '" + ses + "'order by p.ordrep");
        ArrayList<Exercice> listeExercice = (ArrayList<Exercice>) q.list();

        Integer codeEx = listeExercice.get(exo).getCodee();
        Calendrier2 cal = new Calendrier2(LocalDate.now().toString());
        Realisernormal real = new Realisernormal();
        real.setResultat(diff);

        Query q2 = session.createQuery("from Calendrier2 as c where c.dater = '" + LocalDate.now().toString() + "'");
        ArrayList<Object> compteDate = (ArrayList<Object>) q2.list();
        Seance sessCour = (Seance) session.get(Seance.class, Integer.valueOf(ses));
        real.setCalendrier2(cal);
        real.setSeance(sessCour);
        RealisernormalId reId = new RealisernormalId(Integer.valueOf(ses), codeEx, LocalDate.now().toString());
        real.setId(reId);
        real.setExercice(listeExercice.get(exo));
        if (compteDate.isEmpty()) {
            session.save(cal);
        }
        session.save(real);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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