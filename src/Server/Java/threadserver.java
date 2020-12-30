import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysql.management.util.Str;
import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

public class threadserver implements Runnable {


    Socket s,s2;
    public threadserver(Socket s) {
        this.s=s;
        this.s2=s;
    }

    @Override
    public void run() {
       try {

             /*  BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                String message;
                String tmsg;


                do {

                    tmsg = reader.readLine();
                    message=tmsg;
                    if(message!=null ){
                        System.out.println("srv: received:-- " + message);
                    }

                } while (message!=null && !message.isEmpty());*/
          /* BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(s2.getOutputStream()));
           writer.write("connect to server!");
           writer.newLine();
           writer.flush();*/

          /* String msg="connect to server!";
           PrintStream out = new PrintStream(s.getOutputStream());
           out.println(msg);
           out.flush();*/


         // BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));

           /*Scanner scan=new Scanner(new InputStreamReader(s.getInputStream()));
           while (scan.hasNextLine()){
               System.out.println(scan.nextLine());
           }*/


          // System.out.println("thread start");
           /*GameData gameData=new GameData();
           gameData.connect();*/


           Request httpRequest = parse.parserequest(s.getInputStream());

           System.out.println("line : \n"+httpRequest.getMethod()+" "+httpRequest.getUrl()+" "+httpRequest.getVersion()+"\n");
           System.out.println("head : \n"+httpRequest.getHeaders()+"\n");
           /*System.out.println(httpRequest.getHeaders().get("Host")+"\n");
           if(httpRequest.getHeaders().containsKey("Content-Type")){
               System.out.println(httpRequest.getHeaders().containsKey("Content-Type")+"\n");
               System.out.println(httpRequest.getHeaders().get("Content-Type"));
           }*/
           System.out.println("body :\n"+httpRequest.getMessage());
           //System.out.println("\n frist body: \n"+httpRequest.getMessage().substring(0,1));


           if (httpRequest.getMessage().substring(0,1).equals("[")){
               httpRequest.setBodytype("Arrayjson");
           }else {
               httpRequest.setBodytype("json");
           }



          PrintStream out = new PrintStream(s.getOutputStream());

           try {
               String result ="";
               if(httpRequest.getMethod().equals("POST") && httpRequest.getUrl().equals("/users")){

                       Jsonmsg jsonmsg=parse.getjson(httpRequest.getMessage());
                       result=Datasql.Registration(jsonmsg);

               }
               else if (httpRequest.getMethod().equals("POST") && httpRequest.getUrl().equals("/packages")){
                   List<Jsonmsg> jsonmsgList=parse.getjsonlist(httpRequest.getMessage());
                   result=Datasql.createpackages(jsonmsgList);
               }

             /* if(httpRequest.getMethod().equals("GET") && httpRequest.getUrl().equals("/messages")){
                  System.out.println("showmessages");
                  System.out.println(httpRequest.getUrl());
                   result = messages.showMessages();
                  System.out.println(result);

               }else if(httpRequest.getMethod().equals("POST") && httpRequest.getUrl().equals("/messages")){
                  // System.out.println("addMessages");
                  System.out.println(httpRequest.getUrl());
                   result = messages.addMessages(httpRequest.getMessage());

               }else if(httpRequest.getMethod().equals("GET") && httpRequest.getUrl()!="/messages"){
                  System.out.println(httpRequest.getUrl());
                  // System.out.println("SelectMessages");
                  String getindex=httpRequest.getUrl().replaceAll("messages","");
                  getindex=getindex.replace("/","");
                  System.out.println(getindex);
                  int index=Integer.parseInt(getindex);
                  System.out.println("index--"+index);
                  result=messages.SelectMessages(index);

              }else if(httpRequest.getMethod().equals("PUT") ){
                  System.out.println(httpRequest.getUrl());
                  // System.out.println("updateMessages");
                  String getindex=httpRequest.getUrl().replaceAll("messages","");
                  getindex=getindex.replace("/","");
                  System.out.println(getindex);
                  int index=Integer.parseInt(getindex);
                  System.out.println("index--"+index);
                  result=messages.updateMessages(index,httpRequest.getMessage());

              }else if(httpRequest.getMethod().equals("DELETE") ){
                  System.out.println(httpRequest.getUrl());

                  String getindex=httpRequest.getUrl().replaceAll("messages","");
                  getindex=getindex.replace("/","");
                  System.out.println(getindex);
                  int index=Integer.parseInt(getindex);
                  System.out.println("index--"+index);
                  result=messages.DELETEMessages(index);

              }*/

              else{
                   result = "{\"error\":\"no Method\"}";
               }
               String httpRes = parse.buildResponse(httpRequest, result);
               out.print(httpRes);

           } catch (Exception e) {

               String httpRes = parse.buildResponse(httpRequest, e.toString());
               out.print(httpRes);

           }
                out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
           try {
               s.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
           //System.out.println("thread out");
       }



    }

}



