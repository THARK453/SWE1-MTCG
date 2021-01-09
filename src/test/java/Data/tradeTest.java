package Data;
import Parse.*;
import org.junit.jupiter.api.Test;
import Data.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class tradeTest implements SQL{

    @Test
    void test1(){
        ResultSet rst=GameData.Getsql(cards_selectallcard_userid,2);

        try {
            ResultSetPrinter.printResultSet(rst);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void test2(){
        String msg=trade.deletetrading("/tradings/6cd85277-4590-49d4-b0cf-ba0a921faad0","Basic kienboec-mtcgToken");
        System.out.println(msg);
    }

}