package Game;
import Data.*;
import Game.*;
import Cards.Cards;
import Parse.parse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Fights {

    private List<Cards>  user0card;
    private List<Cards>  user1card;
    private int user_0id;
    private int user_1id;
    private int rounds;
    private int currentrounds;
    private String user0name;
    private String user1name;
    private float damage0;
    private float damage1;
    private String cardname0;
    private String cardname1;
    private int type0;
    private int type1;


    public String getUser0name() {
        return user0name;
    }

    public void setUser0name(String user0name) {
        this.user0name = user0name;
    }

    public String getUser1name() {
        return user1name;
    }

    public void setUser1name(String user1name) {
        this.user1name = user1name;
    }

    public int getUser_0id() {
        return user_0id;
    }

    public void setUser_0id(int user_0id) {
        this.user_0id = user_0id;
    }

    public int getUser_1id() {
        return user_1id;
    }

    public void setUser_1id(int user_1id) {
        this.user_1id = user_1id;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public Fights(List<Cards> user0card, List<Cards> user1card, int user_0id, int user_1id, int rounds) {
        this.user0card = user0card;
        this.user1card = user1card;
        this.user_0id = user_0id;
        this.user_1id = user_1id;
        this.rounds = rounds;
        this.currentrounds=0;
        ResultSet rst=user.selectuserbyID(user_0id);
        try {
            if(rst.next()){
                this.user0name=rst.getString("username");
            }

            rst=user.selectuserbyID(user_1id);
            if(rst.next()){
                this.user1name=rst.getString("username");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String Startfight(){
        String result="";


        while (rounds>0){
            List<Integer> Rnumer0= parse.getRandomNumList(4,0,4);
            List<Integer> Rnumer1=parse.getRandomNumList(4,0,4);
            for(int i=0;i<4;i++){
                if (rounds>0){
                   result=result.concat(combat(Rnumer0.get(i),Rnumer1.get(i)));
                }
            }
            result=result.concat("\n reshuffle\n\n");
        }

        ResultSet rst0=stats.getstats(user_0id);
        ResultSet rst1=stats.getstats(user_1id);
        try {
            rst0.next();
            rst1.next();
            if (rst0.getInt("ELO")>rst1.getInt("ELO")){
                result=result.concat("\n user:("+user_0id+" "+user0name+") wins ELO: "+rst0.getInt("ELO"));
                result=result.concat("\n user:("+user_1id+" "+user1name+") lose ELO: "+rst1.getInt("ELO"));
            }else if(rst0.getInt("ELO")<rst1.getInt("ELO")){
                result=result.concat("\n user:("+user_1id+" "+user1name+") wins ELO: "+rst1.getInt("ELO"));
                result=result.concat("\n user:("+user_0id+" "+user0name+") lose ELO: "+rst0.getInt("ELO"));
            }else {
                result=result.concat("\n user:("+user_0id+" "+user0name+") ELO: "+rst0.getInt("ELO"));
                result=result.concat("\n user:("+user_1id+" "+user1name+") ELO: "+rst1.getInt("ELO"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        result=result.concat(" \n\nfight end rounds: "+currentrounds);
         stats.resetELO(user_0id);
         stats.resetELO(user_1id);
        return result;
    }

    private String combat(int x,int y){
        String msg="";
        currentrounds++;
        damage0=user0card.get(x).getDamage();
        damage1=user1card.get(y).getDamage();
        cardname0=user0card.get(x).getName();
        cardname1=user1card.get(y).getName();
        type0=user0card.get(x).getType();
        type1=user1card.get(y).getType();


        if(user0card.get(x).isMonster() && user1card.get(y).isMonster()){
            msg=msg.concat(Docombat());
        }else {
           msg=msg.concat(Effectivenesscombat());
        }
      rounds--;

        return msg;
    }


    private String Effectivenesscombat(){
        String msg="\nEffectivenesscombat:\n";

        if(type0==0){
            if(type1==2){
                damage1=damage1*2;
                damage0=damage0/2;
                msg=msg.concat(Docombat());
            }else if(type1==1){
                damage1=damage1/2;
                damage0=damage0*2;
                msg=msg.concat(Docombat());
            }else {
                msg=msg.concat(Docombat());
            }

        }else if(type0==1){
            if (type1==2){
                damage1=damage1/2;
                damage0=damage0*2;
                msg=msg.concat(Docombat());
            }else if(type1==0){
                damage1=damage1*2;
                damage0=damage0/2;
                msg=msg.concat(Docombat());
            }else {
                msg=msg.concat(Docombat());
            }

        }else if(type0==2){
            if(type1==0){
                damage1=damage1/2;
                damage0=damage0*2;
                msg=msg.concat(Docombat());
            }else if(type1==1){
                damage1=damage1*2;
                damage0=damage0/2;
                msg=msg.concat(Docombat());
            }else {
                msg=msg.concat(Docombat());
            }
        }




            return msg;
    }


     private String Docombat(){
         String msg="";

         if(damage0>damage1){
             msg=msg.concat("user:("+user_0id+" "+user0name+") : "+cardname0+" ("+damage0+" Damage"+
                     ") VS "+"user:("+user_1id+" "+user1name+") : "+cardname1+" ("+damage1+" Damage"+
                     ") Rounds: "+currentrounds+" "+user0name+" wins : "+(damage0-damage1)+"("+stats.parsedamage((int)(damage0-damage1))+")\n");
             int i=stats.pulsELO(user_0id,stats.parsedamage((int)(damage0-damage1)));
             int n=stats.DeductionELO(user_1id,stats.parsedamage((int)(damage0-damage1)));
             if(n==-1){
                 rounds=rounds-rounds;

             }
         }else if(damage0<damage1){
             msg=msg.concat("user:("+user_0id+" "+user0name+") : "+cardname0+" ("+damage0+" Damage"+
                     ") VS "+"user:("+user_1id+" "+user1name+") : "+cardname1+" ("+damage1+" Damage"+
                     ") Rounds: "+currentrounds+" "+user1name+" wins : "+(damage1-damage0)+"("+stats.parsedamage((int)(damage1-damage0))+"\n");
             int i=stats.pulsELO(user_1id,stats.parsedamage((int)(damage1-damage0)));
             int n=stats.DeductionELO(user_0id,stats.parsedamage((int)(damage1-damage0)));
             if(n==-1){
                 rounds=rounds-rounds;

             }
         }else {
             msg=msg.concat("user:("+user_0id+" "+user0name+") : "+cardname0+" ("+damage0+" Damage"+
                     ") VS "+"user:("+user_1id+" "+user1name+") : "+cardname1+" ("+damage1+" Damage"+
                     ") Rounds: "+currentrounds+"  Draw"+"\n");
         }

        return msg;
     }



}
