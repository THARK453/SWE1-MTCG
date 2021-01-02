import Parse.messages;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class messagesTest {

    @Test
    void showMessages() {
        try {
            String msg= messages.showMessages();
            System.out.println(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void addMessages() {
    }

    @Test
    void selectMessages() {
    }

    @Test
    void updateMessages() {
    }

    @Test
    void DELETEMessages() {
    }
}