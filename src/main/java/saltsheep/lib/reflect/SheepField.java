package saltsheep.lib.reflect;

import java.lang.reflect.Field;

import saltsheep.lib.SheepLib;

public final class SheepField {

    public final Field raw;

    public SheepField(Field raw) {
        this.raw = raw;
        if (this.raw != null)
            this.raw.setAccessible(true);
    }

    public Object get(Object owner) {
        try {
            if (this.raw == null) {
                SheepLib.getLogger().warn("It is a disaster, you are trying to control a undefined field.");
                return null;
            }
            return this.raw.get(owner);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void set(Object owner, Object value) {
        try {
            if (this.raw == null) {
                SheepLib.getLogger().warn("It is a disaster, you are trying to control a undefined field.");
                return;
            }
            this.raw.set(owner, value);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
