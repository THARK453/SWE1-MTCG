import java.io.BufferedReader;
import java.io.IOException;

public class Requestbuilder {

    public void RequestLine(BufferedReader reader, Request request) throws IOException {
        String str = reader.readLine();
        if(str!=null) {
            String[] strs = str.split(" ");
            assert strs.length == 3;
            request.setMethod(strs[0]);
            request.setUrl(strs[1]);
            request.setVersion(strs[2]);
        }

    }

}
