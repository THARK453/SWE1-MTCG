package Data;

import java.sql.ResultSet;

public class user {

    public static ResultSet selectuserbytoken(String msgtoken){
        String sqlselectuser="select * from userinfor where basictoken= ?";

        ResultSet rst=GameData.Getsql(sqlselectuser,msgtoken);

        return  rst;
    }

    public static ResultSet selectuserbyID(int id){
        String sqlselectuser="select * from userinfor where id= ?";

        ResultSet rst=GameData.Getsql(sqlselectuser,id);

        return  rst;
    }

}
