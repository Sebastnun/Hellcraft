package com.hellcraft.CompleterCommands;

import com.hellcraft.Events.Player.JoinEvent;
import com.hellcraft.Main;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class TexturePack implements Listener, CommandExecutor {

    private Main main = Main.getInstance();

    private String texture = "https://www.dropbox.com/s/2s23ellpmr3jg6h/Hellcraft%20Texture.zip?dl=1";

    @EventHandler
    private void getTexture(PlayerJoinEvent e){
        if(e.getPlayer().getPersistentDataContainer().has(new NamespacedKey((Plugin) Main.getInstance(), "texture"), PersistentDataType.BYTE)){
            e.getPlayer().setTexturePack(texture);
        }
    }




    public boolean onCommand(CommandSender sender,Command command,String s,String[] args){
        if (!(sender instanceof Player)) {
            sender.sendMessage("No puedes, no eres jugador");
            return true;
        }else {
            Player player = (Player)sender;
            if (player.getPersistentDataContainer().has(new NamespacedKey((Plugin) Main.getInstance(), "texture"), PersistentDataType.BYTE)){
                player.sendMessage(main.format("&6El paquete de texturas ha sido desactivado"));
                player.getPersistentDataContainer().remove(new NamespacedKey(main,"texture"));
                player.setTexturePack("https://www.dropbox.com/s/2s23ellpmr3jg6h/Hellcraft%20Texture.zip?dl=0");
                return true;
            }else{
                player.sendMessage(main.format("&6Ahora tienes el texture pack oficial de &cHellcraft"));
                player.getPersistentDataContainer().set(new NamespacedKey((Plugin)Main.getInstance(), "texture"), PersistentDataType.BYTE, Byte.valueOf((byte)1));
                player.setTexturePack(texture);
                return true;
            }

        }
    }

}
