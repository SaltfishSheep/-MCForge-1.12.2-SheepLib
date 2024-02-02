package saltsheep.lib.data;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import saltsheep.lib.SheepLib;

public class CommonData extends WorldSavedData {

    public static final String NAME = "SHEEP_COMMON_DATA";

    //*Can only put base value,String,int[],byte[],and nbt,nbtlist.
    public final Map<String, Object> baseData = Maps.newHashMap();

    public CommonData() {
        this(NAME);
    }

    public CommonData(String name) {
        super(NAME);
    }

    public static CommonData getData() {
        WorldServer world = SheepLib.getMCServer().getWorld(0);
        MapStorage storage = world.getMapStorage();
        if (storage.getOrLoadData(CommonData.class, NAME) == null) {
            storage.setData(NAME, new CommonData());
        }
        return (CommonData) storage.getOrLoadData(CommonData.class, NAME);
    }

    public void markShouldSave() {
        this.markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        for (String key : nbt.getKeySet()) {
            int valueType = nbt.getTagId(key);
            Object value = null;
            switch (valueType) {
                case 1:
                    value = nbt.getByte(key);
                case 2:
                    value = nbt.getShort(key);
                case 3:
                    value = nbt.getInteger(key);
                case 4:
                    value = nbt.getLong(key);
                case 5:
                    value = nbt.getFloat(key);
                case 6:
                    value = nbt.getDouble(key);
                case 7:
                    value = nbt.getByteArray(key);
                case 8:
                    value = nbt.getString(key);
                case 9:
                    value = (NBTTagList) nbt.getTag(key);
                case 10:
                    value = nbt.getCompoundTag(key);
                case 11:
                    value = nbt.getIntArray(key);
            }
            baseData.put(key, value);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        for (Entry<String, Object> entry : baseData.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof NBTTagCompound) {
                compound.setTag(entry.getKey(), (NBTBase) ((NBTTagCompound) value));
            } else if (value instanceof NBTTagList) {
                compound.setTag(entry.getKey(), (NBTBase) ((NBTTagList) value));
            } else if (value instanceof String) {
                compound.setTag(entry.getKey(), (NBTBase) new NBTTagString((String) value));
            } else if (value instanceof Double) {
                compound.setTag(entry.getKey(), (NBTBase) new NBTTagDouble(((Double) value).doubleValue()));
            } else if (value instanceof Float) {
                compound.setTag(entry.getKey(), (NBTBase) new NBTTagFloat(((Float) value).floatValue()));
            } else if (value instanceof Integer) {
                compound.setTag(entry.getKey(), (NBTBase) new NBTTagInt(((Integer) value).intValue()));
            } else if (value instanceof Long) {
                compound.setTag(entry.getKey(), (NBTBase) new NBTTagLong(((Long) value).longValue()));
            } else if (value instanceof int[]) {
                compound.setTag(entry.getKey(), (NBTBase) new NBTTagIntArray((int[]) value));
            } else if (value instanceof Boolean) {
                compound.setBoolean(entry.getKey(), (boolean) value);
            } else if (value instanceof Byte) {
                compound.setByte(entry.getKey(), (byte) value);
            } else if (value instanceof byte[]) {
                compound.setByteArray(entry.getKey(), (byte[]) value);
            }
        }
        return compound;
    }

}
