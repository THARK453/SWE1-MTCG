import java.util.Scanner;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;


public class threadserver implements Runnable {


    Socket s;
    public threadserver(Socket s) {
        this.s=s;
    }

    @Override
    public void run() {
       try {

                BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                String message;
                String tmsg;
                do {

                    tmsg = reader.readLine();
                    message=tmsg;
                    if(message!=null ){
                        System.out.println("srv: received:-- " + message);
                    }

                } while (message!=null && !message.isEmpty());


           PrintStream out = new PrintStream(s.getOutputStream());

           String mes = "hello world!";
           out.println("HTTP/1.0 200 OK");
           out.println("MIME_version:1.0");
           out.println("Content_Type:text/html");
           int len = mes.getBytes().length;
           out.println("Content_Length:"+len);
           out.println("");
           out.println(mes);
           out.flush();


                s.close();


        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}
