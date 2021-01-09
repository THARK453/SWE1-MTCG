package Game;

import Data.stats;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

class statsTest {

    @Test
    void pulsELO() {
        int i= stats.pulsELO(1,9);
        if(i!=0){
            ResultSet rst=stats.getstats(1);
            try {
                if(rst.next()){
                    System.out.println(" test: "+rst.getInt("user_id")+" "+rst.getInt("ELO"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            System.out.println(" test: Error");
        }

    }

    @Test
    void getstats() {
    }

    @Test
    void deductionELO() {
        int i=stats.DeductionELO(1,19);
        ResultSet rst=stats.getstats(1);
        try {
            if(rst.next()){
                if(i==1){
                    System.out.println(" test: "+rst.getInt("user_id")+" "+rst.getInt("ELO"));
                }else if(i==-1){
                    System.out.println( " test: user: " +rst.getInt("user_id")+" lose");
                }
                else {
                    System.out.println(" test: Error");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void winlose(){
        stats.winpuls(1);
        stats.losepuls(1);
    }

    @Test
    void parsedamage(){
       int i= stats.parsedamage(23);
        System.out.println(" test "+i);
    }

    @Test
    void restELO(){
        stats.resetELO(1);
        stats.resetELO(2);
    }
}