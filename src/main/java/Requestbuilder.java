import Parse.Request;

import java.util.HashMap;
import java.util.Map;

public class Requestbuilder {

    public static String RequestLine(String str) {
        StringBuilder builder = new StringBuilder();
        builder.append(str).append(" /user HTTP/1.1"+"\n"+"Content-Type: application/json"+"\n"+"\n"+"json test"+"\n");
        return builder.toString();
    }



    public static void RequestHeader( Request request) throws Exception {
        Map<String, String> headers = new HashMap<>(16);


        headers.put("Content-Type:","application/json");

        request.setHeaders(headers);
    }


}
