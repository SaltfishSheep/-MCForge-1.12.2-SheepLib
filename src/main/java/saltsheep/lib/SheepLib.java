package saltsheep.lib;

import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;
import saltsheep.lib.asm.Test;
import saltsheep.lib.asm.TestMark;
import saltsheep.lib.network.NetworkHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

//*The real server need only.
@Mod(modid = SheepLib.MODID, name = SheepLib.NAME, version = SheepLib.VERSION, acceptableRemoteVersions = "*")
public class SheepLib {
    public static final String MODID = "sheeplib";
    public static final String NAME = "SheepLib";
    public static final String VERSION = "1.12";
    public static SheepLib instance;

    private static Logger logger;

    public SheepLib() {
        instance = this;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        instance = this;
        NetworkHandler.register();
        ((LaunchClassLoader)this.getClass().getClassLoader()).registerTransformer("saltsheep.lib.asm.Trans");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("Is Sheep ASM work? " + (new Test.Test2() instanceof TestMark));
        try{
            Method[] methods = Test.class.getDeclaredMethods();
            Field[] fields = Test.class.getDeclaredFields();
            logger.info("Test methods count:"+methods.length);
            logger.info("Test fields count:"+fields.length);
            new Test().method1("Unknown","Method replace successful!");
            ((TestMark)new Test.Test2()).method4("Unknown","Method rename successful!");
            /*
            for(Method m:methods) {
                if (m.getName().equals("method4")) {
                    logger.info("Method rename successful!");
                    break;
                }
            }*/
            for(Field f:fields)
                if(f.getName().equals("fieldTest2")){
                    logger.info("Field rename successful!");
                    break;
                }
        }catch(Throwable error){
            error.printStackTrace();
        }
    }

    @EventHandler
    public static void onServerStarting(FMLServerStartingEvent event) {
    }

    public static Logger getLogger() {
        return logger;
    }

    public static MinecraftServer getMCServer() {
        return FMLCommonHandler.instance().getMinecraftServerInstance();
    }

    public static void printError(Throwable error) {
        String messages = "";
        for (StackTraceElement stackTrace : error.getStackTrace()) {
            messages = messages + stackTrace.toString() + "\n";
        }
        SheepLib.getLogger().error("Warning!There's some error in my mod,Error Type "+error.getClass()+":\n"+messages);
    }

    public static void info(String str) {
        logger.info(str);
    }

    public static void info(Object obj) {
        if (obj == null)
            logger.info("null has such obj.");
        else
            logger.info(obj.toString());
    }

}
