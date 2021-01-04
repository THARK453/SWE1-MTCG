package Game;

import Data.GameData;

import java.sql.ResultSet;
import java.sql.SQLException;

public class stats {

    public static int parsedamage(int damage){
        int x=damage/10;
        int y=damage%10;
        if (y==0){
            y=10;
        }
     return x+y;
    }

    public static void resetELO(int id){
        String sqlpuls="UPDATE stats SET \"ELO\"=100 WHERE user_id=?";
        GameData.Dosql(sqlpuls,id);
    }

    public static int pulsELO(int id,int damage){
          String sqlpuls="UPDATE stats SET \"ELO\"=\"ELO\"+? WHERE user_id=?";

          int i= GameData.Dosql(sqlpuls,damage,id);
      return i;
    }

    public static ResultSet getstats(int id){
        String sqlget="SELECT * FROM stats WHERE user_id=?";
        ResultSet rst=GameData.Getsql(sqlget,id);
        return rst;
    }

    public static int DeductionELO(int id,int damage){
        String sqlDeduction="UPDATE stats SET \"ELO\"=\"ELO\"-? WHERE user_id=?";
        int i=GameData.Dosql(sqlDeduction,damage,id);
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
        String sql="UPDATE stats SET win=win+1 WHERE user_id=?";
        int i=GameData.Dosql(sql,id);
        System.out.println(i);
    }

    public static void losepuls(int id){
        String sql="UPDATE stats SET lose=lose+1 WHERE user_id=?";
        int i=GameData.Dosql(sql,id);
        System.out.println(i);
    }
}
