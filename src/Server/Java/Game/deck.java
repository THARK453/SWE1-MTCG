package Game;
import Parse.*;
import Server.*;
import Data.*;
import Data.GameData;

import java.sql.ResultSet;

public class deck {
    public static ResultSet selectdeck(int id){
        String sqlselectuser="select * from deck where user_id= ?";

        ResultSet rst= GameData.Getsql(sqlselectuser,id);

        return  rst;
    }

}
