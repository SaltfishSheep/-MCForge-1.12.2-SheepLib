package saltsheep.lib.network;

import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public abstract class PacketBase {

    /**
     * You must have one none param constructor.
     */
    public PacketBase() {
    }

    public abstract void readBuf(PacketBuffer buf) throws IOException;

    public abstract void writeBuf(PacketBuffer buf) throws IOException;

    public abstract void process();

    public String getRegisterName(){
        return this.getClass().getCanonicalName();
    }

}
