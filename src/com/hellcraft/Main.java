package com.hellcraft;

import com.hellcraft.CompleterCommands.*;
import com.hellcraft.Events.ArmorEvent;
import com.hellcraft.Events.Final.FinalDragon;
import com.hellcraft.Events.Final.Mobs;
import com.hellcraft.Events.Final.Util.EnderTicks;
import com.hellcraft.Events.Player.*;
import com.hellcraft.Item.ItemManager;
import com.hellcraft.Task.EndTask;
import com.hellcraft.Task.MobFactory.SpawnManager;
import com.hellcraft.Util.Library.FileAPI;
import com.hellcraft.Util.Manager.*;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.ArrayList;

public class Main extends JavaPlugin implements Listener {

    public static Plugin instance;

    public static Main ins;

    PluginDescriptionFile pdffile = getDescription();

    private String name = ChatColor.RED+"["+ChatColor.BLUE+pdffile.getName()+ChatColor.RED+"]";

    private String version = pdffile.getVersion();

    private FileManager f;

    private RegisterManager r;

    public FirstRegister first;

    public World world = null;

    public World dimension = null;

    public World BossDimencion = null;

    public String ruta;

    private EndDataManager endData;

    public static String tag = format("&1[&cHellcraft&1]&f:");

    public ArrayList<Player> invisible_list = new ArrayList<>();

    public boolean EvilBlas;

    public boolean SkeletonKing;

    public World endWorld=null;

    private EnderTicks task=null;

    public boolean HellWither;

    private boolean start;

    public boolean Adios;

