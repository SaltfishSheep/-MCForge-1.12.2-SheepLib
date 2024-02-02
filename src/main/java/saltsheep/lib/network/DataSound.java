package saltsheep.lib.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound.AttenuationType;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

public class DataSound {

    private static final String NAME = "SHEEPSOUNDDATA";
    static final FMLEventChannel CHANNEL = NetworkRegistry.INSTANCE.newEventDrivenChannel(NAME);

    public static void playSoundByServer(EntityPlayerMP player, String soundName, SoundCategory category, float volume, float pitch, boolean isAttenuation) {
        PacketBuffer bufSend = new PacketBuffer(Unpooled.buffer());
        NetworkHelper.writeString(bufSend, soundName);
        NetworkHelper.writeString(bufSend, category.getName());
        bufSend.writeFloat(volume);
        bufSend.writeFloat(pitch);
        bufSend.writeBoolean(isAttenuation);
        CHANNEL.sendTo(new FMLProxyPacket(bufSend, NAME), player);
    }

    @SubscribeEvent
    public static void playSoundByClient(FMLNetworkEvent.ClientCustomPacketEvent event) {
        if (event.getPacket().channel().equals(NAME)) {
            Minecraft MC = Minecraft.getMinecraft();
            ByteBuf buf = event.getPacket().payload();
            String soundName = NetworkHelper.readString(buf);
            String categoryName = NetworkHelper.readString(buf);
            float volume = buf.readFloat();
            float pitch = buf.readFloat();
            boolean isAttenuation = buf.readBoolean();
            AttenuationType attenuation = isAttenuation ? AttenuationType.LINEAR : AttenuationType.NONE;
            MC.addScheduledTask(() -> {
                MC.getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(soundName), SoundCategory.getByName(categoryName), volume, pitch, false, 0, attenuation, (float) MC.player.posX, (float) MC.player.posY, (float) MC.player.posZ));
            });
        }
    }

}
