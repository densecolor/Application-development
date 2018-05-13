package fr.jdcoaching.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.Calendrier1;
import metier.Corps;
import metier.HibernateUtil;
import metier.Posseder;
import metier.PossederId;
import metier.Profil;
import metier.Utilisateur;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author liya
 */
public class ServletAjouterProfil extends HttpServlet {

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

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();

        String idCli = request.getParameter("idU");
        Integer idClient = Integer.parseInt(idCli);
        Utilisateur u = (Utilisateur) session.get(Utilisateur.class, idClient);
        if (request.getParameter("profilChoisit") == "") {

            // u.setProfil(null);
        } else {

            String profil = request.getParameter("profilChoisit");
            Query q = session.createQuery("from Profil as p where p.libelleprofil='" + profil + "'");
            int codeP = 0;
            List<Profil> listProfil = (List<Profil>) q.list();
            for (int i = 0; i < listProfil.size(); i++) {
                codeP = listProfil.get(i).getCodeprofil();
            }

            Profil p = (Profil) session.get(Profil.class, codeP);
            //u.setProfil(p);
            p.getUtilisateurs().add(u);
            u.getProfils().add(p);

        }

        if (request.getParameter("PoidsU") == "") {
            u.setPoids(null);

        } else {
            String poids = request.getParameter("PoidsU");
            Double poidsDouble = Double.parseDouble(poids);
            u.setPoids(poidsDouble);
        }

        if (request.getParameter("TailleU") == "") {
            u.setTaille(null);
        } else {
            String taille = request.getParameter("TailleU");
            Double tailleDouble = Double.parseDouble(taille);
            u.setTaille(tailleDouble);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = simpleDateFormat.format(new Date());
        java.sql.Date sqlDate = java.sql.Date.valueOf(today);
//    String ddd= simpleDateFormat.format(new Date(2018-01-01)); 
//    java.sql.Date sqlddd = java.sql.Date.valueOf(ddd); 
//    Calendrier1 calendtest=new Calendrier1();
//    calendtest.setDateh(sqlddd);
//    session.save(calendtest);

        Query qq = session.createQuery("from Calendrier1");
        List<Calendrier1> listc = (List<Calendrier1>) qq.list();

        if (listc.get(listc.size() - 1).getDateh().equals(sqlDate)) {

        } else {
            Calendrier1 calend = new Calendrier1();
            calend.setDateh(sqlDate);

            session.save(calend);

        }
        Query qqq = session.createQuery("from Calendrier1");
        List<Calendrier1> listca = (List<Calendrier1>) qqq.list();

        String str = request.getParameter("listecorps[]");
        String[] strArray = null;
        strArray = str.split(",");
        List<String> listResult = Arrays.asList(strArray);

        System.out.println(listca);
        System.out.println(listResult);
        for (int i = 0; i < listResult.size(); i++) {

            if ("0".equals(listResult.get(i))) {
            } else {
                Double mesure = Double.parseDouble(listResult.get(i));
                PossederId possederId = new PossederId();
                possederId.setCodecorps(i + 1);
                possederId.setCodeu(u.getCodeu());
                possederId.setDateh(listca.get(listca.size() - 1).getDateh());

                Corps co = (Corps) session.get(Corps.class, i + 1);
                Calendrier1 ca = (Calendrier1) session.get(Calendrier1.class, listca.get(listca.size() - 1).getDateh());
                Posseder posseder = new Posseder();
                posseder.setId(possederId);
                posseder.setMesure(mesure);
                session.save(posseder);
                co.getPosseders().add(posseder);
                u.getPosseders().add(posseder);
                ca.getPosseders().add(posseder);
            }

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
