package com.hellcraft.Events;

import com.hellcraft.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class ArmorEvent implements Listener {
    private Main main = Main.getInstance();

    public ArmorEvent(Main main) {
        this.main = main;
    }


    public static boolean isNetheritePiece(ItemStack s) {
        if (s == null)
            return false;
        if (s.hasItemMeta())
            if(s.getItemMeta().hasCustomModelData())
                if (s.getItemMeta().getCustomModelData()==1234567)
                    if (s.getType()==Material.NETHERITE_HELMET||s.getType()==Material.NETHERITE_CHESTPLATE||s.getType()==Material.NETHERITE_LEGGINGS||s.getType()==Material.NETHERITE_BOOTS){
                        return s.getItemMeta().isUnbreakable();
                    }



        return false;
    }

    public static boolean isNebulaPiece(ItemStack s){
        if (s == null)
            return false;
        if (s.hasItemMeta())
            if (s.getItemMeta().isUnbreakable())
                if (s.getType()==Material.NETHERITE_HELMET||s.getType()==Material.ELYTRA||s.getType()==Material.NETHERITE_CHESTPLATE||s.getType()==Material.NETHERITE_LEGGINGS||s.getType()==Material.NETHERITE_BOOTS){
                    if(s.getItemMeta().hasCustomModelData()){
                        return s.getItemMeta().getCustomModelData()==7654321;
                    }

                }

        return false;
    }

    public static boolean isBossPiece(ItemStack s){
        if (s == null)
            return false;
        if (s.hasItemMeta())
            if (s.getItemMeta().isUnbreakable())
                if (s.getType()==Material.NETHERITE_HELMET||s.getType()==Material.ELYTRA||s.getType()==Material.NETHERITE_CHESTPLATE||s.getType()==Material.NETHERITE_LEGGINGS||s.getType()==Material.NETHERITE_BOOTS){
                    if(s.getItemMeta().hasCustomModelData()){
                        return s.getItemMeta().getCustomModelData() == 6666666;
                    }
                }

        return false;
    }





    public static void setupHealth(Player p) {
        Double maxHealth = getAvaibleMaxHealth(p);
        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth.doubleValue());
    }


    public static Double getAvaibleMaxHealth(Player p) {
        int currentNetheritePieces = 0;
        int currentNebulaPieces = 0;
        int currentBossPieces = 0;

        boolean doPlayerAteOne = p.getPersistentDataContainer().has(new NamespacedKey((Plugin) Main.getInstance(), "magic_apple"), PersistentDataType.BYTE);
        for (ItemStack contents : p.getInventory().getArmorContents()) {
            if (isNetheritePiece(contents))
                currentNetheritePieces++;
            if (isNebulaPiece(contents))
                currentNebulaPieces++;
            if (isBossPiece(contents))
                currentBossPieces++;
        }
        Double maxHealth = Double.valueOf(20.0D);
        maxHealth = Double.valueOf(maxHealth.doubleValue()-8.0D);
        if (doPlayerAteOne){
            maxHealth = Double.valueOf(maxHealth.doubleValue() + 4.0D);
        }
        if (currentNetheritePieces >= 4){
            maxHealth = Double.valueOf(maxHealth.doubleValue()+8.0D);
        }
        if (currentBossPieces >= 4){
            maxHealth = Double.valueOf(maxHealth.doubleValue()+44.0D);
        }
        if(currentNebulaPieces >= 4){
            maxHealth = Double.valueOf(maxHealth.doubleValue()+12.0D);
        }



        return Double.valueOf(Math.max(maxHealth.doubleValue(), 1.0E-6D));
    }

    public static Boolean isRealNetherite(Player p){
        int currentNetheritePieces = 0;

        for (ItemStack contents : p.getInventory().getArmorContents()) {
            if (isNetheritePiece(contents))
                currentNetheritePieces++;
        }
        if (currentNetheritePieces>=4){
            return true;
        }else {
            return false;
        }
    }

    public static Boolean isBoss(Player p){
        int currentNetheritePieces = 0;

        for (ItemStack contents : p.getInventory().getArmorContents()) {
            if (isBossPiece(contents))
                currentNetheritePieces++;
        }
        if (currentNetheritePieces>=4){
            return true;
        }else {
            return false;
        }
    }





}
