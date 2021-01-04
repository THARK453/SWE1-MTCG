package Game;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class battleTest {

    @Test
    void createbattlefield() {
        battle.createbattlefield();
    }

    @Test
    void selectbattlefield() {
        ResultSet rsttest=battle.selectbattlefield();

            try {
                while (rsttest.next()){
                    System.out.println(" test: "+rsttest.getString("id")+
                            "  "+rsttest.getString("status"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


    }

    @Test
    void startthebattlefield() {
        int i=battle.inbattlethebattlefield(2);
        if(i==1){
            System.out.println("Start battle 2");
        }else {
            System.out.println("Error");
        }
    }

    @Test
    void closethebattlefield() {
        int i=battle.Availablethebattlefield(1);
        if(i==1){
            System.out.println("Close battle ");
        }else {
            System.out.println("Error");
        }
    }

    @Test
    void checkbattlefield() {
        int i=battle.checkbattlefield(1);
        System.out.println(" test: -> "+i);
    }

    @Test
    void getbattlefielduser(){
        ResultSet rst=battle.getbattlefielduser(1);

        try {
            while (rst.next()){
                System.out.println(" test: "+rst.getString("username")+" "+rst.getString("id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void Startbattle(){
        ResultSet rstbattle=battle.getbattlefielduser(1);
        String msg=battle.Startbattle(rstbattle);


    }
}