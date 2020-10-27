

public class monstercards extends Cards{

    private int HP;
    private int type;
   public int i;

    public monstercards(int damage, int HP, int type) {
        super(damage);
        this.HP = HP;
        this.type = type;
    }



    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }
}
