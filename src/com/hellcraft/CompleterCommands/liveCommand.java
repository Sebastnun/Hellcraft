package com.hellcraft.CompleterCommands;

import com.hellcraft.Main;
import com.hellcraft.Util.Manager.DateManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class liveCommand implements CommandExecutor {
    private Main main;
    public liveCommand(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] arg) {
        Player player= (Player)sender;
        if (!(sender instanceof Player)) {
            if (arg.length>=0){
                Player p = this.main.getServer().getPlayer(arg[1]);
                if (arg[0].equalsIgnoreCase("setlives")) {
                    if (p==null){
                        player.sendMessage(ChatColor.RED+"No se encotro jugador");
                        return true;
                    }else {
                        if (arg[2]==null){
                            player.sendMessage(ChatColor.RED+"Ingresa el numero de vidas");
                        }else {
                            int newlive=Integer.parseInt(arg[2]);
                            main.getF().getConfig().set("players."+p.getUniqueId().toString()+".lives",newlive);
                            main.getF().saveConfig();
                            main.getF().reloadConfig();
                            player.sendMessage(ChatColor.BLUE+"Ha sido recargadas las vidas del jugador "+p.getName()+" a "+newlive);
                            return true;
                        }
                    }
                }else if(arg[0].equalsIgnoreCase("getlives")){
                    int live = main.getF().getConfig().getInt("players."+p.getUniqueId()+".lives");
                    player.sendMessage(ChatColor.AQUA+"Las vidas restantes del jugador son "+(live));
                    return true;
                }else{
                    player.sendMessage(main.format("&6Puedes usar los subcomandos /setlives o /getlives"));
                }

            }
            return true;
        }else {
            if (!(player.hasPermission("hellcraft.lives"))){
                player.sendMessage(main.format("&cNo tienes permisos para ejecutar ese comando"));
                return true;
            }else {
                if (arg.length>=0){
                    Player p = this.main.getServer().getPlayer(arg[1]);
                    if (arg[0].equalsIgnoreCase("setlives")) {
                        if (p==null){
                            player.sendMessage(ChatColor.RED+"No se encotro jugador");
                            return true;
                        }else {
                            int newlive=Integer.parseInt(arg[2]);
                            main.getF().getConfig().set("players."+p.getUniqueId().toString()+".lives",newlive);
                            main.getF().saveConfig();
                            main.getF().reloadConfig();
                            player.sendMessage(ChatColor.BLUE+"Ha sido recargadas las vidas del jugador "+p.getName()+" a "+newlive);
                            return true;
                        }
                    }else if(arg[0].equalsIgnoreCase("getlives")){
                        int live = main.getF().getConfig().getInt("players."+p.getUniqueId()+".lives");
                        player.sendMessage(ChatColor.AQUA+"Las vidas restantes del jugador son "+(live));
                        return true;
                    }

                }
                return true;

            }

        }
    }
}
