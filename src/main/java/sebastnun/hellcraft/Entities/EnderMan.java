package sebastnun.hellcraft.Entities;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import net.minecraft.network.protocol.game.PacketPlayOutEntityStatus;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import sebastnun.hellcraft.Main;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;


public class EnderMan extends Boss implements Listener {

    private static final int health = 1000;

    private static Zombie ender;

    private static final String name = Main.format("&2&l&oMutant Enderman");

    private int phase=0;

    private static BossBar bossEnderman;

    public EnderMan(){
        super(TypeBoss.ENDERMAN, null);
        this.phase=0;}

    public EnderMan(Location loc, CommandSender sender){
        super(TypeBoss.ENDERMAN,loc);
        String modelID = "enderman";
        ender = loc.getWorld().spawn(loc,Zombie.class);

        ender.setMaxHealth(health);
        ender.setHealth(health);
        ender.setCustomName(name);
        ender.setBaby(false);
        ender.setSilent(true);
        ender.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
        ender.setMetadata("Enderman", new FixedMetadataValue(Main.getInstance(),"enderman"));
        if (!ender.isValid()) sender.sendMessage(Main.format("&cLa entidad es invalidad"));
        ActiveModel model = ModelEngineAPI.api.getModelManager().createActiveModel(modelID);
        if (model==null) sender.sendMessage(Main.format("&cNo se encontro el modelo &oenderman"));
        ModeledEntity modeledEntity = ModelEngineAPI.api.getModelManager().createModeledEntity(ender);
        if (modeledEntity==null) sender.sendMessage(Main.format("&cNo se encontro el modelo &oenderman"));
        modeledEntity.addActiveModel(model);
        modeledEntity.detectPlayers();
        modeledEntity.setInvisible(true);
        this.phase=1;
        creatorBossBar();
    }


    @EventHandler
    private void attack(EntityDamageByEntityEvent e){
        if (e.getDamager().hasMetadata("Enderman")){
            if (e.getEntity() instanceof Player){
                int random = new Random().nextInt(10);
                switch (random){
                    case 1:
                        animationTotem(((Player) e.getEntity()).getPlayer(),123456);
                        break;
                    default:
                        e.setDamage(10);
                }
            }
        }
        if (e.getEntity().hasMetadata("Enderman")){
            if(e.getDamager() instanceof Player){
                ((Player)e.getDamager()).playSound(e.getEntity().getLocation(), Sound.ENTITY_ENDERMAN_HURT,1,1);
            }
        }


    }


    //Creator Boss Bar
    private void creatorBossBar(){
        if (phase==0)
            return;
        if (bossEnderman==null){
            bossEnderman = Bukkit.createBossBar(name, BarColor.PURPLE, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
            for (Player p : Bukkit.getOnlinePlayers()){
                bossEnderman.addPlayer(p);
            }
            bossEnderman.setVisible(true);
        }
        UpdateBossBar();
    }

    //Update Boss Bar
    private void UpdateBossBar(){
        if (phase!=0){
            for (Entity en : Main.world.getEntities()){
                if (en instanceof Zombie){
                    if (en.hasMetadata("Enderman")){
                        Zombie e = (Zombie) en;
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (!Bukkit.getOnlinePlayers().isEmpty()){
                                    for (Player p : Bukkit.getOnlinePlayers()){
                                        if (p.getWorld()==Main.world){
                                            bossEnderman.addPlayer(p);
                                        }
                                    }
                                }
                                bossEnderman.setProgress(e.getHealth()/health);
                                if(bossEnderman.getProgress()<=0){
                                    bossEnderman.removeAll();
                                    cancel();
                                }

                            }
                        }.runTaskTimer(Main.getInstance(),0, 5);
                    }
                }
            }
        }
    }



    private void animationTotem(Player e, int CustomModelData){
        Main main = Main.getInstance();

        ItemStack item = e.getPlayer().getInventory().getItemInOffHand();
        NamespacedKey key = new NamespacedKey(main,"animation_totem");

        Player player = e.getPlayer();
        if (player.getInventory().getItemInOffHand().getType()== Material.TOTEM_OF_UNDYING && !player.getPersistentDataContainer().has(key, PersistentDataType.BYTE)){
            ItemMeta meta = player.getInventory().getItemInOffHand().getItemMeta();
            meta.setCustomModelData(CustomModelData);
            player.getInventory().getItemInOffHand().setItemMeta(meta);
        }
        if (player.getInventory().getItemInMainHand().getType()==Material.TOTEM_OF_UNDYING && !player.getPersistentDataContainer().has(key,PersistentDataType.BYTE)){
            ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
            meta.setCustomModelData(CustomModelData);
            player.getInventory().getItemInMainHand().setItemMeta(meta);
        }
        if(item.getType()!=Material.TOTEM_OF_UNDYING && player.getInventory().getItemInMainHand().getType()!=Material.TOTEM_OF_UNDYING ){
            player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
            ItemStack totem = new ItemStack(Material.TOTEM_OF_UNDYING );
            ItemMeta meta = totem.getItemMeta();
            meta.setCustomModelData(CustomModelData);
            totem.setItemMeta(meta);
            player.getInventory().setItemInOffHand(totem);
            player.getPersistentDataContainer().set(key, PersistentDataType.BYTE,(byte)1);
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
            @Override
            public void run() {
                PacketPlayOutEntityStatus packetTotem = new PacketPlayOutEntityStatus(((CraftPlayer)e).getHandle(),(byte) 35);
                ((CraftPlayer)e).getHandle().b.sendPacket(packetTotem);
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


    public int getPhase(){return this.phase;}

    public void setPhase(int phase){this.phase=phase;}

    public String getName(){return name;}

}
