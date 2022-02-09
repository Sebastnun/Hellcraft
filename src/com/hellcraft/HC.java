package com.hellcraft;

import com.hellcraft.Discord.DiscordManager;
import com.hellcraft.Events.Final.Mobs;
import com.hellcraft.Item.NebulaArmor;
import com.hellcraft.Item.RealNetherite;
import com.hellcraft.Item.SpecialItems;
import com.hellcraft.Util.Manager.DateManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class HC implements CommandExecutor{

    private Main main;
    public  HC(Main main){
        this.main = main;
    }
    private Mobs mobs = new Mobs();




    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)){

            if(args[0].equalsIgnoreCase("reload")) {
                main.reload(sender);
                return true;

            }else {
                sender.sendMessage(ChatColor.RED+"No puedes ejecutar este comando aqui");
            }
            return true;
        }else {
            Player player = (Player) sender;

            if (sender.hasPermission("hellcraft.admin")) {
                if (args[0].equalsIgnoreCase("vanish")) {
                    if (main.invisible_list.contains(player)) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.showPlayer(main, player);
                        }
                        TextComponent text_component = new TextComponent("Hello World");
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, text_component);
                        main.invisible_list.remove(player);
                        player.sendMessage(main.format("&2Ahora eres visible para los demas jugadores"));
                    } else if (!main.invisible_list.contains(player)) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.hidePlayer(main, player);
                        }
                        main.invisible_list.add(player);
                        player.sendMessage(main.format("&2Ahora eres invisible para los demas jugadores"));
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("dia")) {
                    long dia = DateManager.getInstance(main).getDays();
                    player.sendMessage(main.format("&6Estamos en el dia " + dia));
                    return true;
                } else if (args[0].equalsIgnoreCase("reload")) {
                    this.main.reload(player);
                    return true;


                } else if (args[0].equalsIgnoreCase("cambiarDia")) {
                    if (args.length <= 1) {
                        player.sendMessage(Main.format("&cNecesitas agregar un dia"));
                        player.sendMessage(Main.format("&eEjemplo: &7/hc cambiarDia <dia>"));
                        return true;
                    } else {
                        DateManager.getInstance(main).setDay(player, args[1]);
                    }

                    return true;
                } else if (args[0].equalsIgnoreCase("live")) {
                    if (!(player.hasPermission("hellcraft.admin"))) {
                        player.sendMessage(main.format("&cNo tienes permisos para ejecutar ese comando"));
                        return true;
                    } else {
                        Player p = Bukkit.getPlayer(args[2]);
                        if (player.hasPermission("hellcraft.lives")) {
                            if (args[1].equalsIgnoreCase("setlives")) {
                                if (p == null) {
                                    player.sendMessage(ChatColor.RED + "No se encotro un jugador");
                                    return true;
                                } else {
                                    int newlive = Integer.parseInt(args[3]);
                                    main.getF().getConfig().set("players." + p.getUniqueId().toString() + ".lives", newlive);
                                    main.getF().saveConfig();
                                    main.getF().reloadConfig();
                                    player.sendMessage(ChatColor.BLUE + "Ha sido recargadas las vidas del jugador " + p.getName() + " a " + newlive);
                                    return true;
                                }
                            } else if (args[1].equalsIgnoreCase("getlives")) {
                                int live = main.getF().getConfig().getInt("players." + p.getUniqueId() + ".lives");
                                player.sendMessage(ChatColor.AQUA + "Las vidas restantes del jugador son " + (live));
                                return true;
                            } else {
                                player.sendMessage(ChatColor.RED + "No se encontro ningun comando");
                                return true;
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "No tienes permiso");
                            return true;
                        }

                    }
                } else if (args[0].equalsIgnoreCase("magicApple")) {
                    player.sendMessage(main.format("&6Revisa si tienes espacio de tu inventario"));
                    player.getInventory().addItem(new SpecialItems().MagicApple());
                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                        player1.getPersistentDataContainer().remove(new NamespacedKey((Plugin) Main.getInstance(), "magic_apple"));
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("test")) {

                    return true;
                } else if (args[0].equalsIgnoreCase("realIngot")) {
                    player.sendMessage(main.format("&6Revisa si tienes espacio de tu inventario"));
                    player.getInventory().addItem(new RealNetherite().NetheriteIngot());
                    return true;
                } else if (args[0].equalsIgnoreCase("armamento")) {
                    SpecialItems armor = new SpecialItems();

                    player.sendMessage(main.format("&6Revisa si tienes espacio de tu inventario"));
                    player.getInventory().setHelmet(armor.CascoDelBoss());
                    player.getInventory().setChestplate(armor.ChestplateDelBoss());
                    player.getInventory().setLeggings(armor.LegginsDelBoss());
                    player.getInventory().setBoots(armor.BootsDelBoss());
                    player.getInventory().setItemInOffHand(new ItemStack(Material.TOTEM_OF_UNDYING, 99));
                    player.getInventory().addItem(armor.EspadaDelBoos());
                    player.getInventory().addItem(armor.ArcoDelBoos());
                    player.getInventory().addItem(armor.MagicApple());
                    player.getInventory().addItem(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 64));
                    player.getInventory().addItem(armor.FlechaDelBoos());
                    player.getInventory().addItem(armor.EscombroDeVida());

                    return true;
                } else if (args[0].equalsIgnoreCase("evilBlas")) {
                    if (main.BossDimencion == null) {
                        player.sendMessage(main.format("&cPon primero /hc start"));
                    } else {
                        player.sendMessage(main.format("&6Spawn"));
                        main.EvilBlas = true;
                        player.getInventory().addItem(mobs.EviLBlasTag());
                        mobs.spawnEvilBlas(player.getLocation());
                    }


                    return true;
                } else if (args[0].equalsIgnoreCase("hellWither")) {
                    player.sendMessage(main.format("&6Spawn"));
                    if (main.BossDimencion == null) {
                        player.sendMessage(main.format("&cPon primero /hc start"));
                    } else {
                        main.HellWither = true;
                        player.getInventory().addItem(mobs.WitherTag());
                        mobs.spawnHellWither(player.getLocation());
                    }


                    return true;
                } else if (args[0].equalsIgnoreCase("start")) {
                    player.sendMessage(main.format("&6Inicio"));
                    main.Adios = true;
                    mobs.phase2 = false;
                    mobs.phase1 = false;
                    mobs.isPhase1 = false;
                    for (World w : Bukkit.getWorlds()) {
                        if (w.getName().equalsIgnoreCase("final")) {
                            main.BossDimencion = w;
                        }
                    }
                    player.getInventory().addItem(mobs.FinalEye(12));
                    for (World w : Bukkit.getWorlds()) {
                        if (w.getEnvironment() == World.Environment.THE_END) {
                            if (w.getName().equalsIgnoreCase("end")) {
                                main.endWorld = w;
                            }
                        }
                    }
                    main.EvilBlas = false;
                    main.SkeletonKing = false;
                    main.HellWither = false;
                    player.sendMessage(main.format("&6Se ha registrado la dimension"));
                    return true;
                } else if (args[0].equalsIgnoreCase("skeletonKing")) {
                    if (main.BossDimencion == null) {
                        player.sendMessage(main.format("&cPon primero /hc start"));
                    } else {
                        player.sendMessage(main.format("&6Spawn"));
                        main.SkeletonKing = true;
                        player.getInventory().addItem(mobs.SkeletonKingTag());
                        player.getInventory().addItem(mobs.SkeletonDemonTag());
                        mobs.spawnSkeletoKing(player.getLocation());
                    }

                    return true;

                } else if (args[0].equalsIgnoreCase("nebula")) {
                    NebulaArmor armor = new NebulaArmor();

                    player.sendMessage(main.format("&6Revisa si tienes espacio de tu inventario"));
                    player.getInventory().addItem(armor.NebuluosIngot());
                    player.getInventory().addItem(armor.DeaethGem());

                    return true;
                }else if (args[0].equalsIgnoreCase("par")) {
                    new DiscordManager().onDataManagerEnable();
                    return true;
                }else if (args[0].equalsIgnoreCase("msg")) {
                    String message = main.format(args[1]);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendMessage(message.replace("_", " "));
                    }
                    return true;
                }else if(args[0].equalsIgnoreCase("realIngotAll")){
                    for (Player p : Bukkit.getOnlinePlayers()){
                        p.sendMessage(main.format("&6Revisa si tienes espacio de tu inventario"));
                        p.getInventory().addItem(new RealNetherite().NetheriteIngot());
                    }
                    return true;

                }else if (args[0].equalsIgnoreCase("kick")){
                    for (Player p : Bukkit.getOnlinePlayers()){
                        p.kickPlayer("Reinicio");
                    }
                    return true;
                } else if(args[0].equalsIgnoreCase("me")){
                    String message = main.format(args[1]);
                    player.sendMessage(message.replace("_"," "));
                    return true;

                }else if (args[0].equalsIgnoreCase("thor")) {
                    player.sendMessage(main.format("&6Revisa si tienes espacio de tu inventario"));
                    player.getInventory().addItem(new SpecialItems().LighningStick());
                    player.getInventory().addItem(new SpecialItems().MagicStick());
                    return true;

                } else {
                    player.sendMessage(ChatColor.RED + "Igresa algun subcomando");
                    return true;
                }
            }else if(args[0].equalsIgnoreCase("me")){
                String message = main.format(args[1]);
                player.sendMessage(message.replace("_"," "));
                return true;
            }else {
                sender.sendMessage(ChatColor.RED+"No tienes permiso para ejecutar ese comando");
                return true;
            }
        }
    }
}
