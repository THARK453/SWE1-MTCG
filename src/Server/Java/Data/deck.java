package Data;
import Cards.*;
import Game.*;
import Parse.*;
import Server.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class deck implements SQL {

    public static ResultSet selectdeck(int id){

        ResultSet rst= GameData.Getsql(deck_selectuserid,id);
        return  rst;
    }


     public static JsonArray setcardsmsg(ResultSet rst){

             JsonArray jsonArray = new JsonArray();
             try {

                     while (rst.next()){
                         ResultSet rstcards=GameData.Getsql(cards_selectid,rst.getString("card_id"));
                         if(rstcards.next()){
                             JsonObject jsonObject=new JsonObject();

                             jsonObject.addProperty("Id",rstcards.getString("id"));
                             jsonObject.addProperty("Name",rstcards.getString("name"));
                             jsonObject.addProperty("Damage",rstcards.getString("damage"));
                             jsonArray.add(jsonObject);
                         }
                     }


             } catch (SQLException throwables) {
                 throwables.printStackTrace();
             }
        return jsonArray;
     }

    public static String testjson(ResultSet rst){
        String msg="[";
        JsonObject jsonObject0=new JsonObject();
       jsonObject0.addProperty("Message: "," user deck");
        msg=msg.concat(jsonObject0.toString()+",\n\n");
        JsonArray jsonArray = new JsonArray();
        try {

            while (rst.next()){
                ResultSet rstcards=GameData.Getsql(cards_selectid,rst.getString("card_id"));
                if(rstcards.next()){
                    JsonObject jsonObject=new JsonObject();

                    jsonObject.addProperty("Id",rstcards.getString("id"));
                    jsonObject.addProperty("Name",rstcards.getString("name"));
                    jsonObject.addProperty("Damage",rstcards.getString("damage"));
                    jsonArray.add(jsonObject);
                    msg=msg.concat(jsonObject.toString()+",\n");
                }
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        msg=parse.replaceLast(msg,",","]");
        return msg;
    }

     public static List<Cards> createdeckcards(int id){
          ResultSet rstdeck=selectdeck(id);
         Gson gson=new Gson();
         Type t = new TypeToken<List<Cards>>() {}.getType();

                 JsonArray jsonArray=setcardsmsg(rstdeck);
                 List<Cards> cards = gson.fromJson(jsonArray, t);
                 cards=parse.parsecards(cards);
                 return cards;


     }

    public static void printdeckcards(List<Cards> cardsList){
        System.out.println("\n\n");
        for(int n=0;n<cardsList.size();n++){

            System.out.println(" id: "+cardsList.get(n).getId()+" name: "+cardsList.get(n).getName()+" damage: "
                    +cardsList.get(n).getDamage()+" type: "+cardsList.get(n).getType());
            if(cardsList.get(n).isMonster()){
                System.out.println(" is a Monster");
            }else {
                System.out.println(" is a Spell");
            }


        }
        System.out.println("\n\n");
    }



    public static String showdeck(String urlmsg,String tokenmsg){
        String msg="";
        String formatmsg="";


        String token= parse.gettoken(tokenmsg);


        if(!urlmsg.equals("/deck")){
            formatmsg=urlmsg.split("\\?")[1];
            msg=msg.concat("\n\nformat test: "+formatmsg+" url: "+urlmsg);

        }else {
            ResultSet rst= GameData.Getsql(user_selecttoken,token);

            try {

                if(rst.next()){

                    ResultSet rstdeck= GameData.Getsql(deck_selectuserid,rst.getInt("id"));
                    msg=msg.concat("\n\n"+rst.getString("username")+" deck: \n");

                    if(rstdeck.isBeforeFirst()){

                        while (rstdeck.next()){
                            ResultSet rstcards= GameData.Getsql(cards_selectid,rstdeck.getString("card_id"));
                            while (rstcards.next()){
                                msg=msg.concat(String.format("\n\nID: %s  name: %s  Damage: %.2f  packageid: %d\n",
                                        rstcards.getString("id"),rstcards.getString("name"),rstcards.getFloat("damage"),rstcards.getInt("package_id")));
                            }

                        }
                    }else {
                        msg=msg.concat("Is empty");
                    }



                }else {
                    msg=msg.concat("\n\nno user or Invalid Token");
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        }


        return msg;
    }



    public static String configuredeck(List<String> messageList,String httpmsg){
        String msg="";
        String token= parse.gettoken(httpmsg);



        ResultSet rstuser= GameData.Getsql(user_selecttoken,token);

        try {
            if(rstuser.next()){
                ResultSet rstcheckuser= GameData.Getsql(deck_selectuserid,rstuser.getInt("id"));


                if(rstcheckuser.next()){

                    msg=msg.concat("\n\nUser already configure deck ");
                }else {

                    for(int i=0;i<messageList.size();i++){
                        int n= GameData.Dosql(deck_insert,messageList.get(i),rstuser.getInt("id"));
                    }
                    msg=msg.concat("\n\nconfigure deck user: "+rstuser.getString("username"));
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
