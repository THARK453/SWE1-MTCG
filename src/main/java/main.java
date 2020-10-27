import java.util.Scanner;

public class main {
    public static void main(String[] args) {
      /* String test;
       Scanner input= new Scanner(System.in);
       while (true){
          test=input.nextLine();
          if("quit".equals(test)){
              System.out.println("000000");
              break;
          }
          System.out.println(test);
       }*/

        player playerA = new player();

        playerA.cardarry.add(new Dragon(50, 100, 1));


        playerA.cardarry.get(0);


        System.out.println(playerA.cardarry.get(0).getCname() + "\n" + playerA.cardarry.get(0).getDamage());

    }
}