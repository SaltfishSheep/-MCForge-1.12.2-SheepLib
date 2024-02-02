package saltsheep.lib.command;

import javax.annotation.Nullable;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import saltsheep.lib.SheepLib;
import saltsheep.lib.common.BaseType;
import saltsheep.lib.common.Function;
import saltsheep.lib.common.NumberHelper;
import saltsheep.lib.exception.InformationUnsameException;
import saltsheep.lib.exception.InputIllegalException;
import saltsheep.lib.exception.UnknowInformationException;

public class CommandSon {

    private boolean mustPlayerRun;
    private int originalArgsLength;
    private String[] commandFlag;
    private BaseType<?>[] usefulArgsType;
    private Function.Void execute;

    /*
     * The first arg of execute will be command sender,
     * and the rest will be the args.
     */
    public CommandSon(boolean mustPlayerRun, int originalArgsLength, @Nullable String[] commandFlag, @Nullable BaseType<?>[] usefulArgsType, Function.Void execute) throws InformationUnsameException, InputIllegalException {
        if (commandFlag == null)
            commandFlag = new String[0];
        if (commandFlag.length != 0)
            for (String eachFlag : commandFlag)
                if (eachFlag.isEmpty())
                    throw new InputIllegalException();
        if (originalArgsLength < commandFlag.length)
            throw new InformationUnsameException();
        if (usefulArgsType == null)
            usefulArgsType = new BaseType[0];
        this.mustPlayerRun = mustPlayerRun;
        this.originalArgsLength = originalArgsLength;
        this.commandFlag = commandFlag;
        this.usefulArgsType = usefulArgsType;
        this.execute = execute;
    }

    public int getFlagLength() {
        return this.commandFlag.length;
    }

    public boolean isMatch(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length != originalArgsLength)
            return false;
        if (commandFlag != null || commandFlag.length != 0)
            for (int i = 0; i < commandFlag.length; i++)
                if (!commandFlag[i].equals(args[i]))
                    return false;
        if (mustPlayerRun && !(sender instanceof EntityPlayer)) {
            sender.sendMessage(new TextComponentString("Warning!You are trying to use a command which is player only by not player."));
            return false;
        }
        return true;
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws UnknowInformationException, InputIllegalException {
        Object[] finalArgs = new Object[originalArgsLength - commandFlag.length + 1];
        finalArgs[0] = sender;
        for (int i = 1; i < finalArgs.length; i++) {

            if (usefulArgsType[i - 1] == BaseType.STRING)
                finalArgs[i] = args[i - 1];
            else if (usefulArgsType[i - 1] == BaseType.CHAR)
                //*!!Unuseful,plz use STRING.
                finalArgs[i] = args[i - 1];
            else if (usefulArgsType[i - 1] == BaseType.DOUBLE) {
                if (NumberHelper.isNumeric(args[i - 1])) {
                    finalArgs[i] = Double.valueOf(args[i - 1]);
                } else {
                    SheepLib.getLogger().warn("input:" + finalArgs[i]);
                    throw new InputIllegalException();
                }
            } else if (usefulArgsType[i - 1] == BaseType.FLOAT) {
                if (NumberHelper.isNumeric(args[i - 1])) {
                    finalArgs[i] = Float.valueOf(args[i - 1]);
                } else {
                    SheepLib.getLogger().warn("input:" + finalArgs[i]);
                    throw new InputIllegalException();
                }
            } else if (usefulArgsType[i - 1] == BaseType.INT) {
                if (NumberHelper.isInteger(args[i - 1])) {
                    finalArgs[i] = Integer.valueOf(args[i - 1]);
                } else {
                    SheepLib.getLogger().warn("input:" + finalArgs[i]);
                    throw new InputIllegalException();
                }
            } else if (usefulArgsType[i - 1] == BaseType.LONG) {
                if (NumberHelper.isInteger(args[i - 1])) {
                    finalArgs[i] = Long.valueOf(args[i - 1]);
                } else {
                    SheepLib.getLogger().warn("input:" + finalArgs[i]);
                    throw new InputIllegalException();
                }
            } else
                throw new UnknowInformationException();
        }
        this.execute.invoke(finalArgs);
    }

    @Nullable
    public String getTab(String[] args) {
        int point = args.length - 1;
        if (point < this.getFlagLength()) {
            for (int i = 0; i < point; i++) {
                if (!args[i].equals(this.commandFlag[i]))
                    return null;
            }
            return this.commandFlag[point];
        }
        return null;
    }

}
