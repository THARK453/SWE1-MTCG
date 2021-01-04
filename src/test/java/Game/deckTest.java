package Game;
import Cards.*;
import Parse.*;
import Server.*;
import Data.*;
import com.google.gson.JsonArray;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class deckTest {

    @Test
    void selectdeck() {
        ResultSet rst=deck.selectdeck(1);

        try {
            while (rst.next()){
                System.out.println(" test: "+rst.getString("card_id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Test
    void setcardsmsg() {
        ResultSet rst=deck.selectdeck(1);
        JsonArray jsonArray=deck.setcardsmsg(rst);
        System.out.println(jsonArray);
    }

    @Test
    void createdeckcards() {

        List<Cards> cardsList=deck.createdeckcards(1);
        String msg="";


            for(int n=0;n<cardsList.size();n++){

                System.out.println(" id: "+cardsList.get(n).getId()+" name: "+cardsList.get(n).getName()+" damage: "
                        +cardsList.get(n).getDamage()+" type: "+cardsList.get(n).getType());
                if(cardsList.get(n).isMonster()){
                    System.out.println(" is a Monster");
                }else {
                    System.out.println(" is a Spell");
                }


            }

        msg=msg.concat(" \ntest fight function: "+Fights.Startfight(cardsList.get(0)));

        System.out.println(msg);

    }
}