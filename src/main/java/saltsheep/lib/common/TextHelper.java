package saltsheep.lib.common;

import net.minecraft.util.text.TextComponentTranslation;

public class TextHelper {

    public static String getLang(String key) {
        return new TextComponentTranslation(key).getUnformattedComponentText();
    }

    public static String getItemStackName(String registryName) {
        return getLang("stacks." + registryName + ".name");
    }

    public static String[] getItemStackLores(String registryName) {
        String original = getLang("stacks." + registryName + ".lore");
        String[] result = original.split(":::");
        return result;
    }

}
