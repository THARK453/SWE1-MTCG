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



    }
}