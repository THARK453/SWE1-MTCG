package Data;
import Cards.*;
import Game.*;
import Parse.*;
import Server.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class trade implements SQL{

    public static String gettrade(String httpmsg){
        String msg="\n[";


        String username=parse.gettoken(httpmsg).split("-")[0];

        ResultSet rst=GameData.Getsql(trade_select);
        JsonObject jsonObject0 = new JsonObject();
        jsonObject0.addProperty("Message: ",username+" trade infor");
        msg=msg.concat(jsonObject0.toString()+",\n");

        try {
        if(rst.isBeforeFirst()) {


            while (rst.next()) {


                JsonObject jsonObject = new JsonObject();

                jsonObject.addProperty("Id", rst.getString("id"));
                jsonObject.addProperty("user_Id", rst.getString("user_id"));
                jsonObject.addProperty("username", rst.getString("username"));
                jsonObject.addProperty("card", rst.getString("card_id"));
                jsonObject.addProperty("type", rst.getString("type"));
                jsonObject.addProperty("MinDamage", rst.getString("MinimumDamage"));
                msg=msg.concat(jsonObject.toString()+",\n");

            }
        }else {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Message","trade is empty");
            msg=msg.concat(jsonObject.toString()+",\n");
            return msg;
        }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        msg=parse.replaceLast(msg,",","]");
        return msg;
    }


   public static String addtrade(Jsonmsg jsonmsg,String httpmsg){
        String msg="\n";

        String token=parse.gettoken(httpmsg);
        ResultSet rstuser=GameData.Getsql(user_selecttoken,token);

       try {
           if (rstuser.next()){
               int n=GameData.Dosql(trade_insert,jsonmsg.getId(),rstuser.getInt("id"),rstuser.getString("username"),
                       jsonmsg.getCardToTrade(),jsonmsg.getType(),Float.parseFloat(jsonmsg.getMinimumDamage()));
           if(n==1) {
               JsonObject jsonObject0 = new JsonObject();
               jsonObject0.addProperty("Message: ", " trade create: " + rstuser.getString("username"));
               msg = msg.concat(jsonObject0.toString() + "\n");
           }else {
               JsonObject jsonObject = new JsonObject();
               jsonObject.addProperty("Message","insert Error or already had this trade");
               msg=msg.concat(jsonObject.toString()+ "\n");
               return msg;
           }
           }else {
               JsonObject jsonObject = new JsonObject();
               jsonObject.addProperty("Message","no user or Invalid Token");
               msg=msg.concat(jsonObject.toString()+ "\n");
               return msg;
           }



       } catch (SQLException throwables) {
           throwables.printStackTrace();
       }
       return msg;
   }


   public  static String deletetrading(String urlmsg,String httpmsg){
            String msg="\n";
            String token=parse.gettoken(httpmsg);

            ResultSet rstuser=GameData.Getsql(user_selecttoken,token);

            String tradeid=urlmsg.split("/")[2];

       try {
           if(rstuser.next()){
            int n=GameData.Dosql(trade_delete,tradeid,rstuser.getInt("id"));
            if(n==1){
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("Message","delete success: "+tradeid);
                msg=msg.concat(jsonObject.toString()+"\n");
            }else {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("Message","delete Error: "+tradeid);
                msg=msg.concat(jsonObject.toString()+"\n");
                return msg;
            }


           }else {
               JsonObject jsonObject = new JsonObject();
               jsonObject.addProperty("Message","no user or Invalid Token");
               msg=msg.concat(jsonObject.toString()+"\n");
               return msg;
           }


       } catch (SQLException throwables) {
           throwables.printStackTrace();
       }

       return msg;
   }


   public static  String dotrade(String urlmsg,String httpmsg,String bodymsg){
       String msg="\n";
       String tradeid=urlmsg.split("/")[2];
       String token=parse.gettoken(httpmsg);
       String tradcardid=bodymsg.replaceAll("\"","");
       ResultSet rstuser=GameData.Getsql(user_selecttoken,token);

       try {
           if(rstuser.next()){
            ResultSet rsttrade=GameData.Getsql(trade_selectid,tradeid);
            if(rsttrade.next()){
                if(rsttrade.getInt("user_id")==rstuser.getInt("id")){
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("Message","can't trade with yourself");
                    msg=msg.concat(jsonObject.toString()+"\n");
                    return msg;
                }else {
                  int n=GameData.Dosql(stack_updateuserid,rstuser.getInt("id"),rsttrade.getString("card_id"));
                  int i=GameData.Dosql(stack_updateuserid,rsttrade.getInt("user_id"),tradcardid);

                  if(n==1&&i==1){
                      JsonObject jsonObject = new JsonObject();
                      jsonObject.addProperty("Message","trade success");
                      int x=GameData.Dosql(trade_delete,tradeid,rsttrade.getInt("user_id"));

                      msg=msg.concat(jsonObject.toString()+"\n");
                  }else {
                      JsonObject jsonObject = new JsonObject();
                      jsonObject.addProperty("Message","trade Error");
                      msg=msg.concat(jsonObject.toString()+"\n");

                  }
                }



            }else {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("Message","no trade infor");
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


       return msg;
   }

}
