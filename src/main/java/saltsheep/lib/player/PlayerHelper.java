package saltsheep.lib.player;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import saltsheep.lib.SheepLib;
import saltsheep.lib.nbt.SheepNBT;

@EventBusSubscriber
public class PlayerHelper {

    public static TextComponentString getMessageSend(String str) {
        return new TextComponentString(str);
    }

    public static void setMaxHealth(EntityPlayer player, double value) {
        player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(value);
    }

    public static float getHunger(EntityPlayer player, boolean isSaturation) {
        return isSaturation ? player.getFoodStats().getSaturationLevel() : player.getFoodStats().getFoodLevel();
    }

    @SuppressWarnings("deprecation")
    public static void setHunger(EntityPlayer player, boolean isSaturation, float amount) {
        if (isSaturation)
            saltsheep.lib.reflect.ReflectHelper.setMCFieldByClass(FoodStats.class, player.getFoodStats(), "foodSaturationLevel", "field_75125_b", amount);
        else
            player.getFoodStats().setFoodLevel((int) amount);
    }

    public static void giveOrDropItem(EntityPlayer player, ItemStack item) {
        if (item != null && player != null)
            if (!player.inventory.addItemStackToInventory(item))
                player.dropItem(item, true, true);
    }

    public static SheepNBT getSheepData(EntityPlayer player) {
        return new SheepNBT(player.getEntityData()).getOrCreateCompound("SaltSheepData");
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        try {
            if (!event.getEntityPlayer().world.isRemote) {
                event.getEntityPlayer().getEntityData().setTag("SaltSheepData", PlayerHelper.getSheepData(event.getOriginal()).getMCNBT());
            }
        } catch (Throwable error) {
            SheepLib.printError(error);
        }
    }

}
