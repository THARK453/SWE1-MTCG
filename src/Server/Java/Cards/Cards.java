package Cards;
import Parse.*;
import Server.*;
import Data.*;
import Game.*;

public class Cards {
    private String Name;
    private float Damage;
    private String Id;
    private boolean isMonster;
    private int type;
    private String Error;

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public float getDamage() {
        return Damage;
    }

    public void setDamage(float damage) {
        Damage = damage;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public boolean isMonster() {
        return isMonster;
    }

    public void setMonster(boolean monster) {
        isMonster = monster;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
