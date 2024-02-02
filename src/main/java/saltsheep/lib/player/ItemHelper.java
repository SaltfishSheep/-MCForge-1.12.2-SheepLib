package saltsheep.lib.player;

import java.util.Map;

import com.google.common.collect.Multimap;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import saltsheep.lib.nbt.SheepNBT;

public class ItemHelper {

    public static void setLore(ItemStack item, String[] lore) {
        if (!item.hasTagCompound())
            item.setTagCompound(new NBTTagCompound());
        new SheepNBT(item.getTagCompound()).getOrCreateCompound("display").setList("Lore", lore);
    }

    //*如果你使用了inventoryItemStacks
    public static ItemStack getRealItemFromContainer(Container container, int slotIn) {
        return container.inventorySlots.get(slotIn).getStack();
    }

    //*尽管MC会有自动更新，但是由于可能存在的执行顺序问题，建议进行更新
    public static void updataContainerChange(Container container) {
        container.detectAndSendChanges();
    }

    //*其默认为主手
    public static double getAttribute(ItemStack item, String name) {
        NBTTagCompound compound = item.getTagCompound();
        if (compound == null) {
            return 0.0D;
        }
        Multimap<String, AttributeModifier> map = item.getAttributeModifiers(EntityEquipmentSlot.MAINHAND);
        for (Map.Entry<String, AttributeModifier> entry : (Iterable<Map.Entry<String, AttributeModifier>>) map.entries()) {
            if (((String) entry.getKey()).equals(name)) {
                AttributeModifier mod = entry.getValue();
                return mod.getAmount();
            }
        }
        return 0.0D;
    }

}
