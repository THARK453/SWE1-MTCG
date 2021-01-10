package Data;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DatasqlTest {

    @Test
    void inbattle() {
       String rst=Datasql.inbattle("Basic kienboec-mtcgToken");
        String rst1=Datasql.inbattle("Basic altenhof-mtcgToken");

        System.out.println(rst);
    }

    @Test
    void deck(){
       String sql1="SELECT cards.id FROM cards join package on package.id=cards.package_id where userid=?";
        ResultSet rst1=GameData.Getsql(sql1,1);
        ResultSet rst2=GameData.Getsql(sql1,2);
        List<String> list1=new ArrayList<>();
        List<String> list2=new ArrayList<>();

        try {
            while (rst1.next()) {
                list1.add(rst1.getString("id"));
            }

            while (rst2.next()) {
                list2.add(rst2.getString("id"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        deck.configuredeck(list1,"Basic kienboec-mtcgToken");
        deck.configuredeck(list2,"Basic altenhof-mtcgToken");


    }
}