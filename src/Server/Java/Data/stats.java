package Data;
import Cards.*;
import Game.*;
import Parse.*;
import Server.*;
import com.google.gson.JsonObject;


import java.sql.ResultSet;
import java.sql.SQLException;

public class stats implements SQL{

    public static int parsedamage(int damage){
        int x=damage/10;
        int y=damage%10;
        if (y==0){
            y=10;
        }
     return x+y;
    }

    public static void resetELO(int id){

        GameData.Dosql(stats_setELO100_id,id);
    }

    public static int pulsELO(int id,int damage){


          int i= GameData.Dosql(stats_updateELO_id,damage,id);
      return i;
    }

    public static ResultSet getstats(int id){

        ResultSet rst=GameData.Getsql(stats_selectuserid,id);
        return rst;
    }



    public static int DeductionELO(int id,int damage){

        int i=GameData.Dosql(stats_updateELO_id,-damage,id);
        ResultSet rst=getstats(id);
        try {
            if(rst.next()&&rst.getInt("ELO")<=0){
                return -1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return i;
    }

    public static void winpuls(int id){

        int i=GameData.Dosql(stats_winpuls,id);
        System.out.println(i);
    }

    public static void losepuls(int id){

        int i=GameData.Dosql(stats_losepuls,id);
        System.out.println(i);
    }

    public static String showstats(String httpmsg){
        String msg="";
        String token=parse.gettoken(httpmsg);

        ResultSet rstuser=GameData.Getsql(user_selecttoken,token);
        try {
            if(rstuser.next()){
                ResultSet rststats=GameData.Getsql(stats_selectuserid,rstuser.getInt("id"));
                if(rststats.next()){
                    JsonObject jsonObject = new JsonObject();


                    jsonObject.addProperty("user_Id", rststats.getString("user_id"));
                    jsonObject.addProperty("username", rstuser.getString("username"));
                    jsonObject.addProperty("ELO", rststats.getString("ELO"));
                    jsonObject.addProperty("WIN", rststats.getString("win"));
                    jsonObject.addProperty("lose", rststats.getString("lose"));
                    msg=msg.concat(jsonObject.toString()+",\n");

                }else {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("Message","no stats infor");
                    msg=msg.concat(jsonObject.toString()+"\n");
                }
            }else {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("Message","no user or Invalid Token");
                msg=msg.concat(jsonObject.toString()+"\n");
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        msg=parse.replaceLast(msg,",","");
        return msg;
    }
}
