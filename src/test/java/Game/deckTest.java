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
        String msg= jsonArray.toString();
        System.out.println("\n"+msg);
    }

    @Test
    void createdeckcards() {

        List<Cards> cardsList=deck.createdeckcards(1);
        String msg="";

        deck.printdeckcards(cardsList);



        System.out.println(msg);

    }
}