
package Server;
import Parse.*;
import Data.*;
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
           /*Data.Data.GameData gameData=new Data.Data.GameData();
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



        if(httpRequest.getMessage()!=null){
            if (httpRequest.getMessage().substring(0,1).equals("[")){
                httpRequest.setBodytype("Arrayjson");
            }else {
                httpRequest.setBodytype("json");
            }
        }


           System.out.println("Token: \n"+httpRequest.getHeaders().containsKey("Authorization"));
           System.out.println(httpRequest.getHeaders().get("Authorization"));
           if(httpRequest.getHeaders().containsKey("Authorization")){
               System.out.println("\n"+parse.gettoken(httpRequest.getHeaders().get("Authorization")));
           }





          PrintStream out = new PrintStream(s.getOutputStream());

           try {
               String result ="";
               if(httpRequest.getMethod().equals("POST") && httpRequest.getUrl().equals("/users")){

                       Jsonmsg jsonmsg=parse.getjson(httpRequest.getMessage());
                       jsonmsg.setToken(jsonmsg.getUsername()+"-mtcgToken");
                       int i=Datasql.checkRegistration(jsonmsg);

                       if(i==1){
                           result="\nuser "+jsonmsg.getUsername()+" already exists";
                       }else {
                           result=Datasql.Registration(jsonmsg);
                       }


                   //result="\nRegistration done username: "+jsonmsg.getUsername()+" password: "+jsonmsg.getPassword();

               }

               else if(httpRequest.getMethod().equals("POST") && httpRequest.getUrl().equals("/sessions")){
                      Jsonmsg jsonmsg=parse.getjson(httpRequest.getMessage());
                      result=Datasql.Login(jsonmsg);
               }

               else if (httpRequest.getMethod().equals("POST") && httpRequest.getUrl().equals("/packages")){
                   int check=Datasql.checkadmin(httpRequest.getHeaders().get("Authorization"));
                   if(check==1){
                       //List<Jsonmsg> jsonmsgList=parse.getjsonlist(httpRequest.getMessage());
                       /*for(int n=0;n<jsonmsgList.size();n++){
                           result=result.concat("\nID: "+jsonmsgList.get(n).getId()+" name: "+jsonmsgList.get(n).getName()
                                   +" Damage: "+jsonmsgList.get(n).getDamage()+"\n");
                       }*/
                       //result=Datasql.createpackages(jsonmsgList);
                   }else if(check==2){
                       result="\nadmin Not logged in";
                   }else {

                       result="\nError Invalid Token or No admin found";
                   }


                   //result=Data.Data.Datasql.createpackages(jsonmsgList);


               }

               else if (httpRequest.getMethod().equals("POST") && httpRequest.getUrl().equals("/transactions/packages")){
                   if(httpRequest.getHeaders().containsKey("Authorization")){
                       result=Datasql.acquirepackages(httpRequest.getHeaders().get("Authorization"));
                   }else {
                       result="\nno token";
                   }

               }

               else if(httpRequest.getMethod().equals("GET") && httpRequest.getUrl().equals("/cards")){
                   if(httpRequest.getHeaders().containsKey("Authorization")){
                       result=Datasql.showacquired(httpRequest.getHeaders().get("Authorization"));
                   }else {
                       result="\nno token";
                   }

               }

               else if(httpRequest.getMethod().equals("GET") && httpRequest.getUrl().startsWith("/deck")){
                          result=Datasql.showdeck(httpRequest.getUrl(),httpRequest.getHeaders().get("Authorization"));
               }

               else if (httpRequest.getMethod().equals("PUT") && httpRequest.getUrl().equals("/deck")){
                   List<String> messageList=parse.getnokeyjsonlist(httpRequest.getMessage());
                   if(messageList.size()==4){
                       result=Datasql.configuredeck(messageList,httpRequest.getHeaders().get("Authorization"));
                   }else {
                       result=result.concat("\nInvalid number of cards");
                   }

               }

               else if(httpRequest.getMethod().equals("GET") && httpRequest.getUrl().startsWith("/users")){
                                 result=Datasql.getusers(httpRequest.getUrl(),httpRequest.getHeaders().get("Authorization"));
               }

               else if(httpRequest.getMethod().equals("PUT") && httpRequest.getUrl().startsWith("/users")){
                   Jsonmsg jsonmsg=parse.getjson(httpRequest.getMessage());
                   result=Datasql.edituser(httpRequest.getUrl(),httpRequest.getHeaders().get("Authorization"),jsonmsg);
               }

               else if(httpRequest.getMethod().equals("POST") && httpRequest.getUrl().equals("/battles")){
                        result=Datasql.inbattle(httpRequest.getHeaders().get("Authorization"));
               }

             /* if(httpRequest.getMethod().equals("GET") && httpRequest.getUrl().equals("/Parse.messages")){
                  System.out.println("showmessages");
                  System.out.println(httpRequest.getUrl());
                   result = Parse.messages.showMessages();
                  System.out.println(result);

               }else if(httpRequest.getMethod().equals("POST") && httpRequest.getUrl().equals("/Parse.messages")){
                  // System.out.println("addMessages");
                  System.out.println(httpRequest.getUrl());
                   result = Parse.messages.addMessages(httpRequest.getMessage());

               }else if(httpRequest.getMethod().equals("GET") && httpRequest.getUrl()!="/Parse.messages"){
                  System.out.println(httpRequest.getUrl());
                  // System.out.println("SelectMessages");
                  String getindex=httpRequest.getUrl().replaceAll("Parse.messages","");
                  getindex=getindex.replace("/","");
                  System.out.println(getindex);
                  int index=Integer.parseInt(getindex);
                  System.out.println("index--"+index);
                  result=Parse.messages.SelectMessages(index);

              }else if(httpRequest.getMethod().equals("PUT") ){
                  System.out.println(httpRequest.getUrl());
                  // System.out.println("updateMessages");
                  String getindex=httpRequest.getUrl().replaceAll("Parse.messages","");
                  getindex=getindex.replace("/","");
                  System.out.println(getindex);
                  int index=Integer.parseInt(getindex);
                  System.out.println("index--"+index);
                  result=Parse.messages.updateMessages(index,httpRequest.getMessage());

              }else if(httpRequest.getMethod().equals("DELETE") ){
                  System.out.println(httpRequest.getUrl());

                  String getindex=httpRequest.getUrl().replaceAll("Parse.messages","");
                  getindex=getindex.replace("/","");
                  System.out.println(getindex);
                  int index=Integer.parseInt(getindex);
                  System.out.println("index--"+index);
                  result=Parse.messages.DELETEMessages(index);

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


