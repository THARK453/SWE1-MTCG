package Data;
import Cards.*;
import Game.*;
import Parse.*;
import Server.*;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class Datasql implements SQL{



   public static String showacquired(String httpmsg){
        String msg="[";
        String token= parse.gettoken(httpmsg);


        ResultSet rst= GameData.Getsql(user_selecttoken,token);

       try {
           if(rst.next()){
               ResultSet rststack= GameData.Getsql(stack_selectuserid,rst.getInt("id"));
               JsonObject jsonObject0 = new JsonObject();
               jsonObject0.addProperty("Message", "acquired cards on: "+rst.getString("username"));
               msg=msg.concat(jsonObject0.toString()+",\n");


               while (rststack.next()){
                   ResultSet rstcards= GameData.Getsql(cards_selectid,rststack.getString("card_id"));
                   while (rstcards.next()){


                       JsonObject jsonObject = new JsonObject();

                       jsonObject.addProperty("Id", rstcards.getString("id"));
                       jsonObject.addProperty("name", rstcards.getString("name"));
                       jsonObject.addProperty("Damage", rstcards.getString("damage"));
                       jsonObject.addProperty("packageid", rstcards.getString("package_id"));

                       msg = msg.concat(jsonObject.toString() + ",\n");



                   }

               }

           }else {

               msg=msg.concat("\n{\"Message\":\"Error Invalid Token\"}\n");
           }


       } catch (SQLException throwables) {
           throwables.printStackTrace();
       }

       msg=parse.replaceLast(msg,",","]");
       return msg;
   }





     public static String inbattle(String httpmsg){
           String msg="\n";
           String token= parse.gettoken(httpmsg);
           ResultSet rstuser=GameData.Getsql(user_selecttoken,token);


           ResultSet rst=battle.selectbattlefield();


         try {
            if(rstuser.next()){

                if(rst.next()){
                    int i=GameData.Dosql(user_inbattle,rst.getInt("id"),rstuser.getInt("id"));
                    if(i==1){
                        msg=msg.concat("\n\nuser: "+rstuser.getString("username")+" in battlefield : "+rst.getInt("id"));
                        int check=battle.checkbattlefield(rst.getInt("id"));
                        if(check>=2){
                            int n=battle.inbattlethebattlefield(rst.getInt("id"));
                            ResultSet rstbattle=battle.getbattlefielduser(rst.getInt("id"));
                             msg=msg.concat(battle.Startbattle(rstbattle));
                             battle.Availablethebattlefield(rst.getInt("id"));
                        }

                    }else {

                        msg=msg.concat("\n{\"Message\":\"Error\"}\n");
                    }

                }else {
                    battle.createbattlefield();
                    ResultSet rstbattlefield=battle.selectbattlefield();
                    if(rstbattlefield.next()){
                        int i=GameData.Dosql(user_inbattle,rstbattlefield.getInt("id"),rstuser.getInt("id"));
                        if(i==1){
                            msg=msg.concat("\n\nuser: "+rstuser.getString("username")+" in battlefield : "+rstbattlefield.getInt("id"));
                            int check=battle.checkbattlefield(rstbattlefield.getInt("id"));
                            if(check>=2){
                                int n=battle.inbattlethebattlefield(rstbattlefield.getInt("id"));
                            }

                        }else {
                            msg=msg.concat("\n{\"Message\":\"Error\"}\n");
                        }
                    }else {
                        msg=msg.concat("\n{\"Message\":\"Error\"}\n");
                    }

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
