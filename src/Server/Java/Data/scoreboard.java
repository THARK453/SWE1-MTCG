package Data;
import Cards.*;
import Game.*;
import Parse.*;
import Server.*;
import com.google.gson.JsonObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class scoreboard implements SQL{

    public static String selectstats(String httpmsg){
         String msg="\n[";

        ResultSet rst= GameData.Getsql(stats_selectELO);

        try {
            if(rst.isBeforeFirst()){
                while (rst.next()){
                    JsonObject jsonObject = new JsonObject();


                    jsonObject.addProperty("user_Id", rst.getString("user_id"));
                    jsonObject.addProperty("ELO", rst.getString("ELO"));
                    jsonObject.addProperty("WIN", rst.getString("win"));
                    jsonObject.addProperty("lose", rst.getString("lose"));
                    msg=msg.concat(jsonObject.toString()+",\n");


                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        msg=parse.replaceLast(msg,",","]");
        return  msg;
    }

}
