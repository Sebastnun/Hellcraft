package sebastnun.hellcraft.Util.Manager;

import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_16_R3.ChatComponentText;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import sebastnun.hellcraft.Main;
import sebastnun.hellcraft.Util.Data.PlayerDataManager;
import sebastnun.hellcraft.Util.PremiumUUID;

import java.io.File;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class TablistManager {

    private List<ChatComponentText> headers = new ArrayList<>();
    private List<ChatComponentText> footers = new ArrayList<>();
    private Main plugin = Main.getInstance();

    public void showTab(){
        if (headers.isEmpty())
            return;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

            PacketPlayOutPlayerListHeaderFooter packet =new PacketPlayOutPlayerListHeaderFooter();
            int count1 = 0;//header
            int count2 = 0;//footers
            @Override
            public void run() {
                try {
                    Field a = packet.getClass().getDeclaredField("header");
                    a.setAccessible(true);
                    Field b = packet.getClass().getDeclaredField("footer");
                    b.setAccessible(true);



                    if (count1 >= headers.size())
                        count1=0;
                    if (count2 >= footers.size())
                        count2=0;
                    a.set(packet,headers.get(count1));



                    if(Bukkit.getOnlinePlayers().size() != 0){
                        for(Player p : Bukkit.getOnlinePlayers()){
                            long lS = (p.getStatistic(Statistic.valueOf("PLAY_ONE_MINUTE")) / 20L);
                            b.set(packet,new ChatComponentText(format("&cTiempo jugado: "+new PlayerDataManager().getTotalTime(p))));
                            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
                        }

                    }
                    count1++;
                    count2++;

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        },0,10);
    }



    public void addHeaders(String header){
        this.headers.add(new ChatComponentText(format(header)));
    }

    public void addFooters(String footer){
        this.footers.add(new ChatComponentText(format(footer)));
    }

    private static String format(String texto){
        return ChatColor.translateAlternateColorCodes('&',texto);
    }



}
