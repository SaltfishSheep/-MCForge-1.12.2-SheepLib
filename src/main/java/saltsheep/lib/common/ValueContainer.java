package saltsheep.lib.common;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.netty.buffer.ByteBuf;

public class ValueContainer {

    public final BaseType<?> type;
    @Nonnull
    private Object value;
    private boolean isDirty;

    private ValueContainer(BaseType<?> type) {
        this.type = type;
        this.value = type.defalut();
    }

    private <T> ValueContainer(BaseType<T> type, T value) {
        this.type = type;
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public <T> T getValue(BaseType<T> type) {
        if (type == this.type)
            return (T) this.value;
        else
            return null;
    }

    public <T> void setValue(BaseType<T> type, T value) {
        if (type == this.type)
            this.value = value;
    }

    public void markDirty() {
        this.isDirty = true;
    }

    public boolean isDirty() {
        return this.isDirty;
    }

    @SuppressWarnings("unchecked")
    public static <T> void write(ByteBuf buf, ValueContainer container) {
        BaseType.write(buf, (BaseType<T>) container.type, (T) container.value);
    }

    public static ValueContainer read(ByteBuf buf) {
        return BaseType.read(buf);
    }

    public static <T> ValueContainer create(BaseType<T> type, T value) {
        return new ValueContainer(type, value);
    }

    public static <T> ValueContainer create(BaseType<T> type) {
        return new ValueContainer(type);
    }

}
