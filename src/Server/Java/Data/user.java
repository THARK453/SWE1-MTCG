package Data;
import Cards.*;
import Game.*;
import Parse.*;
import Server.*;
import com.google.gson.JsonObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class user implements SQL{


    public static String Registration(Jsonmsg jsonmsg){

        String msg="\n";
        String name= jsonmsg.getUsername();
        String password= jsonmsg.getPassword();
        String token=jsonmsg.getToken();
        int coin=20;
        int ELO=100;
        if(jsonmsg.getUsername().equals("admin")){
            coin=999999999;
            ELO=999999999;
        }

        int i= GameData.Dosql(user_insert,name,password,token,coin);
        if(i==1){
            ResultSet rstuser=GameData.Getsql(user_selecttoken,token);
            try {
                if(rstuser.next()){
                    int n=GameData.Dosql(stats_insert,rstuser.getInt("id"),ELO,0,0);
                    if(n==1){
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("Message","Registration done username: "+name);
                        msg=msg.concat(jsonObject.toString()+"\n");

                    }else {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("Message","stats insert Error");
                        msg=msg.concat(jsonObject.toString()+"\n");

                    }
                }




            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }else {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Message","Registration Error: "+name);
            msg=msg.concat(jsonObject.toString()+"\n");

        }

        return msg;
    }


    public static String Login(Jsonmsg jsonmsg){
        String msg="\n";
        String username=jsonmsg.getUsername();
        String password=jsonmsg.getPassword();

        int i= GameData.Dosql(user_login,username,password);
        if(i==1){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Message","sign in suceesfully username: "+username);
            msg=msg.concat(jsonObject.toString()+"\n");
        }else {

            msg=msg.concat("\n{\"Message\":\"Error Login failed\"}\n");
        }

        return msg;
    }


    public static int checkRegistration(Jsonmsg jsonmsg){


        String username=jsonmsg.getUsername();
        String password=jsonmsg.getPassword();
        ResultSet rst= GameData.Getsql(user_selectN_P,username,password,username);

        try {
            if(rst.next()){
                return 1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return 0;
    }

    public static int checkadmin(String httpmag){
        String token= parse.gettoken(httpmag);

        if(token.equals("admin-mtcgToken")){

            ResultSet rst= GameData.Getsql(user_selecttoken,token);
            try {
                if(rst.next()){
                    if(rst.getString("status").equals("Loggedin")){
                        return 1;
                    }else {
                        return 2;
                    }
                }else {
                    return 0;
                }


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

        return 0;


    }

    public static String getusers(String httpmsg,String tokenmsg){
        String msg="\n";
        String token= parse.gettoken(tokenmsg);
        String username=httpmsg.split("\\/")[2];


        ResultSet rstuser= GameData.Getsql(user_selecttoken,token);
        try {
            if(rstuser.next() && username.equals(token.split("-")[0])){

                JsonObject jsonObject = new JsonObject();


                jsonObject.addProperty("Id", rstuser.getString("id"));
                jsonObject.addProperty("name", rstuser.getString("username"));
                jsonObject.addProperty("coin", rstuser.getString("coin"));
                jsonObject.addProperty("status", rstuser.getString("status"));
                jsonObject.addProperty("bio", rstuser.getString("bio"));
                jsonObject.addProperty("image", rstuser.getString("image"));
                msg=msg.concat(jsonObject.toString()+"\n");


            }else {

                msg=msg.concat("\n{\"Message\":\"no user or Invalid Token\"}\n");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return msg;
    }

    public static String edituser(String httpmsg, String tokenmsg, Jsonmsg jsonmsg){
        String msg="";
        String token= parse.gettoken(tokenmsg);
        String username=httpmsg.split("\\/")[2];

        String name=jsonmsg.getName();
        String bio=jsonmsg.getBio();
        String image=jsonmsg.getImage();

        ResultSet rstuser= GameData.Getsql(user_selecttoken,token);

        try {
            if(rstuser.next() && username.equals(token.split("-")[0])){
                int id=rstuser.getInt("id");
                int i= GameData.Dosql(user_update_N_B_I,name,bio,image,id);
                if(i==1){
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("Message","User data has been changed  id: "+id);
                    msg=msg.concat(jsonObject.toString()+"\n");

                }else {

                    msg=msg.concat("\n{\"Message\":\"Error\"}\n");
                }
            }else {

                msg=msg.concat("\n{\"Message\":\"no user or Invalid Token\"}\n");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return msg;
    }






}
