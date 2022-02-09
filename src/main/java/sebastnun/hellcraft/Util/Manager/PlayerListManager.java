package sebastnun.hellcraft.Util.Manager;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import net.minecraft.network.chat.ChatComponentText;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import sebastnun.hellcraft.Main;
import sebastnun.hellcraft.Util.Data.PlayerDataManager;

import java.util.ArrayList;
import java.util.List;

public class PlayerListManager {
    private List<String> headers = new ArrayList<>();
    private Main plugin = Main.getInstance();

    public void showTab(){
        if (headers.isEmpty())
            return;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {


            int count1 = 0;//header
            int count2 = 0;//footers
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()){
                    try {
                        //registerTeams(scoreboard);
                        if (count1 >= headers.size())
                            count1=0;
                        p.setPlayerListHeader(headers.get(count1) + "\n" +
                                "Equipo: Ninguno");

                        if(Bukkit.getOnlinePlayers().size() != 0){
                            if(p.isOp()){
                                p.setPlayerListFooter(format("    "+"&c&lTiempo jugado: "+new PlayerDataManager().getTotalTime(p)+
                                        "         "+"&b&lVidas: &a&l&k00&r    "));
                            }else {
                                p.setPlayerListFooter(format("    "+"&c&lTiempo jugado: "+new PlayerDataManager().getTotalTime(p)+
                                        "         "+"&b&lVidas: &a&l"+Main.Lives.get(p)+"    "));
                            }
                        }

                        count1++;
                        count2++;

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }


            }
        },0,10);
    }



   /* private void AddPacket(PacketPlayOutPlayerListHeaderFooter packet){
        for (Player p : Bukkit.getOnlinePlayers()){
            ProtocolManager manager = ProtocolLibrary.getProtocolManager();
            PacketContainer pack = manager.createPacket(PacketType.Play.Client.CHAT);

            try {
                Field b = packet.getClass().getDeclaredField("footer");
                p.sendMessage(p.getName());
                if (p.getName()==p.getName()){
                    b.set(packet,new ChatComponentText(format("&c&lTiempo jugado: "+new PlayerDataManager().getTotalTime(p)+
                            "              "+"&b&lVidas: &a&l"+Main.Lives.get(p)+"    "+p.getName())));
                    ((CraftPlayer)p).getHandle().
                }

            }catch (Exception e){
                System.out.println(format("&cERROR: Al registrar la tablist"));
            }
        }


    }*/




    private void registerTeams(Scoreboard scoreboard){
        Team hierro = scoreboard.getTeam("hierro");
        if (hierro==null){
            scoreboard.registerNewTeam("hierro");
            hierro.setPrefix(format("[Hierro]"));
        }

        Team oro = scoreboard.getTeam("oro");
        if (oro==null){
            scoreboard.registerNewTeam("oro");
            oro.setPrefix(format("&6[Oro] &r"));
        }

        Team diamante = scoreboard.getTeam("diamante");
        if (diamante==null){
            scoreboard.registerNewTeam("diamante");
            diamante.setPrefix(format("&b[Diamante] &r"));
        }

        Team twitch = scoreboard.getTeam("twitch");
        if (twitch==null){
            scoreboard.registerNewTeam("twitch");
            twitch.setPrefix("&d[Twitch]");
        }

        Team netherite = scoreboard.getTeam("netherite");
        if (netherite==null){
            scoreboard.registerNewTeam("netherite");
            netherite.setPrefix(format("&0[Netherite] &r"));
        }

        Team mod = scoreboard.getTeam("mod");
        if (mod==null){
            scoreboard.registerNewTeam("mod");
            mod.setPrefix("&3[Mod]");
        }

        Team DDM = scoreboard.getTeam("ddm");//Equipo Dios De la muerte
        if (DDM==null){
            scoreboard.registerNewTeam("ddm");
            DDM.setPrefix(format("&c[Dios de la Muerte]&r "));
        }

        Team owner = scoreboard.getTeam("owner");
        if (owner==null){
            scoreboard.registerNewTeam("owner");
            owner.setPrefix(format("&6[Owner] &r"));
        }

    }

    private void addPlayers(Scoreboard scoreboard,Player p){

        Team hierro = scoreboard.getTeam("hierro");
        if (hierro==null){
            registerTeams(scoreboard);
        }
        Team oro = scoreboard.getTeam("oro");
        Team diamante = scoreboard.getTeam("diamante");
        Team twitch = scoreboard.getTeam("twitch");
        Team netherite = scoreboard.getTeam("netherite");
        Team DDM = scoreboard.getTeam("ddm");
        Team owner = scoreboard.getTeam("owner");
        if (Main.isPlayerInGroup(p,"hierro")){
            hierro.addPlayer(p);
        }else {
            hierro.removePlayer(p);
        }
        if (Main.isPlayerInGroup(p,"oro")){
            oro.addPlayer(p);
        }else {
            oro.removePlayer(p);
        }
        if (Main.isPlayerInGroup(p,"diamante")){
            diamante.addPlayer(p);
        }else {
            diamante.removePlayer(p);
        }
        if (Main.isPlayerInGroup(p,"twitch")){
            twitch.addPlayer(p);
        }else {
            twitch.removePlayer(p);
        }
        if (Main.isPlayerInGroup(p,"netherite")){
            netherite.addPlayer(p);
        }else {
            netherite.removePlayer(p);
        }
        if (Main.isPlayerInGroup(p,"ddm")||p.isOp()){
            DDM.addPlayer(p);
        }else {
            DDM.removePlayer(p);
        }
        if (p.getName().equalsIgnoreCase("TheZazz")){
            owner.addPlayer(p);
        }else {
            owner.removePlayer(p);
        }
    }





    public void addHeaders(String header){
        this.headers.add(format(header));
    }

    private static String format(String texto){
        return ChatColor.translateAlternateColorCodes('&',texto);
    }
}
