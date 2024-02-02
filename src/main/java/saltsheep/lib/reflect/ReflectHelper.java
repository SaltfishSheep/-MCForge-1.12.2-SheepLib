package saltsheep.lib.reflect;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import saltsheep.lib.SheepLib;

@Deprecated
public class ReflectHelper {

    @Deprecated
    public static Object getMCField(Object obj, String MCP, String SRG) {
        Object val = null;
        Field valField = null;
        try {
            valField = obj.getClass().getDeclaredField(MCP);
            valField.setAccessible(true);
            val = valField.get(obj);
            SheepLib.info(val);
        } catch (Exception error) {
            try {
                valField = obj.getClass().getDeclaredField(SRG);
                valField.setAccessible(true);
                val = valField.get(obj);
                SheepLib.info(val);
            } catch (Exception e) {
                SheepLib.printError(e);
            }
        }
        return val;
    }

    //*Suggest
    public static Object getMCFieldByClass(Class<?> cla, Object obj, String MCP, String SRG) {
        Object val = null;
        Field valField = null;
        try {
            for (Field filed : cla.getDeclaredFields()) {
                if (filed.getName().equals(MCP)) {
                    valField = filed;
                    valField.setAccessible(true);
                    val = valField.get(obj);
                    break;
                } else if (filed.getName().equals(SRG)) {
                    valField = filed;
                    valField.setAccessible(true);
                    val = valField.get(obj);
                    break;
                }
            }
        } catch (Exception error) {
            SheepLib.printError(error);
        }
        return val;
    }

    public static void setMCFieldByClass(Class<?> cla, Object obj, String MCP, String SRG, Object value) {
        Field valField = null;
        try {
            for (Field filed : cla.getDeclaredFields()) {
                if (filed.getName().equals(MCP)) {
                    valField = filed;
                    valField.setAccessible(true);
                    valField.set(obj, value);
                    break;
                } else if (filed.getName().equals(SRG)) {
                    valField = filed;
                    valField.setAccessible(true);
                    valField.set(obj, value);
                    break;
                }
            }

        } catch (Exception error) {
            SheepLib.printError(error);
        }
    }

    /*The most super method you can get is from 'parent',under the require, 'whoseParent' will get itself(if whoseParent==parent) or its super.
     * So I suggest you input the whoseParent Equaled with parent,it can avoid mistake.*/
    public static Object invokeParentsMethod(Class<?> whoseParent, Class<?> parent, Class<?> returnClass, Object obj, String methodName) {
        try {
            MethodType type = MethodType.methodType(returnClass);
            Field IMPL_LOOKUP;
            IMPL_LOOKUP = MethodHandles.lookup().getClass().getDeclaredField("IMPL_LOOKUP");
            IMPL_LOOKUP.setAccessible(true);
            MethodHandles.Lookup lookup = (Lookup) IMPL_LOOKUP.get(null);
            if (returnClass == void.class) {
                lookup.findSpecial(parent, methodName, type, whoseParent).bindTo(obj).invoke();
                return noReturn.NORETURN;
            }
            return lookup.findSpecial(parent, methodName, type, whoseParent).bindTo(obj).invoke();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return noReturn.NORETURN;
    }

    /*The most super method you can get is from 'parent',under the require, 'whoseParent' will get itself(if whoseParent==parent) or its super(only its father,without grandfather).
     * So I suggest you input the whoseParent Equaled with parent,it can avoid mistake.*/
    public static Object invokeParentsMethod(Class<?> whoseParent, Class<?> parent, Class<?> returnClass, Object obj, String methodName, Object... input) {
        try {
            Class<?>[] inputClass = new Class<?>[input.length];
            for (int i = 0; i < input.length; i++)
                inputClass[i] = input[i].getClass();
            MethodType type = MethodType.methodType(returnClass, inputClass);
            Field IMPL_LOOKUP = MethodHandles.lookup().getClass().getDeclaredField("IMPL_LOOKUP");
            IMPL_LOOKUP.setAccessible(true);
            MethodHandles.Lookup lookup = (Lookup) IMPL_LOOKUP.get(null);
            if (returnClass == void.class) {
                lookup.findSpecial(parent, methodName, type, whoseParent).bindTo(obj).invoke(input);
                return noReturn.NORETURN;
            }
            return lookup.findSpecial(parent, methodName, type, whoseParent).bindTo(obj).invoke(input);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return noReturn.NORETURN;
    }

    /*The most super method you can get is from 'parent',under the require, 'whoseParent' will get itself(if whoseParent==parent) or its super(only its father,without grandfather).
     * So I suggest you input the whoseParent Equaled with parent,it can avoid mistake.*/
    public static Object invokeMCParentsMethod(Class<?> whoseParent, Class<?> parent, Class<?> returnClass, Object obj, String MCP, String SRG, Object... input) {
        for (Method method : parent.getDeclaredMethods()) {
            if (method.getName().equals(MCP)) {
                Object mcpResult = null;
                if (input.length > 0)
                    mcpResult = invokeParentsMethod(whoseParent, parent, returnClass, obj, MCP, input);
                else
                    mcpResult = invokeParentsMethod(whoseParent, parent, returnClass, obj, MCP);
                return mcpResult;
            } else if (method.getName().equals(SRG)) {
                Object srgResult = null;
                if (input.length > 0)
                    srgResult = invokeParentsMethod(whoseParent, parent, returnClass, obj, SRG, input);
                else
                    srgResult = invokeParentsMethod(whoseParent, parent, returnClass, obj, SRG);
                return srgResult;
            }
        }
        SheepLib.getLogger().warn("No such method,named mcp:" + MCP + ";srg:" + SRG);
        return noReturn.NORETURN;
    }

    public static class noReturn {
        public static final noReturn NORETURN = new noReturn();
    }

}
