package Data;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import Parse.ResultSetPrinter;

import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
           // String sql="INSERT INTO userinfor  values(2, 'user2','test2')";
            ResultSet rs=mystm.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int columnsNumber = md.getColumnCount();
            List list = new ArrayList();

            while (rs.next()) {
                list.add(rs.getInt("id"));
            }
            Map rowData = new HashMap();//声明Map
            for (int i = 1; i <= columnsNumber; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));//获取键名及值
            }
            System.out.println(list.get(0));



           // ResultSetPrinter.printResultSet(rst);
          /* int i=0;

            JsonArray jsonArray = new JsonArray();

            while (rst.next()){
                i=rst.getRow();
              JsonObject jsonObject=new JsonObject();
              jsonObject.addProperty("Id",rst.getString("id"));
              jsonObject.addProperty("Name",rst.getString("name"));
              jsonObject.addProperty("Damage",rst.getString("damage"));
              jsonArray.add(jsonObject);
            }
            System.out.println(jsonArray);
            System.out.println("test: "+i);*/

           /* for (int i = 1; i <= columnsNumber; i++) {
                System.out.print(rsmd.getColumnName(i)+"  ");
            }
            System.out.println("\n");
            while (rst.next())
            {

                for (int i = 1; i <= columnsNumber; i++) {

                    String columnValue = rst.getString(i);
                    System.out.print(columnValue + "   " );
                }

               System.out.println(rst.getString("name"));
               if(rst.getString("username").equals("user")){
                   System.out.println("true");
               }else {
                   System.out.println("Flase");
               }

            }*/





        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


    }



}
