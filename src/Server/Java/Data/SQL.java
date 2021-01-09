package Data;
import Game.*;
import Parse.*;
import Server.*;
import Cards.*;

public interface SQL {
    //usersql
    String user_selecttoken="select * from userinfor where basictoken= ?";

    String user_selectid="select * from userinfor where id= ?";

    String user_selectbattlefield_id="select * from userinfor where battlefield_id= ?";

    String user_insert="INSERT INTO userinfor (username, userpassword, basictoken, coin,status) values(?, ?, ?, ?,'Notloggedin')";

    String user_updatecoin="update userinfor set coin =coin-5 where id = ?";

    String user_login="update userinfor set status='Loggedin' where username = ? and userpassword = ? ";

    String user_selectN_P="select * from userinfor where (username= ? and userpassword=?) or (username= ?)";

    String user_update_N_B_I="update userinfor set username =? ,bio= ?, image= ? where id = ?";

    String user_inbattle="update userinfor set battlefield_id =? where id = ?";

    String user_outbattlefield="UPDATE userinfor SET battlefield_id=null WHERE battlefield_id= ?";
    //usersql

    //statssql
    String stats_insert="INSERT INTO stats  VALUES (?, ?, ?, ?)";

    String stats_selectELO="select * from stats where \"ELO\"<=100 ORDER BY win DESC";

    String stats_setELO100_id="UPDATE stats SET \"ELO\"=100 WHERE user_id=?";

    String stats_updateELO_id="UPDATE stats SET \"ELO\"=\"ELO\"+? WHERE user_id=?";

    String stats_selectuserid="SELECT * FROM stats WHERE user_id=?";

    String stats_winpuls="UPDATE stats SET win=win+1 WHERE user_id=?";

    String stats_losepuls="UPDATE stats SET lose=lose+1 WHERE user_id=?";
    //statssql

    //packagesql
    String package_selectuserid="SELECT * from package Where userid= ?";

    String package_insertAvailable="insert into package (status) values ('Available')";

    String package_selectAvailable="SELECT * from package Where status ='Available' ORDER BY id ASC LIMIT 1";

    String package_selectLIMIT1="SELECT id FROM package ORDER BY id DESC LIMIT 1";

    String package_adduser="update package set status ='Sold', userid = ? where id = ?";
    //packagesql



    //cardssql
    String cards_insert="insert into cards (name, damage, package_id, id) values (?, ?, ?, ?)";

    String cards_selectpackageid="SELECT * from cards Where package_id= ?";

    String cards_selectid="select * from cards where id= ?";

    String cards_selectallcard_userid="SELECT cards.name, cards.damage, cards.package_id, cards.id FROM cards join package on package.id=cards.package_id where userid=?";

    //cardssql


    //decksql
    String deck_selectuserid="select * from deck where user_id= ?";

    String deck_insert="insert into deck (card_id, user_id) values (?, ?)";
    //decksql

    //battlefieldsql
    String battlefield_insert_Available="insert into battlefield (status) values ('Available')";

    String battlefield_selectAvailable="SELECT * FROM battlefield Where status ='Available' ORDER BY id LIMIT 1";

    String battlefield_inbattle="update battlefield set status ='inbattle' where id = ?";

    String battlefield_Available="update battlefield set status ='Available' where id = ?";
    //battlefieldsql

    //tradesql
    String trade_select="SELECT * FROM trade ";

    String trade_selectid="SELECT * FROM trade where id= ?";

    String trade_insert="INSERT INTO trade VALUES (?, ?, ?, ?, ?, ?)";

    String trade_delete="DELETE FROM trade WHERE id= ? and user_id= ?";
    //tradesql

    //stacksql
    String stack_insert="INSERT INTO stack(card_id, user_id) VALUES (?, ?)";

    String stack_selectuserid_cardid="SELECT card_id, user_id FROM stack where user_id= ? and card_id= ?";

    String stack_selectuserid="SELECT * FROM stack where user_id= ? ";

    String stack_updateuserid="UPDATE stack SET user_id=? WHERE card_id=?";

}
