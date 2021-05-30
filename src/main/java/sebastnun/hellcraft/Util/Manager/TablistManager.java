package sebastnun.hellcraft.Util.Manager;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import sebastnun.hellcraft.Main;
import sebastnun.hellcraft.Util.Data.PlayerDataManager;

import java.lang.reflect.Field;
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


            int count1 = 0;//header
            int count2 = 0;//footers
            Scoreboard scoreboard = Bukkit.getServer().getScoreboardManager().getMainScoreboard();
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()){
                    try {
                        PacketPlayOutPlayerListHeaderFooter packet =new PacketPlayOutPlayerListHeaderFooter();

                        Field a = packet.getClass().getDeclaredField("header");
                        a.setAccessible(true);
                        Field b = packet.getClass().getDeclaredField("footer");
                        b.setAccessible(true);

                        //registerTeams(scoreboard);
                        if (count1 >= headers.size())
                            count1=0;
                        if (count2 >= footers.size())
                            count2=0;
                        a.set(packet,headers.get(count1));

                        if(Bukkit.getOnlinePlayers().size() != 0){//"&c&lTiempo jugado: "+new PlayerDataManager().getTotalTime(p)+
                            //"              "+"&b&lVidas: &a&l"+Main.Lives.get(p)+"    "+p.getName()
                            b.set(packet,new ChatComponentText(format("&c&lTiempo jugado: "+new PlayerDataManager().getTotalTime(p)+
                                    "              "+"&b&lVidas: &a&l"+Main.Lives.get(p)+"    ")));
                            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
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



    private void AddPacket(PacketPlayOutPlayerListHeaderFooter packet){
        for (Player p : Bukkit.getOnlinePlayers()){
            try {
                Field b = packet.getClass().getDeclaredField("footer");
                p.sendMessage(p.getName());
                if (p.getName()==p.getName()){
                    b.set(packet,new ChatComponentText(format("&c&lTiempo jugado: "+new PlayerDataManager().getTotalTime(p)+
                            "              "+"&b&lVidas: &a&l"+Main.Lives.get(p)+"    "+p.getName())));
                    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
                }

            }catch (Exception e){
                System.out.println(format("&cERROR: Al registrar la tablist"));
            }
        }




    }




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
        this.headers.add(new ChatComponentText(format(header)));
    }

    public void addFooters(String footer){
        this.footers.add(new ChatComponentText(format(footer)));
    }

    private static String format(String texto){
        return ChatColor.translateAlternateColorCodes('&',texto);
    }



}
