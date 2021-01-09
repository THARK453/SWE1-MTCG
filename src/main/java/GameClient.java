import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;



public class GameClient {
    public static void main(String[] args) {
        System.out.println("start client");

        try (Socket socket = new Socket("localhost", 8000);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            PrintStream out = new PrintStream(socket.getOutputStream());
            System.out.println("server "+reader.readLine());

            String input = "";
            System.out.print("option: "+"\n"+"create user -[1]"+"\nLogin -[2]\n");

            Scanner s=new Scanner(System.in);
            int option=s.nextInt();
            if(option==1){
               /* Scanner sin=new Scanner(System.in);
                System.out.println("username: ");
                String username=sin.nextLine();
                System.out.println("password: ");
                String password=sin.nextLine();
                input=input.concat("POST\n"+username+"\n"+password);*/
                String msg=Requestbuilder.RequestLine("GET");
                out.println(msg);
                out.flush();
                System.out.println(" \nStill under development \n");
            }




        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("close client");

    }
}


