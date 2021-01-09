package Data;
import Cards.*;
import Game.*;
import Parse.*;
import Server.*;
import com.google.gson.JsonObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class PackageData implements SQL{


    public static String createpackages(List<Jsonmsg> jsonmsgList){
        String msg="\n[{\"Message\":\"add packages\"},\n";

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

                JsonObject jsonObject = new JsonObject();


                jsonObject.addProperty("Id", jsonmsgList.get(n).getId());
                jsonObject.addProperty("name", jsonmsgList.get(n).getName());
                jsonObject.addProperty("Damage", jsonmsgList.get(n).getDamage());
                jsonObject.addProperty("packageid", packageid);

                msg=msg.concat(jsonObject.toString()+",\n");




            }else {
                msg="\n{\"Message\":\"add packages fail\"}\n";

            }
           /* msg=msg.concat("ID: "+jsonmsgList.get(n).getId()+" name: "+jsonmsgList.get(n).getName()
                    +" Damage: "+jsonmsgList.get(n).getDamage()+"\n");*/
        }
        msg=parse.replaceLast(msg,",","]");
        return msg;
    }




    public static String acquirepackages(String httpmsg){
        String msg="\n";
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
                            JsonObject jsonObject = new JsonObject();


                            jsonObject.addProperty("Message", "package  acquire user");
                            jsonObject.addProperty("name", rst.getString("username" ));
                            jsonObject.addProperty("userid",rst.getString("id"));
                            jsonObject.addProperty("packageid", rst1.getString("id"));

                            msg=msg.concat(jsonObject.toString()+"\n");


                         ResultSet rstcards=GameData.Getsql(cards_selectpackageid,packageid);
                             if(rstcards.isBeforeFirst()){
                                 while (rstcards.next()){
                                     int n=GameData.Dosql(stack_insert,rstcards.getString("id"),userid);
                                     if (n==1){

                                         msg=msg.concat("\n{\"Message\":\"stack insert\"}\n");
                                     }else {
                                         msg=msg.concat("\n{\"Message\":\"stack error\"}\n");
                                     }
                                 }
                             }else {
                                 msg=msg.concat("\n{\"Message\":\"no cards\"}\n");
                             }


                        }else
                        {
                            msg="\n{\"Message\":\"Something went wrong\"}\n";
                        }
                    }else {
                        msg="\n{\"Message\":\"not enough money\"}\n";
                    }


                }else {

                    msg="\n{\"Message\":\"No Available package\"}\n";
                }


            }else {
                msg="\n{\"Message\":\"No user or Invalid token\"}\n";
            }

            //System.out.println("\n\nuserid: "+userid+"\npackageid: "+packageid);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return msg;
    }

}
