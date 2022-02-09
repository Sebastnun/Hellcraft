package com.hellcraft.Events.Player;

import com.hellcraft.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ResurectionEvent implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void onTotem(EntityResurrectEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if (event.getEntity() instanceof Player){
            Player player = ((Player) event.getEntity()).getPlayer();
            if (((Player)event.getEntity()).getInventory().getItemInMainHand().getType() == Material.TOTEM_OF_UNDYING || ((Player)event.getEntity()).getInventory().getItemInOffHand().getType() == Material.TOTEM_OF_UNDYING) {
                for (Player p : Bukkit.getOnlinePlayers()){

                    p.sendMessage(main.format("&4&l&oLe quedan "+cuantosTotems(player))+" Totems");

                }
            }
        }
    }

    private int cuantosTotems(Player player){
        int totems=-1;
        for (ItemStack i : player.getInventory().getContents()){
            if (i!=null){
                if (i.getType()==Material.TOTEM_OF_UNDYING){
                    totems++;
                }
            }
        }
        return totems;
    }

}
