package saltsheep.lib.reflect;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;

public class ClassUtils {

    @SuppressWarnings("rawtypes")
    public static Class[] getClassByPackage(String packageName, Class classObj) throws Throwable {
        try {
            Enumeration<URL> resources = classObj.getClassLoader().getResources(packageName.replaceAll("\\.", "/"));
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                String[] file = new File(url.getFile()).list();
                Class[] classList = new Class[file.length];
                for (int i = 0; i < file.length; i++) {
                    classList[i] = Class.forName(packageName + "." + file[i].replaceAll("\\.class", ""));
                }
                return classList;
            }
        } catch (Throwable e) {
            throw e;
        }
        return null;
    }
}

