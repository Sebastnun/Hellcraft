package com.hellcraft.Task.MobFactory;

import com.hellcraft.Item.SpecialItems;
import com.hellcraft.Main;
import net.minecraft.server.v1_16_R2.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import sun.security.provider.ConfigFile;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SpawnManager implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void onSpawnGhast(EntitySpawnEvent e){
        if (!(e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase(main.dimension.getName())))
            return;
        if (!(e.getEntity() instanceof Spider))
            return;

        e.setCancelled(true);

        InfernalGhast ghast = new InfernalGhast(e.getLocation());

        WorldServer world = ((CraftWorld)e.getLocation().getWorld()).getHandle();

        world.addEntity(ghast);

    }

    @EventHandler
    public void onSpawnSkeleton(EntitySpawnEvent e){
        if (!(e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase(main.dimension.getName())))
            return;
        if (!(e.getEntity() instanceof Zombie))
            return;
        e.setCancelled(true);

        WorldServer world = ((CraftWorld)e.getLocation().getWorld()).getHandle();

        world.addEntity(new InfernalSkeleton(e.getLocation(),"&6&lInfernal Skeleton", new SpecialItems().EspadaDelBoos()));

    }
    @EventHandler
    public void onSpawnSkeleotnBySkeleton(EntitySpawnEvent e){
        if (!(e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase(main.dimension.getName())))
            return;
        if (!(e.getEntity() instanceof Spider))
            return;
        e.setCancelled(true);

        WorldServer world = ((CraftWorld)e.getLocation().getWorld()).getHandle();

        world.addEntity(new InfernalSkeleton(e.getLocation(),"&6&lInfernal Skeleton Comander", new SpecialItems().ArcoDelBoos()));

    }


    @EventHandler
    public void onSpawnDeathModule(CreatureSpawnEvent e){
        if (!(e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase(main.world.getName())))
            return;
        if (!(e.getEntity() instanceof Spider))
            return;
        if (!(e.getSpawnReason()== CreatureSpawnEvent.SpawnReason.NATURAL))
            return;
        if(!(e.getLocation().getBlock().getBiome() == Biome.PLAINS))
            return;
        if (new Random().nextInt(100)==0){
            e.setCancelled(true);
            int x = (int)e.getLocation().getX();
            int y = (int)e.getLocation().getY();
            int z = (int)e.getLocation().getZ();
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(),"summon spawner_minecart "+x+" "+y+" "+z+" {PersistenceRequired:1b,CustomNameVisible:1b,SpawnCount:10,SpawnRange:10,MinSpawnDelay:1,MaxSpawnDelay:60,RequiredPlayerRange:10,Passengers:[{id:\"minecraft:shulker\",PersistenceRequired:1b,DeathLootTable:\"hellcraft:bow/superbow\",AttachFace:0b,Color:14b,ActiveEffects:[{Id:11b,Amplifier:3b,Duration:1000000}],Attributes:[{Name:generic.max_health,Base:1000}]},{Name:generic.armor,Base:25}],CustomName:'{\"text\":\"Modulo de muerte\",\"color\":\"gold\",\"bold\":true}',SpawnData:{id:\"minecraft:potion\",Item:{id:\"minecraft:splash_potion\",Count:1b,tag:{Potion:\"minecraft:harming\",CustomPotionColor:7536640,Enchantments:[{}]}}}}");
        }
    }

    @EventHandler
    public void onCreeper(EntitySpawnEvent e){
        if (!(e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase(main.dimension.getName())))
            return;
        if (!(e.getEntity() instanceof Creeper))
            return;
        e.setCancelled(true);

        WorldServer world = ((CraftWorld)e.getLocation().getWorld()).getHandle();

        world.addEntity(new Creeper(e.getLocation()));

    }

    @EventHandler
    public void onExplotion(ExplosionPrimeEvent e){
        if (!(e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase(main.dimension.getName())))
            return;
        if (!(e.getEntity() instanceof Creeper))
            return;

        e.setRadius(8);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if (!(e.getDamager().getLocation().getWorld().getName().equalsIgnoreCase(main.dimension.getName())))
            return;
        if (!(e.getEntity() instanceof Player))
            return;

        if (e.getCause()==(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)){
            e.setDamage(50.0);
        }
    }
}
