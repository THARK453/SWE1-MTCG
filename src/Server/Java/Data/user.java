package Data;
import Cards.*;
import Game.*;
import Parse.*;
import Server.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class user implements SQL{


    public static String Registration(Jsonmsg jsonmsg){

        String msg="";
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
                        msg="\n\nRegistration done username: "+jsonmsg.getUsername();
                    }else {
                        msg=msg.concat("\n\nstats insert Error");
                    }
                }




            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }else {
            msg="\n\nRegistration not done username: "+jsonmsg.getUsername();
        }

        return msg;
    }


    public static String Login(Jsonmsg jsonmsg){
        String msg="";
        String username=jsonmsg.getUsername();
        String password=jsonmsg.getPassword();

        int i= GameData.Dosql(user_login,username,password);
        if(i==1){
            msg=msg.concat("\n\nsign in suceesfully username: "+username);
        }else {
            msg=msg.concat("\n\nError Login failed");
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
        String msg="";
        String token= parse.gettoken(tokenmsg);
        String username=httpmsg.split("\\/")[2];


        ResultSet rstuser= GameData.Getsql(user_selecttoken,token);
        try {
            if(rstuser.next() && username.equals(token.split("-")[0])){
                msg=msg.concat(String.format("\n\nID: %d  name: %s  coin: %d  status: %s  bio: %s image: %s\n",
                        rstuser.getInt("id"),rstuser.getString("username"),rstuser.getInt("coin")
                        ,rstuser.getString("status"),rstuser.getString("bio"),rstuser.getString("image")  ));
            }else {
                msg=msg.concat("\n\nno user or Invalid Token");
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
                    msg=msg.concat("\n\nUser data has been changed  id: "+id);
                }else {
                    msg=msg.concat("\n\nError");
                }
            }else {
                msg=msg.concat("\n\nno user or Invalid Token");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return msg;
    }






}
