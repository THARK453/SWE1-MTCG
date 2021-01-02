package Data;

import Parse.ResultSetPrinter;

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
            String sql="select * from stats where \"ELO\"<=100 ORDER BY  \"ELO\" DESC";
           // String sql="INSERT INTO userinfor  values(2, 'user2','test2')";
            ResultSet rst=mystm.executeQuery(sql);
            ResultSetMetaData rsmd = rst.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

           // ResultSetPrinter.printResultSet(rst);
           int i=0;
            while (rst.next()){
                i=rst.getRow();
                System.out.println("test: "+rst.getInt("ELO"));
            }
            System.out.println("test: "+i);
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
