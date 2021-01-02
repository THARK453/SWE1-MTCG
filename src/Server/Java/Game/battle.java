package Game;
import Parse.*;
import Server.*;
import Data.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class battle {

    public static void createbattlefield(){
        String sql="insert into battlefield (status) values ('Available')";
        GameData.Getsql(sql);
    }

    public static ResultSet selectbattlefield(){
        String sql="SELECT * FROM battlefield Where status ='Available' ORDER BY id LIMIT 1";

        ResultSet rst=GameData.Getsql(sql);

        return rst;
    }

    public static int Startthebattlefield(int id){
        String sql="update battlefield set status ='inbattle' where id = ?";
        int i=GameData.Dosql(sql,id);
        return i;
    }

    public static int Closethebattlefield(int id){
        String sql="update battlefield set status ='Available' where id = ?";
        int i=GameData.Dosql(sql,id);
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
        System.out.println("\n battletest: ->"+i);
         return i;
    }



}
