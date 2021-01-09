package Data;
import Cards.*;
import Game.*;
import Parse.*;
import Server.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class Datasql implements SQL{



   public static String showacquired(String httpmsg){
        String msg="";
        String token= parse.gettoken(httpmsg);


        ResultSet rst= GameData.Getsql(user_selecttoken,token);

       try {
           if(rst.next()){
               ResultSet rststack= GameData.Getsql(stack_selectuserid,rst.getInt("id"));
               msg=msg.concat("\n\nacquired cards on: "+rst.getString("username"));
               while (rststack.next()){
                   ResultSet rstcards= GameData.Getsql(cards_selectid,rststack.getInt("card_id"));
                   while (rstcards.next()){
                       msg=msg.concat(String.format("\n\nID: %s  name: %s  Damage: %.2f  packageid: %d\n",
                               rstcards.getString("id"),rstcards.getString("name"),rstcards.getFloat("damage"),rstcards.getInt("package_id")));
                   }

               }

           }else {
               msg=msg.concat("\n\nError Invalid Token");
           }


       } catch (SQLException throwables) {
           throwables.printStackTrace();
       }


       return msg;
   }





     public static String inbattle(String httpmsg){
           String msg="";
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
                        msg=msg.concat("\n\nError");
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
                            msg=msg.concat("\n\nError");
                        }
                    }else {
                        msg=msg.concat("\n\nError");
                    }

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
