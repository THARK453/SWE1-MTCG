package Data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatasqlTest {

    @Test
    void inbattle() {
        String rst=Datasql.inbattle("Basic kienboec-mtcgToken");
        //String rst=Datasql.inbattle("Basic altenhof-mtcgToken");

        System.out.println(rst);
    }
}