package sebastnun.hellcraft.Entities;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.boss.BossBar;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import sebastnun.hellcraft.Main;

public class Boss implements Listener {

    private String name;

    private BossBar bar;

    private final TypeBoss typeBoss;

    private String modelID;

    private final Location loc;

    private final World world;

    public int phase;

    public Boss(TypeBoss typeBoss, Location loc, int phase){
        this.typeBoss = typeBoss;
        this.loc = loc;
        this.world = Main.getEventsWorld();
        this.phase = phase;

        switch (typeBoss){
            case ENDERMAN:
                name = Main.format("&2&l&oMutant Enderman");
                modelID = "enderman";
                break;
            case WILDFIRE:
                name = Main.format("&6WildFire");
                modelID = "";
                break;
            case EVILBLAS:
                name = ChatColor.RED+""+ChatColor.ITALIC+"Evil Blas";
                break;
            case WARDEN:

        }

    }



    //Private Metoths
    public void setPhase(int phase){this.phase = phase;}



    //Public Metoths
    public int getPhase() { return phase; }

    public World getWorld() { return world; }

    public void setName(String name) { this.name = name; }

    public BossBar getBar() { return bar; }

    public Location getSpawnLocation() { return loc; }

    public String getName() { return name; }

    public TypeBoss getTypeBoss(){return this.typeBoss;}

}

