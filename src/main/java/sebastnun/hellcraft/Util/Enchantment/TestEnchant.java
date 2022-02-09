package sebastnun.hellcraft.Util.Enchantment;

import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TestEnchant {


    public static final Enchantment FINAL_CUT = new EnchantmentWrapper("final_cut","Final Cut", 1);

    public static void register(){
        boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(FINAL_CUT);

        if (!registered){
                registerEnchantment(FINAL_CUT);
        }

    }

    public static void registerEnchantment(Enchantment enchantment){
        boolean registered = true;

        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null,true);
            Enchantment.registerEnchantment(enchantment);
        }catch (Exception e){
            registered = false;
            e.printStackTrace();
        }
        if (registered){
            //send message to console
        }
    }


}
