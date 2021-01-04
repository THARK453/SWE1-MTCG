package Game;
import Cards.*;
import Parse.*;
import Data.GameData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class deck {

    public static ResultSet selectdeck(int id){
        String sql="select * from deck where user_id= ?";
        ResultSet rst= GameData.Getsql(sql,id);
        return  rst;
    }


     public static JsonArray setcardsmsg(ResultSet rst){
             String sql="select * from cards where id= ?";
             JsonArray jsonArray = new JsonArray();
             try {

                     while (rst.next()){
                         ResultSet rstcards=GameData.Getsql(sql,rst.getString("card_id"));
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


}
