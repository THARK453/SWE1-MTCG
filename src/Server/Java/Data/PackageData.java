package Data;
import Cards.*;
import Game.*;
import Parse.*;
import Server.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class PackageData implements SQL{


    public static String createpackages(List<Jsonmsg> jsonmsgList){
        String msg="\n\nadd packages: \n";

        int packageid=0;
        int i= GameData.Dosql(package_insertAvailable);
        if(i==1){

            try {
                ResultSet rst= GameData.Getsql(package_selectLIMIT1);
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
            int incard= GameData.Dosql(cards_insert,name,damage,packageid,id);
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

        int userid=0;
        int packageid=0;

        token= parse.gettoken(httpmsg);

        ResultSet rst= GameData.Getsql(user_selecttoken,token);
        ResultSet rst1= GameData.Getsql(package_selectAvailable);

        try {
            if(rst.next()){
                userid=rst.getInt("id");

                if(rst1.next()){
                    packageid=rst1.getInt("id");

                    if(rst.getInt("coin")>=5){
                        GameData.Dosql(user_updatecoin,userid);
                        int i= GameData.Dosql(package_adduser,userid,packageid);
                        if(i==1){
                            msg=msg.concat("\n\npackage  acquire user: "+rst.getString("username" )
                                    +"  userid: "+rst.getString("id")+" packageid: "+rst1.getString("id"));

                         ResultSet rstcards=GameData.Getsql(cards_selectpackageid,packageid);
                             if(rstcards.isBeforeFirst()){
                                 while (rstcards.next()){
                                     int n=GameData.Dosql(stack_insert,rstcards.getString("id"),userid);
                                     if (n==1){
                                         msg=msg.concat("\n stack insert");
                                     }else {
                                         msg=msg.concat("\n stack error");
                                     }
                                 }
                             }else {
                                 msg=msg.concat("\nno cards");
                             }


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

}
