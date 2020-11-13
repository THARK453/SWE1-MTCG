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



          // System.out.println("thread start");
         Request httpRequest = parse.parse2request(s.getInputStream());

           System.out.println(httpRequest.getMethod()+" "+httpRequest.getUrl()+" "+httpRequest.getVersion()+"\n");
           System.out.println(httpRequest.getHeaders()+"\n");

           PrintStream out = new PrintStream(s.getOutputStream());



          /* PrintStream out = new PrintStream(s.getOutputStream());

           String mes = "this is a test!->"+"method->"+httpRequest.getMethod()+"url->"+httpRequest.getUrl()+"version->"+httpRequest.getVersion();
           out.println("HTTP/1.0 200 OK");
           out.println("MIME_version:1.0");
           out.println("Content_Type:text/html");
           int len = mes.getBytes().length;
           out.println("Content_Length:"+len);
           out.println("");
           out.println("this is a test!->");
           out.println("method->"+httpRequest.getMethod());
           out.println("url->"+httpRequest.getUrl());
           out.println("version->"+httpRequest.getVersion());
           out.flush();*/

           try {
               String result ="";
               //最最最简单的实现方案：通过if判断找到方法
              if(httpRequest.getMethod().equals("GET") && httpRequest.getUrl().equals("/messages")){
                  System.out.println("showmessages");
                  System.out.println(httpRequest.getUrl());
                   result = messages.showMessages();
               }else if(httpRequest.getMethod().equals("POST") && httpRequest.getUrl().equals("/messages")){
                  System.out.println("addMessages");
                  System.out.println(httpRequest.getUrl());
                   result = messages.addMessages(httpRequest.getMessage());
               }else if(httpRequest.getMethod().equals("GET") && httpRequest.getUrl()!="/messages"){
                  System.out.println(httpRequest.getUrl());
                  System.out.println("SelectMessages");
                  String getindex=httpRequest.getUrl().replaceAll("messages","");
                  getindex=getindex.replace("/","");
                  System.out.println(getindex);
                  int index=Integer.parseInt(getindex);
                  System.out.println("index--"+index);
                  result=messages.SelectMessages(index);
              }else if(httpRequest.getMethod().equals("PUT") ){
                  System.out.println(httpRequest.getUrl());
                  System.out.println("updateMessages");
                  String getindex=httpRequest.getUrl().replaceAll("messages","");
                  getindex=getindex.replace("/","");
                  System.out.println(getindex);
                  int index=Integer.parseInt(getindex);
                  System.out.println("index--"+index);
                  result=messages.updateMessages(index,httpRequest.getMessage());
              }else if(httpRequest.getMethod().equals("DELETE") ){
                  System.out.println(httpRequest.getUrl());
                  System.out.println("updateMessages");
                  String getindex=httpRequest.getUrl().replaceAll("messages","");
                  getindex=getindex.replace("/","");
                  System.out.println(getindex);
                  int index=Integer.parseInt(getindex);
                  System.out.println("index--"+index);
                  result=messages.DELETEMessages(index);
              }
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
