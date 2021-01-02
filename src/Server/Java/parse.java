
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mysql.management.util.Str;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class parse {

    private static void RequestLine(BufferedReader reader, Request request) throws IOException {
        String str = reader.readLine();
        if(str!=null) {
            String[] strs = str.split(" ");
            assert strs.length == 3;
            request.setMethod(strs[0]);
            request.setUrl(strs[1]);
            request.setVersion(strs[2]);
        }

    }


    private static void RequestHeader(BufferedReader reader, Request request) throws Exception {
        Map<String, String> headers = new HashMap<>(16);
        String line = reader.readLine();
        String[] kv;
        while (!"".equals(line)) {
            if(line!=null) {
                kv = line.split(":");
                assert kv.length == 2;
                headers.put(kv[0].trim(), kv[1].trim());
                line = reader.readLine();
            }
        }

        request.setHeaders(headers);
    }

    private static void Requestbody(BufferedReader reader, Request request) throws Exception {
        //请求头可能存在大小写问题 content-length
        int contentLen = Integer.parseInt(request.getHeaders().getOrDefault("Content-Length", "0"));
        System.out.println(contentLen);
        if (contentLen == 0) {
            // 表示没有message，直接返回
            // 如get/options请求就没有message
            return;
        }

        char[] message = new char[contentLen];
        reader.read(message);
        request.setMessage(new String(message));
    }

    public static Request parserequest(InputStream reqStream) throws Exception {
        BufferedReader httpReader = new BufferedReader(new InputStreamReader(reqStream, "UTF-8"));
        Request httpRequest = new Request();
        RequestLine(httpReader, httpRequest);
        RequestHeader(httpReader, httpRequest);
        Requestbody(httpReader, httpRequest);
        return httpRequest;
    }






    public static String buildResponse(Request request, String response) {
        Response httpResponse = new Response();
        httpResponse.setCode(200);
        httpResponse.setStatus("ok");
        httpResponse.setVersion(request.getVersion());

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/plain");
        headers.put("Content-Length", String.valueOf(response.getBytes().length));
        httpResponse.setHeaders(headers);

        httpResponse.setMessage(response);

        StringBuilder builder = new StringBuilder();
        buildResponseLine(httpResponse, builder);
        buildResponseHeaders(httpResponse, builder);
        buildResponsebody(httpResponse, builder);
        return builder.toString();
    }


    private static void buildResponseLine(Response response, StringBuilder stringBuilder) {
        stringBuilder.append(response.getVersion()).append(" ").append(response.getCode()).append(" ")
                .append(response.getStatus()).append("\n");
    }

    private static void buildResponseHeaders(Response response, StringBuilder stringBuilder) {
        for (Map.Entry<String, String> entry : response.getHeaders().entrySet()) {
            stringBuilder.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }
        stringBuilder.append("\n");
    }

    private static void buildResponsebody(Response response, StringBuilder stringBuilder) {
        stringBuilder.append(response.getMessage());
    }



    public static Jsonmsg getjson(String msg){
        Gson gson=new Gson();
        Jsonmsg jsonmsg=gson.fromJson(msg,Jsonmsg.class);
        return jsonmsg;
    }

    public static List<Jsonmsg> getjsonlist(String msg){
        Gson gson=new Gson();
        Type t = new TypeToken<List<Jsonmsg>>() {}.getType();
        List<Jsonmsg> jsonmsgs = gson.fromJson(msg, t);
        return jsonmsgs;
    }

    public static String gettoken(String msg){
        String token=msg.split(" ")[1];
        return token;
    }

    public static List<String> getnokeyjsonlist(String msg){
        Gson gson=new Gson();
        Type t = new TypeToken<List<String>>() {}.getType();
        List<String> messageList = gson.fromJson(msg, t);
        return messageList;
    }

}
