package com.hellcraft;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HCTabCompleter implements TabCompleter {
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> subcommands = new ArrayList<>();
        Player p = (Player) sender;
        String playername = sender.getName();
        if (args.length == 1) {
            if (sender.hasPermission("hellcraft.admin"))
                subcommands.add("vanish");
            if (sender.hasPermission("hellcraft.admin"))
                subcommands.add("dia");
            if (sender.hasPermission("hellcraft.admin"))
                subcommands.add("reload");
            if (sender.hasPermission("hellcraft.admin"))
                subcommands.add("cambiarDia");
            if (sender.hasPermission("hellcraft.admin"))
                subcommands.add("nebula");
            if (sender.hasPermission("hellcraft.admin"))
                subcommands.add("realIngot");
            if (sender.hasPermission("hellcraft.lives"))
                subcommands.add("live");
        }
        if (args.length == 2){
            if (args[1].equalsIgnoreCase("live")){
                if (sender.hasPermission("hellcraft.lives"))
                    subcommands.add("setlives");
                if (sender.hasPermission("hellcraft.lives"))
                    subcommands.add("getlives");
            }else {
                for (Player player : Bukkit.getOnlinePlayers()){
                    if (sender.hasPermission("hellcraft.lives"))
                        subcommands.add(player.getName());
                }
            }


        }
        if (args.length == 3){
            for (Player player : Bukkit.getOnlinePlayers()){
                if (sender.hasPermission("hellcraft.lives"))
                    subcommands.add(player.getName());
            }
        }
        return subcommands;
    }
}
