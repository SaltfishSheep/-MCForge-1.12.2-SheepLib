package saltsheep.lib.network;

import io.netty.buffer.ByteBuf;

public class NetworkHelper {

    public static void writeString(ByteBuf buf, String str) {
        buf.writeInt(str.length());
        for (char i : str.toCharArray())
            buf.writeChar(i);
    }

    public static String readString(ByteBuf buf) {
        char[] asArray = new char[buf.readInt()];
        for (int i = 0; i < asArray.length; i++)
            asArray[i] = buf.readChar();
        return new String(asArray);
    }

}
