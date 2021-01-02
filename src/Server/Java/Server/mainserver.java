package Server;
import Parse.*;
import Data.*;
import java.util.Scanner;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;


public class mainserver  {
    private static ServerSocket _listener = null;
    Socket s;


    public static void main(String[] args) {
        System.out.println("start server");

        try {
            _listener = new ServerSocket(10001, 5);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            while (true) {
                Socket s = _listener.accept();
                new Thread(new threadserver(s)).start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                _listener.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            _listener = null;
            System.out.println("close server");
        }



    }




}
