package saltsheep.lib.reflect;

public final class SheepObject {

    //public static final SheepObject EMPTY = new SheepObject(new Object());

    public final Object raw;

    public SheepObject(Object raw) {
        this.raw = raw;
    }

    public SheepClass rawClass() {
        return SheepClass.instance(raw.getClass());
    }

    /**
     * @param deobf     the name (the one looks good).
     * @param universal the name unreadable. (But you can think it is the another name)
     * @param params    the params input in method(for the param like Object... arg0, input Object[]).
     * @return invoke the method if the object's class has.
     */
    public SheepObject invoke(String deobf, String universal, Object... params) {
        String[] names = universal.equals(deobf) ? new String[]{deobf} : new String[]{deobf, universal};
        SheepMethodVirtual vir = this.rawClass().findMethodVirtual(names);
        Object result = vir.invoke(this.raw, params);
        return result == null ? null : new SheepObject(result);
    }

    /**
     * @param deobf     the name (the one looks good).
     * @param universal the name unreadable. (But you can think it is the another name)
     * @param param     the param input in method(for the param like Object... arg0, input Object[]).
     * @return invoke the method if the object's class has.
     */
    public SheepObject invoke(String deobf, String universal, Object param) {
        return invoke(deobf, universal, new Object[]{param});
    }

    /**
     * The things you must concern:
     * this invoke only contains the static method under this object's class!
     *
     * @param deobf     the name (the one looks good).
     * @param universal the name unreadable. (But you can think it is the another name)
     * @param params    the params input in method(for the param like Object... arg0, input Object[]).
     * @return invoke the method if the object's class has.
     */
    public SheepObject invokeStatic(String deobf, String universal, Object... params) {
        String[] names = universal.equals(deobf) ? new String[]{deobf} : new String[]{deobf, universal};
        SheepMethodStatic st = this.rawClass().findMethodStatic(names);
        Object result = st.invoke(this.raw, params);
        return result == null ? null : new SheepObject(result);
    }

    /**
     * The things you must concern:
     * this invoke only contains the static method under this object's class!
     *
     * @param deobf     the name (the one looks good).
     * @param universal the name unreadable. (But you can think it is the another name)
     * @param param     the param input in method(for the param like Object... arg0, input Object[]).
     * @return invoke the method if the object's class has.
     */
    public SheepObject invokeStatic(String deobf, String universal, Object param) {
        return invokeStatic(deobf, universal, new Object[]{param});
    }

    /**
     * @param classCanonicalName such as saltsheep.lib.asm.Test.B (B is a inner static class)
     * @param deobf              the name looks good.
     * @param universal          the name unreadable. (But you can think it is the another name)
     * @param params             the params input in method(for the param like Object... arg0, input Object[]).
     * @return invoke the method in target class.
     */
    public SheepObject invokeSpecial(String classCanonicalName, String deobf, String universal, Object... params) {
        String[] names = universal.equals(deobf) ? new String[]{deobf} : new String[]{deobf, universal};
        Class<?> targetC = null;
        for (Class<?> nowc = this.raw.getClass(); nowc != null; nowc = nowc.getSuperclass())
            if (nowc.getCanonicalName().equals(classCanonicalName)) {
                targetC = nowc;
                break;
            }
        if (targetC == null)
            return null;
        SheepMethodSpecial sms = SheepClass.findMethodSpecial(targetC, names);
        Object result = sms.invoke(this.raw, params);
        return result == null ? null : new SheepObject(result);
    }

    /**
     * @param classCanonicalName such as saltsheep.lib.asm.Test.B (B is a inner static class)
     * @param deobf              the name looks good.
     * @param universal          the name unreadable. (But you can think it is the another name)
     * @param param              the param input in method(for the param like Object... arg0, input Object[]).
     * @return invoke the method in target class.
     */
    public SheepObject invokeSpecial(String classCanonicalName, String deobf, String universal, Object param) {
        return invokeSpecial(classCanonicalName, deobf, universal, new Object[]{param});
    }

    /**
     * @return the field.
     */
    private SheepField field(String... names) {
        return this.rawClass().findField(names);
    }

    /**
     * @return field's value.
     */
    public SheepObject get(String... names) {
        Object result = this.field(names).get(this.raw);
        return result == null ? null : new SheepObject(result);
    }

    /**
     * set the field's value.
     *
     * @return itself, just call like chain!
     */
    public SheepObject set(Object value, String... names) {
        this.field(names).set(this.raw, value);
        return this;
    }

    public boolean isEmpty() {
        return this.raw == null;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SheepObject && ((SheepObject) obj).raw.equals(this.raw);
    }

    @Override
    public int hashCode() {
        return this.raw.hashCode();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "&" + this.raw.toString();
    }

}
