package sebastnun.hellcraft.Entities;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import sebastnun.hellcraft.Items.Chains;
import sebastnun.hellcraft.Main;


public class EvilBlas {


    public EvilBlas(Location loc){
        Zombie z = loc.getWorld().spawn(loc,Zombie.class);

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        ItemMeta HelMeta = helmet.getItemMeta();
        HelMeta.setUnbreakable(true);
        helmet.setItemMeta(HelMeta);

        z.getEquipment().setHelmet(helmet);
        z.setMetadata("EvilBlas",new FixedMetadataValue(Main.getInstance(),"evilblas"));
        z.setCustomName(ChatColor.RED+""+ChatColor.ITALIC+"Evil Blas");
        z.setPersistent(true);
    }


    public void Boat(Location loc){
        Boat b = loc.getWorld().spawn(loc,Boat.class);
        
    }

}
