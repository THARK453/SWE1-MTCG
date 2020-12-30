import com.mysql.management.util.Str;

import java.sql.*;
import java.sql.DriverManager;

public class Data {
   private static String url="jdbc:postgresql://localhost:5432/MTCG";
    private static String user="postgres";
    private static String password="root";

    public static void main(String[] args){


        try {
            Class.forName("org.postgresql.Driver");
            Connection myconect=DriverManager.getConnection(url,user,password);
            Statement mystm=myconect.createStatement();
            String sql="select * from userinfor";
            ResultSet rst=mystm.executeQuery(sql);

            while (rst.next())
            {
               System.out.println(rst.getString("username")+" "+
                       rst.getString("userpassword"));
               if(rst.getString("username").equals("user")){
                   System.out.println("true");
               }else {
                   System.out.println("Flase");
               }

            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


    }



}
