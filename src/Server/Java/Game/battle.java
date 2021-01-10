package Game;
import Data.*;
import Cards.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class battle implements SQL{

    public static void createbattlefield(){

        GameData.Dosql(battlefield_insert_Available);
    }

    public static ResultSet selectbattlefield(){

        ResultSet rst=GameData.Getsql(battlefield_selectAvailable);

        return rst;
    }

    public static int inbattlethebattlefield(int id){

        int i=GameData.Dosql(battlefield_inbattle,id);
        return i;
    }

    public static int Availablethebattlefield(int id){

        int i=GameData.Dosql(battlefield_Available,id);
        int n=GameData.Dosql(user_outbattlefield,id);
        return i;
    }

    public static int checkbattlefield_id(int id){
        //String sql="select * from userinfor where battlefield_id= ?";
        ResultSet rst=GameData.Getsql(user_selectbattlefield_id,id);
        int i=0;

        try {
            while (rst.next()){
                i=rst.getRow();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return i;
    }


    public static int checkbattlefield(){

         ResultSet rst=GameData.Getsql(battlefield_select);
         int i=0;

            try {
                while (rst.next()){
                    i=rst.getRow();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

         return i;
    }

      public static ResultSet getbattlefielduser(int id){
          //String sql="select * from userinfor where battlefield_id= ?";
          ResultSet rst=GameData.Getsql(user_selectbattlefield_id,id);

        return rst;
      }

      public static String Startbattle(int id0,int id1) {
          String result = "";

          List<Cards> user_0deckcards = deck.createdeckcards(id0);
          List<Cards> user_1deckcards = deck.createdeckcards(id1);

          Fights fights=new Fights(user_0deckcards,user_1deckcards,id0,id1,100);
          result=fights.Startfight();
          /*System.out.println("user0: "+fights.getUser_0id()+" "+fights.getUser0name());
          System.out.println("user1: "+fights.getUser_1id()+" "+fights.getUser1name());*/

              return result;
          }


    public static String Startbattle_rst(ResultSet rstuser) {
        String result = "";
        List<Integer> user_id = new ArrayList();

        try {
            while (rstuser.next()) {
                user_id.add(rstuser.getInt("id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<Cards> user_0deckcards = deck.createdeckcards(user_id.get(0));
        List<Cards> user_1deckcards = deck.createdeckcards(user_id.get(1));

        Fights fights=new Fights(user_0deckcards,user_1deckcards,user_id.get(0),user_id.get(1),100);
        result=fights.Startfight();
          /*System.out.println("user0: "+fights.getUser_0id()+" "+fights.getUser0name());
          System.out.println("user1: "+fights.getUser_1id()+" "+fights.getUser1name());*/

        return result;
    }
}