    @Override
    public void onEnable(){
        ins = this;
        Variables();
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(format("&6&m------------------------------------------"));
        Bukkit.getConsoleSender().sendMessage(format("              &c&lHELLCRAFT"));
        Bukkit.getConsoleSender().sendMessage(format("          &7- Version &e" + getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage(format("          "+setupWorld()));
        Bukkit.getConsoleSender().sendMessage(format("           "+setupDimension()));
        Bukkit.getConsoleSender().sendMessage(format("&6&m------------------------------------------"));
        Bukkit.getConsoleSender().sendMessage("");
        for (Entity e : world.getEntities()){
            if (e instanceof Slime){
                e.remove();
            }
        }
        world.setGameRule(GameRule.DO_WEATHER_CYCLE,false);
        start();
        registryCommando();
        registryEvents();
    }

    private void Variables(){
        Adios = false;
        setupWorld();
        setupDimension();
    }




    @Override
    public void onDisable(){
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(format("&6&m-----------------------------------------"));
        Bukkit.getConsoleSender().sendMessage(format("&6El plugin "+name+" &6ha sido descargado"));
        Bukkit.getConsoleSender().sendMessage(format("&6&m-----------------------------------------"));
        Bukkit.getConsoleSender().sendMessage("");
    }
    public void registryCommando(){
        this.getCommand("lives").setExecutor(new liveCommand(this));
        this.getCommand("lives").setTabCompleter(new LivesCompleter());
        this.getCommand("kit").setExecutor(new KitCommand(this));
        this.getCommand("texture").setExecutor(new TexturePack());
        this.getCommand("rango").setExecutor(new Rango());
        this.getCommand("hc").setExecutor(new HC(this));
        this.getCommand("hc").setTabCompleter(new HCTabCompleter());
    }
    public void registryEvents(){
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getServer().getPluginManager().registerEvents(new DeathEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ArmorEvent(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new JoinEvent(),this);
        Bukkit.getServer().getPluginManager().registerEvents(new BedEvent(),this);
        Bukkit.getServer().getPluginManager().registerEvents(new ItemManager(),this);
        Bukkit.getServer().getPluginManager().registerEvents(new EndTask(),this);
        Bukkit.getServer().getPluginManager().registerEvents(new ResurectionEvent(),this);
        Bukkit.getServer().getPluginManager().registerEvents(new SpawnManager(),this);
        Bukkit.getServer().getPluginManager().registerEvents(new DimencionManager(),this);
        Bukkit.getServer().getPluginManager().registerEvents(new NPC(),this);
        Bukkit.getServer().getPluginManager().registerEvents(new TexturePack(),this);
        Bukkit.getServer().getPluginManager().registerEvents(new Mobs(),this);
        Bukkit.getServer().getPluginManager().registerEvents(new FinalDragon(),this);
    }

    protected String setupWorld(){
        for (World w : Bukkit.getWorlds()) {
            if (w.getEnvironment() == World.Environment.NORMAL) {
                if (w.getName().equalsIgnoreCase("world")){
                    this.world = w;
                }
            }
        }
        return "&aOverworld: &b" + this.world.getName();
    }


    protected String setupDimension(){
        for (World w : Bukkit.getWorlds()) {
            if (w.getName().equalsIgnoreCase("data")){
                this.dimension = w;
            }
        }
        return "&bDimension: &a" + this.dimension.getName();
    }

    public void start(){
        r = new RegisterManager(this);
        this.endData = new EndDataManager(this);
        tickAll();
        start = getConfig().getBoolean("DateManager");
        f = new FileManager(this);
        first = new FirstRegister();
        registerConfig();
        if (!this.getDataFolder().exists()){
            this.getDataFolder().mkdir();
        }

        startOver();

    }

    public void registerConfig() {
        File config = new File(this.getDataFolder(),"config.yml");
        ruta = config.getPath();
        if (!config.exists()){
            this.getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }

    }



    public void tickAll(){
        Bukkit.getScheduler().runTaskTimer((Plugin)this, new Runnable() {@Override public void run() {
            ticksPlayer();
            ticksEvents();
            for (Entity e : world.getEntities()){
                if (e instanceof Slime){
                    e.remove();
                }
            }
        }},0L,5L);
    }

    public void ticksPlayer(){
        for (Player player : Bukkit.getOnlinePlayers()){
            ArmorEvent.setupHealth(player);
            if (ArmorEvent.isRealNetherite(player)){
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,20,0,true,false));
            }
            if (Adios){
                player.setBedSpawnLocation(new Location(this.endWorld,69.17,53,-82.11,41.86F,-22.62F),true);
            }else {
                player.setBedSpawnLocation(world.getSpawnLocation(),true);
            }
            ArmorEffects(player);
        }
    }


    public void ticksEvents(){
        long segundosbrutos = (this.world.getWeatherDuration() / 20);
        long hours = segundosbrutos % 86400L / 3600L;
        long minutes = segundosbrutos % 3600L / 60L;
        long seconds = segundosbrutos % 60L;
        long days = segundosbrutos / 86400L;
        String time = String.format(((days >= 1L) ? String.format("%02d d", new Object[] { Long.valueOf(days) }) : "") + "%02d:%02d:%02d", new Object[] { Long.valueOf(hours), Long.valueOf(minutes), Long.valueOf(seconds) });
        if (world.hasStorm()){
            for (Player p : Bukkit.getOnlinePlayers()){
                String msg = format("&7Quedan %tiempo% de tormenta").replace("%tiempo%", time);
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg));
            }
            if (!(world.getGameRuleValue(GameRule.DO_WEATHER_CYCLE))){
                world.setGameRule(GameRule.DO_WEATHER_CYCLE,true);
            }
        }else {
            if (world.getGameRuleValue(GameRule.DO_WEATHER_CYCLE)){
                world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            }
        }
        if (start){
            new DateManager(this).getInstance(this).tick();
        }



    }

    public void ArmorEffects(Player player){
        if (player.getWorld().getName().equalsIgnoreCase(dimension.getName())){
            if (player.getActivePotionEffects().contains(PotionEffectType.INVISIBILITY)){
                player.getActivePotionEffects().remove(PotionEffectType.INVISIBILITY);
            }
        }
        /*if (ArmorEvent.isRealNetherite(player)){
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,16,1,true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,16,1,true));
            if(player.getInventory().getItemInMainHand().getType()==Material.NETHERITE_PICKAXE){
                if (player.getInventory().getItemInMainHand().getItemMeta().isUnbreakable()){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,16,1,true));
                }
            }else if (player.getInventory().getItemInMainHand().getType()==Material.NETHERITE_SWORD){
                if (player.getInventory().getItemInMainHand().getItemMeta().isUnbreakable()){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,16,1,true));
                }
            }else if (player.getInventory().getItemInMainHand().getType()==Material.NETHERITE_HOE){
                if (player.getInventory().getItemInMainHand().getItemMeta().isUnbreakable()){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,16,1,true));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,16,1,true));
                }
            }
        }*/

        if (ArmorEvent.isBoss(player)){
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,16,4,true,false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,16,2,true,false));
            if(player.getInventory().getItemInMainHand().getType()==Material.IRON_INGOT){
                if (player.getInventory().getItemInMainHand().getItemMeta().isUnbreakable() ){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,16,3,true,false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,16,3,true,false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION,16,4,true,false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,16,4,true,false));
                    player.setAllowFlight(true);
                }
            }else if (player.getInventory().getItemInOffHand().getType()==Material.IRON_INGOT){
                if (player.getInventory().getItemInOffHand().getItemMeta().isUnbreakable() ){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,16,3,true,false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,16,3,true,false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION,16,4,true,false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,16,4,true,false));
                    player.setAllowFlight(true);
                }
            }else {
                if (player.getGameMode().equals(GameMode.SURVIVAL)){
                    if(player.getAllowFlight()){
                        player.setAllowFlight(false);
                    }
                }

            }
        }else {
            if (player.getGameMode().equals(GameMode.SURVIVAL)){
                if(player.getAllowFlight()){
                    player.setAllowFlight(false);
                }
            }

        }


    }


    public void startOver(){
        new FileAPI.UtilFile.FileOut(this,"island1.schem","schematics/", true);
        new FileAPI.UtilFile.FileOut(this,"island2.schem","schematics/", true);
        new FileAPI.UtilFile.FileOut(this,"island3.schem","schematics/", true);
        new FileAPI.UtilFile.FileOut(this,"island4.schem","schematics/", true);
        new FileAPI.UtilFile.FileOut(this,"island5.schem","schematics/", true);
        new FileAPI.UtilFile.FileOut(this,"big.schem","schematics/", true);

    }

    protected void reload(CommandSender sender) {
        registerConfig();
        reloadConfig();
        DateManager.getInstance(this).reloadDate();
        f.reloadConfig();
        r.getCfg();
        first.getCfg();
        sender.sendMessage(format("&aSe ha recargado el archivo de configuraciones"));
        sender.sendMessage(format("&eAlgunos cambios pueden requerir un reinicio para funcionar correctamente."));
        sender.sendMessage(format("&c&lNota importante: &7Algunos cambios pueden requerir un reinicio y la fecha puede no ser exacta."));
    }




    public long getDays() {
        return DateManager.getInstance(this).getDays();
    }

    public RegisterManager getR(){return r;}

    public boolean getStart(){return start;}

    public void safeStone(){this.first.safeCfg();}

    public boolean isStone(String uuid){return this.first.isStoneAviable(uuid);}

    public void setRegister(String name, String uuid) {
        this.first.getCfg().set(uuid, name);
    }

    public static Main getInstance() {
        return ins;
    }

    public EnderTicks getTask() { return this.task; }

    public void setTask(EnderTicks task) { this.task = task; }

    public boolean getKit(String uuid) {
        return this.r.isKitsAviable(uuid);
    }

    public void safeKit() {
        this.r.safeCfg();
    }

    public void setSafe(String name, String uuid) {
        this.r.getCfg().set(uuid, name);
    }

    public static String format(String texto) {
        return ChatColor.translateAlternateColorCodes('&', texto);
    }

    public FileManager getF(){return f;}

    public EndDataManager getEndData() { return this.endData; }


    public static boolean getRank(Player player, String group) { return player.hasPermission("group." + group); }

}
