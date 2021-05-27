package sebastnun.hellcraft.Util;

import org.bukkit.Bukkit;
import org.bukkit.event.EventException;
import org.bukkit.event.Listener;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;

public class PremiumUUID implements Listener {

    public static String getUUID(String playerName){
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);
            Scanner sc = new Scanner(url.openStream());
            StringBuffer sb = new StringBuffer();
            while(sc.hasNext()) {
                sb.append(sc.next());
            }
            String result = sb.toString();
            result = result.replaceAll("<[^>]*>", "");
            result = result.replaceAll("\"",".");
            result = result.replaceAll("}","");
            result = result.replace('{','.');
            result = result.replaceAll("name","");
            result = result.replace(".","");
            result = result.replace(":","");
            result = result.replace(playerName,"");
            result = result.replace("id","");
            result = result.replace(",","");
            StringBuilder builder = new StringBuilder(result.trim());
            /* Backwards adding to avoid index adjustments */
            try {
                builder.insert(20, "-");
                builder.insert(16, "-");
                builder.insert(12, "-");
                builder.insert(8, "-");
            } catch (StringIndexOutOfBoundsException e){
                throw new IllegalArgumentException();
            }
            result = builder.toString();
            return  result;
        }catch (IOException e){
            Bukkit.getPlayer(playerName).kickPlayer("Error al conseguir tu UUID Premium por favor llame a un administrador");
            return "Fallo en conseguir el UUID "+playerName;
        }

    }


    public boolean isUsernamePremium(String username) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/"+username);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = in.readLine())!=null){
                result.append(line);
            }
            return !result.toString().equals("");
        }catch (IOException e){
            return false;
        }

    }

}
