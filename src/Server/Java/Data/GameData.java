package Data;
import Game.*;
import Parse.*;
import Server.*;
import java.sql.*;

public  class GameData {
    private  static String url="jdbc:postgresql://localhost:5432/MTCG";
    private static String user="postgres";
    private static String password="root";
    private static PreparedStatement mystm=null;
    private static Connection myconect=null;

    public static Statement getMystm() {
        return mystm;
    }

    public static Connection getMyconect() {
        return myconect;
    }

    public static void connect(){

        try {
            Class.forName("org.postgresql.Driver");
            myconect= DriverManager.getConnection(url,user,password);

            //System.out.println("Data.Data connect");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public static void closeconnect(){
        if(mystm!=null){
            try {
                mystm.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if(myconect!=null){
            try {
                myconect.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        //System.out.println("Data.Data close");
    }


    public static int Dosql(String sql,Object... objects){
                connect();

        try {
            mystm=myconect.prepareStatement(sql);

            for (int i = 0; i < objects.length; i++) {
                mystm.setObject(i+1, objects[i]);
            }

            return mystm.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
           closeconnect();
        }
         return 0;
    }


    public static ResultSet Getsql(String sql,Object... objects){
        connect();
        try {
            mystm=myconect.prepareStatement(sql);

            for (int i = 0; i < objects.length; i++) {
                mystm.setObject(i+1, objects[i]);
            }

            return mystm.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            closeconnect();
        }
        return null;
    }







}
