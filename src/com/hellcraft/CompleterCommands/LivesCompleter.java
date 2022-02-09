package com.hellcraft.CompleterCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LivesCompleter implements TabCompleter {

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> subcommands = new ArrayList<>();
        Player p = (Player) sender;
        String playername = sender.getName();
        if (args.length == 1) {
            if (sender.hasPermission("hellcraft.lives"))
                subcommands.add("setlives");
            if (sender.hasPermission("hellcraft.lives"))
                subcommands.add("getlives");
        }
        if (args.length==2){
            if(sender.hasPermission("hellcraft.lives")){
                for (Player player : Bukkit.getOnlinePlayers()){
                    subcommands.add(player.getName());
                }
            }
        }
        return subcommands;
    }
}
