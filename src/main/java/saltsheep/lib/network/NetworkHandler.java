package saltsheep.lib.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.SoundCategory;

public class NetworkHandler {

    public static void register() {
        DataSound.CHANNEL.register(DataSound.class);
        PacketSender.CHANNEL.register(PacketSender.INSTANCE);
    }

    @Deprecated
    public static class SoundData {
        public static void playSoundByServer(EntityPlayerMP player, String soundName, SoundCategory records, float volume, float pitch) {
            DataSound.playSoundByServer(player, soundName, records, volume, pitch, false);
        }
    }

}
