public class Person {
    private      int s_next_id=1;
    public int id;

    public Person(String vname,String nname) {
        s_next_id++;
        this.id = s_next_id;
        System.out.println(s_next_id+" ???");
    }
}
