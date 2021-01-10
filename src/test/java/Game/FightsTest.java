package Game;

import Cards.Cards;
import Data.deck;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FightsTest {

    @Test
    void startfight() {
        List<Cards> user_0deckcards = deck.createdeckcards(1);
        List<Cards> user_1deckcards = deck.createdeckcards(2);

        Fights fights=new Fights(user_0deckcards,user_1deckcards,1,2,100);
       String result=fights.Startfight();
        System.out.println(result);
    }
}