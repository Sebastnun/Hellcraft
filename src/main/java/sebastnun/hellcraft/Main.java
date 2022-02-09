package sebastnun.hellcraft;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import sebastnun.hellcraft.Entities.EnderMan;
import sebastnun.hellcraft.Items.Chains;
import sebastnun.hellcraft.Listener.Player.Events;
import sebastnun.hellcraft.Team.HellCommand;
import sebastnun.hellcraft.Util.Commands.AFK;
import sebastnun.hellcraft.Util.Data.PlayerDataManager;
import sebastnun.hellcraft.Util.Enchantment.TestEnchant;
import sebastnun.hellcraft.Util.EscobaMagica;
import sebastnun.hellcraft.Util.Manager.PlayerListManager;
import sebastnun.hellcraft.Worlds.WorldsGeneretor;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public final class Main extends JavaPlugin implements Listener {

    private static Main instance;

    private PlayerListManager tab;

    public static World world = null;

    private static World EventWorld=null;

    private static World Lobby = null;

    public static HashMap<OfflinePlayer,Integer> Lives = new HashMap<>();

    private static HashMap<OfflinePlayer,String> Teams = new HashMap<>();

    private int HoraMinima;

    private Scoreboard scoreboard;

    private static boolean isBloodMoon;

    private static boolean isEvent = true;

    public File groupFile = new File(getDataFolder()+File.separator+"teams");

    @Override
    public void onEnable() {
        instance=this;
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        setupWorld();
        Bukkit.getConsoleSender().sendMessage(format("&6-------------------------"));
        Bukkit.getConsoleSender().sendMessage(format("      &6&lHellcraft"));
        Bukkit.getConsoleSender().sendMessage(format("     "+setupLobby()));
        Bukkit.getConsoleSender().sendMessage(format("  "+setupWorld()));
        Bukkit.getConsoleSender().sendMessage(format(" "+setupEventWorld()));
        Bukkit.getConsoleSender().sendMessage(format("&6-------------------------"));
        start();
        resetPersitentData();
    }

    private void start(){
        HoraMinima=5;
        registerEvents();
        registerCommands();
        setupTabList();
        TestEnchant.register();
        ticksAll();
        saveDefaultConfig();
        new EscobaMagica().Schedule();
        CheckAFK();
        for (Player p : Bukkit.getOnlinePlayers()){
            if (p.getPersistentDataContainer().has(new NamespacedKey(this,"afk"),PersistentDataType.BYTE)&&AFK.activeAFK.containsKey(p)){
                p.getPersistentDataContainer().remove(new NamespacedKey(this,"afk"));
                AFK.activeAFK.remove(p);
            }
            if (configuration(p)!=null){
                Lives.put(p,configuration(p).getInt("info.lives"));
            }
        }
        isBloodMoon = world.getFullTime() >= 300000;
    }


    public void resetPersitentData(){
        for (Player p : Bukkit.getOnlinePlayers()){
            if (p.getPersistentDataContainer().has(new Chains().getKey(),PersistentDataType.BYTE))
                p.getPersistentDataContainer().remove(new Chains().getKey());
        }
    }

    protected String setupWorld(){
        for (World w : Bukkit.getWorlds()){
            if (w.getEnvironment() == World.Environment.NORMAL){
                if (w.getName().equalsIgnoreCase("world")){
                    world = w;
                }
            }
        }
        return "&aOverworld: &b" + world.getName();
    }

    protected String setupEventWorld(){
        for (World w : Bukkit.getWorlds()){
            if (w.getEnvironment() == World.Environment.NORMAL){
                if (w.getName().equalsIgnoreCase("EventWorld")){
                    EventWorld = w;
                }
            }
        }
        if (EventWorld==null)
            createEventWorld();
        return "&6Event World: &b" + EventWorld.getName();
    }


    protected String setupLobby(){
        for (World w : Bukkit.getWorlds()){
            if (w.getEnvironment() == World.Environment.NORMAL){
                if (w.getName().equalsIgnoreCase("Lobby")){
                    Lobby = w;
                }
            }
        }

        if (Lobby==null) this.createLobby();

        return "&6Lobby: &b" + Lobby.getName();
    }

    private void createEventWorld(){
        WorldCreator worldCreator = new WorldCreator("EventWorld");
        worldCreator.generator(this.getWorldGeneretor());
        worldCreator.generateStructures(false);
        worldCreator.environment(World.Environment.NORMAL);
        EventWorld = worldCreator.createWorld();
        EventWorld.setGameRule(GameRule.MOB_GRIEFING,false);
        Location loc = EventWorld.getSpawnLocation().add(0,-1,0);
        loc.getBlock().setType(Material.BEDROCK);
    }

    private void createLobby(){
        WorldCreator worldCreator = new WorldCreator("Lobby");
        worldCreator.generator(this.getWorldGeneretor());
        worldCreator.generateStructures(false);
        worldCreator.environment(World.Environment.NORMAL);
        Lobby = worldCreator.createWorld();
        Lobby.setGameRule(GameRule.MOB_GRIEFING,false);
        Location loc = Lobby.getSpawnLocation().add(0,-1,0);
        loc.getBlock().setType(Material.BEDROCK);
    }

    public static World getEventsWorld(){return EventWorld;}


    public void reloadLives(OfflinePlayer p){
        if (configuration(p)!=null){
            if (Lives.containsKey(p)){
                Lives.remove(p);
            }
            Lives.put(p,configuration(p).getInt("info.lives"));
        }
    }

    public void reloadTeams(OfflinePlayer p){
        if (configuration(p)!=null){
            if (Teams.containsKey(p)){
                Teams.remove(p);
            }
            Teams.put(p,configuration(p).getString("info.teams"));
        }
    }




    public void savePlayerFile(OfflinePlayer p){
        FileConfiguration config = configuration(p);
        try {
            config.save(getFilePlayer(p));
        }catch (IOException er){
            Bukkit.getConsoleSender().sendMessage(this.format("&c[ERROR] No se cargo el archivo de configuracion del jugador "+p.getName()));
        }
    }


    @EventHandler
    private void JoinEvent(PlayerJoinEvent e){
        if (!Lives.containsKey(e.getPlayer())){
            if (getFilePlayer(e.getPlayer()).exists()){
                Lives.put(e.getPlayer(),configuration(e.getPlayer()).getInt("info.lives"));
            }
        }
        if (!Teams.containsKey(e.getPlayer())){
            if (getFilePlayer(e.getPlayer()).exists()){
                Teams.put(e.getPlayer(),configuration(e.getPlayer()).getString("info.team"));
            }
        }

    }


    private void ticksAll(){
        Bukkit.getScheduler().runTaskTimer((Plugin)this, new Runnable() {@Override public void run() {
            ticksEvents();
        }},0L,15L);
    }


    @EventHandler
    private void QuitEvent(PlayerQuitEvent e){
        if (Lives.containsKey(e.getPlayer())){
            Lives.remove(e.getPlayer());
        }
        if (Teams.containsKey(e.getPlayer())){
            Teams.remove(e.getPlayer());
        }

    }

    private void ticksEvents(){
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

        Zombie e=null;
        for (World w : Bukkit.getWorlds()){
            for (Entity i : w.getEntities()){
                if (i instanceof Zombie&&i.hasMetadata("EvilBlas")){
                    e=(Zombie) i;
                    break;
                }
            }
        }

        if (world.getFullTime()>=300000 && !isBloodMoon){
            world.setFullTime(25000);
        }
    }

    public File getFilePlayer(OfflinePlayer p){
        String uuid = p.getUniqueId().toString();
        File folder = new File(this.getDataFolder() + File.separator + "players");
        File file = new File(folder + File.separator + uuid+".yml");
        return file;
    }


    public FileConfiguration configuration(OfflinePlayer p){
        FileConfiguration config;
        String uuid = p.getUniqueId().toString();
        File folder = new File(this.getDataFolder() + File.separator + "players");
        File file = new File(folder + File.separator + uuid+".yml");
        config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
        if (!file.exists()){
            return null;
        }
        return config;
    }


    private void registerCommands(){
        this.getCommand("afk").setExecutor(new AFK());
        this.getCommand("helltime").setExecutor(new PlayerDataManager());
        this.getCommand("hell").setExecutor(new HellCommand());
        this.getCommand("hc").setExecutor(new PrincipalCommand());
    }

    private void registerEvents(){
        Bukkit.getServer().getPluginManager().registerEvents(this,this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerDataManager(),this);
        Bukkit.getServer().getPluginManager().registerEvents(new AFK(),this);
        Bukkit.getServer().getPluginManager().registerEvents(new HellCommand(),this);
        Bukkit.getServer().getPluginManager().registerEvents(new Events(),this);
        Bukkit.getServer().getPluginManager().registerEvents(new EnderMan(),this);
        Bukkit.getServer().getPluginManager().registerEvents(new Chains(),this);
    }
    private void setupTabList(){
        this.tab = new PlayerListManager();
        tab.addHeaders("&6&l☠&c&l&oHellcraft&6&l☠");

        tab.addHeaders("&e&l☠&c&l&oHellcraft&e&l☠");

        tab.addHeaders("&a&l☠&c&l&oHellcraft&a&l☠");

        tab.addHeaders("&b&l☠&c&l&oHellcraft&b&l☠");

        tab.addHeaders("&9&l☠&c&l&oHellcraft&9&l☠");

        tab.addHeaders("&d&l☠&c&l&oHellcraft&d&l☠");

        tab.addHeaders("&f&l☠&c&l&oHellcraft&f&l☠");

        tab.addHeaders("&c&l☠&c&l&oHellcraft&c&l☠");

        tab.showTab();
    }


    private void CheckAFK(){
        NamespacedKey key = new NamespacedKey(this,"afk");
        Main plugin = this;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()){
                    if (!p.getPersistentDataContainer().has(key, PersistentDataType.BYTE)){
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            final double x = p.getLocation().getX();
                            final double y = p.getLocation().getY();
                            final double z = p.getLocation().getZ();
                            @Override
                            public void run() {
                                if (p.getLocation().getX()==x){
                                    if (p.getLocation().getY() == y){
                                        if (p.getLocation().getZ()==z){
                                            p.kickPlayer(format("&c&lHas tardado mucho en moverte"));
                                        }
                                    }
                                }
                            }
                        },minutesToTicks(5));
                    }
                }
            }
        },0,20);
    }


    private long minutesToTicks(long n){
        long a = n*20L;
        long b = a*60;
        return b;
    }


    @Override
    public void onDisable() {
        for (Player p : Bukkit.getOnlinePlayers()){
            if (p.getPersistentDataContainer().has(new NamespacedKey(this,"afk"),PersistentDataType.BYTE)&&AFK.activeAFK.containsKey(p)){
                p.getPersistentDataContainer().remove(new NamespacedKey(this,"afk"));
                AFK.activeAFK.remove(p);
            }
            if (Lives.containsKey(p))
            Lives.remove(p);
        }
        Bukkit.getConsoleSender().sendMessage(format("&6&lHellcraft out"));
    }

    public static String getPlayerGroup(Player player, Collection<String> possibleGroups) {
        for (String group : possibleGroups) {
            if (player.hasPermission("group." + group)) {
                return group;
            }
        }
        return null;
    }

    public ChunkGenerator getWorldGeneretor(){return new WorldsGeneretor(); }

    public static boolean isEvent(){return isEvent;}

    public static boolean isPlayerInGroup(Player player, String group) { return player.hasPermission("group." + group); }

    public static String format(String texto){
        return ChatColor.translateAlternateColorCodes('&',texto);
    }

    public int getHoraMinima(){return HoraMinima;}

    public void setHoraMinima(int i){HoraMinima=i;}

    public static Main getInstance(){return instance;}

    public Scoreboard getScoreboard(){return this.scoreboard;}
}
