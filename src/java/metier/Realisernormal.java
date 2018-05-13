package metier;
// Generated 23 f�vr. 2018 05:43:28 by Hibernate Tools 4.3.1

import java.util.Objects;




/**
 * Realisernormal generated by hbm2java
 */
public class Realisernormal  implements java.io.Serializable {


     private RealisernormalId id;
     private Calendrier2 calendrier2;
     private Exercice exercice;
     private Seance seance;
     private Integer ordrer;
     private String resultat;
     private String commentairecli;
     private String etatlucli;

    public Realisernormal() {
    }

	
    public Realisernormal(RealisernormalId id, Calendrier2 calendrier2, Exercice exercice, Seance seance) {
        this.id = id;
        this.calendrier2 = calendrier2;
        this.exercice = exercice;
        this.seance = seance;
    }
    public Realisernormal(RealisernormalId id, Calendrier2 calendrier2, Exercice exercice, Seance seance, Integer ordrer, String resultat, String commentairecli, String etatlucli) {
       this.id = id;
       this.calendrier2 = calendrier2;
       this.exercice = exercice;
       this.seance = seance;
       this.ordrer = ordrer;
       this.resultat = resultat;
       this.commentairecli = commentairecli;
       this.etatlucli = etatlucli;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Realisernormal other = (Realisernormal) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
   
    public RealisernormalId getId() {
        return this.id;
    }
    
    public void setId(RealisernormalId id) {
        this.id = id;
    }
    public Calendrier2 getCalendrier2() {
        return this.calendrier2;
    }
    
    public void setCalendrier2(Calendrier2 calendrier2) {
        this.calendrier2 = calendrier2;
    }
    public Exercice getExercice() {
        return this.exercice;
    }
    
    public void setExercice(Exercice exercice) {
        this.exercice = exercice;
    }
    public Seance getSeance() {
        return this.seance;
    }
    
    public void setSeance(Seance seance) {
        this.seance = seance;
    }
    public Integer getOrdrer() {
        return this.ordrer;
    }
    
    public void setOrdrer(Integer ordrer) {
        this.ordrer = ordrer;
    }
    public String getResultat() {
        return this.resultat;
    }
    
    public void setResultat(String resultat) {
        this.resultat = resultat;
    }
    public String getCommentairecli() {
        return this.commentairecli;
    }
    
    public void setCommentairecli(String commentairecli) {
        this.commentairecli = commentairecli;
    }
    public String getEtatlucli() {
        return this.etatlucli;
    }
    
    public void setEtatlucli(String etatlucli) {
        this.etatlucli = etatlucli;
    }




}

