package sebastnun.hellcraft.Entities;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.animation.Query;
import com.ticxo.modelengine.api.model.AbstractModeledEntity;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import org.bukkit.Location;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.entity.EntityDeathEvent;

public class WildFire extends ModelEngineAPI{

    public WildFire(Location loc){

        Blaze blaze = loc.getWorld().spawn(loc,Blaze.class);
        ModeledEntity me = ModelEngineAPI.api.getModelManager().createModeledEntity(blaze);
        if (me!=null){
            me.clearModels();
            me.getAllActiveModel().clear();
        }
        ActiveModel am = ModelEngineAPI.api.getModelManager().createActiveModel("wildfire.geo");
        me.addActiveModel(am);
        me.setInvisible(true);
    }

}
