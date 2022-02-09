package sebastnun.hellcraft.Team;

import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import sebastnun.hellcraft.Main;
import sebastnun.hellcraft.Util.Commands.AFK;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;


public class HellCommand implements CommandExecutor, Listener {


    private final String NAMEplus = "HELLBENCH++";
    private final String NAME = "HELLBENCH";

    private final int size = 54;

    private Collection<String> groups = new ArrayList<>();

    private NamespacedKey afk = new NamespacedKey(Main.getInstance(),"afk");


    @Override
    public boolean onCommand(CommandSender sender, Command command, String str, String[] args) {
        if (!(sender instanceof Player)){
            Bukkit.getConsoleSender().sendMessage(Main.format("&cTu no eres Jugador"));
            return true;
        }else {
            Player player = (Player)sender;


            player.openInventory(mainInventory(player));
            return true;
        }
    }


    @EventHandler
    private void onClickInventory(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();

        Inventory open = e.getClickedInventory();
        ItemStack item = e.getCurrentItem();

        if (open.getSize()==this.size && e.getView().getTitle().equalsIgnoreCase(NAME)||open.getSize()==this.size && e.getView().getTitle().equalsIgnoreCase(NAMEplus)){
            e.setCancelled(true);
            if (item.equals(null) || !item.hasItemMeta())return;



            if (!item.getType().equals(Material.BLACK_STAINED_GLASS_PANE))player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK,1,1);

            if (item.getType().equals(Material.PLAYER_HEAD)){

                //player.openInventory(HeadDisplay());
                openAnvilGUI(player,"Ingresa el texto",NAME.concat(" "));
            }
            if (item.getType().equals(Material.CRAFTING_TABLE)&&player.hasPermission("hellcraft.hell")){
                player.openWorkbench(player.getLocation(),true);
            }
            if (item.getType().equals(Material.ENDER_CHEST)&&player.hasPermission("hellcraft.hell")){
                player.openInventory(player.getEnderChest());
            }

            if (!item.hasItemMeta())return;


            if (item.getType().equals(Material.LIME_DYE)&&Objects.requireNonNull(item.getItemMeta()).getCustomModelData()==654321){
                List<String> textureLore = new ArrayList<>();
                textureLore.add(Main.format("&8Desactivado"));
                item.setType(Material.GRAY_DYE);
                ItemMeta meta = item.getItemMeta();
                meta.setLore(textureLore);
                meta.setCustomModelData(654321);
                item.setItemMeta(meta);
            }else if (item.getType().equals(Material.GRAY_DYE)&&item.getItemMeta().getCustomModelData()==654321){
                List<String> textureLore = new ArrayList<>();
                textureLore.add(Main.format("&aActivado"));
                item.setType(Material.LIME_DYE);
                ItemMeta meta = item.getItemMeta();
                meta.setLore(textureLore);
                meta.setCustomModelData(654321);
                item.setItemMeta(meta);
            }

            if (item.getType().equals(Material.PURPLE_DYE)&&item.getItemMeta().getCustomModelData()==123456
                    &&player.getPersistentDataContainer().has(afk, PersistentDataType.BYTE)&&AFK.contains(player)){
                AFK.removeAFK(player);
                List<String> textureLore = new ArrayList<>();
                textureLore.add(Main.format("&8Desactivado"));
                item.setType(Material.GRAY_DYE);
                ItemMeta meta = item.getItemMeta();
                meta.setCustomModelData(123456);
                meta.setLore(textureLore);
                item.setItemMeta(meta);
            }else if (item.getType().equals(Material.GRAY_DYE)&&item.getItemMeta().getCustomModelData()==123456
                    &&!AFK.contains(player)){
                AFK.addAFK(player,player.getStatistic(Statistic.PLAY_ONE_MINUTE));
                List<String> textureLore = new ArrayList<>();
                textureLore.add(Main.format("&aActivado"));
                item.setType(Material.PURPLE_DYE);
                ItemMeta meta = item.getItemMeta();
                meta.setCustomModelData(123456);
                meta.setLore(textureLore);
                item.setItemMeta(meta);
            }

        }
    }


    private Inventory mainInventory(Player player){
        //Declaracion y inicializacion del inventario
        Inventory inventory = Bukkit.createInventory(null,54,NAME);

        if (player.hasPermission("hellcraft.hell")){
            inventory = Bukkit.createInventory(null,54,NAMEplus);
        }

        //Declaracion de los items
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);

        ItemStack inexist = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);ItemMeta ineMeta = inexist.getItemMeta();ineMeta.setDisplayName(Main.format("&7/"));inexist.setItemMeta(ineMeta);

        SkullMeta metaHead = (SkullMeta)head.getItemMeta();metaHead.setDisplayName(Main.format("&6"+player.getName()));metaHead.setOwningPlayer((OfflinePlayer) player);head.setItemMeta(metaHead);


        ItemStack rango = new ItemStack(Material.DIRT);
        ItemMeta metaRango = rango.getItemMeta();

        ItemStack craft = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta craftMeta = craft.getItemMeta();
        craftMeta.setDisplayName(Main.format("&6Mesa de Crafteo"));
        List<String> loreCraft = new ArrayList<String>();
        loreCraft.add(Main.format("&dVentaja de"));
        loreCraft.add(Main.format("&dTwitch"));
        craftMeta.setLore(loreCraft);
        craft.setItemMeta(craftMeta);

        ItemStack ender = new ItemStack(Material.ENDER_CHEST);
        ItemMeta enderMeta = ender.getItemMeta();
        enderMeta.setDisplayName(Main.format("&5Cofre del Ender"));
        enderMeta.setLore(loreCraft);
        ender.setItemMeta(enderMeta);

        ItemStack texture = new ItemStack(Material.GRAY_DYE);
        ItemMeta textureMeta = texture.getItemMeta();
        List<String> desactivado = new ArrayList<>();
        List<String> activado = new ArrayList<>();
        activado.add(Main.format("&aActivado"));
        desactivado.add(Main.format("&8Desactivado"));
        textureMeta.setDisplayName(Main.format("&bTexture pack"));
        textureMeta.setLore(desactivado);
        textureMeta.setCustomModelData(654321);
        texture.setItemMeta(textureMeta);

        ItemStack afk = new ItemStack(Material.GRAY_DYE);
        ItemMeta afkMeta = afk.getItemMeta();
        afkMeta.setDisplayName(Main.format("&bAFK"));
        afkMeta.setLore(desactivado);
        afkMeta.setCustomModelData(123456);
        if (player.getPersistentDataContainer().has(this.afk,PersistentDataType.BYTE)&&AFK.contains(player)){
            afk.setType(Material.PURPLE_DYE);
            afkMeta.setLore(activado);
        }
        afk.setItemMeta(afkMeta);


        this.incialisateCollections();
        switch (Main.getPlayerGroup(player,groups)){
            case "hierro":
                rango.setType(Material.IRON_BLOCK);
                metaRango.setDisplayName(Main.format("&cRango: &fHierro"));
                break;
            case "oro":
                rango.setType(Material.GOLD_BLOCK);
                metaRango.setDisplayName(Main.format("&cRango: &6Oro"));
                break;
            case "diamante":
                rango.setType(Material.DIAMOND_BLOCK);
                metaRango.setDisplayName(Main.format("&cRango: &bDiamante"));
                break;
            case "twitch":
                rango.setType(Material.PURPLE_CONCRETE);
                metaRango.setDisplayName(Main.format("&cRango: &dTwitch"));
                break;
            case "netherite":
                rango.setType(Material.NETHERITE_BLOCK);
                metaRango.setDisplayName(Main.format("&cRango: &0Netherite"));
                break;
            default:
                rango.setType(Material.DIRT);
                metaRango.setDisplayName(Main.format("&cRango: &7Ninguno"));
                break;
        }

        if (player.hasPermission("hellcraft.admin")){
            rango.setType(Material.COMMAND_BLOCK);
            metaRango.setDisplayName(Main.format("&cRango: &6&k!&r&4&lAdmin&6&k!"));
        }

        rango.setItemMeta(metaRango);

        for (int i=0; i<inventory.getSize(); i++){
            inventory.setItem(i,inexist);
        }


        inventory.setItem(4,head);
        inventory.setItem(16,rango);
        inventory.setItem(21,texture);
        inventory.setItem(22,afk);

        if (player.hasPermission("hellcraft.hell")){
            inventory.setItem(37,ender);
            inventory.setItem(10,craft);
        }


        return  inventory;
    }



    private Inventory HeadDisplay(){
        Inventory inventory = Bukkit.createInventory(null,54,NAME);

        ItemStack inexist = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta ineMeta = inexist.getItemMeta();
        ineMeta.setDisplayName(Main.format("&7/"));
        inexist.setItemMeta(ineMeta);


        for (int i=0; i<inventory.getSize(); i++){
            inventory.setItem(i,inexist);
        }


        return inventory;
    }


    private void incialisateCollections(){
        groups.add("hierro");
        groups.add("oro");
        groups.add("diamante");
        groups.add("twich");
        groups.add("netherite");
    }



    private String openAnvilGUI(Player player, String text, String title){
        AtomicReference<String> a = new AtomicReference<>("");
        new AnvilGUI.Builder()
                .text(text)
                .title(title)
                .itemLeft(new ItemStack(Material.PAPER))
                .plugin(Main.getInstance())
                .onComplete((player1, text1) -> {
                    a.set(text1);
                    player.openInventory(mainInventory(player,a.get()));
                    return AnvilGUI.Response.close();
                })
                .open(player);

        String reference = (String) a.get();

        return reference;
    }


    private Inventory mainInventory(Player player, String text){
        //Declaracion y inicializacion del inventario
        Inventory inventory = Bukkit.createInventory(null,54,NAME);

        //Declaracion de los items
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);

        ItemStack inexist = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);ItemMeta ineMeta = inexist.getItemMeta();ineMeta.setDisplayName(Main.format("&7/"));inexist.setItemMeta(ineMeta);

        SkullMeta metaHead = (SkullMeta)head.getItemMeta();metaHead.setDisplayName(Main.format(text));metaHead.setOwningPlayer((OfflinePlayer) player);head.setItemMeta(metaHead);


        ItemStack rango = new ItemStack(Material.DIRT);
        ItemMeta metaRango = rango.getItemMeta();

        this.incialisateCollections();
        switch (Main.getPlayerGroup(player,groups)){
            case "hierro":
                rango.setType(Material.IRON_BLOCK);
                metaRango.setDisplayName(Main.format("&cRango: &fHierro"));
                break;
            case "oro":
                rango.setType(Material.GOLD_BLOCK);
                metaRango.setDisplayName(Main.format("&cRango: &6Oro"));
                break;
            case "diamante":
                rango.setType(Material.DIAMOND_BLOCK);
                metaRango.setDisplayName(Main.format("&cRango: &bDiamante"));
                break;
            case "twitch":
                rango.setType(Material.PURPLE_CONCRETE);
                metaRango.setDisplayName(Main.format("&cRango: &dTwitch"));
                break;
            case "netherite":
                rango.setType(Material.NETHERITE_BLOCK);
                metaRango.setDisplayName(Main.format("&cRango: &0Netherite"));
                break;
            default:
                rango.setType(Material.DIRT);
                metaRango.setDisplayName(Main.format("&cRango: &7Ninguno"));
                break;
        }

        if (player.isOp()){
            rango.setType(Material.COMMAND_BLOCK);
            metaRango.setDisplayName(Main.format("&cRango: &6&k!&r&4&lAdmin&6&k!"));
        }

        rango.setItemMeta(metaRango);



        for (int i=0; i<inventory.getSize(); i++){
            inventory.setItem(i,inexist);
        }


        inventory.setItem(4,head);
        inventory.setItem(16,rango);

        return  inventory;
    }




}
