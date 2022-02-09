package sebastnun.hellcraft.Entities;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import sebastnun.hellcraft.Main;

public class WildFire{

    private BossBar bossWildFire;

    public static boolean WildFire=false;

    public void spawnWildFire(Location loc){
        String modelName = "wildfire.geo";
        Entity mob = loc.getWorld().spawnEntity(loc, EntityType.BLAZE);
        Blaze blaze = (Blaze) mob;
        blaze.setCustomName(Main.format("&6WildFire"));
        blaze.setCustomNameVisible(true);
        blaze.setMaxHealth(100);
        blaze.setHealth(100);
        blaze.setMetadata("WildFire",new FixedMetadataValue(Main.getInstance(),"wildfire"));
        if (!mob.isValid()) {
            for (Player p : Bukkit.getOnlinePlayers())
                p.sendMessage("Invalid entity");
        }
        ActiveModel model = ModelEngineAPI.api.getModelManager().createActiveModel(modelName);
        if (model == null) {
            for (Player p : Bukkit.getOnlinePlayers())
                p.sendMessage("Invalid model");
        }
        ModeledEntity modeledEntity = ModelEngineAPI.api.getModelManager().createModeledEntity(blaze);
        if (modeledEntity == null) {
            for (Player p : Bukkit.getOnlinePlayers())
                p.sendMessage("Invalid modelEntity");
        }
        modeledEntity.addActiveModel(model);
        modeledEntity.detectPlayers();
        modeledEntity.setInvisible(true);
        WildFire = true;
        creatorBossBar();
    }

    //Creator Boss Bar
    private void creatorBossBar(){
        if (!WildFire)
            return;
        if (bossWildFire==null){
            bossWildFire = Bukkit.createBossBar(Main.format("&6&l&oWildFire"), BarColor.YELLOW, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
            for (Player p : Bukkit.getOnlinePlayers()){
                bossWildFire.addPlayer(p);
            }
            bossWildFire.setVisible(true);
        }
        UpdateBossBar();
    }


    private void animationTotem(Player e){
        Main main = Main.getInstance();

        ItemStack item = e.getPlayer().getInventory().getItemInOffHand();
        NamespacedKey key = new NamespacedKey(main,"animation_totem");

        Player player = e.getPlayer();
        if (player.getInventory().getItemInOffHand().getType()== Material.TOTEM_OF_UNDYING && !player.getPersistentDataContainer().has(key, PersistentDataType.BYTE)){
            ItemMeta meta = player.getInventory().getItemInOffHand().getItemMeta();
            meta.setCustomModelData(123456);
            player.getInventory().getItemInOffHand().setItemMeta(meta);
        }
        if (player.getInventory().getItemInMainHand().getType()==Material.TOTEM_OF_UNDYING && !player.getPersistentDataContainer().has(key,PersistentDataType.BYTE)){
            ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
            meta.setCustomModelData(123456);
            player.getInventory().getItemInMainHand().setItemMeta(meta);
        }
        if(item.getType()!=Material.TOTEM_OF_UNDYING && player.getInventory().getItemInMainHand().getType()!=Material.TOTEM_OF_UNDYING ){
            player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
            ItemStack totem = new ItemStack(Material.TOTEM_OF_UNDYING );
            ItemMeta meta = totem.getItemMeta();
            meta.setCustomModelData(123456);
            totem.setItemMeta(meta);
            player.getInventory().setItemInOffHand(totem);
            player.getPersistentDataContainer().set(key, PersistentDataType.BYTE,(byte)1);
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
            @Override
            public void run() {
                ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
                PacketContainer packetTotem = protocolManager.createPacket(PacketType.Play.Server.ENTITY_STATUS);
                packetTotem.getModifier().writeDefaults();
                packetTotem.getIntegers().write(0, e.getPlayer().getEntityId());
                packetTotem.getBytes().write(0, (byte) 35);
                player.stopSound(Sound.ITEM_TOTEM_USE);
                if (player.getInventory().getItemInOffHand().getType()==Material.TOTEM_OF_UNDYING && !player.getPersistentDataContainer().has(key,PersistentDataType.BYTE)){
                    ItemMeta meta = player.getInventory().getItemInOffHand().getItemMeta();
                    meta.setCustomModelData(234567);
                    player.getInventory().getItemInOffHand().setItemMeta(meta);
                }
                if (player.getInventory().getItemInMainHand().getType()==Material.TOTEM_OF_UNDYING && !player.getPersistentDataContainer().has(key,PersistentDataType.BYTE) ){
                    ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
                    meta.setCustomModelData(234567);
                    player.getInventory().getItemInMainHand().setItemMeta(meta);
                }
            }
        },2);
        Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
            @Override
            public void run() {
                if(player.getPersistentDataContainer().has(key,PersistentDataType.BYTE)){
                    player.getInventory().setItemInOffHand(item);
                    player.getPersistentDataContainer().remove(key);
                }
            }
        },3);
    }




    //Update Boss Bar
    private void UpdateBossBar(){
        if (WildFire){
            for (Entity en : Main.world.getEntities()){
                if (en instanceof Blaze){
                    if (en.hasMetadata("WildFire")){
                        Blaze e = (Blaze) en;
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (!Bukkit.getOnlinePlayers().isEmpty()){
                                    for (Player p : Bukkit.getOnlinePlayers()){
                                        if (p.getWorld()==Main.world){
                                            bossWildFire.addPlayer(p);
                                        }
                                    }
                                }
                                bossWildFire.setProgress(e.getHealth()/100);
                                if(bossWildFire.getProgress()<=0)
                                    bossWildFire.removeAll();
                            }
                        }.runTaskTimer(Main.getInstance(),0, 5);
                    }
                }
            }
        }
    }

}
