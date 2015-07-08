package fouriam.android.esgi.fr.filmdroid.Models;

import java.io.Serializable;

/**
 * Created by Save92 on 01/07/15.
 */
public class Acteur implements Serializable{
    private Integer actorId;
    private String actorName;

    public Acteur() {

    }

    public Acteur (Integer id, String name) {
        setActorId(id);
        setActorName(name);
    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }
}
