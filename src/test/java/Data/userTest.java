package Data;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class userTest {

    @Test
    void selectuserbytoken() {
    }

    @Test
    void selectuserbyID() {
        ResultSet rst=user.selectuserbyID(1);
        try {if(rst.next()){
            System.out.println(rst.getString("username"));
        }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}