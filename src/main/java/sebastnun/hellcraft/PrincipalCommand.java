package sebastnun.hellcraft;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.objects.Object2FloatFunction;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import sebastnun.hellcraft.Entities.EnderMan;
import sebastnun.hellcraft.Entities.EvilBlas;
import sebastnun.hellcraft.Entities.WildFire;
import sebastnun.hellcraft.Items.Chains;
import sebastnun.hellcraft.Util.Data.PlayerDataManager;
import sebastnun.hellcraft.Util.Enchantment.TestEnchant;

import java.util.ArrayList;
import java.util.List;

public class PrincipalCommand implements CommandExecutor {


    private final Main main = Main.getInstance();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)){
            Bukkit.getConsoleSender().sendMessage(Main.format("&cTu no eres Jugador"));
            return true;
        }else {
            Player p = (Player) sender;
            if (p.hasPermission("hellcraft.admin")) {
                if (args[0].equalsIgnoreCase("time")) {
                    try {
                        p.setStatistic(Statistic.PLAY_ONE_MINUTE, minutesToTicks(Integer.valueOf(args[1])));
                        p.sendMessage(format("&6Tu tiempo de juego es " + new PlayerDataManager().getTotalTime(p)));
                    } catch (Exception e) {
                        p.sendMessage(format("&cIngresa un valor de minutos"));
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("addtime")) {
                    try {
                        p.setStatistic(Statistic.PLAY_ONE_MINUTE, (p.getStatistic(Statistic.PLAY_ONE_MINUTE) + minutesToTicks(Integer.valueOf(args[1]))));
                        p.sendMessage(format("&6Tu tiempo de juego es " + new PlayerDataManager().getTotalTime(p)));
                    } catch (Exception e) {
                        p.sendMessage(format("&cIngresa un valor de minutos"));
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("moon")) {
                    p.sendMessage(Main.format("&6Luna de Sangre"));
                    BloodMoon(p);
                    return true;
                } else if (args[0].equalsIgnoreCase("item")) {
                    p.sendMessage(Main.format("&6Revisa si tienes espacio en tu inventario"));
                    p.getInventory().addItem(new Chains().Chain());
                    ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(format("&6Final Cut"));
                    List<String> lore = new ArrayList<>();
                    lore.add(ChatColor.GRAY+"Final Cut I");
                    meta.setLore(lore);
                    meta.addEnchant(TestEnchant.FINAL_CUT,1,true);
                    item.setItemMeta(meta);
                    p.getInventory().addItem(item);
                    return true;
                } else if (args[0].equalsIgnoreCase("restTime")) {
                    try {
                        p.setStatistic(Statistic.PLAY_ONE_MINUTE, (p.getStatistic(Statistic.PLAY_ONE_MINUTE) - minutesToTicks(Integer.valueOf(args[1]))));
                        p.sendMessage(format("&6Tu tiempo de juego es " + new PlayerDataManager().getTotalTime(p)));
                    } catch (Exception e) {
                        p.sendMessage(format("&cIngresa un valor de minutos"));
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("event")) {
                    p.teleport(Main.getEventsWorld().getSpawnLocation());
                    p.sendMessage(ChatColor.GOLD + "Has sido teletransportado a " + Main.getEventsWorld().getName());
                    return true;
                } else if (args[0].equalsIgnoreCase("mobEvent")) {
                    p.sendMessage(ChatColor.BLUE + "Se ha invocado el mob");
                    new EnderMan(p.getLocation(), sender);
                    return true;
                } else if (args[0].equalsIgnoreCase("chest")) {
                    try {
                        p.sendMessage(ChatColor.BLUE + "Se ha creado un cofre");
                        lockChest(p.getLocation());
                    } catch (Exception e) {
                        p.sendMessage(format("&c/hc chest <numero>"));
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("mob")) {
                    p.sendMessage(ChatColor.BLUE + "Se ha invocado el mob");
                    new EvilBlas(p.getLocation());
                    return true;
                }else if (args[0].equalsIgnoreCase("resetTime")) {
                    for(Player pl : Bukkit.getOnlinePlayers())
                        pl.setStatistic(Statistic.PLAY_ONE_MINUTE,0);
                    for (OfflinePlayer pl : Bukkit.getOfflinePlayers())
                        pl.setStatistic(Statistic.PLAY_ONE_MINUTE,0);
                    p.sendMessage(ChatColor.AQUA+"Se ha Reiniciado el Contador");
                    return true;
                }else {
                    p.sendMessage(format("&cIngresa un comando valido"));
                    return true;
                }

            }else {
                p.sendMessage(format("&cNo tienes permiso para ejecutar este comando"));
                return true;
            }

        }
    }

    private int minutesToTicks(int n){
        int a = n*20;
        int b = a*60;
        return b;
    }

    private void BloodMoon(Player p){
        if (p.getLocation().getWorld()!=Main.world)
            return;

        p.getWorld().setFullTime(300000);
    }

    private void lockChest(Location l){
        Block block = l.getBlock();
        block.setType(Material.CHEST);
        Chest chest = (Chest) block;
        chest.getPersistentDataContainer().set(key,PersistentDataType.BYTE,(byte)1);
    }


    private static NamespacedKey key = new NamespacedKey(Main.getInstance(),"chest");

    public static NamespacedKey getKey(){return key;}

    private static String format(String msg){
        return Main.format(msg);
    }


}
