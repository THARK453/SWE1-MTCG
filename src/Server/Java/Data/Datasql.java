package Data;
import Parse.*;
import Server.*;
import Game.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class Datasql {

    public static ResultSet selectuser(String msgtoken){
        String sqlselectuser="select * from userinfor where basictoken= ?";

        ResultSet rst=GameData.Getsql(sqlselectuser,msgtoken);

        return  rst;
    }



    public static String Registration(Jsonmsg jsonmsg){
       String msg="";
       String sql="INSERT INTO userinfor (username, userpassword, basictoken, coin,status) values(?, ?, ?, ?,'Notloggedin')";
       String sqlstats="INSERT INTO stats  VALUES (?, ?)";
       String name= jsonmsg.getUsername();
       String password= jsonmsg.getPassword();
       String token=jsonmsg.getToken();
       int coin=20;
       int ELO=100;
       if(jsonmsg.getUsername().equals("admin")){
           coin=999999999;
           ELO=999999999;
       }

       int i= GameData.Dosql(sql,name,password,token,coin);
       if(i==1){
           ResultSet rstuser=selectuser(token);
           try {
               if(rstuser.next()){
                   int n=GameData.Dosql(sqlstats,rstuser.getInt("id"),ELO);
                   if(n==1){
                       msg="\n\nRegistration done username: "+jsonmsg.getUsername();
                   }else {
                       msg=msg.concat("\n\nstats insert Error");
                   }
               }




           } catch (SQLException throwables) {
               throwables.printStackTrace();
           }

       }else {
           msg="\n\nRegistration not done username: "+jsonmsg.getUsername();
       }

        return msg;
    }


    public static String Login(Jsonmsg jsonmsg){
           String msg="";
           String sqllogin="update userinfor set status='Loggedin' where username = ? and userpassword = ? ";
           String username=jsonmsg.getUsername();
           String password=jsonmsg.getPassword();

           int i= GameData.Dosql(sqllogin,username,password);
           if(i==1){
               msg=msg.concat("\n\nsign in suceesfully username: "+username);
           }else {
               msg=msg.concat("\n\nError Login failed");
           }

           return msg;
    }


    public static int checkRegistration(Jsonmsg jsonmsg){

        String sqlselect="select * from userinfor where (username= ? and userpassword=?) or (username= ?)";
        String username=jsonmsg.getUsername();
        String password=jsonmsg.getPassword();
        ResultSet rst= GameData.Getsql(sqlselect,username,password,username);

        try {
            if(rst.next()){
                return 1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return 0;
    }


    public static int checkadmin(String httpmag){
        String token= parse.gettoken(httpmag);

        if(token.equals("admin-mtcgToken")){
            String sqlselect="select * from userinfor where basictoken= ?";
            ResultSet rst= GameData.Getsql(sqlselect,token);
            try {
                if(rst.next()){
                    if(rst.getString("status").equals("Loggedin")){
                        return 1;
                    }else {
                        return 2;
                    }
                }else {
                    return 0;
                }


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

        return 0;


    }

    public static String createpackages(List<Jsonmsg> jsonmsgList){
        String msg="\n\nadd packages: \n";
        String sqlinpackage="insert into package (status) values ('Available')";
        String sqlselpackageid="SELECT id FROM package ORDER BY id DESC LIMIT 1";
        String sqlincards="insert into cards (name, damage, package_id, id) values (?, ?, ?, ?)";
        int packageid=0;
        int i= GameData.Dosql(sqlinpackage);
        if(i==1){

            try {
                ResultSet rst= GameData.Getsql(sqlselpackageid);
                if(rst.next()){
                    packageid=rst.getInt("id");
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        for(int n=0;n<jsonmsgList.size();n++) {
            String name=jsonmsgList.get(n).getName();
            String id=jsonmsgList.get(n).getId();
            Float damage=Float.parseFloat(jsonmsgList.get(n).getDamage());
            int incard= GameData.Dosql(sqlincards,name,damage,packageid,id);
            if(incard==1){
                msg=msg.concat(String.format("\n\nID: %s  name: %s  Damage: %s  packageid: %d\n",
                        jsonmsgList.get(n).getId(), jsonmsgList.get(n).getName(), jsonmsgList.get(n).getDamage(), packageid));
            }else {
                msg="\n\nadd packages: fail\n";
            }
           /* msg=msg.concat("ID: "+jsonmsgList.get(n).getId()+" name: "+jsonmsgList.get(n).getName()
                    +" Damage: "+jsonmsgList.get(n).getDamage()+"\n");*/
        }

     return msg;
    }

   public static String acquirepackages(String httpmsg){
              String msg="";
              String token="";
              String sqlselectuser="select * from userinfor where basictoken= ?";
              String sqlselecpackage="SELECT * from package Where status ='Available' ORDER BY id ASC LIMIT 1";
              String sqladdpackage="update package set status ='Sold', userid = ? where id = ?";
              String sqlcoin="update userinfor set coin =coin-5 where id = ?";
              int userid=0;
              int packageid=0;

              token= parse.gettoken(httpmsg);

             ResultSet rst= GameData.Getsql(sqlselectuser,token);
             ResultSet rst1= GameData.Getsql(sqlselecpackage);

       try {
           if(rst.next()){
               userid=rst.getInt("id");

               if(rst1.next()){
                   packageid=rst1.getInt("id");

                   if(rst.getInt("coin")>=5){
                       GameData.Dosql(sqlcoin,userid);
                       int i= GameData.Dosql(sqladdpackage,userid,packageid);
                       if(i==1){
                           msg=msg.concat("\n\npackage  acquire user: "+rst.getString("username" )
                                   +"  userid: "+rst.getString("id")+" packageid: "+rst1.getString("id"));
                       }else
                       {
                           msg="\n\nSomething went wrong";
                       }
                   }else {
                       msg="\n\nnot enough money";
                   }



               }else {

                   msg="\n\nNo Available package";
               }


           }else {
               msg="\n\nNo user or Invalid token ";
           }

           System.out.println("\n\nuserid: "+userid+"\npackageid: "+packageid);

       } catch (SQLException throwables) {
           throwables.printStackTrace();
       }

       return msg;
   }

   public static String showacquired(String httpmsg){
        String msg="";
        String token= parse.gettoken(httpmsg);
        String sqlselectuser="select * from userinfor where basictoken= ?";
        String sqlpackage="SELECT * from package Where userid= ?";
        String sqlcards="SELECT * from cards Where package_id= ?";

        ResultSet rst= GameData.Getsql(sqlselectuser,token);

       try {
           if(rst.next()){
               ResultSet rstpackage= GameData.Getsql(sqlpackage,rst.getInt("id"));
               msg=msg.concat("\n\nacquired cards on: "+rst.getString("username"));
               while (rstpackage.next()){
                   ResultSet rstcards= GameData.Getsql(sqlcards,rstpackage.getInt("id"));
                   while (rstcards.next()){
                       msg=msg.concat(String.format("\n\nID: %s  name: %s  Damage: %.2f  packageid: %d\n",
                               rstcards.getString("id"),rstcards.getString("name"),rstcards.getFloat("damage"),rstcards.getInt("package_id")));
                   }

               }

           }else {
               msg=msg.concat("\n\nError Invalid Token");
           }


       } catch (SQLException throwables) {
           throwables.printStackTrace();
       }


       return msg;
   }

     public static String showdeck(String urlmsg,String tokenmsg){
               String msg="";
               String formatmsg="";
               String sqlselectuser="select * from userinfor where basictoken= ?";
               String sqlselectdeck="select * from deck where user_id= ?";
               String sqlcards="select * from cards where id= ?";
               String token= parse.gettoken(tokenmsg);


               if(!urlmsg.equals("/deck")){
                   formatmsg=urlmsg.split("\\?")[1];
                   msg=msg.concat("\n\nformat test: "+formatmsg+" url: "+urlmsg);

               }else {
                   ResultSet rst= GameData.Getsql(sqlselectuser,token);

                   try {

                       if(rst.next()){

                            ResultSet rstdeck= GameData.Getsql(sqlselectdeck,rst.getInt("id"));
                           msg=msg.concat("\n\n"+rst.getString("username")+" deck: \n");

                           if(rstdeck.isBeforeFirst()){

                               while (rstdeck.next()){
                                   ResultSet rstcards= GameData.Getsql(sqlcards,rstdeck.getString("card_id"));
                                   while (rstcards.next()){
                                       msg=msg.concat(String.format("\n\nID: %s  name: %s  Damage: %.2f  packageid: %d\n",
                                               rstcards.getString("id"),rstcards.getString("name"),rstcards.getFloat("damage"),rstcards.getInt("package_id")));
                                   }

                               }
                           }else {
                               msg=msg.concat("Is empty");
                           }



                       }else {
                           msg=msg.concat("\n\nno user or Invalid Token");
                       }

                   } catch (SQLException throwables) {
                       throwables.printStackTrace();
                   }


               }


          return msg;
     }

     public static String configuredeck(List<String> messageList,String httpmsg){
         String msg="";
         String token= parse.gettoken(httpmsg);
         String sqlindeck="insert into deck (card_id, user_id) values (?, ?)";
         String sqlselectuser="select * from userinfor where basictoken= ?";
         String sqlcheckuser="select * from deck where user_id= ?";


         ResultSet rstuser= GameData.Getsql(sqlselectuser,token);

         try {
             if(rstuser.next()){
                 ResultSet rstcheckuser= GameData.Getsql(sqlcheckuser,rstuser.getInt("id"));


                 if(rstcheckuser.next()){

                     msg=msg.concat("\n\nUser already configure deck ");
                 }else {

                     for(int i=0;i<messageList.size();i++){
                         int n= GameData.Dosql(sqlindeck,messageList.get(i),rstuser.getInt("id"));
                     }
                     msg=msg.concat("\n\nconfigure deck user: "+rstuser.getString("username"));
                 }

             }else {
                 msg=msg.concat("\n\nno user or Invalid Token");
             }
         } catch (SQLException throwables) {
             throwables.printStackTrace();
         }


         return msg;
     }

     public static String getusers(String httpmsg,String tokenmsg){
        String msg="";
        String token= parse.gettoken(tokenmsg);
        String username=httpmsg.split("\\/")[2];
        String sqlselectuser="select * from userinfor where basictoken= ?";

        ResultSet rstuser= GameData.Getsql(sqlselectuser,token);
         try {
             if(rstuser.next() && username.equals(token.split("-")[0])){
                 msg=msg.concat(String.format("\n\nID: %d  name: %s  coin: %d  status: %s  bio: %s image: %s\n",
                         rstuser.getInt("id"),rstuser.getString("username"),rstuser.getInt("coin")
                         ,rstuser.getString("status"),rstuser.getString("bio"),rstuser.getString("image")  ));
             }else {
                 msg=msg.concat("\n\nno user or Invalid Token");
             }
         } catch (SQLException throwables) {
             throwables.printStackTrace();
         }

         return msg;
     }

     public static String edituser(String httpmsg, String tokenmsg, Jsonmsg jsonmsg){
         String msg="";
         String token= parse.gettoken(tokenmsg);
         String username=httpmsg.split("\\/")[2];
         String sqlselectuser="select * from userinfor where basictoken= ?";
         String sqlupdate="update userinfor set username =? ,bio= ?, image= ? where id = ?";
         String name=jsonmsg.getName();
         String bio=jsonmsg.getBio();
         String image=jsonmsg.getImage();

         ResultSet rstuser= GameData.Getsql(sqlselectuser,token);

         try {
             if(rstuser.next() && username.equals(token.split("-")[0])){
                 int id=rstuser.getInt("id");
                int i= GameData.Dosql(sqlupdate,name,bio,image,id);
                if(i==1){
                    msg=msg.concat("\n\nUser data has been changed  id: "+id);
                }else {
                    msg=msg.concat("\n\nError");
                }
             }else {
                 msg=msg.concat("\n\nno user or Invalid Token");
             }
         } catch (SQLException throwables) {
             throwables.printStackTrace();
         }

         return msg;
     }

     public static String inbattle(String httpmsg){
           String msg="";
           String token= parse.gettoken(httpmsg);
           ResultSet rstuser=selectuser(token);
           String sqluserinbattle="update userinfor set battlefield_id =? where id = ?";

           ResultSet rst=battle.selectbattlefield();

         try {
            if(rstuser.next()){

                if(rst.next()){
                    int i=GameData.Dosql(sqluserinbattle,rst.getInt("id"),rstuser.getInt("id"));
                    if(i==1){
                        msg=msg.concat("\n\nuser: "+rstuser.getString("username")+" in battlefield : "+rst.getInt("id"));
                        int check=battle.checkbattlefield(rst.getInt("id"));
                        if(check>=2){
                            int n=battle.inbattlethebattlefield(rst.getInt("id"));
                            ResultSet rstbattle=battle.getbattlefielduser(rst.getInt("id"));

                        }

                    }else {
                        msg=msg.concat("\n\nError");
                    }

                }else {
                    battle.createbattlefield();
                    ResultSet rstbattlefield=battle.selectbattlefield();
                    if(rstbattlefield.next()){
                        int i=GameData.Dosql(sqluserinbattle,rstbattlefield.getInt("id"),rstuser.getInt("id"));
                        if(i==1){
                            msg=msg.concat("\n\nuser: "+rstuser.getString("username")+" in battlefield : "+rstbattlefield.getInt("id"));
                            int check=battle.checkbattlefield(rstbattlefield.getInt("id"));
                            if(check>=2){
                                int n=battle.inbattlethebattlefield(rstbattlefield.getInt("id"));
                            }

                        }else {
                            msg=msg.concat("\n\nError");
                        }
                    }else {
                        msg=msg.concat("\n\nError");
                    }

                }

            }else {
                msg=msg.concat("\n\nno user or Invalid Token");
            }




         } catch (SQLException throwables) {
             throwables.printStackTrace();
         }

         return msg;
     }



}
