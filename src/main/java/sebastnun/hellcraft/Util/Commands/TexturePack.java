package sebastnun.hellcraft.Util.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import sebastnun.hellcraft.Main;

public class TexturePack implements CommandExecutor, Listener {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String str, String[] args) {
        if (!(sender instanceof Player)){
            Bukkit.getConsoleSender().sendMessage(Main.format("&cTu no eres Jugador"));
            return true;
        }else {
            Player player = (Player)sender;

            return true;
        }
    }



}
