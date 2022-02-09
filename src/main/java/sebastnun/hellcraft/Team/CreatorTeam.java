package sebastnun.hellcraft.Team;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import sebastnun.hellcraft.Main;

import java.io.File;

public class CreatorTeam {


    private final File folder = Main.getInstance().groupFile;

    private final File groupFile;


    public CreatorTeam(String nameTeam) {
        this.groupFile = new File(folder+File.separator+nameTeam+".yml");
    }

    public void registerTeam(){
        FileConfiguration config = YamlConfiguration.loadConfiguration(groupFile);

    }



    public static String getNameTeam(Player player){
        FileConfiguration config;
        String uuid = player.getUniqueId().toString();
        File folder = new File(Main.getInstance().getDataFolder() + File.separator + "players");
        File file = new File(folder + File.separator + uuid+".yml");
        config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
        if (!file.exists())return null;
        String team = config.getString("info.team");
        if (team.equalsIgnoreCase("null")){
            return "Ninguno";
        }
        return team;
    }




}
