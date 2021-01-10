package Data;

import Parse.ResultSetPrinter;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class userTest implements SQL{

    @Test
    void selectuserbytoken() {
        ResultSet rst=GameData.Getsql(user_selecttoken,"kienboec-mtcgToken");
        try {
            if (rst.next()){
                System.out.println("test: "+rst.getString("username"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void selectuserbyID() {

    }
}