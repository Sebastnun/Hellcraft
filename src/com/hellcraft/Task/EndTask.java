package com.hellcraft.Task;

import com.hellcraft.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;


public class EndTask implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void onPortal(PlayerTeleportEvent e){
        if (!e.getCause().equals(PlayerTeleportEvent.TeleportCause.END_GATEWAY))
            return;
        if (e.getPlayer().getWorld().getName().equalsIgnoreCase("world_the_end"))
            return;
        e.setCancelled(true);
        e.getPlayer().sendMessage("test");
    }
    //69.17 53 -82.11 -22.62 41.86
    @EventHandler
    public void onPortalEnderFinal(PlayerTeleportEvent e){
        if (!e.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL))
            return;
        if (!e.getPlayer().getWorld().getName().equalsIgnoreCase("final"))
            return;
        e.setCancelled(true);
        e.getPlayer().teleport(new Location(main.endWorld,69.17,53,-82.11,41.86F,-22.62F));
    }

    @EventHandler
    public void onPortalfinal(PlayerTeleportEvent e){
        if (!e.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL))
            return;
        if (!e.getPlayer().getWorld().getName().equalsIgnoreCase("end"))
            return;
        e.setCancelled(true);
        e.getPlayer().teleport(main.world.getSpawnLocation());
    }





}
