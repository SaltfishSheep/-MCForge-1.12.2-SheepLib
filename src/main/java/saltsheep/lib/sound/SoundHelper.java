package saltsheep.lib.sound;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.util.SoundCategory;
import saltsheep.lib.network.DataSound;

public class SoundHelper {

    public static boolean playSoundToPlayerWithoutClient(EntityPlayerMP player, String soundName, SoundCategory category, float volume, float pitch) {
        player.connection.sendPacket(new SPacketCustomSound(soundName, category, player.posX, player.posY, player.posZ, volume, pitch));
        return true;
    }

    @Deprecated
    public static void playSoundToPlayerInServer(EntityPlayerMP player, String soundName, SoundCategory category, float volume, float pitch) {
        playSoundToPlayerInCommon(player, soundName, category, volume, pitch, false);
    }

    public static void playSoundToPlayerInCommon(EntityPlayerMP player, String soundName, SoundCategory category, float volume, float pitch, boolean isAttenuation) {
        DataSound.playSoundByServer(player, soundName, category, volume, pitch, isAttenuation);
    }

}
