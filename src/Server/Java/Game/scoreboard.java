package Game;
import Parse.*;
import Server.*;
import Data.*;

import java.sql.ResultSet;

public class scoreboard {

    public static ResultSet selectstats(int id){
        String sqlselectuser="select * from stats where \"ELO\"<=100 ORDER BY  \"ELO\" DESC";

        ResultSet rst= GameData.Getsql(sqlselectuser,id);

        return  rst;
    }

}
