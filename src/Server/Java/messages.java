

import java.util.ArrayList;
import java.util.List;

public class messages {

    private static List<String> messageList = new ArrayList<String>();

    public static String showMessages(){
        StringBuilder str = new StringBuilder("{");
        if(messageList.size() != 0) {
            for (int index = 0; index < messageList.size(); index++) {
                str.append("\"" + index + "\" : " + "\"" + messageList.get(index) + "\",");
            }
            str.deleteCharAt(str.length() - 1);
        }
        str.append("}");
        return str.toString();
    }


    public static String addMessages(String message){
        String [] datas = message.split(":");
        String data = datas[1].replaceAll("\"","");
        data = data.replaceAll("}","");
        messageList.add(data);
        return "{\"state\":\"200\"}";
    }
}
