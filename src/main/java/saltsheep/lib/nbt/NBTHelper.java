package saltsheep.lib.nbt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import saltsheep.lib.SheepLib;

public class NBTHelper {

    //*链式调用
    public static NBTTagCompound setTag(NBTTagCompound compound, String key, Object value) {
        if (value instanceof NBTTagCompound) {
            compound.setTag(key, (NBTBase) ((NBTTagCompound) value));
        } else if (value instanceof String) {
            compound.setTag(key, (NBTBase) new NBTTagString((String) value));
        } else if (value instanceof Double) {
            compound.setTag(key, (NBTBase) new NBTTagDouble(((Double) value).doubleValue()));
        } else if (value instanceof Float) {
            compound.setTag(key, (NBTBase) new NBTTagFloat(((Float) value).floatValue()));
        } else if (value instanceof Integer) {
            compound.setTag(key, (NBTBase) new NBTTagInt(((Integer) value).intValue()));
        } else if (value instanceof int[]) {
            compound.setTag(key, (NBTBase) new NBTTagIntArray((int[]) value));
        }
        return compound;
    }

    //*链式调用
    public static NBTTagCompound setList(NBTTagCompound compound, String key, Object[] value) {
        NBTTagList list = new NBTTagList();
        for (Object nbt : value) {
            if (nbt instanceof NBTTagCompound) {
                list.appendTag((NBTBase) ((NBTTagCompound) nbt));
            } else if (nbt instanceof String) {
                list.appendTag((NBTBase) new NBTTagString((String) nbt));
            } else if (nbt instanceof Double) {
                list.appendTag((NBTBase) new NBTTagDouble(((Double) nbt).doubleValue()));
            } else if (nbt instanceof Float) {
                list.appendTag((NBTBase) new NBTTagFloat(((Float) nbt).floatValue()));
            } else if (nbt instanceof Integer) {
                list.appendTag((NBTBase) new NBTTagInt(((Integer) nbt).intValue()));
            } else if (nbt instanceof int[]) {
                list.appendTag((NBTBase) new NBTTagIntArray((int[]) nbt));
            }
        }
        compound.setTag(key, (NBTBase) list);
        return compound;
    }

    public static NBTTagCompound stringToNbt(String str) {
        try {
            return JsonToNBT.getTagFromJson(str);
        } catch (NBTException e) {
            return new NBTTagCompound();
        }
    }

    public static String NBTToJSONString(NBTTagCompound nbt) {
        if (nbt != null && !nbt.isEmpty()) {
            return nbt.toString();
        } else return "{}";
    }

    @Nullable
    public static NBTTagCompound read(File file) {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        try {
            FileInputStream fileinputstream = new FileInputStream(file);
            nbttagcompound = CompressedStreamTools.readCompressed(fileinputstream);
            fileinputstream.close();
        } catch (IOException e) {
            SheepLib.printError(e);
        }
        return nbttagcompound;
    }

    public static void write(File file, NBTTagCompound nbt) {
        try {
            FileOutputStream fileoutputstream = new FileOutputStream(file);
            CompressedStreamTools.writeCompressed(nbt, fileoutputstream);
            fileoutputstream.close();
        } catch (IOException e) {
            SheepLib.printError(e);
        }
    }

}
