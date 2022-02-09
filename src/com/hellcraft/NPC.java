package com.hellcraft;

import ak.znetwork.znpcservers.events.NPCInteractEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class NPC implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void InteractNPC(NPCInteractEvent e){
        int id = e.getZnpc().getId();



        if(id==2){
            e.getPlayer().sendMessage(main.format("&cEl Rich: &fEs un gusto conocerte viajero"));
            e.getPlayer().sendMessage(main.format("&eEn este mundo esta lleno de peligros"));
            e.getPlayer().sendMessage(main.format("&eY atrocidades a sique mucho cuidado y protegete"));
            e.getPlayer().sendMessage(main.format("&eRecuerda que este domingo hay evento!!!"));
        }

        if (id==1){
            e.getPlayer().sendMessage(main.format("&cEl Rich&f: Wow tio, lograron derrotarlo, felicidades &6"+e.getPlayer().getName()));
            e.getPlayer().sendMessage(main.format("&eBueno era obvio es el mas facil de todos"));
            e.getPlayer().sendMessage(main.format("&eAhora entren todos al portal para regresar al lobby"));
            Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask((Plugin)main, new Runnable() {
                public void run() {
                    if (e.getPlayer().getName().equals("TheZazz")){
                        e.getPlayer().sendMessage(main.format("&cEl Rich&f: Hey &6Zazz"));
                        e.getPlayer().sendMessage(main.format("&6&l&oFELICIDADEZ"));
                    }
                }
            }, 20L*5);

        }
    }

}
