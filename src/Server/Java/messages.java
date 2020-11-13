

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class messages {

    private static List<String> messageList = new ArrayList<String>();

    private static String path="C:\\BIF\\WS2020\\SWE\\GIT MTCG\\SWE1-MTCG\\src\\Server\\message\\message.txt";

    public static String showMessages() throws IOException {
        //StringBuilder str = new StringBuilder();
        String msg="";
        File file=new File(path);

            Scanner scan=new Scanner(file);
            int index=1;
        while (scan.hasNextLine()){
            msg=msg.concat(" " + index + " " + scan.nextLine() + "\n");
            index++;
        }

       /* if(messageList.size() != 0) {
            for (int index = 0; index < messageList.size(); index++) {
                str.append(" " + index + " " + messageList.get(index) + "\n");
                //str.append(messageList.get(index)+"\n");
            }
            str.deleteCharAt(str.length() - 1);
        }else {
            return "no message";
        }*/
       // str.append("\n}");

        return msg;
    }


    public static String addMessages(String message) throws IOException{
        //String [] datas = message.split(":");
        //String data = datas[1].replaceAll("\"","");
       // String data=message;

        //data = data.replace("{","");
        //data = data.replaceAll("}","");
       // messageList.add(data);
        //FileWriter writer=new FileWriter(path,true);
         BufferedWriter writer=new BufferedWriter(new FileWriter(path,true));
        File file=new File(path);
        Scanner scan=new Scanner(file);

        if (scan.hasNextLine()){
            writer.newLine();
            writer.append(message);
        }else
       writer.append(message);
       writer.close();
        System.out.println(message);
        return "Messages Added";
    }

    public static String SelectMessages(int index) throws IOException{
        StringBuilder str = new StringBuilder();
        String msg = null;
        File file=new File(path);

        Scanner scan=new Scanner(file);


        for(int i=0;i<index;i++,scan.hasNextLine()){
            msg=(" " + index + " " + scan.nextLine() + "\n");

        }
        /* if(messageList.size() != 0) 

            str.append(" " + index + " " + messageList.get(index) + "\n");

        }else {
            return "no message";
        }*/
        return msg;
    }

    public static String updateMessages(int index,String message) throws IOException{
       String msg=null;
       String tmsg="";

        File file=new File(path);
        Scanner scan=new Scanner(file);
        int i=1;
        while (scan.hasNextLine()){
            if(i==index){
                msg=scan.nextLine();
                tmsg=tmsg.concat(msg + "\n");
                i++;
            }else {

                tmsg=tmsg.concat(scan.nextLine() + "\n");
                i++;
            }

        }


        tmsg=tmsg.replaceAll(msg,message);
        FileWriter writer=new FileWriter(path);
        writer.write(tmsg);
        writer.close();
        System.out.println("--"+msg);
        System.out.println("##"+tmsg);
        return "updateMessages";

    }

    public static String DELETEMessages(int index) throws IOException{
        String msg=null;
        String tmsg="";

        File file=new File(path);
        Scanner scan=new Scanner(file);
        int i=1;
        while (scan.hasNextLine()){
            if(i==index){
                msg=scan.nextLine();
                //tmsg=tmsg.concat(msg + "\n");
                i++;
            }else {

                tmsg=tmsg.concat(scan.nextLine() + "\n");
                i++;
            }

        }



        FileWriter writer=new FileWriter(path);
        writer.write(tmsg);
        writer.close();
        System.out.println("--"+msg);
        System.out.println("##"+tmsg);
        return msg+" ->DELETE";

    }

}
