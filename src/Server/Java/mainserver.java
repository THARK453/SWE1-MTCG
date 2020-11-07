import java.util.Scanner;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;

public class mainserver implements Runnable {
    private static ServerSocket _listener = null;
    Socket s;

    public mainserver(Socket s) {
        this.s=s;
    }


    public static void main(String[] args) {
        System.out.println("start server");

        try {
            _listener = new ServerSocket(8000, 5);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Runtime.getRuntime().addShutdownHook(new Thread(new MainServer()));

        try {
            while (true) {
                Socket s = _listener.accept();
                System.out.println("srv: sending welcome message");
                new Thread(new mainserver(s)).start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

                writer.write("Welcome to myserver!");
                writer.newLine();
                writer.write("Please enter your commands...");
                writer.newLine();
                writer.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                String message;
                do {
                    message = reader.readLine();
                    System.out.println("srv: received: " + message);
                } while (!"quit".equals(message));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            _listener.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        _listener = null;
        System.out.println("close server");
    }
}
