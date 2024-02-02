package saltsheep.lib.world;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

public class WorldHelper {

    public static List<Entity> getNearbyEntities(WorldServer world, double x, double y, double z, double range) {
        AxisAlignedBB bb = (new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)).offset(new BlockPos(x, y, z)).grow(range, range, range);
        return world.getEntitiesWithinAABB(Entity.class, bb);
    }

    public static <T extends Entity> List<T> getNearbyEntities(WorldServer world, double x, double y, double z, double range, Class<T> type) {
        AxisAlignedBB bb = (new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)).offset(new BlockPos(x, y, z)).grow(range, range, range);
        return world.<T>getEntitiesWithinAABB(type, bb);
    }

}
