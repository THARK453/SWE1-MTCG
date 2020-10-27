

public abstract class Cards {
        private int damage;
        private String Cname;

    public Cards(int damage) {
        this.damage = damage;

    }

    public int getDamage() {
        return damage;
    }

    public String getCname() {
        return Cname;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setCname(String cname) {
        Cname = cname;
    }
}
