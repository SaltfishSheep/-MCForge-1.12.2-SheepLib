package saltsheep.lib.common;

import javax.annotation.Nullable;

import io.netty.buffer.ByteBuf;
import saltsheep.lib.network.NetworkHelper;

public final class BaseType<T> {

    public static final BaseType<String> STRING = new BaseType<String>("", (byte) 5);
    public static final BaseType<Double> DOUBLE = new BaseType<Double>((double) 0, (byte) 3);
    public static final BaseType<Float> FLOAT = new BaseType<Float>((float) 0, (byte) 2);
    public static final BaseType<Long> LONG = new BaseType<Long>((long) 0, (byte) 1);
    public static final BaseType<Integer> INT = new BaseType<Integer>(0, (byte) 0);
    public static final BaseType<Character> CHAR = new BaseType<Character>(' ', (byte) 4);
    public static final BaseType<Boolean> BOOLEAN = new BaseType<Boolean>(false, (byte) 6);

    private final T defaultValue;
    final byte type;

    private BaseType(T defaultValue, byte type) {
        this.defaultValue = defaultValue;
        this.type = type;
    }

    public T defalut() {
        return this.defaultValue;
    }

    @SuppressWarnings("unchecked")
    public T format(Object obj) {
        return (T) obj;
    }

    public static <T> void write(ByteBuf buf, BaseType<T> typeIn, T value) {
        buf.writeByte(typeIn.type);
        if (value instanceof Integer)
            buf.writeInt((Integer) value);
        else if (value instanceof Long)
            buf.writeLong((Long) value);
        else if (value instanceof Float)
            buf.writeFloat((Float) value);
        else if (value instanceof Double)
            buf.writeDouble((Double) value);
        else if (value instanceof Character)
            buf.writeChar((Integer) value);
        else if (value instanceof String)
            NetworkHelper.writeString(buf, (String) value);
        else if (value instanceof Boolean)
            buf.writeBoolean((Boolean) value);
    }

    @Nullable
    public static ValueContainer read(ByteBuf buf) {
        byte type = buf.readByte();
        if (type == 0) {
            return ValueContainer.create(INT, buf.readInt());
        } else if (type == 1) {
            return ValueContainer.create(LONG, buf.readLong());
        } else if (type == 2) {
            return ValueContainer.create(FLOAT, buf.readFloat());
        } else if (type == 3) {
            return ValueContainer.create(DOUBLE, buf.readDouble());
        } else if (type == 4) {
            return ValueContainer.create(CHAR, buf.readChar());
        } else if (type == 5) {
            return ValueContainer.create(STRING, NetworkHelper.readString(buf));
        } else if (type == 6) {
            return ValueContainer.create(BOOLEAN, buf.readBoolean());
        } else {
            return null;
        }
    }

}
