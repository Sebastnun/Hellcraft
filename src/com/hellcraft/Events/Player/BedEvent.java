package com.hellcraft.Events.Player;

import com.hellcraft.Main;
import com.hellcraft.Util.Manager.DateManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.plugin.Plugin;

import java.util.SplittableRandom;

public class BedEvent implements Listener {

    private Main main = Main.getInstance();


    @EventHandler
    public void onBed(PlayerBedEnterEvent event){
        Player p = event.getPlayer();
        Location playerbed = event.getBed().getLocation().add(0.0D, 1.0D, 0.0D);

        if (new DateManager(main).getDays()>=37){
            Main.ins.world.playSound(playerbed, Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);
            Main.ins.world.spawnParticle(Particle.EXPLOSION_HUGE, playerbed, 1);
            event.getPlayer().sendMessage(Main.format(Main.tag + " &aHas restablecido el contador de Phantoms."));
            event.getPlayer().setStatistic(Statistic.TIME_SINCE_REST, 0);
            event.setCancelled(true);
            return;
        }

    }



}
