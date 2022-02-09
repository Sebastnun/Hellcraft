package com.hellcraft.CompleterCommands;

import com.hellcraft.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Rango implements CommandExecutor {
    private Main main = Main.getInstance();
    @Override
    public boolean onCommand( CommandSender sender,  Command command, String s, String[] arg) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("No, puedes no eres jugador");
            return false;
        }else {
            Player player = (Player) sender;
            player.sendMessage(main.format("&eSi quieres adquirir un rango ve al canal"));
            player.sendMessage(main.format("&ede TheZazz en twitch o youtube y adquieres los diferentes rangos"));
            player.sendMessage(main.format("&4[&cY&fT&4] &chttps://www.youtube.com/channel/UCG9ioO47xUa9owek0T828OA"));
            player.sendMessage(main.format("&d[Twitch] https://www.twitch.tv/thezazz_"));
            return true;
        }
    }
}
