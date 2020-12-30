import java.io.Console;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
       String test;
       Scanner input= new Scanner(System.in);
        Console con=System.console();
        System.out.println("username: ");
        String username=input.nextLine();
        System.out.println("password: ");

        String password=new String(con.readPassword());
        System.out.println(username+"\n"+password);

        player playerA = new player();

        playerA.cardarry.add(new Dragon(50));


        playerA.cardarry.get(0);


        System.out.println(playerA.cardarry.get(0).getCname() + "\n" + playerA.cardarry.get(0).getDamage());

    }
}