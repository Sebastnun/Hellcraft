package com.hellcraft.CompleterCommands;

import com.hellcraft.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;

public class KitCommand implements CommandExecutor {
    private Main main;

    public KitCommand(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] arg){
        if (!(sender instanceof Player)) {
            sender.sendMessage("No puedes, no eres jugador");
            return true;
        }else {
            Player player= (Player)sender;
            if (!(player.hasPermission("hellcraft.kit"))){
                player.sendMessage(main.format("&cNo tienes permisos para ejecutar ese comando"));
                return true;
            }else {
                if(!main.getKit(player.getUniqueId().toString())){
                    player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
                    player.getInventory().addItem(new ItemStack(Material.DIAMOND_PICKAXE));
                    player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE,5));
                    if(player.getName().equalsIgnoreCase("Sebastnun")){
                        player.getInventory().addItem(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE,5));
                    }else{
                        player.getInventory().addItem(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE,1));
                    }
                    player.getInventory().addItem(new ItemStack(Material.IRON_HELMET));
                    player.getInventory().addItem(new ItemStack(Material.IRON_CHESTPLATE));
                    player.getInventory().addItem(new ItemStack(Material.IRON_LEGGINGS));
                    player.getInventory().addItem(new ItemStack(Material.IRON_BOOTS));
                    player.getInventory().addItem(new ItemStack(Material.SHIELD));
                    player.getInventory().addItem(new ItemStack(Material.BREAD,64));
                    player.sendMessage(main.format("&2Has canjeado tu kit inicial"));
                    this.main.setSafe(player.getName(), player.getUniqueId().toString());
                    this.main.safeKit();

                    return true;
                }else {
                    player.sendMessage(main.format("&cYa has canjeado tu kit"));
                    return true;
                }
            }

        }

    }

}
