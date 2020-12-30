import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysql.management.util.Str;
import org.json.JSONArray;
import org.json.JSONObject;


public class Jsonmsg {
          private String Username;
          private String Password;
          private String Id;
          private String Name;
          private String Damage;
          private String Bio;
          private String Image;
          private String CardToTrade;
          private String Type;
          private String MinimumDamage;

   /* private   List<String> Username = new ArrayList<String>();
    private  List<String> Password = new ArrayList<String>();
    private  List<String> Id = new ArrayList<String>();
    private  List<String> Name = new ArrayList<String>();
    private  List<Float> Damage = new ArrayList<Float>();*/

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getDamage() {
        return Damage;
    }

    public String getBio() {
        return Bio;
    }

    public String getImage() {
        return Image;
    }

    public String getCardToTrade() {
        return CardToTrade;
    }

    public String getType() {
        return Type;
    }

    public String getMinimumDamage() {
        return MinimumDamage;
    }
}


