package sebastnun.hellcraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import sebastnun.hellcraft.Entities.WildFire;
import sebastnun.hellcraft.Util.Data.PlayerDataManager;

public class PrincipalCommand implements CommandExecutor {


    private final Main main = Main.getInstance();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)){
            Bukkit.getConsoleSender().sendMessage(Main.format("&cTu no eres Jugador"));
            return true;
        }else {
            Player p = (Player) sender;
            if (p.hasPermission("hellcraft.admin")) {
                if (args[0].equalsIgnoreCase("time")) {
                    try {
                        p.setStatistic(Statistic.PLAY_ONE_MINUTE, minutesToTicks(Integer.valueOf(args[1])));
                        p.sendMessage(format("&6Tu tiempo de juego es " + new PlayerDataManager().getTotalTime(p)));
                    } catch (Exception e) {
                        p.sendMessage(format("&cIngresa un valor de minutos"));
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("addtime")) {
                    try {
                        p.setStatistic(Statistic.PLAY_ONE_MINUTE, (p.getStatistic(Statistic.PLAY_ONE_MINUTE) + minutesToTicks(Integer.valueOf(args[1]))));
                        p.sendMessage(format("&6Tu tiempo de juego es " + new PlayerDataManager().getTotalTime(p)));
                    } catch (Exception e) {
                        p.sendMessage(format("&cIngresa un valor de minutos"));
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("restTime")) {
                    try {
                        p.setStatistic(Statistic.PLAY_ONE_MINUTE, (p.getStatistic(Statistic.PLAY_ONE_MINUTE) - minutesToTicks(Integer.valueOf(args[1]))));
                        p.sendMessage(format("&6Tu tiempo de juego es " + new PlayerDataManager().getTotalTime(p)));
                    } catch (Exception e) {
                        p.sendMessage(format("&cIngresa un valor de minutos"));
                    }
                    return true;
                }else if (args[0].equalsIgnoreCase("mob")) {
                    p.sendMessage(ChatColor.BLUE+"Se ha invocado el mob");
                    new WildFire(p.getLocation());
                    return true;
                }else {
                    p.sendMessage("&cIngresa un comando valido");
                    return true;
                }

            }else {
                p.sendMessage(format("&cNo tienes permiso para ejecutar este comando"));
                return true;
            }

        }
    }

    private int minutesToTicks(int n){
        int a = n*20;
        int b = a*60;
        return b;
    }


    private static String format(String msg){
        return Main.format(msg);
    }


}
