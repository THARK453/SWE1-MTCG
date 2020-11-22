import com.mysql.management.util.Str;

import java.sql.*;
import java.sql.DriverManager;

public class Data {
   private static String url="jdbc:mariadb://localhost:3306/test";
    private static String user="root";
    private static String password="root";

    public static void main(String[] args){


        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection myconect=DriverManager.getConnection(url,user,password);
            Statement mystm=myconect.createStatement();
            String sql="select * from user";
            ResultSet rst=mystm.executeQuery(sql);

            while (rst.next())
            {
               System.out.println(rst.getString("username"));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


    }



}
