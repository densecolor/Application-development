package metier;
// Generated 23 f�vr. 2018 05:43:28 by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Calendrier1 generated by hbm2java
 */
public class Calendrier1  implements java.io.Serializable {


     private Date dateh;
     private Set<Posseder> posseders = new HashSet<Posseder>(0);

    public Calendrier1() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.dateh);
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
        final Calendrier1 other = (Calendrier1) obj;
        if (!Objects.equals(this.dateh, other.dateh)) {
            return false;
        }
        return true;
    }

	
    public Calendrier1(Date dateh) {
        this.dateh = dateh;
    }
    public Calendrier1(Date dateh, Set<Posseder> posseders) {
       this.dateh = dateh;
       this.posseders = posseders;
    }
   
    public Date getDateh() {
        return this.dateh;
    }
    
    public void setDateh(Date dateh) {
        this.dateh = dateh;
    }
    public Set<Posseder> getPosseders() {
        return this.posseders;
    }
    
    public void setPosseders(Set<Posseder> posseders) {
        this.posseders = posseders;
    }




}


