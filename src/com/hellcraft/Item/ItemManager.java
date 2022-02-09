package com.hellcraft.Item;

import com.hellcraft.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class ItemManager implements Listener {

    private Main main = Main.getInstance();
    private RealNetherite realNetherite = new RealNetherite();
    private NebulaArmor nebulaArmor = new NebulaArmor();
    private ItemStack ingot = nebulaArmor.NebuluosIngot();
    private ItemStack gem = nebulaArmor.DeaethGem();



    @EventHandler
    public void onPlayerCraftEvent(PrepareItemCraftEvent e){
        if(e.getInventory().getMatrix().length<9){
            return;
        }

        //Real Netherite Stick
        StickRecipe(e);

        //Real Netherite Sword
        checkCraft(new RealNetherite().NetheriteSword(), e.getInventory(), new HashMap<Integer, ItemStack>(){{
            put(4,new RealNetherite().NetheriteIngot()); put(7,realNetherite.NetheriteStick()); put(1, realNetherite.NetheriteIngot());
        }});

        //Real Netherite Pickaxe
        checkCraft(new RealNetherite().NetheritePickaxe(), e.getInventory(), new HashMap<Integer, ItemStack>(){{
            put(4,new RealNetherite().NetheriteStick()); put(7,realNetherite.NetheriteStick()); put(1, realNetherite.NetheriteIngot());
            put(0, realNetherite.NetheriteIngot()); put(2, realNetherite.NetheriteIngot());
        }});

        //Real Netherite Axe
        checkCraft(new RealNetherite().NetheriteAxe(), e.getInventory(), new HashMap<Integer, ItemStack>(){{
            put(4,new RealNetherite().NetheriteStick()); put(7,realNetherite.NetheriteStick()); put(1, realNetherite.NetheriteIngot());
            put(2, realNetherite.NetheriteIngot()); put(5, realNetherite.NetheriteIngot());
        }});
        checkCraft(new RealNetherite().NetheriteAxe(), e.getInventory(), new HashMap<Integer, ItemStack>(){{
            put(4,new RealNetherite().NetheriteStick()); put(7,realNetherite.NetheriteStick()); put(1, realNetherite.NetheriteIngot());
            put(0, realNetherite.NetheriteIngot()); put(3, realNetherite.NetheriteIngot());
        }});

        //Real Netherite Shovel
        checkCraft(new RealNetherite().NetheriteShovel(), e.getInventory(), new HashMap<Integer, ItemStack>(){{
            put(4,new RealNetherite().NetheriteStick()); put(7,realNetherite.NetheriteStick()); put(1, realNetherite.NetheriteIngot());
        }});

        //Real Netherite Hoe
        checkCraft(new RealNetherite().NetheriteHoe(), e.getInventory(), new HashMap<Integer, ItemStack>(){{
            put(4,new RealNetherite().NetheriteStick()); put(7,realNetherite.NetheriteStick());
            put(1, realNetherite.NetheriteIngot()); put(2,realNetherite.NetheriteIngot());
        }});
        checkCraft(new RealNetherite().NetheriteHoe(), e.getInventory(), new HashMap<Integer, ItemStack>(){{
            put(4,new RealNetherite().NetheriteStick()); put(7,realNetherite.NetheriteStick());
            put(1, realNetherite.NetheriteIngot()); put(0,realNetherite.NetheriteIngot());
        }});

        //Real Netherite Helmet
        checkCraft(new RealNetherite().HelmetNetherite(), e.getInventory(), new HashMap<Integer, ItemStack>(){{
            put(0,realNetherite.NetheriteIngot()); put(1,realNetherite.NetheriteIngot()); put(2,realNetherite.NetheriteIngot());
            put(3,realNetherite.NetheriteIngot()); put(5,realNetherite.NetheriteIngot());
        }});
        checkCraft(new RealNetherite().HelmetNetherite(), e.getInventory(), new HashMap<Integer, ItemStack>(){{
            put(3,realNetherite.NetheriteIngot()); put(4,realNetherite.NetheriteIngot()); put(5,realNetherite.NetheriteIngot());
            put(6,realNetherite.NetheriteIngot()); put(8,realNetherite.NetheriteIngot());
        }});

        //Real Netherite Chestplate
        checkCraft(new RealNetherite().ChestplateNetherite(), e.getInventory(), new HashMap<Integer, ItemStack>(){{
            put(0,realNetherite.NetheriteIngot()); put(2,realNetherite.NetheriteIngot());
            put(3,realNetherite.NetheriteIngot());put(4,realNetherite.NetheriteIngot());put(5,realNetherite.NetheriteIngot());
            put(6,realNetherite.NetheriteIngot());put(7,realNetherite.NetheriteIngot());put(8,realNetherite.NetheriteIngot());
        }});

        //Real Netherite Leggins
        checkCraft(new RealNetherite().LeggingsNetherite(), e.getInventory(), new HashMap<Integer, ItemStack>(){{
            put(0,realNetherite.NetheriteIngot()); put(2,realNetherite.NetheriteIngot());
            put(3,realNetherite.NetheriteIngot());put(1,realNetherite.NetheriteIngot());put(5,realNetherite.NetheriteIngot());
            put(6,realNetherite.NetheriteIngot());put(8,realNetherite.NetheriteIngot());
        }});

        //Real Netherite Boots
        checkCraft(new RealNetherite().BootsNetherite(), e.getInventory(), new HashMap<Integer, ItemStack>(){{
            put(0,realNetherite.NetheriteIngot()); put(3,realNetherite.NetheriteIngot());
            put(2, realNetherite.NetheriteIngot()); put(5,realNetherite.NetheriteIngot());
        }});
        checkCraft(new RealNetherite().BootsNetherite(), e.getInventory(), new HashMap<Integer, ItemStack>(){{
            put(3, realNetherite.NetheriteIngot()); put(5,realNetherite.NetheriteIngot());
            put(6,realNetherite.NetheriteIngot()); put(8,realNetherite.NetheriteIngot());
        }});



    }



    public void checkCraft(ItemStack result, CraftingInventory inv, HashMap<Integer, ItemStack> ingredients){
        ItemStack[] matrix = inv.getMatrix();
        for(int i=0;i<9;i++){
            if(ingredients.containsKey(i)){
                if (matrix[i]==null || !matrix[i].equals(ingredients.get(i))){
                    return;
                }
            }else {
                if(matrix[i] != null){
                    return;
                }
            }
        }
        inv.setResult(result);

    }

    public void StickRecipe(PrepareItemCraftEvent e){
        for (int i=0; i<=6; i++){
            int finalI = i;
            checkCraft(new RealNetherite().NetheriteStick(), e.getInventory(), new HashMap<Integer, ItemStack>(){{
                put(finalI, realNetherite.NetheriteIngot()); put(finalI +3,realNetherite.NetheriteIngot());
            }});
        }
    }


    @EventHandler
    public void NebulaRecipes(PrepareItemCraftEvent e){
        if(e.getInventory().getMatrix().length<9){
            return;
        }

        //Nebula Helmet
        checkCraft(nebulaArmor.NebulaHelmet(), e.getInventory(), new HashMap<Integer, ItemStack>(){{
            put(0,ingot); put(1,gem); put(2,ingot);
            put(3,ingot); put(5,ingot);
        }});
        checkCraft(nebulaArmor.NebulaHelmet(), e.getInventory(), new HashMap<Integer, ItemStack>(){{
            put(3,ingot); put(4,gem); put(5,ingot);
            put(6,ingot); put(8,ingot);
        }});


        //Nebula Chestplate
        checkCraft(nebulaArmor.NebulaChestplate(), e.getInventory(), new HashMap<Integer, ItemStack>(){{
            put(0,ingot); put(2,ingot);
            put(3,ingot);put(4,gem);put(5,ingot);
            put(6,ingot);put(7,ingot);put(8,ingot);
        }});


        //Nebula Leggins
        checkCraft(nebulaArmor.NebulaLeggins(), e.getInventory(), new HashMap<Integer, ItemStack>(){{
            put(0,ingot); put(2,ingot);
            put(3,ingot);put(1,gem);put(5,ingot);
            put(6,ingot);put(8,ingot);
        }});


        //Nebula Boots
        checkCraft(nebulaArmor.NebulaBoots(), e.getInventory(), new HashMap<Integer, ItemStack>(){{
            put(0,ingot); put(2,ingot);
            put(3, gem); put(5,gem);
        }});
        checkCraft(nebulaArmor.NebulaBoots(), e.getInventory(), new HashMap<Integer, ItemStack>(){{
            put(3, ingot); put(5,ingot);
            put(6,gem); put(8,gem);
        }});


        //Nebula Elytras
        checkCraft(nebulaArmor.NebulaElytras(), e.getInventory(), new HashMap<Integer, ItemStack>(){{
            put(1,gem);
            put(3,ingot);put(4,new ItemStack(Material.ELYTRA));put(5,ingot);
            put(6,gem);put(8,gem);
        }});


        //Magic Apple


    }

    @EventHandler
    public void AppleCraft(PrepareItemCraftEvent e){
        if(e.getInventory().getMatrix().length<9){
            return;
        }
        checkCraft(new SpecialItems().MagicApple(), e.getInventory(), new HashMap<Integer, ItemStack>(){{
            put(0,GoldenBlock());put(1,GoldenBlock());put(2,GoldenBlock());
            put(3,GoldenBlock());put(4,new ItemStack(Material.GOLDEN_APPLE));put(5,GoldenBlock());
            put(6,GoldenBlock());put(7,GoldenBlock());put(8,GoldenBlock());
        }});
    }




    private ItemStack GoldenBlock(){
        ItemStack stack = new ItemStack(Material.GOLD_BLOCK);
        stack.setAmount(2);
        return stack;
    }





}
