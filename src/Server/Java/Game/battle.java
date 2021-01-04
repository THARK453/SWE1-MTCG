package Game;
import Parse.*;
import Server.*;
import Data.*;
import Cards.*;
import smetana.core.__array_of_double__;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class battle {

    public static void createbattlefield(){
        String sql="insert into battlefield (status) values ('Available')";
        GameData.Dosql(sql);
    }

    public static ResultSet selectbattlefield(){
        String sql="SELECT * FROM battlefield Where status ='Available' ORDER BY id LIMIT 1";

        ResultSet rst=GameData.Getsql(sql);

        return rst;
    }

    public static int inbattlethebattlefield(int id){
        String sql="update battlefield set status ='inbattle' where id = ?";
        int i=GameData.Dosql(sql,id);
        return i;
    }

    public static int Availablethebattlefield(int id){
        String sql="update battlefield set status ='Available' where id = ?";
        String sql1="UPDATE userinfor SET battlefield_id=null WHERE battlefield_id= ?";
        int i=GameData.Dosql(sql,id);
        int n=GameData.Dosql(sql1,id);
        return i;
    }


    public static int checkbattlefield(int id){
         String sql="select * from userinfor where battlefield_id= ?";
         ResultSet rst=GameData.Getsql(sql,id);
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
          String sql="select * from userinfor where battlefield_id= ?";
          ResultSet rst=GameData.Getsql(sql,id);

        return rst;
      }

      public static String Startbattle(ResultSet rstuser) {
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
