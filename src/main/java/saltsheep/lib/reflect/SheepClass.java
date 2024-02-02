package saltsheep.lib.reflect;

import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SheepClass {

    private static Map<Class<?>, SheepClass> classes = new HashMap<Class<?>, SheepClass>();

    public final Class<? extends Object> raw;

    private final Map<String, SheepField> fields = new HashMap<String, SheepField>();
    private final Map<String, SheepMethodVirtual> methodsVirtual = new HashMap<String, SheepMethodVirtual>();
    private final Map<String, SheepMethodStatic> methodsStatic = new HashMap<String, SheepMethodStatic>();

    private SheepClass(Class<?> raw) {
        this.raw = raw;
    }

    public static SheepClass instance(Class<?> raw) {
        SheepClass result = classes.get(raw);
        if (result == null) {
            result = new SheepClass(raw);
            classes.put(raw, result);
        }
        return result;
    }

    public static SheepField findFieldSpecial(Class<?> c, String... names) {
        SheepClass sc = instance(c);
        return sc.findField(names);
    }

    public static SheepMethodSpecial findMethodSpecial(Class<?> c, String... names) {
        SheepMethodVirtual vir = instance(c).findMethodVirtual(names);
        return new SheepMethodSpecial(vir.methods, c);
    }

    /**
     * @param name field names, you'd better keep them infer to the same field.
     * @return the nearest field if it has.
     */
    public SheepField findField(String... names) {
        for (String name : names) {
            SheepField f = fields.get(name);
            if (f != null)
                return f;
        }
        Field result = null;
        label:
        for (Class<?> fc = this.raw; fc != null; fc = fc.getSuperclass())
            for (Field f : fc.getDeclaredFields())
                for (String name : names)
                    if (f.getName().equals(name)) {
                        result = f;
                        break label;
                    }
        SheepField fieldWrapper = new SheepField(result);
        for (String name : names) {
            fields.put(name, fieldWrapper);
        }
        return fieldWrapper;
    }

    /**
     * @param names Hum, you'd better keep all names infer to the same method, else may cause some problem!
     * @return a total of all the method has a require name.(And will sort!Let the accurate one first, it can help invoke as the normal situation, such as m(Object) and m(Integer), will invoke m(Integer) first!)
     */
    public SheepMethodVirtual findMethodVirtual(String... names) {
        for (String name : names) {
            SheepMethodVirtual m = methodsVirtual.get(name);
            if (m != null)
                return m;
        }
        List<Method> methods = Lists.newArrayList();
        //*遍历所有类
        for (Class<?> nowc = this.raw; nowc != null; nowc = nowc.getSuperclass()) {
            //*遍历当前类的所有方法
            Method:
            for (Method m : nowc.getDeclaredMethods()) {
                //*不存入静态方法
                if (Modifier.isStatic(m.getModifiers()))
                    continue Method;
                boolean isTargetMethod = false;
                //*是否是指定方法
                NameCheck:
                for (String name : names) {
                    if (m.getName().equals(name)) {
                        isTargetMethod = true;
                        break NameCheck;
                    }
                }
                if (!isTargetMethod)
                    continue Method;
                //*检查是否已经存入该方法
                for (int i = 0; i < methods.size(); i++) {
                    //*即，已经存入该方法时，不再存入
                    if (MethodChecker.isOverride(m, methods.get(i)))
                        continue Method;
                }
                methods.add(m);
            }
        }
        //*将范围窄的方法尽可能地靠前
        for (int i1 = 0, length = methods.size(); i1 < length; i1++)
            for (int i2 = i1 + 1; i2 < length; i2++)
                if (MethodChecker.isAccurate(methods.get(i1), methods.get(i2))) {
                    Method rude = methods.get(i1);
                    Method accurate = methods.get(i2);
                    methods.set(i2, rude);
                    methods.set(i1, accurate);
                }
        SheepMethodVirtual total = new SheepMethodVirtual(methods);
        for (String name : names)
            methodsVirtual.put(name, total);
        return total;
    }

    public SheepMethodStatic findMethodStatic(String... names) {
        for (String name : names) {
            SheepMethodStatic m = methodsStatic.get(name);
            if (m != null)
                return m;
        }
        List<Method> methods = Lists.newArrayList();
        Class<?> nowc = this.raw;
        //*遍历当前类的所有方法
        Method:
        for (Method m : nowc.getDeclaredMethods()) {
            //*只存入静态方法
            if (!Modifier.isStatic(m.getModifiers()))
                continue Method;
            boolean isTargetMethod = false;
            //*是否是指定方法
            NameCheck:
            for (String name : names) {
                if (m.getName().equals(name)) {
                    isTargetMethod = true;
                    break NameCheck;
                }
            }
            if (!isTargetMethod)
                continue Method;
            //*检查是否已经存入该方法
            for (int i = 0; i < methods.size(); i++) {
                //*即，已经存入该方法时，不再存入
                if (MethodChecker.isOverride(m, methods.get(i)))
                    continue Method;
            }
            methods.add(m);
        }
        SheepMethodStatic total = new SheepMethodStatic(methods, nowc);
        for (String name : names)
            methodsStatic.put(name, total);
        return total;
    }

    public void clearField(String... names) {
        for (String name : names)
            this.fields.remove(name);
    }

    public void clearMethodVirtual(String... names) {
        for (String name : names)
            this.methodsVirtual.remove(name);
    }

    public void clearMethodStatic(String... names) {
        for (String name : names)
            this.methodsStatic.remove(name);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SheepClass && ((SheepClass) obj).raw == this.raw;
    }

    @Override
    public int hashCode() {
        return this.raw.hashCode();
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "&" + this.raw.toString();
    }

}
