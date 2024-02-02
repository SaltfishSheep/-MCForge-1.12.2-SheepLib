package saltsheep.lib.command;

import net.minecraft.command.CommandSenderWrapper;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;
import saltsheep.lib.SheepLib;

public class CommandHelper {

    public static void executeCommand(EntityPlayerMP player, String command) {
        ICommandSender icommandsender = CommandSenderWrapper.create(player.server).withEntity(player, new Vec3d(player.posX, player.posY, player.posZ)).withSendCommandFeedback(player.server.worlds[0].getGameRules().getBoolean("commandBlockOutput"));
        player.server.commandManager.executeCommand(icommandsender, command);
    }

    public static void executeCommand(String command) {
        SheepLib.getMCServer().commandManager.executeCommand(SheepLib.getMCServer(), command);
    }

}
